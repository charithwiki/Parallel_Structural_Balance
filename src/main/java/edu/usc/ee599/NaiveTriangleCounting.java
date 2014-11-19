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
package edu.usc.ee599;

import java.util.*;
import com.google.common.collect.Sets;

/**
 * Created by Charith Wickramaarachchi on 11/16/14.
 * A Naive implementation of Streaming Triangle Count
 */
public class NaiveTriangleCounting {


    static Set<Edge> edgeSet = new HashSet<Edge>(1000);

    static HashMap<Integer,Set<Integer>> edgeMap = new HashMap<Integer, Set<Integer>>();

    static Set<Triangle> triangles = new HashSet<Triangle>(1000);

    static int count =0;

    public static void main(String[] args) throws Exception {


        GraphGenerator generator = new SimpleGraphGenerator("/home/charith/Downloads/Email-Enron.txt");

        generator.initialize();

        long startTime = System.currentTimeMillis();

        Edge e = generator.nextEdge();
        int edgeCount = 0;
        while (e != null) {
            edgeCount++;
            if(edgeSet.contains(e)) {
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

                        }

                    }

                }

              edgeSet.add(e);




                if(edgeMap.containsKey(e.source)) {

                    edgeMap.get(e.source).add(e.sink);

                } else {
                    Set<Integer> set = new HashSet<Integer>();
                    set.add(e.sink);

                    edgeMap.put(e.source,set);
                }

                if(edgeMap.containsKey(e.sink)) {

                    edgeMap.get(e.sink).add(e.source);

                } else {
                    Set<Integer> set = new HashSet<Integer>();
                    set.add(e.source);

                    edgeMap.put(e.sink,set);
                }


            }

            e = generator.nextEdge();
        }


        long endTime = System.currentTimeMillis();


        System.out.println(" Time Spent(ms) : " + (endTime - startTime));

        System.out.println("Rate (edges/sec):" + (double)(edgeCount*1000)/(double)(endTime - startTime));

        System.out.println(" Triangle Count: " + count);


    }




}
