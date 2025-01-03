package io.github.tuanpq.javafileio.xml.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

@Service
public class XmlFileService {

	private static final Logger logger = LoggerFactory.getLogger(XmlFileService.class);
	
	private Document kanjiDocument;
	
	private List<Document> dictionaryDocumentList = new ArrayList<Document>();
	
	@Autowired
	private ResourceLoader resourceLoader;
	
	public void load() {
		logger.debug("load: start");
		loadKanjiDictionary();
		loadJMDictionary();
		logger.debug("load: end");
	}

	public Document getKanjiDocument() {
		return kanjiDocument;
	}

	public List<Document> getDictionaryDocumentList() {
		return dictionaryDocumentList;
	}

	private void loadKanjiDictionary() {
		logger.debug("loadKanjiDictionary: start");
		try {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			File kanjiFile = resourceLoader.getResource("classpath:static/xml/kanjidic2.xml").getFile(); //
			kanjiDocument = documentBuilder.parse(kanjiFile);
		} catch (Exception e) {
			logger.error("ERROR: " + e.getMessage());
		}
		logger.debug("loadKanjiDictionary: end");
	}
	
	private void loadJMDictionary() {
		logger.debug("loadJMDictionary: start");
		try {
			dictionaryDocumentList.clear();
			File dictionaryFile = resourceLoader.getResource("classpath:static/xml/JMdict_e.xml").getFile(); //
			try (BufferedReader br = new BufferedReader(new FileReader(dictionaryFile))) {
			    String line = "";
			    int numberOfEnties = 0;
			    int numberOfFiles = 0;
			    boolean newFile = false;
			    BufferedWriter writer = null;
			    String filePartPath = "";
			    StringBuilder sb = new StringBuilder();
			    String header = "";
			    boolean endOfHeader = false;
			    
			    while ((line = br.readLine()) != null) {
			    	if (line.equals("<entry>")) {
			    		if (!endOfHeader) {
			    			endOfHeader = true;
			    			header = sb.toString();
			    		}
			    		
			    		if (!newFile) {
			    			newFile = true;
			    			numberOfFiles++;
			    			filePartPath = dictionaryFile.getParent() + "/JMdict_e_p" + numberOfFiles + ".xml";
						    writer = new BufferedWriter(new FileWriter(filePartPath));
						    writer.write(header);
						}
			    		
			    		numberOfEnties++;
			    		writer.write(line);
					    writer.write("\n");
			    	} else if (line.equals("</entry>")) {
		    			writer.write(line);
					    writer.write("\n");
			    		if (numberOfEnties == 6400) {
						    writer.write("</JMdict>");
						    writer.close();
			    			newFile = false;
			    			numberOfEnties = 0;
			    			
			    			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			    			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			    			File dictionaryFilePart = new File(filePartPath);
			    			Document document = documentBuilder.parse(dictionaryFilePart);
			    			dictionaryDocumentList.add(document);
			    			Files.delete(Paths.get(filePartPath));
			    		}
			    	} else {
			    		if (newFile) {
			    			writer.write(line);
						    writer.write("\n");
			    		} else {
			    			if (!endOfHeader) {
			    				sb.append(line);
			    				sb.append("\n");
			    			}
			    		}
			    	}
			    }
			}
		} catch (Exception e) {
			logger.error("ERROR: " + e.getMessage());
		}
		logger.debug("loadJMDictionary: end");
	}
	
}
