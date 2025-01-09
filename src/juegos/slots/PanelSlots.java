package juegos.slots;

import gui.ColorVariables;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

// IAG: Modificado (ChatGPT y GitHub Copilot)
public class PanelSlots extends JPanel {
    private static final long serialVersionUID = 1L;
    private JLabel[][] labelsSlots = new JLabel[3][3];
    private int[][] numsImg = new int[3][3];
    private JLabel labelRecompensa;

    public PanelSlots(boolean darkMode) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JPanel slotsPanel = new JPanel(new GridBagLayout());
        slotsPanel.setOpaque(true);
        slotsPanel.setBackground(Color.MAGENTA);
        slotsPanel.setPreferredSize(new Dimension(600, 600));
        slotsPanel.setBorder(new LineBorder(Color.GREEN, 10)); // Añadir borde verde
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        add(slotsPanel, gbc);

        GridBagConstraints slotsGbc = new GridBagConstraints();

        // Adding slot labels
        for (int columna = 0; columna < 3; columna++) {
            for (int fila = 0; fila < 3; fila++) {
                slotsGbc.gridx = columna;
                slotsGbc.gridy = fila;
                slotsGbc.gridwidth = 1;
                slotsGbc.fill = GridBagConstraints.NONE;
                labelsSlots[columna][fila] = new JLabel();
                slotsPanel.add(labelsSlots[columna][fila], slotsGbc);
            }
        }

        // Adding horizontal lines
        for (int fila = 0; fila < 3; fila++) {
            slotsGbc.gridx = 0;
            slotsGbc.gridy = fila;
            slotsGbc.gridwidth = 3;
            slotsGbc.fill = GridBagConstraints.HORIZONTAL;
            HorizontalLinePanel linePanel = new HorizontalLinePanel(darkMode);
            slotsPanel.add(linePanel, slotsGbc);
        }

        // Adding diagonal lines
        DiagonalLinesPanel diagonalLinesPanel = new DiagonalLinesPanel(darkMode);
        slotsGbc.gridx = 0;
        slotsGbc.gridy = 0;
        slotsGbc.gridwidth = 3;
        slotsGbc.gridheight = 3;
        slotsGbc.fill = GridBagConstraints.BOTH;
        slotsPanel.add(diagonalLinesPanel, slotsGbc);

        JPanel recompensaPanel = new JPanel(new GridBagLayout());
        recompensaPanel.setOpaque(true);
        recompensaPanel.setBackground(Color.CYAN);
        recompensaPanel.setPreferredSize(new Dimension(600, 100));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 10, 10, 10);
        add(recompensaPanel, gbc);

        labelRecompensa = new JLabel("¡Buena suerte!");
        labelRecompensa.setFont(labelRecompensa.getFont().deriveFont(50.0f));
        labelRecompensa.setForeground(Color.YELLOW);
        recompensaPanel.add(labelRecompensa);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.insets = new Insets(10, 10, 10, 10);
        add(new PanelPremios(), gbc);

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
        } else if (n == 4 && columna == 0) {
            n = 20;
        } else if (n == 4 && columna == 1) {
            n = 21;
        }
        numsImg[columna][0] = n;
        ImageIcon icono = new ImageIcon("resources/img/slots/slot" + n + ".png");
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
                ImageIcon icono = new ImageIcon("resources/img/slots/slot" + n + ".png");
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

    private class DiagonalLinesPanel extends JPanel {
        private static final long serialVersionUID = 1L;
        private boolean darkMode;

        public DiagonalLinesPanel(boolean darkMode) {
            this.darkMode = darkMode;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(3)); // Grosor de línea más grueso
            Color lineColor = darkMode ? Color.DARK_GRAY : Color.LIGHT_GRAY;
            g2.setColor(lineColor);
            g2.drawLine(0, 0, getWidth(), getHeight());
            g2.drawLine(0, getHeight(), getWidth(), 0);
        }
    }

    private class HorizontalLinePanel extends JPanel {
        private static final long serialVersionUID = 1L;
        private boolean darkMode;

        public HorizontalLinePanel(boolean darkMode) {
            this.darkMode = darkMode;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(3)); // Grosor de línea más grueso
            Color lineColor = darkMode ? Color.DARK_GRAY : Color.LIGHT_GRAY;
            g2.setColor(lineColor);
            g2.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2);
        }
    }
}
