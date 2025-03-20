package org.main;

import org.pizzeria.Pizzeria;

import java.io.File;
import java.io.IOException;

import static java.lang.Thread.sleep;

public class Main {
    static File[] successConfig = {
            new File("src"
                    + File.separator
                    + "main"
                    + File.separator
                    + "resources"
                    + File.separator
                    + "configs/bakerConfig.json"),
            new File("src"
                    + File.separator
                    + "main"
                    + File.separator
                    + "resources"
                    + File.separator
                    + "configs/delivererConfig.json"),
            new File("src"
                    + File.separator
                    + "main"
                    + File.separator
                    + "resources"
                    + File.separator
                    + "configs/pizzeriaConfig.json"),
            new File("src"
                    + File.separator
                    + "main"
                    + File.separator
                    + "resources"
                    + File.separator
                    + "configs/menuConfig.json"),
    };

    public static void main(String[] args) throws IOException, InterruptedException {
        Pizzeria pizza = new Pizzeria(successConfig[0], successConfig[1], successConfig[2], successConfig[3]);

        for (int i = 0; i < 8; i++){
            pizza.addOrder();
        }

        pizza.startWorkDay();

        sleep(11000);

        pizza.closePizzeria();

        System.exit(0);
    }
}
