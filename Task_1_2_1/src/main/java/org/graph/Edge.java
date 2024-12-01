package org.graph;

/**
 * Represents an edge in the graph.
 */
public class Edge {
    private int parent;
    private int child;

    /**
     * Constructor for initializing an edge.
     *
     * @param parent the parent vertex number
     * @param child the child vertex number
     */
    public Edge(int parent, int child){
        this.parent = parent;
        this.child = child;
    }

    /**
     * Gets the parent vertex number.
     *
     * @return the parent vertex number
     */
    public int getParent(){
        return parent;
    }

    /**
     * Gets the child vertex number.
     *
     * @return the child vertex number
     */
    public int getChild(){
        return child;
    }

    /**
     * Sets the parent vertex number.
     *
     * @param parent the parent vertex number
     */
    public void setParent(int parent){
        this.parent = parent;
    }

    /**
     * Sets the child vertex number.
     *
     * @param child the child vertex number
     */
    public void setChild(int child){
        this.child = child;
    }
}
