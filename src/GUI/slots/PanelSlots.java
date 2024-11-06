package GUI.slots;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelSlots extends JPanel {
    JLabel[][] botones = new JLabel[3][3];

    public PanelSlots() {
        setLayout(new GridBagLayout());
        setBackground(Color.BLACK);
        GridBagConstraints gbc = new GridBagConstraints();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                gbc.gridx = j;
                gbc.gridy = i;
                botones[i][j] = new JLabel();
                add(botones[i][j], gbc);
            }
        }
    }

    public final void girarRuletas() {
        for (JLabel[] botone : botones) {
            for (JLabel botone1 : botone) {
                Random r = new Random();
                int n = r.nextInt(11) + 1;
                ImageIcon icono = new ImageIcon(getClass().getResource("/img/slots/slot" + n + ".png"));
                Image scaledImage = icono.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                ImageIcon scaledIcono = new ImageIcon(scaledImage);
                botone1.setIcon(scaledIcono);
            }
        }
    }
}
