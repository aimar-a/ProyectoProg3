// Código inspirado por el tutorial "Code Black Jack in Java" de [Kenny Yip Coding] en YouTube.
// Enlace: https://www.youtube.com/watch?v=GMdgjaDdOjI 
package GUI.blackjack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public final class Mazo {
    private final ArrayList<Carta> cartas = new ArrayList<>();
    private final Random aleatorio = new Random();

    public Mazo() {
        construirMazo();
        barajarMazo();
    }

    private void construirMazo() {
        String[] valores = { "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K" };
        String[] tipos = { "C", "D", "H", "S" };

        for (String tipo : tipos) {
            for (String valor : valores) {
                cartas.add(new Carta(valor, tipo));
            }
        }
    }

    public void barajarMazo() {
        Collections.shuffle(cartas, aleatorio);
    }

    public Carta robarCarta() {
        return cartas.isEmpty() ? null : cartas.remove(cartas.size() - 1);
    }

    public ArrayList<Carta> getCartas() {
        return cartas;
    }
}
