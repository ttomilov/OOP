package org.graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;

import org.main.Graph;

public class AdjMatrix implements Graph{
    private int numVert;
    private Vector<Vector<Integer>> graph = new Vector<>();
    private boolean isOriented;

    public AdjMatrix(int numVert, boolean isOriented){
        this.numVert = numVert;
        this.isOriented = isOriented;

        for (int i = 0; i < numVert; i++){
            Vector<Integer> row = new Vector<>();
            for (int j = 0; j < numVert; j++){
                row.add(0);
            }
            graph.add(row);
        }
    }

    public int getNumVert(){
        return numVert;
    }

    public int getEdge(int parent, int child){
        parent--;
        child--;
        if (parent >= numVert || parent < 0 || child >= numVert || child < 0){
            System.out.println("ERROR: Vertex doesn't exist!");
            return 0;
        }
        return graph.get(parent).get(child);
    }

    @Override
    public void addVertex(){
        numVert++;
        for (Vector<Integer> row : graph){
            row.add(0);
        }
        Vector<Integer> newRow = new Vector<>();
        for (int j = 0; j < numVert; j++){
            newRow.add(0);
        }
        graph.add(newRow);
    }

    @Override
    public void delVertex(int vertNum){
        vertNum--;
        if (vertNum >= numVert || vertNum < 0){
            System.out.printf("ERROR: Vertex with number %d doesn't exist!\n", vertNum + 1);
            return;
        }
        graph.remove(vertNum);
        for (Vector<Integer> row : graph){
            row.remove(vertNum);
        }
        numVert--;
    }

    @Override
    public void addEdge(int parent, int child){
        parent--;
        child--;
        if (parent >= numVert || parent < 0 || child >= numVert || child < 0){
            System.out.println("ERROR: Vertex doesn't exist!");
            return;
        }
        graph.get(parent).set(child, 1);
        if (!isOriented){
            graph.get(child).set(parent, 1);
        }
    }

    @Override
    public void delEdge(int parent, int child){
        parent--;
        child--;
        if (parent >= numVert || parent < 0 || child >= numVert || child < 0){
            System.out.println("ERROR: Vertex doesn't exist!");
            return;
        }
        graph.get(parent).set(child, 0);
        if (!isOriented){
            graph.get(child).set(parent, 0);
        }
    }

    @Override
    public void printNeighbours(int vertNum){
        vertNum--;
        if (vertNum >= numVert || vertNum < 0){
            System.out.printf("ERROR: Vertex with number %d doesn't exist!\n", vertNum + 1);
            return;
        }
        System.out.printf("Neighbours of vertex %d:\n", vertNum + 1);
        Vector<Integer> neighbours = graph.get(vertNum);
        for (int i = 0; i < numVert; i++){
            if (neighbours.get(i) == 1){
                System.out.printf("    %d\n", i + 1);
            }
        }
    }

    @Override
    public void scanFromFile(String filename) {
        try (Scanner scanner = new Scanner(new File(filename))){
            int vertices = scanner.nextInt();
            boolean isOrientedFile = scanner.nextBoolean();
            AdjMatrix fileGraph = new AdjMatrix(vertices, isOrientedFile);
            while (scanner.hasNextInt()){
                int parent = scanner.nextInt();
                int child = scanner.nextInt();
                fileGraph.addEdge(parent, child);
            }
            this.numVert = fileGraph.numVert;
            this.isOriented = fileGraph.isOriented;
            this.graph = fileGraph.graph;
        } catch (FileNotFoundException e){
            System.out.println("ERROR: File not found!");
        }
    }
}
