package org.main;

public interface Graph{ 
    public void addVertex();
    public void delVertex(int vertNum);
    public void addEdge(int parent, int child);
    public void delEdge(int parent, int child);
    public void printNeighbours(int vertNum);
    public void scanFromFile(String filename) ;
}