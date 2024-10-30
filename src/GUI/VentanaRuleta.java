package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class VentanaRuleta extends JFrame {
    public VentanaRuleta() {
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(Color.BLUE);
        topBar.add(new JLabel("Ruleta", SwingConstants.CENTER));
        JButton btnSalir = new JButton("<- Volver");
        topBar.add(btnSalir, BorderLayout.WEST);
        btnSalir.addActionListener(e -> {

            dispose();
        });

        setTitle("Ruleta - 007Games");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel label = new JLabel("¡Bienvenido a la Ruleta!", SwingConstants.CENTER);
        add(label, BorderLayout.CENTER);
        add(topBar, BorderLayout.NORTH);

        setVisible(true);
    }
}
