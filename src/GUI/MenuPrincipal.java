package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class MenuPrincipal extends JFrame {
    private static final long serialVersionUID = 1L;
    public boolean logeado;
    private JLabel label;
    private JButton botonLogIn;
    public JButton btnSalir;

    public int altoBotones;
    public int anchoBotones;

    public MenuPrincipal() {
        logeado = false;
        anchoBotones = 400;
        altoBotones = 200;

        // Configuración del JFrame
        setTitle("Menú Principal");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel superior
        JPanel barraAlta = new JPanel(new BorderLayout());
        barraAlta.setBackground(Color.RED);
        barraAlta.add(new JLabel("007Games", SwingConstants.CENTER));

        botonLogIn = new JButton("LogIn/Reg");

        barraAlta.add(botonLogIn, BorderLayout.EAST);

        btnSalir = new JButton("Salir");
        barraAlta.add(btnSalir, BorderLayout.WEST);

        btnSalir.addActionListener((ActionEvent e) -> {
            if (logeado) {
                label.setText("Bienvenido al Menú Principal, ¿A qué desea jugar?");
                botonLogIn.setEnabled(true);
                logeado = false;
            } else {
                dispose();
            }
        });
        botonLogIn.addActionListener((ActionEvent e) -> {
            LogIn login = new LogIn(MenuPrincipal.this);
            login.setVisible(true);
        });

        // Panel central
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Texto de bienvenida
        label = new JLabel("Bienvenido al Menú Principal, ¿A qué desea jugar?", SwingConstants.CENTER);
        label.setPreferredSize(new Dimension(90, 20));
        panel.add(label, BorderLayout.NORTH);

        // Panel para los botones
        JPanel panelSeleccion = new JPanel();
        panelSeleccion.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 20));

        // Boton Carrera con imagen

        ImageIcon iconoCarrera = new ImageIcon(getClass().getResource("/img/images.jpeg"));
        JButton botonCarrera = new JButton("Carrera", iconoCarrera);

        Image scaledImagen = iconoCarrera.getImage().getScaledInstance(anchoBotones, altoBotones, Image.SCALE_SMOOTH);
        ImageIcon scaledIcono = new ImageIcon(scaledImagen);

        // Establecer la imagen redimensionada como icono del botón
        botonCarrera.setIcon(scaledIcono);

        botonCarrera.setPreferredSize(new Dimension(anchoBotones, altoBotones));
        botonCarrera.setHorizontalTextPosition(SwingConstants.CENTER);
        botonCarrera.setVerticalTextPosition(SwingConstants.CENTER);
        botonCarrera.setForeground(Color.WHITE);
        botonCarrera.setFont(new Font(" Monospace", Font.BOLD, 30));
        panelSeleccion.add(botonCarrera);
        panel.add(panelSeleccion, BorderLayout.CENTER);
        botonCarrera.addActionListener((ActionEvent e) -> {
            VentanaCaballos VentanaC = new VentanaCaballos();
            VentanaC.setVisible(true);
        });

        // Botón Ruleta con imagen
        ImageIcon iconoRuleta = new ImageIcon(getClass().getResource("/img/Ruleta.png"));
        JButton botonRuleta = new JButton("RULETA", iconoRuleta);

        Image scaledImage = iconoRuleta.getImage().getScaledInstance(anchoBotones, altoBotones, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        botonRuleta.setIcon(scaledIcon);
        botonRuleta.setPreferredSize(new Dimension(anchoBotones, altoBotones));
        botonRuleta.setHorizontalTextPosition(SwingConstants.CENTER);
        botonRuleta.setVerticalTextPosition(SwingConstants.CENTER);
        botonRuleta.setForeground(Color.BLACK);
        botonRuleta.setFont(new Font("Monospace", Font.BOLD, 30));
        panelSeleccion.add(botonRuleta);

        panel.add(panelSeleccion, BorderLayout.CENTER);
        botonRuleta.addActionListener((ActionEvent e) -> {
            if (logeado) {
                new VentanaRuleta().setVisible(true);
            } else {
                new LogIn(MenuPrincipal.this).setVisible(true);
            }
        });

        // Botón Slots con imagen
        ImageIcon iconoSlot = new ImageIcon(getClass().getResource("/img/Slot.png"));
        JButton botonSlot = new JButton("SLOTS", iconoSlot);

        Image scaledImag = iconoSlot.getImage().getScaledInstance(anchoBotones, altoBotones, Image.SCALE_SMOOTH);
        ImageIcon scaledIco = new ImageIcon(scaledImag);
        botonSlot.setIcon(scaledIco);
        botonSlot.setPreferredSize(new Dimension(anchoBotones, altoBotones));
        botonSlot.setHorizontalTextPosition(SwingConstants.CENTER);
        botonSlot.setVerticalTextPosition(SwingConstants.CENTER);
        botonSlot.setForeground(Color.BLACK);
        botonSlot.setFont(new Font("Monospace", Font.BOLD, 30));
        panelSeleccion.add(botonSlot);

        botonSlot.addActionListener(e -> {
            if (logeado) {
                new VentanaSlot().setVisible(true);
            } else {
                new LogIn(this).setVisible(true);
            }
        });

        panel.add(panelSeleccion, BorderLayout.CENTER);

        add(panel, BorderLayout.CENTER);
        add(barraAlta, BorderLayout.NORTH);

        setVisible(true);
    }

    public void actualizarEstado() {
        if (logeado) {
            label.setText("¡Bienvenido al Menú Principal! Ya estás logueado.");
            botonLogIn.setEnabled(false);
        } else {
            label.setText("Bienvenido al Menú Principal, ¿A qué desea jugar?");
            botonLogIn.setEnabled(true);
        }

    }
}