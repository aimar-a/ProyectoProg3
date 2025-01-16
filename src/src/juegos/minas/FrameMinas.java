package juegos.minas;

import gui.mainMenu.FrameMenuPrincipal;
import java.awt.BorderLayout;
import juegos.MainFrame;

//IAG: Modificado (ChatGPT y GitHub Copilot)
public class FrameMinas extends MainFrame {
    private static final long serialVersionUID = 1L;

    public FrameMinas(FrameMenuPrincipal frameMenuPrincipal, String usuario, boolean darkMode) {
        super("Rasca y gana", frameMenuPrincipal, darkMode);
        PanelMinas panelRasca = new PanelMinas(usuario, darkMode);
        add(panelRasca, BorderLayout.CENTER);
        PanelApuestasMinas panelApuestasRasca = new PanelApuestasMinas(usuario, panelRasca, darkMode);
        add(panelApuestasRasca, BorderLayout.SOUTH);
    }
}