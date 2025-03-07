package org.pizzeria;

import java.util.Vector;

public class Deliverer extends Thread {
    private Vector<Order> bag = new Vector<>();
    private int bagSize;

    Deliverer(int bagSize){
        this.bagSize = bagSize;
    }

    void setBag(){
        while (bag.size() < bagSize){
            Order order = Warehouse.removeOrder();
            if (order != null){
                bag.add(order);
            }
        }
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            setBag();
            while (!bag.isEmpty()){
                Order order = bag.removeFirst();
                System.out.printf("Order %d is delivering now.\n", order.getOrderID());
                try{
                    sleep(4000);
                } catch (InterruptedException ignored) {
                }
                System.out.printf("Order %d was delivered.\n", order.getOrderID());
            }
        }
    }
}