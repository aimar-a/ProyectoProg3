package gui.juegos.ruleta;

import gui.juegos.BaseGamesFrame;
import gui.mainMenu.FrameMenuPrincipal;
import java.awt.BorderLayout;
import javax.swing.JPanel;

public class FrameRuleta extends BaseGamesFrame {

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
