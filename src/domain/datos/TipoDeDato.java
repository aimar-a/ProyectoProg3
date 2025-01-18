package domain.datos;

import db.GestorBD;
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

//IAG: ChatGPT y GitHub Copilot
//SIN-CAMBIOS: Ordenar y mejorar el código original.
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

    private static final String REGEX_LETTERS_SPACES = "^[a-zA-Z ]+$";

    public static String validarDato(TipoDeDato tipo, String valor) {
        if (valor == null || valor.isEmpty()) {
            return "El campo no puede estar vacío.";
        }
        return switch (tipo) {
            case USUARIO -> validarUsuario(valor);
            case SALDO -> validarSaldo(valor);
            case CANTIDAD -> validarCantidad(valor);
            case FECHA, FECHA_INSCRIPCION -> validarFecha(valor);
            case HORA -> validarHora(valor);
            case FECHA_DE_NACIMIENTO -> validarFechaNacimiento(valor);
            case MAIL -> validarMail(valor);
            case TELEFONO -> validarTelefono(valor);
            case DNI -> validarDNI(valor);
            case CONTRASENA -> validarContrasena(valor);
            case NOMBRE, APELLIDOS -> validarNombreApellido(valor);
            case PROVINCIA, CIUDAD, CALLE -> validarNombreLugar(valor, tipo);
            case NUMERO -> validarNumero(valor);
            case TITULAR_TARJETA -> validarTitularTarjeta(valor);
            case NUMERO_TARJETA -> validarNumeroTarjeta(valor);
            case FECHA_TARJETA -> validarFechaTarjeta(valor);
            case CVV_TARJETA -> validarCVVTarjeta(valor);
            case NUMERO_CUENTA -> validarNumeroCuenta(valor);
            case TITULAR_TRANSFERENCIA -> validarTitularTransferencia(valor);
            case CONCEPTO_TRANSFERENCIA -> validarConceptoTransferencia(valor);
            case BANCO_TRANSFERENCIA -> validarBancoTransferencia(valor);
            case CANTIDAD_DEPOSITO -> validarCantidadDeposito(valor);
            case CORREO_PAYPAL -> validarDato(MAIL, valor);
            default -> null;
        };
    }

    private static String validarUsuario(String valor) {
        if (GestorBD.usuarioExiste(valor)) {
            return "El usuario ya existe.";
        }
        if (!valor.matches("^[a-zA-Z0-9._-]{3,20}$")) {
            return "El usuario debe tener entre 3 y 20 caracteres alfanuméricos.";
        }
        return null;
    }

    private static String validarSaldo(String valor) {
        try {
            double saldo = Double.parseDouble(valor);
            if (saldo < 0) {
                return "El saldo debe ser mayor o igual a 0.";
            }
        } catch (NumberFormatException e) {
            return "El saldo debe ser un número válido.";
        }
        return null;
    }

    private static String validarCantidad(String valor) {
        try {
            Double.valueOf(valor);
        } catch (NumberFormatException e) {
            return "La cantidad debe ser un número válido.";
        }
        return null;
    }

    private static String validarFecha(String valor) {
        if (!esFechaValida(valor)) {
            return "La fecha no tiene un formato válido (dd-MM-yyyy).";
        }
        return null;
    }

    private static String validarHora(String valor) {
        if (!valor.matches("^([01]?\\d|2[0-3]):[0-5]\\d:[0-5]\\d$")) {
            return "La hora no tiene un formato válido (HH:mm:ss).";
        }
        return null;
    }

    private static String validarFechaNacimiento(String valor) {
        if (!esFechaValida(valor)) {
            return "La fecha de nacimiento no tiene un formato válido (dd-MM-yyyy).";
        }
        LocalDate fechaNacimiento = LocalDate.parse(valor, DATE_FORMATTER);
        if (Period.between(fechaNacimiento, LocalDate.now()).getYears() < 18) {
            return "El usuario debe ser mayor de edad.";
        }
        return null;
    }

    private static String validarMail(String valor) {
        if (!valor.matches("^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            return "El correo electrónico no tiene un formato válido.";
        }
        return null;
    }

    private static String validarTelefono(String valor) {
        if (!valor.matches("^\\+?\\d{9,15}$")) {
            return "El teléfono debe contener entre 9 y 15 dígitos.";
        }
        return null;
    }

    private static String validarDNI(String valor) {
        if (!valor.matches("^\\d{8}[A-Za-z]$")) {
            return "El DNI debe contener 8 dígitos seguidos de una letra.";
        }
        return null;
    }

    private static String validarContrasena(String valor) {
        if (valor.length() < 8) {
            return "La contraseña debe tener al menos 8 caracteres.";
        }
        return null;
    }

    private static String validarNombreApellido(String valor) {
        if (!valor.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$")) {
            return "El nombre o apellido contiene caracteres no permitidos.";
        }
        return null;
    }

    private static String validarNombreLugar(String valor, TipoDeDato tipo) {
        if (!valor.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$")) {
            return "El nombre de la " + tipo.name().toLowerCase() + " no es válido.";
        }
        return null;
    }

    private static String validarNumero(String valor) {
        if (!valor.matches("^\\d+$")) {
            return "El número de la calle no es válido.";
        }
        return null;
    }

    private static String validarTitularTarjeta(String valor) {
        if (!valor.matches(REGEX_LETTERS_SPACES)) {
            return "El titular de la tarjeta no es válido.";
        }
        return null;
    }

    private static String validarNumeroTarjeta(String valor) {
        if (!valor.matches("^\\d{16}$")) {
            return "El número de la tarjeta debe tener 16 dígitos.";
        }
        return null;
    }

    private static String validarFechaTarjeta(String valor) {
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
        return null;
    }

    private static String validarCVVTarjeta(String valor) {
        if (!valor.matches("^\\d{3}$")) {
            return "El CVV de la tarjeta debe tener 3 dígitos.";
        }
        return null;
    }

    private static String validarNumeroCuenta(String valor) {
        if (!valor.matches("[A-Za-z]{2}\\d{26}")) {
            return "El número de cuenta debe tener 2 letras seguidas de 26 dígitos.";
        }
        return null;
    }

    private static String validarTitularTransferencia(String valor) {
        if (!valor.matches(REGEX_LETTERS_SPACES)) {
            return "El titular de la transferencia no es válido.";
        }
        return null;
    }

    private static String validarConceptoTransferencia(String valor) {
        if (!valor.matches("^[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ ]+$")) {
            return "El concepto de la transferencia contiene caracteres no permitidos.";
        }
        return null;
    }

    private static String validarBancoTransferencia(String valor) {
        if (!valor.matches(REGEX_LETTERS_SPACES)) {
            return "El banco de la transferencia no es valido.";
        }
        return null;
    }

    private static String validarCantidadDeposito(String valor) {
        try {
            double cantidad = Double.parseDouble(valor);
            if (cantidad < 0) {
                return "La cantidad debe ser mayor o igual a 0.";
            }
        } catch (NumberFormatException e) {
            return "La cantidad debe ser un número válido.";
        }
        return null;
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
            @SuppressWarnings("unchecked")
            JComboBox<String> dia = (JComboBox<String>) componentes[0];
            @SuppressWarnings("unchecked")
            JComboBox<String> mes = (JComboBox<String>) componentes[1];
            @SuppressWarnings("unchecked")
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
