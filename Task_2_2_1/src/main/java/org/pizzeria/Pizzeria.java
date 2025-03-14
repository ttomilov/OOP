package org.pizzeria;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.configs.BakerConfig;
import org.configs.DelivererConfig;
import org.configs.PizzeriaConfig;
import org.exeption.InvalidFormatJson;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;
import java.util.Scanner;
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
    private boolean isWorking;
    private boolean finished;
    private int workerID = 1;
    private int workDay = 1;
    private volatile int orderID = 1;
    private Vector<String> menu;
    private int numPizzas = 0;

    public Pizzeria(File bakerConfigJson, File delivererConfigJson, File pizzeriaConfigJson, OutputStream log) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        bakerConfig = objectMapper.readValue(bakerConfigJson, BakerConfig.class);
        delivererConfig = objectMapper.readValue(delivererConfigJson, DelivererConfig.class);
        pizzeriaConfig = objectMapper.readValue(pizzeriaConfigJson, PizzeriaConfig.class);

        exceptionCheck();

        initBakers();
        initDeliverers();
        initPizzeria();

        Logger.setOutputStream(log);
        finished = false;
    }

    private void exceptionCheck() {
        bakerCheck();
        delivererCheck();
        pizzeriaCheck();
    }

    private void bakerCheck() {
        if (bakerConfig == null) {
            throw new InvalidFormatJson("BakerConfig is null");
        }

        if (bakerConfig.getNumBakers() <= 0) {
            throw new InvalidFormatJson("Number of bakers must be greater than 0");
        }

        if (delivererConfig.getSpeeds() == null) {
            throw new InvalidFormatJson("Speeds is null");
        }

        for (int i = 0; i < bakerConfig.getNumBakers(); i++) {
            if (bakerConfig.getSpeeds()[i] <= 0) {
                throw new InvalidFormatJson("Speeds must be greater than 0");
            }
        }
    }

    private void delivererCheck() {
        if (delivererConfig == null) {
            throw new InvalidFormatJson("DelivererConfig is null");
        }

        if (delivererConfig.getNumDeliverers() <= 0) {
            throw new InvalidFormatJson("Number of deliverers must be greater than 0");
        }

        if (delivererConfig.getSpeeds() == null) {
            throw new InvalidFormatJson("Speeds is null");
        }

        for (int i = 0; i < delivererConfig.getNumDeliverers(); i++) {
            if (delivererConfig.getSpeeds()[i] <= 0) {
                throw new InvalidFormatJson("Speeds must be greater than 0");
            }
        }

        if (delivererConfig.getBagSize() <= 0) {
            throw new InvalidFormatJson("Bag size must be greater than 0");
        }
    }

    private void pizzeriaCheck() {
        if (pizzeriaConfig == null) {
            throw new InvalidFormatJson("PizzeriaConfig is null");
        }

        if (pizzeriaConfig.getOrdersQueueSize() <= 0) {
            throw new InvalidFormatJson("Orders queue size must be greater than 0");
        }

        if (pizzeriaConfig.getWarehouseSize() <= 0) {
            throw new InvalidFormatJson("Warehouse size must be greater than 0");
        }

        if (pizzeriaConfig.getWorkTime() <= 0) {
            throw new InvalidFormatJson("Work time must be greater than 0");
        }
    }

    public void initBakers() throws IOException {
        bakers = new Vector<>();
        for (int i = 0; i < bakerConfig.getNumBakers(); i++) {
            bakers.add(new Baker(workerID++, bakerConfig.getSpeeds()[i], warehouse, orderQueue));
            bakers.get(i).start();
        }
    }

    public void initDeliverers() throws IOException {
        deliverers = new Vector<>();
        for (int i = 0; i < delivererConfig.getNumDeliverers(); i++) {
            deliverers.add(new Deliverer(workerID++, delivererConfig.getSpeeds()[i], delivererConfig.getBagSize(), warehouse));
            deliverers.get(i).start();
        }
    }

    public void initPizzeria() throws IOException {
        clock = new Clock(pizzeriaConfig.getWorkTime(), this);
        orderQueue = new Queue<>(pizzeriaConfig.getOrdersQueueSize());
        warehouse = new Queue<>(pizzeriaConfig.getWarehouseSize());
    }

    public void initMenu(boolean fl) {
        menu = new Vector<>();
        Scanner scanner = new Scanner(System.in);
        if (fl) {
            System.out.println("Welcome to Pizzeria! Let's make your menu! Input name of pizzas (to stop adding type 'exit'):");
        }
        while (true) {
            String input = scanner.nextLine();
            if (input.equals("exit")) {
                break;
            }
            menu.add(input);
            numPizzas++;
        }
        if (numPizzas == 0) {
            System.out.println("You didn't make your menu. Let's try again!");
            scanner.close();
            initMenu(false);
        }
        scanner.close();
    }

    public synchronized boolean addOrder() {
        if (finished) {
            Logger.write("Pizzeria finished.");
            return false;
        } else if (!isWorking) {
            Logger.write("Pizzeria is not working");
            return false;
        }
        Random rnd = new Random();
        int number = rnd.nextInt(numPizzas) + 1;
        orderQueue.add(new Order(orderID++, menu.get(number)));
        return true;
    }

    public synchronized void startWorkDay() throws IOException {
        if (isWorking || finished) {
            return;
        }

        isWorking = true;

        if (workDay != 1) {
            initMenu(true);
            clock.startClock();
            Logger.write("Started " + (workDay++) + " work day");
            return;
        }

        clock.startClock();
        for (Worker baker : bakers) {
            baker.notify();
        }

        for (Worker deliverer : deliverers) {
            deliverer.notify();
        }
        Logger.write("Started " + workDay + " work day");
    }

    synchronized void endWorkDay() {
        isWorking = false;
        for (Worker baker : bakers) {
            baker.interrupt();
        }

        for (Worker deliverer : deliverers) {
            deliverer.interrupt();
        }
        Logger.write("Ended " + (workDay++) + " work day");
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

        Logger.write("Pizzeria finished its work on " + workDay + " day");
    }

    public boolean isWorking() {
        return isWorking;
    }

    public boolean isFinished() {
        return finished;
    }
}
