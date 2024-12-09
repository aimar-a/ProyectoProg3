package GUI.generalGames;

import GUI.ColorVariables;
import GUI.mainMenu.FrameMenuPrincipal;
import datos.GestorBD;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
//IAG: Modificado (ChatGPT y GitHub Copilot)
public class TopBar extends JPanel {

    private static final long serialVersionUID = 1L;

    public TopBar(String title, JFrame frame, FrameMenuPrincipal frameMenuPrincipal, boolean darkMode) {
        setLayout(new BorderLayout());
        JLabel lblTitle = new JLabel(title, SwingConstants.CENTER);
        lblTitle.setFont(lblTitle.getFont().deriveFont(20.0f));
        add(lblTitle, BorderLayout.CENTER);
        JButton btnSalir = new JButton("<- Volver");
        btnSalir.setFocusable(false);
        add(btnSalir, BorderLayout.WEST);
        btnSalir.addActionListener(e -> {
            frameMenuPrincipal.setVisible(true);
            frame.dispose();
        });
        JLabel lblSaldo = new JLabel(
                "Saldo: " + GestorBD.obtenerSaldo(frameMenuPrincipal.getUsuario()) + " fichas  ");
        lblSaldo.setFont(lblSaldo.getFont().deriveFont(15.0f));
        add(lblSaldo, BorderLayout.EAST);
        GestorBD.setLblGameMenu(lblSaldo);

        if (darkMode) {
            setBackground(ColorVariables.COLOR_VERDE_DARK);
            lblTitle.setForeground(ColorVariables.COLOR_TEXTO_DARK);
        } else {
            setBackground(ColorVariables.COLOR_VERDE_LIGHT);
            lblTitle.setForeground(ColorVariables.COLOR_TEXTO_LIGHT);
        }
    }
}
