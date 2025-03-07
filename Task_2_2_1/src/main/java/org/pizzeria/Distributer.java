package org.pizzeria;

import java.io.IOException;
import java.util.Vector;

public class Distributer {
    private static Vector<Order> queue;
    private Vector<Baker> bakers;
    private Vector<Deliverer> deliverers = new Vector<>();
    private Warehouse warehouse;
    private int timeWork;
    private int queueSize;
    private static final Object lock = new Object();

    public Distributer() throws IOException{
        SetUpper setup = JsonParser.parser();
        bakers = new Vector<>();
        for (int i = 0; i < setup.getNumBakers(); i++) {
            Baker baker = new Baker(setup.getBakerSpeed()[i]);
            bakers.add(baker);
        }
        for (int i = 0; i < setup.getNumDeliverers(); i++) {
            Deliverer deliverer = new Deliverer(setup.getBagSize());
            deliverers.add(deliverer);
        }
        warehouse = new Warehouse(setup.getWarehouseSize());
        timeWork = setup.getTimeWork();
        queueSize = setup.getQueueSize();
        queue = new Vector<>(queueSize);
    }

    static Object getLock() {
        return lock;
    }

    public void addOrder(Order order) {
        synchronized (queue) {
            if (queue.size() < queueSize) {
                queue.add(order);
                System.out.printf("Order %d added to the queue.\n", order.getOrderID());
                queue.notifyAll();
            } else {
                System.out.println("Queue is full. Cannot add order.");
            }
        }
    }

    public static Order getOrder() {
        synchronized (queue) {
            while (queue.isEmpty()) {
                try {
                    queue.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return null;
                }
            }
            Order order = queue.removeFirst();
            System.out.printf("Order %d taken from the queue.\n", order.getOrderID());
            return order;
        }
    }

    public void work() {
        for (Baker baker : bakers) {
            baker.start();
        }
        for (Deliverer deliverer : deliverers) {
            deliverer.start();
        }

        while (timeWork > 0) {
            try {
                Thread.sleep(1000);
                timeWork -= 1000;
            } catch (InterruptedException ignore) {
            }
        }

        for (Baker baker : bakers) {
            baker.interrupt();
        }
        for (Deliverer deliverer : deliverers) {
            deliverer.interrupt();
        }
    }
}