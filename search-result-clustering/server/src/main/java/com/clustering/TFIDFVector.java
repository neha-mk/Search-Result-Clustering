package com.clustering;

import java.util.ArrayList;
import java.util.List;

import com.Constants.Constants;
import com.docSearch.RetrievedDocument;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

class TFIDFVector {
	public Instances fitTransform(List<RetrievedDocument> retrievedDocuments) throws Exception {
		StringToWordVector stringToWordVector = new StringToWordVector();

 		// Config for StringToWordVector to set it as tf-idf vectorizer
		stringToWordVector.setIDFTransform(true);
		stringToWordVector.setTFTransform(true);
		stringToWordVector.setLowerCaseTokens(true);
		stringToWordVector.setOutputWordCounts(false); // Output TF-IDF values instead of word counts
		// Word vectors will be built based on the entire dataset without considering the class labels
		stringToWordVector.setDoNotOperateOnPerClassBasis(true);
		
		Instances inputDataInstances = convertToWekaInstances(retrievedDocuments);
        stringToWordVector.setInputFormat(inputDataInstances);
        Instances transformedDataInstances = Filter.useFilter(inputDataInstances, stringToWordVector);
        return transformedDataInstances;
	}

	private Instances convertToWekaInstances(List<RetrievedDocument> retrievedDocuments) {
		// Create attributes for title, content, and score
		Attribute docNameAttr = new Attribute(Constants.DOC_FIELD_NAME_STRING, (List<String>) null);
		Attribute docExtractAttr = new Attribute(Constants.DOC_FIELD_EXTRACT_STRING, (List<String>) null);
		Attribute scoreAttr = new Attribute(Constants.DOC_FIELD_SCORE_STRING);

		// Create Instances object with the defined attributes
		ArrayList<Attribute> attributes = new ArrayList<>();
		attributes.add(docNameAttr);
		attributes.add(docExtractAttr);
		attributes.add(scoreAttr);

		Instances wekaInstances = new Instances(Constants.WEKA_INSTANCE_RELATION_NAME_STRING, attributes, retrievedDocuments.size());

		// Add data to Instances
		for (RetrievedDocument doc : retrievedDocuments) {
			double[] values = new double[3];
			values[0] = wekaInstances.attribute(Constants.DOC_FIELD_NAME_STRING).addStringValue(doc.getTitle());
			values[1] = wekaInstances.attribute(Constants.DOC_FIELD_EXTRACT_STRING).addStringValue(doc.getContent());
			values[2] = doc.getScore();

			DenseInstance instance = new DenseInstance(1.0, values);
			wekaInstances.add(instance);
		}

		// Set class index as negative number to indicate no target attribute present
		wekaInstances.setClassIndex(-1);

		return wekaInstances;
	}

}
