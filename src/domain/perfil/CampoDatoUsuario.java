package domain.perfil;

import domain.datos.TipoDeDato;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

// IAG: Modificado (ChatGPT y GitHub Copilot)
public class CampoDatoUsuario {
    private static final Font FORMAT_INFO_FONT = new Font("Arial", Font.ITALIC, 10);

    private final JLabel infoLabel;
    private final String texto;
    private final TipoDeDato tipoDeDato;
    private final JComponent[] componentes;
    private final boolean editable;

    public CampoDatoUsuario(String string, JLabel label, TipoDeDato tipoDeDatos, JComponent[] components,
            boolean editable) {
        this.infoLabel = label;
        this.tipoDeDato = tipoDeDatos;
        this.componentes = components;
        this.texto = string;
        this.editable = editable;
    }

    public CampoDatoUsuario(String string, String label, TipoDeDato tipoDeDatos, JComponent[] components,
            boolean editable) {
        this.infoLabel = createLabel(label);
        this.tipoDeDato = tipoDeDatos;
        this.componentes = components;
        this.texto = string;
        this.editable = editable;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setVerticalAlignment(SwingConstants.TOP);
        label.setFont(FORMAT_INFO_FONT);
        return label;
    }

    public JLabel getInfoLabel() {
        return infoLabel;
    }

    public String getTexto() {
        return texto;
    }

    public TipoDeDato getTipoDeDatos() {
        return tipoDeDato;
    }

    public JComponent[] getComponentes() {
        return componentes;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        for (JComponent component : componentes) {
            switch (component) {
                case JTextField jTextField -> jTextField.setEditable(editable && this.editable);
                case JComboBox<?> jComboBox -> jComboBox.setEnabled(editable && this.editable);
                default -> {
                    throw new IllegalArgumentException("Tipo de componente no soportado");
                }
            }
        }
    }

    public boolean validarCampo() {
        return TipoDeDato.comprobarCampoYInfo(componentes, infoLabel, tipoDeDato);
    }
}
