package GUI.slots;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelSlots extends JPanel {
    JLabel[][] botones = new JLabel[3][3];

    public PanelSlots() {
        setLayout(new GridBagLayout());
        setBackground(Color.GRAY);
        GridBagConstraints gbc = new GridBagConstraints();
        for (int columna = 0; columna < 3; columna++) {
            for (int fila = 0; fila < 3; fila++) {
                gbc.gridx = columna;
                gbc.gridy = fila;
                botones[columna][fila] = new JLabel();
                add(botones[columna][fila], gbc);
            }
        }
    }

    public void girarColumna(int columna) {
        for (int ff = 2; ff > 0; ff--) {
            botones[columna][ff].setIcon(botones[columna][ff - 1].getIcon());
        }
        long seed = System.nanoTime(); // Usa los nanosegundos del sistema como semilla
        Random r = new Random(seed);
        int n = r.nextInt(11) + 1;
        if (n == 10) {
            n = 0;
        }
        ImageIcon icono = new ImageIcon(getClass().getResource("/img/slots/slot" + n + ".png"));
        Image scaledImage = icono.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        ImageIcon scaledIcono = new ImageIcon(scaledImage);
        botones[columna][0].setIcon(scaledIcono);
    }

    public final void girarRuletas() {
        for (int col = 0; col < 3; col++) {
            for (int fil = 0; fil < 3; fil++) {
                Random r = new Random();
                int n = r.nextInt(11) + 1;
                if (n == 10 && (fil == 0 || fil == 1)) {
                    n = 0;
                }
                ImageIcon icono = new ImageIcon(getClass().getResource("/img/slots/slot" + n + ".png"));
                Image scaledImage = icono.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                ImageIcon scaledIcono = new ImageIcon(scaledImage);
                botones[col][fil].setIcon(scaledIcono);
            }
        }
    }
}
