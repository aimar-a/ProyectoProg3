package GUI.caballos;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class FrameCaballos extends JFrame {

    public FrameCaballos() {
        // Barra superior
        JPanel topbar = new JPanel(new BorderLayout());
        topbar.setBackground(Color.BLUE);

        JLabel labelTitulo = new JLabel("Carrera de caballos", SwingConstants.CENTER);
        labelTitulo.setForeground(Color.WHITE);
        topbar.add(labelTitulo, BorderLayout.CENTER);

        JButton btnSalir = new JButton("<- Volver");
        topbar.add(btnSalir, BorderLayout.WEST);

        btnSalir.addActionListener(e -> {
            dispose();
        });

        // Configuración de la ventana principal
        setTitle("Carrera - 007Games");
        setSize(1600, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Añadir el panel de la carrera
        PanelVentanaCarreraCaballos carreraCaballos = new PanelVentanaCarreraCaballos();
        add(carreraCaballos, BorderLayout.CENTER);
        add(topbar, BorderLayout.NORTH);

        // Hacer visible la ventana
        setVisible(true);
    }
}
