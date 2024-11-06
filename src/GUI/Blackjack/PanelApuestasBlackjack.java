package GUI.Blackjack;

import java.awt.*;
import javax.swing.*;

public class PanelApuestasBlackjack extends JPanel {
    protected final JButton botonIniciar = new JButton("Iniciar Partida");
    protected final JButton botonPedir = new JButton("Pedir");
    protected final JButton botonPlantarse = new JButton("Plantarse");
    private final JTextField campoApuesta = new JTextField(10);

    public PanelApuestasBlackjack() {
        setLayout(new FlowLayout());

        botonPedir.setEnabled(false);
        botonPlantarse.setEnabled(false);

        add(new JLabel("Apuesta: "));
        campoApuesta.setDocument(new javax.swing.text.PlainDocument() {
            @Override
            public void insertString(int offs, String str, javax.swing.text.AttributeSet a)
                    throws javax.swing.text.BadLocationException {
                if (str == null) {
                    return;
                }
                if (str.matches("[0-9]*")) {
                    super.insertString(offs, str, a);
                }
            }
        });
        add(campoApuesta);
        add(botonIniciar);
        add(new JLabel(" | "));
        add(botonPedir);
        add(botonPlantarse);

        botonIniciar.addActionListener(e -> iniciarJuego());
    }

    public int getCantidadApuesta() {
        return Integer.parseInt(campoApuesta.getText());
    }

    public void iniciarJuego() {
        panelBlackjack.inicializarJuego();
        botonIniciar.setEnabled(false);
        botonPedir.setEnabled(true);
        botonPlantarse.setEnabled(true);

        botonPedir.addActionListener(e -> panelBlackjack.pedirCartaJugador());
        botonPlantarse.addActionListener(e -> panelBlackjack.plantarseJugador());

        repaint();
    }
}
