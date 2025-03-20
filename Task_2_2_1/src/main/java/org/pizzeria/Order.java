package org.pizzeria;

public class Order {
    private int orderID;
    private String orderName;

    public Order(int OrderID, String OrderName) {
        this.orderID = OrderID;
        this.orderName = OrderName;
    }

    public int getOrderID() {
        return orderID;
    }
}
