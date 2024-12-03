package GUI.dinoRun;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelApuestasDino extends JPanel {
    private JTextField betField;
    private JButton startButton;
    private JButton cashOutButton;
    private PanelDino dinoPlay; // Cambiado para recibir PanelDino

    public PanelApuestasDino(PanelDino dinoPlay) {
    	setBackground(Color.red);
        this.dinoPlay = dinoPlay;

        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JLabel betLabel = new JLabel("Apuesta:");
        betField = new JTextField(10);
        startButton = new JButton("Iniciar Carrera");
        cashOutButton = new JButton("Cobrar");
        cashOutButton.setEnabled(false);

        add(betLabel);
        add(betField);
        add(startButton);
        add(cashOutButton);

        startButton.addActionListener(new StartButtonListener());
        cashOutButton.addActionListener(new CashOutButtonListener());
    }

    private class StartButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String betText = betField.getText();
            if (betText.isEmpty()) {
                JOptionPane.showMessageDialog(dinoPlay, "Por favor, ingrese una cantidad para apostar.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                double betAmount = Double.parseDouble(betText);
                if (betAmount <= 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dinoPlay, "Por favor, ingrese una cantidad válida.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            dinoPlay.startGame(); // Llamada a método en PanelDino
            startButton.setEnabled(false);
            cashOutButton.setEnabled(true);
        }
    }

    private class CashOutButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dinoPlay.cashOut(); // Llamada a método en PanelDino
            startButton.setEnabled(true);
            cashOutButton.setEnabled(false);
        }
    }
}
