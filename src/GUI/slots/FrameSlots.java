package GUI.slots;

import GUI.generalGames.MainFrame;
import java.awt.BorderLayout;

public class FrameSlots extends MainFrame {
	public FrameSlots() {
		super("Slots");

		PanelSlots panelSlots = new PanelSlots();
		add(panelSlots, BorderLayout.CENTER);

		PanelApuestas panelApuestas = new PanelApuestas(panelSlots);
		add(panelApuestas, BorderLayout.SOUTH);

		panelSlots.girarRuletas();
	}
}
