package org.pizzeria;

public class Baker extends Thread {
    private Order order = null;
    private final int speed;

    Baker(int speed) {
        this.speed = speed;
    }

    Order getOrder() {
        return order;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            order = Distributer.getOrder();
            if (order != null) {
                System.out.printf("Baker is cooking order %d.\n", order.getOrderID());
                try {
                    sleep(speed * 1000);
                } catch (InterruptedException e) {
                    break;
                }
                synchronized (Distributer.getLock()) {
                    if (Warehouse.addOrder(order)) {
                        order = null;
                    }
                    Distributer.getLock().notifyAll();
                }
            }
        }
    }
}