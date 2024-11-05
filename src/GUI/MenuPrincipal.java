package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class MenuPrincipal extends JFrame {
    private static final long serialVersionUID = 1L;
    public boolean logeado;
    public String usuario;
    private JLabel label;
    private JButton botonLogIn;
    public JButton botonSalir;
    public JPanel barraAlta;
    private JLabel labelUsuario;

    public int altoBotones;
    public int anchoBotones;

    public MenuPrincipal() {
        logeado = false;
        usuario = null;
        anchoBotones = 400;
        altoBotones = 200;

        // Configuración del JFrame
        setTitle("Menú Principal");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel superior
        barraAlta = new JPanel(new BorderLayout());
        barraAlta.setBackground(Color.RED);
        barraAlta.add(new JLabel("007Games", SwingConstants.CENTER));

        botonLogIn = new JButton("LogIn/Reg");

        barraAlta.add(botonLogIn, BorderLayout.EAST);

        botonSalir = new JButton("Salir");
        barraAlta.add(botonSalir, BorderLayout.WEST);

        botonSalir.addActionListener((ActionEvent e) -> {
            if (logeado) {
                int opcion = JOptionPane.showConfirmDialog(this, "¿Deseas cerrar sesión?", "Cerrar Sesión",
                        JOptionPane.YES_NO_OPTION);
                if (opcion == JOptionPane.YES_OPTION) {
                    label.setText("Bienvenido al Menú Principal, ¿A qué desea jugar?");
                    botonLogIn.setEnabled(true);
                    logeado = false; // Cambia el estado a no logueado
                    usuario = null; // Borra el usuario
                    actualizarEstado(); // Refresca la interfaz
                }
            } else {
                dispose(); // Cierra la aplicación si no está logueado
            }
        });

        botonLogIn.addActionListener((ActionEvent e) -> {
            LogIn login = new LogIn(MenuPrincipal.this, null);
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
            if (logeado) {
                new VentanaCaballos().setVisible(true);
            } else {
                new LogIn(MenuPrincipal.this, "Caballos").setVisible(true);

            }

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
                new LogIn(MenuPrincipal.this, "Ruleta").setVisible(true);

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
                new LogIn(MenuPrincipal.this, "Slots").setVisible(true);
            }
        });

        panel.add(panelSeleccion, BorderLayout.CENTER);

        add(panel, BorderLayout.CENTER);
        add(barraAlta, BorderLayout.NORTH);

        setVisible(true);
    }

    public void actualizarEstado() {
        if (logeado) {
            label.setText("¡Bienvenido " + usuario + "! Ya estás logueado.");
            barraAlta.remove(botonLogIn);
            labelUsuario = new JLabel("Usuario: " + usuario + " ");
            barraAlta.add(labelUsuario, BorderLayout.EAST);
        } else {
            label.setText("Bienvenido al Menú Principal, ¿A qué desea jugar?");
            barraAlta.remove(labelUsuario); // funciona regular
            labelUsuario = null;
            barraAlta.add(botonLogIn, BorderLayout.EAST);
        }
    }
}