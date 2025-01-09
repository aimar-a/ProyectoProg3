package gui.mainMenu;

public enum JuegosDisponibles {
    BLACKJACK("Blackjack"),
    CABALLOS("Caballos"),
    RULETA("Ruleta"),
    SLOTS("Slots"),
    MINAS("Minas"),
    DINOSAURIO("Dinosaurio");

    private final String nombre;

    JuegosDisponibles(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}
