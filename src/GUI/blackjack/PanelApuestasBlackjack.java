// Código inspirado por el tutorial "Code Black Jack in Java" de [Kenny Yip Coding] en YouTube.
// URL: https://www.youtube.com/watch?v=GMdgjaDdOjI 
package GUI.blackjack;

import GUI.ColorVariables;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

//IAG: Modificado (ChatGPT y GitHub Copilot)
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
        botonIniciar.setEnabled(false); // Iniciar deshabilitado por defecto

        // Etiqueta y Spinner para la apuesta
        add(new JLabel("Apuesta: "));
        add(spinnerApuesta);

        // Listener para habilitar o deshabilitar el botón según la apuesta
        spinnerApuesta.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                // Verificar si la apuesta es mayor a 0
                int apuesta = (int) spinnerApuesta.getValue();
                botonIniciar.setEnabled(apuesta > 0);
            }
        });

        // Agregar botones y elementos adicionales
        add(botonIniciar);
        add(checkAutomatico);
        add(new JLabel(" | "));
        add(botonPedir);
        add(botonPlantarse);

        if (darkMode) {
            setBackground(ColorVariables.COLOR_ROJO_DARK);
            setForeground(ColorVariables.COLOR_TEXTO_DARK);
        } else {
            setBackground(ColorVariables.COLOR_ROJO_LIGHT);
            setForeground(ColorVariables.COLOR_TEXTO_LIGHT);
        }
    }

    // Obtener la cantidad apostada
    public int getCantidadApuesta() {
        return (int) spinnerApuesta.getValue();
    }
}
