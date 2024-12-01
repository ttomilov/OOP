package org.main;

import org.graph.AdjList;
import org.graph.AdjMatrix;
import org.graph.IncidenceMatrix;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.*;

/*
 * Test cases for Graph implementations.
 */
public class GraphTests {

    @Test
    public void adjMatrixTest(){
        AdjMatrix graph = new AdjMatrix(5, true);
        graph.addEdge(1, 5);
        graph.addEdge(3, 1);
        graph.delEdge(3, 1);
        graph.delVertex(5);
        graph.printNeighbours(3);
        assertEquals(graph.getNumVert(), 4);
        assertEquals(graph.getEdge(1, 5), 0);
        assertEquals(graph.getEdge(3, 1), 0);
    }

    @Test
    public void incidentMatrixTest(){
        IncidenceMatrix graph = new IncidenceMatrix(5, true);
        graph.addEdge(1, 5);
        graph.addEdge(3, 1);
        graph.delEdge(3, 1);
        graph.delVertex(5);
        graph.printNeighbours(3);
        assertEquals(graph.getNumVert(), 4);
        assertEquals(graph.getEdge(1, 5), 0);
        assertEquals(graph.getEdge(3, 1), 0);
    }

    @Test
    public void adjListTest(){
        AdjList graph = new AdjList(5, true);
        graph.addEdge(1, 5);
        graph.addEdge(3, 1);
        graph.delEdge(3, 1);
        graph.delVertex(5);
        graph.printNeighbours(3);
        assertEquals(graph.getNumVert(), 4);
        assertEquals(graph.getEdge(1, 5), 0);
        assertEquals(graph.getEdge(3, 1), 0);
    }

    @Test
    public void testScanFromFile() {
        AdjList graph = new AdjList(0, false);
        graph.scanFromFile("src/test/resources/test_graph.txt");
        assertEquals(graph.getNumVert(), 5);
        assertEquals(graph.getEdge(1, 2), 1);
        assertEquals(graph.getEdge(3, 4), 1);
        assertEquals(graph.getEdge(2, 3), 0);

        AdjMatrix graph1 = new AdjMatrix(0, false);
        graph1.scanFromFile("src/test/resources/test_graph.txt");
        assertEquals(graph1.getNumVert(), 5);
        assertEquals(graph1.getEdge(1, 2), 1);
        assertEquals(graph1.getEdge(3, 4), 1);
        assertEquals(graph1.getEdge(2, 3), 0);

        IncidenceMatrix graph2 = new IncidenceMatrix(0, false);
        graph2.scanFromFile("src/test/resources/test_graph.txt");
        assertEquals(graph2.getNumVert(), 5);
        assertEquals(graph2.getEdge(1, 2), 1);
        assertEquals(graph2.getEdge(3, 4), 1);
        assertEquals(graph2.getEdge(2, 3), 0);
    }
}