package org.graph;

import java.util.Vector;

/**
 * Represents a vertex in a graph.
 */
public class Vertex {
    private int num;
    private int numEdges = 0;
    private Vector<Edge> edges = new Vector<Edge>();

    /**
     * Constructor for initializing a vertex.
     *
     * @param num the vertex number
     */
    public Vertex(int num){
        this.num = num;
    }

    /**
     * Gets the vertex number.
     *
     * @return the vertex number
     */
    public int getNum(){
        return num;
    }

    /**
     * Sets the vertex number.
     *
     * @param num the vertex number to set
     */
    public void setNum(int num){
        this.num = num;
    }

    /**
     * Checks if an edge exists between two vertices.
     *
     * @param parent the parent vertex number
     * @param child the child vertex number
     * @return true if the edge exists, false otherwise
     */
    public boolean hasEdge(int parent, int child) {
        for (Edge edge : edges) {
            if (edge.getParent() == parent && edge.getChild() == child) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds an edge between two vertices.
     *
     * @param parent the parent vertex number
     * @param child the child vertex number
     */
    public void addEdge(int parent, int child){
        numEdges++;
        edges.add(new Edge(parent, child));
    }

    /**
     * Removes an edge by the parent vertex number.
     *
     * @param parent the parent vertex number
     */
    public void delEdge(int parent){
        for (int i = 0; i < numEdges; i++){
            if (edges.get(i).getParent() == parent){
                edges.remove(i);
                numEdges--;
                return;
            }
        }
    }

    /**
     * Removes an edge by the parent and child vertex numbers.
     *
     * @param parent the parent vertex number
     * @param child the child vertex number
     */
    public void delEdge(int parent, int child){
        for (int i = 0; i < numEdges; i++){
            if (edges.get(i).getParent() == parent && edges.get(i).getChild() == child){
                edges.remove(i);
                numEdges--;
                return;
            }
        }
    }

    /**
     * Prints the neighbors of the vertex.
     */
    public void printNeighbours() {
        System.out.print("Neighbours: ");
        for (Edge edge : edges) {
            System.out.print(edge.getChild() + " ");
        }
        System.out.println();
    }
}
