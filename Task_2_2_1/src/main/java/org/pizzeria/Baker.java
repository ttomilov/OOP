package org.pizzeria;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Baker extends Worker {
    private Queue<Order> orderQueue;
    private Queue<Order> warehouse;
    private Order order = null;
    private static final Logger logger = LogManager.getLogger(Baker.class);

    Baker(int workerID, int speed, Queue<Order> warehouse, Queue<Order> orderQueue) {
        setWorkerId(workerID);
        setSpeed(speed);
        this.warehouse = warehouse;
        this.orderQueue = orderQueue;
    }

    @Override
    void work(){
        if (getIsFinished()) {
            return;
        }

        order = orderQueue.poll();
        if (order == null) {
            return;
        }

        log("Baker " + getWorkerId() + " took order " + order.getOrderID());
        try {
            sleep(getSpeed());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log("Baker " + getWorkerId() + " put order " + order.getOrderID() + " into warehouse");
        warehouse.add(order);
        order = null;
    }

    @Override
    synchronized void finishWork() {
        if (order == null) {
            log("Baker " + getWorkerId() + " finished work");
        } else {
            try {
                sleep(getSpeed());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log("Baker " + getWorkerId() + " put order " + order.getOrderID() + " into warehouse");
            warehouse.add(order);
        }
        log("Baker " + getWorkerId() + " finished work");
        setIsFinished(true);
        notify();
    }

    @Override
    void log(String msg) {
        LoggerConsole.write(msg);
        logger.info(msg);
    }
}
