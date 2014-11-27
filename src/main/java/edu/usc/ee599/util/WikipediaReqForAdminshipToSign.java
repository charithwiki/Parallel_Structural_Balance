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

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by Charith Wickramaarachchi on 11/27/14.
 */
public class WikipediaReqForAdminshipToSign {


    public static void main(String[] args) throws Exception {

        BufferedReader reader = new BufferedReader(new FileReader("/home/charith/Downloads/rfa_all.NL-SEPARATED.txt"));

        PrintWriter writer = new PrintWriter(new BufferedOutputStream(new FileOutputStream("rfa_all.csv")));

        Map<String,Integer> nameToVertex = new HashMap<String, Integer>();

        String line = reader.readLine();


        int vid =0;
        while (line != null) {


            int source = -1;

            int sink =-1;

            int sign =-100;

            do {

                String parts[] = line.split(":");
                if(parts.length < 2) {
                  line = reader.readLine();
                    if(line == null) {
                        break;
                    }
                  continue;
                }
                //System.out.println(line);
                if("SRC".equals(parts[0].trim())) {
                    if(nameToVertex.containsKey(parts[1].trim())) {
                        source = nameToVertex.get(parts[1].trim());
                    } else {
                        source = vid++;
                        nameToVertex.put(parts[1].trim(),source);
                    }
                } else if ("TGT".equals(parts[0].trim())) {

                    if(nameToVertex.containsKey(parts[1].trim())) {
                        sink = nameToVertex.get(parts[1].trim());
                    } else {
                        sink = vid++;
                        nameToVertex.put(parts[1].trim(),sink);
                    }

                } else if ("VOT".equals(parts[0].trim())) {
                    sign = Integer.parseInt(parts[1]);
                    sign = sign ==0?1:sign;
                }

                line = reader.readLine();

                if(line == null) {
                    break;
                }
            } while (!line.isEmpty());


            if(source != -1 && sink != -1 && sign != -100)
                writer.println("" + source + "," + sink + "," + sign + "," + "a");

            line = reader.readLine();

        }

        writer.flush();
        writer.close();

    }

}
