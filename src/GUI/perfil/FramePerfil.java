package GUI.perfil;

import GUI.mainMenu.FrameMenuPrincipal;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

public class FramePerfil extends JDialog {
    JButton btnPerfil = new JButton("Ver Perfil");
    JButton btnDespositarRetirar = new JButton("Depositar/Retirar");
    JButton btnHistorialMovimientos = new JButton("Ver Historial de Movimientos");
    JButton btnCambiarContraseña = new JButton("Cambiar Contraseña");

    JPanel panelCentralPerfil = new JPanel();
    String usuario;

    public FramePerfil(FrameMenuPrincipal parent) {
        super(parent, "Perfil del Usuario", true);
        this.usuario = parent.getUsuario();

        setSize(900, 1000);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        inicializarPanelSuperior();

        // Establecer el panel inicial
        panelCentralPerfil = new PanelDatosUsuario(usuario);
        add(panelCentralPerfil, BorderLayout.CENTER);
    }

    private void inicializarPanelSuperior() {
        btnPerfil.setEnabled(false);
        btnPerfil.addActionListener(e -> {
            cambiarPanel(new PanelDatosUsuario(usuario));
            btnPerfil.setEnabled(false);
            btnDespositarRetirar.setEnabled(true);
            btnHistorialMovimientos.setEnabled(true);
            btnCambiarContraseña.setEnabled(true);
        });

        btnDespositarRetirar.addActionListener(e -> {
            cambiarPanel(new PanelDepositarRetirar(usuario));
            btnPerfil.setEnabled(true);
            btnDespositarRetirar.setEnabled(false);
            btnHistorialMovimientos.setEnabled(true);
            btnCambiarContraseña.setEnabled(true);
        });

        btnHistorialMovimientos.addActionListener(e -> {
            cambiarPanel(new PanelHistorialMovimientos(usuario));
            btnPerfil.setEnabled(true);
            btnDespositarRetirar.setEnabled(true);
            btnHistorialMovimientos.setEnabled(false);
            btnCambiarContraseña.setEnabled(true);
        });

        btnCambiarContraseña.addActionListener(e -> {
            cambiarPanel(new PanelCambiarContrasena(usuario));
            btnPerfil.setEnabled(true);
            btnDespositarRetirar.setEnabled(true);
            btnHistorialMovimientos.setEnabled(true);
            btnCambiarContraseña.setEnabled(false);
        });

        JPanel panelSuperior = new JPanel();
        panelSuperior.add(btnPerfil);
        panelSuperior.add(btnDespositarRetirar);
        panelSuperior.add(btnHistorialMovimientos);
        panelSuperior.add(btnCambiarContraseña);
        add(panelSuperior, BorderLayout.NORTH);
    }

    // IAG: Método para cambiar el panel central
    private void cambiarPanel(JPanel nuevoPanel) {
        remove(panelCentralPerfil); // Eliminar el panel anterior
        panelCentralPerfil = nuevoPanel; // Establecer el nuevo panel
        add(panelCentralPerfil, BorderLayout.CENTER); // Agregar el nuevo panel
        revalidate(); // Asegurarse de que el layout se actualice
        repaint(); // Redibujar el componente
    }
}
