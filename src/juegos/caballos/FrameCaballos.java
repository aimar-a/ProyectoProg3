package juegos.caballos;

import juegos.MainFrame;

import java.awt.BorderLayout;

import gui.mainMenu.FrameMenuPrincipal;

//IAG: Modificado (ChatGPT y GitHub Copilot)
public class FrameCaballos extends MainFrame {

    public FrameCaballos(FrameMenuPrincipal frameMenuPrincipal, String usuario, boolean darkMode) {
        super("Carreras de caballos", frameMenuPrincipal, darkMode);

        PanelCaballos carreraCaballos = new PanelCaballos();
        add(carreraCaballos, BorderLayout.CENTER);

        PanelApuestasCaballos apuestasCaballos = new PanelApuestasCaballos(usuario, darkMode);
        add(apuestasCaballos, BorderLayout.SOUTH);

        carreraCaballos.setPanelApuestasCaballos(apuestasCaballos);
        apuestasCaballos.setPanelCaballos(carreraCaballos);
    }
}
