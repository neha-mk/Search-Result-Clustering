package com.checkInputParams;

import java.util.Map;

import com.Constants.Constants;

public final class CheckInputParams {
	public static boolean inputParamMapIsCorrect(Map<String, Object> inputParamsMap) {
		try {
			if (containsKeyWithNonEmptyValue(inputParamsMap, Constants.INPUT_PARAM_CLUST_ALGO_STRING)
					&& containsKeyWithNonEmptyValue(inputParamsMap, Constants.INPUT_SEARCH_QUERY_STRING)) {

				// If K-means
				if (containsKeyWithNonEmptyValue(inputParamsMap, Constants.INPUT_PARAM_CLUST_ALGO_STRING)
						&& Constants.CLUSTERING_ALGO_KMEANS_STRING
								.equals(inputParamsMap.get(Constants.INPUT_PARAM_CLUST_ALGO_STRING))) {

					// Then input should either have no. of clusters given OR optimal clusters
					// parameter
					if (containsKeyWithNonEmptyValue(inputParamsMap, Constants.INPUT_PARAM_K_VALUE_STRING)
							|| containsKeyWithNonEmptyValue(inputParamsMap, Constants.INPUT_PARAM_OPTIMAL_K_STRING)) {
						return true;
					}
					return false;
				}

				// Add else-if above to accommodate other algorithms
				return false;
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean isValidFileFormat(String fileName) {
        return fileName.endsWith(".txt") || fileName.endsWith(".pdf") || fileName.endsWith(".xml");
    }
	
	private static boolean containsKeyWithNonEmptyValue(Map<String, Object> map, String key) {
		// Check if it has the given key with no empty value
		return map.containsKey(key) && map.get(key) != null && !map.get(key).toString().isEmpty();
	}
}
