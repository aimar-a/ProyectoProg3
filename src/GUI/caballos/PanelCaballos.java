package GUI.caballos;

import java.awt.*;
import java.util.Random;
import javax.swing.*;

public class PanelCaballos extends JPanel {

    private static final long serialVersionUID = 1L;
    private static final int NUM_CABALLOS = 8;
    private static final int META = 1400;
    private final int[] posiciones; // Posiciones de los caballos
    private boolean carreraEnCurso = false; // Estado de la carrera
    private int ganador = -1; // Índice del caballo ganador

    private Image[] caballoImagen;
    private final int[] frameCaballo;
    private final JPanel[] panelCalles;

    public PanelCaballos() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Configura layout vertical
        caballoImagen = new Image[NUM_CABALLOS];
        frameCaballo = new int[NUM_CABALLOS];
        panelCalles = new JPanel[NUM_CABALLOS];
        posiciones = new int[NUM_CABALLOS];

        for (int i = 0; i < NUM_CABALLOS; i++) {
            posiciones[i] = 0;
            Random random = new Random();
            caballoImagen[i] = new ImageIcon(
                    "src/img/caballos/" + (i % 2 == 1 ? "negro" : "normal") + "/caballo8.png")
                    .getImage();
            frameCaballo[i] = random.nextInt(7) + 1;

            // Configura panelCarril con colores intercalados
            final int index = i;
            panelCalles[i] = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Image metaImagen = new ImageIcon("src/img/caballos/meta.png").getImage();
                    int newHeight = getHeight();
                    int newWidth = (int) (156.0 / 313.0 * newHeight);
                    g.setColor(Color.WHITE);
                    g.fillRect(META - 5, 0, newWidth + 10, getHeight());
                    g.drawImage(metaImagen, META, 0, newWidth, newHeight, this);
                    g.drawImage(caballoImagen[index], posiciones[index], (getHeight() - (int) (81 * 1.3)) / 2,
                            (int) (111 * 1.3), (int) (81 * 1.3), this);
                }
            };

            panelCalles[i].setPreferredSize(new Dimension(1500, 100));
            panelCalles[i].setBackground(i % 2 == 0 ? new Color(34, 139, 34) : new Color(144, 238, 144)); // Verde
            add(panelCalles[i]); // Añade cada panelCarril al panel principal
        }
    }

    private class Hilo extends Thread {
        @Override
        public void run() {
            while (carreraEnCurso) {
                try {
                    Thread.sleep(50);
                    for (int i = 0; i < NUM_CABALLOS; i++) {
                        Random random = new Random();
                        int sumaPosicion = random.nextInt(6) + 3;
                        posiciones[i] += sumaPosicion;

                        if (frameCaballo[i] == 8) {
                            frameCaballo[i] = 1;
                        }
                        caballoImagen[i] = new ImageIcon(
                                "src/img/caballos/" + (i % 2 == 1 ? "negro" : "normal") + "/caballo"
                                        + (frameCaballo[i]++) + ".png")
                                .getImage();

                        // Verifica si algún caballo ha cruzado la meta y asegura que solo el primero
                        // sea el ganador
                        if (posiciones[i] >= META - 135 && carreraEnCurso) {
                            carreraEnCurso = false; // Detener la carrera
                            ganador = i; // Establecer el ganador
                            mostrarGanador(i + 1); // Muestra mensaje del ganador y luego reinicia el juego
                            break;
                        }
                    }
                    repaint();
                } catch (InterruptedException e) {
                }
            }
            interrupt();
        }
    }

    // Método para iniciar la carrera
    public void iniciarCarrera() {
        if (!carreraEnCurso) {
            carreraEnCurso = true;
            ganador = -1; // Reinicia el ganador
            for (int i = 0; i < NUM_CABALLOS; i++) {
                posiciones[i] = 0;
            }
            new Hilo().start();
        }
    }

    // Método para mostrar el ganador en un JOptionPane y reiniciar el juego
    private void mostrarGanador(int numeroCaballo) {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(this, "¡Caballo " + numeroCaballo + " es el ganador!",
                    "Resultado de la Carrera", JOptionPane.INFORMATION_MESSAGE);
            reiniciarJuego(); // Reinicia el juego después de cerrar el mensaje
        });
    }

    // Método para reiniciar el juego
    private void reiniciarJuego() {
        ganador = -1;
        carreraEnCurso = false;
        for (int i = 0; i < NUM_CABALLOS; i++) {
            posiciones[i] = 0;
        }
        repaint(); // Refresca la interfaz
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Dibuja la línea de meta en cada panelCarril
        g.setColor(Color.RED);
        g.drawLine(META, 0, META, getHeight());

        // Muestra el ganador si la carrera ha finalizado
        if (ganador != -1) {
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("¡Caballo " + (ganador + 1) + " es el ganador!", 200, getHeight() - 50);
        }
    }
}
