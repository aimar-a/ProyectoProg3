package GUI.perfil;

import java.awt.*;
import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class PanelCambiarContrasena extends JPanel {

    private JPasswordField txtContrasenaAntigua;
    private JPasswordField txtContrasenaNueva;
    private JPasswordField txtConfirmarContrasena;
    private JButton btnCambiar;

    public PanelCambiarContrasena(String usuario) {
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
    }

    private void cambiarContrasena(String usuario) {
        // Obtener las contraseñas introducidas
        String contrasenaAntigua = new String(txtContrasenaAntigua.getPassword());
        String contrasenaNueva = new String(txtContrasenaNueva.getPassword());
        String contrasenaConfirmada = new String(txtConfirmarContrasena.getPassword());

        // Verificar que la nueva contraseña y la confirmación coinciden
        if (!contrasenaNueva.equals(contrasenaConfirmada)) {
            JOptionPane.showMessageDialog(this, "Las contraseñas nuevas no coinciden.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Leer el archivo de usuarios y contraseñas
            List<String> lines = Files.readAllLines(Paths.get("src/CSV/usuarioContra.csv"));
            boolean usuarioEncontrado = false;
            boolean contrasenaCorrecta = false;
            List<String> nuevasLineas = new ArrayList<>();

            for (String line : lines) {
                String[] data = line.split(",");
                String nombreUsuario = data[0];
                String contrasenaActual = data[1];

                // Verificar si el usuario coincide
                if (nombreUsuario.equals(usuario)) {
                    usuarioEncontrado = true;
                    // Verificar si la contraseña antigua es correcta
                    if (contrasenaAntigua.equals(contrasenaActual)) {
                        contrasenaCorrecta = true;
                        // Actualizar la contraseña en la línea
                        nuevasLineas.add(nombreUsuario + "," + contrasenaNueva);
                    } else {
                        JOptionPane.showMessageDialog(this, "La contraseña antigua es incorrecta.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } else {
                    nuevasLineas.add(line); // Mantener la línea original si no es el usuario actual
                }
            }

            // Si el usuario no fue encontrado
            if (!usuarioEncontrado) {
                JOptionPane.showMessageDialog(this, "Usuario no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Si la contraseña antigua fue correcta, proceder a actualizar
            if (contrasenaCorrecta) {
                // Escribir las nuevas líneas en el archivo
                Files.write(Paths.get("src/CSV/usuarioContra.csv"), nuevasLineas);
                JOptionPane.showMessageDialog(this, "Contraseña cambiada correctamente.", "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al leer o escribir el archivo.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
