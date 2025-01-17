package main;

import db.GestorBD;
import gui.mainMenu.FrameMenuPrincipal;
import io.ConfigProperties;

public class Main {
    public static void main(String[] args) {
        ConfigProperties.init();
        GestorBD.init();
        FrameMenuPrincipal menup = new FrameMenuPrincipal();
        menup.setVisible(true);
    }
}
