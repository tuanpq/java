package io.github.tuanpq.javafileio.excel.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

@Service
public class ExcelFileService {

	private int rowNumber = 0;
	
	public void writeFileToResources(String fileName, Set<String> kanjiList) {
		XSSFWorkbook workbook = new XSSFWorkbook();

		FileOutputStream outputStream = null;
		try {
			CellStyle cellStyle = workbook.createCellStyle();
			XSSFFont font = ((XSSFWorkbook) workbook).createFont();
			font.setFontName("MS PGothic");
			font.setFontHeightInPoints((short) 20);
			cellStyle.setFont(font);
			Sheet kanjiListSheet = workbook.createSheet("KanjiList");
			rowNumber = 0;
			kanjiList.forEach(kanji -> {
				Row r = kanjiListSheet.createRow(rowNumber++);
				Cell c = r.createCell(0);
				c.setCellValue(kanji);
				c.setCellStyle(cellStyle);
			});
			
			File currentDirectory = new File(".");
			String path = currentDirectory.getAbsolutePath();
			String fileLocation = path.substring(0, path.length() - 1) + fileName;
			outputStream = new FileOutputStream(fileLocation);
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
	
}
