package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.*;

public class MenuPrincipal extends JFrame {
    public MenuPrincipal() {
        setTitle("Menú Principal");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel topbar = new JPanel();
        topbar.setBackground(Color.BLUE);
        JLabel title = new JLabel("007Games", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        topbar.add(title);
        add(topbar, BorderLayout.NORTH);

        JLabel label = new JLabel("Bienvenido al Menú Principal", SwingConstants.CENTER);
        add(label);
    }
}
