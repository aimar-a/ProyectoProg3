package gui.juegos.dinosaurio;

import gui.juegos.BaseGamesFrame;
import gui.mainMenu.FrameMenuPrincipal;
import java.awt.BorderLayout;
import javax.swing.SwingUtilities;

//IAG: GitHub Copilot
//ADAPTADO: Autocompeltado
public class FrameDino extends BaseGamesFrame {

    private static final long serialVersionUID = 1L;

	public FrameDino(FrameMenuPrincipal frameMenuPrincipal) {
        super("DinoRun", frameMenuPrincipal);

        SwingUtilities.invokeLater(() -> {
            PanelDino dinoPlay = new PanelDino();
            add(dinoPlay, BorderLayout.CENTER);

            PanelApuestasDino apuestasDino = new PanelApuestasDino(dinoPlay);
            add(apuestasDino, BorderLayout.SOUTH);
        });
    }
}
