package GUI.slots;

import GUI.ColorVariables;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelSlots extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// IAG: Modificado (ChatGPT y GitHub Copilot)
    private JLabel[][] labelsSlots = new JLabel[3][3];
    private int[][] numsImg = new int[3][3];
    private JLabel labelRecompensa;

    public PanelSlots(boolean darkMode) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        for (int columna = 0; columna < 3; columna++) {
            for (int fila = 0; fila < 3; fila++) {
                gbc.gridx = columna;
                gbc.gridy = fila;
                labelsSlots[columna][fila] = new JLabel();
                add(labelsSlots[columna][fila], gbc);
            }
        }
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        labelRecompensa = new JLabel("¡Buena suerte!");
        labelRecompensa.setFont(labelRecompensa.getFont().deriveFont(50.0f));
        labelRecompensa.setForeground(Color.YELLOW);
        add(labelRecompensa, gbc);

        if (darkMode) {
            setBackground(ColorVariables.COLOR_FONDO_DARK);
            labelRecompensa.setForeground(ColorVariables.COLOR_TEXTO_DARK);
        } else {
            setBackground(ColorVariables.COLOR_FONDO_LIGHT);
            labelRecompensa.setForeground(ColorVariables.COLOR_TEXTO_LIGHT);
        }
    }

    public void girarColumna(int columna) {
        for (int ff = 2; ff > 0; ff--) {
            labelsSlots[columna][ff].setIcon(labelsSlots[columna][ff - 1].getIcon());
            numsImg[columna][ff] = numsImg[columna][ff - 1];
        }
        long seed = System.nanoTime();
        Random r = new Random(seed);
        int n = r.nextInt(11) + 1;
        if (n == 10 && columna != 2) {
            n = 0;
        }
        numsImg[columna][0] = n;
        ImageIcon icono = new ImageIcon(getClass().getResource("/img/slots/slot" + n + ".png"));
        Image scaledImage = icono.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        ImageIcon scaledIcono = new ImageIcon(scaledImage);
        labelsSlots[columna][0].setIcon(scaledIcono);
    }

    public final void girarRuletas() {
        for (int col = 0; col < 3; col++) {
            for (int fil = 0; fil < 3; fil++) {
                Random r = new Random();
                int n = r.nextInt(11) + 1;
                if (n == 10 && (col != 2)) {
                    n = 0;
                }
                ImageIcon icono = new ImageIcon(getClass().getResource("/img/slots/slot" + n + ".png"));
                Image scaledImage = icono.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                ImageIcon scaledIcono = new ImageIcon(scaledImage);
                labelsSlots[col][fil].setIcon(scaledIcono);
            }
        }
    }

    public int[][] getIntsSlots() {
        return this.numsImg;
    }

    protected void setLabelRecompensa(String recompensa) {
        labelRecompensa.setText(recompensa);
    }
}
