package GUI.slots;

import GUI.generalGames.MainFrame;
import GUI.mainMenu.FrameMenuPrincipal;
import java.awt.BorderLayout;

public class FrameSlots extends MainFrame {
	public FrameSlots(FrameMenuPrincipal frameMenuPrincipal) {
		super("Slots", frameMenuPrincipal);

		PanelSlots panelSlots = new PanelSlots();
		add(panelSlots, BorderLayout.CENTER);

		PanelApuestasSlots panelApuestas = new PanelApuestasSlots();
		add(panelApuestas, BorderLayout.SOUTH);

		new LogicaSlots(panelSlots, panelApuestas);

		panelSlots.girarRuletas();
	}

	public static void main(String[] args) {
		new FrameSlots(null).setVisible(true);
	}
}
