package GUI.logIn;

import GUI.ColorVariables;
import datos.GestorBD;
import datos.TiposDeDatos;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.stream.IntStream;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class FrameRegistro extends JDialog {

        private static final long serialVersionUID = 1L;
        private JTextField[] campos;
        private TiposDeDatos[] tipos;
        private JLabel[] labelsFormatoInfo;
        private static final Font FORMAT_INFO_FONT = new Font("Arial", Font.ITALIC, 10);

        public FrameRegistro(FrameLogIn parent, boolean darkMode) {
                super(parent, "Registrar Usuario", true);
                initComponents(parent, darkMode);
        }

        private void initComponents(FrameLogIn parent, boolean darkMode) {
                JTextField txtUsuario = new JTextField();
                JTextField txtNombre = new JTextField();
                JTextField txtApellidos = new JTextField();
                JTextField txtDNI = new JTextField();
                JTextField txtEmail = new JTextField();
                JComboBox<String> comboPrefijo = new JComboBox<>(new String[] { "+34", "+1", "+44" });
                JTextField txtNumeroTelefono = new JTextField();
                JTextField txtProvincia = new JTextField();
                JTextField txtCiudad = new JTextField();
                JTextField txtCalle = new JTextField();
                JTextField txtNumero = new JTextField();
                JComboBox<String> comboDia = new JComboBox<>(
                                IntStream.rangeClosed(1, 31).mapToObj(String::valueOf).toArray(String[]::new));
                JComboBox<String> comboMes = new JComboBox<>(
                                IntStream.rangeClosed(1, 12).mapToObj(String::valueOf).toArray(String[]::new));
                JComboBox<String> comboAno = new JComboBox<>(
                                IntStream.rangeClosed(1900, 2024).mapToObj(String::valueOf).toArray(String[]::new));
                comboAno.setSelectedItem("2024");
                JPasswordField txtContrasena = new JPasswordField();
                JButton btnAceptar = new JButton("Aceptar");
                JButton btnCancelar = new JButton("Cancelar");
                btnAceptar.setEnabled(false);

                campos = new JTextField[] { txtUsuario, txtNombre, txtApellidos, txtDNI, txtEmail, txtNumeroTelefono,
                                txtProvincia, txtCiudad, txtCalle, txtNumero, txtContrasena };
                tipos = new TiposDeDatos[] { TiposDeDatos.USUARIO, TiposDeDatos.NOMBRE, TiposDeDatos.APELLIDOS,
                                TiposDeDatos.DNI, TiposDeDatos.MAIL, TiposDeDatos.TELEFONO, TiposDeDatos.PROVINCIA,
                                TiposDeDatos.CIUDAD, TiposDeDatos.CALLE, TiposDeDatos.NUMERO, TiposDeDatos.CONTRASENA };
                JLabel jlabelInfoFormatDireccion = new JLabel();
                labelsFormatoInfo = new JLabel[] { new JLabel(), new JLabel(), new JLabel(), new JLabel(), new JLabel(),
                                new JLabel(), new JLabel(), new JLabel(), jlabelInfoFormatDireccion,
                                jlabelInfoFormatDireccion,
                                new JLabel() };
                JLabel jlabelInfoFormatFechaNacimiento = new JLabel(TiposDeDatos
                                .validarDato(TiposDeDatos.FECHA_DE_NACIMIENTO,
                                                TiposDeDatos.formatFecha(comboDia.getSelectedItem(),
                                                                comboMes.getSelectedItem(),
                                                                comboAno.getSelectedItem())));

                Arrays.stream(labelsFormatoInfo).forEach(label -> label.setFont(FORMAT_INFO_FONT));
                jlabelInfoFormatFechaNacimiento.setFont(FORMAT_INFO_FONT);
                Arrays.stream(labelsFormatoInfo).forEach(label -> label.setVerticalAlignment(SwingConstants.TOP));
                jlabelInfoFormatFechaNacimiento.setVerticalAlignment(SwingConstants.TOP);

                btnAceptar.setEnabled(TiposDeDatos.comprobarCamposYInfo(campos, labelsFormatoInfo, tipos));
                Arrays.stream(campos).forEach(campo -> campo.getDocument().addDocumentListener(new DocumentListener() {
                        @Override
                        public void insertUpdate(DocumentEvent e) {
                                btnAceptar.setEnabled(
                                                TiposDeDatos.comprobarCamposYInfo(campos, labelsFormatoInfo, tipos));
                        }

                        @Override
                        public void removeUpdate(DocumentEvent e) {
                                btnAceptar.setEnabled(
                                                TiposDeDatos.comprobarCamposYInfo(campos, labelsFormatoInfo, tipos));
                        }

                        @Override
                        public void changedUpdate(DocumentEvent e) {
                                btnAceptar.setEnabled(
                                                TiposDeDatos.comprobarCamposYInfo(campos, labelsFormatoInfo, tipos));
                        }
                }));

                comboAno.addActionListener(e -> updateFechaNacimientoLabel(comboDia, comboMes, comboAno,
                                jlabelInfoFormatFechaNacimiento));
                comboMes.addActionListener(e -> updateFechaNacimientoLabel(comboDia, comboMes, comboAno,
                                jlabelInfoFormatFechaNacimiento));
                comboDia.addActionListener(e -> updateFechaNacimientoLabel(comboDia, comboMes, comboAno,
                                jlabelInfoFormatFechaNacimiento));

                JPanel panelCampos = new JPanel(new GridLayout(23, 2, 10, 5));
                agregarCampos(panelCampos, txtUsuario, labelsFormatoInfo, comboPrefijo, txtNumeroTelefono, txtNombre,
                                txtApellidos, txtDNI, txtEmail, txtProvincia, txtCiudad, txtCalle, txtNumero, comboDia,
                                comboMes, comboAno, txtContrasena, jlabelInfoFormatFechaNacimiento);

                panelCampos.add(btnCancelar);
                panelCampos.add(btnAceptar);

                btnCancelar.addActionListener(e -> dispose());
                btnAceptar.addActionListener(e -> registrarUsuario(txtUsuario, txtNombre, txtApellidos, txtDNI,
                                txtEmail, comboPrefijo, txtNumeroTelefono, txtCalle, txtNumero, comboDia, comboMes,
                                comboAno, txtContrasena, txtProvincia, txtCiudad));

                JPanel panelPrincipal = new JPanel();
                panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                panelPrincipal.setLayout(new BorderLayout());
                panelPrincipal.add(panelCampos, BorderLayout.CENTER);

                setContentPane(panelPrincipal);
                setSize(600, 700);
                setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                setLocationRelativeTo(parent);

                if (darkMode) {
                        panelPrincipal.setBackground(ColorVariables.COLOR_FONDO_DARK);
                        panelCampos.setBackground(ColorVariables.COLOR_FONDO_DARK);
                        Arrays.stream(campos)
                                        .forEach(campo -> campo.setBackground(ColorVariables.COLOR_FONDO_DARK));
                        Arrays.stream(labelsFormatoInfo)
                                        .forEach(label -> label.setForeground(ColorVariables.COLOR_TEXTO_DARK));
                        Arrays.stream(panelCampos.getComponents()).filter(c -> c instanceof JLabel)
                                        .forEach(c -> c.setForeground(ColorVariables.COLOR_TEXTO_DARK));
                        btnAceptar.setBackground(ColorVariables.COLOR_FONDO_DARK);
                        btnAceptar.setForeground(ColorVariables.COLOR_TEXTO_DARK);
                        btnCancelar.setBackground(ColorVariables.COLOR_FONDO_DARK);
                        btnCancelar.setForeground(ColorVariables.COLOR_TEXTO_DARK);
                } else {
                        panelPrincipal.setBackground(ColorVariables.COLOR_FONDO_LIGHT);
                        panelCampos.setBackground(ColorVariables.COLOR_FONDO_LIGHT);
                        Arrays.stream(campos)
                                        .forEach(campo -> campo.setBackground(ColorVariables.COLOR_FONDO_LIGHT));
                        Arrays.stream(labelsFormatoInfo)
                                        .forEach(label -> label.setForeground(ColorVariables.COLOR_TEXTO_LIGHT));
                        Arrays.stream(panelCampos.getComponents()).filter(c -> c instanceof JLabel)
                                        .forEach(c -> c.setForeground(ColorVariables.COLOR_TEXTO_LIGHT));
                        btnAceptar.setBackground(ColorVariables.COLOR_FONDO_LIGHT);
                        btnAceptar.setForeground(ColorVariables.COLOR_TEXTO_LIGHT);
                        btnCancelar.setBackground(ColorVariables.COLOR_FONDO_LIGHT);
                        btnCancelar.setForeground(ColorVariables.COLOR_TEXTO_LIGHT);
                }
        }

        private void updateFechaNacimientoLabel(JComboBox<String> comboDia, JComboBox<String> comboMes,
                        JComboBox<String> comboAno, JLabel jlabelInfoFormatFechaNacimiento) {
                jlabelInfoFormatFechaNacimiento.setText(TiposDeDatos.validarDato(TiposDeDatos.FECHA_DE_NACIMIENTO,
                                TiposDeDatos.formatFecha(comboDia.getSelectedItem(), comboMes.getSelectedItem(),
                                                comboAno.getSelectedItem())));
        }

        private void agregarCampos(JPanel panelCampos, JTextField txtUsuario, JLabel[] labelsFormatoInfo,
                        JComboBox<String> comboPrefijo, JTextField txtNumeroTelefono, JTextField txtNombre,
                        JTextField txtApellidos, JTextField txtDNI, JTextField txtEmail, JTextField txtProvincia,
                        JTextField txtCiudad, JTextField txtCalle, JTextField txtNumero, JComboBox<String> comboDia,
                        JComboBox<String> comboMes, JComboBox<String> comboAno, JPasswordField txtContrasena,
                        JLabel jlabelInfoFormatFechaNacimiento) {
                addField(panelCampos, "Usuario:", txtUsuario, labelsFormatoInfo[0]);
                addField(panelCampos, "Nombre:", txtNombre, labelsFormatoInfo[1]);
                addField(panelCampos, "Apellidos:", txtApellidos, labelsFormatoInfo[2]);
                addField(panelCampos, "DNI:", txtDNI, labelsFormatoInfo[3]);
                addField(panelCampos, "Email:", txtEmail, labelsFormatoInfo[4]);
                addFieldWithPanel(panelCampos, "Teléfono (Prefijo + Número):", comboPrefijo, txtNumeroTelefono,
                                labelsFormatoInfo[5]);
                addField(panelCampos, "Provincia:", txtProvincia, labelsFormatoInfo[6]);
                addField(panelCampos, "Ciudad:", txtCiudad, labelsFormatoInfo[7]);
                txtCalle.setColumns(20);
                addFieldWithPanel(panelCampos, "Dirección (Calle + Número):", txtCalle, txtNumero,
                                labelsFormatoInfo[8]);
                comboDia.setPreferredSize(new Dimension(50, 20));
                comboMes.setPreferredSize(new Dimension(50, 20));
                comboAno.setPreferredSize(new Dimension(70, 20));
                addFieldWithPanel3(panelCampos, "Fecha de nacimiento (Día/Mes/Año):", comboDia, comboMes, comboAno,
                                jlabelInfoFormatFechaNacimiento);
                addField(panelCampos, "Contraseña:", txtContrasena, labelsFormatoInfo[10]);
        }

        private void addField(JPanel panel, String labelText, JTextField textField, JLabel infoLabel) {
                panel.add(new JLabel(labelText));
                panel.add(textField);
                panel.add(new JLabel());
                panel.add(infoLabel);
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
        }

        private void registrarUsuario(JTextField txtUsuario, JTextField txtNombre, JTextField txtApellidos,
                        JTextField txtDNI, JTextField txtEmail, JComboBox<String> comboPrefijo,
                        JTextField txtNumeroTelefono, JTextField txtCalle, JTextField txtNumero,
                        JComboBox<String> comboDia, JComboBox<String> comboMes, JComboBox<String> comboAno,
                        JPasswordField txtContrasena, JTextField txtProvincia, JTextField txtCiudad) {
                if (!TiposDeDatos.comprobarCamposYInfo(campos, labelsFormatoInfo, tipos) || TiposDeDatos.validarDato(
                                TiposDeDatos.FECHA_DE_NACIMIENTO,
                                TiposDeDatos.formatFecha(comboDia.getSelectedItem(), comboMes.getSelectedItem(),
                                                comboAno.getSelectedItem())) != null) {
                        JOptionPane.showMessageDialog(this, "Por favor, revise los campos con errores.", "Error",
                                        JOptionPane.ERROR_MESSAGE);
                        return;
                }

                String usuario = txtUsuario.getText().trim();
                String nombre = txtNombre.getText().trim();
                String apellidos = txtApellidos.getText().trim();
                String dni = txtDNI.getText().trim();
                String email = txtEmail.getText().trim();
                String prefijo = comboPrefijo.getSelectedItem().toString();
                String numero = txtNumeroTelefono.getText().trim();
                String provincia = txtProvincia.getText().trim();
                String ciudad = txtCiudad.getText().trim();
                String direccion = txtCalle.getText().trim();
                String ndireccion = txtNumero.getText().trim();
                String fechaNacimiento = TiposDeDatos.formatFecha(comboDia.getSelectedItem(),
                                comboMes.getSelectedItem(),
                                comboAno.getSelectedItem());
                String fechaRegistro = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                String contrasena = new String(txtContrasena.getPassword());

                if (usuario.isEmpty() || nombre.isEmpty() || apellidos.isEmpty() || dni.isEmpty() || email.isEmpty()
                                || txtNumeroTelefono.getText().trim().isEmpty() || txtCalle.getText().trim().isEmpty()
                                || txtNumero.getText().trim().isEmpty() || contrasena.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos obligatorios.",
                                        "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                }

                int fichasBienvenida = GestorBD.agregarUsuario(usuario, contrasena,
                                new String[] { nombre, apellidos, dni, email, prefijo, numero, provincia, ciudad,
                                                direccion, ndireccion,
                                                fechaNacimiento, fechaRegistro });

                if (fichasBienvenida > 0) {
                        JOptionPane.showMessageDialog(this,
                                        "Felicidades, has obtenido " + fichasBienvenida
                                                        + " fichas como premio de bienvenida. ¡Bienvenido a la familia!",
                                        "Éxito",
                                        JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                } else {
                        JOptionPane.showMessageDialog(this,
                                        "Error al registrar el usuario. Es posible que el usuario ya exista.", "Error",
                                        JOptionPane.ERROR_MESSAGE);
                }
        }

}
