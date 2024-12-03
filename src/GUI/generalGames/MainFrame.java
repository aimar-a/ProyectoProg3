package GUI.generalGames;

import GUI.mainMenu.FrameMenuPrincipal;
import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

public class MainFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    public MainFrame(String title, FrameMenuPrincipal menuPrinc) {
        setTitle(title + " - 007Games");
        setSize(1600, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        add(new TopBar(title + " - 007Games", this, menuPrinc), BorderLayout.NORTH);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                menuPrinc.setVisible(true);
            }
        });
    }

}
