package org.test;

import org.exception.InvalidJsonFormatException;
import org.junit.jupiter.api.Assertions;
import org.pizzeria.Pizzeria;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class Test {
    File[] errorBaker = {
            new File("src"
                    + File.separator
                    + "test"
                    + File.separator
                    + "resources"
                    + File.separator
                    + "error"
                    + File.separator
                    + "errorBakerConfig.json"),
            new File("src"
                    + File.separator
                    + "test"
                    + File.separator
                    + "resources"
                    + File.separator
                    + "error"
                    + File.separator
                    + "errorBakerConfig1.json")
    };
    File[] errorDeliverer = {
            new File("src"
                    + File.separator
                    + "test"
                    + File.separator
                    + "resources"
                    + File.separator
                    + "error"
                    + File.separator
                    + "errorDelivererConfig.json"),
            new File("src"
                    + File.separator
                    + "test"
                    + File.separator
                    + "resources"
                    + File.separator
                    + "error"
                    + File.separator
                    + "errorDelivererConfig1.json"),
            new File("src"
                    + File.separator
                    + "test"
                    + File.separator
                    + "resources"
                    + File.separator
                    + "error"
                    + File.separator
                    + "errorDelivererConfig2.json")
    };
    File[] errorPizzeria = {
            new File("src"
                    + File.separator
                    + "test"
                    + File.separator
                    + "resources"
                    + File.separator
                    + "error"
                    + File.separator
                    + "errorPizzeriaConfig.json"),
            new File("src"
                    + File.separator
                    + "test"
                    + File.separator
                    + "resources"
                    + File.separator
                    + "error"
                    + File.separator
                    + "errorPizzeriaConfig1.json"),
            new File("src"
                    + File.separator
                    + "test"
                    + File.separator
                    + "resources"
                    + File.separator
                    + "error"
                    + File.separator
                    + "errorPizzeriaConfig2.json")
    };
    File[] successConfig = {
            new File("src"
                    + File.separator
                    + "test"
                    + File.separator
                    + "resources"
                    + File.separator
                    + "success"
                    + File.separator
                    + "configs/bakerConfig.json"),
            new File("src"
                    + File.separator
                    + "test"
                    + File.separator
                    + "resources"
                    + File.separator
                    + "success"
                    + File.separator
                    + "configs/delivererConfig.json"),
            new File("src"
                    + File.separator
                    + "test"
                    + File.separator
                    + "resources"
                    + File.separator
                    + "success"
                    + File.separator
                    + "configs/pizzeriaConfig.json"),
            new File("src"
                    + File.separator
                    + "test"
                    + File.separator
                    + "resources"
                    + File.separator
                    + "success"
                    + File.separator
                    + "configs/menuConfig.json"),
    };
    File errorMenuConfig = new File("src"
            + File.separator
            + "test"
            + File.separator
            + "resources"
            + File.separator
            + "error"
            + File.separator
            + "errorMenuConfig.json");

    boolean parser(String find, File output) {
        return true;
    }

    @org.junit.jupiter.api.Test
    public void errorTest1() throws IOException {
        try{
            Pizzeria pizzeria1 = new Pizzeria(errorBaker[0], errorDeliverer[0], errorPizzeria[0], errorMenuConfig);
            Assertions.fail();
        } catch (InvalidJsonFormatException e) {
            System.out.println(e.getMessage());
        }

        try{
            Pizzeria pizzeria2 = new Pizzeria(successConfig[0], errorDeliverer[0], errorPizzeria[0], errorMenuConfig);
            Assertions.fail();
        } catch (InvalidJsonFormatException e) {
            System.out.println(e.getMessage());
        }

        try{
            Pizzeria pizzeria3 = new Pizzeria(successConfig[0], successConfig[1], errorPizzeria[0], errorMenuConfig);
            Assertions.fail();
        } catch (InvalidJsonFormatException e) {
            System.out.println(e.getMessage());
        }
    }

    @org.junit.jupiter.api.Test
    public void errorTest2() throws IOException {
        try{
            Pizzeria pizzeria1 = new Pizzeria(errorBaker[1], errorDeliverer[1], errorPizzeria[1], successConfig[3]);
            Assertions.fail();
        } catch (InvalidJsonFormatException e) {
            System.out.println(e.getMessage());
        }

        try{
            Pizzeria pizzeria2 = new Pizzeria(successConfig[0], errorDeliverer[1], errorPizzeria[1], successConfig[3]);
            Assertions.fail();
        } catch (InvalidJsonFormatException e) {
            System.out.println(e.getMessage());
        }

        try{
            Pizzeria pizzeria3 = new Pizzeria(successConfig[0], successConfig[1], errorPizzeria[1], successConfig[3]);
            Assertions.fail();
        } catch (InvalidJsonFormatException e) {
            System.out.println(e.getMessage());
        }
    }

    @org.junit.jupiter.api.Test
    public void errorTest3() throws IOException {
        try{
            Pizzeria pizzeria1 = new Pizzeria(successConfig[0], errorDeliverer[2], errorPizzeria[2], successConfig[3]);
            Assertions.fail();
        } catch (InvalidJsonFormatException e) {
            System.out.println(e.getMessage());
        }

        try{
            Pizzeria pizzeria2 = new Pizzeria(successConfig[0], successConfig[1], errorPizzeria[2], successConfig[3]);
            Assertions.fail();
        } catch (InvalidJsonFormatException e) {
            System.out.println(e.getMessage());
        }
    }

    @org.junit.jupiter.api.Test
    public void pizzeriaTest() throws IOException, InterruptedException {
        Pizzeria pizzeria = new Pizzeria(successConfig[0], successConfig[1], successConfig[2], successConfig[3]);
        pizzeria.addOrder();
        pizzeria.startWorkDay();
        pizzeria.closePizzeria();
    }
}
