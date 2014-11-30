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
package edu.usc.ee599.viz;

import javax.swing.*;

/**
 * Created by Charith Wickramaarachchi on 11/29/14.
 */
public class TriangleCountView {
    private JLabel balanced;
    private JLabel unbalanced;
    private JPanel panel;
    private JFrame frame;

    public TriangleCountView() {

    }

    public void show(TriangleCountView view) {
        frame = new JFrame("TriangleCountView");

        frame.setContentPane(view.panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) throws Exception{

        TriangleCountView view = new TriangleCountView();
        view.show(view);
    }



    public void updateBalanced(int count) {

        balanced.setText("" + count);    }

    public void  updateUnBalanced(int count) {
        unbalanced.setText(""+count);
    }


}
