package org.graf;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Choose graph representation:");
        System.out.println("1. Adjacency List");
        System.out.println("2. Adjacency Matrix");
        System.out.println("3. Incidence Matrix");
        int choice = scanner.nextInt();
        scanner.nextLine();  // consume the newline

        switch (choice) {
            case 1:
                testAdjList(scanner);
                break;
            case 2:
                testMatrix(scanner);
                break;
            case 3:
                testIncidenceMatrix(scanner);
                break;
            default:
                System.out.println("Invalid choice.");
                break;
        }
    }

    private static void testAdjList(Scanner scanner) {
        AdjList adjList = new AdjList();

        System.out.println("Adding edges:");
        adjList.addEdge("A", "B", 1);
        adjList.addEdge("A", "C", 2);
        adjList.addEdge("B", "C", 3);
        adjList.printNeighbours("A");

        System.out.println("Removing edge A-C:");
        adjList.delEdge("A", "C");
        adjList.printNeighbours("A");

        System.out.println("Topological sorting:");
        adjList.topSort();

        System.out.println("Reading graph from file:");
        adjList.readGraphFromFile("graph.txt");
        adjList.printNeighbours("A");
    }

    private static void testMatrix(Scanner scanner) {
        Matrix matrix = new Matrix();

        System.out.println("Adding edges:");
        matrix.addEdge(1, 2, 1);
        matrix.addEdge(1, 3, 2);

        System.out.println("Removing edge 1-3:");
        matrix.delEdge(1, 3);

        System.out.println("Topological sorting:");
        matrix.topSort();

        System.out.println("Reading graph from file:");
        matrix.readGraphFromFile("graph.txt");
    }

    private static void testIncidenceMatrix(Scanner scanner) {
        IncidenceMatrix incidenceMatrix = new IncidenceMatrix();

        System.out.println("Adding edges:");
        incidenceMatrix.addEdge(0, 1);
        incidenceMatrix.addEdge(1, 2);
        incidenceMatrix.printMatrix();

        System.out.println("Removing edge 0-1:");
        incidenceMatrix.delEdge(0, 1);
        incidenceMatrix.printMatrix();

        System.out.println("Reading graph from file:");
        incidenceMatrix.readGraphFromFile("graph.txt");
        incidenceMatrix.printMatrix();
    }
}
