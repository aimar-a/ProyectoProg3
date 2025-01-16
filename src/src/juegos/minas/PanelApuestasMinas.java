package juegos.minas;

import datos.AsuntoMovimiento;
import datos.GestorBD;
import gui.ColorVariables;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

//IAG: Modificado (ChatGPT y GitHub Copilot)
public class PanelApuestasMinas extends JPanel {
    private static final long serialVersionUID = 1L;

    private final JSpinner spinnerApuesta = new JSpinner(new SpinnerNumberModel(0, 0, 10000, 1));
    private final JButton btnApostar = new JButton("Apostar");
    private final JButton btnRetirar = new JButton("Retirar");

    public PanelApuestasMinas(String usuario, PanelMinas panelMinas, boolean darkMode) {
        add(new JLabel("Apuesta: "));
        add(spinnerApuesta);
        add(btnApostar);
        btnRetirar.setEnabled(false);
        btnRetirar.addActionListener(e -> panelMinas.finalizarJuego(true));
        add(btnRetirar);

        btnApostar.addActionListener(e -> {
            btnApostar.setEnabled(false);
            int apuesta = (int) spinnerApuesta.getValue();
            if (apuesta <= 0) {
                JOptionPane.showMessageDialog(panelMinas, "Ingresa una apuesta válida.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            } else if (apuesta < 10) {
                JOptionPane.showMessageDialog(panelMinas, "La apuesta mínima de este juego es de 10.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            GestorBD.agregarMovimiento(usuario, -apuesta, AsuntoMovimiento.MINAS_APUESTA);
            panelMinas.iniciarJuego(apuesta, btnApostar, btnRetirar);
        });

        if (darkMode) {
            setBackground(ColorVariables.COLOR_FONDO_DARK);
            spinnerApuesta.setBackground(ColorVariables.COLOR_ROJO_DARK);
            spinnerApuesta.setForeground(ColorVariables.COLOR_TEXTO_DARK);
            btnApostar.setBackground(ColorVariables.COLOR_BOTON_DARK);
            btnApostar.setForeground(ColorVariables.COLOR_BOTON_TEXTO_DARK);
        } else {
            setBackground(ColorVariables.COLOR_FONDO_LIGHT);
            spinnerApuesta.setBackground(ColorVariables.COLOR_ROJO_LIGHT);
            spinnerApuesta.setForeground(ColorVariables.COLOR_TEXTO_LIGHT);
            btnApostar.setBackground(ColorVariables.COLOR_BOTON_LIGHT);
            btnApostar.setForeground(ColorVariables.COLOR_BOTON_TEXTO_LIGHT);
        }
    }
}