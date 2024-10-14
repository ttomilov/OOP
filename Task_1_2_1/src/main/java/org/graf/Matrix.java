package org.graf;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;
import java.util.Vector;

public class Matrix {
    private Vector<Vector<Integer>> matrix;
    private int numOfVert;
    private boolean isDirected;

    public Matrix() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter matrix dimension: ");
        this.numOfVert = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Is the graph directed? (true/false): ");
        this.isDirected = scanner.nextBoolean();

        this.matrix = new Vector<>();
        for (int i = 0; i < numOfVert; i++) {
            Vector<Integer> row = new Vector<>();
            for (int j = 0; j < numOfVert; j++) {
                if (isDirected) {
                    row.add(Integer.MIN_VALUE);
                } else {
                    row.add(0);
                }
            }
            matrix.add(row);
        }
    }

    public void addVertex() {
        numOfVert++;
        Vector<Integer> newRow = new Vector<>();
        for (int i = 0; i < numOfVert; i++) {
            if (isDirected) {
                newRow.add(Integer.MIN_VALUE); // Ориентированный граф: ребро отсутствует
            } else {
                newRow.add(0); // Неориентированный граф: ребро отсутствует
            }
        }
        matrix.add(newRow);

        for (int i = 0; i < numOfVert - 1; i++) {
            if (isDirected) {
                matrix.get(i).add(Integer.MIN_VALUE); // Ориентированный граф
            } else {
                matrix.get(i).add(0); // Неориентированный граф
            }
        }
    }

    public void delVertex(int vertNum) {
        vertNum--;
        if (vertNum < 0 || vertNum >= numOfVert) {
            System.out.println("This vertex doesn`t exist!");
            return;
        }
        // Помечаем все рёбра этой вершины как удалённые
        for (int i = 0; i < numOfVert; i++) {
            matrix.get(vertNum).set(i, isDirected ? Integer.MIN_VALUE : 0);
            matrix.get(i).set(vertNum, isDirected ? Integer.MIN_VALUE : 0);
        }
    }

    public void addEdge(int parent, int child, int weight) {
        parent--;
        child--;
        if (parent < 0 || parent >= numOfVert || child < 0 || child >= numOfVert) {
            System.out.println("Vertex doesn`t exist!");
            return;
        }
        matrix.get(parent).set(child, weight);
        if (!isDirected) {
            matrix.get(child).set(parent, weight);
        }
    }

    public void delEdge(int parent, int child) {
        parent--;
        child--;
        if (parent < 0 || parent >= numOfVert || child < 0 || child >= numOfVert) {
            System.out.println("Vertex doesn`t exist!");
            return;
        }
        matrix.get(parent).set(child, isDirected ? Integer.MIN_VALUE : 0);
        if (!isDirected) {
            matrix.get(child).set(parent, 0);
        }
    }

    public void printNeighbours(int vertNum) {
        vertNum--;
        if (vertNum < 0 || vertNum >= numOfVert) {
            System.out.println("This vertex doesn`t exist!");
            return;
        }
        System.out.printf("Neighbours of %d vertex: ", vertNum + 1);
        for (int i = 0; i < matrix.get(vertNum).size(); i++) {
            if (matrix.get(vertNum).get(i) != (isDirected ? Integer.MIN_VALUE : 0)) {
                System.out.printf("%d ", i + 1);
            }
        }
        System.out.println();
    }

    public void topSort() {
        if (!isDirected) {
            System.out.println("Topological sorting is only available for directed graphs.");
            return;
        }
        Stack<Integer> stack = new Stack<>();
        boolean[] visited = new boolean[numOfVert];
        boolean[] recStack = new boolean[numOfVert];
        Integer[] sortedVertices = new Integer[numOfVert];
        int[] topOrder = new int[numOfVert];
        Integer step = 0;
        for (int i = 0; i < numOfVert; i++) {
            if (!visited[i]) {
                if (dfsTopSort(i, visited, recStack, stack)) {
                    System.out.println("Cycle detected! Topological sort is not possible.");
                    return;
                }
            }
        }
        while (!stack.isEmpty()) {
            sortedVertices[step] = stack.pop();
            step++;
        }
        for (int i = 0; i < numOfVert; i++) {
            topOrder[sortedVertices[i]] = i;
        }
        System.out.print("Topological Sort: ");
        for (int i = 0; i < numOfVert; i++) {
            System.out.printf("%d ", topOrder[i] + 1);
        }
        System.out.println();
    }

    private boolean dfsTopSort(int v, boolean[] visited, boolean[] recStack, Stack<Integer> stack) {
        visited[v] = true;
        recStack[v] = true;
        for (int i = 0; i < numOfVert; i++) {
            if (matrix.get(v).get(i) != Integer.MIN_VALUE) {
                if (!visited[i] && dfsTopSort(i, visited, recStack, stack)) {
                    return true;
                } else if (recStack[i]) {
                    return true;
                }
            }
        }
        recStack[v] = false;
        stack.push(v);
        return false;
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
            this.matrix = new Vector<>();
            for (int i = 0; i < numOfVert; i++) {
                Vector<Integer> row = new Vector<>();
                for (int j = 0; j < numOfVert; j++) {
                    row.add(null);
                }
                matrix.add(row);
            }
            while (scanner.hasNextLine()) {
                String[] edgeData = scanner.nextLine().split(" ");
                if (edgeData.length == 3) {
                    int parent = Integer.parseInt(edgeData[0]) - 1;
                    int child = Integer.parseInt(edgeData[1]) - 1;
                    int weight = Integer.parseInt(edgeData[2]);
                    matrix.get(parent).set(child, weight);
                    if (!isDirected) {
                        matrix.get(child).set(parent, weight);
                    }
                }
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Ошибка: файл не найден.");
        }
    }
}
