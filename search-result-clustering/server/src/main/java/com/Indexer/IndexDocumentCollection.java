package com.Indexer;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.EnumSet;


import org.apache.commons.io.FileUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import com.Constants.Constants;
import com.analyser.LuceneAnalyser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class IndexDocumentCollection {

	public static void indexDocuments() throws IOException {
		System.out.println("Inside indexDocuments() ");
		Path indexPath = Paths.get(Constants.INDEXED_DOCUMENTS_PATH);
		Directory directory = FSDirectory.open(indexPath);

 		// Clear existing indexed documents 
		try {
			clearDirectory(indexPath);
			System.out.println("Previously Indexed files in Index Directory cleared successfully.");
		} catch (IOException e) {
			e.printStackTrace();
		}

		startIndexing(directory);
	}

	private static void startIndexing(Directory directory) throws IOException {
        IndexWriterConfig config = new IndexWriterConfig(LuceneAnalyser.getAnalyzer());
        IndexWriter iWriter = new IndexWriter(directory, config);
        File documentCollectionDir = new File(Constants.DOCUMENTS_COLLECTION_PATH);
        File[] documentFiles = documentCollectionDir.listFiles();

        for (File documentFile : documentFiles) {
            try {
                String content = extractFileContent(documentFile);
                if (content != null) {
                    Document doc = new Document();
                    doc.add(new Field(Constants.DOC_FIELD_EXTRACT_STRING, content, TextField.TYPE_STORED));
                    doc.add(new Field(Constants.DOC_FIELD_NAME_STRING, documentFile.getName(), TextField.TYPE_STORED));
                    iWriter.addDocument(doc);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        iWriter.close();
    }

    private static String extractFileContent(File file) throws Exception {
        if (file.getName().endsWith(".txt")) {
            return FileUtils.readFileToString(file, StandardCharsets.UTF_8);
        } else if (file.getName().endsWith(".pdf")) {
            try (PDDocument pdfDocument = PDDocument.load(file)) {
                PDFTextStripper stripper = new PDFTextStripper();
                return stripper.getText(pdfDocument);
            }
        } else if (file.getName().endsWith(".xml")) {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            org.w3c.dom.Document xmlDoc = builder.parse(file);
            xmlDoc.getDocumentElement().normalize();

            StringBuilder content = new StringBuilder();
            NodeList nodes = xmlDoc.getDocumentElement().getChildNodes();
            for (int i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i);
                if (node.getNodeType() == Node.TEXT_NODE || node.getNodeType() == Node.ELEMENT_NODE) {
                    content.append(node.getTextContent()).append(" ");
                }
            }
            return content.toString();
        }
        return null;
    }

	private static void clearDirectory(Path directory) throws IOException {
		// Delete all files and sub-directories
		Files.walkFileTree(directory, EnumSet.noneOf(FileVisitOption.class), Integer.MAX_VALUE,
				new SimpleFileVisitor<Path>() {
					@Override
					public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
						Files.delete(file);
						return FileVisitResult.CONTINUE;
					}

					@Override
					public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
						// Handle the failure to visit a file (e.g., permission issue)
						return FileVisitResult.CONTINUE;
					}

					@Override
					public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
						// Skip deleting the root directory itself
						if (dir.equals(directory)) {
							return FileVisitResult.CONTINUE;
						}

						// Delete sub-directories and their contents
						Files.delete(dir);
						return FileVisitResult.CONTINUE;
					}
				});
	}
}
