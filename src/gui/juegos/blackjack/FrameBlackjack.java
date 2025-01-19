//FUENTE-EXTERNA
// Código inspirado por el tutorial "Code Black Jack in Java" de [Kenny Yip Coding] en YouTube.
// URL: https://www.youtube.com/watch?v=GMdgjaDdOjI 
// ADAPTADO: Se ha modificado el código original para adaptarlo a las necesidades del proyecto y anadir funcionalidades adicionales.

package gui.juegos.blackjack;

import gui.juegos.BaseGamesFrame;
import gui.mainMenu.FrameMenuPrincipal;
import java.awt.BorderLayout;
import javax.swing.SwingUtilities;

//IAG: GitHub Copilot
//ADAPTADO: Autocompeltado
public class FrameBlackjack extends BaseGamesFrame {
    private final PanelBlackjack panelBlackjack;
    private final PanelApuestasBlackjack panelApuestas;

    public FrameBlackjack(FrameMenuPrincipal menuPrinc) {
        super("Black Jack", menuPrinc);

        panelBlackjack = new PanelBlackjack();
        panelApuestas = new PanelApuestasBlackjack();
        new LogicaBlackjack(panelBlackjack, panelApuestas);

        SwingUtilities.invokeLater(() -> {
            add(panelBlackjack, BorderLayout.CENTER);
            add(panelApuestas, BorderLayout.SOUTH);
        });
    }
}
