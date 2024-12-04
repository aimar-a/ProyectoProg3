package GUI.dinoRun;

import GUI.ColorVariables;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.*;

public class PanelDino extends JPanel {
    private JLabel multiplierLabel;
    private JLabel dinoLabel;
    private JProgressBar progressBar;
    private Timer timer;
    private double multiplier = 1.01; // Inicia en 1.01
    private boolean isRunning = false;
    private final double MAX_MULTIPLIER = 10.0; // Límite del multiplicador
    private Random random = new Random();
    private ImageIcon[] dinoFrames; // Arreglo de imágenes del dinosaurio
    private int currentFrame = 0; // Índice del cuadro actual para la animación
    private Thread animationThread; // Hilo para manejar la animación

    public interface GameEndListener {
        void onGameEnd(boolean cashedOut);
    }

    private GameEndListener gameEndListener;

    public void setGameEndListener(GameEndListener listener) {
        this.gameEndListener = listener;
    }

    public PanelDino(boolean darkMode) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Diseño vertical
        JPanel animationPanel = new JPanel();
        animationPanel.setLayout(new BoxLayout(animationPanel, BoxLayout.Y_AXIS));
        // Cargar imágenes para la animación del dinosaurio
        loadDinoFrames();

        // Panel principal para la disposición general
        JPanel panel = new JPanel(new BorderLayout());

        multiplierLabel = new JLabel("Multiplicador: x1.00", SwingConstants.CENTER);
        dinoLabel = new JLabel("", SwingConstants.CENTER);
        dinoLabel.setPreferredSize(new Dimension(1300, 300)); // Tamaño preferido
        dinoLabel.setIcon(dinoFrames[0]); // Establecer el primer cuadro
        dinoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        // Barra de progreso horizontal de 0 a 100

        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true); // Mostrar el texto del progreso
        progressBar.setString("x1.01");
        progressBar.setMaximumSize(new Dimension(400, 30)); // Tamaño fijo
        progressBar.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrado horizontalmente

        animationPanel.add(dinoLabel);
        animationPanel.add(progressBar);

        // Crear un panel para centrar la barra de progreso

        // Añadir los componentes al panel principal
        panel.add(multiplierLabel, BorderLayout.NORTH);

        panel.add(animationPanel, BorderLayout.CENTER);

        // Agregar el panel principal al frame
        add(panel);

        // Configurar el temporizador para simular el aumento del multiplicador
        timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isRunning) {
                    double increment = 0.1 + (random.nextDouble() * 0.1); // Incremento entre 0.1 y 0.2
                    multiplier += increment; // Aumentar el multiplicador

                    if (multiplier > MAX_MULTIPLIER) {
                        multiplier = MAX_MULTIPLIER;
                    }

                    multiplierLabel.setText(String.format("Multiplicador: x%.2f", multiplier));
                    int progressValue = (int) mapMultiplierToProgress(multiplier);
                    progressBar.setValue(progressValue);
                    progressBar.setString(String.format("x%.2f", multiplier));

                    if (random.nextDouble() < (0.05 + (multiplier / MAX_MULTIPLIER) * 0.1)) {
                        endGame(false);
                    }
                }
            }
        });

        if (darkMode) {
            setForeground(ColorVariables.COLOR_TEXTO_DARK);
            setBackground(ColorVariables.COLOR_FONDO_DARK);
            panel.setBackground(ColorVariables.COLOR_FONDO_DARK);
            animationPanel.setBackground(ColorVariables.COLOR_FONDO_DARK);
        } else {
            setForeground(ColorVariables.COLOR_TEXTO_LIGHT);
            setBackground(ColorVariables.COLOR_FONDO_LIGHT);
            panel.setBackground(ColorVariables.COLOR_FONDO_LIGHT);
            animationPanel.setBackground(ColorVariables.COLOR_FONDO_LIGHT);
        }
    }

    private void loadDinoFrames() {
        dinoFrames = new ImageIcon[6];
        for (int i = 0; i < 6; i++) {
            dinoFrames[i] = new ImageIcon("src/img/dinosaurio/raptor-run" + (i + 1) + ".png");
        }
    }

    private void startAnimation() {
        animationThread = new Thread(() -> {
            while (isRunning) {
                try {
                    currentFrame = (currentFrame + 1) % dinoFrames.length;
                    SwingUtilities.invokeLater(() -> dinoLabel.setIcon(dinoFrames[currentFrame]));
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        animationThread.start();
    }

    private void stopAnimation() {
        if (animationThread != null && animationThread.isAlive()) {
            isRunning = false;
            animationThread.interrupt();

        }
    }

    private double mapMultiplierToProgress(double multiplier) {
        return (multiplier - 1.01) / (MAX_MULTIPLIER - 1.01) * 100;
    }

    public void startGame() {
        if (!isRunning) {
            isRunning = true;
            multiplier = 1.01;
            multiplierLabel.setText("Multiplicador: x1.00");
            progressBar.setValue(0);
            progressBar.setString("x1.00");
            timer.start();
            startAnimation();
        }
    }

    private int apuesta = 0;

    public void setApuesta(int apuesta) {
        this.apuesta = apuesta;
    }
    
    public int cashOut() {
        if (!isRunning)
            return 0; // Si el juego no está corriendo, no hay ganancias

        endGame(true); // Termina el juego
        int ganancia = (int) (apuesta * multiplier);
        apuesta = 0; // Reinicia la apuesta
        return ganancia;
    }

    private void endGame(boolean cashedOut) {
        isRunning = false;
        timer.stop();
        stopAnimation();

        // Notificar al listener que el juego terminó
        if (gameEndListener != null) {
            gameEndListener.onGameEnd(cashedOut);
        }

        // Mensaje al usuario
        String message = cashedOut
                ? "¡Cobraste con éxito! Multiplicador: x" + String.format("%.2f", multiplier) + " sobre tu apuesta."
                : "¡El dinosaurio se estrelló! Perdiste tu apuesta.";
        JOptionPane.showMessageDialog(this, message);
    }
}
