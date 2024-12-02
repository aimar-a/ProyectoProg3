package GUI.perfil;

import datos.GestorUsuarios;
import datos.TiposDeDatos;
import java.awt.*;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class PanelDatosUsuario extends JPanel {

    private static final long serialVersionUID = 1L;
    private static final Font FONT = new Font("Arial", Font.PLAIN, 16);
    private JTextField[] campos;
    private JLabel[] labelsFormatoInfo;
    private TiposDeDatos[] tipos;

    public PanelDatosUsuario(String usuario) {
        String[] datos = GestorUsuarios.obtenerDatos(usuario);
        setLayout(new BorderLayout());

        JPanel panelCentral = new JPanel(new GridBagLayout());
        add(panelCentral, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new java.awt.Insets(5, 20, 5, 20);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.1;

        campos = new JTextField[10]; // Ajustar a la cantidad de campos requeridos
        tipos = new TiposDeDatos[] {
                TiposDeDatos.USUARIO, TiposDeDatos.NOMBRE, TiposDeDatos.APELLIDOS, TiposDeDatos.DNI,
                TiposDeDatos.MAIL, TiposDeDatos.TELEFONO, TiposDeDatos.PROVINCIA, TiposDeDatos.CIUDAD,
                TiposDeDatos.CALLE, TiposDeDatos.NUMERO
        };

        labelsFormatoInfo = Arrays.stream(campos).map(c -> new JLabel()).toArray(JLabel[]::new);

        JTextField txtUsuario = crearCampoTexto(panelCentral, "Usuario: ", usuario, false, gbc);
        JTextField txtNombre = crearCampoTexto(panelCentral, "Nombre: ", datos[0], true, gbc);
        JTextField txtApellidos = crearCampoTexto(panelCentral, "Apellidos: ", datos[1], true, gbc);
        JTextField txtDNI = crearCampoTexto(panelCentral, "DNI: ", datos[2], true, gbc);
        JTextField txtEmail = crearCampoTexto(panelCentral, "Email: ", datos[3], true, gbc);
        JTextField txtTelefono = crearCampoTexto(panelCentral, "Teléfono: ", datos[4] + " " + datos[5], true, gbc);
        JTextField txtProvincia = crearCampoTexto(panelCentral, "Provincia: ", datos[6], true, gbc);
        JTextField txtCiudad = crearCampoTexto(panelCentral, "Ciudad: ", datos[7], true, gbc);
        JTextField txtDireccion = crearCampoTexto(panelCentral, "Dirección: ", datos[8] + ", " + datos[9], true, gbc);
        JTextField txtFechaNacimiento = crearCampoTexto(panelCentral, "Fecha de Nacimiento: ", datos[10], false, gbc);

        JTextField[] camposTexto = { txtNombre, txtApellidos, txtDNI, txtEmail, txtTelefono, txtProvincia, txtCiudad,
                txtDireccion, txtFechaNacimiento };

        JPanel panelOpciones = new JPanel();
        JButton btnEditar = new JButton("Editar");
        JButton btnGuardar = new JButton("Guardar");

        btnEditar.addActionListener(e -> setEditable(true, txtNombre, txtApellidos, txtDNI, txtEmail, txtTelefono,
                txtProvincia, txtCiudad, txtDireccion, txtFechaNacimiento));
        panelOpciones.add(btnEditar);

        btnGuardar.addActionListener(e -> {
            btnGuardar.setEnabled(false);
            String[] datosNuevos = {
                    txtNombre.getText(), txtApellidos.getText(), txtDNI.getText(), txtEmail.getText(),
                    txtTelefono.getText().split(" ")[0], txtTelefono.getText().split(" ")[1],
                    txtProvincia.getText(), txtCiudad.getText(),
                    txtDireccion.getText().split(",")[0], txtDireccion.getText().split(",")[1].replace(" ", ""),
                    txtFechaNacimiento.getText()
            };
            if (GestorUsuarios.cambiarDatos(usuario, datosNuevos)) {
                JOptionPane.showMessageDialog(this, "Datos actualizados correctamente", "Información",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar los datos", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
            setEditable(false, txtNombre, txtApellidos, txtDNI, txtEmail, txtTelefono, txtProvincia, txtCiudad,
                    txtDireccion, txtFechaNacimiento);
        });

        btnGuardar.setEnabled(false); // Deshabilitar el botón guardar inicialmente
        panelOpciones.add(btnGuardar);
        add(panelOpciones, BorderLayout.SOUTH);

        // Agregar listeners para validación en tiempo real
        DocumentListener validationListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                btnGuardar.setEnabled(validarCampos(camposTexto));
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                btnGuardar.setEnabled(validarCampos(camposTexto));
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                btnGuardar.setEnabled(validarCampos(camposTexto));
            }
        };

        agregarDocumentListeners(validationListener, camposTexto);
    }

    private JTextField crearCampoTexto(JPanel panel, String etiqueta, String texto, boolean editable,
            GridBagConstraints gbc) {
        JLabel label = new JLabel(etiqueta);
        label.setFont(FONT);
        panel.add(label, gbc);
        gbc.gridx++;
        gbc.weightx = 1.0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        JTextField campoTexto = new JTextField(texto);
        campoTexto.setFont(FONT);
        campoTexto.setEditable(editable);
        panel.add(campoTexto, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.weightx = 0.1;
        gbc.gridwidth = 1;
        return campoTexto;
    }

    private void setEditable(boolean editable, JTextField... camposTexto) {
        for (JTextField campoTexto : camposTexto) {
            campoTexto.setEditable(editable);
        }
    }

    private void agregarDocumentListeners(DocumentListener listener, JTextField... camposTexto) {
        for (JTextField campoTexto : camposTexto) {
            campoTexto.getDocument().addDocumentListener(listener);
        }
    }

    private boolean validarCampos(JTextField[] camposTexto) {
        for (JTextField campo : camposTexto) {
            if (campo.getText().trim().isEmpty()) {
                return false; // Si algún campo está vacío, deshabilitar el botón de guardar
            }
        }
        return true;
    }
}
