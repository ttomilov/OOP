package org.main;

import org.graph.AdjList;
import org.graph.AdjMatrix;
import org.graph.IncidenceMatrix;

import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Graph graph;
        Scanner scan = new Scanner(System.in);
        System.out.print("Select the graph storage method:\n1. Adjacency list;\n2. Adjacency matrix;\n3. Incidence matrix.\n");
        String sym = scan.nextLine();
        if (Objects.equals(sym, "1")){
            System.out.print("How many vertex do you want: ");
            int numVert = scan.nextInt();
            System.out.print("Oriented or not(true/false): ");
            boolean isOriented = scan.nextBoolean();
            scan.close();
            graph = new AdjList(numVert, isOriented);
        } else if (Objects.equals(sym, "2")){
            System.out.print("How many vertex do you want: ");
            int numVert = scan.nextInt();
            System.out.print("Oriented or not(true/false): ");
            boolean isOriented = scan.nextBoolean();
            scan.close();
            graph = new AdjMatrix(numVert, isOriented);
        } else if (Objects.equals(sym, "3")) {
            System.out.print("How many vertex do you want: ");
            int numVert = scan.nextInt();
            System.out.print("Oriented or not(true/false): ");
            boolean isOriented = scan.nextBoolean();
            scan.close();
            graph = new IncidenceMatrix(numVert, isOriented);
        } else {
            System.out.println("ERROR: wrong input!\n");
            scan.close();
            return;
        }

        graph.addVertex();
        graph.addEdge(1 ,2);
        graph.addEdge(1, 3);
        graph.addEdge(4, 5);
        graph.delVertex(3);
        graph.printNeighbours(1);
    }
}