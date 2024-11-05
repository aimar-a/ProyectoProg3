// https://turbo.spribegaming.com/mines?currency=USD&operator=demo&jurisdiction=CW&lang=EN&return_url=https:%2F%2Fspribe.co%2Fgames&user=22623&token=xJh3K8yHkNth5ndWAqdYAol5o0OBgB0u
package GUI.minas;

import java.awt.*;
import java.util.Random;
import javax.swing.*;

public class PanelMinas extends JPanel {

    private JButton[][] botones = new JButton[5][5]; // Matriz de botones

    public PanelMinas() {
        setLayout(new GridBagLayout());
        setBackground(Color.BLACK);
        GridBagConstraints gbc = new GridBagConstraints();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                gbc.gridx = j;
                gbc.gridy = i;
                botones[i][j] = new JButton();
                botones[i][j].setContentAreaFilled(false); // No se rellena el área del botón
                botones[i][j].setBorderPainted(false); // No se dibuja el borde del botón
                botones[i][j].setFocusPainted(false);
                botones[i][j].setMargin(new Insets(0, 0, 0, 0));
                Random r = new Random();
                int random = r.nextInt(3) + 1;
                botones[i][j].setIcon(new ImageIcon("src/img/minas/box" + random + ".png"));
                add(botones[i][j], gbc);
            }
        }
    }

    public final void iniciarJuego(int nMinas) {
        Random r = new Random();
        int minas[][] = new int[nMinas][2];
        for (int i = 0; i < nMinas; i++) {
            minas[i][0] = r.nextInt(5);
            minas[i][1] = r.nextInt(5);
            for (int j = 0; j < i; j++) {
                if (minas[i][0] == minas[j][0] && minas[i][1] == minas[j][1]) {
                    i--;
                    break;
                }
            }
        }
    }
}
