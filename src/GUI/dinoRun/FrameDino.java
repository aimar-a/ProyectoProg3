package GUI.dinoRun;

import GUI.generalGames.MainFrame;
import GUI.mainMenu.FrameMenuPrincipal;
import java.awt.BorderLayout;

public class FrameDino extends MainFrame {

    public FrameDino(FrameMenuPrincipal frameMenuPrincipal, String usuario, boolean darkMode) {
        super("DinoRun", frameMenuPrincipal, darkMode);

        PanelDino dinoPlay = new PanelDino(darkMode);
        add(dinoPlay, BorderLayout.CENTER);

        PanelApuestasDino apuestasDino = new PanelApuestasDino(usuario, dinoPlay, darkMode);
        add(apuestasDino, BorderLayout.SOUTH);
    }
}
