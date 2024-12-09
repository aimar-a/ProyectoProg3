import GUI.mainMenu.FrameMenuPrincipal;
import datos.GestorBD;

public class Main {
    public static void main(String[] args) {
        GestorBD.crearBD();
        FrameMenuPrincipal menup = new FrameMenuPrincipal();
        menup.setVisible(true);
    }
}
