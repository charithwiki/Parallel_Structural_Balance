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

import edu.usc.ee599.Edge;
import org.graphstream.algorithm.generator.BarabasiAlbertGenerator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
/**
 * Created by Charith Wickramaarachchi on 11/27/14.
 */
public class GraphStreamGenerator {
	
/*	static byte[][] adj = new byte[102][102];
	
	public static void initializeAdjMatrix()
	{

		BufferedReader br = null;
 
		try {
 
		String line;
 
		br = new BufferedReader(new FileReader("sample-graph.csv"));
 
		while ((line = br.readLine()) != null) {
 
		   StringTokenizer stringTokenizer = new StringTokenizer(line, ",");
 
		   while (stringTokenizer.hasMoreElements())
		   {
 
		    int from = Integer.parseInt(stringTokenizer.nextElement().toString());
		    int to = Integer.parseInt(stringTokenizer.nextElement().toString());
			int sign = Integer.parseInt(stringTokenizer.nextElement().toString());
			String flag = stringTokenizer.nextElement().toString();
			String date = stringTokenizer.nextElement().toString();

			if(sign == 1)
			{
				adj[from][to]=adj[to][from] = 1; //since its an undirected graph we need to make (x,y) and (y,x) as true
				}
			else if(sign == -1) 
			{
				adj[from][to]=adj[to][from] = -1; //since its an undirected graph we need to make (x,y) and (y,x) as true
				}
			else
			{
				adj[from][to]=adj[to][from] = 0; //since its an undirected graph we need to make (x,y) and (y,x) as true
			}
		   }
		} 
		br.close();
	} catch (IOException e) {
		e.printStackTrace();
	} finally {
		try {
			if (br != null)
				br.close();
 
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
		
	}
	
	public static int isEdge(int newFromNode, int newToNode)
	{
		return adj[newFromNode][newToNode];
	}	

	public static String newEdgeData(int from, int to, int rn)
	{
	StringBuffer buf = new StringBuffer();
		if (rn < 40)
			{
				adj[from][to]=adj[to][from] = -1;
				buf.append(from+","+to+",-1,a");
			}
		else
			{
				adj[from][to]=adj[to][from] = 1;
				buf.append(from+","+to+",1,a");
			}

		return buf.toString();
	}

	public static String updateEdgeSign(int from, int to)
	{
	StringBuffer buf = new StringBuffer();
		if (adj[from][to] == -1)
			{
				adj[from][to]=adj[to][from] = 1;
				buf.append(from+","+to+",1,u");
			}
		else
			{
				adj[from][to]=adj[to][from] = -1;
				buf.append(from+","+to+",-1,u");
			}

		return buf.toString();
	}

	public static String deleteEdge(int oldFromNode,int oldToNode)
	{
		//since its an undirected graph we need to make (x,y) and (y,x) as true
		adj[oldFromNode][oldToNode]=adj[oldToNode][oldFromNode] = 0;

			StringBuffer buf = new StringBuffer();
				
		return buf.append(oldFromNode+","+oldToNode+",0,d").toString();
	}*/

    public static void main(String[] args) throws Exception{
        final Graph graph = new SingleGraph("Barabàsi-Albert");
        final BarabasiAlbertGenerator gen = new BarabasiAlbertGenerator(2);
        gen.setExactlyMaxLinksPerStep(true);

        List<Edge> edgeList = new ArrayList<Edge>(110);
        
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
           
            double rand = Math.random();
            
            Edge el = new Edge(
            		Integer.parseInt(e.getSourceNode().getId()),
            		Integer.parseInt(e.getTargetNode().getId()),
            		(rand<0.5)?Edge.PLUS:Edge.MINUS,Edge.ADD,false);
            edgeList.add(el);
            writer.println("" + e.getSourceNode() + ","  + e.getTargetNode() + "," + el.getSign() +",a");
        }
                
        Random randG = new Random();
        for(int i =0; i < 20;i++) {
        	double rand = Math.random();
            int idx = randG.nextInt(edgeList.size());
            Edge e1;
            
            if(rand < 0.5) {
            	//Remove
            	e1 = edgeList.remove(idx);
            	//Write to File
            	writer.println("" + e1.getSource() + ","  + e1.getSink() + "," + e1.getSign() +",d");
            } else {
            	//Change Sign
            	e1 = edgeList.get(idx);
            	
            	if(e1.getSign()== 1) {
            	//Write to File
            		e1.setSign(Edge.MINUS);
            	}
            	else {
            		e1.setSign(Edge.PLUS);
                	}
            	writer.println("" + e1.getSource() + ","  + e1.getSink() + "," + e1.getSign() +",u");
            }
            
        	
        }
        
        

        writer.flush();
        writer.close();

    }
}
