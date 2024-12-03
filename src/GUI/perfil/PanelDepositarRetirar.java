package GUI.perfil;

import datos.GestorMovimientos;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.regex.Pattern;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class PanelDepositarRetirar extends JPanel {
    private final CardLayout cardLayout;
    private final JPanel panelCentral;

    private final String usuario;

    private JTextField cantidadTarjeta;
    private JTextField cantidadDinero;
    private JTextField cantidadPaypal;

    private JButton botonDepositarTarjeta;
    private JButton botonDepositarTransferencia;
    private JButton botonRetirarTransferencia;
    private JButton botonDepositarPaypal;
    private JButton botonRetirarPaypal;

    public PanelDepositarRetirar(String usuario) {
        this.usuario = usuario;
        setLayout(new BorderLayout());

        JPanel panelInferior = createButtonPanel();
        add(panelInferior, BorderLayout.SOUTH);

        cardLayout = new CardLayout();
        panelCentral = new JPanel(cardLayout);
        add(panelCentral, BorderLayout.CENTER);

        JPanel panelTarjeta = createTarjetaPanel();
        JPanel panelTransferencia = createTransferenciaPanel();
        JPanel panelPaypal = createPaypalPanel();

        panelCentral.add(panelTarjeta, "Tarjeta");
        panelCentral.add(panelTransferencia, "Banco");
        panelCentral.add(panelPaypal, "Paypal");

        setupButtonActions(panelInferior);
    }

    private JPanel createButtonPanel() {
        JPanel panelInferior = new JPanel();
        JButton botonTarjeta = new JButton("Tarjeta Crédito");
        JButton botonTransferencia = new JButton("Cuenta Banco");
        JButton botonPaypal = new JButton("Cuenta Paypal");
        panelInferior.add(botonTarjeta);
        panelInferior.add(botonTransferencia);
        panelInferior.add(botonPaypal);
        return panelInferior;
    }

    private JPanel createTarjetaPanel() {
        JPanel panelTarjeta = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = createGridBagConstraints();

        JTextField titularTarjeta = createTextField(15);
        JTextField numeroTarjeta = createTextField(15);
        JTextField fechaTarjeta = createTextField(10);
        JTextField cvvTarjeta = createTextField(5);
        cantidadTarjeta = createTextField(20);

        addField(panelTarjeta, gbc, "Cantidad: ", cantidadTarjeta, 0);
        addSpace(panelTarjeta, gbc, 1);
        addField(panelTarjeta, gbc, "Titular:", titularTarjeta, 2);
        addField(panelTarjeta, gbc, "Número:", numeroTarjeta, 3);
        addField(panelTarjeta, gbc, "Fecha MM/AA:", fechaTarjeta, 4);
        addField(panelTarjeta, gbc, "CVV:", cvvTarjeta, 5);

        addSpace(panelTarjeta, gbc, 6);

        botonDepositarTarjeta = new JButton("Depositar");
        botonDepositarTarjeta.setFont(new Font("Arial", Font.BOLD, 18));
        botonDepositarTarjeta.setEnabled(false);
        addButton(panelTarjeta, gbc, botonDepositarTarjeta, 7);

        addDocumentListener(new JTextField[] { titularTarjeta, numeroTarjeta, fechaTarjeta, cvvTarjeta },
                new String[] { "[a-zA-Z\\s]+", "\\d{16}", "(0[1-9]|1[0-2])/\\d{2}", "\\d{3}" }, botonDepositarTarjeta);

        return panelTarjeta;
    }

    private JPanel createTransferenciaPanel() {
        JPanel panelTransferencia = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = createGridBagConstraints();

        JTextField numeroCuenta = createTextField(20);
        JTextField nombreTitularTransferencia = createTextField(20);
        JTextField conceptoTransferencia = createTextField(20);
        JTextField bancoDestino = createTextField(20);
        cantidadDinero = createTextField(20);

        addField(panelTransferencia, gbc, "Cantidad: ", cantidadDinero, 0);
        addSpace(panelTransferencia, gbc, 1);
        addField(panelTransferencia, gbc, "Número de cuenta: ", numeroCuenta, 2);
        addField(panelTransferencia, gbc, "Nombre titular: ", nombreTitularTransferencia, 3);
        addField(panelTransferencia, gbc, "Concepto: ", conceptoTransferencia, 4);
        addField(panelTransferencia, gbc, "Banco destino: ", bancoDestino, 5);

        addSpace(panelTransferencia, gbc, 6);

        botonDepositarTransferencia = new JButton("Depositar");
        botonDepositarTransferencia.setFont(new Font("Arial", Font.BOLD, 18));
        botonRetirarTransferencia = new JButton("Retirar");
        botonRetirarTransferencia.setFont(new Font("Arial", Font.BOLD, 18));
        botonDepositarTransferencia.setEnabled(false);
        botonRetirarTransferencia.setEnabled(false);
        addButton(panelTransferencia, gbc, botonDepositarTransferencia, 7);
        addButton(panelTransferencia, gbc, botonRetirarTransferencia, 8);

        addDocumentListener(
                new JTextField[] { cantidadDinero, numeroCuenta, nombreTitularTransferencia, conceptoTransferencia,
                        bancoDestino },
                new String[] { "\\d+", "[A-Za-z]{2}\\d{26}", "[a-zA-Z\\sáéíóúÁÉÍÓÚñÑ]+", ".+", ".+" },
                botonDepositarTransferencia, botonRetirarTransferencia);

        return panelTransferencia;
    }

    private JPanel createPaypalPanel() {
        JPanel panelPaypal = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = createGridBagConstraints();

        JTextField correoPaypal = createTextField(20);
        JPasswordField contrasenaPaypal = new JPasswordField(20);
        cantidadPaypal = createTextField(20);

        addField(panelPaypal, gbc, "Cantidad: ", cantidadPaypal, 0);
        addSpace(panelPaypal, gbc, 1);
        addField(panelPaypal, gbc, "Correo:", correoPaypal, 2);
        addField(panelPaypal, gbc, "Contraseña:", contrasenaPaypal, 3);

        addSpace(panelPaypal, gbc, 4);

        botonDepositarPaypal = new JButton("Depositar");
        botonDepositarPaypal.setFont(new Font("Arial", Font.BOLD, 18));
        botonRetirarPaypal = new JButton("Retirar");
        botonRetirarPaypal.setFont(new Font("Arial", Font.BOLD, 18));
        botonDepositarPaypal.setEnabled(false);
        botonRetirarPaypal.setEnabled(false);
        addButton(panelPaypal, gbc, botonDepositarPaypal, 5);
        addButton(panelPaypal, gbc, botonRetirarPaypal, 6);

        addDocumentListener(new JTextField[] { correoPaypal, contrasenaPaypal, cantidadPaypal },
                new String[] { ".+@.+\\..+", ".+", "\\d+" }, botonDepositarPaypal, botonRetirarPaypal);

        return panelPaypal;
    }

    private GridBagConstraints createGridBagConstraints() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new java.awt.Insets(5, 5, 5, 5);
        return gbc;
    }

    private void addField(JPanel panel, GridBagConstraints gbc, String label, JTextField field, int y) {
        gbc.gridx = 0;
        gbc.gridy = y;
        JLabel jLabel = new JLabel(label);
        jLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        jLabel.setPreferredSize(new Dimension(150, 30)); // Adjust the width as needed
        panel.add(jLabel, gbc);
        gbc.gridx = 1;
        field.setFont(new Font("Arial", Font.PLAIN, 16));
        field.setPreferredSize(new Dimension(200, 30));
        panel.add(field, gbc);
    }

    private void addSpace(JPanel panel, GridBagConstraints gbc, int y) {
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.gridwidth = 2;
        panel.add(Box.createRigidArea(new Dimension(0, 20)), gbc);
    }

    private void addButton(JPanel panel, GridBagConstraints gbc, JButton button, int y) {
        gbc.gridy = y;
        panel.add(button, gbc);
    }

    private void addDocumentListener(JTextField[] fields, String[] patterns, JButton... buttons) {
        DocumentListener listener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                checkFields();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                checkFields();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                checkFields();
            }

            private void checkFields() {
                boolean allFilled = true;
                for (int i = 0; i < fields.length; i++) {
                    JTextField field = fields[i];
                    String pattern = patterns[i];
                    if (!Pattern.matches(pattern, field.getText().trim())) {
                        allFilled = false;
                        field.setForeground(Color.RED);
                    } else {
                        field.setForeground(Color.BLACK);
                    }
                }
                for (JButton button : buttons) {
                    button.setEnabled(allFilled);
                }
            }
        };

        for (JTextField field : fields) {
            field.getDocument().addDocumentListener(listener);
        }
    }

    private JTextField createTextField(int columns) {
        JTextField textField = new JTextField(columns);
        textField.setFont(new Font("Arial", Font.PLAIN, 16));
        textField.setPreferredSize(new Dimension(200, 30));
        return textField;
    }

    private void setupButtonActions(JPanel panelInferior) {
        JButton botonTarjeta = (JButton) panelInferior.getComponent(0);
        JButton botonTransferencia = (JButton) panelInferior.getComponent(1);
        JButton botonPaypal = (JButton) panelInferior.getComponent(2);

        botonTarjeta.setEnabled(false);
        botonTarjeta.addActionListener(e -> {
            cardLayout.show(panelCentral, "Tarjeta");
            botonTarjeta.setEnabled(false);
            botonTransferencia.setEnabled(true);
            botonPaypal.setEnabled(true);
        });
        botonTransferencia.addActionListener(e -> {
            cardLayout.show(panelCentral, "Banco");
            botonTarjeta.setEnabled(true);
            botonTransferencia.setEnabled(false);
            botonPaypal.setEnabled(true);
        });
        botonPaypal.addActionListener(e -> {
            cardLayout.show(panelCentral, "Paypal");
            botonTarjeta.setEnabled(true);
            botonTransferencia.setEnabled(true);
            botonPaypal.setEnabled(false);
        });

        // Add action listeners for deposit and withdraw buttons
        botonDepositarTarjeta.addActionListener(e -> {
            double cantidad = Double.parseDouble(cantidadTarjeta.getText());
            if (GestorMovimientos.agregarMovimiento(usuario, (int) cantidad * 100, "deposito:tarjeta")) {
                JOptionPane.showMessageDialog(this, "Depósito realizado correctamente", "Información",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Error al realizar el depósito", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        botonDepositarTransferencia.addActionListener(e -> {
            double cantidad = Double.parseDouble(cantidadDinero.getText());
            if (GestorMovimientos.agregarMovimiento(usuario, (int) cantidad * 100, "deposito:transferencia")) {
                JOptionPane.showMessageDialog(this, "Depósito realizado correctamente", "Información",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Error al realizar el depósito", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        botonRetirarTransferencia.addActionListener(e -> {
            double cantidad = Double.parseDouble(cantidadDinero.getText());
            if (GestorMovimientos.agregarMovimiento(usuario, (int) -cantidad * 100, "retiro:transferencia")) {
                JOptionPane.showMessageDialog(this, "Retiro realizado correctamente", "Información",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Error al realizar el retiro", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        botonDepositarPaypal.addActionListener(e -> {
            double cantidad = Double.parseDouble(cantidadPaypal.getText());
            if (GestorMovimientos.agregarMovimiento(usuario, (int) cantidad * 100, "deposito:paypal")) {
                JOptionPane.showMessageDialog(this, "Depósito realizado correctamente", "Información",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Error al realizar el depósito", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        botonRetirarPaypal.addActionListener(e -> {
            double cantidad = Double.parseDouble(cantidadPaypal.getText());
            if (GestorMovimientos.agregarMovimiento(usuario, (int) -cantidad * 100, "retiro:paypal")) {
                JOptionPane.showMessageDialog(this, "Retiro realizado correctamente", "Información",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Error al realizar el retiro", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}