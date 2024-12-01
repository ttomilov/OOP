package org.graph;

import java.io.*;
import java.util.Scanner;
import java.util.Vector;
import org.main.Graph;

public class AdjList implements Graph {
    private int numVert;
    private Vector<Vertex> graph = new Vector<Vertex>();
    private boolean isOriented;

    public AdjList(int numVert, boolean isOriented){
        this.numVert = numVert;
        this.isOriented = isOriented;
        for (int i = 0; i < numVert; i++){
            graph.add(new Vertex(i + 1));
        }
    }

    public int getNumVert(){
        return numVert;
    }

    public int getEdge(int parent, int child) {
        parent--;
        child--;
        if (parent >= numVert || parent < 0 || child >= numVert || child < 0){
            System.out.println("ERROR: Vertex doesn't exist!");
            return 0;
        }
        return graph.get(parent).hasEdge(parent, child) ? 1 : 0;
    }

    @Override
    public void addVertex() {
        numVert++;
        graph.add(new Vertex(numVert));
    }

    @Override
    public void delVertex(int vertNum) {
        vertNum--;
        if (vertNum >= numVert || vertNum < 0){
            System.out.printf("ERROR: Vertex with number %d doesn't exist!\n", vertNum + 1);
            return;
        }
        for (int i = 0; i < numVert; i++){
            graph.get(i).delEdge(vertNum);
        }
        graph.remove(vertNum);
        for (int i = vertNum; i < numVert - 1; i++){
            graph.get(i).setNum(graph.get(i).getNum() - 1);
        }
        numVert--;
        for (int i = 0; i < numVert; i++){
            delEdge(vertNum, i);
        }
    }

    @Override
    public void addEdge(int parent, int child) {
        parent--;
        child--;
        if (parent >= numVert || parent < 0 || child >= numVert || child < 0){
            System.out.println("ERROR: Vertex doesn't exist!");
            return;
        }
        graph.get(parent).addEdge(parent, child);
        if (!isOriented){
            graph.get(child).addEdge(child, parent);
        }
    }

    @Override
    public void delEdge(int parent, int child) {
        parent--;
        child--;
        if (parent >= numVert || parent < 0 || child >= numVert || child < 0){
            System.out.println("ERROR: Vertex doesn't exist!");
            return;
        }
        graph.get(parent).delEdge(parent, child);
        if (!isOriented){
            graph.get(child).delEdge(child, parent);
        }
    }

    @Override
    public void printNeighbours(int vertNum) {
        vertNum--;
        if (vertNum >= numVert || vertNum < 0){
            System.out.println("ERROR: Vertex doesn't exist!");
            return;
        }
        graph.get(vertNum).printNeighbours();
    }

    @Override
    public void scanFromFile(String filename) {
        try (Scanner scanner = new Scanner(new File(filename))){
            int vertices = scanner.nextInt();
            boolean isOrientedFile = scanner.nextBoolean();
            AdjList fileGraph = new AdjList(vertices, isOrientedFile);
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
