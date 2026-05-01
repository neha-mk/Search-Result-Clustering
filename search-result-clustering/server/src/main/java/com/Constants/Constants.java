package com.Constants;

public class Constants {
	public static final String INDEXED_DOCUMENTS_PATH = "src/main/resources/static/IndexedDocuments";
	public static final String DOCUMENTS_COLLECTION_PATH = "src/main/resources/static/Documents";

	public static final int NO_TOP_K_DOCS_TO_SEARCH_INT = 20;

	public static final String DOC_FIELD_EXTRACT_STRING = "docExtract";
	public static final String DOC_FIELD_NAME_STRING = "docName";
	public static final String DOC_FIELD_SCORE_STRING = "score";
	public static final String DOC_FIELD_CLUSTER_STRING = "cluster";
	public static final String DOC_FIELD_COORDINATES_STRING = "coordinates";

	public static final String DOC_FILE_EXTENTSION_STRING = ".txt";
	public static final String DOC_FILE_EXTN_REPLACEMENT_STRING = "";

	public static final String WEKA_INSTANCE_RELATION_NAME_STRING = "DocumentInstances";

	public static final String INPUT_PARAM_OPTIMAL_K_STRING = "isOptK";
	public static final String INPUT_PARAM_K_VALUE_STRING = "kVal";
	public static final String INPUT_PARAM_CLUST_ALGO_STRING = "algorithm";
	public static final String INPUT_SEARCH_QUERY_STRING = "searchString";

	public static final String CLUSTERING_ALGO_KMEANS_STRING = "kMeans";
	public static final int CLUSTERING_KMEANS_MIN_CLUSTERS = 50;

	// Custom Exceptions
	public static final String ALOG_NOT_IN_CONFIG_STRING = "Algorithm not present in the configuration";
	public static final String REQUEST_INPUT_PARAM_NOT_CORRECT_STRING = "Input Parameters not correct/sufficent to proceed";


}
