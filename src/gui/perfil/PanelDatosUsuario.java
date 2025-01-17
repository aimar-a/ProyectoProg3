package gui.perfil;

import db.GestorBD;
import domain.datos.TipoDeDato;
import gui.ColorVariables;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class PanelDatosUsuario extends JPanel {
    private static final long serialVersionUID = 1L;

    public PanelDatosUsuario(String usuario, boolean darkMode) {
        String[] datosUsuario = GestorBD.obtenerDatos(usuario);
        setLayout(new BorderLayout());

        JPanel panelCentral = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(panelCentral, BorderLayout.CENTER);

        JTextField txtUsuario = createTextField(usuario, false);

        JTextField txtNombre = createTextField(datosUsuario[0], false);
        JTextField txtApellidos = createTextField(datosUsuario[1], false);
        JTextField txtDNI = createTextField(datosUsuario[2], false);
        JTextField txtEmail = createTextField(datosUsuario[3], false);
        JComboBox<String> comboPrefijo = createComboBox(new String[] { "+34", "+1", "+44" }, datosUsuario[4], false);
        JTextField txtNumeroTelefono = createTextField(datosUsuario[5], false);
        JTextField txtProvincia = createTextField(datosUsuario[6], false);
        JTextField txtCiudad = createTextField(datosUsuario[7], false);
        JTextField txtCalle = createTextField(datosUsuario[8], false);
        txtCalle.setColumns(15);
        JTextField txtNumeroCalle = createTextField(datosUsuario[9], false);

        JComboBox<String> comboDiaNac = createComboBox(
                IntStream.rangeClosed(1, 31).mapToObj(String::valueOf).toArray(String[]::new),
                datosUsuario[10].split("-")[0], false);
        JComboBox<String> comboMesNac = createComboBox(
                IntStream.rangeClosed(1, 12).mapToObj(String::valueOf).toArray(String[]::new),
                datosUsuario[10].split("-")[1], false);
        JComboBox<String> comboAnoNac = createComboBox(
                IntStream.rangeClosed(1900, 2023).mapToObj(String::valueOf).toArray(String[]::new),
                datosUsuario[10].split("-")[2], false);

        JComboBox<String> comboDiaCre = createComboBox(
                IntStream.rangeClosed(1, 31).mapToObj(String::valueOf).toArray(String[]::new),
                datosUsuario[11].split("-")[0], false);
        JComboBox<String> comboMesCre = createComboBox(
                IntStream.rangeClosed(1, 12).mapToObj(String::valueOf).toArray(String[]::new),
                datosUsuario[11].split("-")[1], false);
        JComboBox<String> comboAnoCre = createComboBox(
                IntStream.rangeClosed(1900, 2050).mapToObj(String::valueOf).toArray(String[]::new),
                datosUsuario[11].split("-")[2], false);

        Map<Integer, CampoDatoUsuario> map = new HashMap<>();
        map.put(0, new CampoDatoUsuario("Usuario:", "No se puede modificar el nombre de usuario.",
                TipoDeDato.USUARIO,
                new JComponent[] { txtUsuario }, false));
        map.put(1, new CampoDatoUsuario("Nombre:", "",
                TipoDeDato.NOMBRE,
                new JComponent[] { txtNombre }, true));
        map.put(2, new CampoDatoUsuario("Apellidos:", "",
                TipoDeDato.APELLIDOS,
                new JComponent[] { txtApellidos }, true));
        map.put(3, new CampoDatoUsuario("DNI:", "",
                TipoDeDato.DNI,
                new JComponent[] { txtDNI }, true));
        map.put(4, new CampoDatoUsuario("Email:", "",
                TipoDeDato.MAIL,
                new JComponent[] { txtEmail }, true));
        map.put(5, new CampoDatoUsuario("Teléfono (Prefijo + Número):", "",
                TipoDeDato.TELEFONO,
                new JComponent[] { comboPrefijo, txtNumeroTelefono }, true));
        map.put(6, new CampoDatoUsuario("Provincia:", "", TipoDeDato.PROVINCIA,
                new JComponent[] { txtProvincia }, true));
        map.put(7, new CampoDatoUsuario("Ciudad:", "", TipoDeDato.CIUDAD,
                new JComponent[] { txtCiudad }, true));
        map.put(8, new CampoDatoUsuario("Dirección (Calle + Número):", "",
                TipoDeDato.DIRECCION,
                new JComponent[] { txtCalle, txtNumeroCalle }, true));
        map.put(9, new CampoDatoUsuario("Fecha de nacimiento (Día/Mes/Año):", "",
                TipoDeDato.FECHA_DE_NACIMIENTO,
                new JComponent[] { comboDiaNac, comboMesNac, comboAnoNac }, true));
        map.put(10,
                new CampoDatoUsuario("Fecha de creación (Día/Mes/Año):", "No se puede modificar la fecha de creación.",
                        TipoDeDato.FECHA_DE_CREACION,
                        new JComponent[] { comboDiaCre, comboMesCre, comboAnoCre }, false));

        agregarCampos(panelCentral, gbc, map);

        JPanel panelOpciones = new JPanel();
        JButton btnEditar = new JButton("Editar");
        JButton btnGuardar = new JButton("Guardar");

        agregarListeners(map, btnGuardar);

        btnEditar.addActionListener(
                e -> {
                    for (CampoDatoUsuario campoDato : map.values()) {
                        campoDato.setEditable(true);
                    }
                });
        panelOpciones.add(btnEditar);

        btnGuardar.addActionListener(e -> {
            btnGuardar.setEnabled(false);
            String[] datosNuevos = {
                    txtNombre.getText(), txtApellidos.getText(), txtDNI.getText(), txtEmail.getText(),
                    comboPrefijo.getSelectedItem().toString(), txtNumeroTelefono.getText(),
                    txtProvincia.getText(), txtCiudad.getText(),
                    txtCalle.getText(), txtNumeroCalle.getText(),
                    TipoDeDato.formatFecha(comboDiaNac.getSelectedItem(), comboMesNac.getSelectedItem(),
                            comboAnoNac.getSelectedItem())
            };
            if (GestorBD.cambiarDatos(usuario, datosNuevos)) {
                JOptionPane.showMessageDialog(this, "Datos actualizados correctamente", "Información",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar los datos", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
            for (CampoDatoUsuario campoDato : map.values()) {
                campoDato.setEditable(false);
            }
        });

        btnGuardar.setEnabled(false);
        panelOpciones.add(btnGuardar);
        add(panelOpciones, BorderLayout.SOUTH);

        if (darkMode) {
            applyDarkMode(panelCentral, panelOpciones, btnEditar, btnGuardar);
        } else {
            applyLightMode(panelCentral, panelOpciones);
        }
    }

    private void applyDarkMode(JPanel panelCentral, JPanel panelOpciones, JButton btnEditar, JButton btnGuardar) {
        setBackground(ColorVariables.COLOR_FONDO_DARK.getColor());
        panelCentral.setBackground(ColorVariables.COLOR_FONDO_DARK.getColor());
        panelOpciones.setBackground(ColorVariables.COLOR_FONDO_DARK.getColor());
        btnEditar.setBackground(ColorVariables.COLOR_BOTON_DARK.getColor());
        btnEditar.setForeground(ColorVariables.COLOR_TEXTO_DARK.getColor());
        btnGuardar.setBackground(ColorVariables.COLOR_BOTON_DARK.getColor());
        btnGuardar.setForeground(ColorVariables.COLOR_TEXTO_DARK.getColor());

        setLabelColors(panelCentral, ColorVariables.COLOR_TEXTO_DARK.getColor());
    }

    private void applyLightMode(JPanel panelCentral, JPanel panelOpciones) {
        setBackground(ColorVariables.COLOR_FONDO_LIGHT.getColor());
        panelCentral.setBackground(ColorVariables.COLOR_FONDO_LIGHT.getColor());
        panelOpciones.setBackground(ColorVariables.COLOR_FONDO_LIGHT.getColor());
    }

    private void setLabelColors(Container container, Color color) {
        for (Component component : container.getComponents()) {
            if (component instanceof JLabel) {
                component.setForeground(color);
            } else if (component instanceof Container container1) {
                setLabelColors(container1, color);
            }
        }
    }

    private JTextField createTextField(String text, boolean editable) {
        JTextField textField = new JTextField(text);
        textField.setEditable(editable);
        return textField;
    }

    private JComboBox<String> createComboBox(String[] items, String selectedItem, boolean enabled) {
        JComboBox<String> comboBox = new JComboBox<>(items);
        comboBox.setSelectedItem(selectedItem);
        comboBox.setEnabled(enabled);
        return comboBox;
    }

    private void agregarCampos(JPanel panelCampos, GridBagConstraints gbc,
            Map<Integer, CampoDatoUsuario> map) {
        map.forEach((y, campoDato) -> addField(panelCampos, gbc, campoDato.getTexto(),
                campoDato.getInfoLabel(), campoDato.getComponentes(), y));
    }

    private void addField(JPanel panel, GridBagConstraints gbc, String labelText, JLabel labelFormatInfo,
            JComponent[] components, int y) {
        gbc.gridx = 0;
        gbc.gridy = y * 2;
        panel.add(new JLabel(labelText), gbc);

        gbc.gridx = 1;
        switch (components.length) {
            case 1 -> panel.add(components[0], gbc);
            case 2 -> {
                JPanel panelCampos = new JPanel(new BorderLayout(5, 0));
                panelCampos.add(components[0], BorderLayout.WEST);
                panelCampos.add(components[1], BorderLayout.CENTER);
                panel.add(panelCampos, gbc);
            }
            case 3 -> {
                JPanel panelCampos = new JPanel(new GridLayout(1, 3, 5, 0));
                panelCampos.add(components[0]);
                panelCampos.add(components[1]);
                panelCampos.add(components[2]);
                panel.add(panelCampos, gbc);
            }
            default ->
                throw new IllegalArgumentException("Número de componentes no soportado");
        }

        gbc.gridy++;
        panel.add(labelFormatInfo, gbc);
    }

    private boolean validarTodo(Map<Integer, CampoDatoUsuario> map) {
        boolean todoCorrecto = true;
        for (CampoDatoUsuario campoDato : map.values()) {
            if (campoDato.isEditable() && !campoDato.validarCampo()) {
                todoCorrecto = false;
            }
        }
        return todoCorrecto;
    }

    private void agregarListeners(Map<Integer, CampoDatoUsuario> map, JButton btnGuardar) {
        for (CampoDatoUsuario campoDato : map.values()) {
            for (JComponent component : campoDato.getComponentes()) {
                if (component instanceof JTextField jTextField) {
                    jTextField.getDocument().addDocumentListener(new DocumentListener() {
                        @Override
                        public void insertUpdate(DocumentEvent e) {
                            btnGuardar.setEnabled(validarTodo(map));
                        }

                        @Override
                        public void removeUpdate(DocumentEvent e) {
                            btnGuardar.setEnabled(validarTodo(map));
                        }

                        @Override
                        public void changedUpdate(DocumentEvent e) {
                            btnGuardar.setEnabled(validarTodo(map));
                        }
                    });
                } else if (component instanceof JComboBox) {
                    ((JComboBox<?>) component).addActionListener(e -> btnGuardar.setEnabled(validarTodo(map)));
                }
            }
        }
    }
}
