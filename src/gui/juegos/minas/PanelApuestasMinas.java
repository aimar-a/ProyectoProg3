package gui.juegos.minas;

import db.GestorBD;
import domain.UsuarioActual;
import domain.datos.AsuntoMovimiento;
import gui.ColorVariables;
import io.ConfigProperties;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

//IAG: GitHub Copilot
//ADAPTADO: Anadir funcionalidades y autocompeltado
public class PanelApuestasMinas extends JPanel {
    private static final long serialVersionUID = 1L;

    private final JSpinner spinnerApuesta = new JSpinner(new SpinnerNumberModel(0, 0, 10000, 1));
    private final JButton btnApostar = new JButton("Apostar");
    private final JButton btnRetirar = new JButton("Retirar");

    public PanelApuestasMinas(PanelMinas panelMinas) {
        add(new JLabel("Apuesta: "));
        add(spinnerApuesta);
        add(btnApostar);
        btnRetirar.setEnabled(false);
        btnRetirar.addActionListener(e -> panelMinas.finalizarJuego(true));
        add(btnRetirar);

        btnApostar.addActionListener(e -> {
            btnApostar.setEnabled(false);
            spinnerApuesta.setEnabled(false);
            int apuesta = (int) spinnerApuesta.getValue();
            if (apuesta <= 0) {
                JOptionPane.showMessageDialog(panelMinas, "Ingresa una apuesta válida.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                btnApostar.setEnabled(true);
                spinnerApuesta.setEnabled(true);
                return;
            } else if (apuesta < 10) {
                JOptionPane.showMessageDialog(panelMinas, "La apuesta mínima de este juego es de 10.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                btnApostar.setEnabled(true);
                spinnerApuesta.setEnabled(true);
                return;
            }

            GestorBD.agregarMovimiento(UsuarioActual.getUsuarioActual(), -apuesta, AsuntoMovimiento.MINAS_APUESTA);
            panelMinas.iniciarJuego(apuesta, btnApostar, btnRetirar, spinnerApuesta);
        });

        if (ConfigProperties.isUiDarkMode()) {
            setBackground(ColorVariables.COLOR_ROJO_DARK.getColor());
            btnApostar.setBackground(ColorVariables.COLOR_BOTON_DARK.getColor());
            btnApostar.setForeground(ColorVariables.COLOR_BOTON_TEXTO_DARK.getColor());
            btnRetirar.setBackground(ColorVariables.COLOR_BOTON_DARK.getColor());
            btnRetirar.setForeground(ColorVariables.COLOR_BOTON_TEXTO_DARK.getColor());
        } else {
            setBackground(ColorVariables.COLOR_ROJO_LIGHT.getColor());
            spinnerApuesta.setBackground(ColorVariables.COLOR_ROJO_LIGHT.getColor());
            spinnerApuesta.setForeground(ColorVariables.COLOR_TEXTO_LIGHT.getColor());
            btnApostar.setBackground(ColorVariables.COLOR_BOTON_LIGHT.getColor());
            btnApostar.setForeground(ColorVariables.COLOR_BOTON_TEXTO_LIGHT.getColor());
        }
    }
}
