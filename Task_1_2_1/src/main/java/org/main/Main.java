package org.main;

import org.graph.AdjList;
import org.graph.AdjMatrix;
import org.graph.IncidenceMatrix;

import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        AdjList graph = new AdjList(5, true);
        graph.addVertex();
        graph.addEdge(1 ,2);
        graph.addEdge(1, 3);
        graph.addEdge(4, 5);
        graph.delVertex(3);
        graph.printNeighbours(1);
    }
}