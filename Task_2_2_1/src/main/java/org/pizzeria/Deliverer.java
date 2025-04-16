package org.pizzeria;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Vector;

public class Deliverer extends Worker {
    private Queue<Order> warehouse;
    private Vector<Order> orders;
    private int bagSize;
    private static final Logger logger = LogManager.getLogger(Deliverer.class);

    public Deliverer(int workerId, int speed, int bagSize, Queue<Order> warehouse) {
        setWorkerId(workerId);
        setSpeed(speed);
        this.bagSize = bagSize;
        this.warehouse = warehouse;
        this.orders = new Vector<>();
    }

    @Override
    void work() {
        if (getIsFinished()){
            return;
        }

        while (orders.size() < bagSize) {
            if (getIsFinished()){
                return;
            }
            Order order = warehouse.poll();
            if (order != null) {
                log("Deliverer " + getWorkerId() + " took order " + order.getOrderID());
                orders.add(order);
            }
        }

        while (!orders.isEmpty()) {
            try {
                sleep(getSpeed());
                log("Deliverer " + getWorkerId() + " delivered order " + orders.get(0).getOrderID());
                orders.remove(0);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    synchronized void finishWork() {
        if (orders.isEmpty()) {
            log("Deliverer " + getWorkerId() + " finished work");
        } else {
            while (!orders.isEmpty()) {
                try {
                    sleep(getSpeed());
                    log("Deliverer " + getWorkerId() + " delivered order " + orders.get(0).getOrderID());
                    orders.remove(0);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            log("Deliverer " + getWorkerId() + " finished work");
        }
        setIsFinished(true);
    }

    @Override
    void log(String msg) {
        LoggerConsole.write(msg);
        logger.info(msg);
    }
}
