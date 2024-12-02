package GUI.logIn;

import GUI.mainMenu.FrameMenuPrincipal;
import GUI.mainMenu.JuegosDisponibles;
import datos.GestorUsuarios;
import datos.TiposDeDatos;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

// IAG: Convertir esta clase de JFrame a JDialog para que simpere este por encima de FrameMenuPrincipal
public class FrameLogIn extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JTextField usuarioField;
    private final JPasswordField passwordField;
    private final JButton botonLogin;
    private final JButton botonRegistro;
    private final FrameMenuPrincipal menuPrincipal;
    private final JuegosDisponibles juegoObjetivo;

    public FrameLogIn(FrameMenuPrincipal menuPrinc, JuegosDisponibles juegoObjetivo) {
        super(menuPrinc, "Login - 007Games", true); // Hacemos el JDialog modal
        this.menuPrincipal = menuPrinc;
        this.juegoObjetivo = juegoObjetivo;

        int ancho_labels = 120;
        int ancho_fields = 200;
        int alto = 40;
        int espacio = 15;
        int altoimg = 335;
        int alto_boton = 50;
        int ancho_boton = 110;

        int linea = 0;
        int columna = 0;

        JPanel panel = new JPanel();
        panel.setLayout(null);
        linea += espacio;
        columna += espacio;

        JLabel img = new JLabel();
        ImageIcon iconoLogin = new ImageIcon(getClass().getResource("/img/logIn/foto.png"));
        Image scaledImagen = iconoLogin.getImage().getScaledInstance(altoimg, altoimg, Image.SCALE_SMOOTH);
        ImageIcon scaledIcono = new ImageIcon(scaledImagen);
        img.setIcon(scaledIcono);
        img.setBounds(columna, linea, ancho_labels + ancho_fields + espacio, altoimg);
        panel.add(img);
        linea += altoimg + espacio;

        JLabel usuarioLabel = new JLabel("Usuario:");
        usuarioLabel.setBounds(columna, linea, ancho_labels, alto);
        columna += ancho_labels + espacio;
        panel.add(usuarioLabel);
        usuarioField = new JTextField();
        usuarioField.setBounds(columna, linea, ancho_fields, alto);
        linea += alto + espacio;
        columna = espacio;
        panel.add(usuarioField);

        JLabel passwordLabel = new JLabel("Contraseña:");
        passwordLabel.setBounds(columna, linea, ancho_labels, alto);
        panel.add(passwordLabel);
        columna += ancho_labels + espacio;
        passwordField = new JPasswordField();
        passwordField.setBounds(columna, linea, ancho_fields, alto);
        linea += alto + espacio * 2;
        columna = espacio;
        panel.add(passwordField);

        botonRegistro = new JButton("Registrar");
        botonRegistro.setBounds(columna, linea, ancho_boton, alto_boton);
        panel.add(botonRegistro);
        columna += ancho_boton + espacio * 8;
        botonLogin = new JButton("Login");

        botonLogin.setBounds(columna, linea, ancho_boton, alto_boton);
        panel.add(botonLogin);
        linea += alto_boton + espacio;

        setSize(ancho_labels + ancho_fields + espacio * 3 + 20, linea + 40);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(menuPrinc); // Ubica la ventana en relación con el FrameMenuPrincipal
        setResizable(false);

        add(panel);

        botonLogin.addActionListener((ActionEvent e) -> {
            realizarLogin();
        });

        botonRegistro.addActionListener((ActionEvent e) -> {
            registrarUsuario();
        });

        usuarioField.addActionListener((ActionEvent e) -> {
            realizarLogin();
        });

        passwordField.addActionListener((ActionEvent e) -> {
            realizarLogin();
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                menuPrinc.loginAbierto = false;
            }
        });
    }

    public void realizarLogin() {
        String usuario = usuarioField.getText();
        String password = new String(passwordField.getPassword());

        if (password.equals(GestorUsuarios.obtenerContrasena(usuario))) {
            JOptionPane.showMessageDialog(this, "Login exitoso.");
            menuPrincipal.logeado = true; // Cambia el estado a true en MenuPrincipal
            menuPrincipal.usuario = usuario; // Guarda el usuario en MenuPrincipal
            menuPrincipal.actualizarEstado(); // Llama al método para actualizar la interfaz

            dispose(); // Cierra la ventana de login
            if (juegoObjetivo != null) {
                menuPrincipal.abrirVentana(juegoObjetivo);
            }
        }

        else {
            JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void registrarUsuario() {
        // Definir los campos de entrada
        JTextField txtUsuario = new JTextField();
        JTextField txtNombre = new JTextField();
        JTextField txtApellidos = new JTextField();
        JTextField txtDNI = new JTextField();
        JTextField txtEmail = new JTextField();
        JTextField txtTelefono = new JTextField("+34 ");
        JTextField txtProvincia = new JTextField();
        JTextField txtCiudad = new JTextField();
        JTextField txtDireccion = new JTextField("Calle, Número");
        JTextField txtFechaNacimiento = new JTextField();
        JPasswordField txtContrasena = new JPasswordField();

        JButton btnAceptar = new JButton("Aceptar");
        JButton btnCancelar = new JButton("Cancelar");
        btnAceptar.setEnabled(false);

        JLabel labelFormatoInfoUsuario = new JLabel();
        JLabel labelFormatoInfoNombre = new JLabel();
        JLabel labelFormatoInfoApellidos = new JLabel();
        JLabel labelFormatoInfoDNI = new JLabel();
        JLabel labelFormatoInfoEmail = new JLabel();
        JLabel labelFormatoInfoTelefono = new JLabel();
        JLabel labelFormatoInfoProvincia = new JLabel();
        JLabel labelFormatoInfoCiudad = new JLabel();
        JLabel labelFormatoInfoDireccion = new JLabel();
        JLabel labelFormatoInfoFechaNacimiento = new JLabel();
        JLabel labelFormatoInfoContrasena = new JLabel();

        JTextField[] camposTexto = { txtNombre, txtApellidos, txtDNI, txtEmail, txtTelefono, txtProvincia, txtCiudad,
                txtDireccion, txtFechaNacimiento, txtUsuario };
        TiposDeDatos[] tiposDatos = { TiposDeDatos.NOMBRE, TiposDeDatos.APELLIDOS, TiposDeDatos.DNI, TiposDeDatos.MAIL,
                TiposDeDatos.TELEFONO, TiposDeDatos.PROVINCIA, TiposDeDatos.CIUDAD, TiposDeDatos.DIRECCION,
                TiposDeDatos.FECHA_DE_NACIMIENTO, TiposDeDatos.USUARIO };
        JLabel[] labelsFormatoInfo = { labelFormatoInfoNombre, labelFormatoInfoApellidos, labelFormatoInfoDNI,
                labelFormatoInfoEmail, labelFormatoInfoTelefono, labelFormatoInfoProvincia, labelFormatoInfoCiudad,
                labelFormatoInfoDireccion, labelFormatoInfoFechaNacimiento, labelFormatoInfoUsuario };

        // Crear un panel con los campos
        JPanel panelCampos = new JPanel(new GridLayout(23, 2, 10, 10));
        panelCampos.add(new JLabel("Usuario:"));
        panelCampos.add(txtUsuario);
        panelCampos.add(new JLabel());
        panelCampos.add(labelFormatoInfoUsuario);

        panelCampos.add(new JLabel("Nombre:"));
        panelCampos.add(txtNombre);
        panelCampos.add(new JLabel());
        panelCampos.add(labelFormatoInfoNombre);

        panelCampos.add(new JLabel("Apellidos:"));
        panelCampos.add(txtApellidos);
        panelCampos.add(new JLabel());
        panelCampos.add(labelFormatoInfoApellidos);

        panelCampos.add(new JLabel("DNI:"));
        panelCampos.add(txtDNI);
        panelCampos.add(new JLabel());
        panelCampos.add(labelFormatoInfoDNI);

        panelCampos.add(new JLabel("Email:"));
        panelCampos.add(txtEmail);
        panelCampos.add(new JLabel());
        panelCampos.add(labelFormatoInfoEmail);

        panelCampos.add(new JLabel("Teléfono:"));
        panelCampos.add(txtTelefono);
        panelCampos.add(new JLabel());
        panelCampos.add(labelFormatoInfoTelefono);

        panelCampos.add(new JLabel("Provincia:"));
        panelCampos.add(txtProvincia);
        panelCampos.add(new JLabel());
        panelCampos.add(labelFormatoInfoProvincia);

        panelCampos.add(new JLabel("Ciudad:"));
        panelCampos.add(txtCiudad);
        panelCampos.add(new JLabel());
        panelCampos.add(labelFormatoInfoCiudad);

        panelCampos.add(new JLabel("Dirección:"));
        panelCampos.add(txtDireccion);
        panelCampos.add(new JLabel());
        panelCampos.add(labelFormatoInfoDireccion);

        panelCampos.add(new JLabel("Fecha de nacimiento:"));
        panelCampos.add(txtFechaNacimiento);
        panelCampos.add(new JLabel());
        panelCampos.add(labelFormatoInfoFechaNacimiento);

        panelCampos.add(new JLabel("Contraseña:"));
        panelCampos.add(txtContrasena);
        panelCampos.add(new JLabel());
        panelCampos.add(labelFormatoInfoContrasena);

        panelCampos.add(btnCancelar);
        panelCampos.add(btnAceptar);

        TiposDeDatos.comprobarCamposYInfo(camposTexto, labelsFormatoInfo, tiposDatos);

        for (JTextField campo : camposTexto) {
            // IAG
            campo.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    TiposDeDatos.comprobarCamposYInfo(camposTexto, labelsFormatoInfo, tiposDatos);
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    TiposDeDatos.comprobarCamposYInfo(camposTexto, labelsFormatoInfo, tiposDatos);
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    TiposDeDatos.comprobarCamposYInfo(camposTexto, labelsFormatoInfo, tiposDatos);
                }
            });
        }

        // Crear un panel principal con borde
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelPrincipal.setLayout(new BorderLayout());
        panelPrincipal.add(panelCampos, BorderLayout.CENTER);

        JDialog dialog = new JDialog(this, "Registrar Usuario", true);

        btnCancelar.addActionListener(e -> dialog.dispose());
        btnAceptar.addActionListener(e -> {
            btnAceptar.setEnabled(false);
            // Obtener los datos ingresados
            String usuario = txtUsuario.getText();
            String nombre = txtNombre.getText();
            String apellidos = txtApellidos.getText();
            String dni = txtDNI.getText();
            String email = txtEmail.getText();
            String telefono = txtTelefono.getText();
            String provincia = txtProvincia.getText();
            String ciudad = txtCiudad.getText();
            String direccion = txtDireccion.getText();
            String fechaNacimiento = txtFechaNacimiento.getText();
            String contrasena = new String(txtContrasena.getPassword());

            String[] datos = { nombre, apellidos, dni, email, telefono.split(" ")[0], telefono.split(" ")[1], provincia,
                    ciudad, direccion.split(",")[0], direccion.split(",")[1].replace(" ", ""), fechaNacimiento };

            if (GestorUsuarios.agregarUsuario(usuario, contrasena, datos)) {
                JOptionPane.showMessageDialog(null, "Usuario registrado exitosamente.", "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al registrar el usuario.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

            dialog.dispose();
        });
        dialog.setContentPane(panelPrincipal);
        dialog.setSize(800, 800);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

}
