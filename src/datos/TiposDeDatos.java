package datos;

import java.awt.Color;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javax.swing.JLabel;
import javax.swing.JTextField;

// IAG
public enum TiposDeDatos {
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
    FECHA_INSCRIPCION;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static String validarDato(TiposDeDatos tipo, String valor) {
        if (valor == null || valor.isEmpty()) {
            return "El campo no puede estar vacío.";
        }
        switch (tipo) {
            case USUARIO:
                if (GestorUsuarios.usuarioExiste(valor)) {
                    return "El usuario ya existe.";
                }
                if (valor == null || valor.isEmpty() || !valor.matches("^[a-zA-Z0-9._-]{3,20}$")) {
                    return "El nombre de usuario no es válido. Debe tener entre 3 y 20 caracteres alfanuméricos.";
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
                if (!valor.matches("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
                    return "El correo electrónico no tiene un formato válido.";
                }
                break;

            case TELEFONO:
                if (!valor.matches("^\\+?\\d{9,15}$")) {
                    return "El teléfono no tiene un formato válido. Debe contener entre 9 y 15 dígitos.";
                }
                break;

            case DNI:
                if (!valor.matches("^\\d{8}[A-Za-z]$")) {
                    return "El DNI no tiene un formato válido. Debe contener 8 dígitos seguidos de una letra.";
                }
                break;

            case CONTRASENA:
                if (valor == null || valor.length() < 8) {
                    return "La contraseña debe tener al menos 8 caracteres.";
                }
                break;

            case NOMBRE:
            case APELLIDOS:
                if (valor == null || !valor.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$")) {
                    return "El nombre o apellido contiene caracteres no permitidos.";
                }
                break;

            case PROVINCIA:
            case CIUDAD:
            case CALLE:
                if (valor == null || valor.isEmpty()) {
                    return "El campo no puede estar vacío.";
                }
                break;

            case NUMERO:
                if (valor == null || valor.isEmpty() || !valor.matches("^\\d+$")) {
                    return "El número de la calle no es válido.";
                }
                break;

            default:
                return "Tipo de dato no reconocido.";
        }
        return null; // Si es válido, devolvemos null
    }

    public static String[] validarCampos(TiposDeDatos[] tipos, JTextField[] campos) {
        if (tipos.length != campos.length) {
            throw new IllegalArgumentException("Los arrays de tipos y campos deben tener la misma longitud.");
        }
        String[] errores = new String[tipos.length];
        for (int i = 0; i < tipos.length; i++) {
            errores[i] = validarDato(tipos[i], campos[i].getText());
        }
        return errores;
    }

    public static boolean comprobarCamposYInfo(JTextField[] campos, JLabel[] info, TiposDeDatos[] tipos) {
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

    private static boolean esFechaValida(String fecha) {
        try {
            LocalDate.parse(fecha, DATE_FORMATTER);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
