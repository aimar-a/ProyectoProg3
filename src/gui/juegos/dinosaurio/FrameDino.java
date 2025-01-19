package gui.juegos.dinosaurio;

import gui.juegos.BaseGamesFrame;
import gui.mainMenu.FrameMenuPrincipal;
import java.awt.BorderLayout;

public class FrameDino extends BaseGamesFrame {

    public FrameDino(FrameMenuPrincipal frameMenuPrincipal) {
        super("DinoRun", frameMenuPrincipal);

        PanelDino dinoPlay = new PanelDino();
        add(dinoPlay, BorderLayout.CENTER);

        PanelApuestasDino apuestasDino = new PanelApuestasDino(dinoPlay);
        add(apuestasDino, BorderLayout.SOUTH);
    }
}
