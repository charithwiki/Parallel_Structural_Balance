package edu.usc.ee599;

import com.google.common.collect.Sets;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by charith on 11/24/14.
 */
public class NaiveBalanceTriangleCounting {


    static Set<Edge> edgeSet = new HashSet<Edge>(1000);

    static HashMap<Integer, Set<Integer>> edgeMap = new HashMap<Integer, Set<Integer>>();

    static Set<Triangle> allPlusT = new HashSet<Triangle>(1000);
    static Set<Triangle> allNegT = new HashSet<Triangle>(1000);
    static Set<Triangle> onePlusT = new HashSet<Triangle>(1000);
    static Set<Triangle> twoPlusT = new HashSet<Triangle>(1000);


    static HashMap<String, Edge> edges = new HashMap<String, Edge>(1000);

    static int count = 0;

    static int balanced = 0;

    static int unbalanced = 0;


    public static void main(String[] args) throws Exception {


        GraphGenerator generator = new SignedGraphGenerator("/home/charith/Downloads/testfile.csv");

        generator.initialize();

        long startTime = System.currentTimeMillis();

        Edge e = generator.nextEdge();
        int edgeCount = 0;


        while (e != null) {
            edgeCount++;

            if (e.operation == Edge.ADD) {

                if (edgeSet.contains(e)) {
                    e = generator.nextEdge();
                    continue;
                } else {


                    if (edgeMap.get(e.source) != null && edgeMap.get(e.sink) != null) {


                        Sets.SetView<Integer> setView = Sets.intersection(edgeMap.get(e.source), edgeMap.get(e.sink));


                        for (int common : setView) {

                            Triangle t = new Triangle(e.source, e.sink, common);

                            if (!triangles.contains(t)) {
                                triangles.add(t);
                                count++;


                                Edge e1 = edges.get("" + e.source + "-" + common);

                                if (e1 == null) {
                                    e1 = edges.get("" + common + "-" + e.sink);
                                }


                                Edge e2 = edges.get("" + e.sink + "-" + common);

                                if (e2 == null) {
                                    e2 = edges.get("" + common + "-" + e.sink);
                                }


                                if ((e.sign * e1.sign * e2.sign) == Edge.PLUS) {

                                    balanced++;

                                } else {
                                    unbalanced++;
                                }

                            }


                        }

                    }

                    edgeSet.add(e);
                    edges.put("" + e.source + "-" + e.sink, e);


                    if (edgeMap.containsKey(e.source)) {

                        edgeMap.get(e.source).add(e.sink);

                    } else {
                        Set<Integer> set = new HashSet<Integer>();
                        set.add(e.sink);

                        edgeMap.put(e.source, set);
                    }

                    if (edgeMap.containsKey(e.sink)) {

                        edgeMap.get(e.sink).add(e.source);

                    } else {
                        Set<Integer> set = new HashSet<Integer>();
                        set.add(e.source);

                        edgeMap.put(e.sink, set);
                    }


                }

            } else {

                if(!edgeSet.contains(e)) {
                    System.out.println("Invalid edge operation, Edge does not exist! : " + e);
                    e = generator.nextEdge();
                    continue;
                }




            }

            e = generator.nextEdge();
        }


        long endTime = System.currentTimeMillis();


        System.out.println(" Time Spent(ms) : " + (endTime - startTime));

        System.out.println("Rate (edges/sec):" + (double) (edgeCount * 1000) / (double) (endTime - startTime));

        System.out.println("Total Triangle Count: " + count);

        System.out.println("Balanced Triangles: " + balanced);

        System.out.println("Unbalanced Triangles: " + unbalanced);

    }
}
