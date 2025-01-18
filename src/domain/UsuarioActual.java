package domain;

public class UsuarioActual {
    private UsuarioActual() {
        // Private constructor to prevent instantiation
    }

    private static String currentUser = null;

    public static String getUsuarioActual() {
        return currentUser;
    }

    public static void setUsuarioActual(String usuarioActual) {
        UsuarioActual.currentUser = usuarioActual;
    }

    public static boolean isLogged() {
        return currentUser != null;
    }

    public static void logout() {
        currentUser = null;
    }
}
