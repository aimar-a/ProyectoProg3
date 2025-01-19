//FUENTE-EXTERNA
// Código inspirado por el tutorial "Code Black Jack in Java" de [Kenny Yip Coding] en YouTube.
// URL: https://www.youtube.com/watch?v=GMdgjaDdOjI 
// ADAPTADO: Se ha modificado el código original para adaptarlo a las necesidades del proyecto y anadir funcionalidades adicionales.

package domain.blackjack;

import java.util.ArrayList;

public class Mano {
    private final ArrayList<Carta> cartas = new ArrayList<>();
    private int suma;
    private int cuentaAses;

    public Mano() {
        suma = 0;
        cuentaAses = 0;
    }

    public Mano(Mano copiar) {
        copiar.getCartas().forEach(carta -> cartas.add(new Carta(carta)));
        suma = copiar.getSuma();
        cuentaAses = copiar.getCuentaAses();
    }

    public void agregarCarta(Carta carta) {
        cartas.add(carta);
        suma += carta.getValor();
        if (carta.esAs()) {
            cuentaAses++;
        }
    }

    public void quitarCarta() {
        Carta carta = cartas.remove(cartas.size() - 1);
        suma -= carta.getValor();
        if (carta.esAs()) {
            cuentaAses--;
        }
    }

    public int getSuma() {
        while (suma > 21 && cuentaAses > 0) {
            suma -= 10;
            cuentaAses--;
        }
        return suma;
    }

    public int getCuentaAses() {
        return cuentaAses;
    }

    public ArrayList<Carta> getCartas() {
        return cartas;
    }

    public void limpiar() {
        cartas.clear();
        suma = 0;
        cuentaAses = 0;
    }

    public int size() {
        return cartas.size();
    }

    public Carta get(int i) {
        return cartas.get(i);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Carta carta : cartas) {
            sb.append(carta).append(" ");
        }
        return sb.toString();
    }
}
