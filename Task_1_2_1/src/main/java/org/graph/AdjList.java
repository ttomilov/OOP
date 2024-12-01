package org.graph;

import java.io.*;
import java.util.Scanner;
import java.util.Vector;
import org.main.Graph;

/**
 * Class representing a graph using an adjacency list.
 * Implements the {@link Graph} interface for managing graph operations such as adding/removing vertices and edges.
 */
public class AdjList implements Graph {
    private int numVert;
    private Vector<Vertex> graph = new Vector<>();
    private boolean isOriented;

    /**
     * Constructs an adjacency list graph.
     *
     * @param numVert The number of vertices in the graph.
     * @param isOriented True if the graph is oriented, false if it is undirected.
     */
    public AdjList(int numVert, boolean isOriented) {
        this.numVert = numVert;
        this.isOriented = isOriented;
        for (int i = 0; i < numVert; i++) {
            graph.add(new Vertex(i + 1));
        }
    }

    /**
     * Returns the number of vertices in the graph.
     *
     * @return The number of vertices.
     */
    public int getNumVert() {
        return numVert;
    }

    /**
     * Checks if an edge exists between two vertices.
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
        return graph.get(parent).hasEdge(parent, child) ? 1 : 0;
    }

    /**
     * Adds a new vertex to the graph.
     */
    @Override
    public void addVertex() {
        numVert++;
        graph.add(new Vertex(numVert));
    }

    /**
     * Deletes a vertex and removes all edges connected to it.
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
        for (int i = 0; i < numVert; i++) {
            graph.get(i).delEdge(vertNum);
        }
        graph.remove(vertNum);
        for (int i = vertNum; i < numVert - 1; i++) {
            graph.get(i).setNum(graph.get(i).getNum() - 1);
        }
        numVert--;
        for (int i = 0; i < numVert; i++) {
            delEdge(vertNum, i);
        }
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
        graph.get(parent).addEdge(parent, child);
        if (!isOriented) {
            graph.get(child).addEdge(child, parent);
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
        graph.get(parent).delEdge(parent, child);
        if (!isOriented) {
            graph.get(child).delEdge(child, parent);
        }
    }

    /**
     * Prints the neighbors of the given vertex.
     *
     * @param vertNum The vertex number (1-based index).
     */
    @Override
    public void printNeighbours(int vertNum) {
        vertNum--;
        if (vertNum >= numVert || vertNum < 0) {
            System.out.println("ERROR: Vertex doesn't exist!");
            return;
        }
        graph.get(vertNum).printNeighbours();
    }

    /**
     * Loads a graph from a file and updates the adjacency list.
     *
     * @param filename The name of the file containing the graph data.
     */
    @Override
    public void scanFromFile(String filename) {
        try (Scanner scanner = new Scanner(new File(filename))) {
            int vertices = scanner.nextInt();
            boolean isOrientedFile = scanner.nextBoolean();
            AdjList fileGraph = new AdjList(vertices, isOrientedFile);
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
