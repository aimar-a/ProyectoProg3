package gui.juegos;

import gui.ColorVariables;
import gui.mainMenu.FrameMenuPrincipal;
import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

public abstract class BaseGamesFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    public BaseGamesFrame(String title, FrameMenuPrincipal menuPrinc, boolean darkMode) {
        setTitle(title + " - 007Games");
        setSize(1600, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        add(new TopBar(title + " - 007Games", this, menuPrinc, darkMode), BorderLayout.NORTH);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                menuPrinc.setVisible(true);
            }
        });

        if (darkMode) {
            getContentPane().setBackground(ColorVariables.COLOR_FONDO_DARK.getColor());
        } else {
            getContentPane().setBackground(ColorVariables.COLOR_FONDO_LIGHT.getColor());
        }
    }

}
