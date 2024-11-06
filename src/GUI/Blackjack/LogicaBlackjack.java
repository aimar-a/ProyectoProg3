package GUI.Blackjack;

import java.util.ArrayList;

import javax.swing.JOptionPane;

public class LogicaBlackjack {
    public LogicaBlackjack(PanelBlackjack panelBlackjack, PanelApuestasBlackjack panelApuestas) {
        panelApuestas.botonIniciar.addActionListener(e -> panelApuestas.iniciarJuego());
        panelApuestas.botonPedir.addActionListener(e -> panelBlackjack.pedirCartaJugador());
        panelApuestas.botonPlantarse.addActionListener(e -> panelBlackjack.plantarseJugador());
    }

    public void inicializarJuego() {
        mazo = new Mazo();
        manoCrupier = new ArrayList<>();
        manoJugador = new ArrayList<>();
        sumaCrupier = sumaJugador = cuentaAsesCrupier = cuentaAsesJugador = 0;

        cartaOculta = mazo.robarCarta();
        sumaCrupier += cartaOculta.getValor();
        cuentaAsesCrupier += cartaOculta.esAs() ? 1 : 0;

        agregarCartaCrupier();
        agregarCartaJugador();
        agregarCartaJugador();
    }

    protected void pedirCartaJugador() {
        Carta carta = mazo.robarCarta();
        sumaJugador += carta.getValor();
        cuentaAsesJugador += carta.esAs() ? 1 : 0;
        manoJugador.add(carta);

        repaint();
        if (ajustarSumaConAses(sumaJugador, cuentaAsesJugador) > 21) {
            finalizarJuego();
        }
    }

    protected void plantarseJugador() {
        while (sumaCrupier < 17) {
            agregarCartaCrupier();
        }
        repaint();
        finalizarJuego();
    }

    private void agregarCartaCrupier() {
        Carta carta = mazo.robarCarta();
        sumaCrupier += carta.getValor();
        cuentaAsesCrupier += carta.esAs() ? 1 : 0;
        manoCrupier.add(carta);
    }

    private void agregarCartaJugador() {
        Carta carta = mazo.robarCarta();
        sumaJugador += carta.getValor();
        cuentaAsesJugador += carta.esAs() ? 1 : 0;
        manoJugador.add(carta);
        sumaJugador = ajustarSumaConAses(sumaJugador, cuentaAsesJugador);
    }

    private int ajustarSumaConAses(int suma, int cuentaAses) {
        while (suma > 21 && cuentaAses > 0) {
            suma -= 10;
            cuentaAses--;
        }
        return suma;
    }

    private void finalizarJuego() {
        botonPedir.setEnabled(false);
        botonPlantarse.setEnabled(false);
        sumaCrupier = ajustarSumaConAses(sumaCrupier, cuentaAsesCrupier);
        sumaJugador = ajustarSumaConAses(sumaJugador, cuentaAsesJugador);

        String mensaje;
        if (sumaJugador > 21) {
            mensaje = "¡Perdiste! Te pasaste de 21.\nCrupier: " + sumaCrupier + ", Jugador: " + sumaJugador;
        } else if (sumaCrupier > 21) {
            mensaje = "¡Ganaste! El crupier se pasó de 21.\nCrupier: " + sumaCrupier + ", Jugador: " + sumaJugador;
        } else if (sumaJugador == sumaCrupier) {
            mensaje = "¡Empate!\nCrupier: " + sumaCrupier + ", Jugador: " + sumaJugador;
        } else if (sumaJugador > sumaCrupier) {
            mensaje = "¡Ganaste!\nCrupier: " + sumaCrupier + ", Jugador: " + sumaJugador;
        } else {
            mensaje = "¡Perdiste!\nCrupier: " + sumaCrupier + ", Jugador: " + sumaJugador;
        }

        JOptionPane.showMessageDialog(this, mensaje, "Fin del Juego", JOptionPane.INFORMATION_MESSAGE);
        botonIniciar.setEnabled(true);
    }
}
