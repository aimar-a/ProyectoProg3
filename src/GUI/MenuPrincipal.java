package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class MenuPrincipal extends JFrame {
    private static final long serialVersionUID = 1L;

    public MenuPrincipal() {
        // Configuración de la ventana
        setTitle("Menú Principal");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel superior
        JPanel topbar = new JPanel();
        topbar.setBackground(Color.RED);
        JLabel title = new JLabel("007Games", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        topbar.add(title);
        add(topbar, BorderLayout.NORTH);

        // Panel central
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Texto de bienvenida
        JLabel label = new JLabel("Bienvenido al Menú Principal, ¿A qué desea jugar?", SwingConstants.CENTER);
        label.setPreferredSize(new Dimension(90, 20));
        panel.add(label, BorderLayout.NORTH);

        // Panel para los botones
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 20));

        // Botón Ruleta con imagen
        ImageIcon iconoRuleta = new ImageIcon(getClass().getResource("/img/Ruleta.png"));
        JButton botonRuleta = new JButton("RULETA", iconoRuleta);

        Image scaledImage = iconoRuleta.getImage().getScaledInstance(180, 60, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        // Establecer la imagen redimensionada como icono del botón
        botonRuleta.setIcon(scaledIcon);

        botonRuleta.setPreferredSize(new Dimension(180, 60));
        botonRuleta.setHorizontalTextPosition(SwingConstants.CENTER);
        botonRuleta.setVerticalTextPosition(SwingConstants.CENTER);
        botonRuleta.setForeground(Color.BLACK);
        botonRuleta.setFont(new Font(" Monospace", Font.BOLD, 30));
        buttonPane.add(botonRuleta);

        buttonPane.add(botonRuleta);
        botonRuleta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VentanaRuleta();
                setVisible(false);
            }
        });

        // Botón Slots con imagen
        ImageIcon iconoSlot = new ImageIcon(getClass().getResource("/img/Slot.png"));
        JButton botonSlot = new JButton("SLOTS", iconoSlot);

        Image scaledImagen = iconoSlot.getImage().getScaledInstance(180, 60, Image.SCALE_SMOOTH);
        ImageIcon scaledIcono = new ImageIcon(scaledImagen);
        botonSlot.setIcon(scaledIcono);
        botonSlot.setPreferredSize(new Dimension(180, 60));
        botonSlot.setHorizontalTextPosition(SwingConstants.CENTER);
        botonSlot.setVerticalTextPosition(SwingConstants.CENTER);
        botonSlot.setForeground(Color.BLACK);
        botonSlot.setFont(new Font(" Monospace", Font.BOLD, 30));
        buttonPane.add(botonSlot);
        botonSlot.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VentanaSlot();
                dispose(); // Cierra el menú principal
            }
        });

        // Añadir el panel de botones al panel central
        panel.add(buttonPane, BorderLayout.CENTER);

        // Añadir el panel central a la ventana
        add(panel, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuPrincipal());
    }
}
