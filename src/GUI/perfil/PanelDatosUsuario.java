package GUI.perfil;

import GUI.ColorVariables;
import datos.GestorBD;
import datos.TiposDeDatos;
import java.awt.*;
import java.util.stream.IntStream;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class PanelDatosUsuario extends JPanel {
	// IAG: Modificado (ChatGPT y GitHub Copilot)
    private static final long serialVersionUID = 1L;
    private static final Font FORMAT_INFO_FONT = new Font("Arial", Font.ITALIC, 10);
    private JTextField[] campos;
    private JLabel[] labelsFormatoInfo;
    private TiposDeDatos[] tipos;

    public PanelDatosUsuario(String usuario, boolean darkMode) {
        String[] datosUsuario = GestorBD.obtenerDatos(usuario);
        setLayout(new BorderLayout());

        JPanel panelCentral = new JPanel(new GridLayout(25, 2, 10, 10));
        add(panelCentral, BorderLayout.CENTER);

        JTextField txtUsuario = createTextField(usuario, false);
        JLabel lblFormInfoUsuario = createLabel("No se puede modificar el nombre de usuario");

        JTextField txtNombre = createTextField(datosUsuario[0], false);
        JLabel lblFormInfoNombre = createLabel();

        JTextField txtApellidos = createTextField(datosUsuario[1], false);
        JLabel lblFormInfoApellidos = createLabel();

        JTextField txtDNI = createTextField(datosUsuario[2], false);
        JLabel lblFormInfoDNI = createLabel();

        JTextField txtEmail = createTextField(datosUsuario[3], false);
        JLabel lblFormInfoEmail = createLabel();

        JComboBox<String> comboPrefijo = createComboBox(new String[] { "+34", "+1", "+44" },
                datosUsuario[4], false);
        JTextField txtNumeroTelefono = createTextField(datosUsuario[5], false);
        JLabel lblFormInfoTelefono = createLabel();

        JTextField txtProvincia = createTextField(datosUsuario[6], false);
        JLabel lblFormInfoProvincia = createLabel();

        JTextField txtCiudad = createTextField(datosUsuario[7], false);
        JLabel lblFormInfoCiudad = createLabel();

        JTextField txtCalle = createTextField(datosUsuario[8], false);
        JTextField txtNumeroCalle = createTextField(datosUsuario[9], false);
        JLabel lblFormInfoDireccion = createLabel();

        JComboBox<String> comboDiaNac = createComboBox(
                IntStream.rangeClosed(1, 31).mapToObj(String::valueOf).toArray(String[]::new),
                Integer.valueOf(datosUsuario[10].split("-")[0]).toString(), false);
        JComboBox<String> comboMesNac = createComboBox(
                IntStream.rangeClosed(1, 12).mapToObj(String::valueOf).toArray(String[]::new),
                Integer.valueOf(datosUsuario[10].split("-")[1]).toString(), false);
        JComboBox<String> comboAnoNac = createComboBox(
                IntStream.rangeClosed(1900, 2023).mapToObj(String::valueOf).toArray(String[]::new),
                datosUsuario[10].split("-")[2], false);
        JLabel lblFormInfoFechaNac = createLabel(
                TiposDeDatos.validarDato(TiposDeDatos.FECHA_DE_NACIMIENTO, TiposDeDatos.formatFecha(
                        comboDiaNac.getSelectedItem(), comboMesNac.getSelectedItem(), comboAnoNac.getSelectedItem())));

        JComboBox<String> comboDiaCre = createComboBox(
                IntStream.rangeClosed(1, 31).mapToObj(String::valueOf).toArray(String[]::new),
                Integer.valueOf(datosUsuario[11].split("-")[0]).toString(), false);
        JComboBox<String> comboMesCre = createComboBox(
                IntStream.rangeClosed(1, 12).mapToObj(String::valueOf).toArray(String[]::new),
                Integer.valueOf(datosUsuario[11].split("-")[1]).toString(), false);
        JComboBox<String> comboAnoCre = createComboBox(
                IntStream.rangeClosed(1900, 2030).mapToObj(String::valueOf).toArray(String[]::new),
                datosUsuario[11].split("-")[2], false);
        JLabel lblFormInfoFechaCre = createLabel("No se puede modificar la fecha de creación");

        campos = new JTextField[] { txtNombre, txtApellidos, txtDNI, txtEmail, txtNumeroTelefono, txtProvincia,
                txtCiudad, txtCalle, txtNumeroCalle };
        tipos = new TiposDeDatos[] { TiposDeDatos.NOMBRE, TiposDeDatos.APELLIDOS, TiposDeDatos.DNI, TiposDeDatos.MAIL,
                TiposDeDatos.TELEFONO, TiposDeDatos.PROVINCIA, TiposDeDatos.CIUDAD, TiposDeDatos.CALLE,
                TiposDeDatos.NUMERO };
        labelsFormatoInfo = new JLabel[] { lblFormInfoNombre, lblFormInfoApellidos, lblFormInfoDNI, lblFormInfoEmail,
                lblFormInfoTelefono, lblFormInfoProvincia, lblFormInfoCiudad, lblFormInfoDireccion,
                lblFormInfoDireccion };

        agregarCampos(panelCentral, txtUsuario, lblFormInfoUsuario, labelsFormatoInfo, comboPrefijo, txtNumeroTelefono,
                txtNombre, txtApellidos, txtDNI, txtEmail, txtProvincia, txtCiudad, txtCalle, txtNumeroCalle,
                comboDiaNac, comboMesNac, comboAnoNac, lblFormInfoFechaNac, comboDiaCre, comboMesCre, comboAnoCre,
                lblFormInfoFechaCre);

        JPanel panelOpciones = new JPanel();
        JButton btnEditar = new JButton("Editar");
        JButton btnGuardar = new JButton("Guardar");

        btnEditar.addActionListener(
                e -> setEditable(true, txtNombre, txtApellidos, txtDNI, txtEmail, comboPrefijo, txtNumeroTelefono,
                        txtProvincia, txtCiudad, txtCalle, txtNumeroCalle, comboDiaNac, comboMesNac, comboAnoNac));
        panelOpciones.add(btnEditar);

        btnGuardar.addActionListener(e -> {
            btnGuardar.setEnabled(false);
            String[] datosNuevos = {
                    txtNombre.getText(), txtApellidos.getText(), txtDNI.getText(), txtEmail.getText(),
                    comboPrefijo.getSelectedItem().toString(), txtNumeroTelefono.getText(),
                    txtProvincia.getText(), txtCiudad.getText(),
                    txtCalle.getText(), txtNumeroCalle.getText(),
                    TiposDeDatos.formatFecha(comboDiaNac.getSelectedItem(), comboMesNac.getSelectedItem(),
                            comboAnoNac.getSelectedItem())
            };
            if (GestorBD.cambiarDatos(usuario, datosNuevos)) {
                JOptionPane.showMessageDialog(this, "Datos actualizados correctamente", "Información",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar los datos", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
            setEditable(false, txtNombre, txtApellidos, txtDNI, txtEmail, comboPrefijo, txtNumeroTelefono, txtProvincia,
                    txtCiudad, txtCalle, txtNumeroCalle, comboDiaNac, comboMesNac, comboAnoNac);
        });

        btnGuardar.setEnabled(false);
        panelOpciones.add(btnGuardar);
        add(panelOpciones, BorderLayout.SOUTH);

        DocumentListener validationListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                btnGuardar.setEnabled(TiposDeDatos.comprobarCamposYInfo(campos, labelsFormatoInfo, tipos));
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                btnGuardar.setEnabled(TiposDeDatos.comprobarCamposYInfo(campos, labelsFormatoInfo, tipos));
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                btnGuardar.setEnabled(TiposDeDatos.comprobarCamposYInfo(campos, labelsFormatoInfo, tipos));
            }
        };

        comboPrefijo.addActionListener(e -> validationListener.changedUpdate(null));
        comboDiaNac.addActionListener(e -> validationListener.changedUpdate(null));
        comboMesNac.addActionListener(e -> validationListener.changedUpdate(null));
        comboAnoNac.addActionListener(e -> validationListener.changedUpdate(null));

        agregarDocumentListeners(validationListener, campos);

        if (darkMode) {
            setBackground(ColorVariables.COLOR_FONDO_DARK);
            panelCentral.setBackground(ColorVariables.COLOR_FONDO_DARK);
            panelOpciones.setBackground(ColorVariables.COLOR_FONDO_DARK);
            btnEditar.setBackground(ColorVariables.COLOR_BOTON_DARK);
            btnEditar.setForeground(ColorVariables.COLOR_TEXTO_DARK);
            btnGuardar.setBackground(ColorVariables.COLOR_BOTON_DARK);
            btnGuardar.setForeground(ColorVariables.COLOR_TEXTO_DARK);
            IntStream.range(0, labelsFormatoInfo.length)
                    .forEach(i -> labelsFormatoInfo[i].setForeground(ColorVariables.COLOR_TEXTO_DARK));
            setLabelColors(panelCentral, ColorVariables.COLOR_TEXTO_DARK);
        } else {
            setBackground(ColorVariables.COLOR_FONDO_LIGHT);
            panelCentral.setBackground(ColorVariables.COLOR_FONDO_LIGHT);
            panelOpciones.setBackground(ColorVariables.COLOR_FONDO_LIGHT);
        }
    }

    private void setLabelColors(Container container, Color color) {
        for (Component component : container.getComponents()) {
            if (component instanceof JLabel) {
                component.setForeground(color);
            } else if (component instanceof Container) {
                setLabelColors((Container) component, color);
            }
        }
    }

    private JTextField createTextField(String text, boolean editable) {
        JTextField textField = new JTextField(text);
        textField.setEditable(editable);
        return textField;
    }

    private JLabel createLabel() {
        JLabel label = new JLabel();
        label.setFont(FORMAT_INFO_FONT);
        return label;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(FORMAT_INFO_FONT);
        return label;
    }

    private JComboBox<String> createComboBox(String[] items, String selectedItem, boolean enabled) {
        JComboBox<String> comboBox = new JComboBox<>(items);
        comboBox.setSelectedItem(selectedItem);
        comboBox.setEnabled(enabled);
        return comboBox;
    }

    private void setEditable(boolean editable, JComponent... camposTexto) {
        for (JComponent campoTexto : camposTexto) {
            if (campoTexto instanceof JTextField) {
                ((JTextField) campoTexto).setEditable(editable);
            } else if (campoTexto instanceof JComboBox) {
                ((JComboBox<?>) campoTexto).setEnabled(editable);
            }
        }
    }

    private void agregarDocumentListeners(DocumentListener listener, JComponent... camposTexto) {
        for (JComponent campoTexto : camposTexto) {
            if (campoTexto instanceof JTextField) {
                ((JTextField) campoTexto).getDocument().addDocumentListener(listener);
            } else if (campoTexto instanceof JComboBox) {
                ((JComboBox<?>) campoTexto).addActionListener(e -> listener.changedUpdate(null));
            }
        }
    }

    private void agregarCampos(JPanel panelCampos, JTextField txtUsuario, JLabel lblInfoFormatUsuario,
            JLabel[] labelsFormatoInfo, JComboBox<String> comboPrefijo, JTextField txtNumeroTelefono,
            JTextField txtNombre, JTextField txtApellidos, JTextField txtDNI, JTextField txtEmail,
            JTextField txtProvincia, JTextField txtCiudad, JTextField txtCalle, JTextField txtNumero,
            JComboBox<String> comboDia, JComboBox<String> comboMes, JComboBox<String> comboAno,
            JLabel jlabelInfoFormatFechaNacimiento, JComboBox<String> comboDiaCre, JComboBox<String> comboMesCre,
            JComboBox<String> comboAnoCre, JLabel jlabelInfoFormatFechaCre) {
        addField(panelCampos, "Usuario:", txtUsuario, lblInfoFormatUsuario);
        addField(panelCampos, "Nombre:", txtNombre, labelsFormatoInfo[0]);
        addField(panelCampos, "Apellidos:", txtApellidos, labelsFormatoInfo[1]);
        addField(panelCampos, "DNI:", txtDNI, labelsFormatoInfo[2]);
        addField(panelCampos, "Email:", txtEmail, labelsFormatoInfo[3]);
        addFieldWithPanel(panelCampos, "Teléfono (Prefijo + Número):", comboPrefijo, txtNumeroTelefono,
                labelsFormatoInfo[4]);
        addField(panelCampos, "Provincia:", txtProvincia, labelsFormatoInfo[5]);
        addField(panelCampos, "Ciudad:", txtCiudad, labelsFormatoInfo[6]);
        txtCalle.setColumns(20);
        addFieldWithPanel(panelCampos, "Dirección (Calle + Número):", txtCalle, txtNumero, labelsFormatoInfo[7]);
        comboDia.setPreferredSize(new Dimension(50, 20));
        comboMes.setPreferredSize(new Dimension(50, 20));
        comboAno.setPreferredSize(new Dimension(70, 20));
        addFieldWithPanel3(panelCampos, "Fecha de nacimiento (Día/Mes/Año):", comboDia, comboMes, comboAno,
                jlabelInfoFormatFechaNacimiento);
        comboDiaCre.setPreferredSize(new Dimension(50, 20));
        comboMesCre.setPreferredSize(new Dimension(50, 20));
        comboAnoCre.setPreferredSize(new Dimension(70, 20));
        addFieldWithPanel3(panelCampos, "Fecha de creación (Día/Mes/Año):", comboDiaCre, comboMesCre, comboAnoCre,
                jlabelInfoFormatFechaCre);
    }

    private void addField(JPanel panel, String labelText, JTextField textField, JLabel infoLabel) {
        panel.add(new JLabel(labelText));
        panel.add(textField);
        panel.add(new JLabel());
        panel.add(infoLabel);
        infoLabel.setVerticalAlignment(SwingConstants.TOP);
    }

    private void addFieldWithPanel(JPanel panel, String labelText, JComponent component1, JComponent component2,
            JLabel infoLabel) {
        panel.add(new JLabel(labelText));
        JPanel subPanel = new JPanel(new BorderLayout());
        subPanel.add(component1, BorderLayout.WEST);
        subPanel.add(component2, BorderLayout.CENTER);
        panel.add(subPanel);
        panel.add(new JLabel());
        panel.add(infoLabel);
        infoLabel.setVerticalAlignment(SwingConstants.TOP);
    }

    private void addFieldWithPanel3(JPanel panel, String labelText, JComponent component1, JComponent component2,
            JComponent component3, JLabel infoLabel) {
        panel.add(new JLabel(labelText));
        JPanel subPanel = new JPanel(new BorderLayout());
        subPanel.add(component1, BorderLayout.WEST);
        subPanel.add(component2, BorderLayout.CENTER);
        subPanel.add(component3, BorderLayout.EAST);
        panel.add(subPanel);
        panel.add(new JLabel());
        panel.add(infoLabel);
        infoLabel.setVerticalAlignment(SwingConstants.TOP);
    }
}
