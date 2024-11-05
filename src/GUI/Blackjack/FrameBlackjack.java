package GUI.Blackjack;

import GUI.generalGames.MainFrame;
import java.awt.*;

public class FrameBlackjack extends MainFrame {
    public BlackJackGame game;
    public final int cardWidth = 110;
    public final int cardHeight = 154;

    public PanelBlackjack panelBlackjack;
    public PanelApuestasBlackjack panelApuestas;

    public FrameBlackjack() {
        super("Black Jack");
        setLocationRelativeTo(null);

        panelBlackjack = new PanelBlackjack();
        add(panelBlackjack, BorderLayout.CENTER);

        panelApuestas = new PanelApuestasBlackjack(panelBlackjack, game);
        add(panelApuestas, BorderLayout.SOUTH);

        panelBlackjack.setFrameBlackjack(this);
    }

}
