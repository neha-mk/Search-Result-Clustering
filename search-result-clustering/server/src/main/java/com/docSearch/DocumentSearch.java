package com.docSearch;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.custom.CustomAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.search.similarities.ClassicSimilarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import com.Constants.Constants;
import com.analyser.LuceneAnalyser;

public class DocumentSearch {
	public List<RetrievedDocument> searchTopDocuments(Map<String, Object> inputParamsMap)
			throws IOException, ParseException {
		String queryString = (String) inputParamsMap.get(Constants.INPUT_SEARCH_QUERY_STRING);
		int numOfTopDocuments = Constants.NO_TOP_K_DOCS_TO_SEARCH_INT;

		Path indexPath = Paths.get(Constants.INDEXED_DOCUMENTS_PATH);
		Directory iReader = FSDirectory.open(indexPath);
		DirectoryReader directoryReader = DirectoryReader.open(iReader);
		IndexSearcher indexSearcher = new IndexSearcher(directoryReader);

		indexSearcher.setSimilarity(new ClassicSimilarity());
		CustomAnalyzer customAnalyzer = LuceneAnalyser.getAnalyzer();

		QueryParser parser = new QueryParser(Constants.DOC_FIELD_EXTRACT_STRING, customAnalyzer);
		Query query = parser.parse(queryString);

//		Get top "n" documents into the collector
		TopScoreDocCollector collector = TopScoreDocCollector.create(numOfTopDocuments);
		indexSearcher.search(query, collector);

//		Fetch the score of the all the top docs and add documents to list along with their scores
		List<RetrievedDocument> retrievedTopDocs = new ArrayList<RetrievedDocument>();
		ScoreDoc[] topDocs = collector.topDocs().scoreDocs;
		for (ScoreDoc topDoc : topDocs) {
			RetrievedDocument relDocument = new RetrievedDocument();
			Document document = indexSearcher.doc(topDoc.doc);
			relDocument.setTitle(document.get(Constants.DOC_FIELD_NAME_STRING));
			relDocument.setContent(document.get(Constants.DOC_FIELD_EXTRACT_STRING));
			relDocument.setScore(topDoc.score);
			retrievedTopDocs.add(relDocument);
		}

		iReader.close();
		directoryReader.close();

		return retrievedTopDocs;
	}
}
