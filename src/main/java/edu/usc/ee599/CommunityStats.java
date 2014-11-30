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

import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;

import java.io.*;

/**
 * Created by Charith Wickramaarachchi on 11/30/14.
 */
public class CommunityStats {

    public static void main(String[] args) throws Exception{

        File dir = new File("results5");
        PrintWriter writer = new PrintWriter(new FileWriter("results5_stats.txt"));

        File[] files = dir.listFiles();

        DescriptiveStatistics statistics1 = new DescriptiveStatistics();
        DescriptiveStatistics statistics2 = new DescriptiveStatistics();
        for(File file : files) {

            BufferedReader reader = new BufferedReader(new FileReader(file));

            String line1 = reader.readLine();
            String line2 = reader.readLine();

            int balanced = Integer.parseInt(line1.split(",")[1]);
            int unbalanced = Integer.parseInt(line2.split(",")[1]);


            double bp = (double)balanced/(double)(balanced+unbalanced);
            double up = (double)unbalanced/(double)(balanced+unbalanced);

            statistics1.addValue(bp);
            statistics2.addValue(up);

        }


        writer.println("AVG Balanced %: " + statistics1.getMean());
        writer.println("AVG Unbalanced %: " + statistics2.getMean());

        writer.println("STD Balanced %: " + statistics1.getStandardDeviation());
        writer.println("STD Unbalanced %: " + statistics2.getStandardDeviation());

        writer.flush();
        writer.close();

    }
}
