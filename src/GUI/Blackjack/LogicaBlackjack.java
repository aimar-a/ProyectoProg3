package GUI.Blackjack;

import java.util.ArrayList;
import javax.swing.JOptionPane;

public class LogicaBlackjack {
    private int cuentaAsesCrupier, cuentaAsesJugador;
    private PanelApuestasBlackjack panelApuestas;
    private int cantidadApuesta;
    private Mazo mazo;
    private ArrayList<Carta> manoCrupier;
    private ArrayList<Carta> manoJugador;
    // esto he puesto yo
    private int sumaCrupier, sumaJugador;
    private Carta cartaOculta;
    private PanelBlackjack panelBlackjack;

    public LogicaBlackjack(PanelBlackjack panelBlackjack, PanelApuestasBlackjack panelApuestas) {
        this.panelBlackjack = panelBlackjack;
        this.panelApuestas = panelApuestas;

        panelApuestas.botonIniciar.addActionListener(e -> iniciarJuego());
        panelApuestas.botonPedir.addActionListener(e -> pedirCartaJugador());
        panelApuestas.botonPlantarse.addActionListener(e -> plantarseJugador());
    }

    public void iniciarJuego() {
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

        panelApuestas.botonIniciar.setEnabled(false);
        panelApuestas.botonPedir.setEnabled(true);
        panelApuestas.botonPlantarse.setEnabled(true);

        panelBlackjack.dibujar(cartaOculta, sumaCrupier, sumaJugador, manoCrupier, manoJugador,
                panelApuestas.botonPlantarse);

        if (sumaJugador == 21) {
            sumaJugador = -1;
            finalizarJuego();
        }
    }

    protected void pedirCartaJugador() {
        Carta carta = mazo.robarCarta();
        sumaJugador += carta.getValor();
        cuentaAsesJugador += carta.esAs() ? 1 : 0;
        manoJugador.add(carta);

        panelBlackjack.dibujar(cartaOculta, sumaCrupier, sumaJugador, manoCrupier, manoJugador,
                panelApuestas.botonPlantarse);
        if (ajustarSumaConAses(sumaJugador, cuentaAsesJugador) > 21) {
            finalizarJuego();
        }
    }

    protected void plantarseJugador() {
        while (sumaCrupier < 17) {
            agregarCartaCrupier();
        }
        panelBlackjack.dibujar(cartaOculta, sumaCrupier, sumaJugador, manoCrupier, manoJugador,
                panelApuestas.botonPlantarse);
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
        panelApuestas.botonPedir.setEnabled(false);
        panelApuestas.botonPlantarse.setEnabled(false);
        sumaCrupier = ajustarSumaConAses(sumaCrupier, cuentaAsesCrupier);
        sumaJugador = ajustarSumaConAses(sumaJugador, cuentaAsesJugador);

        String mensaje;
        if (sumaJugador == -1) {
            mensaje = "¡Blackjack! ¡Ganaste!\nCrupier: " + sumaCrupier + ", Jugador: " + sumaJugador;
        } else if (sumaJugador > 21) {
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

        JOptionPane.showMessageDialog(panelBlackjack, mensaje, "Fin del Juego", JOptionPane.INFORMATION_MESSAGE);
        panelApuestas.botonIniciar.setEnabled(true);
        if (panelApuestas.checkAutomatico.isSelected()) {
            iniciarJuego();
        }
    }
}
