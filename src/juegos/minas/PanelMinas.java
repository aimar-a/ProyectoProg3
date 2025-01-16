// https://turbo.spribegaming.com/mines?currency=USD&operator=demo&jurisdiction=CW&lang=EN&return_url=https:%2F%2Fspribe.co%2Fgames&user=22623&token=xJh3K8yHkNth5ndWAqdYAol5o0OBgB0u
package juegos.minas;

import datos.AsuntoMovimiento;
import datos.GestorBD;
import gui.ColorVariables;
import java.awt.*;
import java.util.Random;
import javax.swing.*;

// IAG: Modificado (ChatGPT y GitHub Copilot)
public class PanelMinas extends JPanel {
    private final JButton[][] botones = new JButton[5][5]; // Matriz de botones
    private final boolean[][] minas = new boolean[][] {
            { false, false, false, false, false },
            { false, false, false, false, false },
            { false, false, false, false, false },
            { false, false, false, false, false },
            { false, false, false, false, false }
    }; // Matriz de minas
    private final Random rand = new Random();
    private static final String[] diamantes = { "blue", "red", "violet", "white" };
    private int apuesta;
    private int ganancia;
    private final String usuario;
    private final JLabel lblGanancia = new JLabel("Ganancia: 0");
    private JButton btnApostar;
    private JButton btnRetirar;
    private JSpinner spinnerApuesta;

    public PanelMinas(String usuario, boolean darkMode) {
        this.usuario = usuario;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 2;
        gbc.gridy = 0;
        lblGanancia.setFont(new Font("Arial", Font.BOLD, 20));
        add(lblGanancia, gbc);

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                gbc.gridx = j;
                gbc.gridy = i + 1;
                botones[i][j] = new JButton();
                botones[i][j].setContentAreaFilled(false); // No se rellena el área del botón
                botones[i][j].setBorderPainted(false); // No se dibuja el borde del botón
                botones[i][j].setFocusPainted(false);
                botones[i][j].setMargin(new Insets(0, 0, 0, 0));
                botones[i][j].setEnabled(false);
                botones[i][j].setPreferredSize(new Dimension(200, 200));
                botones[i][j].setHorizontalAlignment(SwingConstants.CENTER);
                botones[i][j].setVerticalAlignment(SwingConstants.CENTER);
                add(botones[i][j], gbc);
                final int x = i;
                final int y = j;
                botones[i][j].addActionListener(e -> {
                    pulsarBoton(x, y);
                });
            }
        }

        gbc.gridx = 5;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 6;
        JPanel panelPremios = new JPanel(new GridBagLayout());
        gbc.insets = new Insets(10, 10, 10, 10);
        panelPremios.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panelPremios.setBackground(
                darkMode ? ColorVariables.COLOR_FONDO_DARK.brighter() : ColorVariables.COLOR_FONDO_LIGHT.darker());
        add(panelPremios, gbc);

        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        JLabel lblPremios = new JLabel("PREMIOS");
        lblPremios.setForeground(darkMode ? ColorVariables.COLOR_TEXTO_LIGHT : ColorVariables.COLOR_TEXTO_DARK);
        lblPremios.setFont(new Font("Arial", Font.BOLD, 20));
        panelPremios.add(lblPremios, gbc);
        gbc.gridwidth = 1;

        for (int i = 0; i < 4; i++) {
            gbc.gridy = i + 1;
            gbc.gridx = 0;
            ImageIcon icon = new ImageIcon("resources/img/minas/diamond_" + diamantes[i] + ".png");
            Image scaled = icon.getImage().getScaledInstance(icon.getIconWidth() / 2, icon.getIconHeight() / 2,
                    Image.SCALE_SMOOTH);
            icon = new ImageIcon(scaled);
            panelPremios.add(new JLabel(icon), gbc);
            JLabel lbl = new JLabel("x0." + (i + 1));
            lbl.setForeground(darkMode ? ColorVariables.COLOR_TEXTO_DARK : ColorVariables.COLOR_TEXTO_LIGHT);
            lbl.setFont(new Font("Arial", Font.BOLD, 20));
            gbc.gridx = 1;
            panelPremios.add(lbl, gbc);
        }

        setUp();

        if (darkMode) {
            setBackground(ColorVariables.COLOR_FONDO_DARK);
            lblGanancia.setForeground(ColorVariables.COLOR_TEXTO_DARK);
        } else {
            setBackground(ColorVariables.COLOR_FONDO_LIGHT);
            lblGanancia.setForeground(ColorVariables.COLOR_TEXTO_LIGHT);
        }
    }

    public final void iniciarJuego(int apuesta, JButton btnApostar, JButton btnRetirar, JSpinner spinnerApuesta) {
        this.btnApostar = btnApostar;
        this.btnRetirar = btnRetirar;
        this.spinnerApuesta = spinnerApuesta;
        this.apuesta = apuesta;
        this.ganancia = 0;

        for (int i = 0; i < 3; i++) {
            int x = rand.nextInt(5);
            int y = rand.nextInt(5);
            minas[x][y] = true;
        }
        for (JButton[] fila : botones) {
            for (JButton boton : fila) {
                boton.setEnabled(true);
            }
        }
    }

    private void pulsarBoton(int i, int j) {
        botones[i][j].setEnabled(false);
        if (minas[i][j]) {
            botones[i][j].setDisabledIcon(new ImageIcon("resources/img/minas/bomb.png"));
            finalizarJuego(false);
        } else {
            String diamante = diamantes[rand.nextInt(diamantes.length)];
            botones[i][j].setDisabledIcon(new ImageIcon("resources/img/minas/diamond_" + diamante + ".png"));
            int win;
            switch (diamante) {
                case "blue" -> win = (int) (this.apuesta * 0.1);
                case "red" -> win = (int) (this.apuesta * 0.2);
                case "violet" -> win = (int) (this.apuesta * 0.3);
                case "white" -> win = (int) (this.apuesta * 0.4);
                default -> throw new AssertionError();
            }
            lblGanancia.setText("Ganancia: " + (ganancia + win));
            ganancia += win;
            btnRetirar.setEnabled(true);
        }
    }

    protected void finalizarJuego(boolean ganado) {
        btnRetirar.setEnabled(false);
        for (JButton[] fila : botones) {
            for (JButton boton : fila) {
                boton.setEnabled(false);
            }
        }
        if (ganado) {
            JOptionPane.showMessageDialog(this, "¡Has ganado " + ganancia + " fichas!", "Fin del juego",
                    JOptionPane.INFORMATION_MESSAGE);
            GestorBD.agregarMovimiento(usuario, ganancia, AsuntoMovimiento.MINAS_PREMIO);
        } else {
            JOptionPane.showMessageDialog(this, "¡Has perdido!", "Fin del juego", JOptionPane.ERROR_MESSAGE);
        }
        setUp();
        btnApostar.setEnabled(true);
        spinnerApuesta.setEnabled(true);
    }

    private void setUp() {
        lblGanancia.setText("Ganancia: 0");
        for (JButton[] fila : botones) {
            for (JButton boton : fila) {
                int random = rand.nextInt(3) + 1;
                boton.setIcon(new ImageIcon("resources/img/minas/box" + random + ".png"));
                boton.setDisabledIcon(new ImageIcon("resources/img/minas/box" + random + ".png"));
            }
        }
    }
}