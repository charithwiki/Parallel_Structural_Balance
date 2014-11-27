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

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Created by Charith Wickramaarachchi on 11/27/14.
 */
public class SimpleGraphViz {

    public static void main(String[] args) throws Exception{

        BufferedReader reader = new BufferedReader(new FileReader("sample-graph.csv"));


        Graph graph = new SingleGraph("tutorial 1");

        graph.setStrict(false);
        graph.setAutoCreate(true);
        graph.display();


        System.out.println("hello");
        String line = reader.readLine();

        while (line != null) {

            String[] parts = line.split(",");


            Thread.sleep(300);

            if("1".equals(parts[2]))
                graph.addEdge(parts[0] + parts[1], parts[0], parts[1]).
                        addAttribute("ui.style", "fill-color: rgb(0,100,255);");
            else
                graph.addEdge(parts[0] + parts[1], parts[0], parts[1]).
                        addAttribute("ui.style", "fill-color: rgb(255,100,0);");
            line = reader.readLine();
        }





    }
}
