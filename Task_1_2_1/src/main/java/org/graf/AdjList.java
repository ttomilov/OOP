package org.graf;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

public class AdjList {
    private Vector<Vertex> adjList;
    private int numOfVert;
    private boolean isDirected;

    public AdjList() {
        this.adjList = new Vector<>();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter matrix dimension: ");
        this.numOfVert = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Is the graph directed? (true/false): ");
        this.isDirected = scanner.nextBoolean();
        scanner.nextLine();
        for (int i = 0; i < numOfVert; i++) {
            System.out.print("Enter the vert name: ");
            adjList.add(new Vertex(scanner.nextLine()));
        }
    }

    void addVertex() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the vert name: ");
        String vertName = scanner.nextLine();
        if (findVertexByName(vertName) != -1) {
            System.out.println("That vertex does exist!");
            return;
        }
        adjList.add(new Vertex(vertName));
        numOfVert++;
    }

    public void delVertex(String vertName) {
        for (int i = 0; i < numOfVert; i++) {
            if (adjList.get(i).vertName.equals(vertName)) {
                numOfVert--;
                adjList.remove(i);
                continue;
            }
            adjList.get(i).edgeArr.removeIf(edge -> edge.perent.equals(vertName) || edge.child.equals(vertName));
        }
    }

    public void addEdge(String parent, String child, int weight) {
        int parentIndx = findVertexByName(parent), childIndx = findVertexByName(child);
        if (parentIndx == -1 || childIndx == -1) {
            System.out.println("One of these vertex does not exist!");
            return;
        }
        adjList.get(parentIndx).edgeArr.add(new Edge(parent, child, weight));
        if (!isDirected) {
            adjList.get(childIndx).edgeArr.add(new Edge(child, parent, weight));
        }
    }

    private int findVertexByName(String name) {
        for (int i = 0; i < numOfVert; i++) {
            if (adjList.get(i).vertName.equals(name)) {
                return i;
            }
        }
        return -1;
    }

    public void delEdge(String parent, String child) {
        int parentIndx = findVertexByName(parent), childIndx = findVertexByName(child);
        if (parentIndx == -1 || childIndx == -1) {
            System.out.println("One of these vertex doesn`t exist!");
            return;
        }
        int edgeIndx = findEdge(parent, child);
        if (edgeIndx == -1) {
            System.out.println("Edge doesn`t exist!");
            return;
        }
        adjList.get(parentIndx).edgeArr.remove(edgeIndx);
        if (!isDirected) {
            edgeIndx = findEdge(child, parent);
            if (edgeIndx != -1) {
                adjList.get(childIndx).edgeArr.remove(edgeIndx);
            }
        }
    }

    private int findEdge(String parent, String child) {
        for (int i = 0; i < adjList.size(); i++) {
            for (int j = 0; j < adjList.get(i).edgeArr.size(); j++) {
                if (adjList.get(i).edgeArr.get(j).perent.equals(parent) && adjList.get(i).edgeArr.get(j).child.equals(child)) {
                    return j;
                }
            }
        }
        return -1;
    }

    void printNeighbours(String vertName) {
        int vertIndx = findVertexByName(vertName);
        if (vertIndx == -1) {
            System.out.println("Vertex doesn`t exist!");
            return;
        }
        System.out.printf("Neighbours of \"%s\" vertex: ", vertName);
        for (Edge edge : adjList.get(vertIndx).edgeArr) {
            System.out.printf("%s ", edge.child);
        }
        System.out.println();
    }

    public void topSort() {
        if (!isDirected) {
            System.out.println("Topological sorting is only available for directed graphs.");
            return;
        }
        Integer[] help = new Integer[numOfVert];
        int[] tout = new int[numOfVert];
        int[] stepTout = {0};
        int[] flag = {0};
        for (int i = 0; i < numOfVert; i++) {
            if (adjList.get(i).mark == 0) {
                dfsForTopSort(i, help, stepTout, flag);
                if (flag[0] == -1) {
                    System.out.println("Сycle was found!");
                    return;
                }
            }
        }
        for (int i = 0; i < numOfVert / 2; i++) {
            int temp = help[i];
            help[i] = help[numOfVert - i - 1];
            help[numOfVert - i - 1] = temp;
        }
        for (int i = 0; i < numOfVert; i++) {
            System.out.printf("%s ", adjList.get(help[i]).vertName);
        }
        System.out.println();
    }

    private void dfsForTopSort(int v, Integer[] tout, int[] stepTout, int[] flag) {
        adjList.get(v).mark = 1;
        for (Edge edge : adjList.get(v).edgeArr) {
            int u = findVertexByName(edge.child);
            if (adjList.get(u).mark == 1) {
                flag[0] = -1;
                return;
            }
            if (adjList.get(u).mark == 0) {
                dfsForTopSort(u, tout, stepTout, flag);
                if (flag[0] == -1) return;
            }
        }
        adjList.get(v).mark = 2;
        tout[stepTout[0]] = v;
        stepTout[0]++;
    }

    public void readGraphFromFile(String fileName) {
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);
            if (scanner.hasNextInt()) {
                this.numOfVert = scanner.nextInt();
            }
            if (scanner.hasNextBoolean()) {
                this.isDirected = scanner.nextBoolean();
            }
            scanner.nextLine();
            for (int i = 0; i < numOfVert; i++) {
                if (scanner.hasNextLine()) {
                    String vertName = scanner.nextLine();
                    adjList.add(new Vertex(vertName));
                }
            }
            while (scanner.hasNextLine()) {
                String[] edgeData = scanner.nextLine().split(" ");
                if (edgeData.length == 3) {
                    String parent = edgeData[0];
                    String child = edgeData[1];
                    int weight = Integer.parseInt(edgeData[2]);
                    addEdge(parent, child, weight);
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Ошибка: файл не найден.");
        }
    }
    
}
