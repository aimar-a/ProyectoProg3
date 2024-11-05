//https://www.hipodromodelazarzuela.es/apuestas
package GUI.caballos;

import java.awt.*;
import java.util.Random;
import javax.swing.*;

public class PanelCaballos extends JPanel {

    private static final long serialVersionUID = 1L;
    private static final int NUM_CABALLOS = 5;
    private static final int META = 1400;
    private final int[] posiciones; // Posiciones de los caballos
    private boolean carreraEnCurso = false; // Estado de la carrera
    private int ganador = -1; // Índice del caballo ganador
    private final Thread hilo;

    public PanelCaballos() {
        posiciones = new int[NUM_CABALLOS];
        for (int i = 0; i < NUM_CABALLOS; i++) {
            posiciones[i] = 0;
        }

        hilo = new Thread(() -> {
            while (carreraEnCurso) {
                try {
                    Thread.sleep(10);
                    for (int i = 0; i < NUM_CABALLOS; i++) {
                        Random random = new Random();
                        posiciones[i] += random.nextInt(3);

                        // Verifica si algún caballo ha cruzado la meta
                        if (posiciones[i] >= META && carreraEnCurso) {
                            carreraEnCurso = false;
                            ganador = i;
                            break;
                        }
                        System.out.println("Caballo " + (i + 1) + " en posición " + posiciones[i]);
                    }
                    repaint();
                } catch (InterruptedException e) {
                }
            }
        });
    }

    // Método para iniciar la carrera
    public void iniciarCarrera() {
        carreraEnCurso = true;
        for (int i = 0; i < NUM_CABALLOS; i++) {
            posiciones[i] = 0;
        }
        hilo.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Dibuja la línea de meta
        g.setColor(Color.RED);
        g.drawLine(META, 0, META, getHeight());

        // Dibuja los caballos
        for (int i = 0; i < NUM_CABALLOS; i++) {
            g.setColor(Color.BLUE);
            g.fillRect(posiciones[i], 50 + i * 50, 40, 20);
        }

        // Muestra el ganador si la carrera ha finalizado
        if (ganador != -1) {
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("¡Caballo " + (ganador + 1) + " es el ganador!", 200, getHeight() - 50);
        }
    }
}
