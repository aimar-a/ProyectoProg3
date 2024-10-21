package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class LogIn extends JFrame implements ActionListener {
    private JTextField userField;
    private JPasswordField passField;
    private JButton loginButton, registerButton;

    // Almacenamiento de usuarios (usuario, contraseña)
    private Map<String, String> users = new HashMap<>();
    private final String fileName = "users.csv"; // Nombre del archivo CSV

    // Constructor
    public LogIn() {
        // Cargar usuarios desde el archivo CSV
        loadUsersFromCSV();
    }

    // Método para inicializar y mostrar la ventana de login
    public void showLoginWindow() {
        setTitle("Login");
        setSize(350, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new GridLayout(3, 2));

        JLabel userLabel = new JLabel("Username:");
        JLabel passLabel = new JLabel("Password:");
        userField = new JTextField();
        passField = new JPasswordField();
        loginButton = new JButton("Log In");
        registerButton = new JButton("Register");

        // Agregar listeners
        loginButton.addActionListener(this);
        registerButton.addActionListener(this);

        // Añadir componentes a la ventana
        add(userLabel);
        add(userField);
        add(passLabel);
        add(passField);
        add(registerButton); // Botón de registro
        add(loginButton); // Botón de login

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Si se presiona el botón de "Login"
        if (e.getSource() == loginButton) {
            String username = userField.getText();
            String password = new String(passField.getPassword());

            // Validar si el usuario existe y la contraseña es correcta
            if (users.containsKey(username) && users.get(username).equals(password)) {
                JOptionPane.showMessageDialog(this, "Login successful!");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password.");
            }
        }

        // Si se presiona el botón de "Register"
        if (e.getSource() == registerButton) {
            // Abrir la ventana de registro
            showRegisterWindow();
        }
    }

    // Método para mostrar la ventana de registro
    private void showRegisterWindow() {
        JFrame registerFrame = new JFrame("Register");
        registerFrame.setSize(350, 150);
        registerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        registerFrame.setLocationRelativeTo(this);

        registerFrame.setLayout(new GridLayout(3, 2));

        JLabel newUserLabel = new JLabel("New Username:");
        JLabel newPassLabel = new JLabel("New Password:");
        JTextField newUserField = new JTextField();
        JPasswordField newPassField = new JPasswordField();
        JButton createAccountButton = new JButton("Create Account");

        // Agregar listener al botón de creación de cuenta
        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newUsername = newUserField.getText();
                String newPassword = new String(newPassField.getPassword());

                // Validar si el nombre de usuario ya existe
                if (users.containsKey(newUsername)) {
                    JOptionPane.showMessageDialog(registerFrame, "Username already exists.");
                } else {
                    // Registrar nuevo usuario
                    users.put(newUsername, newPassword);
                    saveUserToCSV(newUsername, newPassword); // Guardar en el CSV
                    JOptionPane.showMessageDialog(registerFrame, "Account created successfully!");
                    registerFrame.dispose(); // Cerrar ventana de registro
                }
            }
        });

        // Añadir componentes a la ventana de registro
        registerFrame.add(newUserLabel);
        registerFrame.add(newUserField);
        registerFrame.add(newPassLabel);
        registerFrame.add(newPassField);
        registerFrame.add(new JLabel()); // Espacio vacío
        registerFrame.add(createAccountButton);

        // Mostrar la ventana de registro
        registerFrame.setVisible(true);
    }

    // Método para cargar usuarios desde un archivo CSV
    private void loadUsersFromCSV() {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length == 2) {
                    users.put(values[0], values[1]); // Usuario y contraseña
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading users.csv: " + e.getMessage());
        }
    }

    // Método para guardar un nuevo usuario en el archivo CSV
    private void saveUserToCSV(String username, String password) {
        try (FileWriter fw = new FileWriter(fileName, true); // 'true' para añadir al archivo
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter pw = new PrintWriter(bw)) {

            pw.println(username + "," + password); // Escribir en formato CSV (username,password)

        } catch (IOException e) {
            System.out.println("Error writing to users.csv: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        LogIn login = new LogIn();
        login.showLoginWindow();
    }
}
