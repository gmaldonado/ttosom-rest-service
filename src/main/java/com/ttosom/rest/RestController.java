package com.ttosom.rest;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ttosom.TTOSOM;
import com.ttosom.distance.Distance;
import com.ttosom.neuron.Neuron;
import com.ttosom.neuron.NodeValue;

import weka.classifiers.Evaluation;
import weka.core.Instances;
import static com.ttosom.TTOSOMUtils.*;


@Controller
@RequestMapping(value = "/ttosom")
public class RestController{
	
	private TTOSOM ttosom;
	private Instances dataSet;
 
	private  void initializeTTOSOM(TTOSOMRequest jsonRequest) throws Exception{
		Map<String, Distance> distances = initializeDistancesMap();
		
		dataSet = readArffFromURL(jsonRequest.getDataSetUrl()); 
		
		Distance distanceFunction = distances.get(jsonRequest.getDistanceFunction());
		int initialRadius = jsonRequest.getInitialRadius();
		int finalRadius = jsonRequest.getFinalRadius();
		double initialLearningRate = jsonRequest.getInitialLearningRate();
		double finalLearningRate = jsonRequest.getFinalLearningRate();
		int iterations = jsonRequest.getIterations();
		
		List<NodeValue> treeAsArray = initializeTreeAsArray(jsonRequest.getTreeAsArray());
		
		ttosom = new TTOSOM(dataSet, initialLearningRate, initialRadius, finalLearningRate, finalRadius, iterations, distanceFunction, false, new Random());
		ttosom.describeTopology(treeAsArray);
		ttosom.buildClassifier(dataSet);
	}
	
	@RequestMapping(value = "/", method=RequestMethod.POST)
	public ResponseEntity<List<Neuron>> callRegularFlow(@RequestBody TTOSOMRequest jsonRequest) throws Exception {
		initializeTTOSOM(jsonRequest);
		List<Neuron> neurons = ttosom.getNeuronList();
		return new ResponseEntity<>(neurons,HttpStatus.OK); 
	}
	
	
	//Here we need to see what is the real information that we want to get from cross validation
	@RequestMapping(value = "/crossValidation", method=RequestMethod.POST)
	public ResponseEntity<EvaluationWrapperResponse> crossValidation(@RequestBody TTOSOMcrossValidationRequest jsonRequest) throws Exception {
		
		initializeTTOSOM(jsonRequest);

		int folds = jsonRequest.getFolds();
		int seedValue = jsonRequest.getSeedValue();
		
		Evaluation evaluation = new Evaluation(dataSet);
		evaluation.crossValidateModel(ttosom, dataSet,folds, new Random(seedValue));
		
		EvaluationWrapperResponse responseAsJson = new EvaluationWrapperResponse(evaluation);
		
		return new ResponseEntity<>(responseAsJson,HttpStatus.OK); 
	}
	
}