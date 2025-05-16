package org.main;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Server extends Thread {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private ArrayList<Integer> numbers;
    private final int port;
    private Logger logger;

    Server(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        String input = null;
        while (true) {
            System.out.print("Welcome to the server. Type file name. File must have .bin excitation. Type 'exit' to quit.");
            input = scanner.nextLine();
            if (input.equals("exit")) {
                System.exit(0);
            }

            File file = new File(Objects.requireNonNull(getClass().getResource((input))).getFile());
            if (!file.exists()) {
                System.out.println(input + ": file does not exist.");
                continue;
            }

            try {
                DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
