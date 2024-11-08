package GUI.slots;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PanelApuestasSlots extends JPanel {
    JTextField apuesta;
    JButton botonGirar;

    public PanelApuestasSlots() {
        setBackground(Color.RED);
        add(new JLabel("Apuesta: "));
        apuesta = new JTextField(10);
        add(apuesta);
        botonGirar = new JButton("Girar");
        add(botonGirar);
    }
}
