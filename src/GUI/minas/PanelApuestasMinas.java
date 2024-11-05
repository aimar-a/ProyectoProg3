package GUI.minas;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PanelApuestasMinas extends JPanel {

    private static final long serialVersionUID = 1L;

    public PanelApuestasMinas() {
        add(new JLabel("Apuestas"));
        JTextField txtApuesta = new JTextField();
        txtApuesta.setColumns(10);
        add(txtApuesta);
        JButton btnApostar = new JButton("Apostar");
        add(btnApostar);
        btnApostar.addActionListener(e -> {

        });
    }

}
