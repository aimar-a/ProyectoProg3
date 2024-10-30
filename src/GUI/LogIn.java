package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LogIn extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private JTextField usuarioField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registroButton;

    private static final String CSV_FILE_PATH = "src/CSV/users.csv";

    public LogIn() {
        int ancho_labels = 80;
        int ancho_fields = 160;
        int alto = 25;
        int espacio = 10;
        int altoimg = 250;
        int alto_boton = 40;
        int ancho_boton = 100;

        int linea = 0;
        int columna = 0;

        JPanel panel = new JPanel();
        panel.setLayout(null);
        linea += espacio;
        columna += espacio;

        JLabel img = new JLabel();
        img.setIcon(new ImageIcon("src/img/foto.png"));
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
        loginButton = new JButton("Login");
        loginButton.setBounds(columna, linea, ancho_boton, alto_boton);
        panel.add(loginButton);
        columna += ancho_boton + espacio * 5;
        registroButton = new JButton("Registrar");
        registroButton.setBounds(columna, linea, ancho_boton, alto_boton);
        panel.add(registroButton);
        linea += alto_boton + espacio;
        columna = espacio;

        setTitle("Login - 007Games");
        setSize(ancho_labels + ancho_fields + espacio * 3, linea);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        add(panel);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarLogin();
            }
        });

        registroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarUsuario();
            }
        });
    }

    public void realizarLogin() {
        String usuario = usuarioField.getText();
        String password = new String(passwordField.getPassword());

        if (validarCredenciales(usuario, password)) {
            JOptionPane.showMessageDialog(this, "Login exitoso.");
            abrirMenuPrincipal();
        } else {
            JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean validarCredenciales(String usuario, String password) {
        try {
            List<String> lineas = Files.readAllLines(Paths.get(CSV_FILE_PATH));
            for (String linea : lineas) {
                String[] datos = linea.split(",");
                if (datos[0].equals(usuario) && datos[1].equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void registrarUsuario() {
        String usuario = JOptionPane.showInputDialog(this, "Introduce un nuevo usuario:");
        String password = JOptionPane.showInputDialog(this, "Introduce una nueva contraseña:");

        if (usuario != null && password != null && !usuario.isEmpty() && !password.isEmpty()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE_PATH, true))) {
                writer.write(usuario + "," + password);
                writer.newLine();
                JOptionPane.showMessageDialog(this, "Usuario registrado exitosamente.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Usuario o contraseña no pueden estar vacíos.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirMenuPrincipal() {
        MenuPrincipal menuPrincipal = new MenuPrincipal();
        menuPrincipal.setVisible(true);
        this.dispose(); // Cierra la ventana de login
    }
}
