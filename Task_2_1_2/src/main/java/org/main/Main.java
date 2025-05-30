package org.main;

import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class Main {
    public static void main(String[] args) {
        int port = 5555;

        Server server = new Server(port);
        server.start();

        ArrayList<Client> clients = new ArrayList<>();

        int clientCount = Runtime.getRuntime().availableProcessors();
        for (int i = 0; i < clientCount; i++) {
            clients.add (new Client("localhost", port, i));
            clients.getLast().start();
        }
    }
}
