import GUI.mainMenu.FrameMenuPrincipal;
import datos.CrearBD;

public class Main {
    public static void main(String[] args) {
        new CrearBD();
        FrameMenuPrincipal menup = new FrameMenuPrincipal();
        menup.setVisible(true);
    }
}

