package GUI.perfil;

import java.awt.Color;
import java.awt.Font;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class PanelHistorialMovimientos extends JPanel {

    private JTable table;
    private DefaultTableModel tableModel;

    private static final String RUTA_HISTORIAL_CSV = "src/CSV/historialMovimientos.csv";

    public PanelHistorialMovimientos(String usuario) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Layout vertical

        // Crear el modelo de la tabla con los encabezados
        tableModel = new DefaultTableModel(new String[] { "Fecha", "Usuario", "Modificación", "Tipo", "Saldo Final" },
                0);
        table = new JTable(tableModel);

        // Personalizar el renderer de la tabla
        table.getColumnModel().getColumn(2).setCellRenderer(new ModificacionCellRenderer());
        table.getColumnModel().getColumn(4).setCellRenderer(new SaldoFinalCellRenderer());

        // Agregar la tabla a un JScrollPane para que sea desplazable
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);

        // Cargar los movimientos del archivo CSV y mostrarlos en la tabla
        cargarHistorialMovimientos(usuario);
    }

    private void cargarHistorialMovimientos(String usuario) {
        try {
            // Leer el archivo historialMovimientos.csv
            List<String> lines = Files.readAllLines(Paths.get(RUTA_HISTORIAL_CSV));

            // Filtrar los movimientos para el usuario especificado
            for (String line : lines) {
                String[] data = line.split(",");

                String fecha = data[0];
                String nombreUsuario = data[1];
                String modificacion = data[2];
                String tipo = data[3];
                double saldoFinal = Double.parseDouble(data[4]);

                // Si el movimiento pertenece al usuario dado, añadirlo a la tabla
                if (nombreUsuario.equals(usuario)) {
                    tableModel.addRow(new Object[] { fecha, nombreUsuario, modificacion, tipo, saldoFinal });
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al leer el archivo de historial de movimientos.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // Renderer para la columna "Modificación"
    class ModificacionCellRenderer extends DefaultTableCellRenderer {
        @Override
        protected void setValue(Object value) {
            super.setValue(value);
            String modificacion = (String) value;
            if (modificacion.startsWith("+")) {
                setForeground(Color.GREEN); // Color verde para depósitos
            } else if (modificacion.startsWith("-")) {
                setForeground(Color.RED); // Color rojo para retiros
            } else {
                setForeground(Color.BLACK); // Por defecto, color negro
            }
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
