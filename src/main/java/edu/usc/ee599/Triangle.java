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

import java.util.Arrays;

/**
 * Created by Charith Wickramaarachchi on 11/16/14.
 */
public class Triangle {

    int A;

    int B;

    int C;

    public Triangle(int a, int b, int c) {
        A = a;
        B = b;
        C = c;
    }


    @Override
    public boolean equals(Object obj) {

        if(obj instanceof Triangle) {

            Triangle t = (Triangle)obj;

            if((this.A == t.A &&this.B == t.B && this.C == t.C) ||
                    (this.A == t.A &&this.B == t.C && this.C == t.B) ||
                    (this.A == t.C &&this.B == t.B && this.C == t.A) ||
                    (this.A == t.B &&this.B == t.A && this.C == t.C) ||
                    (this.A == t.C &&this.B == t.A && this.C == t.B) ||
                    (this.A == t.B &&this.B == t.C && this.C == t.A)) {
                return true;
            } else {
                return false;
            }

        } else {
            return false;
        }

    }

    @Override
    public int hashCode() {

        String original = "" + A + "" + B + "" + C;
        char[] chars = original.toCharArray();
        Arrays.sort(chars);
        return new String(chars).hashCode();

    }
}
