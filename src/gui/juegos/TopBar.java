package gui.juegos;

import db.GestorBD;
import domain.UsuarioActual;
import gui.ColorVariables;
import gui.mainMenu.FrameMenuPrincipal;
import io.ConfigProperties;
import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

//IAG: GitHub Copilot
//ADAPTADO: Anadir funcionalidades y autocompeltado
public class TopBar extends JPanel {

    private static final long serialVersionUID = 1L;

    public TopBar(String title, JFrame frame, FrameMenuPrincipal frameMenuPrincipal) {
        setLayout(new BorderLayout());
        JLabel lblTitle = new JLabel(title, SwingConstants.CENTER);
        lblTitle.setFont(lblTitle.getFont().deriveFont(20.0f));
        add(lblTitle, BorderLayout.CENTER);
        JButton btnSalir = new JButton("<- Volver");
        btnSalir.setFocusable(false);
        add(btnSalir, BorderLayout.WEST);
        btnSalir.addActionListener(e -> {
            frameMenuPrincipal.setVisible(true);
            frameMenuPrincipal.requestFocus();
            frame.dispose();
        });
        JLabel lblSaldo = new JLabel(
                "Saldo: " + GestorBD.obtenerSaldo(UsuarioActual.getUsuarioActual()) + " fichas  ");
        lblSaldo.setFont(lblSaldo.getFont().deriveFont(15.0f));
        add(lblSaldo, BorderLayout.EAST);
        GestorBD.setLblGameMenu(lblSaldo);

        if (ConfigProperties.isUiDarkMode()) {
            setBackground(ColorVariables.COLOR_VERDE_DARK.getColor());
            lblTitle.setForeground(ColorVariables.COLOR_TEXTO_DARK.getColor());
        } else {
            setBackground(ColorVariables.COLOR_VERDE_LIGHT.getColor());
            lblTitle.setForeground(ColorVariables.COLOR_TEXTO_LIGHT.getColor());
        }

        // Add KeyListener to handle Esc key press
        frame.setFocusable(true);
        frame.requestFocus();
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    frameMenuPrincipal.setVisible(true);
                    frameMenuPrincipal.requestFocus();
                    frame.dispose();
                }
            }
        });
    }
}
