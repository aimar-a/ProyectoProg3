package gui.perfil;

import domain.UsuarioActual;
import gui.ColorVariables;
import gui.mainMenu.FrameMenuPrincipal;
import io.ConfigProperties;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

//IAG: ChatGPT y GitHub Copilot
//ADAPTADO: Ordenar y limpiar código, anadir funcionalidades y autocompeltado
public class FramePerfil extends JDialog {
    JButton btnPerfil = new JButton("Ver Perfil");
    JButton btnDespositarRetirar = new JButton("Depositar/Retirar");
    JButton btnHistorialMovimientos = new JButton("Ver Historial de Movimientos");
    JButton btnCambiarContraseña = new JButton("Cambiar Contraseña");
    JButton btnVolver = new JButton("<- Volver");

    JPanel panelCentralPerfil = new JPanel();
    String usuario;

    private boolean darkMode;

    public FramePerfil(FrameMenuPrincipal parent) {
        super(parent, "Perfil del Usuario", true);
        this.usuario = UsuarioActual.getUsuarioActual();
        this.darkMode = ConfigProperties.isUiDarkMode();

        setSize((int) (getToolkit().getScreenSize().width * 0.4), (int) (getToolkit().getScreenSize().height * 0.8));
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        inicializarPanelSuperior();

        // Establecer el panel inicial
        panelCentralPerfil = new PanelDatosUsuario();
        add(panelCentralPerfil, BorderLayout.CENTER);
    }

    private void inicializarPanelSuperior() {
        btnPerfil.setEnabled(false);
        btnPerfil.addActionListener(e -> {
            cambiarPanel(new PanelDatosUsuario());
            btnPerfil.setEnabled(false);
            btnDespositarRetirar.setEnabled(true);
            btnHistorialMovimientos.setEnabled(true);
            btnCambiarContraseña.setEnabled(true);
        });

        btnDespositarRetirar.addActionListener(e -> {
            cambiarPanel(new PanelDepositarRetirar());
            btnPerfil.setEnabled(true);
            btnDespositarRetirar.setEnabled(false);
            btnHistorialMovimientos.setEnabled(true);
            btnCambiarContraseña.setEnabled(true);
        });

        btnHistorialMovimientos.addActionListener(e -> {
            cambiarPanel(new PanelHistorialMovimientos());
            btnPerfil.setEnabled(true);
            btnDespositarRetirar.setEnabled(true);
            btnHistorialMovimientos.setEnabled(false);
            btnCambiarContraseña.setEnabled(true);
        });

        btnCambiarContraseña.addActionListener(e -> {
            cambiarPanel(new PanelCambiarContrasena());
            btnPerfil.setEnabled(true);
            btnDespositarRetirar.setEnabled(true);
            btnHistorialMovimientos.setEnabled(true);
            btnCambiarContraseña.setEnabled(false);
        });

        btnVolver.addActionListener(e -> {
            dispose();
        });

        JPanel panelSuperior = new JPanel(new BorderLayout());
        JPanel panelEleccion = new JPanel();
        panelSuperior.add(panelEleccion, BorderLayout.CENTER);
        JPanel panelVolver = new JPanel();
        panelVolver.add(btnVolver);
        panelSuperior.add(panelVolver, BorderLayout.WEST);
        panelEleccion.add(btnPerfil);
        panelEleccion.add(btnDespositarRetirar);
        panelEleccion.add(btnHistorialMovimientos);
        panelEleccion.add(btnCambiarContraseña);
        add(panelSuperior, BorderLayout.NORTH);

        if (darkMode) {
            panelSuperior.setBackground(ColorVariables.COLOR_FONDO_DARK.getColor());
        } else {
            panelSuperior.setBackground(ColorVariables.COLOR_FONDO_LIGHT.getColor());
        }
    }

    // IAG: Método para cambiar el panel central (ChatGPT)
    private void cambiarPanel(JPanel nuevoPanel) {
        remove(panelCentralPerfil); // Eliminar el panel anterior
        panelCentralPerfil = nuevoPanel; // Establecer el nuevo panel
        add(panelCentralPerfil, BorderLayout.CENTER); // Agregar el nuevo panel
        revalidate(); // Asegurarse de que el layout se actualice
        repaint(); // Redibujar el componente
    }
}
