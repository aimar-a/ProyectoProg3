package GUI.caballos;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class PanelApuestasCaballos extends JPanel {

    public PanelApuestasCaballos(PanelCaballos panelCaballos) {
        setBackground(Color.RED);

        // Etiqueta y ComboBox para seleccionar caballo
        add(new JLabel("Caballo: "));
        JComboBox<Integer> comboBoxCaballoSeleccionado = new JComboBox<>(new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8 });
        add(comboBoxCaballoSeleccionado);

        // Etiqueta y JSpinner para introducir la apuesta
        add(new JLabel("Apuesta: "));
        JSpinner spinnerApuesta = new JSpinner(new SpinnerNumberModel(0, 0, 10000, 1)); // Valor mínimo 0, máximo 10000,
                                                                                        // incremento de 1
        add(spinnerApuesta);

        // Botón para iniciar la carrera
        JButton botonIniciarCarrera = new JButton("Iniciar carrera");
        botonIniciarCarrera.setEnabled(false); // Deshabilitado inicialmente
        add(botonIniciarCarrera);

        // Listener para habilitar o deshabilitar el botón según las condiciones
        Runnable actualizarEstadoBoton = () -> {
            boolean caballoSeleccionado = comboBoxCaballoSeleccionado.getSelectedItem() != null;
            boolean apuestaValida = (int) spinnerApuesta.getValue() > 0;
            botonIniciarCarrera.setEnabled(caballoSeleccionado && apuestaValida);
        };

        // Agregar listeners al ComboBox y al Spinner
        comboBoxCaballoSeleccionado.addActionListener(e -> actualizarEstadoBoton.run());
        spinnerApuesta.addChangeListener(e -> actualizarEstadoBoton.run());

        // Acción del botón
        botonIniciarCarrera.addActionListener(e -> {
            panelCaballos.iniciarCarrera();
            botonIniciarCarrera.setEnabled(false);
            spinnerApuesta.setEnabled(false);
            comboBoxCaballoSeleccionado.setEnabled(false);
        });
    }
}
