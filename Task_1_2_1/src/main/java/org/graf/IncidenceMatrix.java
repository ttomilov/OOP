package org.graf;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;

public class IncidenceMatrix {
    private Vector<Vector<Integer>> incidenceMatrix;
    private int numOfVert;
    private int numOfEdges;
    private boolean isDirected;

    public IncidenceMatrix() {
        this.incidenceMatrix = new Vector<>();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter number of vertices: ");
        this.numOfVert = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Is the graph directed? (true/false): ");
        this.isDirected = scanner.nextBoolean();
        scanner.nextLine();

        this.numOfEdges = 0;
        this.incidenceMatrix = new Vector<>(numOfVert);
        for (int i = 0; i < numOfVert; i++) {
            Vector<Integer> row = new Vector<>();
            for (int j = 0; j < numOfEdges; j++) {
                row.add(0);
            }
            incidenceMatrix.add(row);
        }
    }

    public void addVertex() {
        numOfVert++;
        for (Vector<Integer> row : incidenceMatrix) {
            row.add(0);
        }
    }

    public void addEdge(int parent, int child) {
        if (parent >= numOfVert || child >= numOfVert) {
            System.out.println("One of these vertices does not exist!");
            return;
        }

        numOfEdges++;
        for (Vector<Integer> row : incidenceMatrix) {
            row.add(0);
        }

        incidenceMatrix.get(parent).set(numOfEdges - 1, 1);
        incidenceMatrix.get(child).set(numOfEdges - 1, isDirected ? -1 : 1);
    }

    public void delEdge(int parent, int child) {
        for (int edgeIndex = 0; edgeIndex < numOfEdges; edgeIndex++) {
            if (incidenceMatrix.get(parent).get(edgeIndex) == 1 &&
                    incidenceMatrix.get(child).get(edgeIndex) == (isDirected ? -1 : 1)) {
                incidenceMatrix.get(parent).set(edgeIndex, 0);
                incidenceMatrix.get(child).set(edgeIndex, 0);
                return;
            }
        }
        System.out.println("Edge does not exist!");
    }

    public void printMatrix() {
        System.out.println("Incidence Matrix:");
        for (int i = 0; i < numOfVert; i++) {
            for (int j = 0; j < numOfEdges; j++) {
                System.out.print(incidenceMatrix.get(i).get(j) + " ");
            }
            System.out.println();
        }
    }

    public void readGraphFromFile(String fileName) {
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);

            if (scanner.hasNextInt()) {
                this.numOfVert = scanner.nextInt();
            }
            if (scanner.hasNextBoolean()) {
                this.isDirected = scanner.nextBoolean();
            }

            this.incidenceMatrix = new Vector<>(numOfVert);
            for (int i = 0; i < numOfVert; i++) {
                Vector<Integer> row = new Vector<>();
                for (int j = 0; j < numOfEdges; j++) {
                    row.add(0);
                }
                incidenceMatrix.add(row);
            }

            while (scanner.hasNextLine()) {
                String[] edgeData = scanner.nextLine().split(" ");
                if (edgeData.length == 2) {
                    int parent = Integer.parseInt(edgeData[0]) - 1;
                    int child = Integer.parseInt(edgeData[1]) - 1;
                    addEdge(parent, child);
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Ошибка: файл не найден.");
        }
    }
}
