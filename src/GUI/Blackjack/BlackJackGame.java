package GUI.Blackjack;

import java.util.ArrayList;

public class BlackJackGame {
    private Deck deck;
    private ArrayList<Card> dealerHand;
    private int dealerSum;
    private int dealerAceCount;

    private ArrayList<Card> playerHand;
    private int playerSum;
    private int playerAceCount;

    public BlackJackGame() {
        startGame();
    }

    public void startGame() {
        deck = new Deck();

        // Dealer setup
        dealerHand = new ArrayList<>();
        dealerSum = 0;
        dealerAceCount = 0;

        Card hiddenCard = deck.drawCard();
        dealerSum += hiddenCard.getValue();
        dealerAceCount += hiddenCard.isAce() ? 1 : 0;

        Card visibleCard = deck.drawCard();
        dealerSum += visibleCard.getValue();
        dealerAceCount += visibleCard.isAce() ? 1 : 0;
        dealerHand.add(visibleCard);

        // Player setup
        playerHand = new ArrayList<>();
        playerSum = 0;
        playerAceCount = 0;
        for (int i = 0; i < 2; i++) {
            Card card = deck.drawCard();
            playerSum += card.getValue();
            playerAceCount += card.isAce() ? 1 : 0;
            playerHand.add(card);
        }
    }

    public int reducePlayerAce() {
        while (playerSum > 21 && playerAceCount > 0) {
            playerSum -= 10;
            playerAceCount--;
        }
        return playerSum;
    }

    public int reduceDealerAce() {
        while (dealerSum > 21 && dealerAceCount > 0) {
            dealerSum -= 10;
            dealerAceCount--;
        }
        return dealerSum;
    }

    // Getters for game state
    public ArrayList<Card> getDealerHand() {
        return dealerHand;
    }

    public ArrayList<Card> getPlayerHand() {
        return playerHand;
    }

    public int getPlayerSum() {
        return playerSum;
    }

    public int getDealerSum() {
        return dealerSum;
    }

    // Methods to handle actions (hit, stay)
    public Card playerHit() {
        Card card = deck.drawCard();
        playerSum += card.getValue();
        playerAceCount += card.isAce() ? 1 : 0;
        playerHand.add(card);
        return card;
    }

    public void dealerTurn() {
        while (dealerSum < 17) {
            Card card = deck.drawCard();
            dealerSum += card.getValue();
            dealerAceCount += card.isAce() ? 1 : 0;
            dealerHand.add(card);
        }
    }
}
