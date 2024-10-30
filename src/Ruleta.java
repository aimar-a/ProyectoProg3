
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Ruleta extends JFrame {
    private JPanel panelRuleta;
    private JButton botonGirar;
    private int anguloActual = 0;
    private Timer timer;
    private Random random = new Random();
    private int velocidad = 20; // Velocidad de giro inicial
    private boolean girando = false;

    public Ruleta() {
        // Configuración de la ventana principal
        setTitle("Ruleta");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear el panel de la ruleta
        panelRuleta = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                dibujarRuleta(g);
            }
        };
        panelRuleta.setPreferredSize(new Dimension(300, 300));

        // Crear el botón para girar la ruleta
        botonGirar = new JButton("Girar");
        botonGirar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!girando) {
                    girarRuleta();
                }
            }
        });

        // Agregar el panel y el botón a la ventana
        add(panelRuleta, BorderLayout.CENTER);
        add(botonGirar, BorderLayout.SOUTH);
    }

    // Dibujar la ruleta en el panel
    private void dibujarRuleta(Graphics g) {
        int x = 50;
        int y = 50;
        int width = 200;
        int height = 200;

        // Dividir la ruleta en segmentos
        int segmentos = 8;
        int anguloSegmento = 360 / segmentos;

        // Dibujar cada segmento
        for (int i = 0; i < segmentos; i++) {
            g.setColor(i % 2 == 0 ? Color.RED : Color.BLUE);
            g.fillArc(x, y, width, height, anguloActual + i * anguloSegmento, anguloSegmento);
        }

        // Dibujar un marcador en la parte superior
        g.setColor(Color.BLACK);
        g.fillPolygon(new int[] { 150, 140, 160 }, new int[] { 20, 50, 50 }, 3);
    }

    // Método para iniciar el giro de la ruleta
    private void girarRuleta() {
        girando = true;
        velocidad = 20;
        timer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                anguloActual = (anguloActual + velocidad) % 360;
                panelRuleta.repaint();

                // Reducir la velocidad gradualmente
                if (velocidad > 0) {
                    velocidad--;
                } else {
                    detenerRuleta();
                }
            }
        });
        timer.start();
    }

    // Detener la ruleta y seleccionar un segmento aleatorio
    private void detenerRuleta() {
        timer.stop();
        girando = false;
        int anguloFinal = anguloActual % 360;
        int segmentoGanador = anguloFinal / (360 / 8); // Ajusta el número de segmentos
        JOptionPane.showMessageDialog(this, "Segmento ganador: " + segmentoGanador);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Ruleta().setVisible(true);
        });
    }
}
