package GUI.perfil;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

// IAG: Ordenar codigo
public class PanelDatosUsuario extends JPanel {

    private static final long serialVersionUID = 1L;
    private static final String RUTA_ARCHIVO_CSV = "src/CSV/usuarioDatos.csv";
    private static final Font FONT = new Font("Arial", Font.PLAIN, 16);

    public PanelDatosUsuario(String usuario) {
        String[] datos = obtenerDatosUsuario(usuario);
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
        JTextField txtNombre = crearCampoTexto(panelCentral, "Nombre: ", datos[1], false, gbc);
        JTextField txtApellidos = crearCampoTexto(panelCentral, "Apellidos: ", datos[2], false, gbc);
        JTextField txtDNI = crearCampoTexto(panelCentral, "DNI: ", datos[3], false, gbc);
        JTextField txtEmail = crearCampoTexto(panelCentral, "Email: ", datos[4], false, gbc);
        JTextField txtTelefono = crearCampoTexto(panelCentral, "Teléfono: ", datos[5] + " " + datos[6], false, gbc);
        JTextField txtProvincia = crearCampoTexto(panelCentral, "Provincia: ", datos[7], false, gbc);
        JTextField txtCiudad = crearCampoTexto(panelCentral, "Ciudad: ", datos[8], false, gbc);
        JTextField txtDireccion = crearCampoTexto(panelCentral, "Dirección: ", datos[9] + ", " + datos[10], false, gbc);
        JTextField txtFechaNacimiento = crearCampoTexto(panelCentral, "Fecha de Nacimiento: ", datos[11], false, gbc);
        JTextField txtFechaRegistro = crearCampoTexto(panelCentral, "Fecha de Registro: ", datos[12], false, gbc);

        JPanel panelOpciones = new JPanel();
        JButton btnEditar = new JButton("Editar");
        JButton btnGuardar = new JButton("Guardar");

        btnEditar.addActionListener(e -> setEditable(true, txtNombre, txtApellidos, txtDNI, txtEmail, txtTelefono,
                txtProvincia, txtCiudad, txtDireccion, txtFechaNacimiento, txtFechaRegistro));
        panelOpciones.add(btnEditar);

        btnGuardar.addActionListener(e -> {
            btnGuardar.setEnabled(false);
            guardarDatosUsuario(usuario, txtNombre.getText(), txtApellidos.getText(), txtDNI.getText(),
                    txtEmail.getText(), txtTelefono.getText(), txtProvincia.getText(), txtCiudad.getText(),
                    txtDireccion.getText(), txtFechaNacimiento.getText(), txtFechaRegistro.getText());
            setEditable(false, txtNombre, txtApellidos, txtDNI, txtEmail, txtTelefono, txtProvincia, txtCiudad,
                    txtDireccion, txtFechaNacimiento, txtFechaRegistro);
        });
        verificarCampos(txtNombre, txtApellidos, txtDNI, txtEmail, txtTelefono, txtProvincia, txtCiudad,
                        txtDireccion, txtFechaNacimiento, txtFechaRegistro, btnGuardar);
        btnGuardar.setEnabled(false);
        panelOpciones.add(btnGuardar);
        add(panelOpciones, BorderLayout.SOUTH);

        DocumentListener validationListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                verificarCampos(txtNombre, txtApellidos, txtDNI, txtEmail, txtTelefono, txtProvincia, txtCiudad,
                        txtDireccion, txtFechaNacimiento, txtFechaRegistro, btnGuardar);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                verificarCampos(txtNombre, txtApellidos, txtDNI, txtEmail, txtTelefono, txtProvincia, txtCiudad,
                        txtDireccion, txtFechaNacimiento, txtFechaRegistro, btnGuardar);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                verificarCampos(txtNombre, txtApellidos, txtDNI, txtEmail, txtTelefono, txtProvincia, txtCiudad,
                        txtDireccion, txtFechaNacimiento, txtFechaRegistro, btnGuardar);
            }
        };

        agregarDocumentListeners(validationListener, txtNombre, txtApellidos, txtDNI, txtEmail, txtTelefono,
                txtProvincia, txtCiudad, txtDireccion, txtFechaNacimiento, txtFechaRegistro);
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

    // IAG: Optimizar y organizar
    private void verificarCampos(JTextField txtNombre, JTextField txtApellidos, JTextField txtDNI, JTextField txtEmail,
            JTextField txtTelefono, JTextField txtProvincia, JTextField txtCiudad, JTextField txtDireccion,
            JTextField txtFechaNacimiento, JTextField txtFechaRegistro, JButton btnGuardar) {
        boolean esValido = true;

        esValido &= validarCampo(txtNombre, "[a-zA-ZáéíóúÁÉÍÓÚñÑ]+");
        esValido &= validarCampo(txtApellidos, "[a-zA-ZáéíóúÁÉÍÓÚñÑ]+( [a-zA-ZáéíóúÁÉÍÓÚñÑ]+)*");
        esValido &= validarCampo(txtDNI, "\\d{8}[A-HJ-NP-TV-Z]");
        esValido &= validarCampo(txtEmail, "^[A-Za-z0-9+_.-]+@(.+)$");
        esValido &= validarCampo(txtTelefono, "\\+\\d{1,3} \\d{7,10}");
        esValido &= validarCampo(txtProvincia, "[a-zA-ZáéíóúÁÉÍÓÚñÑ]+");
        esValido &= validarCampo(txtCiudad, "[a-zA-ZáéíóúÁÉÍÓÚñÑ]+");
        esValido &= validarCampo(txtDireccion, "^[a-zA-Z0-9 ]+, \\d+$");
        esValido &= validarCampo(txtFechaNacimiento, "\\d{2}/\\d{2}/\\d{4}");
        esValido &= validarCampo(txtFechaRegistro, "\\d{2}/\\d{2}/\\d{4}");

        btnGuardar.setEnabled(esValido);
    }

    private boolean validarCampo(JTextField campo, String regex) {
        boolean esValido = !campo.getText().trim().isEmpty() && campo.getText().matches(regex);
        campo.setForeground(esValido ? Color.BLACK : Color.RED);
        return esValido;
    }

    // IAG
    private String[] obtenerDatosUsuario(String usuario) {
        try (BufferedReader br = new BufferedReader(new FileReader(RUTA_ARCHIVO_CSV))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos[0].equals(usuario)) {
                    return datos;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String[0];
    }

    // IAG
    private void guardarDatosUsuario(String usuario, String nombre, String apellidos, String dni, String email,
            String telefono, String provincia, String ciudad, String direccion, String fechaNacimiento,
            String fechaRegistro) {
        List<String[]> datos = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(RUTA_ARCHIVO_CSV))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datosLinea = linea.split(",");
                if (datosLinea[0].equals(usuario)) {
                    datosLinea[1] = nombre;
                    datosLinea[2] = apellidos;
                    datosLinea[3] = dni;
                    datosLinea[4] = email;
                    datosLinea[5] = telefono.split(" ")[0];
                    datosLinea[6] = telefono.split(" ")[1];
                    datosLinea[7] = provincia;
                    datosLinea[8] = ciudad;
                    datosLinea[9] = direccion.split(", ")[0];
                    datosLinea[10] = direccion.split(", ")[1];
                    datosLinea[11] = fechaNacimiento;
                    datosLinea[12] = fechaRegistro;
                }
                datos.add(datosLinea);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RUTA_ARCHIVO_CSV))) {
            for (String[] datosLinea : datos) {
                bw.write(String.join(",", datosLinea));
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
