package org.main;

public class Main {
    public static void main(String[] args) {
        int port = 5555;

        Server server = new Server(port);
        server.start();

        int clientCount = Runtime.getRuntime().availableProcessors();
        for (int i = 0; i < clientCount; i++) {
            new Client("localhost", port, i).start();
        }
    }
}
