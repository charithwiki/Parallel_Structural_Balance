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

/**
 * Created by Charith Wickramaarachchi on 11/16/14.
 */
public class Edge {
    public int source;
    public int sink;

    public int sign;

    public int operation;

    public static final int PLUS=1;
    public static final int MINUS=-1;


    public static final int ADD=2;

    public static final int REMOVE=-2;

    public static final int UPDATE= 0;


    boolean directed;

    public Edge(int source, int sink, boolean directed) {
        this.source = source;
        this.sink = sink;
        this.directed = directed;
    }

    public Edge(int source, int sink, int sign, int operation, boolean directed) {
        this.source = source;
        this.sink = sink;
        this.sign = sign;
        this.operation = operation;
        this.directed = directed;
    }

    @Override
    public boolean equals(Object obj) {

        if(obj instanceof Edge) {
            Edge edge = (Edge) obj;
            return directed?((this.source == edge.source) && (this.sink == edge.sink)): (
                    (this.source == edge.source) && (this.sink == edge.sink) ||(this.source == edge.sink) && (this.sink == edge.source)
            );

        } else {
            return false;
        }

    }

    @Override
    public String toString() {
        return "Edge{" +
                "source=" + source +
                ", sink=" + sink +
                ", sign=" + sign +
                ", operation=" + operation +
                ", directed=" + directed +
                '}';
    }

	public int getSource() {
		return source;
	}

	public int getSink() {
		return sink;
	}

	public int getSign() {
		return sign;
	}

	public int getOperation() {
		return operation;
	}

	public boolean isDirected() {
		return directed;
	}

	public void setSink(int sink) {
		this.sink = sink;
	}

	public void setSign(int sign) {
		this.sign = sign;
	}

	public void setOperation(int operation) {
		this.operation = operation;
	}

	public void setDirected(boolean directed) {
		this.directed = directed;
	}
	
	
    
    
}
