package GUI.mainMenu;

public enum JuegosDisponibles {
    BLACKJACK("Blackjack"),
    CABALLOS("Caballos"),
    RULETA("Ruleta"),
    SLOTS("Slots"),
    MINAS("Minas");

    private final String nombre;

    JuegosDisponibles(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}
