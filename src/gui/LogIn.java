package gui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.*;
import java.util.List;
import javax.swing.*;

public class LogIn extends JFrame {

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
        int altoimg = 100;
        int alto_boton = 30;
        int ancho_boton = 80;

        int linea = 0;
        int columna = 0;

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.BLUE);
        linea += espacio;
        columna += espacio;

        JLabel usuarioLabel = new JLabel("Usuario:");
        usuarioLabel.setBounds(columna, linea, ancho_labels, alto);
        columna += ancho_labels + espacio;
        panel.add(usuarioLabel);
        JTextField usuarioField = new JTextField();
        usuarioField.setBounds(columna, linea, ancho_fields, alto);
        linea += alto + espacio;
        columna = espacio;

        panel.add(usuarioField);
        JLabel passwordLabel = new JLabel("Contraseña:");
        passwordLabel.setBounds(columna, linea, ancho_labels, alto);
        panel.add(passwordLabel);
        columna += ancho_labels + espacio;
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(columna, linea, ancho_fields, alto);
        linea += alto + espacio;
        columna = espacio;

        panel.add(passwordField);
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(columna, linea, ancho_boton, alto_boton);
        panel.add(loginButton);
        columna += ancho_boton + espacio;
        JButton registroButton = new JButton("Registrar");
        registroButton.setBounds(columna, linea, ancho_boton, alto_boton);
        panel.add(registroButton);
        linea += alto_boton + espacio;
        columna = espacio;

        setTitle("Login - 007Games");
        setSize(espacio + altoimg + espacio + alto + espacio + alto + espacio + alto + espacio, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Añadir panel al frame
        add(panel, BorderLayout.CENTER);

        // Acciones de los botones
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

    private void realizarLogin() {
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
