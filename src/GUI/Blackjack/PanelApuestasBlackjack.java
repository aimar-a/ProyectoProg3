package GUI.Blackjack;

import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PanelApuestasBlackjack extends JPanel {
    public JButton hitButton;
    public JButton stayButton;
    private boolean onGame;
    private PanelBlackjack panelBlackjack;
    private BlackJackGame game;

    public PanelApuestasBlackjack(PanelBlackjack panelBlackjack, BlackJackGame game) {
        this.panelBlackjack = panelBlackjack;
        this.game = game;
        onGame = false;
        setBackground(new Color(53, 101, 77));
        hitButton = new JButton("Hit");
        stayButton = new JButton("Stay");
        hitButton.addActionListener(this::onHit);
        stayButton.addActionListener(this::onStay);

        actualizarPanelApuestas();
    }

    private void actualizarPanelApuestas() {
        if (onGame) {
            remove(new JLabel("Apuesta: "));
            remove(new JTextField(10));
            remove(new JButton("Iniciar Partida"));
            add(hitButton);
            add(stayButton);
        } else {
            remove(hitButton);
            remove(stayButton);
            add(new JLabel("Apuesta: "));
            JTextField apuesta = new JTextField(10);
            add(apuesta);
            JButton iniciarPartida = new JButton("Iniciar Partida");
            iniciarPartida.addActionListener(e -> {
                game = new BlackJackGame();
                onGame = true;
                actualizarPanelApuestas();
                panelBlackjack.updateGameDisplay();
            });
            add(iniciarPartida);
        }
    }

    private void onHit(ActionEvent e) {
        game.playerHit();
        if (game.reducePlayerAce() > 21) {
            hitButton.setEnabled(false);
        }
        panelBlackjack.updateGameDisplay();
    }

    private void onStay(ActionEvent e) {
        hitButton.setEnabled(false);
        stayButton.setEnabled(false);
        game.dealerTurn();
        panelBlackjack.updateGameDisplay();
    }

}
