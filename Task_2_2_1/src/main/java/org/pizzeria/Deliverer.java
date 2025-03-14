package org.pizzeria;

import java.util.Vector;

public class Deliverer extends Worker {
    private final Queue<Order> warehouse;
    private Vector<Order> orders;
    private final int bagSize;
    private int curOrdersNum = 0;

    Deliverer(int workerID, int speed, int bagSize, Queue<Order> warehouse) {
        setWorkerId(workerID);
        setSpeed(speed);
        this.bagSize = bagSize;
        this.warehouse = warehouse;
    }

    @Override
    synchronized void endWork() {
        Logger.write("Deliverer " + getWorkerId() + " has finished work");
        notify();
    }

    @Override
    void takeOrder() {
        Vector<Order> newOrders = new Vector<>();
        while (curOrdersNum < bagSize) {
            Order order = warehouse.poll();
            if (order == null) {
                Logger.write("Warehouse is empty");
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
        if (orders == null) {
            return;
        }
        while (!orders.isEmpty()) {
            Logger.write("Deliverer " + getWorkerId() + "is delivering order " + orders.firstElement().toString());
            try {
                sleep(1000L * getSpeed());
            } catch (InterruptedException e) {
                interrupt();
            }
            Logger.write("Deliverer " + getWorkerId() + "delivered order " + orders.firstElement().toString());
            orders.remove(0);
        }
        orders = null;
    }
}
