package org.example;

import org.example.ui.Menu;

import java.sql.SQLException;
import java.util.logging.Level;

public class App {
    public static void main(String[] args) throws SQLException {
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
        Menu menu = new Menu();
        while (!Menu.stop) {
            menu.start();
        }
    }
}


