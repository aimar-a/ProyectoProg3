package gui.juegos.slots;

import gui.ColorVariables;
import io.ConfigProperties;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

//IAG: ChatGPT y GitHub Copilot
//ADAPTADO: Ordenar y limpiar código, anadir funcionalidades y autocompeltado
public class PanelApuestasSlots extends JPanel {
    private static final long serialVersionUID = 1L;
    protected JSpinner spinnerApuesta;
    protected JButton botonGirar;

    public PanelApuestasSlots() {
        // Etiqueta para la apuesta
        add(new JLabel("Apuesta: "));

        // Configuración del JSpinner
        spinnerApuesta = new JSpinner(new SpinnerNumberModel(0, 0, 1000, 1)); // Valor inicial 0, mínimo 0, máximo 1000,

        add(spinnerApuesta);

        // Botón para girar
        botonGirar = new JButton("Girar");
        add(botonGirar);

        if (ConfigProperties.isUiDarkMode()) {
            setBackground(ColorVariables.COLOR_ROJO_DARK.getColor());
        } else {
            setBackground(ColorVariables.COLOR_ROJO_LIGHT.getColor());
        }
    }

    public int getApuesta() {
        return (int) spinnerApuesta.getValue();
    }
}
