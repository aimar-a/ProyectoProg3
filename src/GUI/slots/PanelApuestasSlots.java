package GUI.slots;

import GUI.ColorVariables;
import GUI.mainMenu.FrameMenuPrincipal;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class PanelApuestasSlots extends JPanel {
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
        botonGirar.setEnabled(false); // Desactivado inicialmente
        add(botonGirar);

        // Listener para habilitar o deshabilitar el botón basado en el valor del
        // spinner
        spinnerApuesta.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int valorApuesta = (int) spinnerApuesta.getValue();
                botonGirar.setEnabled(valorApuesta > 0); // Habilitar si la apuesta es mayor a 0
            }
        });

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
