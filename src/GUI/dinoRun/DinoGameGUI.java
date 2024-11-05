package GUI.dinoRun;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class DinoGameGUI extends JFrame {
    private JButton startButton;
    private JButton cashOutButton;
    private JLabel multiplierLabel;
    private JLabel dinoLabel;
    private JProgressBar progressBar;
    private JTextField betField;
    private Timer timer;
    private double multiplier = 1.01; // Inicia en 1.01
    private boolean isRunning = false;
    private final double MAX_MULTIPLIER = 100000.0; // Límite del multiplicador
    private Random random = new Random();

    public DinoGameGUI() {
        setTitle("Dino Game - Casa de Apuestas");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal para la disposición general
        JPanel panel = new JPanel(new BorderLayout());

        // Configurar etiquetas y campo de entrada para la apuesta
        JPanel betPanel = new JPanel(new FlowLayout());
        JLabel betLabel = new JLabel("Apuesta:");
        betField = new JTextField(10); // Campo para introducir la cantidad de la apuesta
        betPanel.add(betLabel);
        betPanel.add(betField);

        // Configurar la etiqueta del multiplicador y el dinosaurio
        multiplierLabel = new JLabel("Multiplicador: x1.01", SwingConstants.CENTER);
        dinoLabel = new JLabel("🦖", SwingConstants.CENTER);

        // Configurar botones de inicio y cobro
        startButton = new JButton("Iniciar Carrera");
        cashOutButton = new JButton("Cobrar");
        startButton.addActionListener(new StartButtonListener());
        cashOutButton.addActionListener(new CashOutButtonListener());
        cashOutButton.setEnabled(false);

        // Barra de progreso horizontal de 0 a 100
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true); // Muestra el progreso en texto
        progressBar.setOrientation(JProgressBar.HORIZONTAL); // Configura orientación horizontal
        progressBar.setString("x1.01"); // Configuración inicial

        // Crear un panel para centrar la barra de progreso
        JPanel progressPanel = new JPanel();
        progressPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        progressPanel.add(progressBar); // Añadir la barra de progreso al panel

        // Configurar el panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(startButton);
        buttonPanel.add(cashOutButton);

        // Añadir los componentes al panel principal
        panel.add(betPanel, BorderLayout.NORTH); // Panel de apuesta en la parte superior
        panel.add(multiplierLabel, BorderLayout.CENTER);
        panel.add(dinoLabel, BorderLayout.EAST);
        panel.add(progressPanel, BorderLayout.CENTER); // Colocar la barra de progreso centrada
        panel.add(buttonPanel, BorderLayout.SOUTH); // Botones en la parte inferior

        // Agregar el panel principal al frame
        add(panel);

        // Configurar el temporizador para simular el aumento del multiplicador
        timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isRunning) {
                    // Generar un incremento aleatorio que no sea demasiado regular
                    double increment = 0.1 + (random.nextDouble() * 0.4); // Incremento entre 0.1 y 0.5
                    multiplier += increment; // Aumentar el multiplicador

                    // Limitar el multiplicador al máximo permitido
                    if (multiplier > MAX_MULTIPLIER) {
                        multiplier = MAX_MULTIPLIER;
                    }

                    multiplierLabel.setText(String.format("Multiplicador: x%.2f", multiplier));

                    // Actualizar la barra de progreso
                    int progressValue = (int) mapMultiplierToProgress(multiplier);
                    progressBar.setValue(progressValue);
                    progressBar.setString(String.format("x%.2f", multiplier)); // Mostrar multiplicador en texto

                    // Simular probabilidad de choque, con mayor probabilidad a medida que aumenta el multiplicador
                    if (random.nextDouble() < (0.05 + (multiplier / MAX_MULTIPLIER) * 0.1)) { // Aumentar riesgo de choque
                        endGame(false);
                    }
                }
            }
        });
    }

    // Método para mapear el valor del multiplicador al rango de la barra de progreso
    private double mapMultiplierToProgress(double multiplier) {
        // Mapea de 1.01 a 100000 a 0 a 100
        return (multiplier - 1.01) / (MAX_MULTIPLIER - 1.01) * 100;
    }

    // Listener para el botón de inicio
    private class StartButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!isRunning) {
                // Validar que se haya ingresado una cantidad para apostar
                String betText = betField.getText();
                if (betText.isEmpty()) {
                    JOptionPane.showMessageDialog(DinoGameGUI.this, "Por favor, ingrese una cantidad para apostar.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                // Convertir la entrada a un número (aquí se puede añadir más validación si se desea)
                try {
                    double betAmount = Double.parseDouble(betText);
                    if (betAmount <= 0) {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(DinoGameGUI.this, "Por favor, ingrese una cantidad válida.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                isRunning = true;
                multiplier = 1.01; // Reiniciar el multiplicador a 1.01
                multiplierLabel.setText("Multiplicador: x1.01");
                progressBar.setValue(0); // Reiniciar la barra de progreso
                progressBar.setString("x1.01"); // Reiniciar el texto de la barra
                timer.start();
                startButton.setEnabled(false);
                cashOutButton.setEnabled(true);
            }
        }
    }

    // Listener para el botón de cobro
    private class CashOutButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (isRunning) {
                endGame(true);
            }
        }
    }

    // Finalizar el juego
    private void endGame(boolean cashedOut) {
        isRunning = false;
        timer.stop();
        startButton.setEnabled(true);
        cashOutButton.setEnabled(false);

        String message = cashedOut ?
                "¡Cobraste con éxito! Multiplicador: x" + String.format("%.2f", multiplier) + " sobre tu apuesta." :
                "¡El dinosaurio se estrelló! Perdiste tu apuesta.";
        JOptionPane.showMessageDialog(this, message);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DinoGameGUI game = new DinoGameGUI();
            game.setVisible(true);
        });
    }
}
