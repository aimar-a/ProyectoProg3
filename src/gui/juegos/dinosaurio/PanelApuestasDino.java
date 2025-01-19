package gui.juegos.dinosaurio;

import db.GestorBD;
import domain.UsuarioActual;
import domain.datos.AsuntoMovimiento;
import gui.ColorVariables;
import io.ConfigProperties;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

// IAG: Modificado (ChatGPT y GitHub Copilot)
public class PanelApuestasDino extends JPanel {

    private final String usuario; // Usuario para identificar en la base de datos
    private final JSpinner apuestaSpinner; // Cambiado a JSpinner
    private final JButton startButton;
    private final JButton cashOutButton;
    private final PanelDino dinoPlay;

    public PanelApuestasDino(PanelDino dinoPlay) {
        setBackground(
                ConfigProperties.isUiDarkMode() ? ColorVariables.COLOR_ROJO_DARK.getColor()
                        : ColorVariables.COLOR_ROJO_LIGHT.getColor());
        this.usuario = UsuarioActual.getUsuarioActual();
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

        if (ConfigProperties.isUiDarkMode()) {
            setForeground(ColorVariables.COLOR_TEXTO_DARK.getColor());
            setBackground(ColorVariables.COLOR_ROJO_DARK.getColor());
        } else {
            setForeground(ColorVariables.COLOR_TEXTO_LIGHT.getColor());
            setBackground(ColorVariables.COLOR_ROJO_LIGHT.getColor());
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

            if (GestorBD.obtenerSaldo(usuario) < apuesta) {
                JOptionPane.showMessageDialog(dinoPlay, "Saldo insuficiente.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Deduce la apuesta del saldo
            GestorBD.agregarMovimiento(usuario, -apuesta, AsuntoMovimiento.DINO_APUESTA);
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
                GestorBD.agregarMovimiento(usuario, ganancia, AsuntoMovimiento.DINO_PREMIO);
                JOptionPane.showMessageDialog(dinoPlay,
                        "¡Has ganado " + ganancia + " fichas!", "¡Enhorabuena!",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(dinoPlay, "¡Juego terminado!", "Fin del juego",
                        JOptionPane.INFORMATION_MESSAGE);
            }

            resetButtons();
        }
    }
}