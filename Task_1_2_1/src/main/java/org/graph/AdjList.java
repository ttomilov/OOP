package org.graph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
    public void scanFromFile(Graph graph) {
        try (BufferedReader reader = new BufferedReader(new FileReader("graph.txt"))) {
            String line = reader.readLine();
            int numVert = Integer.parseInt(line);
            line = reader.readLine();
            boolean isOriented = Boolean.parseBoolean(line);
            AdjList adjList = new AdjList(numVert, isOriented);
            while ((line = reader.readLine()) != null) {
                String[] edgeData = line.split(" ");
                if (edgeData.length == 2) {
                    int parent = Integer.parseInt(edgeData[0]);
                    int child = Integer.parseInt(edgeData[1]);
                    adjList.addEdge(parent, child);
                }
            }
            graph = adjList;
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error parsing the file data: " + e.getMessage());
        }
    }
}
