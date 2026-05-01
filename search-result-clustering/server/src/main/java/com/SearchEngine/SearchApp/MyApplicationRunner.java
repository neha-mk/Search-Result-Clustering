package com.SearchEngine.SearchApp;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.Indexer.IndexDocumentCollection;

@Component
public class MyApplicationRunner implements ApplicationRunner {

	@Override
	public void run(ApplicationArguments args) throws Exception {

		System.out.println("Server started successfully.");
		System.out.println("Indexing the Document Collection....");
		IndexDocumentCollection.indexDocuments();
		System.out.println("Indexing completed.");
	}
}
