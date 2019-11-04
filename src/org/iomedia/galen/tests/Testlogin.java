package org.iomedia.galen.tests;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.iomedia.galen.pages.Login;
public class Testlogin {
	Login objlogin;
	WebDriver driver;
	public void loginPitchman(String UN,String PWD) {
	 
		objlogin.customerLogin();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		objlogin.setUsername(UN);
		objlogin.setPassword(PWD);
		objlogin.clickLogin();
		
	}
	
	
		
}