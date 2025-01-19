package gui.mainMenu;

import db.GestorBD;
import domain.JuegosDisponibles;
import domain.UsuarioActual;
import gui.ColorVariables;
import gui.juegos.blackjack.FrameBlackjack;
import gui.juegos.caballos.FrameCaballos;
import gui.juegos.dinosaurio.FrameDino;
import gui.juegos.minas.FrameMinas;
import gui.juegos.ruleta.FrameRuleta;
import gui.juegos.slots.FrameSlots;
import gui.logIn.DialogLogIn;
import gui.perfil.FramePerfil;
import io.ConfigProperties;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

//IAG: ChatGPT y GitHub Copilot
//ADAPTADO: Ordenar y limpiar código, anadir funcionalidades y autocompeltado
public class FrameMenuPrincipal extends JFrame {
    private static final long serialVersionUID = 1L;
    private JLabel labelBienvenida;
    private JLabel labelSaldo = new JLabel();
    private JButton botonLogIn;
    private JButton botonSalir;
    private JPanel barraAlta;
    private final JButton botonPerfil;
    public boolean loginAbierto;
    private JPanel panelDerechaBarraAlta;
    private JPanel panelIzquierdaBarraAlta;
    private JPanel panelSeleccion;
    private JPanel panelCentral;
    private JLabel titulo;

    public FrameMenuPrincipal() {
        configurarFrame();
        botonPerfil = new JButton();
        configurarPanelCentral();
        configurarBarraAlta();
        configurarEventosTeclado();
        add(panelCentral, BorderLayout.CENTER);
        add(barraAlta, BorderLayout.NORTH);
    }

    private void configurarFrame() {
        setTitle("Menú Principal");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        if (ConfigProperties.isUiFullScreen()) {
            setSize(screenSize.width, screenSize.height);
            setResizable(false);
            setUndecorated(true);
        } else {
            setSize((int) (screenSize.width * 0.6), (int) (screenSize.height * 0.8));
        }
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void configurarBarraAlta() {
        barraAlta = new JPanel(new BorderLayout());
        barraAlta.setBackground(Color.RED);

        titulo = new JLabel("007Games", SwingConstants.CENTER);
        titulo.setFont(new Font("Monospace", Font.BOLD, 30));
        barraAlta.add(titulo);

        panelDerechaBarraAlta = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelDerechaBarraAlta.setBackground(Color.RED);
        panelDerechaBarraAlta.setPreferredSize(new Dimension(getWidth() / 3, 35));
        barraAlta.add(panelDerechaBarraAlta, BorderLayout.EAST);

        panelIzquierdaBarraAlta = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelIzquierdaBarraAlta.setBackground(Color.RED);
        panelIzquierdaBarraAlta.setPreferredSize(new Dimension(getWidth() / 3, 35));
        barraAlta.add(panelIzquierdaBarraAlta, BorderLayout.WEST);

        configurarBotonesBarraAlta();
        configurarBotonPerfil();
    }

    private void configurarBotonesBarraAlta() {
        botonLogIn = new JButton("LogIn/Reg");
        panelDerechaBarraAlta.add(botonLogIn, BorderLayout.EAST);
        botonLogIn.addActionListener(e -> {
            if (!loginAbierto) {
                loginAbierto = true;
                new DialogLogIn(FrameMenuPrincipal.this, null).setVisible(true);
            }
        });

        botonSalir = new JButton("Salir");
        panelIzquierdaBarraAlta.add(botonSalir, BorderLayout.WEST);
        botonSalir.addActionListener(e -> {
            if (UsuarioActual.isLogged()) {
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

        SwitchButton toggleDarkMode = new SwitchButton();
        toggleDarkMode.setSize(new Dimension(50, 25));
        toggleDarkMode.addEventSelected((boolean selected) -> {
            if (selected)
                enableDarkMode();
            else
                disableDarkMode();
        });
        toggleDarkMode.setSelected(ConfigProperties.isUiDarkMode());
        panelIzquierdaBarraAlta.add(toggleDarkMode, BorderLayout.WEST);
        panelIzquierdaBarraAlta.add(new JLabel("Modo oscuro"));
        toggleDarkMode.repaint();
    }

    private void configurarPanelCentral() {
        panelCentral = new JPanel(new BorderLayout());

        labelBienvenida = new JLabel("Te damos la bienvenida al Menú Principal, ¿A qué desea jugar?",
                SwingConstants.CENTER);
        labelBienvenida.setPreferredSize(new Dimension(getWidth() / 10, getHeight() / 40));
        panelCentral.add(labelBienvenida, BorderLayout.NORTH);

        panelSeleccion = new JPanel(new FlowLayout(FlowLayout.CENTER, getWidth() / 25, getHeight() / 40));
        for (JuegosDisponibles juego : JuegosDisponibles.values()) {
            configurarBotonJuego(panelSeleccion, juego);
        }
        panelCentral.add(panelSeleccion, BorderLayout.CENTER);
    }

    private void configurarBotonJuego(JPanel panel, JuegosDisponibles juegoElegido) {
        int botonWidth = (int) (getWidth() / (getWidth() > 1.5 * getHeight() ? 3.5 : 2.5));
        int botonHeight = (int) (getHeight() / (getWidth() > 1.5 * getHeight() ? 2.5 : 4));

        ImageIcon icono = new ImageIcon("resources/img/juegos/" + juegoElegido.name().toLowerCase() + "/portada.png");
        Image scaledImage = icono.getImage().getScaledInstance(botonWidth, botonHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledIcono = new ImageIcon(scaledImage);

        JButton botonJuego = new JButton(juegoElegido.name(), scaledIcono);
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

        ImageIcon iconoPerfil = new ImageIcon("resources/img/perfil/icono.png");
        Image scaledImagePerfil = iconoPerfil.getImage().getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH);
        ImageIcon scaledIconPerfil = new ImageIcon(scaledImagePerfil);

        botonPerfil.setIcon(scaledIconPerfil);
        botonPerfil.setHorizontalTextPosition(SwingConstants.RIGHT);
        botonPerfil.setHorizontalAlignment(SwingConstants.LEFT);
        botonPerfil.addActionListener(e -> new FramePerfil(this).setVisible(true));
    }

    private void configurarEventosTeclado() {
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_C -> abrirVentana(JuegosDisponibles.CABALLOS);
                    case KeyEvent.VK_R -> abrirVentana(JuegosDisponibles.RULETA);
                    case KeyEvent.VK_S -> abrirVentana(JuegosDisponibles.SLOTS);
                    case KeyEvent.VK_B -> abrirVentana(JuegosDisponibles.BLACKJACK);
                    case KeyEvent.VK_M -> abrirVentana(JuegosDisponibles.MINAS);
                    case KeyEvent.VK_D -> abrirVentana(JuegosDisponibles.DINOSAURIO);
                    case KeyEvent.VK_ESCAPE -> manejarEscape();
                    default -> {
                        // Do nothing
                    }
                }
            }
        });
    }

    private void manejarEscape() {
        if (UsuarioActual.isLogged()) {
            int opcion = JOptionPane.showConfirmDialog(FrameMenuPrincipal.this, "¿Deseas cerrar sesión?",
                    "Cerrar Sesión", JOptionPane.YES_NO_OPTION);
            if (opcion == JOptionPane.YES_OPTION) {
                loginAbierto = false;
                cerrarSesion();
            }
        } else {
            int opcion = JOptionPane.showConfirmDialog(FrameMenuPrincipal.this, "¿Estas seguro?", "Salir",
                    JOptionPane.YES_NO_OPTION);
            if (opcion == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }

    public void abrirVentana(JuegosDisponibles juegoObjetivo) {
        if (UsuarioActual.isLogged()) {
            this.setVisible(false);
            switch (juegoObjetivo) {
                case CABALLOS -> new FrameCaballos(this).setVisible(true);
                case RULETA -> new FrameRuleta(this).setVisible(true);
                case SLOTS -> new FrameSlots(this).setVisible(true);
                case BLACKJACK -> new FrameBlackjack(this).setVisible(true);
                case MINAS -> new FrameMinas(this).setVisible(true);
                case DINOSAURIO -> new FrameDino(this).setVisible(true);
                default ->
                    JOptionPane.showMessageDialog(this, "Juego no disponible", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            if (!loginAbierto) {
                loginAbierto = true;
                new DialogLogIn(FrameMenuPrincipal.this, juegoObjetivo).setVisible(true);
            }
        }
    }

    private void cerrarSesion() {
        UsuarioActual.logout();
        actualizarEstado();
    }

    public void actualizarEstado() {
        if (UsuarioActual.isLogged()) {
            labelBienvenida
                    .setText("Te damos la bienvenida " + UsuarioActual.getUsuarioActual() + "! Ya estás logueado.");
            botonLogIn.setVisible(false);
            botonPerfil.setVisible(true);
            labelSaldo.setVisible(true);
            botonPerfil.setText(" " + UsuarioActual.getUsuarioActual());
            labelSaldo.setText("Saldo: " + GestorBD.obtenerSaldo(UsuarioActual.getUsuarioActual()) + " fichas  ");
            labelSaldo.setFont(labelSaldo.getFont().deriveFont(15.0f));
            labelSaldo.setForeground(Color.WHITE);
            GestorBD.setLblMainMenu(labelSaldo);
            botonSalir.setText("Cerrar Sesión");
        } else {
            labelBienvenida.setText("Te damos la bienvenida al Menú Principal, ¿A qué desea jugar?");
            botonPerfil.setVisible(false);
            botonLogIn.setVisible(true);
            labelSaldo.setVisible(false);
            labelSaldo.setText("");
            botonSalir.setText("Salir");
        }
    }

    private void enableDarkMode() {
        ConfigProperties.setUiDarkMode(true);
        actualizarColores(ColorVariables.COLOR_FONDO_DARK.getColor(), ColorVariables.COLOR_TEXTO_DARK.getColor(),
                ColorVariables.COLOR_ROJO_DARK.getColor());
    }

    private void disableDarkMode() {
        ConfigProperties.setUiDarkMode(false);
        actualizarColores(ColorVariables.COLOR_FONDO_LIGHT.getColor(), ColorVariables.COLOR_TEXTO_LIGHT.getColor(),
                ColorVariables.COLOR_ROJO_LIGHT.getColor());
    }

    private void actualizarColores(Color fondo, Color texto, Color rojo) {
        panelSeleccion.setBackground(fondo);
        labelBienvenida.setForeground(texto);
        panelCentral.setBackground(fondo);
        barraAlta.setBackground(rojo);
        panelDerechaBarraAlta.setBackground(rojo);
        panelIzquierdaBarraAlta.setBackground(rojo);
        titulo.setForeground(texto);
        UIManager.put("OptionPane.background", fondo);
        UIManager.put("Panel.background", fondo);
        UIManager.put("OptionPane.messageForeground", texto);
    }
}
