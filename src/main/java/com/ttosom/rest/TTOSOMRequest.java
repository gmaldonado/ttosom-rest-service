package com.ttosom.rest;

import java.io.Serializable;


public class TTOSOMRequest implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	
	private String dataSetUrl;
	
	private int[] treeAsArray;
	
	private int iterations;
	
	private int initialRadius;
	
	private int finalRadius;
	
	private double initialLearningRate;
	
	private double finalLearningRate;
	
	private String distanceFunction;

	public String getDataSetUrl() {
		return dataSetUrl;
	}

	public void setDataSetUrl(String dataSetUrl) {
		this.dataSetUrl = dataSetUrl;
	}

	public int[] getTreeAsArray() {
		return treeAsArray;
	}

	public void setTreeAsArray(int[] treeAsArray) {
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

	public double getInitialLearningRate() {
		return initialLearningRate;
	}

	public void setInitialLearningRate(double initialLearningRate) {
		this.initialLearningRate = initialLearningRate;
	}

	public double getFinalLearningRate() {
		return finalLearningRate;
	}

	public void setFinalLearningRate(double finalLearningRate) {
		this.finalLearningRate = finalLearningRate;
	}

	public String getDistanceFunction() {
		return distanceFunction;
	}

	public void setDistanceFunction(String distanceFunction) {
		this.distanceFunction = distanceFunction;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
	
	

}
