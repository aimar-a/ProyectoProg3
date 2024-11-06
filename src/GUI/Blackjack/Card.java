package GUI.Blackjack;

public class Card {
    private final String value;
    private final String type;

    public Card(String value, String type) {
        this.value = value;
        this.type = type;
    }

    @Override
    public String toString() {
        return value + "-" + type;
    }

    public int getValue() {
        if ("AJQK".contains(value)) {
            return "A".equals(value) ? 11 : 10;
        }
        return Integer.parseInt(value);
    }

    public boolean isAce() {
        return "A".equals(value);
    }

    public String getImagePath() {
        return "/img/blackjack/" + toString() + ".png";
    }
}
