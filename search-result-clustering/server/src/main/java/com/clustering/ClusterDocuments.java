package com.clustering;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.Constants.Constants;
import com.docSearch.RetrievedDocument;

public class ClusterDocuments {
	private static final Map<String, Class<? extends ClusteringAlgorithm>> clusterAlgoClassMap = new HashMap<>();

	static {
		clusterAlgoClassMap.put(Constants.CLUSTERING_ALGO_KMEANS_STRING, KmeansClusteringAlgo.class);
	}

	public static Map<Integer, List<Map<String, Object>>> cluster(List<RetrievedDocument> retrievedDocuments, 
			Map<String, Object> inputParamsMap) throws Exception {
		
		if (clusterAlgoClassMap.containsKey(inputParamsMap.get(Constants.INPUT_PARAM_CLUST_ALGO_STRING))){
		// Get the relevant class of the given Algorithm
			Class<? extends ClusteringAlgorithm> clusterAlgorithmClass = clusterAlgoClassMap.get(inputParamsMap.get(Constants.INPUT_PARAM_CLUST_ALGO_STRING));
		  	ClusteringAlgorithm clusterAlgoObj = clusterAlgorithmClass.getDeclaredConstructor().newInstance();
			return clusterAlgoObj.clusterDocuments(retrievedDocuments, inputParamsMap);
		}else {
			throw new Exception(Constants.ALOG_NOT_IN_CONFIG_STRING);
		}
		
	}
}
