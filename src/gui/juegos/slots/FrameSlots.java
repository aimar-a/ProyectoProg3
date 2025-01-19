package gui.juegos.slots;

import gui.juegos.BaseGamesFrame;
import gui.mainMenu.FrameMenuPrincipal;
import java.awt.BorderLayout;
import javax.swing.SwingUtilities;

public class FrameSlots extends BaseGamesFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FrameSlots(FrameMenuPrincipal frameMenuPrincipal) {
		super("Slots", frameMenuPrincipal);

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				PanelSlots panelSlots = new PanelSlots();
				add(panelSlots, BorderLayout.CENTER);

				PanelApuestasSlots panelApuestas = new PanelApuestasSlots();
				add(panelApuestas, BorderLayout.SOUTH);

				new LogicaSlots(panelSlots, panelApuestas);

				panelSlots.girarRuletas();
			}
		});
	}
}
