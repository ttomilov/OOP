package org.pizzeria;

import java.util.Vector;

class Warehouse {
    private static Vector<Order> orders = new Vector<Order>();
    private static int curNumOrders = 0;
    private static int warehouseSize;

    Warehouse(int warehouseSize) {
        Warehouse.warehouseSize = warehouseSize;
    }

    static boolean addOrder(Order order) {
        Object lock = Distributer.getLock();
        synchronized (lock) {
            if (orders.size() < warehouseSize) {
                orders.add(order);
                curNumOrders++;
                return true;
            } else{
                return false;
            }
        }
    }

    static Order removeOrder() {
        Object lock = Distributer.getLock();
        synchronized (lock) {
            if (orders.isEmpty()) {
                return null;
            } else {
                Order order = orders.get(curNumOrders - 1);
                orders.remove(curNumOrders - 1);
                curNumOrders--;
                return order;
            }
        }
    }
}
