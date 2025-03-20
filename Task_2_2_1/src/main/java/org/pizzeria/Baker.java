package org.pizzeria;

import java.io.FileNotFoundException;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

class Baker extends Worker {
    private final Queue<Order> warehouse;
    private final Queue<Order> orderQueue;
    private Order order = null;
    private static final Logger logger = LogManager.getLogger(Baker.class);

    Baker(int workerID, int speed, Queue<Order> warehouse, Queue<Order> orderQueue) {
        setWorkerId(workerID);
        setSpeed(speed);
        this.warehouse = warehouse;
        this.orderQueue = orderQueue;
    }

    @Override
    synchronized void print(String msg) {
        LoggerConsole.write(msg);
        logger.info(msg);
    }

    @Override
     synchronized void endWork() {
        LoggerConsole.write("Baker " + getWorkerId() + " has finished work");
        logger.info("Baker {} has finished work", getWorkerId());
        setFinished(true);
        notify();
    }

    @Override
    void takeOrder() {
        if (getFinished()) {
            return;
        }
        order = orderQueue.poll();
        if (order == null) {
            print("Queue is empty");
            order = null;
            return;
        }
        print("Baker " + getWorkerId() + " took order " + order.getOrderID());
    }

    @Override
    void work() {
        if (getFinished()) {
            return;
        }
        if (order == null) {
            return;
        }
        try {
            sleep(1000L * getSpeed());
        } catch (InterruptedException e) {
            interrupt();
        }
        warehouse.add(order);
        print("Baker " + getWorkerId() + " putted order " + order.getOrderID() + " to warehouse");
    }
}
