package com.wkp.algorithm;

import com.wkp.algorithm.bean.Edge;
import com.wkp.algorithm.bean.Path;
import com.wkp.algorithm.bean.Vertex;
import com.wkp.algorithm.utils.MST;
import com.wkp.algorithm.utils.SPA;
import com.wkp.algorithm.utils.StrIndex;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Test {
    public static void main(String[] args) {
//        int[] ints = {13,-3,-25,20,-3,-16,-23,18,20,-7,12,-5,-22,15,-4,7};
//        int[] maxSubArr = Sort.findMaxSubArr(ints);
//        Sort.toString(maxSubArr);
//        Vertex vO = new Vertex("O",0, 0);
//        Vertex vA = new Vertex("A",1, 2);
//        Vertex vB = new Vertex("B",2, 1);
//        Vertex vC = new Vertex("C",3, 0);
//        Vertex vD = new Vertex("D",3, 2);
//        Vertex vE = new Vertex("E",4, 3);
//        Edge eOA = new Edge(vO, vA);
//        Edge eOB = new Edge(vO, vB);
//        Edge eOC = new Edge(vO, vC);
//        Edge eAB = new Edge(vA, vB);
//        Edge eBC = new Edge(vB, vC);
//        Edge eAD = new Edge(vA, vD);
//        Edge eBD = new Edge(vB, vD);
//        Edge eCD = new Edge(vC, vD);
//        Edge eDE = new Edge(vD, vE);
//        List<Vertex> vertices = new ArrayList<>();
//        vertices.add(vO);
//        vertices.add(vA);
//        vertices.add(vB);
//        vertices.add(vC);
//        vertices.add(vD);
//        vertices.add(vE);
//        List<Edge> edges = new ArrayList<>();
//        edges.add(eOA);
//        edges.add(eOB);
//        edges.add(eOC);
//        edges.add(eAB);
//        edges.add(eBC);
//        edges.add(eAD);
//        edges.add(eBD);
//        edges.add(eCD);
//        edges.add(eDE);

        String str = "abcdabcdefghijzxysnfjdshfmfdbmklmnabcdeffdbc";

        long st1 = System.nanoTime();
        int i = StrIndex.indexOfKMP(str, "fdbc");
        long en1 = System.nanoTime();
        System.out.println(en1 - st1);

        long st2 = System.nanoTime();
        int i1 = str.indexOf("fdbc");
        long en2 = System.nanoTime();
        System.out.println(en2 - st2);

        long st3 = System.nanoTime();
        int i2 = StrIndex.indexOfSunday(str, "fdbc");
        long en3 = System.nanoTime();
        System.out.println(en3 - st3);
    }
}
