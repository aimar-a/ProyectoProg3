package GUI.perfil;

import javax.swing.JButton;
import javax.swing.JPanel;

public class PanelSeleccionSuperior extends JPanel {

    /**
    * 
    */
    private static final long serialVersionUID = 1L;

    public PanelSeleccionSuperior() {
        // Configuración del panel
        JButton btnHistorialMovimientos = new JButton("Ver Historial de Movimientos");
        add(btnHistorialMovimientos);

        JButton btnCambiarContraseña = new JButton("Cambiar Contraseña");
        add(btnCambiarContraseña);

        JButton btnDatosPersonales = new JButton("Ver Datos Personales");
        add(btnDatosPersonales);
    }
}
