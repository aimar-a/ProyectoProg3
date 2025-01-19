package gui.juegos.slots;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

//IAG: GitHub Copilot
//ADAPTADO: Anadir funcionalidades y autocompeltado
public class PanelPremios extends JPanel {
    public PanelPremios() {
        super(new GridBagLayout());
        this.setOpaque(true);
        this.setBackground(Color.ORANGE);
        this.setPreferredSize(new Dimension(300, 720));

        JTable tablaPremios = new JTable(new DefaultTableModel(new String[] { "A", "B", "C", "Premio" }, 0));
        tablaPremios.setRowHeight(60);
        tablaPremios.getColumnModel().getColumn(0).setPreferredWidth(60);
        tablaPremios.getColumnModel().getColumn(1).setPreferredWidth(60);
        tablaPremios.getColumnModel().getColumn(2).setPreferredWidth(60);
        tablaPremios.getColumnModel().getColumn(3).setPreferredWidth(80);
        tablaPremios.setBackground(Color.ORANGE);
        tablaPremios.setForeground(Color.BLACK);
        tablaPremios.setFont(tablaPremios.getFont().deriveFont(20.0f));
        tablaPremios.setRowSelectionAllowed(false);
        tablaPremios.setCellSelectionEnabled(false);
        tablaPremios.setFocusable(false);
        tablaPremios.setShowGrid(false);
        tablaPremios.setTableHeader(null);
        tablaPremios.setFillsViewportHeight(true);
        this.add(tablaPremios);

        tablaPremios.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            private static final long serialVersionUID = 1L;

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                JLabel c = new JLabel("");
                c.setBackground(row % 2 == 0 ? Color.ORANGE : Color.YELLOW);
                if (column == 0 || column == 1 || column == 2) {
                    ImageIcon icon = new ImageIcon("resources/img/juegos/slots/simbolos/slot" + value + ".png");
                    Image scaledImage = icon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
                    c.setIcon(new ImageIcon(scaledImage));
                    c.setHorizontalAlignment(JLabel.CENTER);
                } else {
                    c.setText("  x" + value.toString());
                    c.setHorizontalAlignment(JLabel.LEFT);
                }
                return c;
            }
        });

        try (BufferedReader br = new BufferedReader(new FileReader("resources/csv/ListaPremios.csv"))) {
            String line;
            DefaultTableModel model = (DefaultTableModel) tablaPremios.getModel();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                model.addRow(values);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
