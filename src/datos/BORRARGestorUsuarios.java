package datos;

import java.sql.*;

public class BORRARGestorUsuarios {
    private static Connection connection;

    public static void setConnection(Connection connection) {
        GestorUsuarios.connection = connection;
    }

    public static String obtenerContrasena(String usuario) {
        String sql = "SELECT Contrasena FROM UsuarioContra WHERE Usuario = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, usuario);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("Contrasena");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean cambiarContrasena(String usuario, String nuevaContrasena) {
        String sql = "UPDATE UsuarioContra SET Contrasena = ? WHERE Usuario = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, nuevaContrasena);
            pstmt.setString(2, usuario);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String[] obtenerDatos(String usuario) {
        String sql = "SELECT * FROM UsuarioDatos WHERE Usuario = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, usuario);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new String[] {
                        rs.getString("Nombre"), rs.getString("Apellidos"), rs.getString("DNI"),
                        rs.getString("Mail"), rs.getString("Prefijo"), rs.getString("Telefono"),
                        rs.getString("Provincia"), rs.getString("Ciudad"), rs.getString("Calle"),
                        rs.getString("NumeroCalle"), rs.getString("FechaNacimiento"), rs.getString("FechaInscripcion")
                };
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean cambiarDatos(String usuario, String[] nuevosDatos) {
        String sql = """
                UPDATE UsuarioDatos
                SET Nombre = ?, Apellidos = ?, DNI = ?, Mail = ?, Prefijo = ?, Telefono = ?,
                    Provincia = ?, Ciudad = ?, Calle = ?, NumeroCalle = ?, FechaNacimiento = ?
                WHERE Usuario = ?
                """;
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            for (int i = 0; i < nuevosDatos.length; i++) {
                pstmt.setString(i + 1, nuevosDatos[i]);
            }
            pstmt.setString(12, usuario);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean agregarUsuario(String usuario, String contrasena, String[] datos) {
        String usuarioContraSQL = "INSERT INTO UsuarioContra (Usuario, Contrasena) VALUES (?, ?)";
        String usuarioDatosSQL = """
                INSERT INTO UsuarioDatos (Usuario, Nombre, Apellidos, DNI, Mail, Prefijo, Telefono,
                                          Provincia, Ciudad, Calle, NumeroCalle, FechaNacimiento, FechaInscripcion)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;
        try (PreparedStatement pstmtContra = connection.prepareStatement(usuarioContraSQL);
                PreparedStatement pstmtDatos = connection.prepareStatement(usuarioDatosSQL)) {

            // Insertar en UsuarioContra
            pstmtContra.setString(1, usuario);
            pstmtContra.setString(2, contrasena);
            pstmtContra.executeUpdate();

            // Insertar en UsuarioDatos
            pstmtDatos.setString(1, usuario);
            for (int i = 0; i < datos.length; i++) {
                pstmtDatos.setString(i + 2, datos[i]);
            }
            GestorMovimientos.bienvenidaUsuario(usuario);
            pstmtDatos.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean usuarioExiste(String usuario) {
        String sql = "SELECT 1 FROM UsuarioContra WHERE Usuario = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, usuario);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
