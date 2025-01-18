//FUENTE-EXTERNA
// Código inspirado por el tutorial "Code Black Jack in Java" de [Kenny Yip Coding] en YouTube.
// URL: https://www.youtube.com/watch?v=GMdgjaDdOjI 
// ADAPTADO: Se ha modificado el código original para adaptarlo a las necesidades del proyecto y anadir funcionalidades adicionales.

package gui.juegos.blackjack;

import gui.ColorVariables;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

//IAG: GitHub Copilot
//ADAPTADO: Autocompeltado
public class PanelApuestasBlackjack extends JPanel {
    protected final JButton botonIniciar = new JButton("Iniciar Partida");
    protected final JButton botonPedir = new JButton("Pedir");
    protected final JButton botonPlantarse = new JButton("Plantarse");
    protected final JSpinner spinnerApuesta = new JSpinner(new SpinnerNumberModel(0, 0, 10000, 1)); // Mín: 0, Máx:
                                                                                                    // 10000,
                                                                                                    // Incremento: 1
    protected final JCheckBox checkAutomatico = new JCheckBox("Auto");

    public PanelApuestasBlackjack(boolean darkMode) {
        setLayout(new FlowLayout());

        // Deshabilitar botones hasta que comience la partida
        botonPedir.setEnabled(false);
        botonPlantarse.setEnabled(false);

        // Etiqueta y Spinner para la apuesta
        add(new JLabel("Apuesta: "));
        add(spinnerApuesta);

        // Agregar botones y elementos adicionales
        add(botonIniciar);
        add(checkAutomatico);
        add(new JLabel(" | "));
        add(botonPedir);
        add(botonPlantarse);

        if (darkMode) {
            setBackground(ColorVariables.COLOR_ROJO_DARK.getColor());
            setForeground(ColorVariables.COLOR_TEXTO_DARK.getColor());
        } else {
            setBackground(ColorVariables.COLOR_ROJO_LIGHT.getColor());
            setForeground(ColorVariables.COLOR_TEXTO_LIGHT.getColor());
        }
    }

    // Obtener la cantidad apostada
    public int getCantidadApuesta() {
        return (int) spinnerApuesta.getValue();
    }
}
