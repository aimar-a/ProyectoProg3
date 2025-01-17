package gui.juegos.slots;

import gui.ColorVariables;
import gui.juegos.BaseGamesFrame;
import gui.mainMenu.FrameMenuPrincipal;
import java.awt.BorderLayout;

public class FrameSlots extends BaseGamesFrame {
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
			setBackground(ColorVariables.COLOR_FONDO_DARK.getColor());
		} else {
			setBackground(ColorVariables.COLOR_FONDO_LIGHT.getColor());
		}

		panelSlots.girarRuletas();
	}
}
