package gui.juegos.caballos;

import db.GestorBD;
import domain.UsuarioActual;
import domain.datos.AsuntoMovimiento;
import gui.ColorVariables;
import io.ConfigProperties;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

//IAG: ChatGPT y GitHub Copilot
//ADAPTADO: Anadir funcionalidades, autocompeltado y generar codigo desde cero
public class PanelApuestasCaballos extends JPanel {
    private static final long serialVersionUID = 1L;

    private final String usuario;
    private PanelCaballos panelCaballos;
    private int apuesta;
    private final JComboBox<Integer> comboBoxCaballoSeleccionado;
    private final JSpinner spinnerApuesta;
    private final JButton botonIniciarCarrera;

    public PanelApuestasCaballos() {
        setBackground(Color.RED);
        this.usuario = UsuarioActual.getUsuarioActual();
        // Etiqueta y ComboBox para seleccionar caballo
        add(new JLabel("Caballo:"));
        comboBoxCaballoSeleccionado = new JComboBox<>(new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8 });
        add(comboBoxCaballoSeleccionado);

        // Etiqueta y Spinner para seleccionar apuesta
        add(new JLabel("Apuesta:"));
        spinnerApuesta = new JSpinner(new SpinnerNumberModel(0, 0, 10000, 1));
        add(spinnerApuesta);

        botonIniciarCarrera = new JButton("Iniciar carrera");
        add(botonIniciarCarrera);

        // Acción del botón
        botonIniciarCarrera.addActionListener(e -> SwingUtilities.invokeLater(() -> {
            boolean caballoSeleccionado = comboBoxCaballoSeleccionado.getSelectedItem() != null;
            boolean apuestaValida = (int) spinnerApuesta.getValue() > 0;
            if (caballoSeleccionado && apuestaValida) {
                if (GestorBD.obtenerSaldo(usuario) < (int) spinnerApuesta.getValue()) {
                    // Si el usuario no tiene saldo suficiente, mostrar mensaje de error
                    JOptionPane.showMessageDialog(panelCaballos, "Saldo insuficiente", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                GestorBD.agregarMovimiento(usuario, -(int) spinnerApuesta.getValue(),
                        AsuntoMovimiento.CABALLOS_APUESTA);
                botonIniciarCarrera.setEnabled(false);
                spinnerApuesta.setEnabled(false);
                comboBoxCaballoSeleccionado.setEnabled(false);
                apuesta = (int) spinnerApuesta.getValue();
                panelCaballos.iniciarCarrera((int) comboBoxCaballoSeleccionado.getSelectedItem());
            } else {
                // Si no se ha seleccionado caballo o la apuesta es 0, mostrar mensaje de error
                JOptionPane.showMessageDialog(panelCaballos, "Por favor, selecciona un caballo y una apuesta",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }));

        // Personalizar el panel
        setBackground(ConfigProperties.isUiDarkMode() ? ColorVariables.COLOR_ROJO_DARK.getColor()
                : ColorVariables.COLOR_ROJO_LIGHT.getColor());
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
        SwingUtilities.invokeLater(() -> {
            spinnerApuesta.setEnabled(true);
            comboBoxCaballoSeleccionado.setEnabled(true);
            botonIniciarCarrera.setEnabled(true);
        });
    }
}
