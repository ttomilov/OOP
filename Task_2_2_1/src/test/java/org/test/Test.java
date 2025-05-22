package org.test;

import org.exception.InvalidJsonFormatException;
import org.junit.jupiter.api.Assertions;
import org.pizzeria.Pizzeria;

import java.io.*;
import java.util.Vector;

import static java.lang.Thread.sleep;
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
                    + "bakerConfig.json"),
            new File("src"
                    + File.separator
                    + "test"
                    + File.separator
                    + "resources"
                    + File.separator
                    + "success"
                    + File.separator
                    + "delivererConfig.json"),
            new File("src"
                    + File.separator
                    + "test"
                    + File.separator
                    + "resources"
                    + File.separator
                    + "success"
                    + File.separator
                    + "pizzeriaConfig.json"),
            new File("src"
                    + File.separator
                    + "test"
                    + File.separator
                    + "resources"
                    + File.separator
                    + "success"
                    + File.separator
                    + "menuConfig.json"),
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

    boolean parser(String find, BufferedReader output) throws IOException {
        String[] findTokens = find.split(" ");
        String line = output.readLine();
        int count;
        Vector<String> results = new Vector<String>();
        while (line != null) {
            String[] tokens = line.split(" ");
            int indxTokens = 6;
            for (int i = 0; i < findTokens.length; i++) {
                if (findTokens[i].equals(tokens[indxTokens])) {
                    results.add(findTokens[i]);
                    indxTokens++;
                } else {
                    results.clear();
                    break;
                }
            }
            if (findTokens.length == results.size()) {
                return true;
            }
            line = output.readLine();
        }
        return false;
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
        pizzeria.startNewDay();
        sleep(13000);
        pizzeria.closePizzeria();
        FileReader fileReader = new FileReader("logs/pizzeria.log");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        assertTrue(parser("Pizzeria was inited", bufferedReader));
        assertTrue(parser("Bakers were inited", bufferedReader));
        assertTrue(parser("Deliverers were inited", bufferedReader));
        assertTrue(parser("Menu was inited", bufferedReader));
        assertTrue(parser("Order 1 has been accepted", bufferedReader));
        assertTrue(parser("Started 1 work day", bufferedReader));
        assertTrue(parser("Ended 1 work day", bufferedReader));
        assertTrue(parser("Waiting the new day...", bufferedReader));
        assertTrue(parser("Clock ended", bufferedReader));
        assertTrue(parser("Baker 1 finished work", bufferedReader));
        assertTrue(parser("Baker 2 finished work", bufferedReader));
        assertTrue(parser("Baker 3 finished work", bufferedReader));
        assertTrue(parser("Baker 4 finished work", bufferedReader));
        assertTrue(parser("Deliverer 5 finished work", bufferedReader));
        assertTrue(parser("Deliverer 6 finished work", bufferedReader));
        assertTrue(parser("Deliverer 7 finished work", bufferedReader));
        assertTrue(parser("Deliverer 8 finished work", bufferedReader));
        assertTrue(parser("Pizzeria finished 1 work day", bufferedReader));
    }
}
