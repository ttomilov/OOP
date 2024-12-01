package org.graph;

import org.main.Graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;

/**
 * Represents a graph using an incidence matrix.
 * Implements the {@link Graph} interface for managing graph operations like adding/removing vertices and edges.
 */
public class IncidenceMatrix implements Graph {
    private int numVert;
    private int numEdges;
    private Vector<Vector<Integer>> incidenceMatrix = new Vector<Vector<Integer>>();
    private boolean isOriented;

    /**
     * Constructor for initializing the incidence matrix graph.
     *
     * @param numVert the number of vertices in the graph
     * @param isOriented whether the graph is oriented or not
     */
    public IncidenceMatrix(int numVert, boolean isOriented){
        this.numVert = numVert;
        this.numEdges = 0;
        this.isOriented = isOriented;
    }

    /**
     * Retrieves the edge between two vertices.
     *
     * @param parent the parent vertex number
     * @param child the child vertex number
     * @return 1 if the edge exists, 0 otherwise
     */
    public int getEdge(int parent, int child){
        parent--;
        child--;
        if (parent >= numVert || parent < 0 || child >= numVert || child < 0){
            System.out.println("ERROR: Vertex doesn't exist!");
            return 0;
        }

        for (int i = 0; i < numEdges; i++) {
            Vector<Integer> edge = incidenceMatrix.get(i);
            if (edge.get(parent) == 1 &&
                    ((isOriented && edge.get(child) == -1) || (!isOriented && edge.get(child) == 1))) {
                return 1;
            }
        }
        return 0;
    }

    /**
     * Returns the number of vertices in the graph.
     *
     * @return the number of vertices
     */
    public int getNumVert(){
        return numVert;
    }

    /**
     * Adds a new vertex to the graph.
     */
    @Override
    public void addVertex(){
        numVert++;
        for (Vector<Integer> row : incidenceMatrix){
            row.add(0);
        }
    }

    /**
     * Deletes a vertex from the graph.
     * The vertex is removed and all edges connected to it are removed as well.
     *
     * @param vertNum the vertex number
     */
    @Override
    public void delVertex(int vertNum){
        vertNum--;
        if (vertNum >= numVert || vertNum < 0){
            System.out.printf("ERROR: Vertex with number %d doesn't exist!\n", vertNum + 1);
            return;
        }
        for (int i = 0; i < numEdges; i++){
            incidenceMatrix.get(i).remove(vertNum);
        }
        numVert--;
    }

    /**
     * Adds an edge between two vertices.
     *
     * @param parent the parent vertex number
     * @param child the child vertex number
     */
    @Override
    public void addEdge(int parent, int child){
        parent--;
        child--;
        if (parent >= numVert || parent < 0 || child >= numVert || child < 0){
            System.out.println("ERROR: Vertex doesn't exist!");
            return;
        }
        Vector<Integer> newEdge = new Vector<Integer>();
        for (int i = 0; i < numVert; i++){
            newEdge.add(0);
        }
        newEdge.set(parent, 1);
        if (isOriented){
            newEdge.set(child, -1);
        } else{
            newEdge.set(child, 1);
        }
        incidenceMatrix.add(newEdge);
        numEdges++;
    }

    /**
     * Deletes an edge between two vertices.
     *
     * @param parent the parent vertex number
     * @param child the child vertex number
     */
    @Override
    public void delEdge(int parent, int child){
        parent--;
        child--;
        if (parent >= numVert || parent < 0 || child >= numVert || child < 0){
            System.out.println("ERROR: Vertex doesn't exist!");
            return;
        }

        for (int i = 0; i < numEdges; i++){
            Vector<Integer> edge = incidenceMatrix.get(i);
            if (edge.get(parent) == 1 && ((isOriented && edge.get(child) == -1) || (!isOriented && edge.get(child) == 1))){
                incidenceMatrix.remove(i);
                numEdges--;
                return;
            }
        }
        System.out.println("ERROR: Edge doesn't exist!");
    }

    /**
     * Prints the neighbors of a given vertex.
     *
     * @param vertNum the vertex number
     */
    @Override
    public void printNeighbours(int vertNum){
        vertNum--;
        if (vertNum >= numVert || vertNum < 0){
            System.out.printf("ERROR: Vertex with number %d doesn't exist!\n", vertNum + 1);
            return;
        }
        System.out.printf("Neighbours of vertex %d:\n", vertNum + 1);
        for (int i = 0; i < numEdges; i++){
            Vector<Integer> edge = incidenceMatrix.get(i);
            if (edge.get(vertNum) == 1){
                for (int j = 0; j < numVert; j++){
                    if (edge.get(j) == (isOriented ? -1 : 1) && j != vertNum){
                        System.out.printf("    %d\n", j + 1);
                    }
                }
            }
        }
    }

    /**
     * Loads a graph from a file and updates the incidence matrix accordingly.
     *
     * @param filename the name of the file containing the graph data
     */
    @Override
    public void scanFromFile(String filename) {
        try (Scanner scanner = new Scanner(new File(filename))){
            int vertices = scanner.nextInt();
            boolean isOrientedFile = scanner.nextBoolean();
            IncidenceMatrix fileGraph = new IncidenceMatrix(vertices, isOrientedFile);
            while (scanner.hasNextInt()){
                int parent = scanner.nextInt();
                int child = scanner.nextInt();
                fileGraph.addEdge(parent, child);
            }
            this.numVert = fileGraph.numVert;
            this.numEdges = fileGraph.numEdges;
            this.isOriented = fileGraph.isOriented;
            this.incidenceMatrix = fileGraph.incidenceMatrix;
        } catch (FileNotFoundException e){
            System.out.println("ERROR: File not found!");
        }
    }
}
