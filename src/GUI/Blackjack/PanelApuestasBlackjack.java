package GUI.Blackjack;

import java.awt.*;
import javax.swing.*;

public class PanelApuestasBlackjack extends JPanel {
    private final JButton startButton = new JButton("Start Game");
    private final JButton hitButton = new JButton("Hit");
    private final JButton stayButton = new JButton("Stand");

    public PanelApuestasBlackjack(PanelBlackjack panelBlackjack) {
        setLayout(new FlowLayout());

        hitButton.setEnabled(false);
        stayButton.setEnabled(false);

        add(startButton);
        add(hitButton);
        add(stayButton);

        startButton.addActionListener(e -> {
            panelBlackjack.initializeGame();
            panelBlackjack.startGame(hitButton, stayButton);
            startButton.setEnabled(false);
            hitButton.setEnabled(true);
            stayButton.setEnabled(true);
        });

        hitButton.addActionListener(e -> {
            if (panelBlackjack != null)
                panelBlackjack.repaint();
        });

        stayButton.addActionListener(e -> {
            hitButton.setEnabled(false);
            stayButton.setEnabled(false);
            startButton.setEnabled(true);
        });
    }
}
