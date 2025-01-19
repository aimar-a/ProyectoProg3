//FUENTE-EXTERNA
// Código inspirado por el tutorial "Code Black Jack in Java" de [Kenny Yip Coding] en YouTube.
// URL: https://www.youtube.com/watch?v=GMdgjaDdOjI 
// ADAPTADO: Se ha modificado el código original para adaptarlo a las necesidades del proyecto y anadir funcionalidades adicionales.

package domain.blackjack;

import java.util.Objects;

//IAG: GitHub Copilot
//ADAPTADO: Autocompeltado
public class Carta {
    private final String valor;
    private final String palo;

    public Carta(String valor, String palo) {
        this.valor = valor;
        this.palo = palo;
    }

    public Carta(Carta copiar) {
        this.valor = copiar.valor;
        this.palo = copiar.palo;
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
        return "resources/img/juegos/blackjack/cartas/" + toString() + ".png";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Carta carta = (Carta) obj;
        return valor.equals(carta.valor) && palo.equals(carta.palo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor, palo);
    }
}
