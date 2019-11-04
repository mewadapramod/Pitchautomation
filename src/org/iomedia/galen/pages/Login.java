package org.iomedia.galen.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Login {

	@FindBy(xpath="//a[@href='#/campaigns']") WebElement customer_login;
	@FindBy(xpath="//input[@type='text']") WebElement username;
	@FindBy(xpath="//input[@type='password']") WebElement password;
	@FindBy(xpath="//span[text()='Login']") WebElement login;
	@FindBy(xpath="//a[text()='Search Customers'") WebElement search_customers;
	 WebDriver driver;

	public Login(WebDriver ldriver){

		this.driver = ldriver;
	}
	
	//click on customer login
	public void customerLogin() {
		customer_login.click();
	}
	//Enter username
	public void setUsername(String Uname) {
		username.sendKeys(Uname);
	}
	//Enter password
	public void setPassword(String Password) {
		password.sendKeys(Password);
	}

	//click on Login Button
	public void clickLogin() {
		login.click();
	}
	public void set_dimensions(int height, int width) {
		Dimension d = new Dimension(height,width);
		driver.manage().window().setSize(d);
	}
	public   void waitforElement( WebDriver driver, WebElement Element ,  int timeunits) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, timeunits);
			wait.until(ExpectedConditions.visibilityOfElementLocated( (By) Element));
		}
		catch(Exception e) {
			e.printStackTrace();
		}

	}
	
}
