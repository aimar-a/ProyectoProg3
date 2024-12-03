package GUI.caballos;

import GUI.generalGames.MainFrame;
import GUI.mainMenu.FrameMenuPrincipal;
import java.awt.BorderLayout;

public class FrameCaballos extends MainFrame {

    public FrameCaballos(FrameMenuPrincipal frameMenuPrincipal, String usuario, boolean darkMode) {
        super("Carreras de caballos", frameMenuPrincipal, darkMode);

        PanelCaballos carreraCaballos = new PanelCaballos();
        add(carreraCaballos, BorderLayout.CENTER);

        PanelApuestasCaballos apuestasCaballos = new PanelApuestasCaballos(usuario);
        add(apuestasCaballos, BorderLayout.SOUTH);

        carreraCaballos.setPanelApuestasCaballos(apuestasCaballos);
        apuestasCaballos.setPanelCaballos(carreraCaballos);
    }
}
