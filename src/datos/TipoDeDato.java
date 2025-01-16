package datos;

import java.awt.Color;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.format.SignStyle;
import java.time.temporal.ChronoField;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

// IAG: Modificado (ChatGPT y GitHub Copilot)
public enum TipoDeDato {
    USUARIO,
    SALDO,
    FECHA,
    HORA,
    CANTIDAD,
    ASUNTO,
    SALDOFINAL,
    CONTRASENA,
    NOMBRE,
    APELLIDOS,
    DNI,
    MAIL,
    TELEFONO,
    PROVINCIA,
    CIUDAD,
    CALLE,
    NUMERO,
    FECHA_DE_NACIMIENTO,
    FECHA_INSCRIPCION,
    CANTIDAD_DEPOSITO,
    TITULAR_TARJETA,
    NUMERO_TARJETA,
    FECHA_TARJETA,
    CVV_TARJETA,
    NUMERO_CUENTA,
    TITULAR_TRANSFERENCIA,
    CONCEPTO_TRANSFERENCIA,
    BANCO_TRANSFERENCIA,
    CORREO_PAYPAL,
    CONTRASENA_PAYPAL,
    FECHA_DE_CREACION,
    DIRECCION;

    private static final DateTimeFormatter DATE_FORMATTER = new DateTimeFormatterBuilder()
            .appendValue(ChronoField.DAY_OF_MONTH, 1, 2, SignStyle.NOT_NEGATIVE)
            .appendLiteral('-')
            .appendValue(ChronoField.MONTH_OF_YEAR, 1, 2, SignStyle.NOT_NEGATIVE)
            .appendLiteral('-')
            .appendValue(ChronoField.YEAR, 4)
            .toFormatter();

    public static String validarDato(TipoDeDato tipo, String valor) {
        if (valor == null || valor.isEmpty()) {
            return "El campo no puede estar vacío.";
        }
        switch (tipo) {
            case USUARIO:
                if (GestorBD.usuarioExiste(valor)) {
                    return "El usuario ya existe.";
                }
                if (!valor.matches("^[a-zA-Z0-9._-]{3,20}$")) {
                    return "El usuario debe tener entre 3 y 20 caracteres alfanuméricos.";
                }
                break;

            case SALDO:
                try {
                    double saldo = Double.parseDouble(valor);
                    if (saldo < 0) {
                        return "El saldo debe ser mayor o igual a 0.";
                    }
                } catch (NumberFormatException e) {
                    return "El saldo debe ser un número válido.";
                }
                break;

            case CANTIDAD:
                try {
                    Double.parseDouble(valor); // Positivo o negativo permitido
                } catch (NumberFormatException e) {
                    return "La cantidad debe ser un número válido.";
                }
                break;

            case FECHA:
            case FECHA_INSCRIPCION:
                if (!esFechaValida(valor)) {
                    return "La fecha no tiene un formato válido (dd-MM-yyyy).";
                }
                break;

            case HORA:
                if (!valor.matches("^([01]?\\d|2[0-3]):[0-5]\\d:[0-5]\\d$")) {
                    return "La hora no tiene un formato válido (HH:mm:ss).";
                }
                break;

            case FECHA_DE_NACIMIENTO:
                if (!esFechaValida(valor)) {
                    return "La fecha de nacimiento no tiene un formato válido (dd-MM-yyyy).";
                }
                LocalDate fechaNacimiento = LocalDate.parse(valor, DATE_FORMATTER);
                if (Period.between(fechaNacimiento, LocalDate.now()).getYears() < 18) {
                    return "El usuario debe ser mayor de edad.";
                }
                break;

            case MAIL:
                if (!valor.matches("^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
                    return "El correo electrónico no tiene un formato válido.";
                }
                break;

            case TELEFONO:
                if (!valor.matches("^\\+?\\d{9,15}$")) {
                    return "El teléfono debe contener entre 9 y 15 dígitos.";
                }
                break;

            case DNI:
                if (!valor.matches("^\\d{8}[A-Za-z]$")) {
                    return "El DNI debe contener 8 dígitos seguidos de una letra.";
                }
                break;

            case CONTRASENA:
                if (valor.length() < 8) {
                    return "La contraseña debe tener al menos 8 caracteres.";
                }
                break;

            case NOMBRE:
            case APELLIDOS:
                if (!valor.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$")) {
                    return "El nombre o apellido contiene caracteres no permitidos.";
                }
                break;

            case PROVINCIA:
            case CIUDAD:
            case CALLE:
                if (!valor.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$")) {
                    return "El nombre de la " + tipo.name().toLowerCase() + " no es válido.";
                }
                break;

            case NUMERO:
                if (!valor.matches("^\\d+$")) {
                    return "El número de la calle no es válido.";
                }
                break;

            case TITULAR_TARJETA:
                if (!valor.matches("^[a-zA-Z ]+$")) {
                    return "El titular de la tarjeta no es válido.";
                }
                break;

            case NUMERO_TARJETA:
                if (!valor.matches("^\\d{16}$")) {
                    return "El número de la tarjeta debe tener 16 dígitos.";
                }
                break;

            case FECHA_TARJETA:
                if (!valor.contains("/") || valor.split("/").length != 2) {
                    return "La fecha de la tarjeta debe tener el formato MM/AA.";
                }
                String fecha = 1 + "-" + valor.split("/")[0] + "-20" + valor.split("/")[1];
                if (!esFechaValida(fecha)) {
                    return "La fecha de la tarjeta no es una fecha válida.";
                }
                if (LocalDate.parse(fecha, DATE_FORMATTER).isBefore(LocalDate.now())) {
                    return "La tarjeta no puede estar caducada.";
                }
                break;

            case CVV_TARJETA:
                if (!valor.matches("^\\d{3}$")) {
                    return "El CVV de la tarjeta debe tener 3 dígitos.";
                }
                break;

            case NUMERO_CUENTA:
                if (!valor.matches("[A-Za-z]{2}\\d{26}")) {
                    return "El número de cuenta debe tener 2 letras seguidas de 26 dígitos.";
                }
                break;

            case TITULAR_TRANSFERENCIA:
                if (!valor.matches("^[a-zA-Z ]+$")) {
                    return "El titular de la transferencia no es válido.";
                }
                break;

            case CONCEPTO_TRANSFERENCIA:
                if (!valor.matches("^[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ ]+$")) {
                    return "El concepto de la transferencia contiene caracteres no permitidos.";
                }
                break;

            case BANCO_TRANSFERENCIA:
                if (!valor.matches("^[a-zA-Z ]+$")) {
                    return "El banco de la transferencia no es valido.";
                }
                break;

            case CANTIDAD_DEPOSITO:
                try {
                    double cantidad = Double.parseDouble(valor);
                    if (cantidad < 0) {
                        return "La cantidad debe ser mayor o igual a 0.";
                    }
                } catch (NumberFormatException e) {
                    return "La cantidad debe ser un número válido.";
                }
                break;

            case CORREO_PAYPAL:
                return validarDato(MAIL, valor);

            default:
                break;
        }
        return null; // Si es válido, devolvemos null
    }

    public static String[] validarCampos(TipoDeDato[] tipos, JTextField[] campos) {
        if (tipos.length != campos.length) {
            throw new IllegalArgumentException("Los arrays de tipos y campos deben tener la misma longitud.");
        }
        String[] errores = new String[tipos.length];
        for (int i = 0; i < tipos.length; i++) {
            errores[i] = validarDato(tipos[i], campos[i].getText());
        }
        return errores;
    }

    public static boolean comprobarCampoYInfo(JComponent[] componentes, JLabel info, TipoDeDato tipo) {
        if (tipo == DIRECCION) {
            JTextField calle = (JTextField) componentes[0];
            JTextField numero = (JTextField) componentes[1];
            return comprobarDireccion(calle, numero, info);
        }
        if (tipo == FECHA_DE_NACIMIENTO) {
            JComboBox<String> dia = (JComboBox<String>) componentes[0];
            JComboBox<String> mes = (JComboBox<String>) componentes[1];
            JComboBox<String> ano = (JComboBox<String>) componentes[2];
            return comprobarFechaNacCombos(dia, mes, ano, info);
        }
        if (tipo == TELEFONO) {
            return comprobarCampoYInfo((JTextField) componentes[1], info, tipo);
        }
        return comprobarCampoYInfo((JTextField) componentes[0], info, tipo);
    }

    private static boolean comprobarCampoYInfo(JTextField campo, JLabel info, TipoDeDato tipo) {
        String error = validarDato(tipo, campo.getText());
        if (error != null) {
            info.setText(error);
            campo.setForeground(Color.RED);
            return false;
        }
        info.setText("");
        campo.setForeground(Color.BLACK);
        return true;
    }

    public static boolean comprobarCamposYInfo(JTextField[] campos, JLabel[] info, TipoDeDato[] tipos) {
        if (tipos.length != campos.length || tipos.length != info.length) {
            throw new IllegalArgumentException("Los arrays de tipos, campos e info deben tener la misma longitud.");
        }
        boolean valido = true;
        String[] errores = validarCampos(tipos, campos);
        for (int i = 0; i < errores.length; i++) {
            if (errores[i] != null) {
                info[i].setText(errores[i]);
                campos[i].setForeground(Color.RED);
                valido = false;
            } else {
                info[i].setText("");
                campos[i].setForeground(Color.BLACK);
            }
        }
        return valido;
    }

    public static boolean comprobarFechaNacCombos(JComboBox<String> dia, JComboBox<String> mes, JComboBox<String> ano,
            JLabel info) {
        String fecha = formatFecha(dia.getSelectedItem(), mes.getSelectedItem(), ano.getSelectedItem());
        String error = validarDato(FECHA_DE_NACIMIENTO, fecha);
        if (error != null) {
            info.setText(error);
            return false;
        }
        info.setText("");
        return true;
    }

    public static boolean comprobarDireccion(JTextField calle, JTextField numero, JLabel info) {
        String[] errores = validarCampos(new TipoDeDato[] { CALLE, NUMERO }, new JTextField[] { calle, numero });
        if (errores[0] != null) {
            calle.setForeground(Color.RED);
            info.setText(errores[0]);
            return false;
        }
        if (errores[1] != null) {
            numero.setForeground(Color.RED);
            info.setText(errores[1]);
            return false;
        }
        calle.setForeground(Color.BLACK);
        numero.setForeground(Color.BLACK);
        info.setText("");
        return true;
    }

    private static boolean esFechaValida(String fecha) {
        try {
            LocalDate.parse(fecha, DATE_FORMATTER);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static String formatFecha(Object dia, Object mes, Object ano) {
        String diaFormat = String.format("%02d", Integer.valueOf((String) dia));
        String mesFormat = String.format("%02d", Integer.valueOf((String) mes));
        return diaFormat + "-" + mesFormat + "-" + ano;
    }
}
