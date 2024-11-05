package GUI.slots;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class PanelApuestas extends JPanel {
    public PanelApuestas(PanelSlots panelSlots) {
        setBackground(Color.RED);
        add(new JLabel("Apuesta: "));
        add(new JTextArea(1, 6));
        JButton botonGirar = new JButton("Girar");
        botonGirar.addActionListener(e -> panelSlots.girarRuletas());
        add(botonGirar);
    }
}
