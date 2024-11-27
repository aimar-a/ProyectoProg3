// Código inspirado por el tutorial "Code Black Jack in Java" de [Kenny Yip Coding] en YouTube.
// URL: https://www.youtube.com/watch?v=GMdgjaDdOjI 
package GUI.blackjack;

import java.util.ArrayList;
import javax.swing.JOptionPane;

public class LogicaBlackjack {
    private int cuentaAsesCrupier, cuentaAsesJugador;
    private final PanelApuestasBlackjack panelApuestas;
    private Mazo mazo;
    private ArrayList<Carta> manoCrupier;
    private ArrayList<Carta> manoJugador;
    private int sumaCrupier, sumaJugador;
    private Carta cartaOculta;
    private final PanelBlackjack panelBlackjack;

    public LogicaBlackjack(PanelBlackjack panelBlackjack, PanelApuestasBlackjack panelApuestas) {
        this.panelBlackjack = panelBlackjack;
        this.panelApuestas = panelApuestas;

        panelApuestas.botonIniciar.addActionListener(e -> iniciarJuego());
        panelApuestas.botonPedir.addActionListener(e -> agregarCartaJugador());
        panelApuestas.botonPlantarse.addActionListener(e -> plantarseJugador());
    }

    public void iniciarJuego() {
        // Reiniciar valores y configurar el juego
        mazo = new Mazo();
        manoCrupier = new ArrayList<>();
        manoJugador = new ArrayList<>();
        sumaCrupier = sumaJugador = cuentaAsesCrupier = cuentaAsesJugador = 0;

        // Robar la carta oculta del crupier
        cartaOculta = mazo.robarCarta();
        sumaCrupier += cartaOculta.getValor();
        cuentaAsesCrupier += cartaOculta.esAs() ? 1 : 0;

        agregarCartaCrupier();
        agregarCartaJugador();
        agregarCartaJugador();

        // Desactivar el botón "Iniciar" y el spinner de apuestas
        panelApuestas.botonIniciar.setEnabled(false);
        panelApuestas.spinnerApuesta.setEnabled(false); // Desactivar el spinner
        panelApuestas.botonPedir.setEnabled(true);
        panelApuestas.botonPlantarse.setEnabled(true);

        // Dibujar estado inicial
        panelBlackjack.dibujar(cartaOculta, sumaCrupier, sumaJugador, manoCrupier, manoJugador,
                panelApuestas.botonPlantarse);

        // Comprobar Blackjack inicial
        if (sumaJugador == 21) {
            sumaJugador = -1;
            if (sumaCrupier == 21) {
                sumaCrupier = -1;
            }
            finalizarJuego();
        } else if (sumaCrupier == 21) {
            sumaCrupier = -1;
            finalizarJuego();
        }
    }

    protected void plantarseJugador() {
        // Crupier juega según sus reglas
        while (ajustarSumaConAses(sumaCrupier, cuentaAsesCrupier) < 17
                || (sumaCrupier - (cuentaAsesCrupier - 1) * 11 == 17 && cuentaAsesCrupier > 0)) {
            agregarCartaCrupier();
        }

        panelBlackjack.dibujar(cartaOculta, sumaCrupier, sumaJugador, manoCrupier, manoJugador,
                panelApuestas.botonPlantarse);
        finalizarJuego();
    }

    private void finalizarJuego() {
        // Desactivar los botones de acción del jugador
        panelApuestas.botonPedir.setEnabled(false);
        panelApuestas.botonPlantarse.setEnabled(false);

        // Ajustar valores finales considerando los ases
        sumaCrupier = ajustarSumaConAses(sumaCrupier, cuentaAsesCrupier);
        sumaJugador = ajustarSumaConAses(sumaJugador, cuentaAsesJugador);

        // Determinar el resultado
        String mensaje;
        if (sumaCrupier == -1 && sumaJugador == -1) {
            mensaje = "¡Empate! Ambos tienen Blackjack.";
        } else if (sumaCrupier == -1) {
            mensaje = "¡Blackjack! ¡Perdiste!";
        } else if (sumaJugador == -1) {
            mensaje = "¡Blackjack! ¡Ganaste!";
        } else if (sumaJugador > 21) {
            mensaje = "¡Perdiste! Te pasaste de 21.";
        } else if (sumaCrupier > 21) {
            mensaje = "¡Ganaste! El crupier se pasó de 21.";
        } else if (sumaJugador == sumaCrupier) {
            mensaje = "¡Empate!";
        } else if (sumaJugador > sumaCrupier) {
            mensaje = "¡Ganaste!";
        } else {
            mensaje = "¡Perdiste!";
        }

        // Mostrar mensaje del resultado
        JOptionPane.showMessageDialog(panelBlackjack, mensaje, "Fin del Juego", JOptionPane.INFORMATION_MESSAGE);

        // Reactivar el botón "Iniciar" y el spinner de apuestas
        panelApuestas.botonIniciar.setEnabled(true);
        panelApuestas.spinnerApuesta.setEnabled(true); // Reactivar el spinner

        // Reiniciar automáticamente si está activada la opción automática
        if (panelApuestas.checkAutomatico.isSelected()) {
            iniciarJuego();
        }
    }

    private int ajustarSumaConAses(int suma, int cuentaAses) {
        while (suma > 21 && cuentaAses > 0) {
            suma -= 10;
            cuentaAses--;
        }
        return suma;
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

        panelBlackjack.dibujar(cartaOculta, sumaCrupier, sumaJugador, manoCrupier, manoJugador,
                panelApuestas.botonPlantarse);
        if (ajustarSumaConAses(sumaJugador, cuentaAsesJugador) > 21) {
            finalizarJuego();
        }
    }
}
