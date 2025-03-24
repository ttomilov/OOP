package org.main;

import org.pizzeria.Pizzeria;

import java.io.File;
import java.io.IOException;

import static java.lang.Thread.sleep;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        File[] successConfig = {
                new File("src"
                        + File.separator
                        + "main"
                        + File.separator
                        + "resources"
                        + File.separator
                        + "configs"
                        + File.separator
                        + "bakerConfig.json"),
                new File("src"
                        + File.separator
                        + "main"
                        + File.separator
                        + "resources"
                        + File.separator
                        + "configs"
                        + File.separator
                        + "delivererConfig.json"),
                new File("src"
                        + File.separator
                        + "main"
                        + File.separator
                        + "resources"
                        + File.separator
                        + "configs"
                        + File.separator
                        + "pizzeriaConfig.json"),
                new File("src"
                        + File.separator
                        + "main"
                        + File.separator
                        + "resources"
                        + File.separator
                        + "configs"
                        + File.separator
                        + "menuConfig.json"),
        };
        Pizzeria pizzeria = new Pizzeria(successConfig[0], successConfig[1], successConfig[2], successConfig[3]);
        for (int i = 0; i < 8; i++) {
            pizzeria.addOrder();
        }
        pizzeria.startNewDay();
        sleep(15000);
        pizzeria.closePizzeria();
    }
}
