package org.main;

/**
 * Interface defining methods for working with a graph.
 */
public interface Graph{
    /**
     * Adds a vertex to the graph.
     */
    void addVertex();

    /**
     * Deletes a vertex from the graph.
     *
     * @param vertNum The number of the vertex to be deleted.
     */
    void delVertex(int vertNum);

    /**
     * Adds an edge to the graph.
     *
     * @param parent The number of the parent vertex.
     * @param child The number of the child vertex.
     */
    void addEdge(int parent, int child);

    /**
     * Deletes an edge from the graph.
     *
     * @param parent The number of the parent vertex.
     * @param child The number of the child vertex.
     */
    void delEdge(int parent, int child);

    /**
     * Prints the neighbors of a given vertex.
     *
     * @param vertNum The number of the vertex whose neighbors are to be printed.
     */
    void printNeighbours(int vertNum);

    /**
     * Loads a graph from a file.
     *
     * @param filename The name of the file containing the graph.
     */
    void scanFromFile(String filename);
}
