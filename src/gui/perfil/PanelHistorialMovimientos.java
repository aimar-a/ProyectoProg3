package gui.perfil;

import db.GestorBD;
import domain.UsuarioActual;
import gui.ColorVariables;
import io.ConfigProperties;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Collections;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

// IAG: Modificado (ChatGPT y GitHub Copilot)
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

    public PanelHistorialMovimientos() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        table = new JTable(tableModel);
        table.setRowHeight(40);
        table.setDefaultRenderer(Object.class, new CustomTableCellRenderer(ConfigProperties.isUiDarkMode()));

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        sorter.setComparator(0, (String o1, String o2) -> o2.compareTo(o1)); // Custom comparator for "Fecha" column
        sorter.setComparator(1, (String o1, String o2) -> o2.compareTo(o1)); // Custom comparator for "Hora" column
        sorter.setComparator(2, (String o1, String o2) -> Integer.compare(Integer.parseInt(o2), Integer.parseInt(o1))); // Custom
                                                                                                                        // comparator
                                                                                                                        // for
                                                                                                                        // "Modificación"
                                                                                                                        // column
        sorter.setComparator(3, (String o1, String o2) -> {
            String[] tipos = { "slots", "caballo", "blackjack", "ruleta", "dino", "minas", "deposito", "retiro",
                    "bienvenida" };
            int index1 = 0, index2 = 0;
            for (int i = 0; i < tipos.length; i++) {
                if (o1.contains(tipos[i])) {
                    index1 = i;
                }
                if (o2.contains(tipos[i])) {
                    index2 = i;
                }
            }
            return Integer.compare(index1, index2);
        }); // Custom comparator for "Tipo" column
        sorter.setComparator(4, (String o1, String o2) -> {
            String[] acciones = { "premio", "apuesta", "empate", "retirar_ap", "deposito", "retiro", "bienvenida" };
            int index1 = 0, index2 = 0;
            for (int i = 0; i < acciones.length; i++) {
                if (o1.contains(acciones[i])) {
                    index1 = i;
                }
                if (o2.contains(acciones[i])) {
                    index2 = i;
                }
            }
            return Integer.compare(index1, index2);
        }); // Custom comparator for "Accion" column
        sorter.setComparator(5, (Integer o1, Integer o2) -> o2.compareTo(o1)); // Custom comparator for "Saldo Final"
                                                                               // column
        table.setRowSorter(sorter);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);

        applyColorScheme(ConfigProperties.isUiDarkMode(), scrollPane);

        cargarHistorialMovimientos(UsuarioActual.getUsuarioActual());

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                if (row >= 0) {
                    int modelRow = table.convertRowIndexToModel(row);
                    String fecha = (String) tableModel.getValueAt(modelRow, 0);
                    String hora = (String) tableModel.getValueAt(modelRow, 1);
                    String modificacion = (String) tableModel.getValueAt(modelRow, 2);
                    String accion = (String) tableModel.getValueAt(modelRow, 4);
                    int saldoFinal = (int) tableModel.getValueAt(modelRow, 5);

                    JOptionPane.showMessageDialog(null,
                            "Fecha: " + fecha + "\n" +
                                    "Hora: " + hora + "\n" +
                                    "Modificación: " + modificacion + "\n" +
                                    "Acción: " + accion.replace(":", ", ") + "\n" +
                                    "Saldo Final: " + saldoFinal,
                            "Detalles del Movimiento",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        table.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                table.clearSelection();
                if (row >= 0) {
                    table.addRowSelectionInterval(row, row);
                }
            }
        });
    }

    private void applyColorScheme(boolean darkMode, JScrollPane scrollPane) {
        if (darkMode) {
            setBackground(ColorVariables.COLOR_FONDO_DARK.getColor());
            table.setBackground(ColorVariables.COLOR_FONDO_DARK.getColor());
            table.setForeground(ColorVariables.COLOR_TEXTO_DARK.getColor());
            table.setGridColor(Color.GRAY);
            scrollPane.getViewport().setBackground(ColorVariables.COLOR_FONDO_DARK.getColor());
        } else {
            setBackground(ColorVariables.COLOR_FONDO_LIGHT.getColor());
            table.setBackground(ColorVariables.COLOR_FONDO_LIGHT.getColor());
            table.setForeground(ColorVariables.COLOR_TEXTO_LIGHT.getColor());
            table.setGridColor(Color.LIGHT_GRAY);
            scrollPane.getViewport().setBackground(ColorVariables.COLOR_FONDO_LIGHT.getColor());
        }
    }

    private void cargarHistorialMovimientos(String usuario) {
        List<String[]> historial = GestorBD.obtenerHistorial(usuario);

        // Ordenar la lista de movimientos por fecha y hora (más recientes primero)
        Collections.sort(historial, (String[] o1, String[] o2) -> {
            String fechaHora1 = o1[0] + " " + o1[1];
            String fechaHora2 = o2[0] + " " + o2[1];
            return fechaHora2.compareTo(fechaHora1); // Orden descendente
        });

        for (String[] data : historial) {
            String fecha = data[0];
            String hora = data[1];
            String modificacion = data[2];
            String asunto = data[3];
            int saldoFinal = Integer.parseInt(data[4]);

            tableModel.addRow(new Object[] { fecha, hora, modificacion, asunto, asunto, saldoFinal });
        }
    }

    private static class CustomTableCellRenderer extends DefaultTableCellRenderer {
        private final boolean darkMode;

        public CustomTableCellRenderer(boolean darkMode) {
            this.darkMode = darkMode;
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int column) {
            JLabel cell = new JLabel(value.toString());
            cell.setFont(new Font("Arial", Font.PLAIN, 16));
            cell.setForeground(darkMode ? ColorVariables.COLOR_TEXTO_DARK.getColor()
                    : ColorVariables.COLOR_TEXTO_LIGHT.getColor());

            if (isSelected) {
                cell.setBackground(darkMode ? Color.DARK_GRAY : Color.LIGHT_GRAY);
                cell.setOpaque(true);
            } else {
                cell.setOpaque(false);
            }

            switch (column) {
                case 0:
                    styleDateColumn(cell);
                    break;
                case 1:
                    styleTimeColumn(cell, value.toString());
                    break;
                case 2:
                    styleModificationColumn(cell, table, row, value.toString());
                    break;
                case 3:
                    styleTypeColumn(cell, value.toString());
                    break;
                case 4:
                    styleActionColumn(cell, value.toString());
                    break;
                case 5:
                    styleFinalBalanceColumn(cell);
                    break;
            }
            return cell;
        }

        private void styleDateColumn(JLabel cell) {
            cell.setHorizontalAlignment(SwingConstants.CENTER);
            cell.setFont(new Font("Arial", Font.ITALIC, 20));
        }

        private void styleTimeColumn(JLabel cell, String time) {
            cell.setHorizontalAlignment(SwingConstants.CENTER);
            cell.setFont(new Font("Arial", Font.ITALIC, 20));
            String[] timeParts = time.split(":");
            int hour = Integer.parseInt(timeParts[0]);
            int minute = Integer.parseInt(timeParts[1]);
            int second = Integer.parseInt(timeParts[2]);
            float intensidad = (hour * 3600 + minute * 60 + second) / 86400f;
            cell.setBackground(new Color(intensidad, intensidad, intensidad));
            cell.setForeground(intensidad > 0.5 ? Color.BLACK : Color.WHITE);
            cell.setOpaque(true);
        }

        private void styleModificationColumn(JLabel cell, JTable table, int row, String value) {
            cell.setHorizontalAlignment(SwingConstants.CENTER);
            cell.setOpaque(true);
            cell.setFont(new Font("Times new roman", Font.BOLD, 20));
            int modificationValue = Integer.parseInt(value);
            if (modificationValue > 0) {
                cell.setText("+" + value);
                cell.setBackground(new Color(0, 255, 0, 50));
            } else if (modificationValue < 0) {
                cell.setBackground(new Color(255, 0, 0, 50));
            } else {
                cell.setBackground(new Color(255, 255, 0, 50));
            }
            String type = (String) table.getValueAt(row, 3);
            if (type.contains("victoria")) {
                cell.setForeground(Color.GREEN);
            } else if (type.contains("apuesta")) {
                cell.setForeground(Color.RED);
            } else if (type.contains("deposito")) {
                cell.setForeground(Color.BLUE);
            } else if (type.contains("retiro")) {
                cell.setForeground(Color.ORANGE);
            } else if (type.contains("bienvenida")) {
                cell.setForeground(Color.MAGENTA);
            }
        }

        private void styleTypeColumn(JLabel cell, String value) {
            cell.setHorizontalAlignment(SwingConstants.CENTER);
            cell.setText("");
            ImageIcon icon = getIconForType(value);
            if (icon != null) {
                Image image = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
                cell.setIcon(new ImageIcon(image));
            } else {
                cell.setText(value);
            }
        }

        private void styleActionColumn(JLabel cell, String value) {
            cell.setHorizontalAlignment(SwingConstants.CENTER);
            cell.setText("");
            ImageIcon icon = getIconForAction(value);
            if (icon != null) {
                Image image = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
                cell.setIcon(new ImageIcon(image));
            } else {
                cell.setText(value);
            }
        }

        private void styleFinalBalanceColumn(JLabel cell) {
            cell.setHorizontalAlignment(SwingConstants.RIGHT);
            cell.setFont(cell.getFont().deriveFont(Font.BOLD, 25f));
        }

        private ImageIcon getIconForType(String value) {
            if (value.contains("slots")) {
                return new ImageIcon("resources/img/perfil/movimientos/slot.png");
            } else if (value.contains("caballo")) {
                return new ImageIcon("resources/img/perfil/movimientos/caballo.png");
            } else if (value.contains("blackjack")) {
                return new ImageIcon("resources/img/perfil/movimientos/blackjack.png");
            } else if (value.contains("ruleta")) {
                return new ImageIcon("resources/img/perfil/movimientos/ruleta.png");
            } else if (value.contains("dino")) {
                return new ImageIcon("resources/img/perfil/movimientos/dino.png");
            } else if (value.contains("minas")) {
                return new ImageIcon("resources/img/perfil/movimientos/minas.png");
            } else if (value.contains("deposito")) {
                return new ImageIcon("resources/img/perfil/movimientos/deposito.png");
            } else if (value.contains("retiro")) {
                return new ImageIcon("resources/img/perfil/movimientos/retiro.png");
            } else if (value.contains("bienvenida")) {
                return new ImageIcon("resources/img/perfil/movimientos/bienvenida.png");
            }
            return null;
        }

        private ImageIcon getIconForAction(String value) {
            if (value.contains("premio")) {
                return new ImageIcon("resources/img/perfil/movimientos/premio.png");
            } else if (value.contains("apuesta")) {
                return new ImageIcon("resources/img/perfil/movimientos/apuesta.png");
            } else if (value.contains("empate")) {
                return new ImageIcon("resources/img/perfil/movimientos/empate.png");
            } else if (value.contains("retirar_ap")) {
                return new ImageIcon("resources/img/perfil/movimientos/cancelar.png");
            } else if (value.contains("deposito")) {
                return new ImageIcon("resources/img/perfil/movimientos/deposito.png");
            } else if (value.contains("retiro")) {
                return new ImageIcon("resources/img/perfil/movimientos/retiro.png");
            } else if (value.contains("bienvenida")) {
                return new ImageIcon("resources/img/perfil/movimientos/bienvenida.png");
            }
            return null;
        }
    }
}
