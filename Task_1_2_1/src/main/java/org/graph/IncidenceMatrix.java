package org.graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;

import org.main.Graph;

public class IncidenceMatrix implements Graph{
    private int numVert;
    private int numEdges;
    private Vector<Vector<Integer>> incidenceMatrix = new Vector<Vector<Integer>>();
    private boolean isOriented;

    public IncidenceMatrix(int numVert, boolean isOriented){
        this.numVert = numVert;
        this.numEdges = 0;
        this.isOriented = isOriented;
    }

    public int getEdge(int parent, int child){
        parent--;
        child--;
        if (parent >= numVert || parent < 0 || child >= numVert || child < 0){
            System.out.println("ERROR: Vertex doesn't exist!");
            return 0;
        }

        for (int i = 0; i < numEdges; i++) {
            Vector<Integer> edge = incidenceMatrix.get(i);
            if (edge.get(parent) == 1 &&
                    ((isOriented && edge.get(child) == -1) || (!isOriented && edge.get(child) == 1))) {
                return 1;
            }
        }
        return 0;
    }

    public int getNumVert(){
        return numVert;
    }

    @Override
    public void addVertex(){
        numVert++;
        for (Vector<Integer> row : incidenceMatrix){
            row.add(0);
        }
    }

    @Override
    public void delVertex(int vertNum){
        vertNum--;
        if (vertNum >= numVert || vertNum < 0){
            System.out.printf("ERROR: Vertex with number %d doesn't exist!\n", vertNum + 1);
            return;
        }
        for (int i = 0; i < numEdges; i++){
            incidenceMatrix.get(i).remove(vertNum);
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
        Vector<Integer> newEdge = new Vector<Integer>();
        for (int i = 0; i < numVert; i++){
            newEdge.add(0);
        }
        newEdge.set(parent, 1);
        if (isOriented){
            newEdge.set(child, -1);
        } else{
            newEdge.set(child, 1);
        }
        incidenceMatrix.add(newEdge);
        numEdges++;
    }

    @Override
    public void delEdge(int parent, int child){
        parent--;
        child--;
        if (parent >= numVert || parent < 0 || child >= numVert || child < 0){
            System.out.println("ERROR: Vertex doesn't exist!");
            return;
        }

        for (int i = 0; i < numEdges; i++){
            Vector<Integer> edge = incidenceMatrix.get(i);
            if (edge.get(parent) == 1 && ((isOriented && edge.get(child) == -1) || (!isOriented && edge.get(child) == 1))){
                incidenceMatrix.remove(i);
                numEdges--;
                return;
            }
        }
        System.out.println("ERROR: Edge doesn't exist!");
    }

    @Override
    public void printNeighbours(int vertNum){
        vertNum--;
        if (vertNum >= numVert || vertNum < 0){
            System.out.printf("ERROR: Vertex with number %d doesn't exist!\n", vertNum + 1);
            return;
        }
        System.out.printf("Neighbours of vertex %d:\n", vertNum + 1);
        for (int i = 0; i < numEdges; i++){
            Vector<Integer> edge = incidenceMatrix.get(i);
            if (edge.get(vertNum) == 1){
                for (int j = 0; j < numVert; j++){
                    if (edge.get(j) == (isOriented ? -1 : 1) && j != vertNum){
                        System.out.printf("    %d\n", j + 1);
                    }
                }
            }
        }
    }

    @Override
    public void scanFromFile(Graph graph){
        try (Scanner scanner = new Scanner(new File("graph.txt"))){
            int vertices = scanner.nextInt();
            boolean isOrientedFile = scanner.nextBoolean();
            IncidenceMatrix fileGraph = new IncidenceMatrix(vertices, isOrientedFile);

            while (scanner.hasNextInt()){
                int parent = scanner.nextInt();
                int child = scanner.nextInt();
                fileGraph.addEdge(parent, child);
            }

            this.numVert = fileGraph.numVert;
            this.numEdges = fileGraph.numEdges;
            this.isOriented = fileGraph.isOriented;
            this.incidenceMatrix = fileGraph.incidenceMatrix;

        } catch (FileNotFoundException e){
            System.out.println("ERROR: File not found!");
        }
    }
}
