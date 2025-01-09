// Código inspirado por el tutorial "Code Black Jack in Java" de [Kenny Yip Coding] en YouTube.
// URL: https://www.youtube.com/watch?v=GMdgjaDdOjI 
package juegos.blackjack;

import juegos.MainFrame;

import java.awt.*;

import gui.mainMenu.FrameMenuPrincipal;

public class FrameBlackjack extends MainFrame {
    private final PanelBlackjack panelBlackjack;
    private final PanelApuestasBlackjack panelApuestas;

    public FrameBlackjack(FrameMenuPrincipal menuPrinc, String usuario, boolean darkMode) {
        super("Black Jack", menuPrinc, darkMode);

        panelBlackjack = new PanelBlackjack(darkMode);
        panelApuestas = new PanelApuestasBlackjack(darkMode);
        new LogicaBlackjack(panelBlackjack, panelApuestas, usuario);

        add(panelBlackjack, BorderLayout.CENTER);
        add(panelApuestas, BorderLayout.SOUTH);
    }
}
