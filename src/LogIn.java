import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogIn extends JFrame implements ActionListener {
    // Componentes de la ventana
    private JTextField userField;
    private JPasswordField passField;
    private JButton loginButton;

    // Constructor para configurar la ventana
    public LogIn() {
        // Título de la ventana
        setTitle("Login");
        setSize(350, 150); // Dimensiones de la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar ventana en la pantalla

        // Configurar el layout
        setLayout(new GridLayout(3, 2));

        // Etiquetas
        JLabel userLabel = new JLabel("Username:");
        JLabel passLabel = new JLabel("Password:");

        // Campos de texto
        userField = new JTextField();
        passField = new JPasswordField();

        // Botón
        loginButton = new JButton("Log In");
        loginButton.addActionListener(this); // Agregar acción al botón

        // Añadir componentes a la ventana
        add(userLabel);
        add(userField);
        add(passLabel);
        add(passField);
        add(new JLabel()); // Espacio vacío
        add(loginButton);

        // Hacer la ventana visible
        setVisible(true);
    }

    // Método que maneja los eventos de los botones
    @Override
    public void actionPerformed(ActionEvent e) {
        // Obtener los datos ingresados
        String username = userField.getText();
        String password = new String(passField.getPassword());

        // Lógica básica de autenticación (puedes reemplazarla con una validación real)
        if (username.equals("admin") && password.equals("12345")) {
            JOptionPane.showMessageDialog(this, "Login successful!");
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password.");
        }
    }

    // Método principal para ejecutar el programa
    public static void main(String[] args) {
        // Crear una instancia de la ventana LogIn
        new LogIn();
    }
}
