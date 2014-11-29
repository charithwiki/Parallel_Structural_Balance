package edu.usc.ee599;

import com.google.common.collect.Sets;

import java.util.*;

/**
 * Created by charith on 11/18/14.
 */
public class ApproximateStreamingTCount {

    private static final int SE=500;

    private static Edge[] edgesRes = new Edge[SE];

    private static Wedge[] wedges = new Wedge[SE];

    private static List<Wedge> realWedges = new ArrayList<Wedge>();

    private static boolean[] isClosed = new boolean[SE];

    private static int totalWedges = 0;

    private static double p = 0.0;

    private static Random randomGen = new Random();


    private static Map<Integer,Integer> degrees = new HashMap<Integer, Integer>();

    private static Map<Integer,List<Integer>> adjList = new HashMap<Integer, List<Integer>>();


    public static void main(String[] args) throws Exception{

        GraphGenerator generator = new SimpleGraphGenerator("/home/charith/Downloads/Email-Enron.txt");

        generator.initialize();

        long startTime = System.currentTimeMillis();

        int T=0;

        Edge e = generator.nextEdge();
        int edgeCount = 0;


        while(e != null) {
            edgeCount++;
            update(e,edgeCount);
            T = (int) ((p*Math.pow(edgeCount,2)/(SE*(SE-1)))*totalWedges);
            e = generator.nextEdge();
        }


        long endTime = System.currentTimeMillis();


        System.out.println(" Time Spent(ms) : " + (endTime - startTime));

        System.out.println("Rate (edges/sec):" + (double)(edgeCount*1000)/(double)(endTime - startTime));

        System.out.println(" Triangle Count: " + T);


    }


    public static void  update(Edge e,int t){



        for(int i=0; i < wedges.length;i++) {

            if(wedges[i] != null) {
                if((e.source == wedges[i].A && e.sink == wedges[i].C) ||
                        (e.sink == wedges[i].A && e.source == wedges[i].C)) {
                    isClosed[i] = true;
                } else {
                    isClosed[i] = false;
                }
            } else {
                isClosed[i] = false;
            }

        }


        double flipCount = Math.random();


        double headProb = 1 - Math.pow((1- 1/t),SE);

        if( flipCount > headProb) {
            //Got tail
            return;
        }

        int idx = randomGen.nextInt(SE);

        Edge toRemove = edgesRes[idx];

        edgesRes[idx] = e;

        //5 Update tot wedges, the number of wedges formed by edge res.
        totalWedges = totalWedges + NumberOfNewWedges(e) - NumberOfNewWedges(toRemove);


        if(toRemove != null) {
            if(degrees.containsKey(toRemove.source)) {
                degrees.put(toRemove.source, degrees.get(toRemove.source) -1);
                adjList.get(toRemove.source).remove(toRemove.sink);
            }

            if(degrees.containsKey(toRemove.sink)) {
                degrees.put(toRemove.sink, degrees.get(toRemove.sink) -1);
                adjList.get(toRemove.sink).remove(toRemove.source);

            }


        }

        if(degrees.containsKey(e.source)) {
            degrees.put(e.source, degrees.get(e.source) + 1);
            adjList.get(e.source).add(e.sink);
        } else {

            degrees.put(e.source, 1);
            ArrayList<Integer> temp = new ArrayList<Integer>();
            temp.add(e.sink);
            adjList.put(e.source,temp);
        }

        if(degrees.containsKey(e.sink)) {
            degrees.put(e.sink, degrees.get(e.sink) + 1);
            adjList.get(e.sink).add(e.source);
        } else {
            degrees.put(e.sink, 1);
            ArrayList<Integer> temp = new ArrayList<Integer>();
            temp.add(e.source);
            adjList.put(e.sink,temp);
        }

      //4 Determine Nt and let new wedges = |Nt|.
        int new_wedges = NumberOfNewWedges(e);

      //  6 Set q = new wedges/tot wedges.


        double q = (double)new_wedges/(double)totalWedges;
//
//        7 For each index i ∈ [sw],
//        8 Flip coin with heads probability q.
//        9 If tails, continue to next index in loop.
//        10 Pick uniform random w ∈ Nt that involves et.
//        11 Replace wedge res[i] = w. Reset isClosed[i] = false.



        for(int i=0; i < SE; i++) {

            double flip = Math.random();

            if(flip >= q) {
                continue;
            }

            Wedge w = findRandomWedge(e);

            if(w != null) {
                wedges[i] = w;
                isClosed[i] = false;
            }

        }


        int fraction = 0;
        for(int i = 0; i < isClosed.length;i++) {

            if(isClosed[i]) {
                fraction++;
            }
        }


        p = (double)fraction/(double)isClosed.length;

    }


    private static int NumberOfNewWedges(Edge e) {

        int sd = 0;

        int td = 0;

        if(degrees.containsKey(e.source)) {
            sd = degrees.get(e.source);
        }

        if(degrees.containsKey(e.sink)) {
            td = degrees.get(e.sink);
        }

        return sd + td;

    }

    private static Wedge findRandomWedge(Edge e) {

        List<Integer> listOne = adjList.get(e.source);



        List<Integer> listTwo = adjList.get(e.sink);



        if(listOne != null && listTwo != null) {
            int idx = randomGen.nextInt(listOne.size() + listTwo.size());
            if(idx >= listOne.size()) {
               int we =  listTwo.get(idx - listOne.size());

                return  new Wedge(e.source,e.sink,we);

            } else {
                int we =  listOne.get(idx);

                return  new Wedge(e.sink,e.source,we);
            }
        } else if (listOne != null) {
            int idx = randomGen.nextInt(listOne.size());
            int we =  listOne.get(idx);

            return  new Wedge(e.sink,e.source,we);

        } else if( listTwo != null) {
            int idx = randomGen.nextInt(listOne.size());
            int we =  listTwo.get(idx - listOne.size());
            return  new Wedge(e.source,e.sink,we);
        }

        return null;





    }

}
