package org.iomedia.galen.pages;

import org.iomedia.framework.Driver.HashMapNew;
import org.iomedia.framework.Driver.TestDevice;
import org.iomedia.framework.Reporting;
import org.iomedia.framework.WebDriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.SkipException;

import java.awt.AWTException;
import java.sql.Timestamp;

import org.iomedia.common.BaseUtil;
import org.iomedia.framework.Assert;

public class Homepage extends BaseUtil {
	
	private String driverType;
	public Homepage(WebDriverFactory driverFactory, HashMapNew Dictionary, HashMapNew Environment, Reporting Reporter, Assert Assert, org.iomedia.framework.SoftAssert SoftAssert) {
		super(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert);
		driverType = driverFactory.getDriverType().get();
	}
		
	private By signInLink = By.xpath(".//div[@class='mobile-signin']//span[text()='Sign In']");
	private By signInLink1 = By.xpath(".//div[@class='mobile-signin']//span");
	private By forgotpassword = By.xpath(".//a[@href='/forgot']");
	public By emailAddress = By.xpath(".//input[@name='email']");
	private By submitBtn = By.xpath(".//button[@type='submit']");
	private By notificationBanner = By.xpath(".//div[starts-with(@class, 'notification-notificationBanner')]");
	private By cancelLink = By.xpath(".//div[contains(@class, 'anchorContainer')]//p");
	private By reactComponent = By.cssSelector("div.react-root-account");
	private By passwordLink = By.xpath(".//input[@name='password']");
	private By signUpLink = By.xpath(".//a[@href='/signup']");
	private By signInReactLink = By.xpath(".//a[@href='/']");
	private By errorMessage = By.xpath(".//input[@name='email']/..//span[starts-with(@class, 'theme-error')]");
	private By userMenu = By.xpath("//*[contains(@id,'block-useraccountmenu')]//a");
	private By logout = By.xpath("//*[@id='amgr-user-menu']/li/a[contains(@href, 'user/logout')]");
	private By loginMessage = By.cssSelector("div[class*='notification-notificationBannerTitle']");
	private By firstName = By.name("first_name");
	private By lastName = By.name("last_name");
	private By termsConditionsCheckBox = By.cssSelector("form div[class*='container-fluid'] div[class*='midFormLink'] > label div");
	private By agree = By.cssSelector("button.default");
	private By rememberCheckbox = By.cssSelector("label[data-react-toolbox='checkbox'] div");
	private By backIcon = By.cssSelector("i[class*='modal-back-icon']");
	private By welcome1 = By.xpath(".//a[@href='#amgr-user-menu']");
	private By CopyrightLink = By.cssSelector(".footer p a");
	private By Policy= By.xpath("//div[@class='footer']/div/div/div/ul/li[1]/a");
	private By Policylink = By.xpath("//a[contains(text(),'Privacy Policy')]");
	private By Terms = By.xpath("//div[@class='footer']/div/div/div/ul/li[2]/a");
	private By Termsofuse = By.xpath("//div[@class='container']/div/h1");
//	private By welcome = By.cssSelector(".react-root-dashboard-header > div > div > div:first-child > p:first-child");
	private By componentSubHeading = By.cssSelector("div[class*='componentSubHeading']");
	private By passwordShowHide = By.cssSelector(".passwordShowHide");
	private By privacy_terms_link = By.cssSelector("label[data-react-toolbox='checkbox'] div[class*='forms-anchorContainer'] p a");

	public String getLoginMessage(){
		return getText(loginMessage);
	}
	
	public void clickPrivacyTermsLink() {
		click(privacy_terms_link, "Privacy/Terms link");
	}
	
	public String getPrivacyTermsLinkUrl() {
		return getAttribute(privacy_terms_link, "href");
	}
	
	public String getCopyrightLink(){
		WebElement we = getElementWhenVisible(this.CopyrightLink);
		return we.getAttribute("href");
	}
	
	public String getPolicyLink(){
		WebElement we = getElementWhenVisible(this.Policy);
		return we.getAttribute("href");
	}
	
	public String getTermsLink(){
		WebElement we = getElementWhenVisible(this.Terms);
		return we.getAttribute("href");
	}
	
	public String getTermsofuse(){
		return getText(Termsofuse);
	}
	
	public void clickLogout(){
		click(userMenu,"UserMenu");
		click(logout, "Logout");
	}
	
	public void clickSignInLink(){
		click(signInLink, "Sign In", submitBtn, 1);
	}
	
	public void clickSignInLink1() {
		click(signInLink1, "Sign In");
	}
	
	public void clickSignInLinkinOtherLang(TestDevice device) {
		if((device != null && (device.getName().trim().equalsIgnoreCase("mini-tablet") || device.getName().trim().equalsIgnoreCase("mobile"))) || driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) {
			clickSignInLink1();
		}
	}
	
	public String getSignInTextInOtherLang(){
		return getText(signInLink1);
	}
	
	public void clickSignUpLink(){
		if(checkIfElementPresent(signUpLink))
			click(signUpLink, "Sign Up");
	}
	
	public void clickSignInReactLink() {
		if(checkIfElementPresent(signInReactLink))
			click(signInReactLink, "Sign in");
	}
	
	public void clickForgotPasswordLink(){
		click(forgotpassword, "Forgot password");
	}
	
	public void typeEmailAddress(String emailAddress){
		WebElement we = getElementWhenVisible(this.emailAddress);
		if(driverType.trim().toUpperCase().contains("SAFARI")) {
			we.clear();
			we.sendKeys(emailAddress, Keys.TAB);
		} else {
			type(we, "Email address", emailAddress);
			we.sendKeys(Keys.TAB);
		}
	}
	
	public String getEmailAddress(){
		WebElement we = getElementWhenVisible(this.emailAddress);
		return we.getAttribute("value");
	}
	
	public void enterEmailAddress(String emailAddress){
		WebElement we = getElementWhenVisible(this.emailAddress);
		we.clear();
		we.sendKeys(emailAddress, Keys.TAB);
		getElementWhenVisible(componentSubHeading).click();
		int counter = 10;
		while(!isErrorMessageDisplayed() && counter > 0){
			if(driverType.trim().toUpperCase().contains("IOS")) {
				click(backIcon, "Back icon");
				clickSignInLink();
			}
			if(!isErrorMessageDisplayed()) {
				String emailAddressValue = getEmailAddress();
				if(emailAddressValue.trim().equalsIgnoreCase("")) {
					WebElement we1 = getElementWhenVisible(this.emailAddress);
					we1.sendKeys(emailAddress, Keys.TAB);
					getElementWhenVisible(componentSubHeading).click();
				}
			}
			counter--;
		}
		Assert.assertTrue(isErrorMessageDisplayed(), "Verify error message is displayed");
	}
	
	public void typeEmail(String emailAddress) throws AWTException{
		WebElement we = getElementWhenVisible(this.emailAddress);
		we.clear();
		we.sendKeys(emailAddress, Keys.TAB);
		getElementWhenVisible(componentSubHeading).click();
			
		int counter = 10;
		while(!isSendEnabled() && counter > 0){
			if(driverType.trim().toUpperCase().contains("IOS")) {
				click(backIcon, "Back icon");
				clickSignInLink();
			}
			if(!isSendEnabled()) {
				String emailAddressValue = getEmailAddress();
				if(emailAddressValue.trim().equalsIgnoreCase("")) {
					WebElement we1 = getElementWhenVisible(this.emailAddress);
					we1.sendKeys(emailAddress, Keys.TAB);
					getElementWhenVisible(componentSubHeading).click();
				}
			}
			counter--;
		}
		Assert.assertTrue(isSendEnabled(), "Verify send button is enabled");
	}
	
	public boolean isSendEnabled() {
		String value = getAttribute(submitBtn, "disabled");
		return value == null ? true : value.trim().equalsIgnoreCase("true") ? false : true;
	}
	
	public void clickSendButton(){
		click(submitBtn, "Send");
	}
	
	public void clickCopyright(){
		click(CopyrightLink, "Copyright © 2017 Ticketmaster All rights reserved.");
	}
	
	public void clickPolicy(){
		click(Policylink,"Privacy Policy");
	}
	
	public void clickTerms(){
		click(Terms,"Terms of Use");
		getElementWhenVisible(Termsofuse, 10);
	}
	
	public boolean isWelcomePageDisplayed(){
		return checkIfElementPresent(welcome1);
	}

	public boolean isNotificationBannerDisplayed(){
		return checkIfElementPresent(notificationBanner, 8);
	}
	
	public boolean isHomepageDisplayed(TestDevice device){
		if((device != null && (device.getName().trim().equalsIgnoreCase("mini-tablet") || device.getName().trim().equalsIgnoreCase("mobile"))) || driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) {
			getElementWhenVisible(signInLink);
		} else {
			getElementWhenVisible(reactComponent);
		}
		return true;
	}
	
	public boolean isSignInPageDisplayed(){
		return checkIfElementPresent(submitBtn);
	}
	
	public boolean isSignUpPageDisplayed(){
		return checkIfElementPresent(submitBtn);
	}
	
	public boolean isForgotpasswordPageDisplayed(){
		return checkIfElementPresent(submitBtn);
	}
	
	public boolean isErrorMessageDisplayed(){
		return checkIfElementPresent(errorMessage, 1);
	}
	
	public void clickCancelLink(){
		click(cancelLink, "Cancel");
	}
	
	public void clickPasswordLink(){
		click(passwordLink, "Password");
	}
	
	public void typePassword(String password){
		WebElement we = getElementWhenVisible(this.passwordLink);
		we.clear();
		we.sendKeys(password, Keys.TAB);
		if(!driverType.trim().toUpperCase().contains("IE"))
			getElementWhenVisible(passwordShowHide).click();
	}
	
	public void clickRememberMe() {
		click(rememberCheckbox, "Remember me", 1);
	}
	
	public void clickSignInButton(){
		click(submitBtn, "Sign In");
	}
	
	public void login(String emailaddress, String password, TestDevice device, boolean interstitial){
		if(!interstitial){
			if((device != null && (device.getName().trim().equalsIgnoreCase("mini-tablet") || device.getName().trim().equalsIgnoreCase("mobile"))) || driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) {
				clickSignInLink();
			}
		}
		
		clickSignInReactLink();
		
		if(emailaddress.trim().equalsIgnoreCase("")){
			emailaddress = Dictionary.get("EMAIL_ADDRESS").trim();
			password = Dictionary.get("PASSWORD").trim();
		}
		
		if(emailaddress.trim().equalsIgnoreCase("") || password.trim().equalsIgnoreCase("") || emailaddress.trim().equalsIgnoreCase("EMPTY") || password.trim().equalsIgnoreCase("EMPTY")) {
			throw new SkipException("Please provide valid email address and password");
		}

    	typeEmailAddress(emailaddress);
		typePassword(password);
		clickRememberMe();
		clickSignInButton(); 
		
		Hamburger hamburger = new Hamburger(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
		Assert.assertTrue(hamburger.waitforLoggedInPage(device), "Verify user get logged in");
//		if(!driverType.trim().toUpperCase().contains("ANDROID") && !driverType.trim().toUpperCase().contains("IOS")) {
    		try {
    			((JavascriptExecutor) getDriver()).executeScript("$('#doorbell-button').remove()");
    		} catch(Exception ex) {
    			//Do Nothing
    		}
//    	}
	}
	
	public void stplogin(String emailaddress, String password, TestDevice device){
		if(emailaddress.trim().equalsIgnoreCase("")){
			emailaddress = Dictionary.get("EMAIL_ADDRESS").trim();
			password = Dictionary.get("PASSWORD").trim();
		}
		
		if(emailaddress.trim().equalsIgnoreCase("") || password.trim().equalsIgnoreCase("") || emailaddress.trim().equalsIgnoreCase("EMPTY") || password.trim().equalsIgnoreCase("EMPTY")) {
			throw new SkipException("Please provide valid email address and password");
		}

    	typeEmailAddress(emailaddress);
		typePassword(password);
		clickRememberMe();
		clickSignInButton(); 
		
		STP stp = new STP(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert);
		Assert.assertTrue(stp.waitforStpLoggedInPage(device), "Verify user get logged in");
	}
	
	public void createaccount(TestDevice device, boolean interstitial) {
		if(!interstitial){
			if((device != null && (device.getName().trim().equalsIgnoreCase("mini-tablet") || device.getName().trim().equalsIgnoreCase("mobile"))) || driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) {
				clickSignInLink();
			}
		}
		
		clickSignUpLink();
		if(checkIfElementPresent(firstName, 1))
			type(firstName, "First name", "Test", true);
		if(checkIfElementPresent(lastName, 1))
			type(lastName, "Last name", "Test", true);
		
		//******************* Fetch Current TimeStamp ************************
		java.util.Date today = new java.util.Date();
		Timestamp now = new java.sql.Timestamp(today.getTime());
		String tempNow[] = now.toString().split("\\.");
		final String sStartTime = tempNow[0].replaceAll(":", "").replaceAll(" ", "").replaceAll("-", "");
		String emailaddress = "test" + sStartTime + "@example.com";
		String password = "123456";
		typeEmailAddress(emailaddress);
		typePassword(password);
		click(termsConditionsCheckBox, "Terms & Conditions");
		click(submitBtn, "Sign Up");
		if(Environment.get("tncpop").trim().equalsIgnoreCase("false")) {
			//Do Nothing
		} else {
			try {
				click(agree, "Agree", null, By.name("AGREE"), "Agree", 10);
			} catch(Exception ex) {
				//Do Nothing
			}
		}
		Hamburger hamburger = new Hamburger(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
		Assert.assertTrue(hamburger.waitforLoggedInPage(device), "Verify user get logged in");
		Dictionary.put("NEW_EMAIL_ADDRESS", emailaddress);
		Dictionary.put("NEW_PASSWORD", password);
//		if(!driverType.trim().toUpperCase().contains("ANDROID") && !driverType.trim().toUpperCase().contains("IOS")) {
    		try {
    			((JavascriptExecutor) getDriver()).executeScript("$('#doorbell-button').remove()");
    		} catch(Exception ex) {
    			//Do Nothing
    		}
//    	}
	}
	
	public void get(String uri) {
		load(uri);
		WebElement we = getElementWhenPresent(By.xpath(".//input[@name='email'] | .//div[@class='mobile-signin']//span[text()='Sign In']"));
		Assert.assertNotNull(we, "Verify page is displayed");
	}
}