package io.github.tuanpq.javafileio.pdf.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;

@Service
public class PDFFileService {

	public String readFile(Path filePath) throws IOException {
		try (InputStream inputStream = Files.newInputStream(filePath)) {
			return read(inputStream);
		}
	}
	
	private String read(InputStream inputStream) throws IOException {
		PDDocument document = Loader.loadPDF(inputStream.readAllBytes());
		PDFTextStripper stripper = new PDFTextStripper();
		String text = stripper.getText(document);
		document.close();
		return text;
	}
	
}
