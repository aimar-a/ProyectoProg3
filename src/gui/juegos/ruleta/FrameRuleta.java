package gui.juegos.ruleta;

import gui.juegos.BaseGamesFrame;
import gui.mainMenu.FrameMenuPrincipal;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class FrameRuleta extends BaseGamesFrame {

	private static final long serialVersionUID = 1L;

	public FrameRuleta(FrameMenuPrincipal frameMenuPrincipal) {
		super("Ruleta", frameMenuPrincipal);

		SwingUtilities.invokeLater(() -> {
			JPanel centralPanel = new JPanel();
			add(centralPanel, BorderLayout.CENTER);

			PanelRuleta roulettePanel = new PanelRuleta();
			centralPanel.add(roulettePanel);

			PanelTablaDeApuestas bettingPanel = new PanelTablaDeApuestas(roulettePanel);
			centralPanel.add(bettingPanel);
		});
	}
}
