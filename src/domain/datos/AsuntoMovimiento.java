package domain.datos;

// IAG: Modificado (ChatGPT y GitHub Copilot)
public enum AsuntoMovimiento {
    BIENVENIDA("bienvenida"),
    DEPOSITO_TARJETA("deposito:tarjeta"),
    DEPOSITO_TRANSFERENCIA("deposito:transferencia"),
    DEPOSITO_PAYPAL("deposito:paypal"),
    RETIRO_TRANSFERENCIA("retiro:transferencia"),
    RETIRO_PAYPAL("retiro:paypal"),

    CABALLOS_APUESTA("caballos:apuesta"),
    CABALLOS_PREMIO("caballos:premio"),
    BLACKJACK_APUESTA("blackjack:apuesta"),
    BLACKJACK_PREMIO("blackjack:premio"),
    BLACKJACK_EMPATE("blackjack:empate"),
    DINO_APUESTA("dino:apuesta"),
    DINO_PREMIO("dino:premio"),
    RULETA_APUESTA("ruleta:apuesta"),
    RULETA_RETIRAR_APUESTA("ruleta:retirar_ap"),
    RULETA_PREMIO("ruleta:premio"),
    SLOTS_APUESTA("slots:apuesta"),
    SLOTS_PREMIO("slots:premio"),
    MINAS_APUESTA("minas:apuesta"),
    MINAS_PREMIO("minas:premio");

    private final String nombre;

    AsuntoMovimiento(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public static AsuntoMovimiento getAsuntoMovimiento(String nombre) {
        for (AsuntoMovimiento asunto : AsuntoMovimiento.values()) {
            if (asunto.getNombre().equals(nombre)) {
                return asunto;
            }
        }
        return null;
    }
}
