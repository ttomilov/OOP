package org.main;

import java.io.IOException;
import java.util.Scanner;
import org.pizzeria.Distributer;
import org.pizzeria.Order;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Distributer distributer = new Distributer();
        Thread distributerThread = new Thread(distributer::work);
        distributerThread.start();

        Scanner scanner = new Scanner(System.in);
        int orderId = 1;

        while (true) {
           System.out.println("Enter order name (or 'exit' to stop):");
           String orderName = scanner.nextLine();
           if (orderName.equalsIgnoreCase("exit")) {
               break;
           }
           Order order = new Order(orderId++, orderName);
           distributer.addOrder(order);
        }
        distributerThread.join();
        scanner.close();
    }
}