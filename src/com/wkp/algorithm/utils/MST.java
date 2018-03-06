package com.wkp.algorithm.utils;

import com.wkp.algorithm.bean.Edge;
import com.wkp.algorithm.bean.Vertex;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * 最小生成树算法，解决整体图连通的最小成本问题
 */
public class MST {
    /**
     * Prim算法 普里姆算法
     *
     * @return
     */
    public static Set<Edge> prim(Set<Vertex> vertices, Set<Edge> edges) {
        //不接收空集合
        if (vertices == null || edges == null) {
            return null;
        }
        //初始集合大小
        int size = vertices.size();
        Set<Vertex> temp = new HashSet<>();
        temp.addAll(vertices);
        //不接收空顶点，初始化新顶点集合，初始化新边集合
        Iterator<Vertex> iterator = temp.iterator();
        Vertex v0 = null;
        if (iterator.hasNext()) {
            v0 = iterator.next();
            iterator.remove();
        }
        if (v0 == null) {
            return null;
        }
        Set<Vertex> newVertices = new HashSet<>();
        newVertices.add(v0);
        Set<Edge> newEdges = new HashSet<>();
        //循环至新顶点集合大小达到初始大小
        while (newVertices.size() < size) {
            Iterator<Vertex> vertexIterator = newVertices.iterator();
            //初始化比较所用的边及其长度
            Edge newEdge = null;
            double length = Double.MAX_VALUE;
            //迭代新顶点集合
            while (vertexIterator.hasNext()) {
                Vertex vertex = vertexIterator.next();
                //迭代初始边集合
                for (Edge edge : edges) {
                    //不接收空边
                    if (edge == null) {
                        return null;
                    }
                    //判断边是否含有该顶点
                    if (edge.containVertex(vertex)) {
                        Vertex v1 = edge.getV1();
                        Vertex v2 = edge.getV2();
                        //不接收空顶点
                        if (v1 == null || v2 == null) {
                            return null;
                        }
                        //新顶点集合不同时包含边的两个顶点
                        if (!(newVertices.contains(v1) && newVertices.contains(v2))) {
                            //初始顶点集合包含边的另一个顶点
                            if (temp.contains(v1) || temp.contains(v2)) {
                                //找出边长最小的边
                                if (edge.getLength() < length) {
                                    length = edge.getLength();
                                    newEdge = edge;
                                }
                            }
                        }
                    }
                }
            }
            //添加最小边长
            if (newEdge != null) {
                if (!newVertices.contains(newEdge.getV1())) {
                    newVertices.add(newEdge.getV1());
                    temp.remove(newEdge.getV1());
                }
                if (!newVertices.contains(newEdge.getV2())) {
                    newVertices.add(newEdge.getV2());
                    temp.remove(newEdge.getV2());
                }
                newEdges.add(newEdge);
            }
        }
        return newEdges;
    }


    /*--------------------------------------------------------------分割线-------------------------------------------------------------------------*/


    /**
     * Kruskal算法
     * @param vertices
     * @param edges
     * @return
     */
    public static Set<Edge> kruskal(Set<Vertex> vertices, Set<Edge> edges) {
        //不接收空集合
        if (vertices == null || edges == null) {
            return null;
        }
        //不接收空顶点
        for (Vertex vertex : vertices) {
            if (vertex == null) {
                return null;
            }
        }
        Set<Edge> newEdges = new HashSet<>();
        Set<Edge> temp = new HashSet<>();
        temp.addAll(edges);
        while (!containsAllVertices(vertices, newEdges)) {
            Edge minEdge = minLengthEdge(temp);
            if (minEdge == null) {
                return null;
            }
            temp.remove(minEdge);
            if (!isConnected(minEdge,newEdges)) {
                newEdges.add(minEdge);
            }
        }
        return newEdges;
    }

    /**
     * Kruskal算法 获取边集合中最短边
     * @param edges
     * @return
     */
    private static Edge minLengthEdge(Set<Edge> edges) {
        double length = Double.MAX_VALUE;
        Edge result = null;
        for (Edge edge : edges) {
            if (edge == null) {
                return null;
            }
            if (edge.getLength() < length) {
                length = edge.getLength();
                result = edge;
            }
        }
        return result;
    }

    /**
     * Kruskal算法 边集合中是否包含所有顶点
     * @param vertices
     * @param edges
     * @return
     */
    private static boolean containsAllVertices(Set<Vertex> vertices, Set<Edge> edges) {
        for (Vertex vertex : vertices) {
            if (!containsVertex(vertex,edges)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Kruskal算法 边的两个顶点在边集合中是否已经连通
     * @param edge
     * @param edges
     * @return
     */
    private static boolean isConnected(Edge edge, Set<Edge> edges) {
        if (edges != null && edge != null) {
            if (edges.contains(edge)) {
                return true;
            }
            Vertex v1 = edge.getV1();
            Vertex v2 = edge.getV2();
            if (!(containsVertex(v1,edges) && containsVertex(v2,edges))) {
                return false;
            }
            Set<Edge> temp = new HashSet<>();
            temp.addAll(edges);
            return v1ToV2(v1, v2, temp);
        }
        return true;
    }

    /**
     * Kruskal算法 顶点1 与 顶点2 在边集合中是否连通
     * @param v1
     * @param v2
     * @param edges
     * @return
     */
    private static boolean v1ToV2(Vertex v1, Vertex v2, Set<Edge> edges) {
        Vertex point = v1;
        Iterator<Edge> iterator = edges.iterator();
        while (iterator.hasNext()) {
            Edge edge = iterator.next();
            Vertex otherOne = edge.getOtherOne(point);
            if (otherOne != null) {
                if (otherOne.equals(v2)) {
                    return true;
                }
                iterator.remove();
                if (v1ToV2(point, v2, edges)) {
                    return true;
                }
                point = otherOne;
                return v1ToV2(point, v2, edges);
            }
        }
        return false;
    }

    /**
     * Kruskal算法 边集合中是否包含该顶点
     * @param vertex
     * @param edges
     * @return
     */
    private static boolean containsVertex(Vertex vertex, Set<Edge> edges) {
        if (edges != null) {
            for (Edge edge : edges) {
                if (edge.containVertex(vertex)) {
                    return true;
                }
            }
        }
        return false;
    }

}
