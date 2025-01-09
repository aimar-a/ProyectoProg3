package juegos.ruleta;

import juegos.MainFrame;

import java.awt.BorderLayout;
import javax.swing.JPanel;

import gui.mainMenu.FrameMenuPrincipal;

public class FrameRuleta extends MainFrame {

	public FrameRuleta(FrameMenuPrincipal frameMenuPrincipal, String usuario, boolean darkMode) {
		super("Ruleta", frameMenuPrincipal, darkMode);

		JPanel centralPanel = new JPanel();
		add(centralPanel, BorderLayout.CENTER);

		PanelRuleta roulettePanel = new PanelRuleta(darkMode);
		centralPanel.add(roulettePanel);

		PanelTablaDeApuestas bettingPanel = new PanelTablaDeApuestas(roulettePanel, usuario, darkMode);
		centralPanel.add(bettingPanel);
	}
}
