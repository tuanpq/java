package io.github.tuanpq.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Application {

	public static void main(String[] args) {
		WebDriver driver = new ChromeDriver();
		
		driver.get("https://www.selenium.dev/selenium/web/web-form.html");
		WebElement textBox = driver.findElement(By.name("my-text"));
        WebElement submitButton = driver.findElement(By.cssSelector("button"));
        textBox.sendKeys("Selenium");
        submitButton.click();
        try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        
        driver.quit();
	}

}
