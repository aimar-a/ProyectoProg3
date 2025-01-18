package gui.juegos.ruleta;

import db.GestorBD;
import domain.UsuarioActual;
import domain.datos.AsuntoMovimiento;
import gui.ColorVariables;
import io.ConfigProperties;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

//IAG: ChatGPT y GitHub Copilot
//ADAPTADO: Ordenar y limpiar código, anadir funcionalidades y autocompeltado
public class PanelTablaDeApuestas extends JPanel {
    private static final long serialVersionUID = 1L;
    private final int tamanoBoton = 50;
    private final JButton[] botonesFichas = new JButton[6];
    private int fichaSeleccionada = 0;
    private final boolean darkMode;
    private HashMap<String, Integer> apuestas = new HashMap<>();
    private HashMap<String, JButton> botonesApuestas = new HashMap<>();
    private boolean apuestasPermitidas = true;
    private final PanelRuleta ruleta;
    private final String usuario;
    private JLabel lblInfo;

    public PanelTablaDeApuestas(PanelRuleta ruleta) {
        this.usuario = UsuarioActual.getUsuarioActual();
        this.ruleta = ruleta;
        this.darkMode = ConfigProperties.isUiDarkMode();
        setLayout(new GridBagLayout());
        setBackground(
                darkMode ? ColorVariables.COLOR_FONDO_DARK.getColor() : ColorVariables.COLOR_FONDO_LIGHT.getColor());
        GridBagConstraints gbc = new GridBagConstraints();

        JButton btn0 = new JButton("0");
        btn0.setBackground(Color.GREEN);
        btn0.setOpaque(true);
        btn0.setBorderPainted(true);
        btn0.setPreferredSize(new Dimension(tamanoBoton, tamanoBoton));
        gbc.ipadx = tamanoBoton;
        gbc.ipady = tamanoBoton;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 3;
        gbc.fill = GridBagConstraints.VERTICAL;
        btn0.addActionListener(l -> ponerFicha("0"));
        add(btn0, gbc);
        gbc.gridheight = 1;
        botonesApuestas.put("0", btn0);

        int[] nums = { 3, 6, 9, 12, 15, 18, 21, 24, 27, 30, 33, 36, 2, 5, 8, 11, 14, 17, 20, 23, 26, 29, 32, 35, 1,
                4, 7, 10, 13, 16, 19, 22, 25, 28, 31, 34 };
        boolean[] isRed = { true, false, true, true, false, true, true, false, true, true, false, true,
                false, true, false, false, true, false, false, true, false, false, true, false,
                true, false, true, false, false, true, false, false, true, true, false, true, false, false, true };
        for (int i = 0; i < 36; i++) {
            JButton btn = new JButton(String.valueOf(nums[i]));
            if (isRed[i]) {
                btn.setBackground(Color.RED);
            } else {
                btn.setBackground(Color.BLACK);
            }
            btn.setForeground(Color.WHITE);
            btn.setBorderPainted(true);
            btn.setPreferredSize(new Dimension(tamanoBoton, tamanoBoton));
            gbc.gridx = 1 + i % 12;
            gbc.gridy = i / 12;
            final int index = i;
            btn.addActionListener(l -> ponerFicha(String.valueOf(nums[index])));
            add(btn, gbc);
            botonesApuestas.put(String.valueOf(nums[i]), btn);
        }

        gbc.gridx = 13;
        for (int i = 0; i < 3; i++) {
            JButton btn = new JButton("L" + (i + 1));
            btn.setBackground(Color.GRAY);
            btn.setForeground(Color.WHITE);
            btn.setBorderPainted(true);
            btn.setPreferredSize(new Dimension(tamanoBoton, tamanoBoton));
            gbc.gridy = i;
            final int index = i;
            btn.addActionListener(l -> ponerFicha("L" + (index + 1)));
            add(btn, gbc);
            botonesApuestas.put("L" + (i + 1), btn);
        }

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        for (int i = 1; i < 4; i++) {
            JButton btn = new JButton((i * 12 - 11) + "-" + (i * 12));
            btn.setBackground(Color.GRAY);
            btn.setForeground(Color.WHITE);
            btn.setBorderPainted(true);
            btn.setPreferredSize(new Dimension(tamanoBoton, tamanoBoton));
            gbc.gridx = i * 4 - 3;
            final int index = i;
            btn.addActionListener(l -> ponerFicha((index * 12 - 11) + "-" + (index * 12)));
            add(btn, gbc);
            botonesApuestas.put((i * 12 - 11) + "-" + (i * 12), btn);
        }

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        String[] apuestas = { "Par", "Impar", "Rojo", "Negro", "1-18", "19-36" };
        for (int i = 0; i < 6; i++) {
            JButton btn = new JButton(apuestas[i]);
            switch (apuestas[i]) {
                case "Rojo" -> btn.setBackground(Color.RED);
                case "Negro" -> btn.setBackground(Color.BLACK);
                default -> btn.setBackground(Color.GRAY);
            }
            btn.setForeground(Color.WHITE);
            btn.setBorderPainted(true);
            btn.setPreferredSize(new Dimension(tamanoBoton, tamanoBoton));
            gbc.gridx = i * 2 + 1;
            final int index = i;
            btn.addActionListener(l -> ponerFicha(apuestas[index]));
            add(btn, gbc);
            botonesApuestas.put(apuestas[i], btn);
        }

        gbc.gridy = 5;
        gbc.gridx = 1;
        lblInfo = new JLabel("Buena suerte!");
        lblInfo.setFont(new Font("Serif", Font.BOLD, 18));
        lblInfo.setHorizontalAlignment(SwingConstants.CENTER);
        lblInfo.setForeground(
                darkMode ? ColorVariables.COLOR_TEXTO_DARK.getColor() : ColorVariables.COLOR_TEXTO_LIGHT.getColor());
        add(lblInfo, gbc);

        gbc.gridwidth = 1;
        String[] fichas = { "ficha_1", "ficha_5", "ficha_25", "ficha_50", "ficha_100", "borra" };
        for (int i = 4; i < 10; i++) {
            gbc.gridx = i;
            ImageIcon icon = new ImageIcon("resources/img/ruleta/" + fichas[i - 4] + ".png");
            Image img = icon.getImage();
            Image scaledImg = img.getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            JButton btn = new JButton(new ImageIcon(scaledImg));
            btn.setPreferredSize(new Dimension(30, 30));
            if (i - 4 == 0) {
                btn.setBackground(Color.YELLOW);
            } else {
                btn.setBackground(darkMode ? ColorVariables.COLOR_BOTON_DARK.getColor()
                        : ColorVariables.COLOR_BOTON_LIGHT.getColor());
            }
            final int index = i - 4;
            btn.addActionListener(l -> seleccionarFicha(index));
            botonesFichas[index] = btn;
            add(btn, gbc);
        }

        gbc.gridx = 11;
        gbc.gridwidth = 2;
        JButton btn = new JButton("Girar");
        btn.setBackground(Color.MAGENTA);
        btn.setForeground(Color.WHITE);
        btn.addActionListener(l -> {
            btn.setEnabled(false);
            setApuestasPermitidas(false);
            lblInfo.setText("No va más!");
            this.ruleta.setNumeroPremiado(-1);
            this.ruleta.spinRoulette();
            new Thread(() -> {
                while (this.ruleta.getNumeroPremiado() == -1) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                int premio = premio(this.ruleta.getNumeroPremiado());
                GestorBD.agregarMovimiento(usuario, premio, AsuntoMovimiento.RULETA_PREMIO);
                JOptionPane.showMessageDialog(this,
                        "Número premiado: " + this.ruleta.getNumeroPremiado() + "\nPremio: " + premio);
                setApuestasPermitidas(true);
                lblInfo.setText("Ultimo premio: " + this.ruleta.getNumeroPremiado());
                this.ruleta.setNumeroPremiado(-1);
                limpiarApuestas();
                btn.setEnabled(true);
            }).start();
        });
        add(btn, gbc);

    }

    protected void setApuestasPermitidas(boolean b) {
        apuestasPermitidas = b;
    }

    private void seleccionarFicha(int ficha) {
        if (fichaSeleccionada == ficha) {
            return;
        }
        fichaSeleccionada = ficha;
        for (int i = 0; i < 6; i++) {
            if (i == ficha) {
                botonesFichas[i].setBackground(Color.YELLOW);
            } else {
                botonesFichas[i].setBackground(darkMode ? ColorVariables.COLOR_BOTON_DARK.getColor()
                        : ColorVariables.COLOR_BOTON_LIGHT.getColor());
            }
        }
    }

    private void ponerFicha(String apuesta) {
        if (!apuestasPermitidas) {
            return;
        }
        switch (fichaSeleccionada) {
            case 0:
                if (GestorBD.agregarMovimiento(usuario, -1, AsuntoMovimiento.RULETA_APUESTA)) {
                    apuestas.put(apuesta, apuestas.getOrDefault(apuesta, 0) + 1);
                }
                break;
            case 1:
                if (GestorBD.agregarMovimiento(usuario, -5, AsuntoMovimiento.RULETA_APUESTA)) {
                    apuestas.put(apuesta, apuestas.getOrDefault(apuesta, 0) + 5);
                }
                break;
            case 2:
                if (GestorBD.agregarMovimiento(usuario, -25, AsuntoMovimiento.RULETA_APUESTA)) {
                    apuestas.put(apuesta, apuestas.getOrDefault(apuesta, 0) + 25);
                }
                break;
            case 3:
                if (GestorBD.agregarMovimiento(usuario, -50, AsuntoMovimiento.RULETA_APUESTA)) {
                    apuestas.put(apuesta, apuestas.getOrDefault(apuesta, 0) + 50);
                }
                break;
            case 4:
                if (GestorBD.agregarMovimiento(usuario, -100, AsuntoMovimiento.RULETA_APUESTA)) {
                    apuestas.put(apuesta, apuestas.getOrDefault(apuesta, 0) + 100);
                }
                break;
            case 5:
                if (GestorBD.agregarMovimiento(usuario, apuestas.get(apuesta),
                        AsuntoMovimiento.RULETA_RETIRAR_APUESTA)) {
                    apuestas.remove(apuesta);
                }
                break;
            default:
                throw new AssertionError();
        }
        JButton btn = botonesApuestas.get(apuesta);
        if (btn != null) {
            if (apuestas.get(apuesta) == null) {
                btn.setText(apuesta);
            } else {
                btn.setText(apuesta + " (" + apuestas.get(apuesta) + ")");
            }
        }
    }

    private void limpiarApuestas() {
        apuestas.clear();
        for (String apuesta : botonesApuestas.keySet()) {
            JButton btn = botonesApuestas.get(apuesta);
            btn.setText(apuesta);
        }
    }

    private int premio(int numero) {
        if (apuestasPermitidas) {
            new AssertionError("No se han cerrado las apuestas");
        }
        int premio = 0;
        for (String apuesta : apuestas.keySet()) {
            if (apuesta.equals(String.valueOf(numero))) {
                premio += apuestas.get(apuesta) * 36;
            } else if (apuesta.equals("Rojo")) {
                if (java.util.Arrays.asList(1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36)
                        .contains(numero)) {
                    premio += apuestas.get(apuesta) * 2;
                }
            } else if (apuesta.equals("Negro")) {
                if (java.util.Arrays.asList(2, 4, 6, 8, 10, 11, 13, 15, 17, 20, 22, 24, 26, 28, 29, 31, 33, 35)
                        .contains(numero)) {
                    premio += apuestas.get(apuesta) * 2;
                }
            } else if (apuesta.equals("Par")) {
                if (numero != 0 && numero % 2 == 0) {
                    premio += apuestas.get(apuesta) * 2;
                }
            } else if (apuesta.equals("Impar")) {
                if (numero % 2 == 1) {
                    premio += apuestas.get(apuesta) * 2;
                }
            } else if (apuesta.equals("1-18")) {
                if (numero >= 1 && numero <= 18) {
                    premio += apuestas.get(apuesta) * 3;
                }
            } else if (apuesta.equals("19-36")) {
                if (numero >= 19 && numero <= 36) {
                    premio += apuestas.get(apuesta) * 3;
                }
            } else if (apuesta.equals("1-12")) {
                if (numero >= 1 && numero <= 12) {
                    premio += apuestas.get(apuesta) * 3;
                }
            } else if (apuesta.equals("13-24")) {
                if (numero >= 13 && numero <= 24) {
                    premio += apuestas.get(apuesta) * 3;
                }
            } else if (apuesta.equals("25-36")) {
                if (numero >= 25 && numero <= 36) {
                    premio += apuestas.get(apuesta) * 3;
                }
            } else if (apuesta.equals("L3")) {
                if (numero % 3 == 1) {
                    premio += apuestas.get(apuesta) * 3;
                }
            } else if (apuesta.equals("L2")) {
                if (numero % 3 == 2) {
                    premio += apuestas.get(apuesta) * 3;
                }
            } else if (apuesta.equals("L1")) {
                if (numero % 3 == 0 && numero != 0) {
                    premio += apuestas.get(apuesta) * 3;
                }
            }
        }
        return premio;
    }

}