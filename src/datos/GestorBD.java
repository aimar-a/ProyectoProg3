package datos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GestorBD {
    private static final String DB_URL = "jdbc:sqlite:resources/db/historialMovimientos.db";

    public GestorBD() {
        // Crear la tabla si no existe al inicializar la clase
        try (Connection conn = DriverManager.getConnection(DB_URL);
                Statement stmt = conn.createStatement()) {
            String createTableSQL = """
                        CREATE TABLE IF NOT EXISTS historial_movimientos (
                            id INTEGER PRIMARY KEY AUTOINCREMENT,
                            fecha TEXT NOT NULL,
                            hora TEXT NOT NULL,
                            usuario TEXT NOT NULL,
                            cantidad_movida REAL NOT NULL,
                            descripcion TEXT NOT NULL,
                            saldo_final REAL NOT NULL
                        );
                    """;
            stmt.execute(createTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<String[]> obtenerHistorial(String usuario) {
        List<String[]> historial = new ArrayList<>();
        String query = "SELECT fecha, hora, cantidad_movida, descripcion, saldo_final FROM historial_movimientos WHERE usuario = ? ORDER BY id ASC";
        try (Connection conn = DriverManager.getConnection(DB_URL);
                PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, usuario);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String[] movimiento = new String[] {
                        rs.getString("fecha"),
                        rs.getString("hora"),
                        String.format("%.2f", rs.getDouble("cantidad_movida")),
                        rs.getString("descripcion"),
                        String.format("%.2f", rs.getDouble("saldo_final")) };
                historial.add(movimiento);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return historial;
    }

    public static boolean agregarMovimiento(String usuario, double cantidad, String asunto) {
        String insertSQL = """
                    INSERT INTO historial_movimientos (fecha, hora, usuario, cantidad_movida, descripcion, saldo_final)
                    VALUES (?, ?, ?, ?, ?, ?);
                """;

        String saldoQuery = "SELECT saldo_final FROM historial_movimientos WHERE usuario = ? ORDER BY id DESC LIMIT 1";
        double saldoAnterior = 0.0;

        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            // Obtener el saldo anterior
            try (PreparedStatement saldoStmt = conn.prepareStatement(saldoQuery)) {
                saldoStmt.setString(1, usuario);
                ResultSet rs = saldoStmt.executeQuery();
                if (rs.next()) {
                    saldoAnterior = rs.getDouble("saldo_final");
                }
            }

            // Calcular el nuevo saldo
            double saldoFinal = saldoAnterior + cantidad;

            // Insertar el movimiento
            try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
                pstmt.setString(1, java.time.LocalDate.now().toString());
                pstmt.setString(2, java.time.LocalTime.now().toString());
                pstmt.setString(3, usuario);
                pstmt.setDouble(4, cantidad);
                pstmt.setString(5, asunto);
                pstmt.setDouble(6, saldoFinal);
                pstmt.executeUpdate();
                return true;
            }

        } catch (SQLException e) {
            return false;
        }
    }
}
