package org.main;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server extends Thread {
    private ServerSocket serverSocket;
    private final int port;
    private final List<ClientSession> clients = new ArrayList<>();
    private volatile boolean running = true;
    private final long clientWaitMillis = 5000;
    private final Object waitMonitor = new Object();

    public Server(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(port);
            Logger.log("Server started on port " + port);

            new Thread(this::acceptClients).start();

            Logger.log("Waiting " + clientWaitMillis + "ms for clients to connect...");
            synchronized (waitMonitor) {
                waitMonitor.wait(clientWaitMillis);
            }
            Logger.log("Finished waiting. " + clients.size() + " clients connected.");

            Scanner scanner = new Scanner(System.in);
            Logger.log("Enter .bin filename or 'exit':");

            while (running) {
                String input = scanner.nextLine();
                if (input.equals("exit")) {
                    synchronized (waitMonitor) {
                        waitMonitor.notify();
                    }
                    broadcastShutdown();
                    running = false;
                    try {
                        serverSocket.close();
                    } catch (IOException ignored) {}
                    break;
                }

                List<Integer> numbers = loadNumbersFromFile(input);
                if (numbers == null) {
                    continue;
                }

                if (clients.isEmpty()) {
                    Logger.log("No clients connected. Try again later.");
                    continue;
                }

                int clientCount = clients.size();
                ArrayList<ArrayList<Integer>> parts = splitList(numbers, clientCount);
                Queue<List<Integer>> taskQueue = new LinkedList<>(parts);
                List<Boolean> results = Collections.synchronizedList(new ArrayList<>());

                List<Thread> threads = new ArrayList<>();

                while (!taskQueue.isEmpty()) {
                    List<Integer> subtask = taskQueue.poll();

                    ClientSession client;
                    synchronized (clients) {
                        if (clients.isEmpty()) {
                            break;
                        }
                        client = clients.remove(0);
                    }

                    final ClientSession selectedClient = client;
                    final List<Integer> currentTask = subtask;

                    Thread task = new Thread(() -> {
                        try {
                            selectedClient.sendTask(currentTask);
                            boolean result = selectedClient.receiveResult();
                            results.add(result);

                            synchronized (clients) {
                                clients.add(selectedClient);
                            }

                        } catch (IOException e) {
                            Logger.log("Client " + selectedClient.id + " failed: " + e.getMessage());

                            synchronized (taskQueue) {
                                taskQueue.add(currentTask);
                            }
                        }
                    });

                    task.start();
                    threads.add(task);
                }

                for (Thread task : threads) {
                    task.join();
                }

                boolean anyNotPrime = results.contains(true);
                Logger.log("Final result: " + (anyNotPrime ? "Some numbers are NOT prime" : "All numbers are prime"));
            }

            Logger.log("Server terminated gracefully.");

        } catch (IOException | InterruptedException e) {
            if (running) {
                Logger.log("Server error: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void acceptClients() {
        int id = 0;
        while (running) {
            try {
                Socket socket = serverSocket.accept();
                Logger.log("Client " + id + " connected.");
                clients.add(new ClientSession(socket, id++));
            } catch (IOException e) {
                if (running) {
                    Logger.log("Error accepting client: " + e.getMessage());
                }
                break;
            }
        }
    }

    private void broadcastShutdown() {
        for (ClientSession client : clients) {
            try {
                client.sendShutdownSignal();
            } catch (IOException ignored) {}
        }
    }

    private List<Integer> loadNumbersFromFile(String filename) {
        File file = new File(filename);

        if (!file.exists()) {
            Logger.log("File not found: " + filename);
            return null;
        }

        try (DataInputStream dataIn = new DataInputStream(new BufferedInputStream(new FileInputStream(file)))) {
            List<Integer> numbers = new ArrayList<>();
            int n = dataIn.readInt();
            for (int i = 0; i < n; i++) {
                numbers.add(dataIn.readInt());
            }
            return numbers;

        } catch (IOException e) {
            Logger.log("Error reading file: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private ArrayList<ArrayList<Integer>> splitList(List<Integer> list, int parts) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        int chunkSize = (int) Math.ceil((double) list.size() / parts);
        for (int i = 0; i < list.size(); i += chunkSize) {
            result.add(new ArrayList<>(list.subList(i, Math.min(list.size(), i + chunkSize))));
        }
        return result;
    }

    private static class ClientSession {
        private final Socket socket;
        private final int id;
        private final DataOutputStream out;
        private final DataInputStream in;

        public ClientSession(Socket socket, int id) throws IOException {
            this.socket = socket;
            this.id = id;
            this.out = new DataOutputStream(socket.getOutputStream());
            this.in = new DataInputStream(socket.getInputStream());
        }

        public void sendTask(List<Integer> numbers) throws IOException {
            out.writeInt(numbers.size());
            for (int num : numbers) {
                out.writeInt(num);
            }
            out.flush();
        }

        public boolean receiveResult() throws IOException {
            int response = in.readInt();
            Logger.log("Client " + id + " returned: " + response);
            return response == 1;
        }

        public void sendShutdownSignal() throws IOException {
            out.writeInt(-1);
            out.flush();
            socket.close();
        }
    }
}
