package edu.usc.ee599;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

/**
 * Created by charith on 11/24/14.
 */
public class SignedGraphGenerator implements GraphGenerator {

    String filePath;


    private ArrayList<Edge> edges = new ArrayList<Edge>(1000);

    private static final Pattern SEPARATOR = Pattern.compile(",");

    private int index=0;

    public SignedGraphGenerator(String filePath) {
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

            String[] tokens = SEPARATOR.split(line.toString());

            if(tokens.length ==2) {

            }else if(tokens.length == 5) {
                Edge e = new Edge(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]), "a".equals(tokens[3])?Edge.ADD:Edge.REMOVE,false);
                edges.add(e);
            } else {
                throw new RuntimeException("Unexpected file format");
            }
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
