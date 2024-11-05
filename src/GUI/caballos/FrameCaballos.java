package GUI.caballos;

import GUI.generalGames.MainFrame;
import GUI.mainMenu.FrameMenuPrincipal;
import java.awt.BorderLayout;

public class FrameCaballos extends MainFrame {

    public FrameCaballos(FrameMenuPrincipal frameMenuPrincipal) {
        super("Carreras de caballos", frameMenuPrincipal);

        PanelCaballos carreraCaballos = new PanelCaballos();
        add(carreraCaballos, BorderLayout.CENTER);

        PanelApuestasCaballos apuestasCaballos = new PanelApuestasCaballos(carreraCaballos);
        add(apuestasCaballos, BorderLayout.SOUTH);
    }
}
