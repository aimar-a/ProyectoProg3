package GUI.Blackjack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Deck {
    private final ArrayList<Card> cards = new ArrayList<>();
    private final Random random = new Random();

    public Deck() {
        buildDeck();
        shuffleDeck();
    }

    private void buildDeck() {
        String[] values = { "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K" };
        String[] types = { "C", "D", "H", "S" };

        for (String type : types) {
            for (String value : values) {
                cards.add(new Card(value, type));
            }
        }
    }

    public void shuffleDeck() {
        Collections.shuffle(cards, random);
    }

    public Card drawCard() {
        return cards.isEmpty() ? null : cards.remove(cards.size() - 1);
    }

    public ArrayList<Card> getCards() {
        return cards;
    }
}
