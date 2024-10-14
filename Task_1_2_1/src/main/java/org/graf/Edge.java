package org.graf;

public class Edge {
    public String perent;
    public String child;
    public int weight;

    public Edge(String perent, String child, int weight){
        this.perent = perent;
        this.child = child;
        this.weight = weight;
    }
}
