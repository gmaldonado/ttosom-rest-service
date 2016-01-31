package com.ttosom;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ttosom.distance.Distance;
import com.ttosom.distance.NormalizedEuclideanDistance;
import com.ttosom.distance.NormalizedManhattanDistance;
import com.ttosom.neuron.NodeValue;

import weka.core.Instances;
import weka.core.converters.ArffLoader;

public class TTOSOMUtils {

	
	public static List<NodeValue> initializeTreeAsArray(int[] array) {
		List<NodeValue> treeAsArray = new ArrayList<NodeValue>();

		for (int i = 0; i < array.length; i++) {
			NodeValue node = new NodeValue(i, array[i]);
			treeAsArray.add(node);
		}

		return treeAsArray;
	}

	public static Instances readArffFromURL(String url) throws IOException {
		ArffLoader arffLoader = new ArffLoader();
		arffLoader.setURL(url);
		Instances dataSet = arffLoader.getDataSet();
		dataSet.setClassIndex(dataSet.numAttributes() - 1);
		return dataSet;
	}

	public static Map<String, Distance> initializeDistancesMap() {

		Map<String, Distance> distancesMap = new HashMap<String, Distance>();
		distancesMap.put("Euclidean", new NormalizedEuclideanDistance());
		distancesMap.put("Manhattan", new NormalizedManhattanDistance());
		return distancesMap;
	}

}
