package gui.juegos.caballos;

import gui.juegos.BaseGamesFrame;
import gui.mainMenu.FrameMenuPrincipal;
import java.awt.BorderLayout;
import javax.swing.SwingUtilities;

public class FrameCaballos extends BaseGamesFrame {

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
