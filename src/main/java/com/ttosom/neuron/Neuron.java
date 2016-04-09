package com.ttosom.neuron;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.fasterxml.jackson.annotation.JsonIgnore;

import weka.core.Instance;
import weka.core.Instances;

public class Neuron implements Serializable {

	private static final long serialVersionUID = 1L;
	private ArrayList<Neuron> children;
	private int id;
	private Neuron parent;
	private Instance weight;

	public Neuron(Neuron parent, List<NodeValue> elements, Instances dataSet) {
		final int childrenCount = elements.get(0).getValue();
		final Random random = new Random();

		/*
		 * You should do a copy instead of reference the instance, because if
		 * you reference the instance, then when you modify the vector in the
		 * update rule, you will be modifying the original data set and this
		 * could cause chaos, so it's VERY IMPORTANT to copy the object, that
		 * it's the same as the method clone in Java
		 */
		weight = (Instance) dataSet.instance(random.nextInt(dataSet.numInstances())).copy();
		children = new ArrayList<Neuron>();
		this.parent = parent;
		id = elements.get(0).getKey();

		elements.remove(0);

		for (int i = 0; i < childrenCount; i++) {
			children.add(new Neuron(this, elements, dataSet));
		}

	}

	@JsonIgnore
	public ArrayList<Neuron> getChildren() {
		return children;
	}

	@JsonIgnore
	public int getId() {
		return id;
	}

	@JsonIgnore
	public Neuron getParent() {
		return parent;
	}

	@JsonIgnore
	public Instance getWeight() {
		return weight;
	}

	public String getWeightWithoutClass() {
		String weightWithoutClass = "";
		for (int i = 0; i < weight.numAttributes() - 1; i++) {
			weightWithoutClass += weight.value(i);
			if (i != weight.numAttributes() - 2) {
				weightWithoutClass += ",";
			}
		}
		return weightWithoutClass;
	}

	public void setChildren(ArrayList<Neuron> children) {
		this.children = children;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setParent(Neuron parent) {
		this.parent = parent;
	}

	@JsonIgnore
	public void setWeight(Instance weight) {
		this.weight = weight;
	}

}
