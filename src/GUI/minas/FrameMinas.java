package GUI.minas;

import GUI.generalGames.MainFrame;
import GUI.mainMenu.FrameMenuPrincipal;
import java.awt.BorderLayout;

public class FrameMinas extends MainFrame {

    private static final long serialVersionUID = 1L;

    public FrameMinas(FrameMenuPrincipal frameMenuPrincipal) {
        super("Rasca y gana", frameMenuPrincipal);

        PanelMinas panelRasca = new PanelMinas();
        add(panelRasca, BorderLayout.CENTER);

        PanelApuestasMinas panelApuestasRasca = new PanelApuestasMinas();
        add(panelApuestasRasca, BorderLayout.SOUTH);
    }
}
