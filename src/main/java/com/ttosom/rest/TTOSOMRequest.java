package com.ttosom.rest;

import java.io.Serializable;
import java.util.List;

import com.ttosom.distance.DistanceEnum;
import com.ttosom.neuron.NodeValue;

public class TTOSOMRequest implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	
	private String dataSetUrl;
	
	private List<NodeValue> treeArray;
	
	private int iterations;
	
	private int initialRadius;
	
	private int finalRadius;
	
	private int initialLearningRate;
	
	private int finalLearningRate;
	
	private DistanceEnum distance;

	public String getDataSetUrl() {
		return dataSetUrl;
	}

	public void setDataSetUrl(String dataSetUrl) {
		this.dataSetUrl = dataSetUrl;
	}

	public List<NodeValue> getTreeArray() {
		return treeArray;
	}

	public void setTreeArray(List<NodeValue> treeArray) {
		this.treeArray = treeArray;
	}

	public int getIterations() {
		return iterations;
	}

	public void setIterations(int iterations) {
		this.iterations = iterations;
	}

	public int getInitialRadius() {
		return initialRadius;
	}

	public void setInitialRadius(int initialRadius) {
		this.initialRadius = initialRadius;
	}

	public int getFinalRadius() {
		return finalRadius;
	}

	public void setFinalRadius(int finalRadius) {
		this.finalRadius = finalRadius;
	}

	public int getInitialLearningRate() {
		return initialLearningRate;
	}

	public void setInitialLearningRate(int initialLearningRate) {
		this.initialLearningRate = initialLearningRate;
	}

	public int getFinalLearningRate() {
		return finalLearningRate;
	}

	public void setFinalLearningRate(int finalLearningRate) {
		this.finalLearningRate = finalLearningRate;
	}

	public DistanceEnum getDistance() {
		return distance;
	}

	public void setDistance(DistanceEnum distance) {
		this.distance = distance;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	

}
