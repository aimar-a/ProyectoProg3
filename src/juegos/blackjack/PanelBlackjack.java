// Código inspirado por el tutorial "Code Black Jack in Java" de [Kenny Yip Coding] en YouTube.
// URL: https://www.youtube.com/watch?v=GMdgjaDdOjI 
package juegos.blackjack;

import java.awt.*;
import javax.swing.*;

public class PanelBlackjack extends JPanel {
    private JButton botonPlantarse;
    private Mano manoCrupier;
    private Mano manoJugador;
    private JPanel panelSuperiorDerecha;

    private JLabel labelProbabilidadPedirGanar;
    private JLabel labelProbabilidadPedirEmpatar;
    private JLabel labelProbabilidadPedirPerder;
    private JLabel labelProbabilidadPlantarseGanar;
    private JLabel labelProbabilidadPlantarseEmpatar;
    private JLabel labelProbabilidadPlantarsePerder;
    private JButton botonCalcularProbabilidades = new JButton("Calcular");

    public PanelBlackjack(boolean darkMode) {
        setBackground(new Color(53, 101, 77));
        setLayout(new BorderLayout()); // Asegúrate de que el panel principal usa BorderLayout.

        // Crear un contenedor para la parte derecha
        JPanel contenedorDerecha = new JPanel();
        contenedorDerecha.setLayout(new BorderLayout());
        contenedorDerecha.setOpaque(false); // Hacer transparente el contenedor.

        // Crear y configurar el panel superior derecha
        panelSuperiorDerecha = new JPanel();
        panelSuperiorDerecha.setLayout(new BoxLayout(panelSuperiorDerecha, BoxLayout.Y_AXIS));
        if (darkMode) {
            panelSuperiorDerecha.setBackground(new Color(0, 0, 0, 50));
        } else {
            panelSuperiorDerecha.setBackground(new Color(255, 255, 255, 50));
        }
        panelSuperiorDerecha.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 0), 10));

        // Agregar contenido al panel
        panelSuperiorDerecha.add(new JLabel("PROBABILIDADES"));
        panelSuperiorDerecha.add(Box.createVerticalStrut(5));
        panelSuperiorDerecha.add(botonCalcularProbabilidades);
        panelSuperiorDerecha.add(new JSeparator(SwingConstants.HORIZONTAL));
        panelSuperiorDerecha.add(Box.createVerticalStrut(5));

        panelSuperiorDerecha.add(new JLabel("Pedir:"));
        panelSuperiorDerecha.add(Box.createVerticalStrut(10));
        labelProbabilidadPedirGanar = new JLabel("Ganar: ---");
        panelSuperiorDerecha.add(labelProbabilidadPedirGanar);
        panelSuperiorDerecha.add(Box.createVerticalStrut(5));
        labelProbabilidadPedirEmpatar = new JLabel("Empatar: ---");
        panelSuperiorDerecha.add(labelProbabilidadPedirEmpatar);
        panelSuperiorDerecha.add(Box.createVerticalStrut(5));
        labelProbabilidadPedirPerder = new JLabel("Perder: ---");
        panelSuperiorDerecha.add(labelProbabilidadPedirPerder);
        panelSuperiorDerecha.add(Box.createVerticalStrut(5));
        panelSuperiorDerecha.add(new JSeparator(SwingConstants.HORIZONTAL));
        panelSuperiorDerecha.add(Box.createVerticalStrut(5));

        panelSuperiorDerecha.add(new JLabel("Plantarse:"));
        panelSuperiorDerecha.add(Box.createVerticalStrut(10));
        labelProbabilidadPlantarseGanar = new JLabel("Ganar: ---");
        panelSuperiorDerecha.add(labelProbabilidadPlantarseGanar);
        panelSuperiorDerecha.add(Box.createVerticalStrut(5));
        labelProbabilidadPlantarseEmpatar = new JLabel("Empatar: ---");
        panelSuperiorDerecha.add(labelProbabilidadPlantarseEmpatar);
        panelSuperiorDerecha.add(Box.createVerticalStrut(5));
        labelProbabilidadPlantarsePerder = new JLabel("Perder: ---");
        panelSuperiorDerecha.add(labelProbabilidadPlantarsePerder);

        // Agregar el panel superior derecha al norte del contenedor derecha
        contenedorDerecha.add(panelSuperiorDerecha, BorderLayout.NORTH);

        // Agregar el contenedor derecha al este del PanelBlackjack
        add(contenedorDerecha, BorderLayout.EAST);

        // Agregar un oyente al botón de calcular probabilidades
        botonCalcularProbabilidades.addActionListener(e -> {
            new Thread(() -> {
                SwingUtilities.invokeLater(() -> {
                    botonCalcularProbabilidades.setEnabled(false);
                    botonCalcularProbabilidades.setText("Calculando...");
                });
                Mazo mazo = new Mazo();
                mazo.quitarCartas(manoCrupier.getCartas());
                mazo.quitarCartas(manoJugador.getCartas());
                final double[] probabilidadesPedir = calcularProbabilidades(true, manoCrupier, manoJugador,
                        mazo);
                SwingUtilities.invokeLater(() -> {
                    labelProbabilidadPedirGanar
                            .setText("Ganar: " + String.format("%.2f", probabilidadesPedir[0] * 100) + "%");
                    labelProbabilidadPedirEmpatar
                            .setText("Empatar: " + String.format("%.2f", probabilidadesPedir[1] * 100) + "%");
                    labelProbabilidadPedirPerder
                            .setText("Perder: " + String.format("%.2f", probabilidadesPedir[2] * 100) + "%");
                });
                final double[] probabilidadesPlantarse = calcularProbabilidades(false, manoCrupier, manoJugador,
                        mazo);
                SwingUtilities.invokeLater(() -> {
                    labelProbabilidadPlantarseGanar
                            .setText("Ganar: " + String.format("%.2f", probabilidadesPlantarse[0] * 100) + "%");
                    labelProbabilidadPlantarseEmpatar
                            .setText("Empatar: " + String.format("%.2f", probabilidadesPlantarse[1] * 100) + "%");
                    labelProbabilidadPlantarsePerder
                            .setText("Perder: " + String.format("%.2f", probabilidadesPlantarse[2] * 100) + "%");
                    botonCalcularProbabilidades.setEnabled(true);
                    botonCalcularProbabilidades.setText("Calcular");
                });
            }).start();
        });
    }

    // Método que se llama para actualizar los datos y repintar el panel
    protected void dibujar(Mano manoCrupier,
            Mano manoJugador, JButton botonPlantarse) {
        this.manoCrupier = manoCrupier;
        this.manoJugador = manoJugador;
        this.botonPlantarse = botonPlantarse;

        botonCalcularProbabilidades.setEnabled(botonPlantarse.isEnabled());

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            // Obtener las dimensiones del panel
            int panelAncho = getWidth();
            int panelAlto = getHeight();

            // Ajustar los tamaños de las cartas manteniendo la relación de aspecto
            int nuevoAnchoCarta = (int) (panelAncho * 0.1); // El ancho será un 10% del panel
            int nuevoAltoCarta = (int) (nuevoAnchoCarta * 1.4); // Mantener la relación de aspecto 110x154

            g.setFont(new Font("Arial", Font.BOLD, 16));
            g.setColor(Color.WHITE);

            // Mostrar la suma del crupier
            String textoSumaCrupier = botonPlantarse.isEnabled() ? "?" : String.valueOf(manoCrupier.getSuma());
            g.drawString("Crupier: " + textoSumaCrupier, 20, 30);

            // Mostrar la suma del jugador
            g.drawString("Jugador: " + this.manoJugador.getSuma(), 20, panelAlto - 20);

            // Dibujar las cartas del crupier
            for (int i = 0; i < this.manoCrupier.size(); i++) {
                Image imagenCarta = new ImageIcon(this.manoCrupier.get(i).getRutaImagen()).getImage();
                g.drawImage(imagenCarta, 20 + (nuevoAnchoCarta + 5) * i, 40, nuevoAnchoCarta, nuevoAltoCarta, null);
            }
            if (this.manoCrupier.size() == 1) {
                Image imagenCartaOculta = new ImageIcon("resources/img/Blackjack/BACK.png").getImage();
                g.drawImage(imagenCartaOculta, nuevoAnchoCarta + 25, 40, nuevoAnchoCarta, nuevoAltoCarta, null);
            }

            // Dibujar las cartas del jugador
            for (int i = 0; i < this.manoJugador.size(); i++) {
                Image imagenCarta = new ImageIcon(this.manoJugador.get(i).getRutaImagen()).getImage();
                g.drawImage(imagenCarta, 20 + (nuevoAnchoCarta + 5) * i, panelAlto - nuevoAltoCarta - 40,
                        nuevoAnchoCarta, nuevoAltoCarta, null);
            }
        } catch (Exception e) {
        }
    }

    private double[] calcularProbabilidades(boolean pedir, Mano manoCrupierRec,
            Mano manoJugadorRec, Mazo mazo) {

        // Condiciones de terminación
        if (manoJugadorRec.getSuma() > 21) {
            return new double[] { 0, 0, 1 };
        } else if (manoCrupierRec.getSuma() > 21) {
            return new double[] { 1, 0, 0 };
        } else if (!pedir && manoCrupierRec.getSuma() >= 17) {
            return calcularProbabilidadesFinales(manoCrupierRec, manoJugadorRec);
        }

        double[] probabilidad = new double[] { 0, 0, 0 };
        if (pedir) {
            calcularProbabilidadesPedir(manoCrupierRec, manoJugadorRec, mazo, probabilidad);
        } else {
            calcularProbabilidadesPlantarse(manoCrupierRec, manoJugadorRec, mazo, probabilidad);
        }
        return probabilidad;
    }

    private double[] calcularProbabilidadesFinales(Mano manoCrupierRec, Mano manoJugadorRec) {
        if (manoCrupierRec.getSuma() > manoJugadorRec.getSuma()) {
            return new double[] { 0, 0, 1 };
        } else if (manoCrupierRec.getSuma() == manoJugadorRec.getSuma()) {
            return new double[] { 0, 1, 0 };
        } else {
            return new double[] { 1, 0, 0 };
        }
    }

    private void calcularProbabilidadesPedir(Mano manoCrupierRec, Mano manoJugadorRec, Mazo mazo,
            double[] probabilidad) {
        for (Carta carta : mazo.getCartas()) {
            Mano nuevaManoJugador = new Mano(manoJugadorRec);
            nuevaManoJugador.agregarCarta(carta);
            Mazo nuevoMazo = new Mazo(mazo);
            nuevoMazo.quitarCarta(carta);

            if (nuevaManoJugador.getSuma() > 21) {
                probabilidad[2]++;
                continue;
            }

            double[] probabilidadFor = calcularProbabilidades(true, manoCrupierRec, nuevaManoJugador, nuevoMazo);
            probabilidad[0] += probabilidadFor[0] / 2;
            probabilidad[1] += probabilidadFor[1] / 2;
            probabilidad[2] += probabilidadFor[2] / 2;
            probabilidadFor = calcularProbabilidades(false, manoCrupierRec, nuevaManoJugador, nuevoMazo);
            probabilidad[0] += probabilidadFor[0] / 2;
            probabilidad[1] += probabilidadFor[1] / 2;
            probabilidad[2] += probabilidadFor[2] / 2;
        }
        probabilidad[0] /= mazo.size();
        probabilidad[1] /= mazo.size();
        probabilidad[2] /= mazo.size();
    }

    private void calcularProbabilidadesPlantarse(Mano manoCrupierRec, Mano manoJugadorRec, Mazo mazo,
            double[] probabilidad) {
        int mazoSize = mazo.size();
        for (Carta carta : mazo.getCartas()) {
            if (manoCrupierRec.size() == 1 && ((manoCrupierRec.get(0).getValor() == 10 && carta.getValor() == 1) ||
                    (manoCrupierRec.get(0).getValor() == 1 && carta.getValor() == 10))) {
                mazoSize--;
                continue;
            }
            Mano nuevaManoCrupier = new Mano(manoCrupierRec);
            nuevaManoCrupier.agregarCarta(carta);
            Mazo nuevoMazo = new Mazo(mazo);
            nuevoMazo.quitarCarta(carta);

            if (nuevaManoCrupier.getSuma() > 21) {
                probabilidad[0]++;
                continue;
            }

            double[] probabilidadFor = calcularProbabilidades(false, nuevaManoCrupier, manoJugadorRec, nuevoMazo);
            probabilidad[0] += probabilidadFor[0];
            probabilidad[1] += probabilidadFor[1];
            probabilidad[2] += probabilidadFor[2];
        }
        probabilidad[0] /= mazoSize;
        probabilidad[1] /= mazoSize;
        probabilidad[2] /= mazoSize;
    }
}
