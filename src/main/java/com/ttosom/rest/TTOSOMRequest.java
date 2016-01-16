package com.ttosom.rest;

import java.io.Serializable;
import java.util.List;

import com.ttosom.neuron.NodeValue;

public class TTOSOMRequest implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	
	private String dataSetUrl;
	
	private List<NodeValue> treeAsArray;
	
	private int iterations;
	
	private int initialRadius;
	
	private int finalRadius;
	
	private int initialLearningRate;
	
	private int finalLearningRate;
	
	private String distance;

	public String getDataSetUrl() {
		return dataSetUrl;
	}

	public void setDataSetUrl(String dataSetUrl) {
		this.dataSetUrl = dataSetUrl;
	}

	public List<NodeValue> getTreeAsArray() {
		return treeAsArray;
	}

	public void setTreeAsArray(List<NodeValue> treeAsArray) {
		this.treeAsArray = treeAsArray;
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

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	

}
