/*
 *  Copyright 2013 University of Southern California
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.package edu.usc.goffish.gopher.sample;
 */
package edu.usc.ee599.viz;

import com.google.common.collect.Sets;
import edu.usc.ee599.Edge;
import edu.usc.ee599.Triangle;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Charith Wickramaarachchi on 11/30/14.
 */
public class BalancedTrangleCountWithViz {

    private TriangleCountView view;


    static Set<Edge> edgeSet = new HashSet<Edge>(1000);

    static HashMap<Integer, Set<Integer>> edgeMap = new HashMap<Integer, Set<Integer>>();

    static Set<Triangle> allPlusT = new HashSet<Triangle>(1000);
    static Set<Triangle> allNegT = new HashSet<Triangle>(1000);
    static Set<Triangle> onePlusT = new HashSet<Triangle>(1000);
    static Set<Triangle> twoPlusT = new HashSet<Triangle>(1000);


    static HashMap<String, Edge> edges = new HashMap<String, Edge>(1000);



    public BalancedTrangleCountWithViz(TriangleCountView view) {
        this.view = view;
    }


    public void calculate(Edge e) {


        if (e.operation == Edge.ADD) {

            if (edgeSet.contains(e)) {
               return;
            } else {


                if (edgeMap.get(e.source) != null && edgeMap.get(e.sink) != null) {


                    Sets.SetView<Integer> setView = Sets.intersection(edgeMap.get(e.source), edgeMap.get(e.sink));


                    for (int common : setView) {

                        Triangle t = new Triangle(e.source, e.sink, common);

                        if (!allPlusT.contains(t) && !allNegT.contains(t) && !onePlusT.contains(t) && ! twoPlusT.contains(t)) {


                            Edge e1 = edges.get("" + e.source + "-" + common);

                            if (e1 == null) {
                                e1 = edges.get("" + common + "-" + e.source);
                            }


                            Edge e2 = edges.get("" + e.sink + "-" + common);

                            if (e2 == null) {
                                e2 = edges.get("" + common + "-" + e.sink);
                            }



                            int switchKey = e.sign  +  e1.sign + e2.sign;



                            switch(switchKey) {

                                case 3: {
                                    allPlusT.add(t);
                                    view.updateBalanced(allPlusT.size() + allNegT.size() + onePlusT.size());
                                    view.updateUnBalanced(twoPlusT.size());
                                    break;
                                }

                                case -3: {
                                    allNegT.add(t);
                                    view.updateBalanced(allPlusT.size() + allNegT.size() + onePlusT.size());
                                    view.updateUnBalanced(twoPlusT.size());
                                    break;
                                }

                                case 1: {
                                    twoPlusT.add(t);
                                    view.updateBalanced(allPlusT.size() + allNegT.size() + onePlusT.size());
                                    view.updateUnBalanced(twoPlusT.size());
                                    break;
                                }

                                case -1: {
                                    onePlusT.add(t);
                                    view.updateBalanced(allPlusT.size() + allNegT.size() + onePlusT.size());
                                    view.updateUnBalanced(twoPlusT.size());
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
               return;
            }

            if (edgeMap.get(e.source) != null && edgeMap.get(e.sink) != null) {


                Sets.SetView<Integer> setView = Sets.intersection(edgeMap.get(e.source), edgeMap.get(e.sink));

                for (int common : setView) {

                    Triangle t = new Triangle(e.source, e.sink, common);

                    if(allPlusT.contains(t)) {
                        allPlusT.remove(t);
                        view.updateBalanced(allPlusT.size() + allNegT.size() + onePlusT.size());
                        view.updateUnBalanced(twoPlusT.size());
                    }

                    if(allNegT.contains(t)) {
                        allNegT.remove(t);
                        view.updateBalanced(allPlusT.size() + allNegT.size() + onePlusT.size());
                        view.updateUnBalanced(twoPlusT.size());
                    }

                    if(onePlusT.contains(t)) {
                        onePlusT.remove(t);
                        view.updateBalanced(allPlusT.size() + allNegT.size() + onePlusT.size());
                        view.updateUnBalanced(twoPlusT.size());
                    }

                    if(twoPlusT.contains(t)) {
                        twoPlusT.remove(t);
                        view.updateBalanced(allPlusT.size() + allNegT.size() + onePlusT.size());
                        view.updateUnBalanced(twoPlusT.size());
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
               return;
            }

            Set<Integer> sourceSet = edgeMap.get(e.source);
            Set<Integer> sinkSet = edgeMap.get(e.sink);


            if (sourceSet != null && sinkSet != null) {


                Sets.SetView<Integer> setView = null;
                if(sourceSet.size() <= sourceSet.size()) {
                    setView= Sets.intersection(sourceSet, sinkSet);
                } else {
                    setView = Sets.intersection(sinkSet,sourceSet);
                }

                for (int common : setView) {

                    Triangle t = new Triangle(e.source, e.sink, common);

                    int switchKey = -1;

                    if(allPlusT.contains(t)) {
                        switchKey = 1; // All + -> two +
                        allPlusT.remove(t);

                        view.updateBalanced(allPlusT.size() + allNegT.size() + onePlusT.size());
                        view.updateUnBalanced(twoPlusT.size());
                    }

                    if(allNegT.contains(t)) {
                        switchKey = 2; // All - -> one +
                        allNegT.remove(t);
                        view.updateBalanced(allPlusT.size() + allNegT.size() + onePlusT.size());
                        view.updateUnBalanced(twoPlusT.size());
                    }

                    if(onePlusT.contains(t)) {
                        if(e.sign == Edge.MINUS) {
                            switchKey = 3; // One + -> All -
                        } else {
                            switchKey = 1; // One + -> Two +
                        }
                        onePlusT.remove(t);
                        view.updateBalanced(allPlusT.size() + allNegT.size() + onePlusT.size());
                        view.updateUnBalanced(twoPlusT.size());
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
                            view.updateBalanced(allPlusT.size() + allNegT.size() + onePlusT.size());
                            view.updateUnBalanced(twoPlusT.size());
                            break;
                        }
                        case 2: {
                            onePlusT.add(t);
                            view.updateBalanced(allPlusT.size() + allNegT.size() + onePlusT.size());
                            view.updateUnBalanced(twoPlusT.size());
                            break;
                        }
                        case 3: {
                            allNegT.add(t);
                            view.updateBalanced(allPlusT.size() + allNegT.size() + onePlusT.size());
                            view.updateUnBalanced(twoPlusT.size());
                            break;
                        }
                        case 4: {
                            allPlusT.add(t);
                            view.updateBalanced(allPlusT.size() + allNegT.size() + onePlusT.size());
                            view.updateUnBalanced(twoPlusT.size());
                            break;
                        }
                    }


                }


            }


        }




    }
}
