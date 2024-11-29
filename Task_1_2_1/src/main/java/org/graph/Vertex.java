package org.graph;

import java.util.Vector;

public class Vertex {
    private int num;
    private int numEdges = 0;
    private Vector<Edge> edges = new Vector<Edge>();

    public Vertex(int num){
        this.num = num;
    }

    public int getNum(){
        return num;
    }

    public void setNum(int num){
        this.num = num;
    }

    public boolean hasEdge(int parent, int child) {
        for (Edge edge : edges) {
            if (edge.getParent() == parent && edge.getChild() == child) {
                return true;
            }
        }
        return false;
    }

    public void addEdge(int parent, int child){
        numEdges++;
        edges.add(new Edge(parent, child));
    }

    public void delEdge(int parent){
        for (int i = 0; i < numEdges; i++){
            if (edges.get(i).getParent() == parent){
                edges.remove(i);
                numEdges--;
                return;
            }
        }
    }

    public void delEdge(int parent, int child){
        for (int i = 0; i < numEdges; i++){
            if (edges.get(i).getParent() == parent && edges.get(i).getChild() == child){
                edges.remove(i);
                numEdges--;
                return;
            }
        }
    }

    public void printNeighbours() {
        System.out.print("Neighbours: ");
        for (Edge edge : edges) {
            System.out.print(edge.getChild() + " ");
        }
        System.out.println();
    }
}
