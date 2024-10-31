package GUI;

import java.awt.BorderLayout;


import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class VentanaSlot extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public VentanaSlot() {
	        setTitle("Slot");
	        setSize(400, 300);
	        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        setLocationRelativeTo(null);
	        JLabel label = new JLabel("¡Bienvenido a los Slots!", SwingConstants.CENTER);
	        add(label, BorderLayout.CENTER);

	        setVisible(true);
	    }
	}
