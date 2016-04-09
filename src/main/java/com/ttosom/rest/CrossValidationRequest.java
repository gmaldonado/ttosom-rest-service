package com.ttosom.rest;

public class CrossValidationRequest extends BasicRequest {

	private static final long serialVersionUID = 1L;

	private int folds;
	private int seedValue;

	public int getFolds() {
		return folds;
	}

	public int getSeedValue() {
		return seedValue;
	}

	public void setFolds(int folds) {
		this.folds = folds;
	}

	public void setSeedValue(int seedValue) {
		this.seedValue = seedValue;
	}

}
