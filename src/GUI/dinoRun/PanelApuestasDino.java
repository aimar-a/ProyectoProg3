package GUI.dinoRun;

import GUI.ColorVariables;
import datos.GestorMovimientos;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class PanelApuestasDino extends JPanel {

    private final String usuario; // Usuario para identificar en la base de datos
    private final JSpinner apuestaSpinner; // Cambiado a JSpinner
    private final JButton startButton;
    private final JButton cashOutButton;
    private final PanelDino dinoPlay;

    public PanelApuestasDino(String usuario, PanelDino dinoPlay, boolean darkMode) {
        setBackground(Color.RED);
        this.usuario = usuario;
        this.dinoPlay = dinoPlay;

        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JLabel betLabel = new JLabel("Apuesta:");
        apuestaSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 10000, 1));
        startButton = new JButton("Iniciar Carrera");
        cashOutButton = new JButton("Cobrar");
        cashOutButton.setEnabled(false);

        add(betLabel);
        add(apuestaSpinner);
        add(startButton);
        add(cashOutButton);

        startButton.addActionListener(new StartButtonListener());
        cashOutButton.addActionListener(new CashOutButtonListener());

        // Configurar el listener de fin de juego
        dinoPlay.setGameEndListener((cashedOut) -> {
            SwingUtilities.invokeLater(this::resetButtons);
        });

        if (darkMode) {
            setForeground(ColorVariables.COLOR_TEXTO_DARK);
            setBackground(ColorVariables.COLOR_ROJO_DARK);
        } else {
            setForeground(ColorVariables.COLOR_TEXTO_LIGHT);
            setBackground(ColorVariables.COLOR_ROJO_LIGHT);
        }
    }

    private void resetButtons() {
        startButton.setEnabled(true);
        cashOutButton.setEnabled(false);
        apuestaSpinner.setEnabled(true);
    }

    private class StartButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int apuesta = (int) apuestaSpinner.getValue();
            if (apuesta <= 0) {
                JOptionPane.showMessageDialog(dinoPlay, "Ingresa una apuesta válida.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (GestorMovimientos.obtenerSaldo(usuario) < apuesta) {
                JOptionPane.showMessageDialog(dinoPlay, "Saldo insuficiente.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Deduce la apuesta del saldo
            GestorMovimientos.agregarMovimiento(usuario, -apuesta, "apuesta:dinosaurio");
            dinoPlay.setApuesta(apuesta); // Pasar apuesta a PanelDino
            dinoPlay.startGame();

            startButton.setEnabled(false);
            cashOutButton.setEnabled(true);
            apuestaSpinner.setEnabled(false);
        }
    }

    private class CashOutButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int ganancia = dinoPlay.cashOut();
            if (ganancia > 0) {
                GestorMovimientos.agregarMovimiento(usuario, ganancia, "victoria:dinosaurio");
                JOptionPane.showMessageDialog(dinoPlay,
                        "¡Has ganado " + ganancia + " monedas!", "¡Enhorabuena!",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(dinoPlay, "¡Juego terminado!", "Fin del juego",
                        JOptionPane.INFORMATION_MESSAGE);
            }

            resetButtons();
        }
    }
}
