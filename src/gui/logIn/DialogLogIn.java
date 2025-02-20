package gui.logIn;

import db.GestorBD;
import domain.JuegosDisponibles;
import domain.UsuarioActual;
import gui.ColorVariables;
import gui.mainMenu.FrameMenuPrincipal;
import io.ConfigProperties;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

//IAG: ChatGPT y GitHub Copilot
//ADAPTADO: Ordenar y limpiar código, anadir funcionalidades y autocompeltado
public class DialogLogIn extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JTextField usuarioField;
    private final JPasswordField passwordField;
    private final JButton botonLogin;
    private final JButton botonRegistro;
    private final FrameMenuPrincipal menuPrincipal;
    private final JuegosDisponibles juegoObjetivo;

    public DialogLogIn(FrameMenuPrincipal menuPrinc, JuegosDisponibles juegoObjetivo) {
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
        ImageIcon iconoLogin = new ImageIcon("resources/img/logIn/foto.png");
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
            new DialogRegistro(this).setVisible(true);
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

        if (ConfigProperties.isUiDarkMode()) {
            panel.setBackground(ColorVariables.COLOR_FONDO_DARK.getColor());
            usuarioLabel.setForeground(ColorVariables.COLOR_TEXTO_DARK.getColor());
            passwordLabel.setForeground(ColorVariables.COLOR_TEXTO_DARK.getColor());
            usuarioField.setBackground(ColorVariables.COLOR_FONDO_DARK.getColor());
            usuarioField.setForeground(ColorVariables.COLOR_TEXTO_DARK.getColor());
            passwordField.setBackground(ColorVariables.COLOR_FONDO_DARK.getColor());
            passwordField.setForeground(ColorVariables.COLOR_TEXTO_DARK.getColor());
            botonLogin.setBackground(ColorVariables.COLOR_BOTON_DARK.getColor());
            botonLogin.setForeground(ColorVariables.COLOR_TEXTO_DARK.getColor());
            botonRegistro.setBackground(ColorVariables.COLOR_BOTON_DARK.getColor());
            botonRegistro.setForeground(ColorVariables.COLOR_TEXTO_DARK.getColor());
        } else {
            panel.setBackground(ColorVariables.COLOR_FONDO_LIGHT.getColor());
            usuarioLabel.setForeground(ColorVariables.COLOR_TEXTO_LIGHT.getColor());
            passwordLabel.setForeground(ColorVariables.COLOR_TEXTO_LIGHT.getColor());
            usuarioField.setBackground(ColorVariables.COLOR_FONDO_LIGHT.getColor());
            usuarioField.setForeground(ColorVariables.COLOR_TEXTO_LIGHT.getColor());
            passwordField.setBackground(ColorVariables.COLOR_FONDO_LIGHT.getColor());
            passwordField.setForeground(ColorVariables.COLOR_TEXTO_LIGHT.getColor());
            botonLogin.setBackground(ColorVariables.COLOR_BOTON_LIGHT.getColor());
            botonLogin.setForeground(ColorVariables.COLOR_TEXTO_LIGHT.getColor());
            botonRegistro.setBackground(ColorVariables.COLOR_BOTON_LIGHT.getColor());
            botonRegistro.setForeground(ColorVariables.COLOR_TEXTO_LIGHT.getColor());
        }
    }

    public void realizarLogin() {
        String usuario = usuarioField.getText();
        String password = new String(passwordField.getPassword());

        if (password.equals(GestorBD.obtenerContrasena(usuario))) {
            JOptionPane.showMessageDialog(this, "Login exitoso.");
            UsuarioActual.setUsuarioActual(usuario); // Establece el usuario actual
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

}
