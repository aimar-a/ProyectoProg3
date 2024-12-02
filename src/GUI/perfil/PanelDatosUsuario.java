package GUI.perfil;

import datos.AccionValidarCampo;
import datos.GestorUsuarios;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

// IAG: Ordenar codigo
public class PanelDatosUsuario extends JPanel {

    private static final long serialVersionUID = 1L;
    private static final Font FONT = new Font("Arial", Font.PLAIN, 16);

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

        crearCampoTexto(panelCentral, "Usuario: ", usuario, false, gbc);
        JTextField txtNombre = crearCampoTexto(panelCentral, "Nombre: ", datos[0], false, gbc);
        JTextField txtApellidos = crearCampoTexto(panelCentral, "Apellidos: ", datos[1], false, gbc);
        JTextField txtDNI = crearCampoTexto(panelCentral, "DNI: ", datos[2], false, gbc);
        JTextField txtEmail = crearCampoTexto(panelCentral, "Email: ", datos[3], false, gbc);
        JTextField txtTelefono = crearCampoTexto(panelCentral, "Teléfono: ", datos[4] + " " + datos[5], false, gbc);
        JTextField txtProvincia = crearCampoTexto(panelCentral, "Provincia: ", datos[6], false, gbc);
        JTextField txtCiudad = crearCampoTexto(panelCentral, "Ciudad: ", datos[7], false, gbc);
        JTextField txtDireccion = crearCampoTexto(panelCentral, "Dirección: ", datos[8] + ", " + datos[9], false, gbc);
        JTextField txtFechaNacimiento = crearCampoTexto(panelCentral, "Fecha de Nacimiento: ", datos[10], false, gbc);
        crearCampoTexto(panelCentral, "Fecha de Registro: ", datos[11], false, gbc);

        JTextField[] camposTexto = { txtNombre, txtApellidos, txtDNI, txtEmail, txtTelefono, txtProvincia, txtCiudad,
                txtDireccion, txtFechaNacimiento };
        TiposDeDatos[] regex = { TiposDeDatos.NOMBRE, TiposDeDatos.APELLIDOS, TiposDeDatos.DNI, TiposDeDatos.EMAIL,
                TiposDeDatos.TELEFONO, TiposDeDatos.PROVINCIA, TiposDeDatos.CIUDAD, TiposDeDatos.DIRECCION,
                TiposDeDatos.FECHA_NACIMIENTO };

        JPanel panelOpciones = new JPanel();
        JButton btnEditar = new JButton("Editar");
        JButton btnGuardar = new JButton("Guardar");

        btnEditar.addActionListener(e -> setEditable(true, txtNombre, txtApellidos, txtDNI, txtEmail, txtTelefono,
                txtProvincia, txtCiudad, txtDireccion, txtFechaNacimiento));
        panelOpciones.add(btnEditar);

        btnGuardar.addActionListener(e -> {
            btnGuardar.setEnabled(false);
            String[] datosNuevos = { txtNombre.getText(), txtApellidos.getText(), txtDNI.getText(), txtEmail.getText(),
                    txtTelefono.getText().split(" ")[0], txtTelefono.getText().split(" ")[1], txtProvincia.getText(),
                    txtCiudad.getText(), txtDireccion.getText().split(",")[0],
                    txtDireccion.getText().split(",")[1].replace(" ", ""), txtFechaNacimiento.getText() };
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
        AccionValidarCampo.verificarCampos(btnGuardar, camposTexto, regex);
        btnGuardar.setEnabled(false);
        panelOpciones.add(btnGuardar);
        add(panelOpciones, BorderLayout.SOUTH);

        DocumentListener validationListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                AccionValidarCampo.verificarCampos(btnGuardar, camposTexto, regex);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                AccionValidarCampo.verificarCampos(btnGuardar, camposTexto, regex);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                AccionValidarCampo.verificarCampos(btnGuardar, camposTexto, regex);
            }
        };

        agregarDocumentListeners(validationListener, txtNombre, txtApellidos, txtDNI, txtEmail, txtTelefono,
                txtProvincia, txtCiudad, txtDireccion, txtFechaNacimiento);
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
}
