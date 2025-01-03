package io.github.tuanpq.javafileio.excel.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import io.github.tuanpq.javafileio.xml.service.XmlFileService;

@Service
public class ExcelFileService {

	private int rowNumber = 0;
	private int columnMax = 0;
	
	@Autowired
	private ResourceLoader resourceLoader;
	
	@Autowired
	private XmlFileService xmlFileService;
	
	public void writeFile(Path filePath, Set<String> kanjiList) {
		xmlFileService.load();
		XSSFWorkbook workbook = new XSSFWorkbook();

		FileOutputStream outputStream = null;
		try {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			File kanjiDictionary = resourceLoader.getResource("classpath:static/xml/kanjidic2.xml").getFile();
			Document document = documentBuilder.parse(kanjiDictionary);
			Element element = document.getDocumentElement();
			
			CellStyle cellStyle = workbook.createCellStyle();
			XSSFFont font = ((XSSFWorkbook) workbook).createFont();
			NodeList characterList = element.getElementsByTagName("character");
			Sheet kanjiListSheet = workbook.createSheet("KanjiList");
			
			font.setFontName("MS PGothic");
			font.setFontHeightInPoints((short) 20);
			cellStyle.setFont(font);
			rowNumber = 0;
			columnMax = 0;
			
			kanjiList.forEach(kanji -> {
				Row row = kanjiListSheet.createRow(rowNumber);
				Cell kanjiCell = row.createCell(0);
				kanjiCell.setCellValue(kanji);
				kanjiCell.setCellStyle(cellStyle);
				lookupKanji(kanji, characterList, row, cellStyle);
				rowNumber++;
			});
			
			for (int i = 1; i < columnMax; i++) {
				kanjiListSheet.autoSizeColumn(i);
			}
			
			String fileName = filePath.toString();
			int dotIndex = fileName.lastIndexOf('.');
			fileName = fileName.substring(0, dotIndex) + ".xlsx";
			outputStream = new FileOutputStream(fileName);
			workbook.write(outputStream);
			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private void lookupKanji(String kanji, NodeList characterList, Row currentRow, CellStyle cellStyle) {
		List<String> readingTypeList = Stream.of("vietnam", "ja_on", "ja_kun").collect(Collectors.toList());
		int column = 1;
		
		for (int i1 = 0; i1 < characterList.getLength(); i1++) {
			Node characterNode = characterList.item(i1);
			if (characterNode.getNodeType() == Node.ELEMENT_NODE) {
				Element characterElement = (Element)characterNode;
				NodeList literalList = characterElement.getElementsByTagName("literal");
				if (literalList != null && literalList.getLength() > 0) {
					Node literalNode = literalList.item(0);
					if (literalNode.getNodeType() == Node.ELEMENT_NODE) {
						Element literalElement = (Element)literalNode;
						if (kanji.equals(literalElement.getTextContent())) {
							NodeList readingList = characterElement.getElementsByTagName("reading");
							NodeList meaningList = characterElement.getElementsByTagName("meaning");
							
							if (readingList != null && readingList.getLength() > 0) {
								for (int i2 = 0; i2 < readingList.getLength(); i2++) {
									Node readingNode = readingList.item(i2);
									if (readingNode.getNodeType() == Node.ELEMENT_NODE) {
										Element readingElement = (Element)readingNode;
										String readingType = readingElement.getAttribute("r_type");
										if (readingTypeList.contains(readingType)) {
											Cell currentCell = currentRow.createCell(column++);
											currentCell.setCellValue(readingElement.getTextContent());
											currentCell.setCellStyle(cellStyle);
										}
									}
								}
							}
							
							if (meaningList != null && meaningList.getLength() > 0) {
								for (int i3 = 0; i3 < meaningList.getLength(); i3++) {
									Node meaningNode = meaningList.item(i3);
									if (meaningNode.getNodeType() == Node.ELEMENT_NODE) {
										Element meaningElement = (Element)meaningNode;
										String languageType = meaningElement.getAttribute("m_lang");
										if (languageType == null || languageType.length() == 0) {
											Cell currentCell = currentRow.createCell(column++);
											currentCell.setCellValue(meaningElement.getTextContent());
											currentCell.setCellStyle(cellStyle);
										}
									}
								}
							}
							
							if (columnMax < column) {
								columnMax = column;
							}
							
							break;
						}
					}
				}
			}
		}
	}

}
