package org.pizzeria;

public class Order {
    private int orderID;
    private String orderName;

    public Order(int orderID, String orderName) {
        this.orderID = orderID;
        this.orderName = orderName;
    }

    public int getOrderID() {
        return orderID;
    }
}