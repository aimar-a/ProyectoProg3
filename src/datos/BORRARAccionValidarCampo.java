package datos;

import GUI.perfil.BORRARTiposDeDatos;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JTextField;

public class BORRARAccionValidarCampo {
    public static void verificarCampos(JButton btnGuardar, JTextField[] camposTexto,
            TiposDeDatos[] regex) {
        boolean esValido = true;

        for (int i = 0; i < camposTexto.length; i++) {
            esValido &= validarCampo(camposTexto[i], regex[i].getRegex());
            if (regex[i] == TiposDeDatos.USUARIO && GestorUsuarios.usuarioExiste(camposTexto[i].getText())) {
                esValido = false;
                camposTexto[i].setForeground(Color.BLACK);
            }
        }

        btnGuardar.setEnabled(esValido);
    }

    public static boolean validarCampo(JTextField campo, String regex) {
        boolean esValido = !campo.getText().trim().isEmpty() && campo.getText().matches(regex);
        campo.setForeground(esValido ? Color.BLACK : Color.RED);
        return esValido;
    }
}