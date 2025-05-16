package org.main;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Client extends Thread {
    private String host;
    private int port;
    private int id;
    private Logger logger;
    private ArrayList<Integer> numbers = new ArrayList<>();

    Client(String host, int port, int id) {
        this.host = host;
        this.port = port;
        this.id = id;
    }

    private boolean notPrimeFinder() {
        for (int i : numbers) {
            for (int j = 2; j * j < i; j++){
                if (i % j == 0) {
                    return true;
                }
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
                if(socket.isClosed()) {
                    Logger.log("Server closed connection.");
                }

                int n;
                n = dataIn.readInt();

                Logger.log("Client " + id + " is reading...");
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
            Logger.log("ERROR: " + e.getMessage());
        }
    }

}
