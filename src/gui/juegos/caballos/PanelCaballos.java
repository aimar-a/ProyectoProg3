package gui.juegos.caballos;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Random;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

//IAG: ChatGPT y GitHub Copilot
//ADAPTADO: Ordenar y limpiar código, anadir funcionalidades y autocompeltado
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

    private int caballoSeleccionado = 0;

    private PanelApuestasCaballos panelApuestasCaballos;

    @SuppressWarnings("serial")
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
                    "resources/img/juegos/caballos/animacion/" + (i % 2 == 1 ? "negro" : "normal") + "/caballo8.png")
                    .getImage();
            frameCaballo[i] = random.nextInt(7) + 1;

            // Configura panelCarril con colores intercalados
            final int index = i;
            panelCalles[i] = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Image metaImagen = new ImageIcon("resources/img/juegos/caballos/meta.png").getImage();
                    int newHeight = getHeight();
                    int newWidth = (int) (156.0 / 313.0 * newHeight);
                    g.setColor(Color.WHITE);
                    g.fillRect(META - 10, 0, newWidth + 10, getHeight());
                    g.drawImage(metaImagen, META - 5, 0, newWidth, newHeight, this);
                    g.drawImage(caballoImagen[index], posiciones[index], (getHeight() - (int) (81 * 1.3)) / 2,
                            (int) (111 * 1.3), (int) (81 * 1.3), this);

                    // Dibuja el número del caballo
                    g.setColor(Color.BLACK);
                    g.setFont(new Font("Arial", Font.BOLD, 20));
                    g.drawString("Caballo " + (index + 1), 10, 30);
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
                    SwingUtilities.invokeLater(() -> {
                        for (int i = 0; i < NUM_CABALLOS; i++) {
                            Random random = new Random();
                            int sumaPosicion = random.nextInt(6) + 3;
                            posiciones[i] += sumaPosicion;

                            if (frameCaballo[i] == 8) {
                                frameCaballo[i] = 1;
                            }
                            caballoImagen[i] = new ImageIcon(
                                    "resources/img/juegos/caballos/animacion/" + (i % 2 == 1 ? "negro" : "normal")
                                            + "/caballo"
                                            + (frameCaballo[i]++) + ".png")
                                    .getImage();

                            // Verifica si algún caballo ha cruzado la meta y asegura que solo el primero
                            // sea el ganador
                            if (posiciones[i] >= META - 135 && carreraEnCurso) {
                                carreraEnCurso = false; // Detener la carrera
                                ganador = i; // Establecer el ganador
                                panelApuestasCaballos.mostrarGanador(ganador + 1 == caballoSeleccionado, i + 1);
                                reiniciarJuego();
                                break;
                            }
                        }
                        repaint();
                    });
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            interrupt();
        }

        // Método para reiniciar el juego
        private void reiniciarJuego() {
            ganador = -1;
            carreraEnCurso = false;
            for (int i = 0; i < NUM_CABALLOS; i++) {
                posiciones[i] = 0;
            }
            SwingUtilities.invokeLater(() -> {
                repaint(); // Refresca la interfaz
                panelApuestasCaballos.reiniciarJuego();
            });
        }

    }

    // Método para iniciar la carrera
    public void iniciarCarrera(int caballoSeleccionado) {
        if (!carreraEnCurso) {
            this.caballoSeleccionado = caballoSeleccionado;
            carreraEnCurso = true;
            ganador = -1; // Reinicia el ganador
            for (int i = 0; i < NUM_CABALLOS; i++) {
                posiciones[i] = 0;
            }
            new Hilo().start();
        }
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

    protected void setPanelApuestasCaballos(PanelApuestasCaballos panelApuestasCaballos) {
        this.panelApuestasCaballos = panelApuestasCaballos;
    }
}
