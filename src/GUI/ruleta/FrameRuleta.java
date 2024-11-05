package GUI.ruleta;

import GUI.generalGames.MainFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;

public class FrameRuleta extends MainFrame {

	public FrameRuleta() {
		super("Ruleta");

		JPanel centralPanel = new JPanel();
		add(centralPanel, BorderLayout.CENTER);

		PanelRuleta roulettePanel = new PanelRuleta();
		centralPanel.add(roulettePanel);

		PanelTablaDeApuestas bettingPanel = new PanelTablaDeApuestas();
		centralPanel.add(bettingPanel);
	}
}
