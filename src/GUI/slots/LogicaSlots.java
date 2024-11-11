// https://www.freeslots.com/es/Slot21.htm 
package GUI.slots;

import java.awt.event.ActionEvent;

public class LogicaSlots {
    private final PanelSlots panelSlots;
    private final PanelApuestasSlots panelApuestas;
    Thread hilo;

    public LogicaSlots(PanelSlots panelSlots, PanelApuestasSlots panelApuestas) {
        this.panelSlots = panelSlots;
        this.panelApuestas = panelApuestas;
        panelApuestas.botonGirar.addActionListener(e -> realizarTirada());
        panelSlots.girarRuletas();
        panelApuestas.apuesta.addActionListener((ActionEvent e) -> {
            realizarTirada();
        });
    }

    public class Hilo extends Thread {
        @Override
        public void run() {
            for (int i = 30; i > 0; i--) {
                try {
                    Thread.sleep(30);
                    if (i > 16) {
                        panelSlots.girarColumna(0);
                    }
                    if (i > 8) {
                        panelSlots.girarColumna(1);
                    }
                    panelSlots.girarColumna(2);
                } catch (InterruptedException ex) {
                }
            }
            interrupt();
        }
    }

    public void realizarTirada() {
        if (hilo != null && hilo.isAlive()) {
            return;
        }
        hilo = new Hilo();
        hilo.start();
    }

}
