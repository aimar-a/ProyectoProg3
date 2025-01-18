package db;

import domain.datos.AsuntoMovimiento;
import io.ConfigProperties;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.swing.JLabel;

public class GestorBD {
    private static final String DB_URL = ConfigProperties.getDbUrl();
    private static final String DB_DRIVER = ConfigProperties.getDbDriver();
    private static final String DB_DIR = ConfigProperties.getDbDir();
    private static JLabel lblMainMenu;
    private static JLabel lblGameMenu;

    private static final Logger logger = Logger.getLogger(GestorBD.class.getName());

    static {
        try {
            // Create log directory if it doesn't exist
            File logDir = new File("log");
            if (!logDir.exists()) {
                logDir.mkdirs();
            }

            // Configure logger to write to a file
            FileHandler fileHandler = new FileHandler("log/gestorBD.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            logger.setLevel(Level.ALL);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error configuring logger", e);
        }
    }

    public static void init() {
        try {
            Class.forName(DB_DRIVER);
            logger.info("Driver cargado.");
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Error al cargar el driver.", e);
        }
        if (ConfigProperties.isDbCreate()) {
            borrarBD();
            crearTablas();
        } else if (ConfigProperties.isDbReCreateTables()) {
            borrarTablas();
            crearTablas();
        }
        if (ConfigProperties.isDbLoadFromCSV()) {
            limpiarTablas();
            cargarDatosDeCSV();
        }
    }

    public static void borrarBD() {
        try {
            Files.deleteIfExists(Paths.get(DB_DIR));
            logger.info("Base de datos borrada.");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error al borrar la base de datos.", e);
        }
    }

    public static void borrarTablas() {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            if (conn != null) {
                String borrarUsuario = "DROP TABLE IF EXISTS usuario;";
                String borrarHistorialMovimientos = "DROP TABLE IF EXISTS historial_movimientos;";
                String borrarAsunto = "DROP TABLE IF EXISTS asunto;";
                try (Statement stmt = conn.createStatement()) {
                    stmt.execute(borrarUsuario);
                    stmt.execute(borrarHistorialMovimientos);
                    stmt.execute(borrarAsunto);
                    logger.info("Tablas borradas.");
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al borrar las tablas.", e);
        }
    }

    public static void limpiarTablas() {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            if (conn != null) {
                String limpiarUsuario = "DELETE FROM usuario;";
                String limpiarHistorialMovimientos = "DELETE FROM historial_movimientos;";
                String limpiarAsunto = "DELETE FROM asunto;";
                try (Statement stmt = conn.createStatement()) {
                    stmt.execute(limpiarUsuario);
                    stmt.execute(limpiarHistorialMovimientos);
                    stmt.execute(limpiarAsunto);
                    logger.info("Tablas limpiadas.");
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al limpiar las tablas.", e);
        }
    }

    public static void crearTablas() {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            if (conn != null) {
                String crearUsuario = """
                        CREATE TABLE IF NOT EXISTS usuario (
                            usuario TEXT PRIMARY KEY,
                            contrasena TEXT NOT NULL,
                            fichas INTEGER NOT NULL,
                            nombre TEXT,
                            apellidos TEXT,
                            dni TEXT,
                            mail TEXT,
                            prefijo TEXT,
                            telefono TEXT,
                            provincia TEXT,
                            ciudad TEXT,
                            calle TEXT,
                            numero_calle TEXT,
                            fecha_nacimiento TEXT,
                            fecha_registro TEXT
                        );
                        """;

                String crearHistorialMovimientos = """
                        CREATE TABLE IF NOT EXISTS historial_movimientos (
                            id INTEGER PRIMARY KEY AUTOINCREMENT,
                            fecha TEXT,
                            hora TEXT,
                            usuario TEXT,
                            cantidad INTEGER,
                            asunto INTEGER,
                            saldo_final INTEGER,
                            FOREIGN KEY (usuario) REFERENCES usuario (usuario),
                            FOREIGN KEY (asunto) REFERENCES asunto (id)
                        );
                        """;

                String crearAsunto = """
                        CREATE TABLE IF NOT EXISTS asunto (
                            id INTEGER PRIMARY KEY AUTOINCREMENT,
                            asunto TEXT NOT NULL
                        );
                        """;

                try (Statement stmt = conn.createStatement()) {
                    stmt.execute(crearUsuario);
                    stmt.execute(crearHistorialMovimientos);
                    stmt.execute(crearAsunto);
                    logger.info("Tablas creadas.");
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al crear las tablas.", e);
        }
    }

    private static boolean leerConfig(String rutaConfig) {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaConfig))) {
            return Boolean.parseBoolean(br.readLine().trim());
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error al leer la configuración.", e);
            return false;
        }
    }

    private static void cargarDatosDeCSV() {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            Map<String, Integer> saldos = leerCartera(ConfigProperties.getDbDirCsvBalancesCSV());
            logger.info("Saldos leídos.");
            Map<String, String> contrasenas = leerUsuarioContra(ConfigProperties.getDbDirPasswordsCSV());
            logger.info("Contraseñas leídas.");
            System.out.println(ConfigProperties.getDbDirUsersDataCSV());
            List<String[]> datosUsuario = leerCSV(ConfigProperties.getDbDirUsersDataCSV());
            logger.info("Datos de usuario leídos.");
            List<String[]> historialMovimientos = leerCSV(ConfigProperties.getDbDirCsvTransactionsCSV());
            logger.info("Historial de movimientos leído.");

            // Insertar usuarios
            String insertarUsuario = """
                    INSERT INTO usuario (
                        usuario, contrasena, fichas, nombre, apellidos, dni, mail, prefijo, telefono,
                        provincia, ciudad, calle, numero_calle, fecha_nacimiento, fecha_registro
                    ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
                    """;

            try (PreparedStatement ps = conn.prepareStatement(insertarUsuario)) {
                for (String[] fila : datosUsuario) {
                    String usuario = fila[0];
                    ps.setString(1, usuario);
                    ps.setString(2, contrasenas.getOrDefault(usuario, ""));
                    ps.setInt(3, saldos.getOrDefault(usuario, 0));
                    for (int i = 1; i < fila.length; i++) {
                        ps.setString(i + 3, fila[i]);
                    }
                    ps.executeUpdate();
                }
                logger.info("Usuarios insertados.");
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Error al insertar usuarios.", e);
            }

            // Insertar asuntos
            String insertarAsunto = """
                    INSERT INTO asunto (asunto) VALUES (?);
                    """;

            try (PreparedStatement ps = conn.prepareStatement(insertarAsunto)) {
                for (AsuntoMovimiento asunto : AsuntoMovimiento.values()) {
                    ps.setString(1, asunto.getNombre());
                    ps.executeUpdate();
                }
                logger.info("Asuntos insertados.");
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Error al insertar asuntos.", e);
            }

            // Insertar historial de movimientos
            String insertarMovimiento = """
                    INSERT INTO historial_movimientos (fecha, hora, usuario, cantidad, asunto, saldo_final)
                    VALUES (?, ?, ?, ?, ?, ?);
                    """;

            try (PreparedStatement ps = conn.prepareStatement(insertarMovimiento)) {
                for (String[] fila : historialMovimientos) {
                    ps.setString(1, fila[0]);
                    ps.setString(2, fila[1]);
                    ps.setString(3, fila[2]);
                    ps.setInt(4, Integer.parseInt(fila[3]));
                    AsuntoMovimiento asunto = AsuntoMovimiento.getAsuntoMovimiento(fila[4]);
                    ps.setInt(5, asunto.ordinal() + 1);
                    ps.setInt(6, Integer.parseInt(fila[5]));
                    ps.executeUpdate();
                }
                logger.info("Historial de movimientos insertado.");
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Error al insertar historial de movimientos.", e);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al cargar datos de CSV.", e);
        }
    }

    private static Map<String, Integer> leerCartera(String ruta) {
        Map<String, Integer> saldos = new HashMap<>();
        try {
            List<String[]> filas = leerCSV(ruta);
            for (String[] fila : filas) {
                saldos.put(fila[0], Integer.parseInt(fila[1]));
            }
        } catch (NumberFormatException e) {
            logger.log(Level.SEVERE, "Error al leer cartera.", e);
        }
        return saldos;
    }

    private static Map<String, String> leerUsuarioContra(String ruta) {
        Map<String, String> contrasenas = new HashMap<>();
        try {
            List<String[]> filas = leerCSV(ruta);
            for (String[] fila : filas) {
                contrasenas.put(fila[0], fila[1]);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al leer usuario y contraseñas.", e);
        }
        return contrasenas;
    }

    private static List<String[]> leerCSV(String ruta) {
        List<String[]> filas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                filas.add(linea.split(","));
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error al leer CSV.", e);
        }
        return filas;
    }

    public static List<String[]> obtenerHistorial(String usuario) {
        String sql = """
                SELECT fecha, hora, cantidad, asunto, saldo_final
                FROM historial_movimientos
                WHERE usuario = ?
                ORDER BY id DESC
                """;
        List<String[]> historial = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL);
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, usuario);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    historial.add(new String[] {
                            rs.getString("fecha"), rs.getString("hora"), rs.getString("cantidad"),
                            AsuntoMovimiento.values()[rs.getInt("asunto") - 1].getNombre(),
                            rs.getString("saldo_final")
                    });
                }
                logger.info("Historial obtenido para el usuario: " + usuario);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al obtener historial.", e);
        }
        return historial;
    }

    public static boolean cambiarContrasena(String usuario, String nuevaContrasena) {
        String sql = "UPDATE usuario SET contrasena = ? WHERE usuario = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nuevaContrasena);
            ps.setString(2, usuario);
            boolean result = ps.executeUpdate() > 0;
            if (result) {
                logger.info("Contraseña cambiada para el usuario: " + usuario);
            }
            return result;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al cambiar contraseña.", e);
        }
        return false;
    }

    public static String obtenerContrasena(String usuario) {
        String sql = "SELECT contrasena FROM usuario WHERE usuario = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, usuario);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    logger.info("Contraseña obtenida para el usuario: " + usuario);
                    return rs.getString("contrasena");
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al obtener contraseña.", e);
        }
        return null;
    }

    public static boolean cambiarDatos(String usuario, String[] nuevosDatos) {
        String sql = """
                UPDATE usuario
                SET nombre = ?, apellidos = ?, dni = ?, mail = ?, prefijo = ?, telefono = ?,
                    provincia = ?, ciudad = ?, calle = ?, numero_calle = ?, fecha_nacimiento = ?
                WHERE usuario = ?
                """;
        try (Connection conn = DriverManager.getConnection(DB_URL);
                PreparedStatement ps = conn.prepareStatement(sql)) {
            for (int i = 0; i < nuevosDatos.length; i++) {
                ps.setString(i + 1, nuevosDatos[i]);
            }
            ps.setString(nuevosDatos.length + 1, usuario);
            boolean result = ps.executeUpdate() > 0;
            if (result) {
                logger.info("Datos cambiados para el usuario: " + usuario);
            }
            return result;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al cambiar datos.", e);
        }
        return false;
    }

    public static String[] obtenerDatos(String usuario) {
        String sql = "SELECT * FROM usuario WHERE usuario = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, usuario);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    logger.info("Datos obtenidos para el usuario: " + usuario);
                    return new String[] {
                            rs.getString("nombre"), rs.getString("apellidos"), rs.getString("dni"),
                            rs.getString("mail"), rs.getString("prefijo"), rs.getString("telefono"),
                            rs.getString("provincia"), rs.getString("ciudad"), rs.getString("calle"),
                            rs.getString("numero_calle"), rs.getString("fecha_nacimiento"),
                            rs.getString("fecha_registro")
                    };
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al obtener datos.", e);
        }
        return null;
    }

    public static int agregarUsuario(String usuario, String contrasena, String[] datos) {
        String sql = """
                INSERT INTO usuario (
                    usuario, contrasena, fichas, nombre, apellidos, dni, mail, prefijo, telefono,
                    provincia, ciudad, calle, numero_calle, fecha_nacimiento, fecha_registro
                ) VALUES (?, ?, 0, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
                """;
        try (Connection conn = DriverManager.getConnection(DB_URL);
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, usuario);
            ps.setString(2, contrasena);
            for (int i = 0; i < datos.length; i++) {
                ps.setString(i + 3, datos[i]);
            }
            ps.executeUpdate();
            Random r = new Random();
            int cantidad = r.nextInt(999) + 1;
            agregarMovimiento(usuario, cantidad, AsuntoMovimiento.BIENVENIDA);
            logger.info("Usuario agregado: " + usuario);
            return cantidad;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al agregar usuario.", e);
        }
        return 0;
    }

    public static boolean usuarioExiste(String usuario) {
        String sql = "SELECT 1 FROM usuario WHERE usuario = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, usuario);
            try (ResultSet rs = ps.executeQuery()) {
                boolean exists = rs.next();
                logger.info("Usuario " + usuario + " existe: " + exists);
                return exists;
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al verificar si el usuario existe.", e);
        }
        return false;
    }

    public static int obtenerSaldo(String usuario) {
        String sql = "SELECT fichas FROM usuario WHERE usuario = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, usuario);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int saldo = rs.getInt("fichas");
                    logger.info("Saldo obtenido para el usuario " + usuario + ": " + saldo);
                    return saldo;
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al obtener saldo.", e);
        }
        return 0;
    }

    private static boolean actualizarSaldo(String usuario, int cantidad) {
        String sql = "UPDATE usuario SET fichas = ? WHERE usuario = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cantidad);
            ps.setString(2, usuario);
            if (ps.executeUpdate() > 0) {
                updateLabels(cantidad);
                logger.info("Saldo actualizado para el usuario " + usuario + ": " + cantidad);
                return true;
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al actualizar saldo.", e);
        }
        return false;
    }

    public static boolean agregarMovimiento(String usuario, int cantidad, AsuntoMovimiento asunto) {
        if (-cantidad > obtenerSaldo(usuario)) {
            logger.warning("Saldo insuficiente para el usuario " + usuario);
            return false;
        }
        if (!actualizarSaldo(usuario, obtenerSaldo(usuario) + cantidad)) {
            return false;
        }
        String sql = """
                INSERT INTO historial_movimientos (fecha, hora, usuario, cantidad, asunto, saldo_final)
                VALUES (date('now'), time('now'), ?, ?, ?, (SELECT fichas FROM usuario WHERE usuario = ?))
                """;
        try (Connection conn = DriverManager.getConnection(DB_URL);
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, usuario);
            ps.setInt(2, cantidad);
            ps.setInt(3, asunto.ordinal() + 1);
            ps.setString(4, usuario);
            boolean result = ps.executeUpdate() > 0;
            if (result) {
                logger.info("Movimiento agregado para el usuario " + usuario + ": " + cantidad + " fichas, asunto: "
                        + asunto.getNombre());
            }
            return result;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al agregar movimiento.", e);
        }
        return false;
    }

    public static void setLblMainMenu(JLabel lblMainMenu) {
        GestorBD.lblMainMenu = lblMainMenu;
    }

    public static void setLblGameMenu(JLabel lblGameMenu) {
        GestorBD.lblGameMenu = lblGameMenu;
    }

    private static void updateLabels(int nuevoSaldo) {
        if (lblMainMenu != null) {
            lblMainMenu.setText("Saldo: " + nuevoSaldo + " fichas  ");
        }
        if (lblGameMenu != null) {
            lblGameMenu.setText("Saldo: " + nuevoSaldo + " fichas  ");
        }
    }
}