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
        JPanel barraAlta = new JPanel(new BorderLayout());
        barraAlta.setBackground(Color.RED);
        barraAlta.add(new JLabel("007Games", SwingConstants.CENTER));
        JButton botonLogIn = new JButton("LogIn/Reg");
        barraAlta.add(botonLogIn, BorderLayout.EAST);
        
        JButton btnSalir = new JButton("<- Volver");
        barraAlta.add(btnSalir, BorderLayout.WEST);
        btnSalir.addActionListener(e -> {

            dispose();
        });
        
        botonLogIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LogIn().setVisible(true);
                dispose();
            }
        });
        
        
        

        // Panel central
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Texto de bienvenida
        JLabel label = new JLabel("Bienvenido al Menú Principal, ¿A qué desea jugar?", SwingConstants.CENTER);
        label.setPreferredSize(new Dimension(90, 20));
        panel.add(label, BorderLayout.NORTH);

        // Panel para los botones
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 7));
     
        
        // Boton Carrera con imagen
        
        ImageIcon iconoCarrera = new ImageIcon(getClass().getResource("/img/images.jpeg"));
        JButton botonCarrera = new JButton("Carrera", iconoCarrera);

        Image scaledImagen = iconoCarrera.getImage().getScaledInstance(180, 60, Image.SCALE_SMOOTH);
        ImageIcon scaledIcono = new ImageIcon(scaledImagen);

        // Establecer la imagen redimensionada como icono del botón
        botonCarrera.setIcon(scaledIcono);

        botonCarrera.setPreferredSize(new Dimension(180, 60));
        botonCarrera.setHorizontalTextPosition(SwingConstants.CENTER);
        botonCarrera.setVerticalTextPosition(SwingConstants.CENTER);
        botonCarrera.setForeground(Color.WHITE);
        botonCarrera.setFont(new Font(" Monospace", Font.BOLD, 30));
        buttonPane.add(botonCarrera);
        panel.add(buttonPane,BorderLayout.CENTER);
        botonCarrera.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VentanaCaballos VentanaC = new VentanaCaballos();
                VentanaC.setVisible(true);
            }
        });
        

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
        botonRuleta.setForeground(Color.WHITE);
        botonRuleta.setFont(new Font(" Monospace", Font.BOLD, 30));
        buttonPane.add(botonRuleta);
        panel.add(buttonPane,BorderLayout.CENTER);
        botonRuleta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VentanaRuleta();
                setVisible(false);
                dispose();
            }
        });

        // Botón Slots con imagen
        ImageIcon iconoSlot = new ImageIcon(getClass().getResource("/img/Slot.png"));
        JButton botonSlot = new JButton("SLOTS", iconoSlot);

        Image scaledImagn = iconoSlot.getImage().getScaledInstance(180, 60, Image.SCALE_SMOOTH);
        ImageIcon scaledIcno = new ImageIcon(scaledImagn);
        botonSlot.setIcon(scaledIcno);
        botonSlot.setPreferredSize(new Dimension(180, 60));
        botonSlot.setHorizontalTextPosition(SwingConstants.CENTER);
        botonSlot.setVerticalTextPosition(SwingConstants.CENTER);
        botonSlot.setForeground(Color.WHITE);
        botonSlot.setFont(new Font(" Monospace", Font.BOLD, 30));
        buttonPane.add(botonSlot);
        panel.add(buttonPane,BorderLayout.CENTER);
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
        add(barraAlta,BorderLayout.NORTH);

        setVisible(true);
    }
    
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuPrincipal());
    }
}
