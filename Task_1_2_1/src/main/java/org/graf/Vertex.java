package org.graf;

import java.util.Vector;

public class Vertex {
    public String vertName;
    public int mark = 0;
    public Vector<Edge> edgeArr;

    public Vertex(String vertName) {
        this.vertName = vertName;
        this.edgeArr = new Vector<>();
    }
}
