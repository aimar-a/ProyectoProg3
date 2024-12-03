package GUI.dinoRun;

import GUI.generalGames.MainFrame;
import GUI.mainMenu.FrameMenuPrincipal;
import java.awt.BorderLayout;

public class FrameDino extends MainFrame {

    public FrameDino(FrameMenuPrincipal frameMenuPrincipal) {
        super("DinoRun", frameMenuPrincipal);

        PanelDino dinoPlay = new PanelDino();
        add(dinoPlay, BorderLayout.CENTER);

        PanelApuestasDino apuestasDino = new PanelApuestasDino(dinoPlay);
        add(apuestasDino, BorderLayout.SOUTH);
    }
}
