package GUI.ruleta;

import GUI.ColorVariables;
import javax.swing.JButton;
import javax.swing.JPanel;

public class PanelApuestasRuleta extends JPanel {
    public PanelApuestasRuleta(String usuario, PanelRuleta ruleta, PanelTablaDeApuestas tablaDeApuestas,
            boolean darkMode) {
        setBackground(darkMode ? ColorVariables.COLOR_ROJO_DARK : ColorVariables.COLOR_ROJO_LIGHT);

        JButton botonGirar = new JButton("Girar");
        botonGirar.addActionListener(e -> {
            botonGirar.setEnabled(false);
            ruleta.spinRoulette();
        });
        add(botonGirar);
    }
}
