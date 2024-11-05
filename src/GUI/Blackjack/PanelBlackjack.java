package GUI.Blackjack;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class PanelBlackjack extends JPanel {
    FrameBlackjack frameBlackjack;

    public PanelBlackjack() {
        setLayout(new BorderLayout());
        setBackground(new Color(53, 101, 77));
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawHands(g);
    }

    private void drawHands(Graphics g) {
        // Dibujar la carta oculta del dealer
        Image hiddenCardImg = new ImageIcon(getClass().getResource("/img/Blackjack/BACK.png")).getImage();
        if (!frameBlackjack.panelApuestas.stayButton.isEnabled()) {
            // Revelar la carta oculta del dealer si el jugador ha hecho "Stay"
            hiddenCardImg = new ImageIcon(
                    getClass().getResource(frameBlackjack.game.getDealerHand().get(0).getImagePath()))
                    .getImage();
        }
        g.drawImage(hiddenCardImg, 20, 20, frameBlackjack.cardWidth, frameBlackjack.cardHeight, null);

        // Dibujar las cartas visibles del dealer
        for (int i = 1; i < frameBlackjack.game.getDealerHand().size(); i++) {
            Card card = frameBlackjack.game.getDealerHand().get(i);
            Image cardImg = new ImageIcon(getClass().getResource(card.getImagePath())).getImage();
            g.drawImage(cardImg, frameBlackjack.cardWidth + 25 + (frameBlackjack.cardWidth + 5) * (i - 1), 20,
                    frameBlackjack.cardWidth, frameBlackjack.cardHeight, null);
        }

        // Dibujar las cartas del jugador
        for (int i = 0; i < frameBlackjack.game.getPlayerHand().size(); i++) {
            Card card = frameBlackjack.game.getPlayerHand().get(i);
            Image cardImg = new ImageIcon(getClass().getResource(card.getImagePath())).getImage();
            g.drawImage(cardImg, 20 + (frameBlackjack.cardWidth + 5) * i, 320, frameBlackjack.cardWidth,
                    frameBlackjack.cardHeight, null);
        }

        // Mostrar el mensaje de fin de juego si el botón de "Stay" está deshabilitado
        if (!frameBlackjack.panelApuestas.stayButton.isEnabled()) {
            int dealerSum = frameBlackjack.game.reduceDealerAce();
            int playerSum = frameBlackjack.game.reducePlayerAce();

            String message = "";
            if (playerSum > 21) {
                message = "You Lose!";
            } else if (dealerSum > 21) {
                message = "You Win!";
            } else if (playerSum == dealerSum) {
                message = "Tie!";
            } else if (playerSum > dealerSum) {
                message = "You Win!";
            } else {
                message = "You Lose!";
            }

            // Configuración de texto y color para el mensaje
            g.setFont(new Font("Arial", Font.PLAIN, 30));
            g.setColor(Color.white);
            g.drawString(message, 220, 250);
        }
    }

    public void updateGameDisplay() {
        repaint();
    }

    public void setFrameBlackjack(FrameBlackjack frameBlackjack2) {
        this.frameBlackjack = frameBlackjack2;
    }
}
