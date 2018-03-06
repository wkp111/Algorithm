package com.wkp.algorithm.utils;

import com.wkp.algorithm.bean.Edge;
import com.wkp.algorithm.bean.Path;
import com.wkp.algorithm.bean.Vertex;

import java.util.*;

/**
 * 最短路径算法
 */
public class SPA {

    /**
     * Dijkstra算法 迪杰斯特拉算法 不具备向量性
     *
     * @param edges    边集合
     * @param vertices 顶点集合
     * @param vStart   起始点
     * @return
     */
    public static List<Path> dijkstra(List<Edge> edges, List<Vertex> vertices, Vertex vStart) {
        if (containsNull(edges, vertices, vStart)) {
            return null;
        }
        List<Path> shortPaths = new ArrayList<>();
        List<Vertex> U = new ArrayList<>();
        U.addAll(vertices);
        U.remove(vStart);
        Path P0To0 = new Path(vStart, vStart);
        shortPaths.add(P0To0);
        Path currentPath = P0To0;
        while (U.size() > 0) {
            Path tempPath = null;
            double tempLength = Double.MAX_VALUE;
            for (Edge edge : edges) {
                if (isAdjacentEdge(edge, currentPath, U) && currentPath != null) {
                    Path newPath = createNewPath(currentPath, edge.getOtherOne(currentPath.getVEnd()));
                    Path shortPath = addShortPath(shortPaths, newPath);
                    if (shortPath.getLength() < tempLength) {
                        tempLength = shortPath.getLength();
                        tempPath = shortPath;
                    }
                }
            }
            currentPath = tempPath;
            if (currentPath != null) {
                U.remove(currentPath.getVEnd());
            }
        }
        return shortPaths;
    }

    /**
     * Dijkstra算法 比较最短路径并添加集合
     *
     * @param shortPaths  最短路径集合
     * @param currentPath 待比较路径
     * @return
     */
    private static Path addShortPath(List<Path> shortPaths, Path currentPath) {
        for (int i = 0; i < shortPaths.size(); i++) {
            Path path = shortPaths.get(i);
            if (Objects.equals(path.getVStart(), currentPath.getVStart()) && Objects.equals(path.getVEnd(), currentPath.getVEnd())) {
                if (path.getLength() < currentPath.getLength()) {
                    return path;
                } else {
                    shortPaths.set(i, currentPath);
                    return currentPath;
                }
            }
        }
        shortPaths.add(currentPath);
        return currentPath;
    }

    /**
     * Dijkstra算法 在已知路径上创建新路径
     *
     * @param firstPath 已知路径
     * @param vertex    路径最新结束顶点
     * @return
     */
    private static Path createNewPath(Path firstPath, Vertex vertex) {
        List<Vertex> vertices = new ArrayList<>();
        for (Vertex v : firstPath.getVertices()) {
            if (!vertices.contains(v)) {
                vertices.add(v);
            }
        }
        vertices.add(vertex);
        return new Path(vertices);
    }

    /**
     * Dijkstra算法 判断是否为路径结束顶点的有效临边
     *
     * @param edge 待判断临边
     * @param path 已知路径
     * @param u    有效临边的非结束顶点集合
     * @return
     */
    private static boolean isAdjacentEdge(Edge edge, Path path, List<Vertex> u) {
        if (!edge.containVertex(path.getVEnd())) {
            return false;
        }
        Vertex otherOne = edge.getOtherOne(path.getVEnd());
        return u.contains(otherOne);
    }

    /**
     * Dijkstra算法 判断边集合、顶点集合、起始点是否满足条件
     *
     * @param edges
     * @param vertices
     * @param vertex
     * @return
     */
    private static boolean containsNull(List<Edge> edges, List<Vertex> vertices, Vertex vertex) {
        if (edges == null || vertices == null || vertex == null) {
            return true;
        }
        if (!containsVertex(vertex, edges) || !vertices.contains(vertex)) {
            return true;
        }
        for (Edge edge : edges) {
            if (edge == null) {
                return true;
            }
        }
        for (Vertex v : vertices) {
            if (v == null) {
                return true;
            }
        }
        return false;
    }

    /**
     * Dijkstra算法 边集合中是否包含该顶点
     *
     * @param vertex
     * @param edges
     * @return
     */
    private static boolean containsVertex(Vertex vertex, List<Edge> edges) {
        if (edges != null) {
            for (Edge edge : edges) {
                if (edge.containVertex(vertex)) {
                    return true;
                }
            }
        }
        return false;
    }



    /*--------------------------------------------------------------分割线-------------------------------------------------------------------------*/


    /**
     * Floyd算法 弗洛伊德算法 具备向量性
     * @param vertices
     * @param edges
     * @return
     */
    public static List<Path> floyd(List<Vertex> vertices, List<Edge> edges) {
        List<Path> shortPaths = new ArrayList<>();
        double inf = Double.MAX_VALUE / 2; //无穷大
        double[][] lengths = new double[vertices.size()][vertices.size()];  //两点之间距离数组，相同点0，向量相邻点有值，其他无穷大
        for (int i = 0; i < vertices.size(); i++) {
            for (int j = 0; j < vertices.size(); j++) {
                if (i == j) {
                    lengths[i][j] = 0;
                    shortPaths.add(new Path(vertices.get(i), vertices.get(j)));
                } else {
                    Edge edge = getEdgeByVertex(vertices.get(i), vertices.get(j), edges);
                    if (edge != null) {
                        lengths[i][j] = edge.getLength();
                        shortPaths.add(new Path(vertices.get(i), vertices.get(j)));
                    } else {
                        lengths[i][j] = inf;
                    }
                }
            }
        }

        for (int k = 0; k < vertices.size(); k++) {
            for (int i = 0; i < vertices.size(); i++) {
                for (int j = 0; j < vertices.size(); j++) {
                    if (lengths[i][j] > (lengths[i][k] + lengths[k][j])) {
                        Path pathIJ = findPathByVertex(vertices.get(i), vertices.get(j), shortPaths);
                        Path pathIK = findPathByVertex(vertices.get(i), vertices.get(k), shortPaths);
                        Path pathKJ = findPathByVertex(vertices.get(k), vertices.get(j), shortPaths);
                        List<Vertex> mergePath = mergePath(pathIK, pathKJ);
                        if (pathIJ != null) {
                            List<Vertex> verticesIJ = pathIJ.getVertices();
                            verticesIJ.clear();
                            verticesIJ.addAll(mergePath);
                            pathIJ.refreshLength();
                        } else {
                            shortPaths.add(new Path(mergePath));
                        }
                        lengths[i][j] = lengths[i][k] + lengths[k][j];
                    }
                }
            }
        }

        return shortPaths;
    }

    /**
     * Floyd算法 根据起始点在路径集合中查找路径
     * @param vStart
     * @param vEnd
     * @param paths
     * @return
     */
    public static Path findPathByVertex(Vertex vStart, Vertex vEnd, List<Path> paths) {
        for (Path path : paths) {
            if (vStart.equals(path.getVStart()) && vEnd.equals(path.getVEnd())) {
                return path;
            }
        }
        return null;
    }

    /**
     * Floyd算法 合并路径
     * @param front
     * @param off
     * @return
     */
    private static List<Vertex> mergePath(Path front, Path off) {
        List<Vertex> result = new ArrayList<>();
        List<Vertex> vertices = front.getVertices();
        result.addAll(vertices);
        result.remove(result.size() - 1);
        result.addAll(off.getVertices());
        return result;
    }

    /**
     * Floyd算法 根据起始点在边集合中查找边
     * @param vStart
     * @param vEnd
     * @param edges
     * @return
     */
    private static Edge getEdgeByVertex(Vertex vStart, Vertex vEnd, List<Edge> edges) {
        for (Edge edge : edges) {
            if (vStart.equals(edge.getV1()) && vEnd.equals(edge.getV2())) {
                return edge;
            }
        }
        return null;
    }

}
