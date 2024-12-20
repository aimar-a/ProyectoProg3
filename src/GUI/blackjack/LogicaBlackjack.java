// Código inspirado por el tutorial "Code Black Jack in Java" de [Kenny Yip Coding] en YouTube.
// URL: https://www.youtube.com/watch?v=GMdgjaDdOjI 
package GUI.blackjack;

import datos.AsuntoMovimiento;
import datos.GestorBD;
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

    private int apuesta;
    private String usuario;

    public LogicaBlackjack(PanelBlackjack panelBlackjack, PanelApuestasBlackjack panelApuestas, String usuario) {
        this.panelBlackjack = panelBlackjack;
        this.panelApuestas = panelApuestas;
        this.usuario = usuario;

        panelApuestas.botonIniciar.addActionListener(e -> iniciarJuego());
        panelApuestas.botonPedir.addActionListener(e -> agregarCartaJugador());
        panelApuestas.botonPlantarse.addActionListener(e -> plantarseJugador());

    }

    public void iniciarJuego() {
        this.apuesta = panelApuestas.getCantidadApuesta();
        if (GestorBD.obtenerSaldo(usuario) < this.apuesta) {
            JOptionPane.showMessageDialog(panelBlackjack, "No tienes suficientes fichas para realizar esta apuesta.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
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

        GestorBD.agregarMovimiento(usuario, -this.apuesta, AsuntoMovimiento.BLACKJACK_APUESTA);

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
        // Desactivar los botones de acción del jugador
        panelApuestas.botonPedir.setEnabled(false);
        panelApuestas.botonPlantarse.setEnabled(false);

        // Mostrar la carta oculta del crupier
        panelBlackjack.dibujar(cartaOculta, sumaCrupier, sumaJugador, manoCrupier, manoJugador,
                panelApuestas.botonPlantarse);

        // Crear un Timer para agregar cartas con un intervalo de 1 segundo
        new Thread(() -> {
            try {
                while (ajustarSumaConAses(sumaCrupier, cuentaAsesCrupier) < 17
                        || (sumaCrupier - (cuentaAsesCrupier - 1) * 11 == 17 && cuentaAsesCrupier > 0)) {
                    Thread.sleep(1000);
                    agregarCartaCrupier();
                    panelBlackjack.dibujar(cartaOculta, ajustarSumaConAses(sumaCrupier,
                            cuentaAsesCrupier), sumaJugador, manoCrupier, manoJugador,
                            panelApuestas.botonPlantarse);
                }
                finalizarJuego();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
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
        if (mensaje.contains("Ganaste")) {
            GestorBD.agregarMovimiento(usuario, this.apuesta * 2, AsuntoMovimiento.BLACKJACK_PREMIO);
            mensaje += "\nHas ganado " + this.apuesta + " fichas";
        } else if (mensaje.contains("Empate")) {
            GestorBD.agregarMovimiento(usuario, this.apuesta, AsuntoMovimiento.BLACKJACK_EMPATE);
            mensaje += "\nHas recuperado tu apuesta de " + this.apuesta + " fichas";
        } else {
            mensaje += "\nHas perdido " + this.apuesta + " fichas";
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

        panelBlackjack.dibujar(cartaOculta, sumaCrupier, ajustarSumaConAses(sumaJugador, cuentaAsesJugador),
                manoCrupier, manoJugador,
                panelApuestas.botonPlantarse);
        if (ajustarSumaConAses(sumaJugador, cuentaAsesJugador) > 21) {
            finalizarJuego();
        }
    }
}
