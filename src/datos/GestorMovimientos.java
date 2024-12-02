package datos;

import java.sql.*;
import java.util.*;

public class GestorMovimientos {
    private static Connection connection;

    public static void setConnection(Connection connection) {
        GestorMovimientos.connection = connection;
    }

    public static List<String[]> obtenerHistorial(String usuario) {
        String sql = "SELECT Fecha, Hora, Usuario, Cantidad, Asunto, SaldoFinal FROM HistorialMovimientos WHERE Usuario = ?";
        List<String[]> historial = new ArrayList<>();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, usuario);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                historial.add(new String[] {
                        rs.getString("Fecha"), rs.getString("Hora"), rs.getString("Usuario"),
                        rs.getString("Cantidad"), rs.getString("Asunto"), rs.getString("SaldoFinal")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return historial;
    }

    public static boolean agregarMovimiento(String usuario, double cantidad, String asunto) {
        if (!cambiarSaldo(usuario, obtenerSaldo(usuario) + cantidad)) {
            return false;
        }
        String sql = """
                INSERT INTO HistorialMovimientos (Fecha, Hora, Usuario, Cantidad, Asunto, SaldoFinal)
                VALUES (date('now'), time('now'), ?, ?, ?, (SELECT Saldo FROM Cartera WHERE Usuario = ?))
                """;
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, usuario);
            pstmt.setDouble(2, cantidad);
            pstmt.setString(3, asunto);
            pstmt.setString(4, usuario);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Double obtenerSaldo(String usuario) {
        String sql = "SELECT Saldo FROM Cartera WHERE Usuario = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, usuario);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("Saldo");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static boolean cambiarSaldo(String usuario, double nuevoSaldo) {
        String sql = "UPDATE Cartera SET Saldo = ? WHERE Usuario = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setDouble(1, nuevoSaldo);
            pstmt.setString(2, usuario);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    protected static boolean bienvenidaUsuario(String usuario) {
        String sql = "INSERT INTO Cartera (Usuario, Saldo) VALUES (?, 0)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, usuario);
            pstmt.executeUpdate();
            Random random = new Random();
            agregarMovimiento(usuario, random.nextFloat(10), "bienvenida");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
