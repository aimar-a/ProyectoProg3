package GUI.slots;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class PanelSlots extends JPanel {
    JButton[][] botones = new JButton[3][3];

    public PanelSlots() {
        setLayout(new GridBagLayout());
        setBackground(Color.BLACK);
        GridBagConstraints gbc = new GridBagConstraints();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                gbc.gridx = j;
                gbc.gridy = i;
                botones[i][j] = new JButton();
                add(botones[i][j], gbc);
            }
        }
    }

    public final void girarRuletas() {
        for (JButton[] botone : botones) {
            for (JButton botone1 : botone) {
                Random r = new Random();
                int n = r.nextInt(9) + 1;
                botone1.setIcon(new ImageIcon("src/img/slots/slot" + n + ".png"));
            }
        }
    }
}
