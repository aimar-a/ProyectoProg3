package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.*;
import java.util.List;
import java.util.ArrayList;

public class LogIn extends JFrame {
    private JTextField userField;
    private JPasswordField passwordField;
    private JButton loginButton, registerButton;

    private final String csvFilePath = "src/CSV/users.csv";

    public LogIn() {
        setTitle("Login");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2));

        // Labels y campos de texto
        panel.add(new JLabel("Usuario:"));
        userField = new JTextField();
        panel.add(userField);

        panel.add(new JLabel("Contraseña:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        // Botones de login y registro
        loginButton = new JButton("Iniciar Sesión");
        registerButton = new JButton("Registrarse");

        panel.add(loginButton);
        panel.add(registerButton);

        add(panel);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String usuario = userField.getText();
                String contraseña = new String(passwordField.getPassword());
                if (verificarUsuario(usuario, contraseña)) {
                    JOptionPane.showMessageDialog(null, "Login Exitoso");
                    new MenuPrincipal();  // Abre la nueva interfaz
                    dispose();  // Cierra la ventana de login
                } else {
                    JOptionPane.showMessageDialog(null, "Usuario o Contraseña incorrectos");
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String usuario = userField.getText();
                String contraseña = new String(passwordField.getPassword());
                if (registrarUsuario(usuario, contraseña)) {
                    JOptionPane.showMessageDialog(null, "Usuario registrado con éxito");
                } else {
                    JOptionPane.showMessageDialog(null, "El usuario ya existe");
                }
            }
        });

        setVisible(true);
    }

    private boolean verificarUsuario(String usuario, String contraseña) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(csvFilePath));
            for (String line : lines) {
                String[] data = line.split(",");
                if (data[0].equals(usuario) && data[1].equals(contraseña)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean registrarUsuario(String usuario, String contraseña) {
        // Verificar si el usuario ya existe
        if (verificarUsuario(usuario, contraseña)) {
            return false; // Ya existe
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFilePath, true))) {
            writer.write(usuario + "," + contraseña);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
