package BDtiradas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TiradasCaballos {
    private static Connection connection;

    // Establecer la conexión
    public static void setConnection(Connection connection) {
        TiradasCaballos.connection = connection;
    }

    // Insertar un registro en la tabla Caballos
    public static boolean agregarResultadoCarrera(String fecha, int idPartida, String caballoGanador) {
        String sql = """
                INSERT INTO Caballos (fecha, id_partida, caballo_ganador)
                VALUES (?, ?, ?)
                """;

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, fecha); // Fecha en formato "YYYY-MM-DD"
            pstmt.setInt(2, idPartida); // ID único de la partida
            pstmt.setString(3, caballoGanador); // Nombre o identificador del caballo ganador
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Obtener el historial de carreras
    public static List<String[]> obtenerHistorialCarreras() {
        String sql = "SELECT * FROM Caballos";
        List<String[]> historial = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                historial.add(new String[]{
                        rs.getString("fecha"),
                        String.valueOf(rs.getInt("id_partida")),
                        rs.getString("caballo_ganador")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return historial;
    }
}

