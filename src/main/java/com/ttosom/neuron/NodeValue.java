package com.ttosom.neuron;

import java.io.Serializable;

/**
 *   Tree-based Topology-Oriented SOM: Java implementation and R binding
 *   Copyright (C) 2013  Gonzalo Maldonado, Cesar A. Astudillo
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

public class NodeValue implements Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int key;
    private int value;
    
    public NodeValue(int key, int value){
        this.key = key;
        this.value = value;
    }

   public NodeValue(){
	   
   }

public int getKey() {
	return key;
}

public void setKey(int key) {
	this.key = key;
}

public int getValue() {
	return value;
}

public void setValue(int value) {
	this.value = value;
}

@Override
public String toString() {
	return "NodeValue [key=" + key + ", value=" + value + "]";
}


   
   

}