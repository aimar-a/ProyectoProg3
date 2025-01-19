package gui.logIn;

import db.GestorBD;
import domain.datos.TipoDeDato;
import domain.perfil.CampoDatoUsuario;
import gui.ColorVariables;
import io.ConfigProperties;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

//IAG: ChatGPT y GitHub Copilot
//ADAPTADO: Ordenar y limpiar código, anadir funcionalidades y autocompeltado
public class DialogRegistro extends JDialog {

    private static final long serialVersionUID = 1L;
    @SuppressWarnings("unused")
	private static final Font FORMAT_INFO_FONT = new Font("Arial", Font.ITALIC, 10);

    public DialogRegistro(DialogLogIn parent) {
        super(parent, "Registrar Usuario", true);
        initComponents(parent);
    }

    private void initComponents(DialogLogIn parent) {
        JTextField txtUsuario = new JTextField();
        txtUsuario.setColumns(20);
        JTextField txtNombre = new JTextField();
        JTextField txtApellidos = new JTextField();
        JTextField txtDNI = new JTextField();
        JTextField txtEmail = new JTextField();
        JComboBox<String> comboPrefijo = new JComboBox<>(new String[] { "+34", "+1", "+44" });
        JTextField txtNumeroTelefono = new JTextField();
        JTextField txtProvincia = new JTextField();
        JTextField txtCiudad = new JTextField();
        JTextField txtCalle = new JTextField();
        txtCalle.setColumns(15);
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

        JPanel panelCentral = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JPanel panelOpciones = new JPanel();
        panelOpciones.add(btnCancelar);
        panelOpciones.add(btnAceptar);

        Map<Integer, CampoDatoUsuario> map = new HashMap<>();
        map.put(0, new CampoDatoUsuario("Usuario:", "",
                TipoDeDato.USUARIO,
                new JComponent[] { txtUsuario }, true));
        map.put(1, new CampoDatoUsuario("Nombre:", "",
                TipoDeDato.NOMBRE,
                new JComponent[] { txtNombre }, true));
        map.put(2, new CampoDatoUsuario("Apellidos:", "",
                TipoDeDato.APELLIDOS,
                new JComponent[] { txtApellidos }, true));
        map.put(3, new CampoDatoUsuario("DNI:", "",
                TipoDeDato.DNI,
                new JComponent[] { txtDNI }, true));
        map.put(4, new CampoDatoUsuario("Email:", "",
                TipoDeDato.MAIL,
                new JComponent[] { txtEmail }, true));
        map.put(5, new CampoDatoUsuario("Teléfono (Prefijo + Número):", "",
                TipoDeDato.TELEFONO,
                new JComponent[] { comboPrefijo, txtNumeroTelefono }, true));
        map.put(6, new CampoDatoUsuario("Provincia:", "",
                TipoDeDato.PROVINCIA,
                new JComponent[] { txtProvincia }, true));
        map.put(7, new CampoDatoUsuario("Ciudad:", "",
                TipoDeDato.CIUDAD,
                new JComponent[] { txtCiudad }, true));
        map.put(8, new CampoDatoUsuario("Dirección (Calle + Número):", "",
                TipoDeDato.DIRECCION,
                new JComponent[] { txtCalle, txtNumero }, true));
        map.put(9, new CampoDatoUsuario("Fecha de nacimiento (Día/Mes/Año):", "",
                TipoDeDato.FECHA_DE_NACIMIENTO,
                new JComponent[] { comboDia, comboMes, comboAno }, true));
        map.put(10, new CampoDatoUsuario("Contraseña:", "",
                TipoDeDato.CONTRASENA,
                new JComponent[] { txtContrasena }, true));

        agregarCampos(panelCentral, gbc, map);
        agregarListeners(map, btnAceptar);

        btnAceptar.setEnabled(validarTodo(map));

        btnCancelar.addActionListener(e -> dispose());
        btnAceptar.addActionListener(e -> registrarUsuario(map));

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.add(panelCentral, BorderLayout.CENTER);
        panelPrincipal.add(panelOpciones, BorderLayout.SOUTH);

        setContentPane(panelPrincipal);
        setSize(500, 700);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(parent);

        if (ConfigProperties.isUiDarkMode()) {
            panelPrincipal.setBackground(ColorVariables.COLOR_FONDO_DARK.getColor());
            panelCentral.setBackground(ColorVariables.COLOR_FONDO_DARK.getColor());
            map.values().stream().forEach(campoDato -> {
                Arrays.stream(campoDato.getComponentes())
                        .filter(JTextField.class::isInstance)
                        .forEach(c -> c.setBackground(ColorVariables.COLOR_BOTON_DARK.getColor()));
                campoDato.getInfoLabel().setForeground(ColorVariables.COLOR_BOTON_TEXTO_DARK.getColor());
            });
            btnAceptar.setBackground(ColorVariables.COLOR_FONDO_DARK.getColor());
            btnAceptar.setForeground(ColorVariables.COLOR_TEXTO_DARK.getColor());
            btnCancelar.setBackground(ColorVariables.COLOR_FONDO_DARK.getColor());
            btnCancelar.setForeground(ColorVariables.COLOR_TEXTO_DARK.getColor());
        } else {
            panelPrincipal.setBackground(ColorVariables.COLOR_FONDO_LIGHT.getColor());
            panelCentral.setBackground(ColorVariables.COLOR_FONDO_LIGHT.getColor());
            map.values().stream().forEach(campoDato -> {
                Arrays.stream(campoDato.getComponentes())
                        .filter(JTextField.class::isInstance)
                        .forEach(c -> c.setBackground(ColorVariables.COLOR_BOTON_LIGHT.getColor()));
                campoDato.getInfoLabel().setForeground(ColorVariables.COLOR_BOTON_TEXTO_LIGHT.getColor());
            });
            btnAceptar.setBackground(ColorVariables.COLOR_FONDO_LIGHT.getColor());
            btnAceptar.setForeground(ColorVariables.COLOR_TEXTO_LIGHT.getColor());
            btnCancelar.setBackground(ColorVariables.COLOR_FONDO_LIGHT.getColor());
            btnCancelar.setForeground(ColorVariables.COLOR_TEXTO_LIGHT.getColor());
        }
    }

    private void registrarUsuario(Map<Integer, CampoDatoUsuario> map) {
        String usuario = ((JTextField) map.get(0).getComponentes()[0]).getText().trim();
        String nombre = ((JTextField) map.get(1).getComponentes()[0]).getText().trim();
        String apellidos = ((JTextField) map.get(2).getComponentes()[0]).getText().trim();
        String dni = ((JTextField) map.get(3).getComponentes()[0]).getText().trim();
        String email = ((JTextField) map.get(4).getComponentes()[0]).getText().trim();
        String prefijo = ((JComboBox<?>) map.get(5).getComponentes()[0]).getSelectedItem().toString();
        String numero = ((JTextField) map.get(5).getComponentes()[1]).getText().trim();
        String provincia = ((JTextField) map.get(6).getComponentes()[0]).getText().trim();
        String ciudad = ((JTextField) map.get(7).getComponentes()[0]).getText().trim();
        String direccion = ((JTextField) map.get(8).getComponentes()[0]).getText().trim();
        String ndireccion = ((JTextField) map.get(8).getComponentes()[1]).getText().trim();
        String fechaNacimiento = TipoDeDato.formatFecha(
                ((JComboBox<?>) map.get(9).getComponentes()[0]).getSelectedItem().toString(),
                ((JComboBox<?>) map.get(9).getComponentes()[1]).getSelectedItem().toString(),
                ((JComboBox<?>) map.get(9).getComponentes()[2]).getSelectedItem().toString());
        String fechaRegistro = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String contrasena = new String(((JPasswordField) map.get(10).getComponentes()[0]).getPassword());

        if (usuario.isEmpty() || nombre.isEmpty() || apellidos.isEmpty() || dni.isEmpty() || email.isEmpty()
                || numero.isEmpty() || direccion.isEmpty() || ndireccion.isEmpty() || contrasena.isEmpty()) {
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

    private boolean validarTodo(Map<Integer, CampoDatoUsuario> map) {
        boolean todoCorrecto = true;
        for (CampoDatoUsuario campoDato : map.values()) {
            if (campoDato.isEditable() && !campoDato.validarCampo()) {
                todoCorrecto = false;
            }
        }
        return todoCorrecto;
    }

    private void agregarListeners(Map<Integer, CampoDatoUsuario> map, JButton btnGuardar) {
        for (CampoDatoUsuario campoDato : map.values()) {
            for (JComponent component : campoDato.getComponentes()) {
                if (component instanceof JTextField jTextField) {
                    jTextField.getDocument().addDocumentListener(new DocumentListener() {
                        @Override
                        public void insertUpdate(DocumentEvent e) {
                            btnGuardar.setEnabled(validarTodo(map));
                        }

                        @Override
                        public void removeUpdate(DocumentEvent e) {
                            btnGuardar.setEnabled(validarTodo(map));
                        }

                        @Override
                        public void changedUpdate(DocumentEvent e) {
                            btnGuardar.setEnabled(validarTodo(map));
                        }
                    });
                } else if (component instanceof JComboBox) {
                    ((JComboBox<?>) component).addActionListener(e -> btnGuardar.setEnabled(validarTodo(map)));
                }
            }
        }
    }

    private void agregarCampos(JPanel panelCampos, GridBagConstraints gbc,
            Map<Integer, CampoDatoUsuario> map) {
        map.forEach((y, campoDato) -> addField(panelCampos, gbc, campoDato.getTexto(),
                campoDato.getInfoLabel(), campoDato.getComponentes(), y));
    }

    private void addField(JPanel panel, GridBagConstraints gbc, String labelText, JLabel labelFormatInfo,
            JComponent[] components, int y) {
        gbc.gridx = 0;
        gbc.gridy = y * 2;
        panel.add(new JLabel(labelText), gbc);

        gbc.gridx = 1;
        switch (components.length) {
            case 1 -> panel.add(components[0], gbc);
            case 2 -> {
                JPanel panelCampos = new JPanel(new BorderLayout(5, 0));
                panelCampos.add(components[0], BorderLayout.WEST);
                panelCampos.add(components[1], BorderLayout.CENTER);
                panel.add(panelCampos, gbc);
            }
            case 3 -> {
                JPanel panelCampos = new JPanel(new GridLayout(1, 3, 5, 0));
                panelCampos.add(components[0]);
                panelCampos.add(components[1]);
                panelCampos.add(components[2]);
                panel.add(panelCampos, gbc);
            }
            default ->
                throw new IllegalArgumentException("Número de componentes no soportado");
        }

        gbc.gridy++;
        panel.add(labelFormatInfo, gbc);
    }

}
