//FUENTE-EXTERNA
// Código inspirado por el tutorial "Code Black Jack in Java" de [Kenny Yip Coding] en YouTube.
// URL: https://www.youtube.com/watch?v=GMdgjaDdOjI 
// ADAPTADO: Se ha modificado el código original para adaptarlo a las necesidades del proyecto y anadir funcionalidades adicionales.

package gui.juegos.blackjack;

import db.GestorBD;
import domain.UsuarioActual;
import domain.blackjack.Carta;
import domain.blackjack.Mano;
import domain.blackjack.Mazo;
import domain.datos.AsuntoMovimiento;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

//IAG: ChatGPT y GitHub Copilot
//ADAPTADO: Anadir funcionalidades y autocompeltado
public class LogicaBlackjack {
    private final PanelApuestasBlackjack panelApuestas;
    private Mazo mazo;
    private Mano manoCrupier;
    private Mano manoJugador;
    private Carta cartaOculta;
    private final PanelBlackjack panelBlackjack;

    private int apuesta;
    private final String usuario;

    public LogicaBlackjack(PanelBlackjack panelBlackjack, PanelApuestasBlackjack panelApuestas) {
        this.panelBlackjack = panelBlackjack;
        this.panelApuestas = panelApuestas;
        this.usuario = UsuarioActual.getUsuarioActual();

        panelApuestas.botonIniciar.addActionListener(e -> iniciarJuego());
        panelApuestas.botonPedir.addActionListener(e -> {
            panelBlackjack.romperCalculoProbabilidades();
            agregarCartaJugador();
        });
        panelApuestas.botonPlantarse.addActionListener(e -> {
            panelBlackjack.romperCalculoProbabilidades();
            plantarseJugador();
        });

    }

    public void iniciarJuego() {
        if (panelApuestas.getCantidadApuesta() == 0) {
            JOptionPane.showMessageDialog(panelBlackjack, "Debes apostar al menos 1 ficha.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        this.apuesta = panelApuestas.getCantidadApuesta();
        if (GestorBD.obtenerSaldo(usuario) < this.apuesta) {
            JOptionPane.showMessageDialog(panelBlackjack, "No tienes suficientes fichas para realizar esta apuesta.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Reiniciar valores y configurar el juego
        mazo = new Mazo();
        manoCrupier = new Mano();
        manoJugador = new Mano();

        // Robar la carta oculta del crupier
        cartaOculta = mazo.robarCarta();

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
        SwingUtilities
                .invokeLater(() -> panelBlackjack.dibujar(manoCrupier, manoJugador, panelApuestas.botonPlantarse));

        if (manoJugador.getSuma() == 21 && manoCrupier.getSuma() + cartaOculta.getValor() == 21) {
            finalizarJuego(3);
        } else if (manoJugador.getSuma() == 21) {
            finalizarJuego(1);
        } else if (manoCrupier.getSuma() + cartaOculta.getValor() == 21) {
            finalizarJuego(2);
        }
    }

    protected void plantarseJugador() {
        // Desactivar los botones de acción del jugador
        panelApuestas.botonPedir.setEnabled(false);
        panelApuestas.botonPlantarse.setEnabled(false);

        // Mostrar la carta oculta del crupier
        manoCrupier.agregarCarta(cartaOculta);
        SwingUtilities
                .invokeLater(() -> panelBlackjack.dibujar(manoCrupier, manoJugador, panelApuestas.botonPlantarse));

        // Crear un Timer para agregar cartas con un intervalo de 1 segundo
        new Thread(() -> {
            try {
                while (manoCrupier.getSuma() < 17
                        || (manoCrupier.getSuma() == 17 && manoCrupier.getCuentaAses() > 0)) {
                    Thread.sleep(1000);
                    agregarCartaCrupier();
                    SwingUtilities.invokeLater(
                            () -> panelBlackjack.dibujar(manoCrupier, manoJugador, panelApuestas.botonPlantarse));
                }
                finalizarJuego();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    private void finalizarJuego() {
        finalizarJuego(0);
    }

    private void finalizarJuego(int resultado) {
        // Desactivar los botones de acción del jugador
        panelApuestas.botonPedir.setEnabled(false);
        panelApuestas.botonPlantarse.setEnabled(false);

        String mensaje;

        if (resultado == 1 || resultado == 2 || resultado == 3) {
            // Mostrar la carta oculta del crupier
            manoCrupier.agregarCarta(cartaOculta);
            SwingUtilities
                    .invokeLater(() -> panelBlackjack.dibujar(manoCrupier, manoJugador, panelApuestas.botonPlantarse));
        }

        if (resultado == 3) {
            mensaje = "¡Empate! Ambos tienen Blackjack.";
        } else if (resultado == 2) {
            mensaje = "¡Blackjack! ¡Perdiste!";
        } else if (resultado == 1) {
            mensaje = "¡Blackjack! ¡Ganaste!";
        } else if (manoJugador.getSuma() > 21) {
            mensaje = "¡Perdiste! Te pasaste de 21.";
        } else if (manoCrupier.getSuma() > 21) {
            mensaje = "¡Ganaste! El crupier se pasó de 21.";
        } else if (manoJugador.getSuma() == manoCrupier.getSuma()) {
            mensaje = "¡Empate!";
        } else if (manoJugador.getSuma() > manoCrupier.getSuma()) {
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

    private void agregarCartaCrupier() {
        Carta carta = mazo.robarCarta();
        manoCrupier.agregarCarta(carta);
    }

    private void agregarCartaJugador() {
        Carta carta = mazo.robarCarta();
        manoJugador.agregarCarta(carta);

        SwingUtilities
                .invokeLater(() -> panelBlackjack.dibujar(manoCrupier, manoJugador, panelApuestas.botonPlantarse));
        if (manoJugador.getSuma() > 21) {
            finalizarJuego();
        }
    }
}
