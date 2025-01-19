package gui.juegos;

import gui.ColorVariables;
import gui.mainMenu.FrameMenuPrincipal;
import io.ConfigProperties;
import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

//IAG: GitHub Copilot
//ADAPTADO: Autocompeltado
public abstract class BaseGamesFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    public BaseGamesFrame(String title, FrameMenuPrincipal menuPrinc) {
        setTitle(title + " - 007Games");
        setSize(1600, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        if (ConfigProperties.isUiFullScreen()) {
            setUndecorated(true);
        }

        add(new TopBar(title + " - 007Games", this, menuPrinc), BorderLayout.NORTH);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                menuPrinc.setVisible(true);
                menuPrinc.requestFocus();
            }
        });

        if (ConfigProperties.isUiDarkMode()) {
            getContentPane().setBackground(ColorVariables.COLOR_FONDO_DARK.getColor());
        } else {
            getContentPane().setBackground(ColorVariables.COLOR_FONDO_LIGHT.getColor());
        }
    }

}
