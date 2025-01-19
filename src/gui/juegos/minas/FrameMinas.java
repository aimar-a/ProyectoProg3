package gui.juegos.minas;

import gui.juegos.BaseGamesFrame;
import gui.mainMenu.FrameMenuPrincipal;
import java.awt.BorderLayout;
import javax.swing.SwingUtilities;

//IAG: GitHub Copilot
//ADAPTADO: Autocompeltado
public class FrameMinas extends BaseGamesFrame {
    private static final long serialVersionUID = 1L;

    public FrameMinas(FrameMenuPrincipal frameMenuPrincipal) {
        super("Rasca y gana", frameMenuPrincipal);
        SwingUtilities.invokeLater(() -> {
            PanelMinas panelRasca = new PanelMinas();
            add(panelRasca, BorderLayout.CENTER);
            PanelApuestasMinas panelApuestasRasca = new PanelApuestasMinas(panelRasca);
            add(panelApuestasRasca, BorderLayout.SOUTH);
        });
    }
}