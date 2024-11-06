package GUI.Blackjack;

public class Carta {
    private final String valor;
    private final String palo;

    public Carta(String valor, String palo) {
        this.valor = valor;
        this.palo = palo;
    }

    @Override
    public String toString() {
        return valor + "-" + palo;
    }

    public int getValor() {
        if ("AJQK".contains(valor)) {
            return "A".equals(valor) ? 11 : 10;
        }
        return Integer.parseInt(valor);
    }

    public boolean esAs() {
        return "A".equals(valor);
    }

    public String getRutaImagen() {
        return "/img/blackjack/" + toString() + ".png";
    }
}
