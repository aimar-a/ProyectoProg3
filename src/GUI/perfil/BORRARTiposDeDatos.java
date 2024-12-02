package GUI.perfil;

public enum BORRARTiposDeDatos {
    USUARIO("[a-zA-Z0-9]+"),
    NOMBRE("[a-zA-ZáéíóúÁÉÍÓÚñÑ]+"),
    APELLIDOS("[a-zA-ZáéíóúÁÉÍÓÚñÑ]+( [a-zA-ZáéíóúÁÉÍÓÚñÑ]+)*"),
    DNI("\\d{8}[A-HJ-NP-TV-Z]"),
    EMAIL("^[A-Za-z0-9+_.-]+@(.+)$"),
    TELEFONO("\\+\\d{1,3} \\d{7,10}"),
    PROVINCIA("[a-zA-ZáéíóúÁÉÍÓÚñÑ]+"),
    CIUDAD("[a-zA-ZáéíóúÁÉÍÓÚñÑ]+"),
    DIRECCION("^[a-zA-Z0-9 ]+, \\d+$"),
    FECHA_NACIMIENTO("\\d{2}/\\d{2}/\\d{4}");

    private final String regex;

    TiposDeDatos(String regex) {
        this.regex = regex;
    }

    public String getRegex() {
        return regex;
    }
}
