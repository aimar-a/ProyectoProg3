package GUI;

import javax.swing.JFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Profile extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Profile() {
        // Configuración de la ventana principal del perfil
        setTitle("Perfil del Usuario");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1, 10, 10)); // Distribución en una cuadrícula de botones

        // Botón para ver el historial de movimientos
        JButton btnHistorialMovimientos = new JButton("Ver Historial de Movimientos");
        btnHistorialMovimientos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarHistorialMovimientos();
            }
        });

        // Botón para cambiar la contraseña
        JButton btnCambiarContraseña = new JButton("Cambiar Contraseña");
        btnCambiarContraseña.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cambiarContraseña();
            }
        });

        // Botón para ver datos personales
        JButton btnDatosPersonales = new JButton("Ver Datos Personales");
        btnDatosPersonales.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarDatosPersonales();
            }
        });

        // Botón para cerrar sesión
        JButton btnCerrarSesion = new JButton("Cerrar Sesión");
        btnCerrarSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cerrarSesion();
            }
        });

        // Agregar los botones a la ventana
        add(btnHistorialMovimientos);
        add(btnCambiarContraseña);
        add(btnDatosPersonales);
        add(btnCerrarSesion);
    }

    // Método para mostrar el historial de movimientos en un JTable
    private void mostrarHistorialMovimientos() {
        JFrame frame = new JFrame("Historial de Movimientos");
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);

        String[] columnNames = {"Fecha", "Descripción", "Monto", "Saldo"};
        Object[][] data = {
            {"2024-11-01", "Depósito", "$500", "$1500"},
            {"2024-11-02", "Apuesta en Blackjack", "-$50", "$1450"},
            {"2024-11-03", "Ganancia en Ruleta", "+$200", "$1650"},
            {"2024-11-04", "Retiro", "-$300", "$1350"}
        };

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane);

        frame.setVisible(true);
    }

    // Método para cambiar la contraseña (abre un cuadro de diálogo)
    private void cambiarContraseña() {
        JPanel panel = new JPanel(new GridLayout(3, 2));
        
        JLabel lblOldPassword = new JLabel("Contraseña Actual:");
        JLabel lblNewPassword = new JLabel("Nueva Contraseña:");
        JLabel lblConfirmPassword = new JLabel("Confirmar Contraseña:");
        
        JPasswordField txtOldPassword = new JPasswordField();
        JPasswordField txtNewPassword = new JPasswordField();
        JPasswordField txtConfirmPassword = new JPasswordField();
        
        panel.add(lblOldPassword);
        panel.add(txtOldPassword);
        panel.add(lblNewPassword);
        panel.add(txtNewPassword);
        panel.add(lblConfirmPassword);
        panel.add(txtConfirmPassword);
        
        int result = JOptionPane.showConfirmDialog(this, panel, "Cambiar Contraseña",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String oldPassword = new String(txtOldPassword.getPassword());
            String newPassword = new String(txtNewPassword.getPassword());
            String confirmPassword = new String(txtConfirmPassword.getPassword());

            if (newPassword.equals(confirmPassword)) {
                // Aquí iría la lógica para cambiar la contraseña en la base de datos
                JOptionPane.showMessageDialog(this, "Contraseña cambiada exitosamente.");
            } else {
                JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Método para mostrar datos personales en un JTable
    private void mostrarDatosPersonales() {
        JFrame frame = new JFrame("Datos Personales");
        frame.setSize(400, 200);
        frame.setLocationRelativeTo(null);

        String[] columnNames = {"Campo", "Información"};
        Object[][] data = {
            {"Nombre", "Juan Pérez"},
            {"Usuario", "juanperez123"},
            {"Correo", "juan.perez@email.com"},
            {"Teléfono", "+34 600 123 456"}
        };

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane);

        frame.setVisible(true);
    }

    // Método para cerrar sesión (cierra la ventana actual)
    private void cerrarSesion() {
        int result = JOptionPane.showConfirmDialog(this, "¿Estás seguro de que deseas cerrar sesión?", 
                "Cerrar Sesión", JOptionPane.YES_NO_OPTION);
        
        if (result == JOptionPane.YES_OPTION) {
            dispose(); // Cierra la ventana de perfil
            JOptionPane.showMessageDialog(null, "Has cerrado sesión.");
        }
        setVisible(true);
    }
    
}
