package gui.perfil;

import db.GestorBD;
import gui.ColorVariables;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
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
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        table = new JTable(tableModel);
        table.setRowHeight(40);
        table.setDefaultRenderer(Object.class, new CustomTableCellRenderer(darkMode));

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);

        applyColorScheme(darkMode, scrollPane);

        cargarHistorialMovimientos(usuario);
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
                return new ImageIcon("resources/img/perfil/slot.png");
            } else if (value.contains("caballo")) {
                return new ImageIcon("resources/img/perfil/caballo.png");
            } else if (value.contains("blackjack")) {
                return new ImageIcon("resources/img/perfil/blackjack.png");
            } else if (value.contains("ruleta")) {
                return new ImageIcon("resources/img/perfil/ruleta.png");
            } else if (value.contains("dino")) {
                return new ImageIcon("resources/img/perfil/dino.png");
            } else if (value.contains("minas")) {
                return new ImageIcon("resources/img/perfil/minas.png");
            } else if (value.contains("deposito")) {
                return new ImageIcon("resources/img/perfil/deposito.png");
            } else if (value.contains("retiro")) {
                return new ImageIcon("resources/img/perfil/retiro.png");
            } else if (value.contains("bienvenida")) {
                return new ImageIcon("resources/img/perfil/bienvenida.png");
            }
            return null;
        }

        private ImageIcon getIconForAction(String value) {
            if (value.contains("premio")) {
                return new ImageIcon("resources/img/perfil/premio.png");
            } else if (value.contains("apuesta")) {
                return new ImageIcon("resources/img/perfil/apuesta.png");
            } else if (value.contains("empate")) {
                return new ImageIcon("resources/img/perfil/empate.png");
            } else if (value.contains("retirar_ap")) {
                return new ImageIcon("resources/img/perfil/cancelar.png");
            } else if (value.contains("deposito")) {
                return new ImageIcon("resources/img/perfil/deposito.png");
            } else if (value.contains("retiro")) {
                return new ImageIcon("resources/img/perfil/retiro.png");
            } else if (value.contains("bienvenida")) {
                return new ImageIcon("resources/img/perfil/bienvenida.png");
            }
            return null;
        }
    }
}
