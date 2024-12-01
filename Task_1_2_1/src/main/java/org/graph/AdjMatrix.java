package org.graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;
import org.main.Graph;

/**
 * Class representing a graph using an adjacency matrix.
 * Implements the {@link Graph} interface for graph operations like adding/removing vertices and edges.
 */
public class AdjMatrix implements Graph {
    private int numVert;
    private Vector<Vector<Integer>> graph = new Vector<>();
    private boolean isOriented;

    /**
     * Constructs an adjacency matrix graph.
     *
     * @param numVert The number of vertices in the graph.
     * @param isOriented True if the graph is oriented, false otherwise.
     */
    public AdjMatrix(int numVert, boolean isOriented) {
        this.numVert = numVert;
        this.isOriented = isOriented;

        for (int i = 0; i < numVert; i++) {
            Vector<Integer> row = new Vector<>();
            for (int j = 0; j < numVert; j++) {
                row.add(0);
            }
            graph.add(row);
        }
    }

    /**
     * Returns the number of vertices in the graph.
     *
     * @return The number of vertices in the graph.
     */
    public int getNumVert() {
        return numVert;
    }

    /**
     * Checks whether an edge exists between two vertices.
     *
     * @param parent The parent vertex.
     * @param child The child vertex.
     * @return 1 if an edge exists, 0 otherwise.
     */
    public int getEdge(int parent, int child) {
        parent--;
        child--;
        if (parent >= numVert || parent < 0 || child >= numVert || child < 0) {
            System.out.println("ERROR: Vertex doesn't exist!");
            return 0;
        }
        return graph.get(parent).get(child);
    }

    /**
     * Adds a new vertex to the graph.
     */
    @Override
    public void addVertex() {
        numVert++;
        for (Vector<Integer> row : graph) {
            row.add(0);
        }
        Vector<Integer> newRow = new Vector<>();
        for (int j = 0; j < numVert; j++) {
            newRow.add(0);
        }
        graph.add(newRow);
    }

    /**
     * Deletes a vertex from the graph.
     * Removes the vertex and its associated edges.
     *
     * @param vertNum The vertex number to delete.
     */
    @Override
    public void delVertex(int vertNum) {
        vertNum--;
        if (vertNum >= numVert || vertNum < 0) {
            System.out.printf("ERROR: Vertex with number %d doesn't exist!\n", vertNum + 1);
            return;
        }
        graph.remove(vertNum);
        for (Vector<Integer> row : graph) {
            row.remove(vertNum);
        }
        numVert--;
    }

    /**
     * Adds an edge between two vertices.
     *
     * @param parent The parent vertex.
     * @param child The child vertex.
     */
    @Override
    public void addEdge(int parent, int child) {
        parent--;
        child--;
        if (parent >= numVert || parent < 0 || child >= numVert || child < 0) {
            System.out.println("ERROR: Vertex doesn't exist!");
            return;
        }
        graph.get(parent).set(child, 1);
        if (!isOriented) {
            graph.get(child).set(parent, 1);
        }
    }

    /**
     * Deletes an edge between two vertices.
     *
     * @param parent The parent vertex.
     * @param child The child vertex.
     */
    @Override
    public void delEdge(int parent, int child) {
        parent--;
        child--;
        if (parent >= numVert || parent < 0 || child >= numVert || child < 0) {
            System.out.println("ERROR: Vertex doesn't exist!");
            return;
        }
        graph.get(parent).set(child, 0);
        if (!isOriented) {
            graph.get(child).set(parent, 0);
        }
    }

    /**
     * Prints the neighbors of a given vertex.
     *
     * @param vertNum The vertex number whose neighbors will be printed.
     */
    @Override
    public void printNeighbours(int vertNum) {
        vertNum--;
        if (vertNum >= numVert || vertNum < 0) {
            System.out.println("ERROR: Vertex doesn't exist!");
            return;
        }
        System.out.println("Neighbours of vertex " + (vertNum + 1) + ":");
        for (int i = 0; i < numVert; i++) {
            if (graph.get(vertNum).get(i) == 1) {
                System.out.println(i + 1);
            }
        }
    }

    /**
     * Reads graph data from a file and updates the graph accordingly.
     * The file should contain the number of vertices, a boolean indicating if the graph is oriented,
     * and a list of edges (pairs of vertex numbers).
     *
     * @param filename The name of the file containing the graph data.
     */
    @Override
    public void scanFromFile(String filename) {
        try (Scanner scanner = new Scanner(new File(filename))) {
            int vertices = scanner.nextInt();
            boolean isOrientedFile = scanner.nextBoolean();
            AdjMatrix fileGraph = new AdjMatrix(vertices, isOrientedFile);
            while (scanner.hasNextInt()) {
                int parent = scanner.nextInt();
                int child = scanner.nextInt();
                fileGraph.addEdge(parent, child);
            }
            this.numVert = fileGraph.numVert;
            this.isOriented = fileGraph.isOriented;
            this.graph = fileGraph.graph;
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: File not found!");
        }
    }
}
