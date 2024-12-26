package io.github.tuanpq.javafileio.pdf.service;

import java.io.IOException;
import java.io.InputStream;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

@Service
public class PDFFileService {

	@Autowired
	private ResourceLoader resourceLoader;

	public String readFileFromResources(String filename) throws IOException {
		Resource resource = resourceLoader.getResource("classpath:static/" + filename);
		InputStream inputStream = resource.getInputStream();
		PDDocument document = Loader.loadPDF(inputStream.readAllBytes());
	    PDFTextStripper stripper = new PDFTextStripper();
	    String text = stripper.getText(document);
	    document.close();
		return text;
	}

}
