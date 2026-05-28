package io.github.tuanpq.tools.jsontoexcel;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;

public class App {

	private static final int MAX_CHARACTER_IN_CELL		= 32767;
	private static int columnIndex 						= 0;
	private static final String EXCEL_FILE_NAME 			= "ExtractedLog.xlsx";
	private static final String JSON_ROOT_NODE_KEY 		= "data";
	private static final String EXTRACTED_FROM_NODE 		= "category";
	private static final List<String> COLUMN_NAMES 		= List.of("dateTime", "moduleName", "actionCode", "parametersContent", "resultsContent", "status");
	private static int sheetNo 							= 0;

	public static void main(String[] args) {

		System.out.println("");
		System.out.println("################################################");
		System.out.println("#                                              #");
		System.out.println("#  ##  ##  ######  ######      ####    ####    #");
		System.out.println("#  ######    ##    ##          ##  ##  ##  ##  #");
		System.out.println("#  ##  ##    ##    ######  ##  ##  ##  ##      #");
		System.out.println("#  ##  ##    ##        ##      ##  ##  ##  ##  #");
		System.out.println("#  ##  ##    ##    ######      ####    ####    #");
		System.out.println("#                                              #");
		System.out.println("#  Author: TuanPQ                              #");
		System.out.println("#                                              #");
		System.out.println("################################################");
		System.out.println("");
		
		System.out.println("Usage: java -jar jsontoexcel.jar <path_to_raw_log_folder>");
		System.out.println("");
		
		if (args.length < 1) {
			System.out.println("Please provide the path to the raw log file as an argument.");
			return;
		}
		
		try {
			Path rawLogFolderPath = Paths.get(args[0]);
			String extractedLogFilePath = Path.of(rawLogFolderPath.toString(), EXCEL_FILE_NAME).toString();
			FileOutputStream fos = new FileOutputStream(extractedLogFilePath);
			Workbook workbook = new XSSFWorkbook();
			
			// Use try-with-resources to ensure the stream is closed
			try (Stream<Path> stream = Files.walk(rawLogFolderPath)) {
				stream.filter(Files::isRegularFile) // Filter to keep only files
						.forEach(path -> {
							String currentFilePath = path.toString();
							if (!currentFilePath.endsWith(".txt")) {
								return;
							}
							String sheetName = getSheetName(currentFilePath);
							parseLogFile(currentFilePath, workbook, sheetName, fos);
							System.out.println("Processed file: " + currentFilePath);
						});
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			System.out.println("Extracted log file: " + extractedLogFilePath);
			
			workbook.write(fos);
			fos.close();
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void parseLogFile(String rawLogFilePath, Workbook workbook, String sheetName, FileOutputStream fos) {
		try {
			String jsonString = Files.readString(Path.of(rawLogFilePath));
			JSONObject jsonObject1 = new JSONObject(jsonString);
			Object jsonObject1Data = jsonObject1.get(JSON_ROOT_NODE_KEY);

			sheetNo++;
			Sheet sheet = workbook.createSheet(sheetName);
			boolean headerCreated = false;
			int rowIndex = 0;
			
			if (jsonObject1Data instanceof JSONArray jsonArray) {
				for (int i = 0; i < jsonArray.length(); i++) {
					Object value = jsonArray.get(i);
					if (value instanceof JSONObject jsonObject2) {
						if (!headerCreated) {
							headerCreated = true;
							
							Row headerRow = sheet.createRow(rowIndex++);
							columnIndex = 0;
							for (String key2 : jsonObject2.keySet()) {
								if (!COLUMN_NAMES.contains(key2)) {
									continue;
								}
								
								Cell cell = headerRow.createCell(columnIndex++);
								cell.setCellValue(key2);
							}
							
							Row row = sheet.createRow(rowIndex++);
							columnIndex = 0;
							for (String key2 : jsonObject2.keySet()) {
								if (!COLUMN_NAMES.contains(key2)) {
									continue;
								}

								Object value2 = jsonObject2.get(key2);
								Cell cell = row.createCell(columnIndex++);
								List<Map<String, Object>> outputList = new ArrayList<>();
								traverse(value2, outputList, cell);
								if (outputList.size() > 0) {
									writeDataToCell(cell, outputList.toString());
								} else {
									writeDataToCell(cell, value2.toString());
								}
					        }
						} else {
							Row row = sheet.createRow(rowIndex++);
							columnIndex = 0;
							for (String key2 : jsonObject2.keySet()) {
								if (!COLUMN_NAMES.contains(key2)) {
									continue;
								}

								Object value2 = jsonObject2.get(key2);
								Cell cell = row.createCell(columnIndex++);
								List<Map<String, Object>> outputList = new ArrayList<>();
								traverse(value2, outputList, cell);
								if (outputList.size() > 0) {
									writeDataToCell(cell, outputList.toString());
								} else {
									writeDataToCell(cell, value2.toString());
								}
					        }
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void traverse(Object obj, List<Map<String, Object>> outputList, Cell cell) {
		if (obj.toString().contains(EXTRACTED_FROM_NODE)) {
			if (obj instanceof JSONObject jsonObject1) {
	            for (String key1 : jsonObject1.keySet()) {
	                Object value1 = jsonObject1.get(key1);
	                if (key1.equals(EXTRACTED_FROM_NODE)) {
	                	extractCategory(value1, outputList);
	                	// No need to traverse deeper, the value has been extracted
	                	return;
	                } else {
	                	traverse(value1, outputList, cell);
	                }
	            }
	        } else if (obj instanceof JSONArray jsonArray) {
	            for (int i = 0; i < jsonArray.length(); i++) {
	                Object value = jsonArray.get(i);
	                traverse(value, outputList, cell);
	            }
	        } else {
	            try {
	            	Object convertedJsonObject = new JSONObject(obj.toString());
	            	if (convertedJsonObject != null) {
	            		traverse(convertedJsonObject, outputList, cell);
	            	}
	            } catch (Exception e) {
					// Can not traverse deeper, write the value to cell
				}
	        }
		}
    }
	
	private static void extractCategory(Object categoryValue, List<Map<String, Object>> outputList) {
		if (categoryValue instanceof JSONArray jsonArray) {
            for (int i = 0; i < jsonArray.length(); i++) {
                Object value = jsonArray.get(i);
                extractCategory(value, outputList);
            }
		} else if (categoryValue instanceof JSONObject jsonObject) {
			for (String key : jsonObject.keySet()) {
				if (key.equals("subCategory")) {
					Map<String, Object> patientDataMap = new HashMap<>();
					Object value = jsonObject.get(key);
					patientDataMap.put("extractedSubCategory", value);
					outputList.add(patientDataMap);
					// No need to traverse deeper, the value has been extracted
					return;
				}
			}
		} else {
			try {
				Object convertedJsonObject = new JSONObject(categoryValue.toString());
				if (convertedJsonObject != null) {
					extractCategory(convertedJsonObject, outputList);
				}
			} catch (Exception e) {
				// Can not traverse deeper, write the value to cell
			}
		}
		
		
	}
	
	private static void writeDataToCell(Cell cell, String value) {
		if (value.length() > MAX_CHARACTER_IN_CELL) {
			value = value.substring(0, MAX_CHARACTER_IN_CELL);
		}
		cell.setCellValue(value);
	}
	
	
	private static String getSheetName(String filePath) {
		try {
			String fileName = Path.of(filePath).getFileName().toString();
			int dotIndex = fileName.lastIndexOf('.');
			String[] components = fileName.substring(0, dotIndex).split("_");
			
			return components[0] + "_" + components[2];
		} catch (Exception e) {
			e.printStackTrace();
			return "Sheet_" + sheetNo;
		}
	}

}
