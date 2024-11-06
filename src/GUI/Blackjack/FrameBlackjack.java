package GUI.Blackjack;

import GUI.generalGames.MainFrame;
import GUI.mainMenu.FrameMenuPrincipal;
import java.awt.*;

public class FrameBlackjack extends MainFrame {
    private final PanelBlackjack panelBlackjack;
    private final PanelApuestasBlackjack panelApuestas;

    public FrameBlackjack(FrameMenuPrincipal menuPrinc) {
        super("Black Jack", menuPrinc);

        panelBlackjack = new PanelBlackjack();
        panelApuestas = new PanelApuestasBlackjack();
        new LogicaBlackjack(panelBlackjack, panelApuestas);

        add(panelBlackjack, BorderLayout.CENTER);
        add(panelApuestas, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            FrameBlackjack frame = new FrameBlackjack(null);
            frame.setVisible(true);
        });
    }
}
