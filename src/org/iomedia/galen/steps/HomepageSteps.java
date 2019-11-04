package org.iomedia.galen.steps;

import java.util.Set;

import org.iomedia.common.BaseUtil;
import org.iomedia.galen.common.Utils;
import org.iomedia.galen.pages.DashboardSection;
import org.iomedia.galen.pages.Homepage;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebElement;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class HomepageSteps {
	
	Homepage homepage;
	BaseUtil base;
	Utils utils;
	org.iomedia.framework.Assert Assert;
	String driverType;
	DashboardSection section;
	
	public HomepageSteps(Homepage homepage, BaseUtil base, Utils utils, org.iomedia.framework.Assert Assert, DashboardSection section) {
		this.homepage = homepage;
		this.base = base;
		this.utils = utils;
		this.Assert = Assert;
		this.section = section;
		driverType = base.driverFactory.getDriverType().get();
	}
	
	@When("^User verify congratulation message$")
	public void user_verify_congratulation_msg() {
		Assert.assertTrue(homepage.getLoginMessage().contains("Congratulations"), "Verify 'Congratulations' text is displayed on login screen");
	}

	@Given("^User is on (.+) Page$")
	public void user_is_on_Page(String uri) {
		uri = (String) base.getGDValue(uri);
		homepage.get(uri);
	}
	
	@When("^User enters (.+) and (.+)$")
	public void user_enters_and(String emailaddress, String password) {
		emailaddress = (String) base.getGDValue(emailaddress);
		password = (String) base.getGDValue(password);
		homepage.login(emailaddress, password, null, false);
	}
	
	@When("^User landed on interstitial page and enters (.+) and (.+)$")
	public void user_landed_on_interstitial_page_and_enters_and(String emailaddress, String password) {
		emailaddress = (String) base.getGDValue(emailaddress);
		password = (String) base.getGDValue(password);
		homepage.login(emailaddress, password, null, true);
	}
	
	@When("^User navigates to STP homepage after entering (.+) and (.+)$")
	public void user_navigates_to_stp_homepage_after_entering_and(String emailaddress, String password) {
		emailaddress = (String) base.getGDValue(emailaddress);
		password = (String) base.getGDValue(password);
		homepage.stplogin(emailaddress, password, null);
	}
	
	@Then("^User creates account$")
	public void user_creates_account() {
		homepage.createaccount(null, false);
	}
	
	@Given("^User navigates to (.+) from NAM$")
	public void user_navigates_to_from_nam(String uri) {
		uri = (String) base.getGDValue(uri);
		utils.navigateTo(uri);
	}
	
	@Then("^NAM homepage is displayed$")
	public void nam_homepage_is_displayed() {
		WebElement we = base.getElementWhenPresent(By.xpath(".//input[@name='email'] | .//div[@class='mobile-signin']//span[text()='Sign In']"));
		Assert.assertNotNull(we, "Verify NAM homepage is displayed");
	}
	
	@Given("^User clicked on signup link$")
	public void user_clicked_on_signup_link() {
		if(driverType.trim().toUpperCase().contains("ANDROID") || driverType.trim().toUpperCase().contains("IOS")) {
			homepage.clickSignInLink();
		}
		homepage.clickSignUpLink();
	}
	
	@When("^User clicked on privacy link$")
	public void user_clicked_on_privacy_link() {
		homepage.clickPrivacyTermsLink();
	}
	
	@Then("^Verify user is navigated to correct link on clicking privacy policy or terms link$")
	public void verify_user_is_navigated_to_correct_link_on_clicking_privacy_policy_or_terms_link() throws Exception {
		String url = homepage.getPrivacyTermsLinkUrl();
		
		Assert.assertTrue(!url.trim().equalsIgnoreCase(""), "Verify privacy or terms conditions link url is set");
		
		if(!url.trim().contains(base.Environment.get("APP_URL"))) {
			url = base.Environment.get("APP_URL") + url.trim().substring(url.trim().lastIndexOf("/"));
		}
		
		Set<Cookie> cookies = base.getDriver().manage().getCookies();
		int code = section.checkStatuscode(url, cookies);
		Assert.assertTrue(code==200 || code==302 || code==301, "Verify URL status code");
		Assert.assertTrue(url.trim().contains("privacy-policy") || url.trim().contains("terms"), "Verify user is redirecting to correct page");
	}
}