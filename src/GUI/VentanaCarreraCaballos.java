package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class VentanaCarreraCaballos extends JPanel implements ActionListener {
    
	private static final long serialVersionUID = 1L;
	private static final int NUM_CABALLOS = 5; 
    private static final int META = 700; 
    private int[] posiciones; 
    private int[] velocidades; 
    private Timer timer;
    private boolean carreraFinalizada = false;
    private int ganador = -1;

    public VentanaCarreraCaballos() {
        posiciones = new int[NUM_CABALLOS];
        velocidades = new int[NUM_CABALLOS];
        Random random = new Random();

        for (int i = 0; i < NUM_CABALLOS; i++) {
            posiciones[i] = 0; 
            velocidades[i] = random.nextInt(5) + 1; 
        }

        timer = new Timer(20, this);
        timer.start();
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        g.setColor(Color.RED);
        g.drawLine(META, 0, META, getHeight());

       
        for (int i = 0; i < NUM_CABALLOS; i++) {
            g.setColor(Color.BLUE);
            g.fillRect(posiciones[i], 50 + i * 50, 40, 20);
        }

        if (carreraFinalizada) {
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("¡Caballo " + (ganador + 1) + " es el ganador!", 200, getHeight() - 50);
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!carreraFinalizada) {
            for (int i = 0; i < NUM_CABALLOS; i++) {
                posiciones[i] += velocidades[i]; 
                
                if (posiciones[i] >= META && !carreraFinalizada) {
                    carreraFinalizada = true;
                    ganador = i;
                    timer.stop();
                    break;
                }
            }
            repaint();
        }
    }
}