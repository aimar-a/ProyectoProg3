package GUI.Blackjack;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class PanelBlackjack extends JPanel {
    private Carta cartaOculta;
    private int sumaCrupier, sumaJugador;
    private JButton botonPlantarse;
    private ArrayList<Carta> manoCrupier;
    private ArrayList<Carta> manoJugador;

    public PanelBlackjack() {
        setBackground(new Color(53, 101, 77));
        setLayout(new BorderLayout());
    }

    // Método que se llama para actualizar los datos y repintar el panel
    void dibujar(Carta cartaOculta, int sumaCrupier, int sumaJugador, ArrayList<Carta> manoCrupier,
            ArrayList<Carta> manoJugador, JButton botonPlantarse) {
        this.cartaOculta = cartaOculta;
        this.sumaCrupier = sumaCrupier;
        this.sumaJugador = sumaJugador;
        this.manoCrupier = manoCrupier;
        this.manoJugador = manoJugador;
        this.botonPlantarse = botonPlantarse;

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            // Obtener las dimensiones del panel
            int panelAncho = getWidth();
            int panelAlto = getHeight();

            // Ajustar los tamaños de las cartas manteniendo la relación de aspecto
            int nuevoAnchoCarta = (int) (panelAncho * 0.1); // El ancho será un 10% del panel
            int nuevoAltoCarta = (int) (nuevoAnchoCarta * 1.4); // Mantener la relación de aspecto 110x154

            g.setFont(new Font("Arial", Font.BOLD, 16));
            g.setColor(Color.WHITE);

            // Mostrar la suma del crupier
            String textoSumaCrupier = botonPlantarse.isEnabled() ? "?" : String.valueOf(sumaCrupier);
            g.drawString("Crupier: " + textoSumaCrupier, 20, 30);

            // Mostrar la suma del jugador
            g.drawString("Jugador: " + sumaJugador, 20, panelAlto - 20);

            // Mostrar la carta oculta del crupier
            Image imagenCartaOculta = new ImageIcon(getClass().getResource("/img/Blackjack/BACK.png")).getImage();
            if (!botonPlantarse.isEnabled()) {
                imagenCartaOculta = new ImageIcon(getClass().getResource(cartaOculta.getRutaImagen())).getImage();
            }
            g.drawImage(imagenCartaOculta, 20, 40, nuevoAnchoCarta, nuevoAltoCarta, null);

            // Dibujar las cartas del crupier
            for (int i = 0; i < manoCrupier.size(); i++) {
                Image imagenCarta = new ImageIcon(getClass().getResource(manoCrupier.get(i).getRutaImagen()))
                        .getImage();
                g.drawImage(imagenCarta, nuevoAnchoCarta + 25 + (nuevoAnchoCarta + 5) * i, 40, nuevoAnchoCarta,
                        nuevoAltoCarta, null);
            }

            // Dibujar las cartas del jugador
            for (int i = 0; i < manoJugador.size(); i++) {
                Image imagenCarta = new ImageIcon(getClass().getResource(manoJugador.get(i).getRutaImagen()))
                        .getImage();
                g.drawImage(imagenCarta, 20 + (nuevoAnchoCarta + 5) * i, panelAlto - nuevoAltoCarta - 40,
                        nuevoAnchoCarta, nuevoAltoCarta, null);
            }
        } catch (Exception e) {
        }
    }
}
