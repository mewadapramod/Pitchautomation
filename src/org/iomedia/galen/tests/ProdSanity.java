package org.iomedia.galen.tests;

import org.iomedia.framework.Driver;
import org.testng.annotations.Test;

import cucumber.api.CucumberOptions;

@CucumberOptions(plugin = "json:target/cucumber-report-feature-composite.json", format = "pretty", features = "features/prodsanity.feature", glue = {"org.iomedia.galen.steps"}, monochrome = true, strict = true)
public class ProdSanity extends Driver {
	
	@Test(groups={"smoke","regression","sso", "prod"}, priority = 1)
	public void prodSanityVerification() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups={"smoke","regression", "prod"}, priority = 2)
	public void verifyPrivacyLink() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups={"smoke","regression", "prod", "cms"}, priority = 3)
	public void verifyCMSLogin() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
}