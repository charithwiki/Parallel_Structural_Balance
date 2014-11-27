package edu.usc.ee599;

import com.google.common.collect.Sets;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by charith on 11/24/14.
 */
public class SignedTriangleCountingInDynamicGraph {


    static Set<Edge> edgeSet = new HashSet<Edge>(1000);

    static HashMap<Integer, Set<Integer>> edgeMap = new HashMap<Integer, Set<Integer>>();

    static Set<Triangle> allPlusT = new HashSet<Triangle>(1000);
    static Set<Triangle> allNegT = new HashSet<Triangle>(1000);
    static Set<Triangle> onePlusT = new HashSet<Triangle>(1000);
    static Set<Triangle> twoPlusT = new HashSet<Triangle>(1000);


    static HashMap<String, Edge> edges = new HashMap<String, Edge>(1000);





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

                            if (!allPlusT.contains(t) && !allNegT.contains(t) && !onePlusT.contains(t) && ! twoPlusT.contains(t)) {


                                Edge e1 = edges.get("" + e.source + "-" + common);

                                if (e1 == null) {
                                    e1 = edges.get("" + common + "-" + e.sink);
                                }


                                Edge e2 = edges.get("" + e.sink + "-" + common);

                                if (e2 == null) {
                                    e2 = edges.get("" + common + "-" + e.sink);
                                }


                                int switchKey = e.sign  +  e1.sign + e2.sign;

                                switch(switchKey) {

                                    case 3: {
                                        allPlusT.add(t);
                                        break;
                                    }

                                    case -3: {
                                        allNegT.add(t);
                                        break;
                                    }

                                    case 1: {
                                        twoPlusT.add(t);
                                        break;
                                    }

                                    case -1: {
                                        onePlusT.add(t);
                                        break;
                                    }
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

            } else if (e.operation == Edge.REMOVE) {

                if(!edgeSet.contains(e)) {
                    System.out.println("Invalid edge operation, Edge does not exist! : " + e);
                    e = generator.nextEdge();
                    continue;
                }

                if (edgeMap.get(e.source) != null && edgeMap.get(e.sink) != null) {


                    Sets.SetView<Integer> setView = Sets.intersection(edgeMap.get(e.source), edgeMap.get(e.sink));

                    for (int common : setView) {

                        Triangle t = new Triangle(e.source, e.sink, common);

                        if(allPlusT.contains(t)) {
                            allPlusT.remove(t);
                        }

                        if(allNegT.contains(t)) {
                            allNegT.remove(t);
                        }

                        if(onePlusT.contains(t)) {
                            onePlusT.remove(t);
                        }

                        if(twoPlusT.contains(t)) {
                            twoPlusT.remove(t);
                        }


                    }


                }


                edgeSet.remove(e);

                if(edgeMap.containsKey(e.source)) {
                    edgeMap.get(e.source).remove(e.sink);
                }

                if(edgeMap.containsKey(e.sink)) {
                    edgeMap.get(e.sink).remove(e.source);
                }

            } else if(e.operation == Edge.UPDATE) {


                if(!edgeSet.contains(e)) {
                    System.out.println("Invalid edge operation, Edge does not exist! : " + e);
                    e = generator.nextEdge();
                    continue;
                }

                if (edgeMap.get(e.source) != null && edgeMap.get(e.sink) != null) {


                    Sets.SetView<Integer> setView = Sets.intersection(edgeMap.get(e.source), edgeMap.get(e.sink));

                    for (int common : setView) {

                        Triangle t = new Triangle(e.source, e.sink, common);

                        int switchKey = -1;

                        if(allPlusT.contains(t)) {
                            switchKey = 1; // All + -> two +
                            allPlusT.remove(t);
                        }

                        if(allNegT.contains(t)) {
                            switchKey = 2; // All - -> one +
                            allNegT.remove(t);
                        }

                        if(onePlusT.contains(t)) {
                            if(e.sign == Edge.MINUS) {
                                switchKey = 3; // One + -> All -
                            } else {
                                switchKey = 1; // One + -> Two +
                            }
                            onePlusT.remove(t);
                        }

                        if(twoPlusT.contains(t)) {

                            if(e.sign == Edge.MINUS) {
                                switchKey = 2; // Two + -> One +
                            } else {
                                switchKey = 4; //Two + -> All +
                            }

                            twoPlusT.remove(t);
                        }


                        switch(switchKey) {
                            case 1: {
                                twoPlusT.add(t);
                                break;
                            }
                            case 2: {
                                onePlusT.add(t);
                                break;
                            }
                            case 3: {
                                allNegT.add(t);
                                break;
                            }
                            case 4: {
                                allPlusT.add(t);
                                break;
                            }
                        }


                    }


                }


            }

            e = generator.nextEdge();
        }





        long endTime = System.currentTimeMillis();


        System.out.println(" Time Spent(ms) : " + (endTime - startTime));

        System.out.println("Rate (edges/sec):" + (double) (edgeCount * 1000) / (double) (endTime - startTime));

        System.out.println("Total Triangle Count: " + (allNegT.size() + allPlusT.size() + twoPlusT.size() + onePlusT.size()));

        System.out.println("Balanced Triangles: " + (allNegT.size() + allPlusT.size() + onePlusT.size()));

        System.out.println("Unbalanced Triangles: " + (twoPlusT.size()));

    }
}
