package gui.juegos.minas;

import gui.juegos.BaseGamesFrame;
import gui.mainMenu.FrameMenuPrincipal;
import java.awt.BorderLayout;

//IAG: Modificado (ChatGPT y GitHub Copilot)
public class FrameMinas extends BaseGamesFrame {
    private static final long serialVersionUID = 1L;

    public FrameMinas(FrameMenuPrincipal frameMenuPrincipal) {
        super("Rasca y gana", frameMenuPrincipal);
        PanelMinas panelRasca = new PanelMinas();
        add(panelRasca, BorderLayout.CENTER);
        PanelApuestasMinas panelApuestasRasca = new PanelApuestasMinas(panelRasca);
        add(panelApuestasRasca, BorderLayout.SOUTH);
    }
}