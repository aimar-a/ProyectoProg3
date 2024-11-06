package GUI.Blackjack;

import java.awt.*;
import javax.swing.*;

public class PanelApuestasBlackjack extends JPanel {
    protected final JButton botonIniciar = new JButton("Iniciar Partida");
    protected final JButton botonPedir = new JButton("Pedir");
    protected final JButton botonPlantarse = new JButton("Plantarse");
    private final JTextField campoApuesta = new JTextField(10);
    protected final JCheckBox checkAutomatico = new JCheckBox("Auto");

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
        add(checkAutomatico);
        add(new JLabel(" | "));
        add(botonPedir);
        add(botonPlantarse);
    }

    public int getCantidadApuesta() {
        return Integer.parseInt(campoApuesta.getText());
    }
}
