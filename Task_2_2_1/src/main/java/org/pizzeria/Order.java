package org.pizzeria;

public class Order {
    private int OrderID;
    private String OrderName;

    public Order(int OrderID, String OrderName) {
        this.OrderID = OrderID;
        this.OrderName = OrderName;
    }

    public int getOrderID() {
        return OrderID;
    }
}
