package GUI.slots;

import GUI.generalGames.MainFrame;
import GUI.mainMenu.FrameMenuPrincipal;
import java.awt.BorderLayout;

public class FrameSlots extends MainFrame {
	public FrameSlots(FrameMenuPrincipal frameMenuPrincipal) {
		super("Slots", frameMenuPrincipal);

		PanelSlots panelSlots = new PanelSlots();
		add(panelSlots, BorderLayout.CENTER);

		PanelApuestas panelApuestas = new PanelApuestas(panelSlots);
		add(panelApuestas, BorderLayout.SOUTH);

		panelSlots.girarRuletas();
	}
}
