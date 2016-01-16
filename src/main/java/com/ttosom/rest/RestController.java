package com.ttosom.rest;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ttosom.TTOSOM;
import com.ttosom.rest.*;
import com.ttosom.distance.Distance;
import com.ttosom.distance.NormalizedEuclideanDistance;
import com.ttosom.distance.NormalizedManhattanDistance;
import com.ttosom.neuron.Neuron;
import com.ttosom.neuron.NodeValue;
import weka.core.Instances;
import weka.core.converters.ArffLoader;


@Controller
public class RestController{
 
	
	
	
	@RequestMapping(value = "/ttosom", method=RequestMethod.POST)
	public ResponseEntity<List<Neuron>> callRegularFlow(@RequestBody TTOSOMRequest jsonRequest) throws Exception {
		
		Map<String, Distance> distances = initializeDistancesMap();
		
		Instances dataSet = readArffFromURL(jsonRequest.getDataSetUrl()); 
		
		Distance distance = distances.get(jsonRequest.getDistance());
		double initialLearningRate = jsonRequest.getInitialLearningRate();
		double initialRadius = jsonRequest.getInitialRadius();
		double finalLearning = jsonRequest.getFinalLearningRate();
		double finalRadius = jsonRequest.getFinalRadius();
		int iterations = jsonRequest.getIterations();
		List<NodeValue> treeAsArray = jsonRequest.getTreeAsArray();
		
		TTOSOM ttosom = new TTOSOM(dataSet, initialLearningRate, initialRadius, finalLearning, finalRadius, iterations, distance, false, new Random());
		ttosom.describeTopology(treeAsArray);
		ttosom.buildClassifier(dataSet);

		List<Neuron> neurons = ttosom.getNeuronList();
		return new ResponseEntity<>(neurons,HttpStatus.OK); 
	}
	
	private Map<String, Distance> initializeDistancesMap(){
		
		Map<String, Distance> distancesMap = new HashMap<String, Distance>();
		distancesMap.put("Euclidean", new NormalizedEuclideanDistance());
		distancesMap.put("Manhattan", new NormalizedManhattanDistance());
		return distancesMap;
	}


	private Instances readArffFromURL(String url) throws IOException {
		ArffLoader arffLoader = new ArffLoader();
		arffLoader.setURL(url);
		Instances dataSet = arffLoader.getDataSet();
        dataSet.setClassIndex(dataSet.numAttributes() - 1);
		return dataSet;
	}


	

	
}