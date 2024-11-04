package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class VentanaCaballos extends JFrame {
    public void VentanaC() {
        JPanel topbar = new JPanel();
        topbar.setBackground(Color.BLUE);
        topbar.add(new JLabel("Carrera de caballos", SwingConstants.CENTER));
        JButton btnSalir = new JButton("<- Volver");
        topbar.add(btnSalir, BorderLayout.WEST);
        btnSalir.addActionListener(e -> {
            new MenuPrincipal();
            dispose();
        });

        setTitle("Carrera - 007Games");
        setSize(1600, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        VentanaCarreraCaballos VentanaC = new VentanaCarreraCaballos();
        add(VentanaC, BorderLayout.CENTER);
        add(topbar, BorderLayout.NORTH);

        setVisible(true);
    }

}
