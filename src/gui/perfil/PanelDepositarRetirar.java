package gui.perfil;

import db.GestorBD;
import domain.datos.AsuntoMovimiento;
import domain.datos.TipoDeDato;
import gui.ColorVariables;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class PanelDepositarRetirar extends JPanel {
    private static final Font FORMAT_INFO_FONT = new Font("Arial", Font.ITALIC, 10);
    private static final Font FORMAT_LABEL_FONT = new Font("Arial", Font.PLAIN, 16);

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

    private final Color backgroundColor;
    private final Color foregroundColor;
    private final Color buttonColor;
    private final Color buttonTextColor;

    public PanelDepositarRetirar(String usuario, boolean darkMode) {
        this.usuario = usuario;
        setLayout(new BorderLayout());

        // Define color variables
        if (darkMode) {
            backgroundColor = ColorVariables.COLOR_FONDO_DARK.getColor();
            foregroundColor = ColorVariables.COLOR_TEXTO_DARK.getColor();
            buttonColor = ColorVariables.COLOR_BOTON_DARK.getColor();
            buttonTextColor = ColorVariables.COLOR_BOTON_TEXTO_DARK.getColor();
        } else {
            backgroundColor = ColorVariables.COLOR_FONDO_LIGHT.getColor();
            foregroundColor = ColorVariables.COLOR_TEXTO_LIGHT.getColor();
            buttonColor = ColorVariables.COLOR_BOTON_LIGHT.getColor();
            buttonTextColor = ColorVariables.COLOR_BOTON_TEXTO_LIGHT.getColor();
        }

        setBackground(backgroundColor);

        JPanel panelInferior = createButtonPanel();
        add(panelInferior, BorderLayout.SOUTH);

        cardLayout = new CardLayout();
        panelCentral = new JPanel(cardLayout);
        panelCentral.setBackground(backgroundColor);
        add(panelCentral, BorderLayout.CENTER);

        panelCentral.add(createTarjetaPanel(), "Tarjeta");
        panelCentral.add(createTransferenciaPanel(), "Banco");
        panelCentral.add(createPaypalPanel(), "Paypal");

        setupButtonActions(panelInferior);
    }

    private JPanel createButtonPanel() {
        JPanel panelInferior = new JPanel();
        panelInferior.setBackground(backgroundColor);
        panelInferior.add(createButton("Tarjeta Crédito"));
        panelInferior.add(createButton("Cuenta Banco"));
        panelInferior.add(createButton("Cuenta Paypal"));
        return panelInferior;
    }

    private JPanel createTarjetaPanel() {
        JPanel panelTarjeta = new JPanel(new GridBagLayout());
        panelTarjeta.setBackground(backgroundColor);
        GridBagConstraints gbc = createGridBagConstraints();

        JTextField titularTarjeta = createTextField(15);
        JTextField numeroTarjeta = createTextField(15);
        JTextField fechaTarjeta = createTextField(10);
        JTextField cvvTarjeta = createTextField(5);
        cantidadTarjeta = createTextField(20);

        JLabel[] errorLabels = {
                createErrorLabel(), createErrorLabel(), createErrorLabel(),
                createErrorLabel(), createErrorLabel()
        };

        gbc.gridy = 0;
        addField(panelTarjeta, gbc, "Cantidad: ", cantidadTarjeta, errorLabels[0]);
        addSpace(panelTarjeta, gbc);
        addField(panelTarjeta, gbc, "Titular:", titularTarjeta, errorLabels[1]);
        addField(panelTarjeta, gbc, "Número:", numeroTarjeta, errorLabels[2]);
        addField(panelTarjeta, gbc, "Fecha MM/AA:", fechaTarjeta, errorLabels[3]);
        addField(panelTarjeta, gbc, "CVV:", cvvTarjeta, errorLabels[4]);

        addSpace(panelTarjeta, gbc);

        botonDepositarTarjeta = createButton("Depositar");
        botonDepositarTarjeta.setEnabled(false);
        addButton(panelTarjeta, gbc, botonDepositarTarjeta);

        addDocumentListener(
                new JTextField[] { cantidadTarjeta, titularTarjeta, numeroTarjeta, fechaTarjeta, cvvTarjeta },
                errorLabels,
                new TipoDeDato[] { TipoDeDato.CANTIDAD_DEPOSITO, TipoDeDato.TITULAR_TARJETA, TipoDeDato.NUMERO_TARJETA,
                        TipoDeDato.FECHA_TARJETA, TipoDeDato.CVV_TARJETA },
                botonDepositarTarjeta);

        return panelTarjeta;
    }

    private JPanel createTransferenciaPanel() {
        JPanel panelTransferencia = new JPanel(new GridBagLayout());
        panelTransferencia.setBackground(backgroundColor);
        GridBagConstraints gbc = createGridBagConstraints();

        JTextField numeroCuenta = createTextField(20);
        JTextField nombreTitularTransferencia = createTextField(20);
        JTextField conceptoTransferencia = createTextField(20);
        JTextField bancoDestino = createTextField(20);
        cantidadDinero = createTextField(20);

        JLabel[] errorLabels = {
                createErrorLabel(), createErrorLabel(), createErrorLabel(),
                createErrorLabel(), createErrorLabel()
        };

        gbc.gridy = 0;
        addField(panelTransferencia, gbc, "Cantidad: ", cantidadDinero, errorLabels[0]);
        addSpace(panelTransferencia, gbc);
        addField(panelTransferencia, gbc, "Número de cuenta: ", numeroCuenta, errorLabels[1]);
        addField(panelTransferencia, gbc, "Nombre titular: ", nombreTitularTransferencia, errorLabels[2]);
        addField(panelTransferencia, gbc, "Concepto: ", conceptoTransferencia, errorLabels[3]);
        addField(panelTransferencia, gbc, "Banco destino: ", bancoDestino, errorLabels[4]);

        addSpace(panelTransferencia, gbc);

        botonDepositarTransferencia = createButton("Depositar");
        botonRetirarTransferencia = createButton("Retirar");
        botonDepositarTransferencia.setEnabled(false);
        botonRetirarTransferencia.setEnabled(false);
        addButton(panelTransferencia, gbc, botonDepositarTransferencia);
        addButton(panelTransferencia, gbc, botonRetirarTransferencia);

        addDocumentListener(
                new JTextField[] { cantidadDinero, numeroCuenta, nombreTitularTransferencia, conceptoTransferencia,
                        bancoDestino },
                errorLabels,
                new TipoDeDato[] { TipoDeDato.CANTIDAD_DEPOSITO, TipoDeDato.NUMERO_CUENTA,
                        TipoDeDato.TITULAR_TRANSFERENCIA,
                        TipoDeDato.CONCEPTO_TRANSFERENCIA, TipoDeDato.BANCO_TRANSFERENCIA },
                botonDepositarTransferencia, botonRetirarTransferencia);

        return panelTransferencia;
    }

    private JPanel createPaypalPanel() {
        JPanel panelPaypal = new JPanel(new GridBagLayout());
        panelPaypal.setBackground(backgroundColor);
        GridBagConstraints gbc = createGridBagConstraints();

        JTextField correoPaypal = createTextField(20);
        JPasswordField contrasenaPaypal = new JPasswordField(20);
        cantidadPaypal = createTextField(20);

        JLabel[] errorLabels = {
                createErrorLabel(), createErrorLabel(), createErrorLabel()
        };

        gbc.gridy = 0;
        addField(panelPaypal, gbc, "Cantidad: ", cantidadPaypal, errorLabels[0]);
        addSpace(panelPaypal, gbc);
        addField(panelPaypal, gbc, "Correo:", correoPaypal, errorLabels[1]);
        addField(panelPaypal, gbc, "Contraseña:", contrasenaPaypal, errorLabels[2]);

        addSpace(panelPaypal, gbc);

        botonDepositarPaypal = createButton("Depositar");
        botonRetirarPaypal = createButton("Retirar");
        botonDepositarPaypal.setEnabled(false);
        botonRetirarPaypal.setEnabled(false);
        addButton(panelPaypal, gbc, botonDepositarPaypal);
        addButton(panelPaypal, gbc, botonRetirarPaypal);

        addDocumentListener(
                new JTextField[] { correoPaypal, contrasenaPaypal, cantidadPaypal },
                errorLabels,
                new TipoDeDato[] { TipoDeDato.CORREO_PAYPAL, TipoDeDato.CONTRASENA_PAYPAL,
                        TipoDeDato.CANTIDAD_DEPOSITO },
                botonDepositarPaypal, botonRetirarPaypal);

        return panelPaypal;
    }

    private GridBagConstraints createGridBagConstraints() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new java.awt.Insets(5, 5, 5, 5);
        return gbc;
    }

    private void addField(JPanel panel, GridBagConstraints gbc, String label, JTextField field, JLabel errorLabel) {
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 1;
        JLabel jLabel = new JLabel(label);
        jLabel.setFont(FORMAT_LABEL_FONT);
        jLabel.setPreferredSize(new Dimension(150, 30)); // Adjust the width as needed
        jLabel.setForeground(foregroundColor);
        panel.add(jLabel, gbc);
        gbc.gridx = 1;
        field.setFont(FORMAT_LABEL_FONT);
        field.setPreferredSize(new Dimension(200, 30));
        field.setBackground(backgroundColor);
        field.setForeground(foregroundColor);
        panel.add(field, gbc);
        gbc.gridy++;
        panel.add(errorLabel, gbc);
    }

    private void addSpace(JPanel panel, GridBagConstraints gbc) {
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.gridy++;
        panel.add(Box.createRigidArea(new Dimension(0, 20)), gbc);
    }

    private void addButton(JPanel panel, GridBagConstraints gbc, JButton button) {
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.gridy++;
        panel.add(button, gbc);
    }

    private void addDocumentListener(JTextField[] fields, JLabel[] errorLabels, TipoDeDato[] patterns,
            JButton... buttons) {
        DocumentListener listener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                handleDocumentEvent();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                handleDocumentEvent();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                handleDocumentEvent();
            }

            private void handleDocumentEvent() {
                TipoDeDato.comprobarCamposYInfo(fields, errorLabels, patterns);
                updateButtonState(fields, buttons);
            }
        };

        for (JTextField field : fields) {
            field.getDocument().addDocumentListener(listener);
        }
    }

    private void updateButtonState(JTextField[] fields, JButton... buttons) {
        boolean allFieldsValid = true;
        for (JTextField field : fields) {
            if (field.getText().trim().isEmpty()) {
                allFieldsValid = false;
                break;
            }
        }
        for (JButton button : buttons) {
            button.setEnabled(allFieldsValid);
        }
    }

    private JTextField createTextField(int columns) {
        JTextField textField = new JTextField(columns);
        textField.setFont(FORMAT_LABEL_FONT);
        textField.setPreferredSize(new Dimension(200, 30));
        textField.setBackground(backgroundColor);
        textField.setForeground(foregroundColor);
        return textField;
    }

    private JLabel createErrorLabel() {
        JLabel label = new JLabel();
        label.setFont(FORMAT_INFO_FONT);
        label.setForeground(Color.RED);
        label.setVerticalAlignment(SwingConstants.TOP);
        return label;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(FORMAT_LABEL_FONT);
        button.setBackground(buttonColor);
        button.setForeground(buttonTextColor);
        return button;
    }

    private void setupButtonActions(JPanel panelInferior) {
        JButton botonTarjeta = (JButton) panelInferior.getComponent(0);
        JButton botonTransferencia = (JButton) panelInferior.getComponent(1);
        JButton botonPaypal = (JButton) panelInferior.getComponent(2);

        botonTarjeta.setEnabled(false);
        botonTarjeta.addActionListener(e -> switchPanel("Tarjeta", botonTarjeta, botonTransferencia, botonPaypal));
        botonTransferencia.addActionListener(e -> switchPanel("Banco", botonTarjeta, botonTransferencia, botonPaypal));
        botonPaypal.addActionListener(e -> switchPanel("Paypal", botonTarjeta, botonTransferencia, botonPaypal));

        setupDepositWithdrawActions();
    }

    private void switchPanel(String panelName, JButton botonTarjeta, JButton botonTransferencia, JButton botonPaypal) {
        cardLayout.show(panelCentral, panelName);
        botonTarjeta.setEnabled(!panelName.equals("Tarjeta"));
        botonTransferencia.setEnabled(!panelName.equals("Banco"));
        botonPaypal.setEnabled(!panelName.equals("Paypal"));
    }

    private void setupDepositWithdrawActions() {
        botonDepositarTarjeta
                .addActionListener(e -> handleTransaction(cantidadTarjeta, AsuntoMovimiento.DEPOSITO_TARJETA));
        botonDepositarTransferencia
                .addActionListener(e -> handleTransaction(cantidadDinero, AsuntoMovimiento.DEPOSITO_TRANSFERENCIA));
        botonRetirarTransferencia
                .addActionListener(e -> handleTransaction(cantidadDinero, AsuntoMovimiento.RETIRO_TRANSFERENCIA, true));
        botonDepositarPaypal
                .addActionListener(e -> handleTransaction(cantidadPaypal, AsuntoMovimiento.DEPOSITO_PAYPAL));
        botonRetirarPaypal
                .addActionListener(e -> handleTransaction(cantidadPaypal, AsuntoMovimiento.RETIRO_PAYPAL, true));
    }

    private void handleTransaction(JTextField cantidadField, AsuntoMovimiento asunto) {
        handleTransaction(cantidadField, asunto, false);
    }

    private void handleTransaction(JTextField cantidadField, AsuntoMovimiento asunto, boolean isWithdrawal) {
        double cantidad = Double.parseDouble(cantidadField.getText());
        int amount = (int) (cantidad * 100) * (isWithdrawal ? -1 : 1);
        if (GestorBD.agregarMovimiento(usuario, amount, asunto)) {
            JOptionPane.showMessageDialog(this, (isWithdrawal ? "Retiro" : "Depósito") + " realizado correctamente",
                    "Información", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Error al realizar el " + (isWithdrawal ? "retiro" : "depósito"),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
