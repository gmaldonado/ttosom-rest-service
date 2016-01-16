package com.ttosom.distance;

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

import java.io.Serializable;
import weka.core.EuclideanDistance;
import weka.core.Instance;
import weka.core.Instances;


public class NormalizedEuclideanDistance implements Distance, Serializable{

	private static final long serialVersionUID = 1L;

	public double calculateDistance(Instance item1, Instance item2,Instances trainingSet) {
        EuclideanDistance calculus = new EuclideanDistance(trainingSet);
        calculus.setDontNormalize(false);   
        return calculus.distance(item1, item2);    
    }
  
}