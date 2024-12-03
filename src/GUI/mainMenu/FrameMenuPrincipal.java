package GUI.mainMenu;

import GUI.blackjack.FrameBlackjack;
import GUI.caballos.FrameCaballos;
import GUI.dinoRun.FrameDino;
import GUI.logIn.FrameLogIn;
import GUI.minas.FrameMinas;
import GUI.perfil.FramePerfil;
import GUI.ruleta.FrameRuleta;
import GUI.slots.FrameSlots;
import datos.GestorMovimientos;
import java.awt.*;
import javax.swing.*;

public class FrameMenuPrincipal extends JFrame {
    private static final long serialVersionUID = 1L;
    public boolean logeado;
    private String usuario;
    private JLabel labelBienvenida;
    private JLabel labelSaldo = new JLabel();
    private JButton botonLogIn;
    private JButton botonSalir;
    private JPanel barraAlta;
    private final JButton botonPerfil;
    public boolean loginAbierto;
    private JPanel panelDerechaBarraAlta;

    public FrameMenuPrincipal() {
        // Configuración inicial del JFrame
        setTitle("Menú Principal");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int frameWidth = (int) (screenSize.width * 0.6);
        int frameHeight = (int) (screenSize.height * 0.8);
        setSize(frameWidth, frameHeight);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        logeado = false;
        usuario = null;
        botonPerfil = new JButton();

        // Configuración de la barra superior
        configurarBarraAlta();

        // Configuración del panel central con opciones de juego
        JPanel panelCentral = configurarPanelCentral();

        labelSaldo.setVisible(false);
        botonPerfil.setVisible(false);
        panelDerechaBarraAlta.add(labelSaldo);
        panelDerechaBarraAlta.add(botonPerfil);

        // Añadir paneles al JFrame
        add(panelCentral, BorderLayout.CENTER);
        add(barraAlta, BorderLayout.NORTH);
    }

    private void configurarBarraAlta() {
        barraAlta = new JPanel(new BorderLayout());
        barraAlta.setBackground(Color.RED);

        JLabel titulo = new JLabel("007Games", SwingConstants.CENTER);
        titulo.setFont(new Font("Monospace", Font.BOLD, 30));
        barraAlta.add(titulo);

        panelDerechaBarraAlta = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelDerechaBarraAlta.setBackground(Color.RED);
        panelDerechaBarraAlta.setPreferredSize(new Dimension(getWidth() / 3, 35));
        barraAlta.add(panelDerechaBarraAlta, BorderLayout.EAST);

        JPanel panelIzquierdaBarraAlta = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelIzquierdaBarraAlta.setBackground(Color.RED);
        panelIzquierdaBarraAlta.setPreferredSize(new Dimension(getWidth() / 3, 35));
        barraAlta.add(panelIzquierdaBarraAlta, BorderLayout.WEST);

        botonLogIn = new JButton("LogIn/Reg");
        panelDerechaBarraAlta.add(botonLogIn, BorderLayout.EAST);
        botonLogIn.addActionListener(e -> {
            if (!loginAbierto) {
                loginAbierto = true;
                new FrameLogIn(FrameMenuPrincipal.this, null).setVisible(true);
            }
        });

        botonSalir = new JButton("Salir");
        panelIzquierdaBarraAlta.add(botonSalir, BorderLayout.WEST);
        botonSalir.addActionListener(e -> {
            if (logeado) {
                int opcion = JOptionPane.showConfirmDialog(this, "¿Deseas cerrar sesión?", "Cerrar Sesión",
                        JOptionPane.YES_NO_OPTION);
                if (opcion == JOptionPane.YES_OPTION) {
                    loginAbierto = false;
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

        labelBienvenida = new JLabel("Bienvenido al Menú Principal, ¿A qué desea jugar?", SwingConstants.CENTER);
        int labelWidth = getWidth() / 10;
        int labelHeight = getHeight() / 40;
        labelBienvenida.setPreferredSize(new Dimension(labelWidth, labelHeight));
        panelCentral.add(labelBienvenida, BorderLayout.NORTH);

        JPanel panelSeleccion = new JPanel(new FlowLayout(FlowLayout.CENTER, getWidth() / 25, getHeight() / 40));

        configurarBotonJuego(panelSeleccion, JuegosDisponibles.CABALLOS, "/img/mainMenu/Carrera.jpeg");
        // configurarBotonJuego(panelSeleccion, JuegosDisponibles.RULETA,
        // "/img/mainMenu/Ruleta.png");
        configurarBotonJuego(panelSeleccion, JuegosDisponibles.SLOTS, "/img/mainMenu/Slot.png");
        configurarBotonJuego(panelSeleccion, JuegosDisponibles.BLACKJACK, "/img/mainMenu/Blackjack.jpg");
        // configurarBotonJuego(panelSeleccion, JuegosDisponibles.MINAS,
        // "/img/mainMenu/Minas.jpg");
        configurarBotonJuego(panelSeleccion, JuegosDisponibles.DINOSAURIO, "/img/mainMenu/Dinosaurio.jpg");

        panelCentral.add(panelSeleccion, BorderLayout.CENTER);
        return panelCentral;
    }

    private void configurarBotonJuego(JPanel panel, JuegosDisponibles juegoElegido, String rutaImagen) {
        int botonWidth = (int) (getWidth() / (getWidth() > 1.5 * getHeight() ? 3.5 : 2.5));
        int botonHeight = (int) (getHeight() / (getWidth() > 1.5 * getHeight() ? 2.5 : 4));

        ImageIcon icono = new ImageIcon(getClass().getResource(rutaImagen));
        Image scaledImage = icono.getImage().getScaledInstance(botonWidth, botonHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledIcono = new ImageIcon(scaledImage);

        JButton botonJuego = new JButton(juegoElegido.getNombre(), scaledIcono);
        botonJuego.setPreferredSize(new Dimension(botonWidth, botonHeight));
        botonJuego.setHorizontalTextPosition(SwingConstants.CENTER);
        botonJuego.setVerticalTextPosition(SwingConstants.CENTER);
        botonJuego.setForeground(Color.WHITE);
        botonJuego.setFont(new Font("Monospace", Font.BOLD, botonWidth / 6));
        botonJuego.addActionListener(e -> abrirVentana(juegoElegido));

        panel.add(botonJuego);
    }

    private void configurarBotonPerfil() {
        int iconSize = 16;

        ImageIcon iconoPerfil = new ImageIcon(getClass().getResource("/img/mainMenu/iconoPerfil.png"));
        Image scaledImagePerfil = iconoPerfil.getImage().getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH);
        ImageIcon scaledIconPerfil = new ImageIcon(scaledImagePerfil);

        botonPerfil.setIcon(scaledIconPerfil);
        botonPerfil.setHorizontalTextPosition(SwingConstants.RIGHT);
        botonPerfil.setHorizontalAlignment(SwingConstants.LEFT);
        botonPerfil.addActionListener(e -> new FramePerfil(this).setVisible(true));
    }

    public void abrirVentana(JuegosDisponibles juegoObjetivo) {
        if (logeado) {
            this.setVisible(false);
            switch (juegoObjetivo) {
                case JuegosDisponibles.CABALLOS -> new FrameCaballos(this, usuario).setVisible(true);
                case JuegosDisponibles.RULETA -> new FrameRuleta(this).setVisible(true);
                case JuegosDisponibles.SLOTS -> new FrameSlots(this).setVisible(true);
                case JuegosDisponibles.BLACKJACK -> new FrameBlackjack(this, usuario).setVisible(true);
                case JuegosDisponibles.MINAS -> new FrameMinas(this).setVisible(true);
                case JuegosDisponibles.DINOSAURIO -> new FrameDino(this,usuario).setVisible(true);
                default -> {
                    JOptionPane.showMessageDialog(this, "Juego no disponible", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            if (!loginAbierto) {
                loginAbierto = true;
                new FrameLogIn(FrameMenuPrincipal.this, juegoObjetivo).setVisible(true);
            }
        }
    }

    private void cerrarSesion() {
        labelBienvenida.setText("Bienvenido al Menú Principal, ¿A qué desea jugar?");
        botonLogIn.setEnabled(true);
        logeado = false;
        usuario = null;
        actualizarEstado();
    }

    public void actualizarEstado() {
        if (logeado) {
            labelBienvenida.setText("¡Bienvenido " + usuario + "! Ya estás logueado.");
            botonLogIn.setVisible(false);
            botonPerfil.setVisible(true);
            labelSaldo.setVisible(true);
            botonPerfil.setText(" " + usuario);
            labelSaldo.setText("Saldo: " + GestorMovimientos.obtenerSaldo(usuario) + " fichas  ");
            labelSaldo.setFont(labelSaldo.getFont().deriveFont(15.0f));
            labelSaldo.setForeground(Color.WHITE);
            GestorMovimientos.setLabelMainMenu(labelSaldo);
            botonSalir.setText("Cerrar Sesión");
        } else {
            labelBienvenida.setText("Bienvenido al Menú Principal, ¿A qué desea jugar?");
            botonPerfil.setVisible(false);
            botonLogIn.setVisible(true);
            labelSaldo.setVisible(false);
            labelSaldo.setText("");
            botonSalir.setText("Salir");
        }
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
