package com.wkp.algorithm.bean;

/**
 * 边类
 */
public final class Edge {
    private Vertex v1;
    private Vertex v2;

    public Edge() {
    }

    public Edge(Vertex v1, Vertex v2) {
        this.v1 = v1;
        this.v2 = v2;
    }

    public Vertex getV1() {
        return v1;
    }

    public void setV1(Vertex v1) {
        this.v1 = v1;
    }

    public Vertex getV2() {
        return v2;
    }

    public void setV2(Vertex v2) {
        this.v2 = v2;
    }

    public double getLength() {
        if (v1 == null || v2 == null) {
            return 0;
        }
        return Math.sqrt(Math.pow((v2.getX() - v1.getX()), 2) + Math.pow((v2.getY() - v1.getY()), 2));
    }

    public boolean containVertex(Vertex vertex) {
        return vertex != null && v1 != null && v2 != null &&
                ((vertex.getX() == v1.getX() && vertex.getY() == v1.getY()) || (vertex.getX() == v2.getX() && vertex.getY() == v2.getY()));
    }

    public Vertex getOtherOne(Vertex vertex) {
        if (containVertex(vertex)) {
            return vertex.equals(v1) ? v2 : v1;
        }
        return null;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "v1=" + v1 +
                ", v2=" + v2 +
                ", length=" + getLength() +
                '}';
    }
}
