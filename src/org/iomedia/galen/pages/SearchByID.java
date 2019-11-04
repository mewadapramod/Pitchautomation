package org.iomedia.galen.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class SearchByID {
	@FindBy(xpath="//h4[text()='Search by Archtics']") WebElement searchByArchtics;
	@FindBy(xpath="//input[@value='artichtics_id']") WebElement archticsID_radioButton;
	@FindBy(xpath="//span[text()='Search in Archtics']")public WebElement SearchInArchtics;
	@FindBy(id="archtics_ID") WebElement archticsId;
	
	@FindBy(xpath="(//div[@class='react-grid-Cell__value'])[1]//span//div") public WebElement Archtics_id;
	@FindBy(xpath="(//div[@class='react-grid-Cell__value'])[2]//span//div")public WebElement Email;
	@FindBy(xpath="((//div[@class='react-grid-Cell__value'])[3]//span//div)[1]")
	public WebElement phoneno;
	@FindBy(xpath="(//div[@class='react-grid-Cell__value'])[4]//span//div")
	public WebElement StreetAddress;
	@FindBy(xpath="(//div[@class='react-grid-Cell__value'])[3]//span//div")
	public WebElement City;
	@FindBy(xpath="(//div[@class='react-grid-Cell__value'])[4]//span//div") public WebElement State;
	@FindBy(xpath="(//div[@class='react-grid-Cell__value'])[5]//span//div")public WebElement zip;
	@FindBy(xpath="//div[@title='invoice Test']")public WebElement Name;
	WebDriver driver;
	
	
	public void click_archticsIdRadioButton() {
		archticsID_radioButton.click();
	}
	
	public void setArchticsId(String A_ID) {
		archticsId.sendKeys(A_ID);
	}
	
	public String get_text(WebElement A) {
		String text=A.getText();
		return text;
	}
	
	public void Assert_text( String Exp_Text, String Act_Text, String message) {
		Assert.assertEquals( Exp_Text, Act_Text, message);
	}
	
	
	public   void scrollbyelement(WebDriver driver, WebElement Element) throws InterruptedException {
		((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", Element);
		Thread.sleep(3000);
	}
	public void click_element(WebElement element) {
		element.click();
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
