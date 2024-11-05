package GUI.ruleta;

import java.awt.*;
import javax.swing.*;

public class PanelRuleta extends JPanel {
    private static final int RADIUS = 250; // Radio de la ruleta
    private static final Color RED = new Color(255, 0, 0);
    private static final Color BLACK = new Color(0, 0, 0);

    // Orden de los números en una ruleta de casino real
    private static final int[] NUMS = {
            0, 32, 15, 19, 4, 21, 2, 25, 17, 34, 6, 27, 13, 36, 11, 30, 8, 23, 10, 5,
            24, 16, 33, 1, 20, 14, 31, 9, 22, 18, 29, 7, 28, 12, 35, 3, 26
    };

    public PanelRuleta() {
        setPreferredSize(new Dimension(2 * RADIUS, 2 * RADIUS));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Dibujar la ruleta
        drawRuleta(g2d);

        // Dibujar la circunferencia central ajustada
        drawCentralCircle(g2d);
    }

    private void drawRuleta(Graphics2D g2d) {
        double anglePerSector = 360.0 / NUMS.length;

        for (int i = 0; i < NUMS.length; i++) {
            int number = NUMS[i];
            double startAngle = i * anglePerSector;

            // Determinar el color del sector (verde para 0, rojo/negro alternado para el
            // resto)
            Color color;
            if (number == 0) {
                color = Color.GREEN;
            } else {
                // Alternar colores basados en el patrón de ruletas reales
                color = (i % 2 == 0) ? RED : BLACK;
            }
            g2d.setColor(color);

            // Dibujar el sector de la ruleta
            g2d.fillArc(0, 0, 2 * RADIUS, 2 * RADIUS, (int) -startAngle, (int) -anglePerSector - 1);

            // Dibujar el número en el borde del sector
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 12));
            String numStr = Integer.toString(number);

            // Calcular la posición del número más cerca del borde
            double angleForNumber = Math.toRadians(startAngle + anglePerSector / 2);
            int x = (int) (RADIUS + RADIUS * 0.85 * Math.cos(angleForNumber)); // 0.85 ajusta la cercanía al borde
            int y = (int) (RADIUS + RADIUS * 0.85 * Math.sin(angleForNumber));

            // Centrar el número en las coordenadas calculadas
            FontMetrics fm = g2d.getFontMetrics();
            g2d.drawString(numStr, x - fm.stringWidth(numStr) / 2, y + fm.getAscent() / 2);
        }
    }

    private void drawCentralCircle(Graphics2D g2d) {
        int innerRadius = (int) (RADIUS * 0.75); // Radio del círculo central ajustado para que quede más grande

        // Posición y tamaño del círculo
        int x = RADIUS - innerRadius;
        int y = RADIUS - innerRadius;
        int diameter = 2 * innerRadius;

        g2d.setColor(Color.DARK_GRAY); // Color del círculo central
        g2d.fillOval(x, y, diameter, diameter);
    }
}
