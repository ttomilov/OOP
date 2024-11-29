package org.main;

import org.graph.AdjList;
import org.graph.AdjMatrix;
import org.graph.IncidenceMatrix;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/*
 * Test cases for Graph implementations.
 */
public class GraphTests {

    @Test
    public void adjMatrixTest(){
        Graph graph = new AdjMatrix(5, true);
        graph.addEdge(1, 5);
        graph.addEdge(3, 1);
        graph.delVertex(5);
        graph.printNeighbours(3);
        assertEquals(((AdjMatrix) graph).getNumVert(), 4);
        assertEquals(((AdjMatrix) graph).getEdge(1, 5), 0);
    }

    @Test
    public void incidentMatrixTest(){
        Graph graph = new IncidenceMatrix(5, true);
        graph.addEdge(1, 5);
        graph.addEdge(3, 1);
        graph.delVertex(5);
        graph.printNeighbours(3);
        assertEquals(((IncidenceMatrix) graph).getNumVert(), 4);
        assertEquals(((IncidenceMatrix) graph).getEdge(1, 5), 0);

    }

    @Test
    public void adjListTest(){
        Graph graph = new AdjList(5, true);
        graph.addEdge(1, 5);
        graph.addEdge(3, 1);
        graph.delVertex(5);
        graph.printNeighbours(3);
        assertEquals(((AdjList) graph).getNumVert(), 4);
        assertEquals(((AdjList) graph).getEdge(1, 5), 0);
    }
}
