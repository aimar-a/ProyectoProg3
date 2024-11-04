package GUI;

import java.awt.*;
import javax.swing.*;

public class RoulettePanel extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int tamanoBoton = 60;

    public RoulettePanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JButton btn0 = new JButton("0");
        btn0.setBackground(Color.GREEN);
        btn0.setOpaque(true);
        btn0.setBorderPainted(true);
        gbc.ipadx = tamanoBoton;
        gbc.ipady = tamanoBoton;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 3;
        gbc.fill = GridBagConstraints.VERTICAL;
        add(btn0, gbc);
        gbc.gridheight = 1;

        int[] nums = { 3, 6, 9, 12, 15, 18, 21, 24, 27, 30, 33, 36, 2, 5, 8, 11, 14, 17, 20, 23, 26, 29, 32, 35, 1,
                4, 7, 10, 13, 16, 19, 22, 25, 28, 31, 34 };
        boolean[] isRed = { true, false, true, true, false, true, true, false, true, true, false, true,
                false, true, false, false, true, false, false, true, false, false, true, false,
                true, false, true, false, false, true, false, false, true, true, false, true, false, false, true };
        for (int i = 0; i < 36; i++) {
            JButton btn = new JButton(String.valueOf(nums[i]));
            if (isRed[i]) {
                btn.setBackground(Color.RED);
            } else {
                btn.setBackground(Color.BLACK);
            }
            btn.setForeground(Color.WHITE);
            btn.setOpaque(true);
            btn.setBorderPainted(true);
            gbc.gridx = 1 + i % 12;
            gbc.gridy = i / 12;
            add(btn, gbc);
        }

        gbc.gridx = 13;
        for (int i = 0; i < 3; i++) {
            JButton btn = new JButton("L" + (i + 1));
            btn.setBackground(Color.GRAY);
            btn.setForeground(Color.WHITE);
            btn.setOpaque(true);
            btn.setBorderPainted(true);
            gbc.gridy = i;
            add(btn, gbc);
        }

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel(" "), gbc);
        gbc.gridwidth = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        for (int i = 1; i < 4; i++) {
            JButton btn = new JButton((i * 12 - 11) + "-" + (i * 12));
            btn.setBackground(Color.GRAY);
            btn.setForeground(Color.WHITE);
            btn.setOpaque(true);
            btn.setBorderPainted(true);
            gbc.gridx = i * 4 - 3;
            add(btn, gbc);
        }

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel(" "), gbc);
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        String[] apuestas = { "Par", "Impar", "Rojo", "Negro", "1-18", "19-36" };
        for (int i = 0; i < 6; i++) {
            JButton btn = new JButton(apuestas[i]);
            if (apuestas[i].equals("Rojo")) {
                btn.setBackground(Color.RED);
            } else if (apuestas[i].equals("Negro")) {
                btn.setBackground(Color.BLACK);
            } else {
                btn.setBackground(Color.GRAY);
            }
            btn.setForeground(Color.WHITE);
            btn.setOpaque(true);
            btn.setBorderPainted(true);
            gbc.gridx = i * 2 + 1;
            add(btn, gbc);
        }

    }
}