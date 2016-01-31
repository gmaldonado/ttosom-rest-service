package com.ttosom.rest;

import weka.classifiers.Evaluation;

public class EvaluationWrapperResponse {

	private String summary;
	private String classDetails;
	private String matrix;
	
	public EvaluationWrapperResponse(Evaluation evaluationFromWeka) throws Exception{
		this.summary = evaluationFromWeka.toSummaryString();
		this.classDetails = evaluationFromWeka.toClassDetailsString();
		this.matrix = evaluationFromWeka.toMatrixString();
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getClassDetails() {
		return classDetails;
	}

	public void setClassDetails(String classDetails) {
		this.classDetails = classDetails;
	}

	public String getMatrix() {
		return matrix;
	}

	public void setMatrix(String matrix) {
		this.matrix = matrix;
	}
	
	
	
}
