package GUI.slots;

import GUI.ColorVariables;
import GUI.generalGames.MainFrame;
import GUI.mainMenu.FrameMenuPrincipal;
import java.awt.BorderLayout;

public class FrameSlots extends MainFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FrameSlots(FrameMenuPrincipal frameMenuPrincipal, String usuario, boolean darkMode) {
		super("Slots", frameMenuPrincipal, darkMode);

		PanelSlots panelSlots = new PanelSlots(darkMode);
		add(panelSlots, BorderLayout.CENTER);

		PanelApuestasSlots panelApuestas = new PanelApuestasSlots(darkMode);
		add(panelApuestas, BorderLayout.SOUTH);

		new LogicaSlots(panelSlots, panelApuestas, usuario);

		if (darkMode) {
			setBackground(ColorVariables.COLOR_FONDO_DARK);
		} else {
			setBackground(ColorVariables.COLOR_FONDO_LIGHT);
		}

		panelSlots.girarRuletas();
	}
}
