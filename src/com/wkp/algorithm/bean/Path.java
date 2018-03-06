package com.wkp.algorithm.bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 路径类
 */
public final class Path {
    //路径顶点顺序集合
    private List<Vertex> mVertices;
    //路径长度
    private double length;

    public Path() {
        mVertices = new ArrayList<>();
    }

    public Path(List<Vertex> vertices) {
        mVertices = vertices;
        length = getPathLength();
    }

    public Path(Vertex... vertices) {
        mVertices = new ArrayList<>();
        if (vertices != null) {
            mVertices.addAll(Arrays.asList(vertices));
        }
        length = getPathLength();
    }

    /**
     * 添加顶点到路径
     * @param index 索引
     * @param vertex 顶点
     * @return
     */
    public boolean addVertex(int index, Vertex vertex) {
        if (mVertices != null) {
            mVertices.add(index, vertex);
            length = getPathLength();
            return true;
        }
        return false;
    }

    /**
     * 添加顶点到路径
     * @param vertex 顶点
     * @return
     */
    public boolean addVertex(Vertex vertex) {
        if (mVertices != null) {
            mVertices.add(vertex);
            length = getPathLength();
            return true;
        }
        return false;
    }

    /**
     * 移除顶点
     * @param vertex 顶点
     * @return
     */
    public boolean removeVertex(Vertex vertex) {
        if (mVertices != null) {
            mVertices.remove(vertex);
            length = getPathLength();
            return true;
        }
        return false;
    }

    /**
     * 移除顶点
     * @param index 索引
     * @return
     */
    public boolean removeVertex(int index) {
        if (mVertices != null) {
            mVertices.remove(index);
            length = getPathLength();
            return true;
        }
        return false;
    }

    /**
     * 获取路径长度
     * @return
     */
    public double getLength() {
        return length;
    }

    public void refreshLength() {
        length = getPathLength();
    }

    /**
     * 获取路径顶点集合
     * @return
     */
    public List<Vertex> getVertices() {
        return mVertices;
    }

    /**
     * 获取路径起始点
     * @return
     */
    public Vertex getVStart() {
        if (mVertices != null && mVertices.size() > 0) {
            return mVertices.get(0);
        }
        return null;
    }

    /**
     * 获取路径结束点
     * @return
     */
    public Vertex getVEnd() {
        if (mVertices != null && mVertices.size() > 0) {
            return mVertices.get(mVertices.size() - 1);
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder vertices = new StringBuilder();
        if (mVertices != null) {
            for (int i = 0; i < mVertices.size(); i++) {
                if (i == mVertices.size() - 1) {
                    vertices.append(mVertices.get(i).getName());
                } else {
                    vertices.append(mVertices.get(i).getName()).append("-->");
                }
            }
        }
        return "Path{" +
                vertices.toString() +
                ", length=" + length +
                '}';
    }

    /**
     * 路径长度
     * @return
     */
    private double getPathLength() {
        if (mVertices != null && mVertices.size() > 1) {
            Vertex vStart = mVertices.get(0);
            double length = 0;
            for (int i = 1; i < mVertices.size(); i++) {
                Vertex vEnd = mVertices.get(i);
                length += getV1ToV2Length(vStart, vEnd);
                vStart = vEnd;
            }
            return length;
        }
        return 0;
    }

    /**
     * 两顶点之间的距离
     * @param v1
     * @param v2
     * @return
     */
    private double getV1ToV2Length(Vertex v1, Vertex v2) {
        if (v1 == null || v2 == null) {
            return 0;
        }
        return Math.sqrt(Math.pow((v2.getX() - v1.getX()), 2) + Math.pow((v2.getY() - v1.getY()), 2));
    }
}
