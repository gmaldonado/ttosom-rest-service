package com.ttosom.rest;


import java.util.List;
import java.util.Map;
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
import com.ttosom.neuron.NodeValue;

import weka.classifiers.Evaluation;
import weka.core.Instance;
import weka.core.Instances;
import static com.ttosom.TTOSOMUtils.*;


@Controller
@RequestMapping(value = "/ttosom")
public class RestController{
	
	private TTOSOM ttosom;
	private Instances dataSet;
	private Distance distanceFunction;
 
	private  void initializeTTOSOM(BasicRequest jsonRequest) throws Exception{
		Map<String, Distance> distances = initializeDistancesMap();
		
		dataSet = readArffFromURL(jsonRequest.getDataSetUrl()); 
		
		distanceFunction = distances.get(jsonRequest.getDistanceFunction());
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
	
	@RequestMapping(value = "/classification", method=RequestMethod.POST)
	public ResponseEntity<JSONObject> classification(@RequestBody BasicRequest jsonRequest) throws Exception {
		
		initializeTTOSOM(jsonRequest);
        Instances classifiedData = null;
        Instances unlabeledData = new Instances(dataSet,0);
        
        extractUnlabeledData(unlabeledData);
        
        classifiedData = classifyUnlabeledData(unlabeledData,classifiedData);
		
        JSONObject jsonObject = generateInstancesAsJson(classifiedData);
		return new ResponseEntity<JSONObject>(jsonObject,HttpStatus.OK); 
	}

	private void extractUnlabeledData(Instances unlabeledData) {
		for(int i=0;i<dataSet.numInstances();i++){
            if(dataSet.instance(i).classIsMissing()){
            	unlabeledData.add(dataSet.instance(i));
            }
        }
	}
	
	private Instances classifyUnlabeledData( Instances unlabeledData, Instances classifiedData) {
		
		if(unlabeledData.numInstances()>0 && unlabeledData.numInstances()!=dataSet.numInstances()){
				//the data to be classified is the unlabeled data
				classifiedData = new Instances(unlabeledData);
        		for(int i=0;i<unlabeledData.numInstances();i++){
                    double classLabel = ttosom.classifyInstance(unlabeledData.instance(i));
                    classifiedData.instance(i).setClassValue(classLabel);
        		}
        }
		return classifiedData;
	}

	
	
	@SuppressWarnings("unchecked")
	private JSONObject generateInstancesAsJson(Instances instances){
		
		//there's no data to be classified, then return an empty object
		if(instances == null){
			return new JSONObject();
		}
		
		JSONObject instancesAsJson = new JSONObject();

		for(int i=0;i<instances.numInstances();i++){
			JSONArray currentInstanceAsJson = new JSONArray();
			Instance instance = instances.instance(i);
			for(int j=0;j<instance.numValues();j++){
				currentInstanceAsJson.add(j, instance.value(j));
				instancesAsJson.put(i, currentInstanceAsJson);
			}
		}
		
		return instancesAsJson;
		
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/clustering", method=RequestMethod.POST)
	public ResponseEntity<JSONObject> clustering(@RequestBody BasicRequest jsonRequest) throws Exception {
		initializeTTOSOM(jsonRequest);
		int[] clusterVector = ttosom.generateClusterVector(dataSet, distanceFunction);
		JSONObject clusterVectorAsJson = new JSONObject();
		clusterVectorAsJson.put("cluster vector", clusterVector);
		return new ResponseEntity<JSONObject>(clusterVectorAsJson,HttpStatus.OK); 
	
	}
	
	//Here we need to see what is the real information that we want to get from cross validation
	@RequestMapping(value = "/crossValidation", method=RequestMethod.POST)
	public ResponseEntity<EvaluationWrapperResponse> crossValidation(@RequestBody CrossValidationRequest jsonRequest) throws Exception {
		
		initializeTTOSOM(jsonRequest);

		int folds = jsonRequest.getFolds();
		int seedValue = jsonRequest.getSeedValue();
		
		Evaluation evaluation = new Evaluation(dataSet);
		evaluation.crossValidateModel(ttosom, dataSet,folds, new Random(seedValue));
		
		EvaluationWrapperResponse responseAsJson = new EvaluationWrapperResponse(evaluation);
		
		return new ResponseEntity<>(responseAsJson,HttpStatus.OK); 
	}
	
}