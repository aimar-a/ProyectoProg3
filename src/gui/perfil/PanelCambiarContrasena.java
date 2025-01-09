package gui.perfil;

import datos.GestorBD;
import gui.ColorVariables;

import java.awt.*;
import javax.swing.*;

public class PanelCambiarContrasena extends JPanel {
    // IAG: Modificado (ChatGPT y GitHub Copilot)
    private final JPasswordField txtContrasenaAntigua;
    private final JPasswordField txtContrasenaNueva;
    private final JPasswordField txtConfirmarContrasena;
    private final JButton btnCambiar;

    public PanelCambiarContrasena(String usuario, boolean darkMode) {
        setLayout(new BorderLayout()); // Configurar el layout en BorderLayout

        // Panel central con GridBagLayout
        JPanel panelCentro = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("Arial", Font.PLAIN, 18);
        Font fieldFont = new Font("Arial", Font.PLAIN, 18);

        // Etiquetas y campos de texto
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel lblContrasenaAntigua = new JLabel("Contraseña Antigua:");
        lblContrasenaAntigua.setFont(labelFont);
        panelCentro.add(lblContrasenaAntigua, gbc);
        gbc.gridx = 1;
        txtContrasenaAntigua = new JPasswordField(15);
        txtContrasenaAntigua.setFont(fieldFont);
        panelCentro.add(txtContrasenaAntigua, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel lblContrasenaNueva = new JLabel("Contraseña Nueva:");
        lblContrasenaNueva.setFont(labelFont);
        panelCentro.add(lblContrasenaNueva, gbc);
        gbc.gridx = 1;
        txtContrasenaNueva = new JPasswordField(15);
        txtContrasenaNueva.setFont(fieldFont);
        panelCentro.add(txtContrasenaNueva, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel lblConfirmarContrasena = new JLabel("Confirmar Contraseña Nueva:");
        lblConfirmarContrasena.setFont(labelFont);
        panelCentro.add(lblConfirmarContrasena, gbc);
        gbc.gridx = 1;
        txtConfirmarContrasena = new JPasswordField(15);
        txtConfirmarContrasena.setFont(fieldFont);
        panelCentro.add(txtConfirmarContrasena, gbc);

        add(panelCentro, BorderLayout.CENTER);

        // Panel sur con el botón
        JPanel panelSur = new JPanel();
        btnCambiar = new JButton("Cambiar Contraseña");
        btnCambiar.setFont(new Font("Arial", Font.PLAIN, 18));
        btnCambiar.addActionListener(e -> cambiarContrasena(usuario));
        panelSur.add(btnCambiar);
        add(panelSur, BorderLayout.SOUTH);

        if (darkMode) {
            setBackground(ColorVariables.COLOR_FONDO_DARK);
            panelCentro.setBackground(ColorVariables.COLOR_FONDO_DARK);
            panelSur.setBackground(ColorVariables.COLOR_FONDO_DARK);
            lblContrasenaAntigua.setForeground(ColorVariables.COLOR_TEXTO_DARK);
            lblContrasenaNueva.setForeground(ColorVariables.COLOR_TEXTO_DARK);
            lblConfirmarContrasena.setForeground(ColorVariables.COLOR_TEXTO_DARK);
            txtContrasenaAntigua.setBackground(ColorVariables.COLOR_FONDO_DARK);
            txtContrasenaAntigua.setForeground(ColorVariables.COLOR_TEXTO_DARK);
            txtContrasenaNueva.setBackground(ColorVariables.COLOR_FONDO_DARK);
            txtContrasenaNueva.setForeground(ColorVariables.COLOR_TEXTO_DARK);
            txtConfirmarContrasena.setBackground(ColorVariables.COLOR_FONDO_DARK);
            txtConfirmarContrasena.setForeground(ColorVariables.COLOR_TEXTO_DARK);
            btnCambiar.setBackground(ColorVariables.COLOR_BOTON_DARK);
            btnCambiar.setForeground(ColorVariables.COLOR_TEXTO_DARK);
        } else {
            setBackground(ColorVariables.COLOR_FONDO_LIGHT);
            panelCentro.setBackground(ColorVariables.COLOR_FONDO_LIGHT);
            panelSur.setBackground(ColorVariables.COLOR_FONDO_LIGHT);
        }
    }

    private void cambiarContrasena(String usuario) {
        // Obtener las contraseñas introducidas
        String contrasenaAntigua = new String(txtContrasenaAntigua.getPassword());
        String contrasenaNueva = new String(txtContrasenaNueva.getPassword());
        String contrasenaConfirmada = new String(txtConfirmarContrasena.getPassword());

        if (!contrasenaAntigua.equals(GestorBD.obtenerContrasena(usuario))) {
            JOptionPane.showMessageDialog(this, "La contraseña antigua es incorrecta.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!contrasenaNueva.equals(contrasenaConfirmada)) {
            JOptionPane.showMessageDialog(this, "Las contraseñas nuevas no coinciden.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (contrasenaAntigua.equals(contrasenaNueva)) {
            JOptionPane.showMessageDialog(this, "La nueva contraseña no puede ser igual a la anterior.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (GestorBD.cambiarContrasena(usuario, contrasenaNueva)) {
            JOptionPane.showMessageDialog(this, "Contraseña cambiada correctamente.", "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Ha ocurrido un error al cambiar la contraseña.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
