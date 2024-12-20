package datos;

import java.io.*;
import java.sql.*;
import java.util.*;
import javax.swing.JLabel;

// IAG: Modificado (ChatGPT y GitHub Copilot)
public class GestorBD {
    private static final String DB_URL = "jdbc:sqlite:resources/db/casino.db";
    private static JLabel lblMainMenu;
    private static JLabel lblGameMenu;

    public static boolean crearBD() {
        try {
            // Leer configuración
            boolean crearBD = leerConfig("resources/db/crearBD.txt");
            if (crearBD) {
                // Cambiar txt a false
                try (BufferedWriter bw = new BufferedWriter(new FileWriter("resources/db/crearBD.txt"))) {
                    bw.write("false");
                }
                // Crear base de datos
                inicializarBaseDeDatos();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private static boolean leerConfig(String rutaConfig) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaConfig))) {
            return Boolean.parseBoolean(br.readLine().trim());
        }
    }

    private static void inicializarBaseDeDatos() throws IOException {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            if (conn != null) {
                crearTablas(conn);
                cargarDatos(conn);
                System.out.println("Base de datos inicializada correctamente.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void crearTablas(Connection conn) throws SQLException {
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
        }
    }

    private static void cargarDatos(Connection conn) throws SQLException, IOException {
        Map<String, Integer> saldos = leerCartera("resources/csv/cartera.csv");
        Map<String, String> contrasenas = leerUsuarioContra("resources/csv/usuarioContra.csv");
        List<String[]> datosUsuario = leerCSV("resources/csv/usuarioDatos.csv");
        List<String[]> historialMovimientos = leerCSV("resources/csv/historialMovimientos.csv");

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
        }

    }

    private static Map<String, Integer> leerCartera(String ruta) throws IOException {
        Map<String, Integer> saldos = new HashMap<>();
        List<String[]> filas = leerCSV(ruta);
        for (String[] fila : filas) {
            saldos.put(fila[0], Integer.parseInt(fila[1]));
        }
        return saldos;
    }

    private static Map<String, String> leerUsuarioContra(String ruta) throws IOException {
        Map<String, String> contrasenas = new HashMap<>();
        List<String[]> filas = leerCSV(ruta);
        for (String[] fila : filas) {
            contrasenas.put(fila[0], fila[1]);
        }
        return contrasenas;
    }

    private static List<String[]> leerCSV(String ruta) throws IOException {
        List<String[]> filas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                filas.add(linea.split(","));
            }
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return historial;
    }

    public static boolean cambiarContrasena(String usuario, String nuevaContrasena) {
        String sql = "UPDATE usuario SET contrasena = ? WHERE usuario = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nuevaContrasena);
            ps.setString(2, usuario);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
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
                    return rs.getString("contrasena");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
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
            e.printStackTrace();
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
            return cantidad;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static boolean usuarioExiste(String usuario) {
        String sql = "SELECT 1 FROM usuario WHERE usuario = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, usuario);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
                    return rs.getInt("fichas");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean agregarMovimiento(String usuario, int cantidad, AsuntoMovimiento asunto) {
        if (-cantidad > obtenerSaldo(usuario)) {
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
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
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