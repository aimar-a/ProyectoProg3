package GUI.logIn;

import GUI.mainMenu.FrameMenuPrincipal;
import GUI.mainMenu.JuegosDisponibles;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
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

    private static final String RUTA_USUARIO_CONTRA = "src/CSV/usuarioContra.csv";
    private static final String RUTA_USUARIO_DATOS = "src/CSV/usuarioDatos.csv";
    private static final String RUTA_CARTERA = "src/CSV/cartera.csv";

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

        if (validarCredenciales(usuario, password)) {
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

    // IAG: Optimizar la búsqueda binaria para que sea más eficiente
    public static boolean validarCredenciales(String usuario, String password) {
        try {
            List<String> lineas = Files.readAllLines(Paths.get(RUTA_USUARIO_CONTRA));

            // Hacer búsqueda binaria sobre el usuario
            int inicio = 0;
            int fin = lineas.size() - 1;

            while (inicio <= fin) {
                int medio = (inicio + fin) / 2;
                String[] datos = lineas.get(medio).split(",");
                String usuarioActual = datos[0];

                int comparacion = usuario.compareTo(usuarioActual);
                if (comparacion == 0) {
                    // Usuario encontrado, verificar la contraseña
                    return datos[1].equals(password);
                } else if (comparacion < 0) {
                    fin = medio - 1; // El usuario está en la mitad izquierda
                } else {
                    inicio = medio + 1; // El usuario está en la mitad derecha
                }
            }
        } catch (IOException e) {
            // Manejo de errores, podrías registrar el error si es necesario
        }
        return false; // Usuario no encontrado
    }

    // IAG
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
        JTextField txtFechaNacimiento = new JTextField("DD/MM/AAAA");
        JPasswordField txtContrasena = new JPasswordField();
        JButton btnAceptar = new JButton("Aceptar");
        JButton btnCancelar = new JButton("Cancelar");
        btnAceptar.setEnabled(false);

        // Crear un panel con los campos
        JPanel panelCampos = new JPanel(new GridLayout(12, 2, 10, 10));
        panelCampos.add(new JLabel("Usuario:"));
        panelCampos.add(txtUsuario);
        panelCampos.add(new JLabel("Nombre:"));
        panelCampos.add(txtNombre);
        panelCampos.add(new JLabel("Apellidos:"));
        panelCampos.add(txtApellidos);
        panelCampos.add(new JLabel("DNI:"));
        panelCampos.add(txtDNI);
        panelCampos.add(new JLabel("Email:"));
        panelCampos.add(txtEmail);
        panelCampos.add(new JLabel("Teléfono:"));
        panelCampos.add(txtTelefono);
        panelCampos.add(new JLabel("Provincia:"));
        panelCampos.add(txtProvincia);
        panelCampos.add(new JLabel("Ciudad:"));
        panelCampos.add(txtCiudad);
        panelCampos.add(new JLabel("Dirección:"));
        panelCampos.add(txtDireccion);
        panelCampos.add(new JLabel("Fecha de nacimiento:"));
        panelCampos.add(txtFechaNacimiento);
        panelCampos.add(new JLabel("Contraseña:"));
        panelCampos.add(txtContrasena);
        panelCampos.add(btnCancelar);
        panelCampos.add(btnAceptar);

        // Crear un panel principal con borde
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelPrincipal.setLayout(new BorderLayout());
        panelPrincipal.add(panelCampos, BorderLayout.CENTER);

        DocumentListener validationListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                verificarCampos(txtNombre, txtApellidos, txtDNI, txtEmail, txtTelefono, txtProvincia, txtCiudad,
                        txtDireccion, txtFechaNacimiento, txtUsuario, btnAceptar);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                verificarCampos(txtNombre, txtApellidos, txtDNI, txtEmail, txtTelefono, txtProvincia, txtCiudad,
                        txtDireccion, txtFechaNacimiento, txtUsuario, btnAceptar);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                verificarCampos(txtNombre, txtApellidos, txtDNI, txtEmail, txtTelefono, txtProvincia, txtCiudad,
                        txtDireccion, txtFechaNacimiento, txtUsuario, btnAceptar);
            }
        };
        JDialog dialog = new JDialog(this, "Registrar Usuario", true);

        agregarDocumentListeners(validationListener, txtNombre, txtApellidos, txtDNI, txtEmail, txtTelefono,
                txtProvincia, txtCiudad, txtDireccion, txtFechaNacimiento, txtUsuario);

        verificarCampos(txtNombre, txtApellidos, txtDNI, txtEmail, txtTelefono, txtProvincia, txtCiudad, txtDireccion,
                txtFechaNacimiento, txtUsuario, btnAceptar);

        btnCancelar.addActionListener(e -> dialog.dispose());
        btnAceptar.addActionListener(e -> {
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
            String fechaRegistro = new SimpleDateFormat("dd/MM/yyyy").format(new Date());

            // Verificar que el usuario no exista en usuarioContra.csv
            try {
                List<String> usuarioContraLines = Files.readAllLines(Paths.get(RUTA_USUARIO_CONTRA));
                for (String line : usuarioContraLines) {
                    String[] data = line.split(",");
                    if (data[0].equals(usuario)) {
                        JOptionPane.showMessageDialog(null, "El usuario ya existe.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                // Añadir al usuario en usuarioContra.csv
                usuarioContraLines.add(usuario + "," + contrasena);
                Collections.sort(usuarioContraLines);
                Files.write(Paths.get(RUTA_USUARIO_CONTRA), usuarioContraLines);

                // Añadir al usuario en usuarioDatos.csv
                List<String> usuarioDatosLines = Files.readAllLines(Paths.get(RUTA_USUARIO_DATOS));
                String usuarioDatos = usuario + "," + nombre + "," + apellidos + "," + dni + "," + email + "," +
                        telefono.substring(0, 3) + "," + telefono.substring(3) + "," + provincia + "," + ciudad + "," +
                        direccion.split(", ")[0] + "," + direccion.split(", ")[1] + "," + fechaNacimiento + ","
                        + fechaRegistro;
                usuarioDatosLines.add(usuarioDatos);
                Collections.sort(usuarioDatosLines);
                Files.write(Paths.get(RUTA_USUARIO_DATOS), usuarioDatosLines);

                // Añadir al usuario en cartera.csv
                List<String> carteraLines = Files.readAllLines(Paths.get(RUTA_CARTERA));
                carteraLines.add(usuario + ",0.0");
                Collections.sort(carteraLines);
                Files.write(Paths.get(RUTA_CARTERA), carteraLines);

                JOptionPane.showMessageDialog(null, "Usuario registrado exitosamente.", "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error al acceder a los archivos.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
            dialog.dispose();
        });
        dialog.setContentPane(panelPrincipal);
        dialog.setSize(400, 500);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void agregarDocumentListeners(DocumentListener listener, JTextField... camposTexto) {
        for (JTextField campoTexto : camposTexto) {
            campoTexto.getDocument().addDocumentListener(listener);
        }
    }

    private void verificarCampos(JTextField txtNombre, JTextField txtApellidos, JTextField txtDNI, JTextField txtEmail,
            JTextField txtTelefono, JTextField txtProvincia, JTextField txtCiudad, JTextField txtDireccion,
            JTextField txtFechaNacimiento, JTextField txtUsuario, JButton btnAceptar) {
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
        esValido &= validarCampo(txtUsuario, "[a-zA-Z0-9]+");

        try {
            List<String> usuarioContraLines = Files.readAllLines(Paths.get(RUTA_USUARIO_CONTRA));
            for (String line : usuarioContraLines) {
                String[] data = line.split(",");
                if (data[0].equals(txtUsuario.getText())) {
                    esValido = false;
                    txtUsuario.setForeground(Color.RED);
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        btnAceptar.setEnabled(esValido);
    }

    private boolean validarCampo(JTextField campo, String regex) {
        boolean esValido = !campo.getText().trim().isEmpty() && campo.getText().matches(regex);
        campo.setForeground(esValido ? Color.BLACK : Color.RED);
        return esValido;
    }
}
