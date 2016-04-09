package com.ttosom.rest;

import java.io.Serializable;

public class BasicRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	protected String dataSetUrl;

	protected String distanceFunction;

	protected double finalLearningRate;

	protected int finalRadius;

	protected double initialLearningRate;

	protected int initialRadius;

	protected int iterations;

	protected int[] treeAsArray;

	public String getDataSetUrl() {
		return dataSetUrl;
	}

	public String getDistanceFunction() {
		return distanceFunction;
	}

	public double getFinalLearningRate() {
		return finalLearningRate;
	}

	public int getFinalRadius() {
		return finalRadius;
	}

	public double getInitialLearningRate() {
		return initialLearningRate;
	}

	public int getInitialRadius() {
		return initialRadius;
	}

	public int getIterations() {
		return iterations;
	}

	public int[] getTreeAsArray() {
		return treeAsArray;
	}

	public void setDataSetUrl(String dataSetUrl) {
		this.dataSetUrl = dataSetUrl;
	}

	public void setDistanceFunction(String distanceFunction) {
		this.distanceFunction = distanceFunction;
	}

	public void setFinalLearningRate(double finalLearningRate) {
		this.finalLearningRate = finalLearningRate;
	}

	public void setFinalRadius(int finalRadius) {
		this.finalRadius = finalRadius;
	}

	public void setInitialLearningRate(double initialLearningRate) {
		this.initialLearningRate = initialLearningRate;
	}

	public void setInitialRadius(int initialRadius) {
		this.initialRadius = initialRadius;
	}

	public void setIterations(int iterations) {
		this.iterations = iterations;
	}

	public void setTreeAsArray(int[] treeAsArray) {
		this.treeAsArray = treeAsArray;
	}

}
