package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class VentanaSlot extends JFrame {
	JButton f1c1;
	JButton f1c2;
	JButton f1c3;
	JButton f2c1;
	JButton f2c2;
	JButton f2c3;
	JButton f3c1;
	JButton f3c2;
	JButton f3c3;
	JButton[] botones = { f1c1, f1c2, f1c3, f2c1, f2c2, f2c3, f3c1, f3c2, f3c3 };

	public VentanaSlot() {
		setTitle("Slot");
		setSize(1600, 1400);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());

		JPanel panelRuletas = new JPanel(new GridBagLayout());
		panelRuletas.setBackground(Color.BLACK);
		GridBagConstraints gbc = new GridBagConstraints();
		for (int i = 0; i < botones.length; i++) {
			gbc.gridx = i % 3;
			gbc.gridy = i / 3;
			botones[i] = new JButton();
			panelRuletas.add(botones[i], gbc);
		}
		girarRuletas();

		JPanel panelApuestas = new JPanel();
		panelApuestas.setBackground(Color.RED);
		panelApuestas.add(new JLabel("Apuesta: "));
		panelApuestas.add(new JTextArea(1, 6));
		JButton botonGirar = new JButton("Girar");
		botonGirar.addActionListener(e -> girarRuletas());
		panelApuestas.add(botonGirar);

		add(panelApuestas, BorderLayout.SOUTH);
		add(panelRuletas, BorderLayout.CENTER);
		setVisible(true);
	}

	public void girarRuletas() {
		for (int i = 0; i < botones.length; i++) {
			Random r = new Random();
			int n = r.nextInt(9) + 1;
			botones[i].setIcon(new ImageIcon("src/img/slot" + n + ".png"));
		}
	}
}
