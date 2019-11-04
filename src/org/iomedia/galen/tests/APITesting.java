package org.iomedia.galen.tests;

import org.iomedia.framework.Driver;
import org.testng.annotations.Test;
import cucumber.api.CucumberOptions;


@CucumberOptions(plugin = "json:target/cucumber-report-feature-composite.json", format = "pretty", features = {"features/apiTesting.feature"}, glue = {"org.iomedia.galen.steps"}, monochrome = true, strict = true)	
public class APITesting extends Driver{
	
		
	@Test(groups={"smoke","regression","prod", "api"}, priority = 0)
	public void createCampaignByPrivatePurlCreatePurlAPI() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups={"smoke","regression","prod", "api"}, priority = 1)
	public void createCampaignByPublicPurlCreatePurlAPI() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups={"smoke","regression","prod", "api"}, priority = 2)
	public void changePurlStatusDraftBySavePurl() throws Throwable {
			runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups={"smoke","regression","prod", "api"}, priority = 3)
	public void createCampaignPrivatePurl() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups={"smoke","regression","prod", "api"}, priority = 4)
	public void createCampaignPublicPurl() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups={"smoke","regression","prod", "api"}, priority = 5)
	public void changePurlStatusPublicBySavepurl() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups={"smoke","regression","prod", "api"}, priority = 6)
	public void changePurlStatusPrivateBySavepurl() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups={"smoke","regression","prod", "api"}, priority = 7)
	public void verifyPurlIdBySavepurl() throws Throwable {
		System.out.println("fdgn   "+Dictionary.get("SCENARIO"));
		runScenario(Dictionary.get("SCENARIO"));
	}

	@Test(groups={"smoke","regression","prod", "api"}, priority = 8)
	public void campaignNotCreatedDraftPurlSavePurl() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}

	@Test(groups={"smoke","regression","prod", "api"}, priority = 9)
	public void campaignNotCreatedPrivatePurlUser2SavePurl() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups={"smoke","regression","prod", "api"}, priority = 10)
	public void campaignNotCreatedDraftPurlUser2SavePurl() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups={"smoke","regression","prod", "api"}, priority = 11)
	public void campaignCreatedPublicPurlUser2CreatePurl() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups={"smoke","regression","prod", "api"}, priority = 12)
	public void campaignNotCreateddraftCreatePurl() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups={"smoke","regression","prod", "api"}, priority = 13)
	public void campaignNotCreatedPrivatePurlUser2CreatePurl() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups={"smoke","regression","prod", "api"}, priority = 14)
	public void createCampaignByPublicPurlUser2CreatePurlAPI() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}

	@Test(groups={"smoke","regression","prod", "api"}, priority = 15)
	public void campaignNotCreateddraftUser2CreatePurl() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}

	@Test(groups={"smoke","regression","prod", "api"}, priority = 16)
	public void purlWithMultipleCampaignsDifferentUser() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}

	@Test(groups={"smoke","regression","prod", "api"}, priority = 17)
	public void purlWithMultipleCampaignsSingleUser() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups={"smoke","regression","prod", "api"}, priority = 18)
	public void singleEmailTemplateMultipleCampaignsSingleUser() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups={"smoke","regression","prod", "api"}, priority = 19)
	public void singleEmailTemplateMultipleCampaignDifferentUser() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}

	@Test(groups={"smoke","regression","prod", "api"}, priority = 20)
	public void compareMasterTemplateSavePurl() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups={"smoke","regression","prod", "api"}, priority = 21)
	public void invalidETemplateCampaignNotCreated() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups={"smoke","regression","prod", "api"}, priority = 22)
	public void invalidPurlIDCampaignNotCreated() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups = { "smoke", "regression", "prod", "api" }, priority = 23)
	public void publicPurlCreatedByCreatePurlCloneBySavePurl() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups = { "smoke", "regression", "prod", "api" }, priority = 24)
	public void privatePurlCreatedByCreatePurlCloneBySavePurl() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	

	@Test(groups = { "smoke", "regression", "prod", "api" }, priority = 25)
	public void draftPurlCreatedByCreatePurlCloneBySavePurl() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups = { "smoke", "regression", "prod", "api" }, priority = 26)
	public void publicPurlCreatedBySavePurlCloneBySavePurl() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}

	@Test(groups = { "smoke", "regression", "prod", "api" }, priority = 27)
	public void privatePurlCreatedBySavePurlCloneBySavePurl() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));

	}
	
	@Test(groups = { "smoke", "regression", "prod", "api" }, priority = 28)
	public void draftPurlCreatedBySavePurlCloneBySavePurl() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups = {"smoke", "regression", "prod", "api"}, priority = 29)
	public void createPurlandGetPurlId() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups = { "smoke", "regression", "prod", "api" }, priority = 30)
	public void createPurlpurlNameDuplicate() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	

	@Test(groups = { "smoke", "regression", "prod", "api" }, priority = 31)
	public void verifyPublicPurlVisibility() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}

	@Test(groups = { "smoke", "regression", "prod", "api" }, priority = 32)
	public void verifyPrivatePurlVisibility() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups = {"smoke","regression","prod","api"}, priority = 33)
	public void verifyDraftPurlVisibility() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}

	@Test(groups = { "smoke", "regression", "prod", "api" }, priority = 34)
	public void verifyClonePurlStatus() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups = { "smoke", "regression", "prod", "api" }, priority = 35)
	public void createEmailtemplateId() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	

	@Test(groups = { "smoke", "regression", "prod", "api" }, priority = 36)
	public void verifyUpdatePurlTypeDraftToPublic() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups = { "smoke", "regression", "prod", "api" }, priority = 37)
	public void veifyUpdatePurlTypeDraftToPrivate() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}


	@Test(groups = { "smoke", "regression", "prod", "api" }, priority = 38)
	public void verifyUpdatePurlTypePrivateToPublicFromOwner() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups = { "smoke", "regression", "prod", "api" }, priority = 39)
	public void verifyUpdatePurlTypepublicToDraft() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups = { "smoke", "regression", "prod", "api" }, priority = 40)
	public void generateCampaignIdThenUpdatePNamePTypePrivate() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}

	@Test(groups = { "smoke", "regression", "prod", "api" }, priority = 41)
	public void generateCampaignIdThenUpdatePNamePTypePrivateFromPOwner() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	

	@Test(groups = { "smoke", "regression", "prod", "api" }, priority = 42)
	public void generateCampaignIdThenUpdatePNameFromOtherUserAccount() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	

	@Test(groups = { "smoke", "regression", "prod", "api" }, priority = 43)
	public void generateCampaignIdThenUpdatePNameFromPOwnerAccount() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));	
	}
	
	@Test(groups = { "smoke", "regression", "prod", "api" }, priority = 44)
	public void verifyUpdatePurlTypePrivate() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups = { "smoke", "regression", "prod", "api" }, priority = 45)
	public void verifyUpdatePurlTypePrivateToPublic() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}

	@Test(groups = { "smoke", "regression", "prod", "api" }, priority = 46)
	public void verifyupdatePurlTypePublicToPrivate() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}	

	@Test(groups = { "smoke", "regression", "prod", "api" }, priority = 47)
	public void generateCampaignIdThenUpdatePurlTypeDraft() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups = { "smoke", "regression", "prod", "api" }, priority = 48)
	public void generateCampaignIdThenUpdatePurlTypepublic() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups = { "smoke", "regression", "prod", "api" }, priority = 49)
	public void createPurlWithInvalidPurlOwner() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups = { "smoke", "regression", "prod", "api" }, priority = 50)
	public void createPurlWithInvalidSlideOwner() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	@Test(groups = { "smoke", "regression", "prod", "api" }, priority = 51)
	public void verifyCampaignWithDuplicateCampaignName() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups = { "smoke", "regression", "prod", "api" }, priority = 52)
	public void createPurlWithInvalidTemplateId() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups = { "smoke", "regression", "prod", "api" }, priority = 53)
	public void updatePurlWithInvalidTemplateId() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
		
	@Test(groups = { "smoke", "regression", "prod", "api" }, priority = 54)
	public void savePurlWithInvalidTemplateId() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	
	@Test(groups = { "smoke", "regression", "prod", "api" }, priority = 55)
	public void searchPurlwithInvalidPurlId() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups = { "smoke", "regression", "prod", "api" }, priority = 56)
	public void updatePurlWithInvalidPurlId() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	@Test(groups = { "smoke", "regression", "prod", "api" }, priority = 57)
	public void clonePurlWithDuplicatePName() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
	
	
	@Test(groups = { "smoke", "regression", "prod", "api" }, priority = 58)
	public void updatePurlWithInvalidPurlOwner() throws Throwable {
		runScenario(Dictionary.get("SCENARIO"));
	}
}
