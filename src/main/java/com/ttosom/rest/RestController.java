package com.ttosom.rest;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ttosom.TTOSOM;
import com.ttosom.distance.Distance;
import com.ttosom.distance.NormalizedEuclideanDistance;
import com.ttosom.distance.NormalizedManhattanDistance;
import com.ttosom.neuron.Neuron;
import com.ttosom.neuron.NodeValue;

import weka.core.Instances;
import weka.core.converters.ArffLoader;

 
@Controller
public class RestController {
 
	
	@RequestMapping(value = "/ttosom", method=RequestMethod.POST)
	public ResponseEntity<List<Neuron>> callRegularFlow(@RequestBody TTOSOMRequest jsonRequest) throws Exception {
		Instances dataSet = readArff(jsonRequest.getDataSetUrl()); 
		
		Distance distance = initializeDistance(jsonRequest);
		
		double initialLearning = jsonRequest.getInitialLearningRate();
		double initialRadius = jsonRequest.getInitialRadius();
		double finalLearning = jsonRequest.getFinalLearningRate();
		double finalRadius = jsonRequest.getFinalRadius();
		int iterations = jsonRequest.getIterations();
		List<NodeValue> treeArray = jsonRequest.getTreeArray();
		
		TTOSOM ttosom = new TTOSOM(dataSet, initialLearning, initialRadius, finalLearning, finalRadius, iterations, distance, false, new Random());
		ttosom.describeTopology(treeArray);
		ttosom.buildClassifier(dataSet);

		List<Neuron> neurons = ttosom.getNeuronList();
		return new ResponseEntity<>(neurons,HttpStatus.OK); 
	}

	private Distance initializeDistance(TTOSOMRequest jsonRequest) {
		Distance distance;
		switch(jsonRequest.getDistance()){
			case EUCLIDEAN:
				distance = new NormalizedEuclideanDistance();
				break;
			case MANHATTAN:
				distance = new NormalizedManhattanDistance();
				break;
			default:
				distance = new NormalizedEuclideanDistance();
				break;
		}
		return distance;
	}

	private Instances readArff(String url) throws IOException {
		ArffLoader arffLoader = new ArffLoader();
		arffLoader.setURL(url);
		Instances dataSet = arffLoader.getDataSet();
        dataSet.setClassIndex(dataSet.numAttributes() - 1);
		return dataSet;
	}
	
	

	
}