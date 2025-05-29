package org.main;

import java.io.*;
import java.net.Socket;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class Test {

    private static void generateBinFile(String filename, List<Integer> numbers) throws IOException {
        try (DataOutputStream out = new DataOutputStream(new FileOutputStream("src/main/resources/" + filename))) {
            out.writeInt(numbers.size());
            for (int num : numbers) {
                out.writeInt(num);
            }
        }
    }

    private void runTest(String filename, List<Integer> numbers, String expectedOutput) throws Exception {
        generateBinFile(filename, numbers);

        ByteArrayInputStream testInput = new ByteArrayInputStream(
                (filename + System.lineSeparator() + "exit" + System.lineSeparator()).getBytes());
        InputStream originalIn = System.in;
        System.setIn(testInput);

        ByteArrayOutputStream testOut = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(testOut));

        Server server = new Server(5555);
        Thread serverThread = new Thread(server);
        serverThread.start();

        int clientCount = 4;
        for (int i = 0; i < clientCount; i++) {
            new Client("localhost", 5555, i).start();
        }

        serverThread.join();

        System.setOut(originalOut);
        System.setIn(originalIn);

        String output = testOut.toString();
        System.out.println(output);

        assertTrue(output.contains(expectedOutput));
    }

    @org.junit.jupiter.api.Test
    public void testAllPrime() throws Exception {
        runTest(
                "input_all_prime.bin",
                List.of(2, 3, 5, 7, 11, 13, 17, 19),
                "Final result: All numbers are prime"
        );
    }

    @org.junit.jupiter.api.Test
    public void testSomeNotPrime() throws Exception {
        runTest(
                "input_with_composite.bin",
                List.of(2, 3, 4, 5, 7, 9, 11),
                "Final result: Some numbers are NOT prime"
        );
    }

    @org.junit.jupiter.api.Test
    public void testClientDisconnects() throws Exception {
        String filename = "input_disconnect.bin";
        List<Integer> numbers = List.of(2, 3, 5, 7, 11, 13, 17, 19);
        generateBinFile(filename, numbers);

        ByteArrayInputStream testInput = new ByteArrayInputStream(
                (filename + System.lineSeparator() + "exit" + System.lineSeparator()).getBytes());
        InputStream originalIn = System.in;
        System.setIn(testInput);

        ByteArrayOutputStream testOut = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(testOut));

        Server server = new Server(5556);
        Thread serverThread = new Thread(server);
        serverThread.start();

        new Thread(() -> {
            try (Socket socket = new Socket("localhost", 5556)) {
                Logger.log("Fake client 0 connected then disconnected");
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        for (int i = 1; i < 4; i++) {
            new Client("localhost", 5556, i).start();
        }

        serverThread.join();

        System.setOut(originalOut);
        System.setIn(originalIn);

        String output = testOut.toString();
        System.out.println(output);

        assertTrue(output.contains("Final result: All numbers are prime"));
        assertTrue(output.contains("Client 0"));
    }

}
