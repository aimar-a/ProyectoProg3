package GUI.caballos;

import GUI.generalGames.MainFrame;
import java.awt.BorderLayout;

public class FrameCaballos extends MainFrame {

    public FrameCaballos() {
        super("Carreras de caballos");

        PanelCaballos carreraCaballos = new PanelCaballos();
        add(carreraCaballos, BorderLayout.CENTER);

        PanelApuestasCaballos apuestasCaballos = new PanelApuestasCaballos(carreraCaballos);
        add(apuestasCaballos, BorderLayout.SOUTH);
    }
}
	