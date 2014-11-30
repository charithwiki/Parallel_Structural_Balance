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

/**
 * Created by Charith Wickramaarachchi on 11/30/14.
 */
public class Analytics {

    public static void main(String[] args) throws Exception{
        SignedTriangleCountingInDynamicGraph.run("rfa_all.csv","rfa_all_results.txt");
        SignedTriangleCountingInDynamicGraph.run("soc-sign-epinions.csv","soc-sign-epinions_results.txt");
        SignedTriangleCountingInDynamicGraph.run("soc-sign-Slashdot081106.csv","soc-sign-Slashdot081106_results.txt");
        SignedTriangleCountingInDynamicGraph.run("soc-sign-Slashdot090216.csv","soc-sign-Slashdot090216_results.txt");
        SignedTriangleCountingInDynamicGraph.run("soc-sign-Slashdot090221.csv","soc-sign-Slashdot090221_results.txt");
    }
}
