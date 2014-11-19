package edu.usc.ee599;

import com.google.common.collect.Sets;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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




    public static void main(String[] args) throws Exception{

        GraphGenerator generator = new SimpleGraphGenerator("/home/charith/Downloads/Email-Enron.txt");

        generator.initialize();

        long startTime = System.currentTimeMillis();

        int T=0;

        Edge e = generator.nextEdge();
        int edgeCount = 0;


        while(e != null) {
            edgeCount++;
            double p = update(e,edgeCount);
            T = (int) ((p*Math.pow(edgeCount,2)/(SE*(SE-1)))*totalWedges);
            e = generator.nextEdge();
        }


        long endTime = System.currentTimeMillis();


        System.out.println(" Time Spent(ms) : " + (endTime - startTime));

        System.out.println("Rate (edges/sec):" + (double)(edgeCount*1000)/(double)(endTime - startTime));

        System.out.println(" Triangle Count: " + T);


    }


    public static double update(Edge e,int t){

        double p =0;

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


        boolean updated = false;
        for(int i=0; i < edgesRes.length;i++) {
            double x = Math.random();
            if(x <= ((double)1/(double)t)) {
                edgesRes[i] = e;
                updated = true;
            }
        }

        if(updated) {

        }




        return p;
    }


}
