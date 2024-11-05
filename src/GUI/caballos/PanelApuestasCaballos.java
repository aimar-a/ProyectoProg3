package GUI.caballos;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class PanelApuestasCaballos extends JPanel {

    public PanelApuestasCaballos(PanelCaballos panelCaballos) {
        setBackground(Color.RED);
        add(new JLabel("Caballo: "));
        add(new JTextArea(1, 6));
        add(new JLabel("Apuesta: "));
        add(new JTextArea(1, 6));
        JButton botonIniciarCarrera = new JButton("Iniciar carrera");
        botonIniciarCarrera.addActionListener(e -> panelCaballos.iniciarCarrera());
        add(botonIniciarCarrera);
    }
}
