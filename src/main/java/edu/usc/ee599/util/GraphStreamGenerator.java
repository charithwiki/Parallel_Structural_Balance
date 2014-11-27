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
package edu.usc.ee599.util;

import org.graphstream.algorithm.generator.BarabasiAlbertGenerator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Iterator;

/**
 * Created by Charith Wickramaarachchi on 11/27/14.
 */
public class GraphStreamGenerator {

    public static void main(String[] args) throws Exception{
        final Graph graph = new SingleGraph("Barabàsi-Albert");
        final BarabasiAlbertGenerator gen = new BarabasiAlbertGenerator(2);
        gen.setExactlyMaxLinksPerStep(true);

        PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("sample-graph.csv")));

        gen.addSink(graph);
        gen.begin();
        for (int i = 0; i < 100; i++) {
            gen.nextEvents();
        }
        gen.end();




        Iterator edgeIterator = graph.getEdgeIterator();

        while (edgeIterator.hasNext()) {
            org.graphstream.graph.Edge e = (org.graphstream.graph.Edge)edgeIterator.next();
            writer.println("" + e.getSourceNode() + ","  + e.getTargetNode() + "," + (Math.random() > 0.4?1:-1));
        }
        writer.flush();
        writer.close();

    }
}
