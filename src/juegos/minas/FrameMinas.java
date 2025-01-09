package juegos.minas;

import java.awt.BorderLayout;

import gui.mainMenu.FrameMenuPrincipal;
import juegos.MainFrame;

public class FrameMinas extends MainFrame {

    private static final long serialVersionUID = 1L;

    public FrameMinas(FrameMenuPrincipal frameMenuPrincipal, String usuario, boolean darkMode) {
        super("Rasca y gana", frameMenuPrincipal, darkMode);

        PanelMinas panelRasca = new PanelMinas();
        add(panelRasca, BorderLayout.CENTER);

        PanelApuestasMinas panelApuestasRasca = new PanelApuestasMinas();
        add(panelApuestasRasca, BorderLayout.SOUTH);
    }
}
