package gui.juegos.ruleta;

import gui.juegos.BaseGamesFrame;
import gui.mainMenu.FrameMenuPrincipal;
import java.awt.BorderLayout;
import javax.swing.JPanel;

public class FrameRuleta extends BaseGamesFrame {

	public FrameRuleta(FrameMenuPrincipal frameMenuPrincipal) {
		super("Ruleta", frameMenuPrincipal);

		JPanel centralPanel = new JPanel();
		add(centralPanel, BorderLayout.CENTER);

		PanelRuleta roulettePanel = new PanelRuleta();
		centralPanel.add(roulettePanel);

		PanelTablaDeApuestas bettingPanel = new PanelTablaDeApuestas(roulettePanel);
		centralPanel.add(bettingPanel);
	}
}
