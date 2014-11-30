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

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Created by Charith Wickramaarachchi on 11/29/14.
 */
public class BreakToCommunity {

    public static void main(String[] args) throws Exception{

        BufferedReader mReader = new BufferedReader(new FileReader("/home/charith/Downloads/communities1.txt"));
        BufferedReader reader = new BufferedReader(new FileReader("soc-sign-epinions.csv"));

        Map<Integer,Integer> map = new HashMap<Integer,Integer>();


        String line = mReader.readLine();

        int vid =0;
        while (line != null) {

            int mapV = Integer.parseInt(line);
            map.put(vid, mapV);
            vid++;
            line = mReader.readLine();
        }



        HashMap<Integer,PrintWriter> writerHashMap = new HashMap<Integer, PrintWriter>();




        line = reader.readLine();

        while (line != null) {
            String[] parts = line.split(",");

            int s = Integer.parseInt(parts[0]);
            int t = Integer.parseInt(parts[1]);

            if(map.get(s) == map.get(t)) {

                if(writerHashMap.containsKey(map.get(s))) {
                    writerHashMap.get(map.get(s)).println(line);
                } else {
                    PrintWriter writer = new PrintWriter(new BufferedOutputStream(new FileOutputStream("dir2/comm_" +
                            map.get(s)+ ".csv")));
                    writerHashMap.put(map.get(s),writer);
                    writer.println(line);
                }

            }

            line = reader.readLine();
        }



        for (PrintWriter w: writerHashMap.values()) {
            w.flush();
            w.close();
        }











    }
}
