package gui.perfil;

import datos.GestorBD;
import gui.ColorVariables;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

//IAG: Modificado (ChatGPT y GitHub Copilot)
public class PanelHistorialMovimientos extends JPanel {

    private final JTable table;
    private final DefaultTableModel tableModel = new DefaultTableModel(
            new String[] { "Fecha", "Hora", "Modificación", "Tipo", "Accion", "Saldo Final" },
            0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    public PanelHistorialMovimientos(String usuario, boolean darkMode) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Layout vertical

        table = new JTable(tableModel);

        // Personalizar la fuente de la tabla
        table.setRowHeight(40); // Ajustar la altura de las filas para la nueva fuente

        // Personalizar el renderer de la tabla
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                JLabel cell = new JLabel(value.toString());
                cell.setForeground(darkMode ? ColorVariables.COLOR_TEXTO_DARK : ColorVariables.COLOR_TEXTO_LIGHT);
                cell.setFont(new Font("Arial", Font.PLAIN, 16)); // Fuente y tamaño
                if (column == 0) {
                    cell.setHorizontalAlignment(SwingConstants.CENTER); // Centrar el texto
                    cell.setFont(new Font("Arial", Font.ITALIC, 20)); // Fuente en negrita
                } else if (column == 1) {
                    cell.setHorizontalAlignment(SwingConstants.CENTER); // Centrar el texto
                    cell.setFont(new Font("Arial", Font.ITALIC, 20)); // Fuente en negrita
                    String[] timeParts = value.toString().split(":");
                    int hour = Integer.parseInt(timeParts[0]);
                    int minute = Integer.parseInt(timeParts[1]);
                    int second = Integer.parseInt(timeParts[2]);
                    float intensidad = (hour * 3600 + minute * 60 + second) / 86400f;
                    cell.setBackground(new Color(intensidad, intensidad, intensidad));
                    cell.setForeground(intensidad > 0.5 ? Color.BLACK : Color.WHITE);
                    cell.setOpaque(true);
                } else if (column == 2) { // Columna "Modificación"
                    cell.setHorizontalAlignment(SwingConstants.CENTER); // Centrar el texto
                    cell.setOpaque(true); // Permitir cambiar el color de fondo
                    cell.setFont(new Font("Times new roman", Font.BOLD, 20)); // Fuente en negrita
                    if (Integer.parseInt(value.toString()) > 0) {
                        cell.setText("+" + value.toString()); // Agregar el signo + si es positivo
                        cell.setBackground(new Color(0, 255, 0, 50)); // Fondo verde claro
                    } else if (Integer.parseInt(value.toString()) < 0) {
                        cell.setBackground(new Color(255, 0, 0, 50)); // Fondo rojo claro
                    } else {
                        cell.setBackground(new Color(255, 255, 0, 50)); // Fondo amarillo claro
                    }
                    if (((String) table.getValueAt(row, 3)).contains("victoria")) { // Si es una victoria
                        cell.setForeground(Color.GREEN); // Color verde
                    } else if (((String) table.getValueAt(row, 3)).contains("apuesta")) { // Si es una apuesta
                        cell.setForeground(Color.RED); // Color rojo
                    } else if (((String) table.getValueAt(row, 3)).contains("deposito")) { // Si es un depósito
                        cell.setForeground(Color.BLUE); // Color azul
                    } else if (((String) table.getValueAt(row, 3)).contains("retiro")) { // Si es un retiro
                        cell.setForeground(Color.ORANGE); // Color naranja
                    } else if (((String) table.getValueAt(row, 3)).contains("bienvenida")) {
                        cell.setForeground(Color.MAGENTA); // Color magenta
                    }
                } else if (column == 5) { // Columna "Saldo Final"
                    cell.setHorizontalAlignment(SwingConstants.RIGHT); // Alinear a la derecha
                    cell.setFont(cell.getFont().deriveFont(Font.BOLD, 25f)); // Fuente en negrita y tamaño 20
                    cell.setFont(cell.getFont().deriveFont(Font.BOLD)); // Fuente en negrita
                } else if (column == 3) {
                    cell.setHorizontalAlignment(SwingConstants.CENTER); // Centrar el texto
                    cell.setText(""); // Limpiar el texto
                    if (((String) value).contains("slots")) {
                        ImageIcon icon = new ImageIcon(("resources/img/perfil/slot.png"));
                        Image image = icon.getImage().getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
                        cell.setIcon(new ImageIcon(image));
                    } else if (((String) value).contains("caballo")) {
                        ImageIcon icon = new ImageIcon(("resources/img/perfil/caballo.png"));
                        Image image = icon.getImage().getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
                        cell.setIcon(new ImageIcon(image));
                    } else if (((String) value).contains("blackjack")) {
                        ImageIcon icon = new ImageIcon(("resources/img/perfil/blackjack.png"));
                        Image image = icon.getImage().getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
                        cell.setIcon(new ImageIcon(image));
                    } else if (((String) value).contains("ruleta")) {
                        ImageIcon icon = new ImageIcon(("resources/img/perfil/ruleta.png"));
                        Image image = icon.getImage().getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
                        cell.setIcon(new ImageIcon(image));
                    } else if (((String) value).contains("dino")) {
                        ImageIcon icon = new ImageIcon(("resources/img/perfil/dino.png"));
                        Image image = icon.getImage().getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
                        cell.setIcon(new ImageIcon(image));
                    } else if (((String) value).contains("deposito")) {
                        ImageIcon icon = new ImageIcon(("resources/img/perfil/deposito.png"));
                        Image image = icon.getImage().getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
                        cell.setIcon(new ImageIcon(image));
                    } else if (((String) value).contains("retiro")) {
                        ImageIcon icon = new ImageIcon(("resources/img/perfil/retiro.png"));
                        Image image = icon.getImage().getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
                        cell.setIcon(new ImageIcon(image));
                    } else if (((String) value).contains("bienvenida")) {
                        ImageIcon icon = new ImageIcon(("resources/img/perfil/bienvenida.png"));
                        Image image = icon.getImage().getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
                        cell.setIcon(new ImageIcon(image));
                    } else {
                        cell.setText(value.toString());
                    }
                } else if (column == 4) {
                    cell.setHorizontalAlignment(SwingConstants.CENTER); // Centrar el texto
                    cell.setText(""); // Limpiar el texto
                    if (((String) value).contains("premio")) {
                        ImageIcon icon = new ImageIcon(("resources/img/perfil/premio.png"));
                        Image image = icon.getImage().getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
                        cell.setIcon(new ImageIcon(image));
                    } else if (((String) value).contains("apuesta")) {
                        ImageIcon icon = new ImageIcon(("resources/img/perfil/apuesta.png"));
                        Image image = icon.getImage().getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
                        cell.setIcon(new ImageIcon(image));
                    } else if (((String) value).contains("empate")) {
                        ImageIcon icon = new ImageIcon(("resources/img/perfil/empate.png"));
                        Image image = icon.getImage().getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
                        cell.setIcon(new ImageIcon(image));
                    } else if (((String) value).contains("retirar_ap")) {
                        ImageIcon icon = new ImageIcon(("resources/img/perfil/cancelar.png"));
                        Image image = icon.getImage().getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
                        cell.setIcon(new ImageIcon(image));
                    } else if (((String) value).contains("deposito")) {
                        ImageIcon icon = new ImageIcon(("resources/img/perfil/deposito.png"));
                        Image image = icon.getImage().getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
                        cell.setIcon(new ImageIcon(image));
                    } else if (((String) value).contains("retiro")) {
                        ImageIcon icon = new ImageIcon(("resources/img/perfil/retiro.png"));
                        Image image = icon.getImage().getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
                        cell.setIcon(new ImageIcon(image));
                    } else if (((String) value).contains("bienvenida")) {
                        ImageIcon icon = new ImageIcon(("resources/img/perfil/bienvenida.png"));
                        Image image = icon.getImage().getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
                        cell.setIcon(new ImageIcon(image));
                    } else {
                        cell.setText(value.toString());
                    }
                }
                return cell;
            }
        });

        // Agregar la tabla a un JScrollPane para que sea desplazable
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);

        // Aplicar colores según el modo
        if (darkMode) {
            setBackground(ColorVariables.COLOR_FONDO_DARK);
            table.setBackground(ColorVariables.COLOR_FONDO_DARK);
            table.setForeground(ColorVariables.COLOR_TEXTO_DARK);
            table.setGridColor(Color.GRAY);
            scrollPane.getViewport().setBackground(ColorVariables.COLOR_FONDO_DARK);
        } else {
            setBackground(ColorVariables.COLOR_FONDO_LIGHT);
            table.setBackground(ColorVariables.COLOR_FONDO_LIGHT);
            table.setForeground(ColorVariables.COLOR_TEXTO_LIGHT);
            table.setGridColor(Color.LIGHT_GRAY);
            scrollPane.getViewport().setBackground(ColorVariables.COLOR_FONDO_LIGHT);
        }

        // Cargar los movimientos del archivo CSV y mostrarlos en la tabla
        cargarHistorialMovimientos(usuario);
    }

    private void cargarHistorialMovimientos(String usuario) {
        List<String[]> historial = GestorBD.obtenerHistorial(usuario);
        for (String[] data : historial) {
            String fecha = data[0];
            String hora = data[1];
            String modificacion = data[2];
            String asunto = data[3];
            int saldoFinal = Integer.parseInt(data[4]);

            tableModel.addRow(new Object[] { fecha, hora, modificacion, asunto, asunto, saldoFinal });
        }
    }
}
