package gui;

import java.awt.Color;

//IAG: GitHub Copilot
//ADAPTADO: Autocompeltado y pasar de una clase a un enum

public enum ColorVariables {
    COLOR_FONDO_DARK(new Color(50, 50, 50)),
    COLOR_FONDO_LIGHT(Color.WHITE),
    COLOR_TEXTO_DARK(Color.WHITE),
    COLOR_TEXTO_LIGHT(Color.BLACK),
    COLOR_BOTON_DARK(Color.DARK_GRAY),
    COLOR_BOTON_LIGHT(Color.LIGHT_GRAY),
    COLOR_BOTON_TEXTO_DARK(Color.WHITE),
    COLOR_BOTON_TEXTO_LIGHT(Color.BLACK),
    COLOR_ROJO_DARK(new Color(139, 0, 0)),
    COLOR_ROJO_LIGHT(Color.RED),
    COLOR_VERDE_DARK(new Color(0, 100, 0)),
    COLOR_VERDE_LIGHT(new Color(0, 200, 0));

    private final Color color;

    ColorVariables(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
