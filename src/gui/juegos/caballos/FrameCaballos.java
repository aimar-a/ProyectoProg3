package gui.juegos.caballos;

import gui.juegos.BaseGamesFrame;
import gui.mainMenu.FrameMenuPrincipal;
import java.awt.BorderLayout;
import javax.swing.SwingUtilities;

//IAG: GitHub Copilot
//ADAPTADO: Autocompeltado
public class FrameCaballos extends BaseGamesFrame {

	private static final long serialVersionUID = -8690598750646963543L;

	public FrameCaballos(FrameMenuPrincipal frameMenuPrincipal) {
        super("Carreras de caballos", frameMenuPrincipal);

        SwingUtilities.invokeLater(() -> {
            PanelCaballos carreraCaballos = new PanelCaballos();
            add(carreraCaballos, BorderLayout.CENTER);

            PanelApuestasCaballos apuestasCaballos = new PanelApuestasCaballos();
            add(apuestasCaballos, BorderLayout.SOUTH);

            carreraCaballos.setPanelApuestasCaballos(apuestasCaballos);
            apuestasCaballos.setPanelCaballos(carreraCaballos);
        });
    }
}
