package GUI.generalGames;

import GUI.mainMenu.FrameMenuPrincipal;
import datos.GestorMovimientos;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class TopBar extends JPanel {

    private static final long serialVersionUID = 1L;

    public TopBar(String title, JFrame frame, FrameMenuPrincipal frameMenuPrincipal) {
        setLayout(new BorderLayout());
        setBackground(Color.GREEN);
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
                "Saldo: " + GestorMovimientos.obtenerSaldo(frameMenuPrincipal.getUsuario()) + " fichas  ");
        lblSaldo.setFont(lblSaldo.getFont().deriveFont(15.0f));
        lblSaldo.setForeground(Color.WHITE);
        add(lblSaldo, BorderLayout.EAST);
        GestorMovimientos.setLabelGameMenu(lblSaldo);
    }
}
