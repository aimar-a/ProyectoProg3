import datos.GestorBD;
import gui.mainMenu.FrameMenuPrincipal;

public class Main {
    public static void main(String[] args) {
        GestorBD.crearBD();
        FrameMenuPrincipal menup = new FrameMenuPrincipal();
        menup.setVisible(true);
    }
}
