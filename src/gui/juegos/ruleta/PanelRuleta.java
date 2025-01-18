package gui.juegos.ruleta;

import gui.ColorVariables;
import io.ConfigProperties;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

//IAG: ChatGPT y GitHub Copilot
//ADAPTADO: Ordenar y limpiar código, anadir funcionalidades y autocompeltado
public class PanelRuleta extends JPanel {
    private final int[] numbers = {
            0, 32, 15, 19, 4, 21, 2, 25, 17, 34, 6, 27, 13, 36, 11, 30, 8, 23, 10, 5,
            24, 16, 33, 1, 20, 14, 31, 9, 22, 18, 29, 7, 28, 12, 35, 3, 26
    };
    private final JLabel resultLabel;
    private final JPanel numbersPanel;
    private int currentIndex = 0;
    private static final int NUMEROS_EN_CADA_LADO = 18;
    private int numeroPremiado = -1;

    public PanelRuleta() {
        setLayout(new BorderLayout());
        setBackground(
                ConfigProperties.isUiDarkMode() ? ColorVariables.COLOR_FONDO_DARK.getColor()
                        : ColorVariables.COLOR_FONDO_LIGHT.getColor());

        // Numbers panel
        numbersPanel = new JPanel();
        numbersPanel.setLayout(new GridLayout(1, numbers.length));
        Random random = new Random();
        currentIndex = random.nextInt(numbers.length);
        for (int i = -NUMEROS_EN_CADA_LADO; i < NUMEROS_EN_CADA_LADO + 1; i++) {
            int index = (currentIndex + i + numbers.length) % numbers.length;
            JLabel numberLabel = new JLabel(String.valueOf(numbers[index]), SwingConstants.CENTER);
            numberLabel.setPreferredSize(new Dimension(80, 80));
            numberLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            numberLabel.setForeground(Color.WHITE);
            if (index == 0) {
                numberLabel.setBackground(Color.GREEN);
            } else {
                numberLabel.setBackground((index % 2 == 0) ? Color.BLACK : Color.RED);
            }
            numbersPanel.add(numberLabel);
            numberLabel.setOpaque(true);
        }

        // Result label
        resultLabel = new JLabel("\u25B2", SwingConstants.CENTER); // Triangle as indicator
        resultLabel.setFont(new Font("Arial", Font.BOLD, 24));
        resultLabel.setForeground(ConfigProperties.isUiDarkMode() ? Color.WHITE : Color.BLACK);

        // Add components to panel
        add(numbersPanel, BorderLayout.CENTER);
        add(resultLabel, BorderLayout.SOUTH);
    }

    public void spinRoulette() {
        Random random = new Random();
        double slower = random.nextDouble(0.2) + 1.1;
        new Thread(() -> {
            for (double wait = 0.2; wait < 800; wait *= slower) {
                try {
                    Thread.sleep((long) wait);
                    currentIndex = (currentIndex + 1) % numbers.length;
                    SwingUtilities.invokeLater(() -> {
                        for (int j = -NUMEROS_EN_CADA_LADO; j < NUMEROS_EN_CADA_LADO + 1; j++) {
                            JLabel label = (JLabel) numbersPanel.getComponent(j + NUMEROS_EN_CADA_LADO);
                            int index = (currentIndex + j + numbers.length) % numbers.length;
                            label.setText(
                                    String.valueOf(numbers[index]));
                            if (index == 0) {
                                label.setBackground(Color.GREEN);
                            } else {
                                label.setBackground((index % 2 == 0) ? Color.BLACK : Color.RED);
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            numeroPremiado = numbers[currentIndex];
        }).start();
    }

    public int getResult() {
        return numbers[currentIndex];
    }

    protected void setNumeroPremiado(int numeroPremiado) {
        this.numeroPremiado = numeroPremiado;
    }

    protected int getNumeroPremiado() {
        return numeroPremiado;
    }
}
