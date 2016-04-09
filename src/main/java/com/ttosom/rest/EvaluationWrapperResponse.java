package com.ttosom.rest;

import weka.classifiers.Evaluation;

public class EvaluationWrapperResponse {

	private String classDetails;
	private String matrix;
	private String summary;

	public EvaluationWrapperResponse(Evaluation evaluationFromWeka) throws Exception {
		summary = evaluationFromWeka.toSummaryString();
		classDetails = evaluationFromWeka.toClassDetailsString();
		matrix = evaluationFromWeka.toMatrixString();
	}

	public String getClassDetails() {
		return classDetails;
	}

	public String getMatrix() {
		return matrix;
	}

	public String getSummary() {
		return summary;
	}

	public void setClassDetails(String classDetails) {
		this.classDetails = classDetails;
	}

	public void setMatrix(String matrix) {
		this.matrix = matrix;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

}
