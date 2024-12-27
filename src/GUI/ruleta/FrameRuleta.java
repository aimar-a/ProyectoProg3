package GUI.ruleta;

import GUI.generalGames.MainFrame;
import GUI.mainMenu.FrameMenuPrincipal;
import java.awt.BorderLayout;
import javax.swing.JPanel;

public class FrameRuleta extends MainFrame {

	public FrameRuleta(FrameMenuPrincipal frameMenuPrincipal, String usuario, boolean darkMode) {
		super("Ruleta", frameMenuPrincipal, darkMode);

		JPanel centralPanel = new JPanel();
		add(centralPanel, BorderLayout.CENTER);

		PanelRuleta roulettePanel = new PanelRuleta(darkMode);
		centralPanel.add(roulettePanel);

		PanelTablaDeApuestas bettingPanel = new PanelTablaDeApuestas(darkMode);
		centralPanel.add(bettingPanel);

		PanelApuestasRuleta betsPanel = new PanelApuestasRuleta(usuario, roulettePanel, bettingPanel, darkMode);
		add(betsPanel, BorderLayout.SOUTH);
	}
}
