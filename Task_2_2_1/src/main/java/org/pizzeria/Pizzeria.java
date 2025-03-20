package org.pizzeria;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.exception.InvalidJsonFormatException;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.configs.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;

public class Pizzeria {
    private Queue<Order> orderQueue;
    private Queue<Order> warehouse;
    private Vector<Worker> bakers;
    private Vector<Worker> deliverers;
    private Clock clock;
    private final BakerConfig bakerConfig;
    private final DelivererConfig delivererConfig;
    private final PizzeriaConfig pizzeriaConfig;
    private final MenuConfig menuConfig;
    private boolean isWorking;
    private boolean finished;
    private int workerID = 1;
    private int workDay = 1;
    private volatile int orderID = 1;
    private Vector<String> menu;
    private int numPizzas = 0;
    private static final Logger logger = LogManager.getLogger(Pizzeria.class);


    public Pizzeria(File bakerConfigJson, File delivererConfigJson, File pizzeriaConfigJson, File menuConfigJson) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        bakerConfig = objectMapper.readValue(bakerConfigJson, BakerConfig.class);
        delivererConfig = objectMapper.readValue(delivererConfigJson, DelivererConfig.class);
        pizzeriaConfig = objectMapper.readValue(pizzeriaConfigJson, PizzeriaConfig.class);
        menuConfig = objectMapper.readValue(menuConfigJson, MenuConfig.class);

        exceptionCheck();

        initPizzeria();
        initBakers();
        initDeliverers();
        initMenu();

        finished = false;
    }

    private void exceptionCheck() {
        bakerCheck();
        delivererCheck();
        pizzeriaCheck();
        menuCheck();
    }

    private void bakerCheck() {
        if (bakerConfig == null) {
            throw new InvalidJsonFormatException("BakerConfig is null");
        }

        if (bakerConfig.getNumBakers() <= 0) {
            throw new InvalidJsonFormatException("Number of bakers must be greater than 0");
        }

        if (delivererConfig.getSpeeds() == null) {
            throw new InvalidJsonFormatException("Speeds is null");
        }

        for (int i = 0; i < bakerConfig.getNumBakers(); i++) {
            if (bakerConfig.getSpeeds()[i] <= 0) {
                throw new InvalidJsonFormatException("Speeds must be greater than 0");
            }
        }
    }

    private void delivererCheck() {
        if (delivererConfig == null) {
            throw new InvalidJsonFormatException("DelivererConfig is null");
        }

        if (delivererConfig.getNumDeliverers() <= 0) {
            throw new InvalidJsonFormatException("Number of deliverers must be greater than 0");
        }

        if (delivererConfig.getSpeeds() == null) {
            throw new InvalidJsonFormatException("Speeds is null");
        }

        for (int i = 0; i < delivererConfig.getNumDeliverers(); i++) {
            if (delivererConfig.getSpeeds()[i] <= 0) {
                throw new InvalidJsonFormatException("Speeds must be greater than 0");
            }
        }

        if (delivererConfig.getBagSize() <= 0) {
            throw new InvalidJsonFormatException("Bag size must be greater than 0");
        }
    }

    private void pizzeriaCheck() {
        if (pizzeriaConfig == null) {
            throw new InvalidJsonFormatException("PizzeriaConfig is null");
        }

        if (pizzeriaConfig.getOrdersQueueSize() <= 0) {
            throw new InvalidJsonFormatException("Orders queue size must be greater than 0");
        }

        if (pizzeriaConfig.getWarehouseSize() <= 0) {
            throw new InvalidJsonFormatException("Warehouse size must be greater than 0");
        }

        if (pizzeriaConfig.getWorkTime() <= 0) {
            throw new InvalidJsonFormatException("Work time must be greater than 0");
        }
    }

    private void menuCheck() {
        if (menuConfig == null) {
            throw new InvalidJsonFormatException("MenuConfig is null");
        }

        if (menuConfig.getMenu() == null) {
            throw new InvalidJsonFormatException("Menu is null");
        }

        if (menuConfig.getMenu().isEmpty()){
            throw new InvalidJsonFormatException("Menu is empty");
        }
    }

    private void initBakers() throws IOException {
        bakers = new Vector<>();
        for (int i = 0; i < bakerConfig.getNumBakers(); i++) {
            bakers.add(new Baker(workerID++, bakerConfig.getSpeeds()[i], warehouse, orderQueue));
        }
    }

    private void initDeliverers() {
        deliverers = new Vector<>();
        for (int i = 0; i < delivererConfig.getNumDeliverers(); i++) {
            deliverers.add(new Deliverer(workerID++, delivererConfig.getSpeeds()[i], delivererConfig.getBagSize(), warehouse));
        }
    }

    private void initPizzeria() throws IOException {
        clock = new Clock(pizzeriaConfig.getWorkTime(), this);
        orderQueue = new Queue<>(pizzeriaConfig.getOrdersQueueSize());
        warehouse = new Queue<>(pizzeriaConfig.getWarehouseSize());
    }

    private void initMenu() {
        menu = menuConfig.getMenu();
        numPizzas = menu.size();
    }

    public synchronized void addOrder() {
        if (finished) {
            LoggerConsole.write("Pizzeria closed.");
            logger.info("Pizzeria closed.");
            return;
        } else if (isWorking) {
            LoggerConsole.write("Pizzeria is working");
            logger.info("Pizzeria is working");
            return;
        }
        int number = (int)(Math.random() * numPizzas);
        LoggerConsole.write("Order " + orderID + " for " + menu.get(number) + " has been accepted");
        logger.info("Order {} for {} has been accepted", orderID, menu.get(number));
        orderQueue.add(new Order(orderID++, menu.get(number)));
    }

    public synchronized void startWorkDay() {
        if (isWorking || finished) {
            return;
        }

        isWorking = true;

        if (workDay == 1) {
            clock.startClock();
            for (Worker baker : bakers) {
                baker.start();
            }

            for (Worker deliverer : deliverers) {
                deliverer.start();
            }
            LoggerConsole.write("Started " + workDay + " work day");
            logger.info("Started {} work day", workDay);
            notify();
            return;
        }

        clock.startClock();
        for (Worker baker : bakers) {
            baker.notify();
        }

        for (Worker deliverer : deliverers) {
            deliverer.notify();
        }
        LoggerConsole.write("Started " + workDay + " work day");
        logger.info("Started {} work day", workDay);
    }

    public synchronized void endWorkDay() throws FileNotFoundException {
        isWorking = false;
        for (Worker baker : bakers) {
            baker.interrupt();
        }

        for (Worker deliverer : deliverers) {
            deliverer.interrupt();
        }
        LoggerConsole.write("Ended " + workDay + " work day");
        logger.info("Ended {} work day", (workDay++));
        this.notify();
    }

    public synchronized void closePizzeria() throws InterruptedException {
        if (finished) {
            return;
        }

        if (isWorking) {
            wait();
        }

        isWorking = false;
        finished = true;

        clock.endClock();

        for (Worker baker : bakers) {
            baker.endWork();
            baker.join();
        }

        for (Worker deliverer : deliverers) {
            deliverer.endWork();
            deliverer.join();
        }

        LoggerConsole.write("Pizzeria finished its work on " + workDay + " day");
        logger.info("Pizzeria finished {} work day", workDay);

        for (Worker baker : bakers) {
            baker.interrupt();
        }

        for (Worker deliverer : deliverers) {
            deliverer.interrupt();
        }
    }
}
