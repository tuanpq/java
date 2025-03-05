package io.github.apitraining.selenium;

import java.io.File;
import java.io.IOException;

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
