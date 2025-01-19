package gui.juegos.dinoRun;

import gui.ColorVariables;
import io.ConfigProperties;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.util.Random;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

// IAG: Modificado (ChatGPT y GitHub Copilot)
public class PanelDino extends JPanel {
    // Constantes
    private static final double INITIAL_MULTIPLIER = 1.01;
    private static final double MAX_MULTIPLIER = 10.0;
    private static final int TIMER_DELAY = 500; // Milisegundos
    private static final int ANIMATION_DELAY = 100; // Milisegundos

    // Componentes
    private JLabel multiplierLabel;
    private JLabel dinoLabel;
    private JProgressBar progressBar;

    private Timer timer;
    private double multiplier = INITIAL_MULTIPLIER;
    private boolean isRunning = false;

    private Random random = new Random();
    private ImageIcon[] dinoFrames;
    private int currentFrame = 0;
    private Thread animationThread;

    // Listener de fin de juego
    public interface GameEndListener {
        void onGameEnd(boolean cashedOut);
    }

    private GameEndListener gameEndListener;

    public void setGameEndListener(GameEndListener listener) {
        this.gameEndListener = listener;
    }

    public PanelDino() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        initComponents();
        initTimer();
    }

    private void initComponents() {
        // Configuración de paneles
        JPanel animationPanel = new JPanel();
        animationPanel.setLayout(new BoxLayout(animationPanel, BoxLayout.Y_AXIS));
        JPanel panel = new JPanel(new BorderLayout());

        // Etiqueta del multiplicador
        multiplierLabel = new JLabel("Multiplicador: x1.00", SwingConstants.CENTER);
        multiplierLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Etiqueta del dinosaurio
        dinoLabel = new JLabel("", SwingConstants.CENTER);
        dinoLabel.setPreferredSize(new Dimension(1300, 300));
        loadDinoFrames();
        dinoLabel.setIcon(dinoFrames[0]);
        dinoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Barra de progreso
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        progressBar.setString("x1.01");
        progressBar.setMaximumSize(new Dimension(400, 30));
        progressBar.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Añadir componentes
        animationPanel.add(dinoLabel);
        animationPanel.add(progressBar);
        panel.add(multiplierLabel, BorderLayout.NORTH);
        panel.add(animationPanel, BorderLayout.CENTER);
        add(panel);

        // Configurar modo oscuro o claro
        setBackground(ConfigProperties.isUiDarkMode() ? ColorVariables.COLOR_FONDO_DARK.getColor()
                : ColorVariables.COLOR_FONDO_LIGHT.getColor());
        panel.setBackground(getBackground());
        animationPanel.setBackground(getBackground());
    }

    private void initTimer() {
        timer = new Timer(TIMER_DELAY, e -> {
            if (isRunning) {
                multiplier = calculateMultiplier(multiplier, 1);
                if (multiplier > MAX_MULTIPLIER) {
                    multiplier = MAX_MULTIPLIER;
                }
                updateUIElements();
                checkCrash();
            }
        });
    }

    private void updateUIElements() {
        multiplierLabel.setText(String.format("Multiplicador: x%.2f", multiplier));
        int progressValue = (int) mapMultiplierToProgress(multiplier);
        progressBar.setValue(progressValue);
        progressBar.setString(String.format("x%.2f", multiplier));
    }

    private void checkCrash() {
        double crashChance = 0.05 + (multiplier / MAX_MULTIPLIER) * 0.1;
        if (random.nextDouble() < crashChance) {
            endGame(false);
        }
    }

    private void loadDinoFrames() {
        dinoFrames = new ImageIcon[6];
        for (int i = 0; i < 6; i++) {
            dinoFrames[i] = new ImageIcon("resources/img/dinosaurio/raptor-run" + (i + 1) + ".png");
        }
    }

    private void startAnimation() {
        animationThread = new Thread(() -> {
            while (isRunning) {
                try {
                    currentFrame = (currentFrame + 1) % dinoFrames.length;
                    SwingUtilities.invokeLater(() -> dinoLabel.setIcon(dinoFrames[currentFrame]));
                    Thread.sleep(ANIMATION_DELAY);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        animationThread.start();
    }

    private void stopAnimation() {
        isRunning = false;
        if (animationThread != null && animationThread.isAlive()) {
            animationThread.interrupt();
        }
    }

    private double mapMultiplierToProgress(double multiplier) {
        return (multiplier - INITIAL_MULTIPLIER) / (MAX_MULTIPLIER - INITIAL_MULTIPLIER) * 100;
    }

    private double calculateMultiplier(double currentMultiplier, int steps) {
        if (steps <= 0) {
            return currentMultiplier;
        }
        double increment = 0.1 + random.nextDouble() * 0.1;
        return calculateMultiplier(currentMultiplier + increment, steps - 1);
    }

    public void startGame() {
        if (!isRunning) {
            isRunning = true;
            multiplier = INITIAL_MULTIPLIER;
            updateUIElements();
            timer.start();
            startAnimation();
        }
    }

    public int cashOut() {
        if (!isRunning)
            return 0;
        endGame(true);
        return (int) (apuesta * multiplier);
    }

    private int apuesta = 0;

    public void setApuesta(int apuesta) {
        this.apuesta = apuesta;
    }

    private void endGame(boolean cashedOut) {
        stopAnimation();
        timer.stop();

        if (gameEndListener != null) {
            gameEndListener.onGameEnd(cashedOut);
        }

        String message = cashedOut
                ? String.format("¡Cobraste con éxito! Multiplicador: x%.2f", multiplier)
                : "¡El dinosaurio se estrelló! Perdiste tu apuesta.";
        JOptionPane.showMessageDialog(this, message);
    }
}
