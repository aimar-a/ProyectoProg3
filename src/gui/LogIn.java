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
        setTitle("Login - 007Games");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panelImagen = new JPanel();
        JLabel imagenLabel = new JLabel();
        ImageIcon icon = new ImageIcon("img/logpic.png");
        imagenLabel.setIcon(icon);
        panelImagen.add(imagenLabel);

        // Paneles para la interfaz gráfica
        JPanel panel = new JPanel(new GridLayout(3, 2));

        // Etiquetas y campos de texto
        JLabel usuarioLabel = new JLabel("Usuario:");
        usuarioField = new JTextField();

        JLabel passwordLabel = new JLabel("Contraseña:");
        passwordField = new JPasswordField();

        loginButton = new JButton("Login");
        registroButton = new JButton("Registrar");

        // Añadiendo componentes al panel
        panel.add(usuarioLabel);
        panel.add(usuarioField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);
        panel.add(registroButton);

        // Añadir panel al frame
        add(panelImagen, BorderLayout.NORTH);
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
