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

import com.sun.corba.se.impl.orbutil.graph.Graph;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by Charith Wickramaarachchi on 11/16/14.
 */
public class SimpleGraphGenerator implements GraphGenerator {

    private String filePath;

    private ArrayList<Edge> edges = new ArrayList<Edge>(1000);

    private int index=0;

    public SimpleGraphGenerator(String filePath) {
        this.filePath = filePath;
    }


    public void initialize() throws Exception {


        BufferedReader reader = new BufferedReader(new FileReader(filePath));


        String line = reader.readLine();

        while (line != null) {
            if(line.startsWith("#") || "".equals(line.trim())) {
                line = reader.readLine();
                continue;
            }


            StringTokenizer tokenizer = new StringTokenizer(line);
            Edge e  = new Edge(Integer.parseInt(tokenizer.nextToken()), Integer.parseInt(tokenizer.nextToken()),false);
            edges.add(e);
            line = reader.readLine();
        }

    }

    public Edge nextEdge() {
        if(index > (edges.size() -1)) {
            return null;
        }

       return edges.get(index++);

    }
}
