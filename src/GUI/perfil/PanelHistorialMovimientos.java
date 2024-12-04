package GUI.perfil;

import GUI.ColorVariables;
import datos.GestorMovimientos;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class PanelHistorialMovimientos extends JPanel {

    private final JTable table;
    private final DefaultTableModel tableModel;

    public PanelHistorialMovimientos(String usuario, boolean darkMode) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Layout vertical

        // Crear el modelo de la tabla con los encabezados
        tableModel = new DefaultTableModel(
                new String[] { "Fecha", "Hora", "Modificación", "Tipo", "Saldo Final" },
                0);
        table = new JTable(tableModel);

        // Personalizar la fuente de la tabla
        Font font = new Font("Arial", Font.PLAIN, 16);
        table.setFont(font);
        table.setRowHeight(20); // Ajustar la altura de las filas para la nueva fuente

        // Personalizar el renderer de la tabla
        table.getColumnModel().getColumn(2).setCellRenderer(new ModificacionCellRenderer());
        table.getColumnModel().getColumn(4).setCellRenderer(new SaldoFinalCellRenderer());

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
        List<String[]> historial = GestorMovimientos.obtenerHistorial(usuario);
        for (String[] data : historial) {
            String fecha = data[0];
            String hora = data[1];
            String modificacion = data[3];
            String tipo = data[4];
            int saldoFinal = Integer.parseInt(data[5]);

            tableModel.addRow(new Object[] { fecha, hora, modificacion, tipo, saldoFinal });
        }
    }

    // Renderer para la columna "Modificación"
 // Renderer para la columna "Modificación"
    class ModificacionCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

            // Llamar al método padre para establecer las configuraciones iniciales
            Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            // Obtener el valor de la columna "Tipo" en la misma fila
            String tipo = (String) table.getValueAt(row, 3); // Índice de la columna "Tipo"

            // Cambiar colores según el tipo de operación
            if ("victoria".equalsIgnoreCase(tipo)) {
                cell.setForeground(Color.GREEN); // Verde para ganar
            } else if ("apuesta".equalsIgnoreCase(tipo)) {
                cell.setForeground(Color.RED); // Rojo para perder
            } else if ("deposito".equalsIgnoreCase(tipo)) {
                cell.setForeground(Color.BLUE); // Gris para depósito
            } else {
                cell.setForeground(Color.BLACK); // Color predeterminado
            }

            return cell;
        }
    }



    // Renderer para la columna "Saldo Final"
    class SaldoFinalCellRenderer extends DefaultTableCellRenderer {
        @Override
        protected void setValue(Object value) {
            super.setValue(value);
            setFont(getFont().deriveFont(Font.BOLD)); // Establecer la fuente en negrita
            setForeground(Color.BLACK); // Color negro para el saldo
        }
    }
}
