package io.github.tuanpq.selenium;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.google.common.io.Files;

public class App {
	
	public static void main(String[] args) {
		WebDriver driver = new ChromeDriver();
		
		try {
			driver.get("https://race.banmaischool.edu.vn/");
			driver.manage().window().maximize();
			WebElement loginRegisterButton = driver.findElement(By.xpath("//button[@class='btn btn-default']"));
			loginRegisterButton.click();
			Thread.sleep(2000);
			WebElement emailText = driver.findElement(By.name("email"));
			WebElement passwordText = driver.findElement(By.name("password1"));
			emailText.sendKeys("tuanpq@gmail.com");
			passwordText.sendKeys("{Enter password here}");
			WebElement loginButton = driver.findElement(By.xpath("//button[@class='btn btn-default secondary']"));
			loginButton.click();
			Thread.sleep(3000);
			TakesScreenshot ts = (TakesScreenshot) driver;
			File source = ts.getScreenshotAs(OutputType.FILE);
			File target = new File("./screenshot.png");
			Files.copy(source, target);
			System.out.println("Captured file: " + target.getAbsolutePath());
			
			Workbook workbook = new XSSFWorkbook();
			Sheet sheet = workbook.createSheet("UT01-001");
			Row row0 = sheet.createRow(0);
			Row row1 = sheet.createRow(1);
			Row row2 = sheet.createRow(2);
			row0.createCell(0).setCellValue("Pre-condition");
			row1.createCell(1).setCellValue("User's role is Developer");
			row2.createCell(1).setCellValue("Navigate from Product List screen");
			InputStream targetStream = new FileInputStream(target.getAbsolutePath());
			byte[] bytes = IOUtils.toByteArray(targetStream);
			int pictureIdx = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
			targetStream.close();
			XSSFDrawing drawing = (XSSFDrawing) sheet.createDrawingPatriarch();
			XSSFClientAnchor anchor = new XSSFClientAnchor();
			anchor.setCol1(1);
			anchor.setRow1(4);
			anchor.setCol2(26);
			anchor.setRow2(44);
			drawing.createPicture(anchor, pictureIdx);
			try (FileOutputStream fileOut = new FileOutputStream("./Evidence.xlsx")) {
				workbook.write(fileOut);
				workbook.close();
			}
			
			Thread.sleep(5000);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		driver.quit();
	}

}
