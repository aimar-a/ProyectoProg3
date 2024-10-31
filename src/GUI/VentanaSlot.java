package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

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

	public VentanaSlot() {
		setTitle("Slot");
		setSize(1600, 1400);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());

		JPanel panelRuletas = new JPanel(new GridBagLayout());
		panelRuletas.setBackground(Color.BLACK);
		GridBagConstraints gbc = new GridBagConstraints();
		f1c1 = new JButton();
		panelRuletas.add(f1c1, gbc);
		f1c2 = new JButton();
		panelRuletas.add(f1c2, gbc);
		f1c3 = new JButton();
		panelRuletas.add(f1c3, gbc);
		f2c1 = new JButton();
		panelRuletas.add(f2c1, gbc);
		f2c2 = new JButton();
		panelRuletas.add(f2c2, gbc);
		f2c3 = new JButton();
		panelRuletas.add(f2c3, gbc);
		f3c1 = new JButton();
		panelRuletas.add(f3c1, gbc);
		f3c2 = new JButton();
		panelRuletas.add(f3c2, gbc);
		f3c3 = new JButton();
		panelRuletas.add(f3c3, gbc);
		girarRuletas();

		JPanel panelApuestas = new JPanel();
		panelApuestas.setBackground(Color.RED);
		panelApuestas.add(new JLabel("Apuesta: "));
		panelApuestas.add(new JTextArea(1, 6));
		panelApuestas.add(new JButton("Girar"));

		add(panelApuestas, BorderLayout.SOUTH);
		add(panelRuletas, BorderLayout.CENTER);
		setVisible(true);
	}

	public void girarRuletas() {

	}
}
