package org.iomedia.galen.tests;

import org.iomedia.framework.Driver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.iomedia.galen.pages.*;

public class Homescreen extends Driver{
	
	private Homepage homepage; 
	
	@BeforeMethod(alwaysRun=true)
	public void init(){
		homepage = new Homepage(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert);
	}

	@Test(groups={"smoke", "miscUi","regression","prod"}, dataProvider="devices", priority = 1)
	public void verifyHomePage(TestDevice device) throws Exception{
		load("/");
		Assert.assertTrue(homepage.isHomepageDisplayed(device), "Verify homepage is displayed");
		checkLayout(Dictionary.get("SPEC_FILE_NAME"), device.getTags());
	}
}