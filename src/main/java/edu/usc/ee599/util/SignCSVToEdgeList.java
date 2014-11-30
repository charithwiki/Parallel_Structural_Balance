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

/**
 * Created by Charith Wickramaarachchi on 11/29/14.
 */
public class SignCSVToEdgeList {

    public static void main(String[] args) throws Exception{

        BufferedReader reader = new BufferedReader(new FileReader("rfa_all.csv"));

        PrintWriter writer = new PrintWriter(new BufferedOutputStream(new FileOutputStream("rfa_all_el.txt")));


        String line = reader.readLine();

        while (line != null) {

            String[] parts = line.split(",");

            writer.println(parts[0] + "\t" + parts[1]);

            line = reader.readLine();

        }

        writer.flush();
        writer.close();

    }
}
