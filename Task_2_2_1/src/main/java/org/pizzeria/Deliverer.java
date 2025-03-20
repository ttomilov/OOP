package org.pizzeria;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Vector;

public class Deliverer extends Worker {
    private final Queue<Order> warehouse;
    private Vector<Order> orders;
    private final int bagSize;
    private int curOrdersNum = 0;
    private static final Logger logger = LogManager.getLogger(Deliverer.class);

    Deliverer(int workerID, int speed, int bagSize, Queue<Order> warehouse) {
        setWorkerId(workerID);
        setSpeed(speed);
        this.bagSize = bagSize;
        this.warehouse = warehouse;
    }

    @Override
    void print(String msg) {
        LoggerConsole.write(msg);
        logger.info(msg);
    }

    @Override
    synchronized void endWork() {
        LoggerConsole.write("Deliverer " + getWorkerId() + " has finished work");
        logger.info("Deliverer {} has finished work", getWorkerId());
        setFinished(true);
        notify();
    }

    @Override
    void takeOrder() {
        if (getFinished()) {
            return;
        }
        Vector<Order> newOrders = new Vector<>();
        while (curOrdersNum < bagSize) {
            Order order = warehouse.poll();
            if (order == null) {
                print("Warehouse is empty");
                orders = null;
                return;
            }
            newOrders.add(order);
            curOrdersNum++;
        }
        orders = newOrders;
    }

    @Override
    void work() {
        if (getFinished()) {
            return;
        }
        if (orders == null) {
            return;
        }
        while (!orders.isEmpty()) {
            print("Deliverer " + getWorkerId() + " is delivering order " + orders.firstElement().toString());
            try {
                sleep(1000L * getSpeed());
            } catch (InterruptedException e) {
                interrupt();
            }
            print("Deliverer " + getWorkerId() + " delivered order " + orders.firstElement().toString());
            orders.remove(0);
        }
        orders = null;
    }
}
