package GUI.generalGames;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class TopBar extends JPanel {

    private static final long serialVersionUID = 1L;

    public TopBar(String title, JFrame frame) {
        setLayout(new BorderLayout());
        setBackground(Color.GREEN);
        add(new JLabel(title, SwingConstants.CENTER));
        JButton btnSalir = new JButton("<- Volver");
        add(btnSalir, BorderLayout.WEST);
        btnSalir.addActionListener(e -> {
            frame.dispose();
        });
    }
}
