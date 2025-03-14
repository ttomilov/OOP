package org.pizzeria;

class Baker extends Worker {
    private final Queue<Order> warehouse;
    private final Queue<Order> orderQueue;
    private Order order = null;

    Baker(int workerID, int speed, Queue<Order> warehouse, Queue<Order> orderQueue) {
        setWorkerId(workerID);
        setSpeed(speed);
        this.warehouse = warehouse;
        this.orderQueue = orderQueue;
    }

    Order getOrder(){
        return order;
    }

    @Override
     synchronized void endWork() {
        Logger.write("Baker " + getWorkerId() + " has finished work");
        notify();
    }

    @Override
    void takeOrder() {
        order = orderQueue.poll();
        if (order == null) {
            Logger.write("Queue is empty");
            order = null;
            return;
        }
        Logger.write("Baker " + getWorkerId() + "took order" + order.getOrderID());
    }

    @Override
    void work() {
        if (order == null) {
            return;
        }
        try {
            sleep(1000L * getSpeed());
        } catch (InterruptedException e) {
            interrupt();
        }
        warehouse.add(order);
        Logger.write("Baker " + getWorkerId() + "putted order " + order.getOrderID() + " to warehouse");
    }
}
