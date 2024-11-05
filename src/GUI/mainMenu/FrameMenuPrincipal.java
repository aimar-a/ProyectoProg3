package GUI.mainMenu;

import GUI.caballos.FrameCaballos;
import GUI.logIn.FrameLogIn;
import GUI.ruleta.FrameRuleta;
import GUI.slots.FrameSlots;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

public class FrameMenuPrincipal extends JFrame {
    private static final long serialVersionUID = 1L;
    public boolean logeado;
    public String usuario;
    private JLabel label;
    private JButton botonLogIn;
    private JButton botonSalir;
    private JPanel barraAlta;
    private final JButton botonPerfil;
    private final int altoBotones = 200;
    private final int anchoBotones = 400;

    public FrameMenuPrincipal() {
        // Configuración inicial del JFrame
        setTitle("Menú Principal");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        logeado = false;
        usuario = null;
        botonPerfil = new JButton();

        // Configuración de la barra superior
        configurarBarraAlta();

        // Configuración del panel central con opciones de juego
        JPanel panelCentral = configurarPanelCentral();

        // Añadir paneles al JFrame
        add(panelCentral, BorderLayout.CENTER);
        add(barraAlta, BorderLayout.NORTH);
        setVisible(true);
    }

    private void configurarBarraAlta() {
        barraAlta = new JPanel(new BorderLayout());
        barraAlta.setBackground(Color.RED);

        JLabel titulo = new JLabel("007Games", SwingConstants.CENTER);
        barraAlta.add(titulo);

        botonLogIn = new JButton("LogIn/Reg");
        barraAlta.add(botonLogIn, BorderLayout.EAST);
        botonLogIn.addActionListener(e -> new FrameLogIn(FrameMenuPrincipal.this, null).setVisible(true));

        botonSalir = new JButton("Salir");
        barraAlta.add(botonSalir, BorderLayout.WEST);
        botonSalir.addActionListener(e -> {
            if (logeado) {
                int opcion = JOptionPane.showConfirmDialog(this, "¿Deseas cerrar sesión?", "Cerrar Sesión",
                        JOptionPane.YES_NO_OPTION);
                if (opcion == JOptionPane.YES_OPTION) {
                    cerrarSesion();
                }
            } else {
                int opcion = JOptionPane.showConfirmDialog(this, "¿Estas seguro?", "Salir",
                        JOptionPane.YES_NO_OPTION);
                if (opcion == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

        configurarBotonPerfil();
    }

    private JPanel configurarPanelCentral() {
        JPanel panelCentral = new JPanel(new BorderLayout());

        label = new JLabel("Bienvenido al Menú Principal, ¿A qué desea jugar?", SwingConstants.CENTER);
        label.setPreferredSize(new Dimension(90, 20));
        panelCentral.add(label, BorderLayout.NORTH);

        JPanel panelSeleccion = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 20));

        configurarBotonJuego(panelSeleccion, "Carrera", "/img/mainMenu/Carrera.jpeg",
                e -> abrirVentana(new FrameCaballos(), "Caballos"));
        configurarBotonJuego(panelSeleccion, "RULETA", "/img/mainMenu/Ruleta.png",
                e -> abrirVentana(new FrameRuleta(), "Ruleta"));
        configurarBotonJuego(panelSeleccion, "SLOTS", "/img/mainMenu/Slot.png",
                e -> abrirVentana(new FrameSlots(), "Slots"));

        panelCentral.add(panelSeleccion, BorderLayout.CENTER);
        return panelCentral;
    }

    private void configurarBotonJuego(JPanel panel, String texto, String rutaImagen, ActionListener accion) {
        ImageIcon icono = new ImageIcon(getClass().getResource(rutaImagen));
        Image scaledImage = icono.getImage().getScaledInstance(anchoBotones, altoBotones, Image.SCALE_SMOOTH);
        ImageIcon scaledIcono = new ImageIcon(scaledImage);

        JButton botonJuego = new JButton(texto, scaledIcono);
        botonJuego.setPreferredSize(new Dimension(anchoBotones, altoBotones));
        botonJuego.setHorizontalTextPosition(SwingConstants.CENTER);
        botonJuego.setVerticalTextPosition(SwingConstants.CENTER);
        botonJuego.setForeground(Color.BLACK);
        botonJuego.setFont(new Font("Monospace", Font.BOLD, 30));
        botonJuego.addActionListener(accion);

        panel.add(botonJuego);
    }

    private void configurarBotonPerfil() {
        ImageIcon iconoPerfil = new ImageIcon(getClass().getResource("/img/mainMenu/iconoPerfil.png"));
        Image scaledImagePerfil = iconoPerfil.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        ImageIcon scaledIconPerfil = new ImageIcon(scaledImagePerfil);

        botonPerfil.setIcon(scaledIconPerfil);
        botonPerfil.setPreferredSize(new Dimension(50, 50));
        botonPerfil.setHorizontalTextPosition(SwingConstants.CENTER);
        botonPerfil.setVerticalTextPosition(SwingConstants.CENTER);
        botonPerfil.addActionListener(e -> new FrameProfile(usuario).setVisible(true));
    }

    private void abrirVentana(JFrame frame, String nombreJuego) {
        if (logeado) {
            frame.setVisible(true);
        } else {
            new FrameLogIn(FrameMenuPrincipal.this, nombreJuego).setVisible(true);
        }
    }

    private void cerrarSesion() {
        label.setText("Bienvenido al Menú Principal, ¿A qué desea jugar?");
        botonLogIn.setEnabled(true);
        logeado = false;
        usuario = null;
        actualizarEstado();
    }

    public void actualizarEstado() {
        if (logeado) {
            label.setText("¡Bienvenido " + usuario + "! Ya estás logueado.");
            botonLogIn.setVisible(false);
            botonPerfil.setVisible(true);
            barraAlta.add(botonPerfil, BorderLayout.EAST);
            botonSalir.setText("Cerrar Sesión");
        } else {
            label.setText("Bienvenido al Menú Principal, ¿A qué desea jugar?");
            botonPerfil.setVisible(false);
            botonLogIn.setVisible(true);
            barraAlta.add(botonLogIn, BorderLayout.EAST);
            botonSalir.setText("Salir");
        }
    }
}
