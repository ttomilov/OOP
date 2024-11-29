package org.graph;

public class Edge {
    private int parent;
    private int child;

    public Edge(int parent, int child){
        this.parent = parent;
        this.child = child;
    }

    public int getParent(){
        return parent;
    }

    public int getChild(){
        return child;
    }

    public void setParent(int parent){
        this.parent = parent;
    }

    public void setChild(int child){
        this.child = child;
    }
}
