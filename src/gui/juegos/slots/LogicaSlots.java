// https://www.freeslots.com/es/Slot21.htm 
package gui.juegos.slots;

import db.GestorBD;
import domain.UsuarioActual;
import domain.datos.AsuntoMovimiento;
import domain.slots.Premios;
import javax.swing.JOptionPane;

//IAG: ChatGPT y GitHub Copilot
//ADAPTADO: Ordenar y limpiar código, anadir funcionalidades y autocompeltado
public class LogicaSlots {
    private final PanelSlots panelSlots;
    private final PanelApuestasSlots panelApuestas;
    private Thread hilo;
    private int apuesta;
    private Premios premios = new Premios();
    private String usuario;

    public LogicaSlots(PanelSlots panelSlots, PanelApuestasSlots panelApuestas) {
        this.panelSlots = panelSlots;
        this.panelApuestas = panelApuestas;
        this.usuario = UsuarioActual.getUsuarioActual();

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
                GestorBD.agregarMovimiento(usuario, apuesta * mult, AsuntoMovimiento.SLOTS_PREMIO);
            }
            panelSlots.setLabelRecompensa(apuesta + "x" + mult + " = " + apuesta * mult);
            interrupt();
        }
    }

    public void realizarTirada() {
        if (hilo != null && hilo.isAlive()) {
            return;
        }
        if (panelApuestas.getApuesta() == 0) {
            JOptionPane.showMessageDialog(panelSlots, "Introduce una apuesta", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        this.apuesta = panelApuestas.getApuesta();
        if (GestorBD.obtenerSaldo(usuario) < apuesta) {
            JOptionPane.showMessageDialog(panelSlots, "Saldo insuficiente", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        GestorBD.agregarMovimiento(usuario, -apuesta, AsuntoMovimiento.SLOTS_APUESTA);
        hilo = new Hilo();
        hilo.start();
    }

}
