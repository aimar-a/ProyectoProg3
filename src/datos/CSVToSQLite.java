package datos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

// IAG
public class CSVToSQLite {

    private static final String DB_URL = "jdbc:sqlite:resources/db/historialMovimientos.db";
    private static final String CSV_FILE = "resources/csv/historialMovimientos.csv";

    public static void main(String[] args) {
        try {
            // Crear la conexión a la base de datos
            Connection conn = DriverManager.getConnection(DB_URL);
            System.out.println("Conexión a SQLite establecida.");

            // Crear la tabla si no existe
            try (Statement stmt = conn.createStatement()) {
                String createTableSQL = "CREATE TABLE IF NOT EXISTS historial_movimientos ("
                        + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + "fecha TEXT NOT NULL, "
                        + "hora TEXT NOT NULL, "
                        + "usuario TEXT NOT NULL, "
                        + "cantidad_movida REAL NOT NULL, "
                        + "descripcion TEXT NOT NULL, "
                        + "saldo_final REAL NOT NULL"
                        + ");";
                stmt.execute(createTableSQL);
                System.out.println("Tabla creada o ya existente.");
            }

            // Leer el archivo CSV
            try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE))) {
                String line;
                boolean isHeader = true;

                String insertSQL = "INSERT INTO historial_movimientos (fecha, hora, usuario, cantidad_movida, descripcion, saldo_final) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement pstmt = conn.prepareStatement(insertSQL);

                while ((line = br.readLine()) != null) {
                    // Saltar la cabecera
                    if (isHeader) {
                        isHeader = false;
                        continue;
                    }

                    // Separar las columnas por coma
                    String[] columns = line.split(",");
                    if (columns.length != 6) {
                        System.err.println("Línea mal formateada: " + line);
                        continue;
                    }

                    // Configurar parámetros
                    pstmt.setString(1, columns[0]); // fecha
                    pstmt.setString(2, columns[1]); // hora
                    pstmt.setString(3, columns[2]); // usuario
                    pstmt.setDouble(4, Double.parseDouble(columns[3])); // cantidad_movida
                    pstmt.setString(5, columns[4]); // descripcion
                    pstmt.setDouble(6, Double.parseDouble(columns[5])); // saldo_final

                    pstmt.addBatch();
                }

                // Ejecutar el batch
                pstmt.executeBatch();
                System.out.println("Datos insertados correctamente en la base de datos.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
