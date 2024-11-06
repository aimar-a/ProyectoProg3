package GUI.Blackjack;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class PanelBlackjack extends JPanel {
    Carta cartaOculta;
    int sumaCrupier, sumaJugador;
    JButton botonPlantarse;
    private final int anchoCarta = 110;
    private final int altoCarta = 154;
    ArrayList<Carta> manoCrupier;
    ArrayList<Carta> manoJugador;

    public PanelBlackjack() {
        setBackground(new Color(53, 101, 77));
        setLayout(new BorderLayout());
    }

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

}
