package datos;

import java.io.*;
import java.nio.file.*;
import java.sql.*;
import java.util.*;

public class CrearBD {
    private static final String DB_URL = "jdbc:sqlite:resources/db/casino.db";
    private static final String CONFIG_PATH = "resources/db/crearBD.txt";
    private static final String CSV_DIR = "resources/csv/";

    private static Connection dbConnection;

    public CrearBD() {
        try {
            openConnection();
            GestorMovimientos.setConnection(dbConnection);
            GestorUsuarios.setConnection(dbConnection);
            boolean createDatabase = readConfig();
            if (createDatabase) {
                initializeDatabase();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean readConfig() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(CONFIG_PATH));
        return lines.size() > 0 && lines.get(0).trim().equalsIgnoreCase("true");
    }

    private void initializeDatabase() throws SQLException, IOException {
        try {
            if (dbConnection != null) {
                createTables();
                populateTables();
                System.out.println("Database initialized successfully.");
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    private void openConnection() throws SQLException {
        if (dbConnection == null || dbConnection.isClosed()) {
            dbConnection = DriverManager.getConnection(DB_URL);
        }
    }

    private void createTables() throws SQLException {
        String carteraTable = """
                CREATE TABLE IF NOT EXISTS Cartera (
                    Usuario TEXT PRIMARY KEY,
                    Saldo REAL
                );
                """;

        String usuarioContraTable = """
                CREATE TABLE IF NOT EXISTS UsuarioContra (
                    Usuario TEXT PRIMARY KEY,
                    Contrasena TEXT
                );
                """;

        String usuarioDatosTable = """
                CREATE TABLE IF NOT EXISTS UsuarioDatos (
                    Usuario TEXT PRIMARY KEY,
                    Nombre TEXT,
                    Apellidos TEXT,
                    DNI TEXT,
                    Mail TEXT,
                    Prefijo TEXT,
                    Telefono TEXT,
                    Provincia TEXT,
                    Ciudad TEXT,
                    Calle TEXT,
                    NumeroCalle INTEGER,
                    FechaNacimiento TEXT,
                    FechaInscripcion TEXT
                );
                """;

        String historialMovimientosTable = """
                CREATE TABLE IF NOT EXISTS HistorialMovimientos (
                    Fecha TEXT,
                    Hora TEXT,
                    Usuario TEXT,
                    Cantidad REAL,
                    Asunto TEXT,
                    SaldoFinal REAL
                );
                """;

        try (Statement stmt = dbConnection.createStatement()) {
            stmt.execute(carteraTable);
            stmt.execute(usuarioContraTable);
            stmt.execute(usuarioDatosTable);
            stmt.execute(historialMovimientosTable);
        }
    }

    private void populateTables() throws SQLException, IOException {
        populateTable("Cartera", "Usuario,Saldo", CSV_DIR + "cartera.csv");
        populateTable("UsuarioContra", "Usuario,Contrasena", CSV_DIR + "usuarioContra.csv");
        populateTable("UsuarioDatos",
                "Usuario,Nombre,Apellidos,DNI,Mail,Prefijo,Telefono,Provincia,Ciudad,Calle,NumeroCalle,FechaNacimiento,FechaInscripcion",
                CSV_DIR + "usuarioDatos.csv");
        populateTable("HistorialMovimientos",
                "Fecha,Hora,Usuario,Cantidad,Asunto,SaldoFinal", CSV_DIR + "historialMovimientos.csv");
    }

    private void populateTable(String tableName, String columns, String csvFilePath)
            throws SQLException, IOException {
        try (BufferedReader br = Files.newBufferedReader(Paths.get(csvFilePath))) {
            String line;
            String sql = "INSERT INTO " + tableName + " (" + columns + ") VALUES ";
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String placeholders = String.join(",", Collections.nCopies(values.length, "?"));
                String insertSQL = sql + "(" + placeholders + ")";
                try (PreparedStatement pstmt = dbConnection.prepareStatement(insertSQL)) {
                    for (int i = 0; i < values.length; i++) {
                        pstmt.setString(i + 1, values[i].trim());
                    }
                    pstmt.executeUpdate();
                }
            }
        }
    }

    public static void main(String[] args) {
        new CrearBD();
    }
}
