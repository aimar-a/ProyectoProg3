package GUI.caballos;

import GUI.ColorVariables;
import datos.AsuntoMovimiento;
import datos.GestorBD;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
//IAG: Modificado (ChatGPT y GitHub Copilot)
public class PanelApuestasCaballos extends JPanel {
    private static final long serialVersionUID = 1L;

    private final String usuario;
    private PanelCaballos panelCaballos;
    private int apuesta;
    private final JComboBox<Integer> comboBoxCaballoSeleccionado;
    private final JSpinner spinnerApuesta;
    private final JButton botonIniciarCarrera;

    public PanelApuestasCaballos(String usuario, boolean darkMode) {
        setBackground(Color.RED);
        this.usuario = usuario;
        // Etiqueta y ComboBox para seleccionar caballo
        add(new JLabel("Caballo:"));
        comboBoxCaballoSeleccionado = new JComboBox<>(new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8 });
        add(comboBoxCaballoSeleccionado);

        // Etiqueta y Spinner para seleccionar apuesta
        add(new JLabel("Apuesta:"));
        spinnerApuesta = new JSpinner(new SpinnerNumberModel(0, 0, 10000, 1)); // Valor mínimo 0, máximo 10000,
                                                                               // incremento de 1
                                                                               // incremento de 1
        add(spinnerApuesta);

        botonIniciarCarrera = new JButton("Iniciar carrera");
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
            if (GestorBD.obtenerSaldo(usuario) < (int) spinnerApuesta.getValue()) {
                // Si el usuario no tiene saldo suficiente, mostrar mensaje de error
                JOptionPane.showMessageDialog(panelCaballos, "Saldo insuficiente", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            GestorBD.agregarMovimiento(usuario, -(int) spinnerApuesta.getValue(), AsuntoMovimiento.CABALLOS_APUESTA);
            botonIniciarCarrera.setEnabled(false);
            spinnerApuesta.setEnabled(false);
            comboBoxCaballoSeleccionado.setEnabled(false);
            apuesta = (int) spinnerApuesta.getValue();
            panelCaballos.iniciarCarrera((int) comboBoxCaballoSeleccionado.getSelectedItem());
        });

        // Personalizar el panel
        setBackground(darkMode ? ColorVariables.COLOR_ROJO_DARK : ColorVariables.COLOR_ROJO_LIGHT);
    }

    protected void setPanelCaballos(PanelCaballos panelCaballos) {
        this.panelCaballos = panelCaballos;
    }

    protected void mostrarGanador(boolean haGanado, int caballoGanador) {
        if (haGanado) {
            JOptionPane.showMessageDialog(panelCaballos,
                    "¡Has ganado " + apuesta * 8 + " monedas!\nCaballo ganador: " + caballoGanador, "¡Enhorabuena!",
                    JOptionPane.INFORMATION_MESSAGE);
            GestorBD.agregarMovimiento(usuario, apuesta * 8, AsuntoMovimiento.CABALLOS_PREMIO);
            apuesta = 0;
        } else {
            JOptionPane.showMessageDialog(panelCaballos, "¡Has perdido!\nCaballo ganador: " + caballoGanador,
                    "¡Lo siento!",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    protected void reiniciarJuego() {
        spinnerApuesta.setEnabled(true);
        comboBoxCaballoSeleccionado.setEnabled(true);
        botonIniciarCarrera.setEnabled(true);
    }
}
