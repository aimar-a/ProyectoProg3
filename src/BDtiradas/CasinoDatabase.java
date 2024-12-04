package BDtiradas;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CasinoDatabase {
    private static final String DB_URL = "jdbc:sqlite:casino.db";
	private static final CasinoDatabase DatabaseInitializer = null;

    public static void main(String[] args) {
        createDatabase();
        createTables();
    }

    // Método para crear la base de datos
    public static void createDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            if (conn != null) {
                System.out.println("Base de datos creada o conectada exitosamente.");
            }
        } catch (SQLException e) {
            System.out.println("Error al crear/conectar la base de datos: " + e.getMessage());
        }
    }

    // Método para crear las tablas
    public static void createTables() {
    	String createCaballosTable = """
                CREATE TABLE IF NOT EXISTS Caballos (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    fecha DATETIME NOT NULL,
                    numero_partida INT NOT NULL,
                    caballo_ganador VARCHAR(255) NOT NULL
                );
                """;

        String createSlotsTable = """
                CREATE TABLE IF NOT EXISTS Slots (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    fecha DATETIME NOT NULL,
                    numero_partida INT NOT NULL,
                    ganador BOOLEAN NOT NULL,
                    multiplicador FLOAT NOT NULL
                );
                """;

        String createDinoRunTable = """
                CREATE TABLE IF NOT EXISTS DinoRun (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    fecha DATETIME NOT NULL,
                    numero_partida INT NOT NULL,
                    ganador BOOLEAN NOT NULL,
                    tiempo_derrota FLOAT NOT NULL
                );
                """;

        try (Connection connection = DatabaseConfig.getConnection();
             Statement statement = connection.createStatement()) {

            statement.execute(createCaballosTable);
            statement.execute(createSlotsTable);
            statement.execute(createDinoRunTable);

            System.out.println("Tablas creadas exitosamente.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main1(String[] args) {
        // Crear tablas
        DatabaseInitializer.createTables();

        // Insertar datos de prueba
        GameDataInserter.insertCaballos("2024-12-04 14:30:00", 1, "Relámpago");
        GameDataInserter.insertSlots("2024-12-04 15:00:00", 1, true, 2.5f);
        GameDataInserter.insertDinoRun("2024-12-04 15:30:00", 1, false, 12.4f);
    }
}

