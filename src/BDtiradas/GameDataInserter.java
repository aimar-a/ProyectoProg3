package BDtiradas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GameDataInserter {
    public static void insertCaballos(String fecha, int numeroPartida, String caballoGanador) {
        String query = "INSERT INTO Caballos (fecha, numero_partida, caballo_ganador) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, fecha);
            ps.setInt(2, numeroPartida);
            ps.setString(3, caballoGanador);

            ps.executeUpdate();
            System.out.println("Datos de Caballos insertados exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertSlots(String fecha, int numeroPartida, boolean ganador, float multiplicador) {
        String query = "INSERT INTO Slots (fecha, numero_partida, ganador, multiplicador) VALUES (?, ?, ?, ?)";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, fecha);
            ps.setInt(2, numeroPartida);
            ps.setBoolean(3, ganador);
            ps.setFloat(4, multiplicador);

            ps.executeUpdate();
            System.out.println("Datos de Slots insertados exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertDinoRun(String fecha, int numeroPartida, boolean ganador, float tiempoDerrota) {
        String query = "INSERT INTO DinoRun (fecha, numero_partida, ganador, tiempo_derrota) VALUES (?, ?, ?, ?)";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, fecha);
            ps.setInt(2, numeroPartida);
            ps.setBoolean(3, ganador);
            ps.setFloat(4, tiempoDerrota);

            ps.executeUpdate();
            System.out.println("Datos de DinoRun insertados exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
