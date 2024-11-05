package GUI.ruleta;

import GUI.generalGames.MainFrame;
import GUI.mainMenu.FrameMenuPrincipal;
import java.awt.BorderLayout;
import javax.swing.JPanel;

public class FrameRuleta extends MainFrame {

	public FrameRuleta(FrameMenuPrincipal frameMenuPrincipal) {
		super("Ruleta", frameMenuPrincipal);

		JPanel centralPanel = new JPanel();
		add(centralPanel, BorderLayout.CENTER);

		PanelRuleta roulettePanel = new PanelRuleta();
		centralPanel.add(roulettePanel);

		PanelTablaDeApuestas bettingPanel = new PanelTablaDeApuestas();
		centralPanel.add(bettingPanel);
	}
}
