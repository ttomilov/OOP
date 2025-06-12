package org.main;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class Client extends Thread {
    private final String host;
    private final int port;
    private final int id;
    private final ArrayList<Integer> numbers = new ArrayList<>();

    Client(String host, int port, int id) {
        this.host = host;
        this.port = port;
        this.id = id;
    }

    private boolean notPrimeFinder() {
        for (int i : numbers) {
            if (i <= 1) return true;
            for (int j = 2; j * j <= i; j++) {
                if (i % j == 0) return true;
            }
        }
        return false;
    }

    @Override
    public void run() {
        try (Socket socket = new Socket(host, port);
             DataInputStream dataIn = new DataInputStream(socket.getInputStream());
             DataOutputStream dataOut = new DataOutputStream(socket.getOutputStream())) {

            Logger.log("Client " + id + " connected to server at " + host + ":" + port);

            while (true) {
                int n;
                try {
                    n = dataIn.readInt();
                } catch (EOFException | SocketException e) {
                    Logger.log("Client " + id + " connection closed by server.");
                    break;
                }

                if (n == -1) {
                    Logger.log("Client " + id + " received shutdown signal.");
                    break;
                }

                Logger.log("Client " + id + " received " + n + " numbers");
                numbers.clear();
                for (int i = 0; i < n; i++) {
                    numbers.add(dataIn.readInt());
                }

                Logger.log("Client " + id + " started work...");
                boolean result = notPrimeFinder();
                Logger.log("Client " + id + " finished work!");

                dataOut.writeInt(result ? 1 : 0);
                dataOut.flush();
            }

        } catch (IOException e) {
            Logger.log("Client " + id + " ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
