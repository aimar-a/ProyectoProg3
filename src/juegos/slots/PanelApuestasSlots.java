package juegos.slots;

import gui.ColorVariables;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

// IAG: Modificado (ChatGPT y GitHub Copilot)
public class PanelApuestasSlots extends JPanel {
    private static final long serialVersionUID = 1L;
    protected JSpinner spinnerApuesta;
    protected JButton botonGirar;

    public PanelApuestasSlots(boolean darkMode) {
        // Etiqueta para la apuesta
        add(new JLabel("Apuesta: "));

        // Configuración del JSpinner
        spinnerApuesta = new JSpinner(new SpinnerNumberModel(0, 0, 1000, 1)); // Valor inicial 0, mínimo 0, máximo 1000,
                                                                              // paso 1
        add(spinnerApuesta);

        // Botón para girar
        botonGirar = new JButton("Girar");
        add(botonGirar);

        if (darkMode) {
            setBackground(ColorVariables.COLOR_ROJO_DARK);
        } else {
            setBackground(ColorVariables.COLOR_ROJO_LIGHT);
        }
    }

    public int getApuesta() {
        return (int) spinnerApuesta.getValue();
    }
}
