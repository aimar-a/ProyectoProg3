// https://www.freeslots.com/es/Slot21.htm 
package GUI.slots;

import datos.GestorMovimientos;

public class LogicaSlots {
    private final PanelSlots panelSlots;
    private final PanelApuestasSlots panelApuestas;
    private Thread hilo;
    private int apuesta;
    private Premios premios = new Premios();
    private String usuario;

    public LogicaSlots(PanelSlots panelSlots, PanelApuestasSlots panelApuestas, String usuario) {
        this.panelSlots = panelSlots;
        this.panelApuestas = panelApuestas;
        this.usuario = usuario;

        panelApuestas.botonGirar.addActionListener(e -> realizarTirada());
        panelSlots.girarRuletas();
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
            int mult = premios.comprobarResultado(panelSlots.getIntsSlots());
            if (mult > 0) {
                GestorMovimientos.agregarMovimiento(usuario, apuesta * mult, "victoria:slots");
            }
            panelSlots.setLabelRecompensa(apuesta + "x" + mult + " = " + apuesta * mult);
            interrupt();
        }
    }

    public void realizarTirada() {
        if (hilo != null && hilo.isAlive()) {
            return;
        }
        this.apuesta = panelApuestas.getApuesta();
        GestorMovimientos.agregarMovimiento(usuario, -apuesta, "apuesta:slots");
        hilo = new Hilo();
        hilo.start();
    }

}
