package GUI.Blackjack;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class PanelBlackjack extends JPanel {
    private Deck deck;
    private ArrayList<Card> dealerHand;
    private ArrayList<Card> playerHand;
    private Card hiddenCard;
    private int dealerSum, playerSum;
    private int dealerAceCount, playerAceCount;
    private JButton hitButton, stayButton;
    private final int cardWidth = 110;
    private final int cardHeight = 154;

    public PanelBlackjack() {
        setBackground(new Color(53, 101, 77));
        setLayout(new BorderLayout());
        initializeGame();
    }

    public void initializeGame() {
        deck = new Deck();
        dealerHand = new ArrayList<>();
        playerHand = new ArrayList<>();
        dealerSum = playerSum = dealerAceCount = playerAceCount = 0;
        hiddenCard = deck.drawCard();
        dealerSum += hiddenCard.getValue();
        dealerAceCount += hiddenCard.isAce() ? 1 : 0;
        addDealerCard();
        addPlayerCard();
        addPlayerCard();
    }

    public void startGame(JButton hitButton, JButton stayButton) {
        this.hitButton = hitButton;
        this.stayButton = stayButton;
        hitButton.addActionListener(e -> playerHit());
        stayButton.addActionListener(e -> playerStay());
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawCards(g);
    }

    private void drawCards(Graphics g) {
        try {
            Image hiddenCardImg = new ImageIcon(getClass().getResource("/img/Blackjack/BACK.png")).getImage();
            if (!stayButton.isEnabled()) {
                hiddenCardImg = new ImageIcon(getClass().getResource(hiddenCard.getImagePath())).getImage();
            }
            g.drawImage(hiddenCardImg, 20, 20, cardWidth, cardHeight, null);

            for (int i = 0; i < dealerHand.size(); i++) {
                Image cardImg = new ImageIcon(getClass().getResource(dealerHand.get(i).getImagePath())).getImage();
                g.drawImage(cardImg, cardWidth + 25 + (cardWidth + 5) * i, 20, cardWidth, cardHeight, null);
            }

            for (int i = 0; i < playerHand.size(); i++) {
                Image cardImg = new ImageIcon(getClass().getResource(playerHand.get(i).getImagePath())).getImage();
                g.drawImage(cardImg, 20 + (cardWidth + 5) * i, 320, cardWidth, cardHeight, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void playerHit() {
        Card card = deck.drawCard();
        playerSum += card.getValue();
        playerAceCount += card.isAce() ? 1 : 0;
        playerHand.add(card);
        if (reduceAceCount(playerSum, playerAceCount) > 21) {
            hitButton.setEnabled(false);
        }
        repaint();
    }

    private void playerStay() {
        hitButton.setEnabled(false);
        stayButton.setEnabled(false);
        while (dealerSum < 17) {
            addDealerCard();
        }
        repaint();
    }

    private void addDealerCard() {
        Card card = deck.drawCard();
        dealerSum += card.getValue();
        dealerAceCount += card.isAce() ? 1 : 0;
        dealerHand.add(card);
    }

    private void addPlayerCard() {
        Card card = deck.drawCard();
        playerSum += card.getValue();
        playerAceCount += card.isAce() ? 1 : 0;
        playerHand.add(card);
    }

    private int reduceAceCount(int sum, int aceCount) {
        while (sum > 21 && aceCount > 0) {
            sum -= 10;
            aceCount--;
        }
        return sum;
    }
}
