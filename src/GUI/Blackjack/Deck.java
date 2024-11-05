package GUI.Blackjack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Deck {
    private ArrayList<Card> deck;
    private Random random = new Random();

    public Deck() {
        buildDeck();
        shuffleDeck();
    }

    public void buildDeck() {
        deck = new ArrayList<>();
        String[] values = { "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K" };
        String[] types = { "C", "D", "H", "S" };

        for (String type : types) {
            for (String value : values) {
                deck.add(new Card(value, type));
            }
        }
    }

    public void shuffleDeck() {
        Collections.shuffle(deck, random);
    }

    public Card drawCard() {
        return deck.remove(deck.size() - 1);
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }
}
