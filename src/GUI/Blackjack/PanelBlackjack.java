package GUI.Blackjack;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class PanelBlackjack extends JPanel {
    private Mazo mazo;
    private ArrayList<Carta> manoCrupier;
    private ArrayList<Carta> manoJugador;
    private Carta cartaOculta;
    private int sumaCrupier, sumaJugador;
    private int cuentaAsesCrupier, cuentaAsesJugador;
    private JButton botonPedir, botonPlantarse, botonIniciar;
    private PanelApuestasBlackjack panelApuestas;
    private int cantidadApuesta;
    private final int anchoCarta = 110;
    private final int altoCarta = 154;

    public PanelBlackjack() {
        setBackground(new Color(53, 101, 77));
        setLayout(new BorderLayout());
    }

    //
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        dibujarCartas(g);
    }

    private void dibujarCartas(Graphics g) {
        try {
            g.setFont(new Font("Arial", Font.BOLD, 16));
            g.setColor(Color.WHITE);

            String textoSumaCrupier = botonPlantarse.isEnabled() ? "?" : String.valueOf(sumaCrupier);
            g.drawString("Crupier: " + textoSumaCrupier, 20, 15);

            g.drawString("Jugador: " + sumaJugador, 20, 300);

            Image imagenCartaOculta = new ImageIcon(getClass().getResource("/img/Blackjack/BACK.png")).getImage();
            if (!botonPlantarse.isEnabled()) {
                imagenCartaOculta = new ImageIcon(getClass().getResource(cartaOculta.getRutaImagen())).getImage();
            }
            g.drawImage(imagenCartaOculta, 20, 20, anchoCarta, altoCarta, null);

            for (int i = 0; i < manoCrupier.size(); i++) {
                Image imagenCarta = new ImageIcon(getClass().getResource(manoCrupier.get(i).getRutaImagen()))
                        .getImage();
                g.drawImage(imagenCarta, anchoCarta + 25 + (anchoCarta + 5) * i, 20, anchoCarta, altoCarta, null);
            }

            for (int i = 0; i < manoJugador.size(); i++) {
                Image imagenCarta = new ImageIcon(getClass().getResource(manoJugador.get(i).getRutaImagen()))
                        .getImage();
                g.drawImage(imagenCarta, 20 + (anchoCarta + 5) * i, 320, anchoCarta, altoCarta, null);
            }
        } catch (Exception e) {
        }
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
