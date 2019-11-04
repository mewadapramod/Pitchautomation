package org.iomedia.galen.steps;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.List;

import org.iomedia.common.BaseUtil;
import org.iomedia.framework.Driver;
import org.iomedia.galen.common.PitchmanApi;
import org.iomedia.galen.common.Utils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;

import com.google.gson.JsonObject;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import gherkin.deps.com.google.gson.Gson;
import netscape.javascript.JSObject;

public class PurlSteps {

	Utils utils;
	BaseUtil base;
	PitchmanApi api;

	public PurlSteps(Utils utils, BaseUtil base, PitchmanApi api, org.iomedia.framework.Assert Assert) {
		this.utils = utils;
		this.base = base;
		this.api = api;
	}

	ArrayList<Object> al = new ArrayList<Object>();

	@When("^Generate draft purl using savepurlAPI (.+) and (.+)$")
	public void generate_draft_purl_savepurl(String AccessToken, String PurlName) throws Exception {
		AccessToken = (String) base.getGDValue(AccessToken);
		PurlName = (String) base.getGDValue(PurlName);
		base.Dictionary.put("PURLID", api.createAndGetSavePurl(AccessToken,PurlName));
	}

	@When("^Create campaign by using (.+), (.+), (.+), (.+), (.+) and (.+)$")
	public void createCampaign(String AccessToken, String PurlID, String CampaignName, String eTemplateID,String masterETemplateId,String email) throws Exception {
		AccessToken = (String) base.getGDValue(AccessToken);
		PurlID = (String) base.getGDValue(PurlID);
		CampaignName = (String) base.getGDValue(CampaignName);
		eTemplateID = (String) base.getGDValue(eTemplateID);
	    masterETemplateId = (String) base.getGDValue(masterETemplateId);
	    email = (String) base.getGDValue(email);
		api.createCampaignGetCampaignId(AccessToken,PurlID,CampaignName,eTemplateID,masterETemplateId,email);
	}

	@Given("^Generate Unique Purl Name using (.+)$")
	public void generate_purl_name(String purlName) {
		purlName = (String) base.getGDValue(purlName);
		base.Dictionary.put("NEW_PURLNAME", api.uniqueNameGenerator(purlName));
	}



 @When("^Generate purl by createpurl API using (.+), (.+), (.+) and (.+)$")
	public void generate_Purl_ID(String AccessToken, String PurlName, String PurlType, String templateID) throws Exception {
		AccessToken = (String) base.getGDValue(AccessToken);
		PurlName = (String) base.getGDValue(PurlName);
		PurlType = (String) base.getGDValue(PurlType);	
		templateID = (String) base.getGDValue(templateID);
		api.createPurlandGetPurlId(AccessToken, PurlName, PurlType, base.Dictionary.get("userId"), base.Dictionary.get("userId"),templateID);
	}



	@Then("^Verify purlID using (.+) and (.+)$")
	public void verify_purlID_purlName_searchpurl(String AccessToken, String PurlID) throws Exception {
		AccessToken = (String) base.getGDValue(AccessToken);
		PurlID = (String) base.getGDValue(PurlID);
		JSONObject response = api.searchPurlID(AccessToken, PurlID);
		JSONObject Obj = response.getJSONObject("data").getJSONObject("Purl");
		String purl_ID = Obj.get("purlId").toString();
	//	base.Dictionary.put("PURLID_SEARCHPURL", purlID);
		Assert.assertEquals(PurlID, purl_ID,"PurlID is not matching");
		String purlName = Obj.get("name").toString();
	//	base.Dictionary.put("purlName_searchpurl", purlName);
		Assert.assertEquals(base.Dictionary.get("NEW_PURLNAME"), purlName,"PurlName is not matching");
	}

	@Then("^Verify status of purl as draft using (.+) and (.+)$")
	public void verify_purlStatusDraft_searchpurl(String AccessToken, String PurlID) throws Exception {
		AccessToken = (String) base.getGDValue(AccessToken);
		PurlID = (String) base.getGDValue(PurlID);
		JSONObject response = api.searchPurlID(AccessToken, PurlID);
		JSONObject Obj = response.getJSONObject("data").getJSONObject("Purl");
		String purlType = Obj.get("purlType").toString();
		//base.Dictionary.put("purlType_searchpurl", purlType);
		Assert.assertEquals(base.Dictionary.get("DRAFT_PURL"), purlType, "Purl status is not matching");
	}
		
	@Then("^Search and verify cloned purl and purlType should be draft by default using (.+), (.+) and (.+)$")
	public void verify_purlStatusDraft_clonedpurl(String AccessToken, String PurlId, String PurlType) throws Exception {
		AccessToken = (String) base.getGDValue(AccessToken);
		PurlId = (String) base.getGDValue(PurlId);
		PurlType = (String) base.getGDValue(PurlType);
		JSONObject response = api.searchPurlID(AccessToken, PurlId);
		JSONObject Obj = response.getJSONObject("data").getJSONObject("Purl");
		String ResponsePurlType = Obj.get("purlType").toString();
		// JSONObject Object = response.getJSONObject("data").getJSONObject("Purl");
		String ResponsePurlName = Obj.get("name").toString();
		// JSONObject purlOwner = response.getJSONObject("data").getJSONObject("Purl");
		String ResponsePurlOwner = Obj.get("purlOwner").toString();
		// base.Dictionary.put("purlType", ResponsePurlType);
		Assert.assertEquals(PurlType, ResponsePurlType, "purlType is not matched");
		Assert.assertEquals(base.Dictionary.get("NEW_PURLNAME"), ResponsePurlName, "purlName is not matched");
		Assert.assertEquals(base.Dictionary.get("userId"), ResponsePurlOwner, "purlOnwer  is not matched");
	}

	@Then("^Change status of purl as private using (.+) and (.+)")
	public void change_status_savepurl_private(String AccessToken, String PurlId) throws Exception {
		AccessToken = (String) base.getGDValue(AccessToken);
		PurlId = (String) base.getGDValue(PurlId);
		api.updatePurlInfo(AccessToken, PurlId, base.Dictionary.get("PRIVATE_PURL"));
	}

	@Then("^Verify status of purl as private using (.+) and (.+)$")
	public void verify_status_purl_private(String AccessToken, String PurlId) throws Exception {
		AccessToken = (String) base.getGDValue(AccessToken);
		PurlId = (String) base.getGDValue(PurlId);
		JSONObject response = api.searchPurlID(AccessToken, PurlId);
		JSONObject Obj = response.getJSONObject("data").getJSONObject("Purl");
		String purlType = Obj.get("purlType").toString();
	//	base.Dictionary.put("purlType_searchpurl", purlType);
		Assert.assertEquals(base.Dictionary.get("PRIVATE_PURL"),purlType, "Purl status is not matching");
	}

	@Then("^Change status of purl as draft using (.+) and (.+)")
	public void change_status_savepurl_draft(String AccessToken, String PurlId) throws Exception {
		AccessToken = (String) base.getGDValue(AccessToken);
		PurlId = (String) base.getGDValue(PurlId);
		api.updatePurlInfo(AccessToken, PurlId, base.Dictionary.get("DRAFT_PURL"));
	}
	
	@Then("^Change status of purl as public using (.+) and (.+)")
	public void change_status_savepurl_public(String AccessToken, String PurlId) throws Exception {
		AccessToken = (String) base.getGDValue(AccessToken);
		PurlId = (String) base.getGDValue(PurlId);
		api.updatePurlInfo(AccessToken, PurlId, base.Dictionary.get("PUBLIC_PURL"));
	}

	@Then("^Verify status of purl as public using (.+) and (.+)$")
	public void verify_status_purl_public(String AccessToken, String PurlId) throws Exception {
		AccessToken = (String) base.getGDValue(AccessToken);
		PurlId = (String) base.getGDValue(PurlId);
		JSONObject response = api.searchPurlID(AccessToken, PurlId);
		JSONObject Obj = response.getJSONObject("data").getJSONObject("Purl");
		String purlType = Obj.get("purlType").toString();
	//	base.Dictionary.put("purlType_searchpurl", purlType);
		Assert.assertEquals(base.Dictionary.get("PUBLIC_PURL"),purlType,"Purl status is not matching");
	}

	@Then("^Verify PurlID should be null using (.+) and (.+)$")
	public void searchbypurlID_notfound(String AccessToken, String PurlId) throws Exception {
		AccessToken = (String) base.getGDValue(AccessToken);
		PurlId = (String) base.getGDValue(PurlId);
		try {
			JSONObject response = api.searchPurlID(AccessToken, PurlId);
			Assert.assertNull(response, "Test case failed. PurlID should not be found");
		} catch (Exception e) {
			System.out.println("Test case passed. PurlID is found");
		}
	}

	@When("^Create email templateID using (.+), (.+) and (.+)$")
	public void createEmailTemplate(String AccessToken,String templateName,String templateID) throws Exception {
		AccessToken = (String) base.getGDValue(AccessToken);
		templateName = (String) base.getGDValue(templateName);
		templateID = (String) base.getGDValue(templateID);	
		api.createEmailtemplate(AccessToken,templateName,templateID);
	}

	
	
	@Then("^Campaign should not be created with invalid data using (.+), (.+), (.+), (.+) and (.+)$")
	public void invalidPurlIDCampaignNotCreated(String AccessToken,String PurlID,String eTemplateID,String Email,String mTemplateId) throws Exception {
		AccessToken = (String) base.getGDValue(AccessToken);
		PurlID = (String) base.getGDValue(PurlID);
		eTemplateID = (String) base.getGDValue(eTemplateID);
		Email = (String) base.getGDValue(Email);
		mTemplateId = (String) base.getGDValue(mTemplateId);

		api.campaign_failed(AccessToken,PurlID,eTemplateID,Email, mTemplateId,base.Dictionary.get("CAMPAIGN_NAME"));
		Assert.assertEquals(base.Dictionary.get("CAMPAIGN_ERROR_MSG"), base.Dictionary.get("failedMessage"),"Failed message is not matched");
		Assert.assertEquals(base.Dictionary.get("CAMPAIGN_ERROR_CODE"), base.Dictionary.get("failedStatusCode"), "Failed status code is not matching");
	}
	
	

	@Then("^Verify campaignDetails by campaignById Api using (.+) and (.+)$")
	public void search_campaigndetail_campaignbyid(String AccessToken, String CampaignId) throws Exception {
		AccessToken = (String) base.getGDValue(AccessToken);
		CampaignId = (String) base.getGDValue(CampaignId);
		JSONObject response = api.searchCampaignById(AccessToken, CampaignId);
		
		JSONObject Obj = response.getJSONObject("data").getJSONObject("Campaign");
		String campaign_ID = Obj.get("campaignId").toString();
	//	base.Dictionary.put("campaignID_search", campaignID);
		Assert.assertEquals(CampaignId, campaign_ID,"Campaign Id is not matching");
		String campaign_Name = Obj.get("name").toString();
	//	base.Dictionary.put("campaignName_search", campaignname);
		String createdBy = Obj.get("createdBy").toString();
		Assert.assertEquals(base.Dictionary.get("createdBy1"), createdBy, "createdBy  is not matched");
		Assert.assertEquals(base.Dictionary.get("CAMPAIGN_NAME"), campaign_Name,"Campaign Name is not matching");
		JSONObject Obj1 = response.getJSONObject("data").getJSONObject("Campaign").getJSONObject("purl");
		String purlname = Obj1.get("name").toString();
		String purlId = Obj1.get("purlId").toString();
	//	base.Dictionary.put("purlName_search", purlname);
	//	base.Dictionary.put("purlId_search", purlId);
		Assert.assertEquals(base.Dictionary.get("NEW_PURLNAME"), purlname ,"Purl Name is not matching");
		Assert.assertEquals(base.Dictionary.get("PURLID"),purlId,"Purl ID is not matching");
		api.waitForSentState(AccessToken, CampaignId, "Sent");
	}

	@When("^Verify campaign get associated with PurlID using (.+), (.+) and (.+)$")
	public void search_campaign_campaignbypurlid(String AccessToken, String PurlID, String CampID) throws Exception {
		AccessToken = (String) base.getGDValue(AccessToken);
		PurlID = (String) base.getGDValue(PurlID);
		CampID = (String) base.getGDValue(CampID);
		JSONObject response = api.searchCampaignListByPurlID(AccessToken, PurlID);
		JSONArray Obj = response.getJSONObject("data").getJSONObject("CampaignListByPurlId").getJSONArray("Campaigns");
		JSONObject ob = Obj.getJSONObject(0);
		String campaignID = ob.getString("campaignId");
		String campaignName = ob.getString("name");
		String createdBy = ob.get("createdBy").toString();
		Assert.assertEquals(base.Dictionary.get("createdBy1"), createdBy, "createdBy  is not matched");
	//	base.Dictionary.put("campaignName_search", campaignName);
	//	base.Dictionary.put("campaignID_search", campaignID);
		Assert.assertEquals(CampID, campaignID,"Campaign Id is not matching");
		Assert.assertEquals(base.Dictionary.get("CAMPAIGN_NAME"),campaignName,"Campaign Name is not matching");
	}



	@Then("^Search and Verify data details get matched using (.+), (.+) and (.+)$")
	public void verify_details_match(String AccessToken, String PurlID,String purlType) throws Exception {
		AccessToken = (String) base.getGDValue(AccessToken);
		purlType = (String) base.getGDValue(purlType);
		PurlID = (String) base.getGDValue(PurlID);
		JSONObject response = api.searchPurlID(AccessToken, PurlID);

		JSONObject Obj = response.getJSONObject("data").getJSONObject("Purl");
	//	JSONObject obj1 = Obj.getJSONArray("slides").getJSONObject(0);
	//	System.out.println(obj1.get("slideOwner").toString());
	//	base.Dictionary.put("SLIDE_OWNER",obj1.getString("slideOwner"));
		String ResponsePurlType = Obj.get("purlType").toString();
		String ResponsePurlName = Obj.get("name").toString();
		String ResponsePurlOwner = Obj.get("purlOwner").toString();
		String createdBy = Obj.get("createdBy").toString();
		String modifiedBy = Obj.get("modifiedBy").toString();
				
		Assert.assertEquals(purlType, ResponsePurlType, "purlType is not matched");
		Assert.assertEquals(base.Dictionary.get("NEW_PURLNAME"), ResponsePurlName, "purlName is not matched");
		Assert.assertEquals(base.Dictionary.get("userId"), ResponsePurlOwner, "purlOnwer  is not matched");
		Assert.assertEquals(base.Dictionary.get("userId"), createdBy, "createdBy  is not matched");
		Assert.assertEquals(base.Dictionary.get("userId"), modifiedBy, "modifiedBy  is not matched");
	}

	@Then("^Campaign should not be found using (.+), (.+), (.+) and (.+)$")
	public void campaign_notFound(String AccessToken, String CampaignId, String errCode, String errMessage) throws Exception {
		AccessToken = (String) base.getGDValue(AccessToken);
		CampaignId = (String) base.getGDValue(CampaignId);
		errCode = (String) base.getGDValue(errCode);
		errMessage = (String) base.getGDValue(errMessage);		
		JSONObject response = api.searchCampaignById(AccessToken, CampaignId);
		JSONArray a = response.getJSONArray("errors");
		JSONObject ob = a.getJSONObject(0);
		String message = ob.getString("message");
		String message1 = (new JSONObject(message)).get("message").toString();
		String statusCode = (new JSONObject(message)).get("statusCode").toString();
		Assert.assertEquals(message1, errMessage,"Failed message is not matching");
		Assert.assertEquals(statusCode, errCode, "Status Code is not matching");
	}

	@Then("^Save multiple Campaigns data in dictionary$")
	public void save_multipleCampaigndata() {
		base.Dictionary.put("campaignid_1", base.Dictionary.get("CAMPAIGNID"));
		base.Dictionary.put("campaignName_1", base.Dictionary.get("CAMPAIGN_NAME"));
	}

	@Then("^Verify multiple campaigns data by searchCampaignByPurlID using (.+) and (.+)$")
	public void multiple_campaigns_PurlID(String AccessToken, String PurlID) throws Exception {
		AccessToken = (String) base.getGDValue(AccessToken);
		PurlID = (String) base.getGDValue(PurlID);
		JSONObject response = api.searchCampaignListByPurlID(AccessToken, PurlID);
		JSONArray Obj = response.getJSONObject("data").getJSONObject("CampaignListByPurlId").getJSONArray("Campaigns");
		JSONObject ob = Obj.getJSONObject(0);
		String campaignID = ob.getString("campaignId");
		String campaignName = ob.getString("name");
		Assert.assertEquals(base.Dictionary.get("CAMPAIGNID"), campaignID, "Campaign Id is not matching");
		Assert.assertEquals(base.Dictionary.get("CAMPAIGN_NAME"), campaignName, "Campaign Name is not matching");
		// second object
		JSONObject ob2 = Obj.getJSONObject(1);
		String campaignID_1 = ob2.getString("campaignId");
		String campaignName_1 = ob2.getString("name");
		Assert.assertEquals(base.Dictionary.get("campaignid_1"), campaignID_1, "Campaign Id is not matching");
		Assert.assertEquals(base.Dictionary.get("campaignName_1"), campaignName_1, "Campaign Name is not matching");
	}

	@When("^Save Template Data using token (.+) and templateId (.+)$")
	public void masterTemplate(String AccessToken,String templateId) throws Exception {
		AccessToken = (String) base.getGDValue(AccessToken);
		templateId = (String) base.getGDValue(templateId);
		JSONObject response = api.purlTemplate(AccessToken);
		JSONArray Obj = response.getJSONObject("data").getJSONArray("PurlTemplates");
		
		for (int i = 0; i < Obj.length(); i++) {
			JSONObject templateData = Obj.getJSONObject(i);
			if(templateData.get("templateId").toString().equals(templateId))	{
				JSONArray slides = templateData.getJSONArray("slides");
				for (int j = 0; j < slides.length(); j++) {
					JSONObject slides_data = slides.getJSONObject(j);
					base.Dictionary.put("slidesName[" + j + "]", slides_data.get("slideName").toString());
					base.Dictionary.put("slideNo[" + j + "]", slides_data.get("slideNo").toString());
					base.Dictionary.put("orderBy[" + j + "]", slides_data.get("orderBy").toString());
					base.Dictionary.put("isLocked[" + j + "]", slides_data.get("isLocked").toString());
					base.Dictionary.put("hideSlide[" + j + "]", slides_data.get("hideSlide").toString());
					JSONObject components = slides_data.getJSONArray("components").getJSONObject(0);

					base.Dictionary.put("components_type[" + j + "]", components.get("type").toString());
					base.Dictionary.put("component_name[" + j + "]", components.get("name").toString());
					base.Dictionary.put("component_id[" + j + "]", components.get("id").toString());
					base.Dictionary.put("component_locked[" + j + "]", components.get("locked").toString());
					base.Dictionary.put("component_layer[" + j + "]", components.get("componentLayer").toString());
				}
		}
		}
	}

		//JSONObject slides = Obj.getJSONObject(1);


//		JSONArray slides = Obj.getJSONArray(1);
//		System.out.println(slides.length());
//		System.out.println(slides);
//		for (int i = 0; i < 3; i++) {
//			JSONObject slides_data = slides.getJSONObject(i);
//			base.Dictionary.put("slidesName[" + i + "]", slides_data.get("slideName").toString());
//			base.Dictionary.put("slideNo[" + i + "]", slides_data.get("slideNo").toString());
//			base.Dictionary.put("orderBy[" + i + "]", slides_data.get("orderBy").toString());
//			base.Dictionary.put("isLocked[" + i + "]", slides_data.get("isLocked").toString());
//			base.Dictionary.put("hideSlide[" + i + "]", slides_data.get("hideSlide").toString());
//			JSONObject components = slides_data.getJSONArray("components").getJSONObject(0);
//
//			base.Dictionary.put("components_type[" + i + "]", components.get("type").toString());
//			base.Dictionary.put("component_name[" + i + "]", components.get("name").toString());
//			base.Dictionary.put("component_id[" + i + "]", components.get("id").toString());
//			base.Dictionary.put("component_locked[" + i + "]", components.get("locked").toString());
//			base.Dictionary.put("component_layer[" + i + "]", components.get("componentLayer").toString());
//		}
//
//	}

	@Then("^Call searchpurl and verify details from PurlTemplate using (.+) and (.+)$")
	public void verifyData_savepurl_purlTemplate(String AccessToken, String PurlId) throws Exception {
		AccessToken = (String) base.getGDValue(AccessToken);
		PurlId = (String) base.getGDValue(PurlId);
		JSONObject response = api.searchPurlID(AccessToken, PurlId);
		JSONObject Obj = response.getJSONObject("data").getJSONObject("Purl");
		JSONArray slides = Obj.getJSONArray("slides");
		for (int i = 0; i < slides.length(); i++) {
			JSONObject slides_data = slides.getJSONObject(i);
			JSONObject components = slides_data.getJSONArray("components").getJSONObject(0);
			Assert.assertEquals(base.Dictionary.get("slidesName[" + i + "]"), slides_data.get("slideName").toString());
			Assert.assertEquals(base.Dictionary.get("slideNo[" + i + "]"), slides_data.get("slideNo").toString());
			Assert.assertEquals(base.Dictionary.get("orderBy[" + i + "]"), slides_data.get("orderBy").toString());
			Assert.assertEquals(base.Dictionary.get("isLocked[" + i + "]"), slides_data.get("isLocked").toString());
			Assert.assertEquals(base.Dictionary.get("hideSlide[" + i + "]"), slides_data.get("hideSlide").toString());
			Assert.assertEquals(base.Dictionary.get("components_type[" + i + "]"), components.get("type").toString());
			Assert.assertEquals(base.Dictionary.get("component_name[" + i + "]"), components.get("name").toString());
			Assert.assertEquals(base.Dictionary.get("component_locked[" + i + "]"),
					components.get("locked").toString());
			Assert.assertEquals(base.Dictionary.get("component_layer[" + i + "]"),
					components.get("componentLayer").toString());
			Assert.assertEquals(base.Dictionary.get("component_id[" + i + "]"), components.get("id").toString());
		}
	}

	@Then("^Clone purl using savePurl Api using (.+) and (.+)$")
	public void clonePurl(String AccessToken, String PurlID) throws Exception {
		AccessToken = (String) base.getGDValue(AccessToken);
		PurlID = (String) base.getGDValue(PurlID);
		api.cloneSavePurl(AccessToken, PurlID);
	}
	
	@Then("^Clone purl with duplicate purl name using (.+), (.+), (.+) and (.+)$")
	public void clonePurlWithDuplicatePurlName(String AccessToken,String PurlID,String errCode, String errMessage) throws Exception {
		AccessToken = (String) base.getGDValue(AccessToken);
		PurlID = (String) base.getGDValue(PurlID);
		errCode = (String) base.getGDValue(errCode);
		errMessage = (String) base.getGDValue(errMessage);
		api.clonePurlWithInvalidData(AccessToken,PurlID,base.Dictionary.get("NEW_PURLNAME"));
		Assert.assertEquals(base.Dictionary.get("message"),errMessage,"Failed message is not matching");
		Assert.assertEquals(base.Dictionary.get("StatusCode"), errCode, "Status Code is not matching");
	}

	@Then("^Search and Verify cloned purl details get matched using (.+) and (.+)$")
	public void verify_details_match_clonePurl(String AccessToken, String PurlID) throws Exception {
		AccessToken = (String) base.getGDValue(AccessToken);
		PurlID = (String) base.getGDValue(PurlID);
		JSONObject response = api.searchPurlID(AccessToken, PurlID);
		JSONObject Obj = response.getJSONObject("data").getJSONObject("Purl");		
		String ResponsePurlType = Obj.get("purlType").toString();
		String ResponsePurlName = Obj.get("name").toString();
		String ResponsePurlOwner = Obj.get("purlOwner").toString();
		String createdBy = Obj.get("createdBy").toString();
		String modifiedBy = Obj.get("modifiedBy").toString();
		Assert.assertEquals("draft", ResponsePurlType, "purlType is not matched");
		Assert.assertEquals(base.Dictionary.get("NEW_PURLNAME"), ResponsePurlName, "purlName is not matched");
		Assert.assertEquals(base.Dictionary.get("userId"), ResponsePurlOwner, "purlOnwer  is not matched");
		Assert.assertEquals(base.Dictionary.get("userId"), createdBy, "createdBy  is not matched");
		Assert.assertEquals(base.Dictionary.get("userId"), modifiedBy, "modifiedBy  is not matched");
	}

	@When("^Generate purlId using (.+), (.+) and (.+)$")
	public void generate_purl_ID(String AccessToken, String PurlName, String purlType) throws Exception {
		AccessToken = (String) base.getGDValue(AccessToken);
		PurlName = (String) base.getGDValue(PurlName);
		purlType = (String) base.getGDValue(purlType);
		api.createPurlandGetPurlId(AccessToken,PurlName,purlType,base.Dictionary.get("userId"), base.Dictionary.get("userId"),base.Dictionary.get("templateId"));
	}

	@Then("^Create purl with duplicate purl name and verify purl should not be created using (.+), (.+) and (.+)$")
	public void Verify_with_DuplicatePurlName(String AccessToken, String PurlName, String purlType) throws Exception {
		AccessToken = (String) base.getGDValue(AccessToken);
		PurlName = (String) base.getGDValue(PurlName);
		purlType = (String) base.getGDValue(purlType);
		
		api.createPurlWithDuplicateData(AccessToken,PurlName,purlType,base.Dictionary.get("userId"),base.Dictionary.get("userId"),base.Dictionary.get("templateId"));
		Assert.assertEquals(base.Dictionary.get("message"), base.Dictionary.get("PURL_SUCCESS_MESSAGE"),"Purl is created with duplicate purlName");
		Assert.assertEquals(base.Dictionary.get("StatusCode"), base.Dictionary.get("PURL_SUCCESS_CODE"), "Purl created ");
	}

	@Given("^Generate purlId purlType using (.+), (.+) and (.+)$")
	public void Verify_publicPurl_visible_to_other(String AccessToken, String PurlName, String PurlType)throws Exception {
		AccessToken = (String) base.getGDValue(AccessToken);
		PurlName = (String) base.getGDValue(PurlName);
		PurlType = (String) base.getGDValue(PurlType);
		api.createPurlandGetPurlId(AccessToken,PurlName,PurlType,base.Dictionary.get("userId"), base.Dictionary.get("userId"),base.Dictionary.get("templateId"));
	}
	

	@Then("^Login with another user and search for private or draft purl and verify purl should not be visible using (.+), (.+), (.+) and (.+)$")
	public void search_privatePurl_fromOther_User(String AccessToken, String PurlId, String errCode, String errMsg) throws Exception {
		AccessToken = (String) base.getGDValue(AccessToken);
		PurlId = (String) base.getGDValue(PurlId);
		errMsg = (String) base.getGDValue(errMsg);
		errCode = (String) base.getGDValue(errCode);
	    api.searchPurlWithInvalidData(AccessToken, PurlId);
		Assert.assertEquals(base.Dictionary.get("message"), errMsg,"Failed message is not matching");
		Assert.assertEquals(base.Dictionary.get("StatusCode"), errCode, "Status Code is not matching");
	}

	@Then("^Login with other user and clone_Purl using (.+) and (.+)$")
	public void clone_Presentation(String AccessToken, String PurlId) throws Exception {
		AccessToken = (String) base.getGDValue(AccessToken);
		PurlId = (String) base.getGDValue(PurlId);	
		api.cloneSavePurl(AccessToken, PurlId);
	}

	
	@Then("^Search and verify public purl should be visible to everyone using (.+) and (.+)$")
	public void Search_WithOtherUser_for_updated_PurlType(String AccessToken, String purlId) throws Exception {
		AccessToken = (String) base.getGDValue(AccessToken);
		purlId = (String) base.getGDValue(purlId);
		JSONObject response = api.searchPurlID(AccessToken, purlId);
		JSONObject Obj = response.getJSONObject("data").getJSONObject("Purl");
		String ResponsePurlType = Obj.get("purlType").toString();
		// JSONObject Object = response.getJSONObject("data").getJSONObject("Purl");
		String ResponsePurlName = Obj.get("name").toString();
		String responsePurlID = Obj.get("purlId").toString();
		Assert.assertEquals(purlId , responsePurlID, "purlId not found");
		Assert.assertEquals(base.Dictionary.get("PUBLIC_PURL"), ResponsePurlType, "purlType is not matched");
		Assert.assertEquals(base.Dictionary.get("NEW_PURLNAME"), ResponsePurlName, "purlName is not matched");
	}

	@Then("^Verify purlList should contain purlId purlName using (.+)$")
	public void verify_PurlList(String AccessToken) throws Exception {
		AccessToken = (String) base.getGDValue(AccessToken);
		JSONObject response = api.purlList(AccessToken);
		// JSONObject Obj = response.getJSONObject("data");
		// Obj.getJSONArray("Purls");
		String purlLIstData = response.toString();
		Assert.assertTrue(purlLIstData.contains(base.Dictionary.get("purlID")));
		Assert.assertTrue(purlLIstData.contains(base.Dictionary.get("NEW_PurlName")));
	}

	@Then("^Verify purlList should not contain purlId and purlName using (.+)$")
	public void verify_PurlId_InPurlList(String AccessToken) throws Exception {
		AccessToken = (String) base.getGDValue(AccessToken);
		JSONObject response = api.purlList(AccessToken);
	//	JSONObject Obj = response.getJSONObject("data");
		//JSONArray jsonArray = Obj.getJSONArray("Purls");
		String purlLIstData = response.toString();
		Assert.assertFalse(purlLIstData.contains(base.Dictionary.get("PURLID")));
		Assert.assertFalse(purlLIstData.contains(base.Dictionary.get("NEW_PURLNAME")));
	}

	@Given("^Delete purl and verify response purl should be deleted using (.+), (.+), (.+) and (.+)$")
	public void deletePurl(String AccessToken, String PurlId, String purlMsg, String purlCode) throws Exception {
		AccessToken = (String) base.getGDValue(AccessToken);
		PurlId = (String) base.getGDValue(PurlId);
		purlMsg = (String) base.getGDValue(purlMsg);
		purlCode = (String) base.getGDValue(purlCode);
		api.deleteDataPurl(AccessToken, PurlId);
	
		Assert.assertEquals(base.Dictionary.get("message"), purlMsg, "purl not deleted");
		Assert.assertEquals(base.Dictionary.get("StatusCode"),purlCode, "Status Code is not matching");

	}
	
	@Given("^Delete purl of other user and verify purl should not be deleted using (.+) and (.+)$")
	public void deleteOtherUserPurl(String AccessToken, String PurlId) throws Exception {
		AccessToken = (String) base.getGDValue(AccessToken);
		PurlId = (String) base.getGDValue(PurlId);
		JSONObject response = api.deletePurl(AccessToken, PurlId);
		JSONArray a = response.getJSONArray("errors");
		JSONObject ob = a.getJSONObject(0);
		String message = ob.getString("message");
		String message1 = (new JSONObject(message)).get("message").toString();
		//base.Dictionary.put("message", message1);
		String statusCode = (new JSONObject(message)).get("statusCode").toString();
		//base.Dictionary.put("statusCode", statusCode);
		Assert.assertEquals(message1, base.Dictionary.get("PURL_ERR_MESSAGE"),"Failed message is not matching");
		Assert.assertEquals(statusCode, base.Dictionary.get("PURL_ERR_CODE"), "Status Code is not matching");

	}

	@Given("^Update purl name and purlType public using (.+) and (.+) and (.+) and (.+)$")
	public void update_PurlName_PurlType(String AccessToken, String PurlId, String PurlName, String PurlType)throws Exception {
		AccessToken = (String) base.getGDValue(AccessToken);
		PurlId = (String) base.getGDValue(PurlId);
		PurlName = (String) base.getGDValue(PurlName);
		PurlType = (String) base.getGDValue(PurlType);
        api.updatePurlName(AccessToken, PurlId, PurlName, PurlType);

	}
	
	@Given("^Update purlType private using (.+) and (.+) and (.+) and (.+)$")
	public void update_PurlType(String AccessToken, String PurlId, String PurlName, String PurlType) throws Exception {
		AccessToken = (String) base.getGDValue(AccessToken);
		PurlId = (String) base.getGDValue(PurlId);
		PurlName = (String) base.getGDValue(PurlName);
		PurlType = (String) base.getGDValue(PurlType);
		api.updatePurlName(AccessToken,PurlId,PurlName,PurlType);
	}

	@When("^Update purlType draft from public using (.+) and (.+) and (.+) and (.+)$")
	public void update_PurlTy(String AccessToken, String PurlId, String PurlName, String PurlType) throws Exception {
		AccessToken = (String) base.getGDValue(AccessToken);
		PurlId = (String) base.getGDValue(PurlId);
		PurlName = (String) base.getGDValue(PurlName);
		PurlType = (String) base.getGDValue(PurlType);
		api.updatePurlName(AccessToken,PurlId,PurlName,PurlType);
	}

	

	@Then("^Update purlType draft after purl associated with campaign using (.+) and (.+) and (.+) and (.+)$")
	public void update_PurlType_Draft_PurlAssociatedWithCampaign(String AccessToken, String PurlId, String PurlName,
			String PurlType) throws Exception {
		AccessToken = (String) base.getGDValue(AccessToken);
		PurlId = (String) base.getGDValue(PurlId);
		PurlName = (String) base.getGDValue(PurlName);
		PurlType = (String) base.getGDValue(PurlType);
		
		api.updatePurlWithInvlidData(AccessToken, PurlId,PurlName,PurlType,base.Dictionary.get("userId"), base.Dictionary.get("userId"),base.Dictionary.get("templateId"));
		Assert.assertEquals(base.Dictionary.get("message"),base.Dictionary.get("PURL_MESSAGE"),"Failed message is not matching");
		Assert.assertEquals(base.Dictionary.get("StatusCode"),base.Dictionary.get("PURL_CODE"), "Status Code is not matching");

	}

	@Then("^Update purlType which can not be updated using (.+), (.+), (.+), (.+), (.+), (.+) and (.+)$")
	public void updatePurlType(String AccessToken, String PurlID, String PurlName,String PurlType,String templateId,String errMessage,String errCode) throws Exception {
		AccessToken = (String) base.getGDValue(AccessToken);
		PurlID = (String) base.getGDValue(PurlID);
		PurlName = (String) base.getGDValue(PurlName);
		PurlType = (String) base.getGDValue(PurlType);
		templateId = (String) base.getGDValue(templateId);	
		errMessage = (String) base.getGDValue(errMessage);
		errCode = (String) base.getGDValue(errCode);
	    api.updatePurlWithInvlidData(AccessToken,PurlID,PurlName,PurlType,base.Dictionary.get("userId"), base.Dictionary.get("userId"),templateId);
		Assert.assertEquals(base.Dictionary.get("message"),errMessage,"Failed message is not matching");
		Assert.assertEquals(base.Dictionary.get("StatusCode"), errCode, "Status Code is not matching");

	}

	@Then("^Delete purl which can not be deleted using (.+), (.+), (.+) and (.+)$")
	public void delete_AsscoiatedPurlWith_Campaign(String AccessToken, String PurlId,String errMessage,String errCode) throws Exception {
		AccessToken = (String) base.getGDValue(AccessToken);
		PurlId = (String) base.getGDValue(PurlId);
		errMessage = (String) base.getGDValue(errMessage);
		errCode = (String) base.getGDValue(errCode);	
		api.deleteDataPurl(AccessToken,PurlId);
		Assert.assertEquals(base.Dictionary.get("message"), errMessage,"Failed message is not matching");
		Assert.assertEquals(base.Dictionary.get("StatusCode"), errCode, "Status Code is not matching");
	}

	@When("^Update purl name and purlType private of other user purl using (.+) and (.+) and (.+) and (.+)$")
	public void update_PurlNamePurlType(String AccessToken, String PurlId, String PurlName, String PurlType)
			throws Exception {
		AccessToken = (String) base.getGDValue(AccessToken);
		PurlId = (String) base.getGDValue(PurlId);
		PurlName = (String) base.getGDValue(PurlName);
		PurlType = (String) base.getGDValue(PurlType);
		base.Dictionary.put("purlType", "private");
		api.updatePurlWithInvlidData(AccessToken,PurlId,PurlName,PurlType,base.Dictionary.get("userId"), base.Dictionary.get("userId"),base.Dictionary.get("templateId"));
		Assert.assertEquals(base.Dictionary.get("message"), base.Dictionary.get("PURL_MESSAGE"),"Failed message is not matching");
		Assert.assertEquals(base.Dictionary.get("StatusCode"),base.Dictionary.get("PURL_CODE"),"Status Code is not matching");

	}

	@When("^Update purl name purlType public of other user and verify purl should not be updated using (.+) and (.+) and (.+) and (.+)$")
	public void update_PurlPurlType(String AccessToken, String PurlId, String PurlName, String PurlType)throws Exception {
		AccessToken = (String) base.getGDValue(AccessToken);
		PurlId = (String) base.getGDValue(PurlId);
		PurlName = (String) base.getGDValue(PurlName);
		PurlType = (String) base.getGDValue(PurlType);
	
		api.updatePurlWithInvlidData(AccessToken,PurlId,PurlName,PurlType,base.Dictionary.get("userId"), base.Dictionary.get("userId"),base.Dictionary.get("templateId"));
		Assert.assertEquals(base.Dictionary.get("message"), base.Dictionary.get("PURL_MESSAGE"),
				"Failed message is not matching");
		Assert.assertEquals(base.Dictionary.get("StatusCode"), base.Dictionary.get("PURL_CODE"), "Status Code is not matching");

	}

	@Then("^Update purl name purlType draft of other user and verify purl should not be updated using (.+) and (.+) and (.+) and (.+)$")
	public void update_PurlPurlType_draft(String AccessToken, String PurlId, String PurlName, String PurlType)
			throws Exception {
		AccessToken = (String) base.getGDValue(AccessToken);
		PurlId = (String) base.getGDValue(PurlId);
		PurlName = (String) base.getGDValue(PurlName);
		PurlType = (String) base.getGDValue(PurlType);

		api.updatePurlWithInvlidData(AccessToken, PurlId,PurlName,PurlType,base.Dictionary.get("userId"), base.Dictionary.get("userId"),base.Dictionary.get("templateId"));
		Assert.assertEquals(base.Dictionary.get("message"), base.Dictionary.get("PURL_ERROR_MESSAGE"),"Failed message is not matching");
		Assert.assertEquals(base.Dictionary.get("StatusCode"), base.Dictionary.get("PURL_ERROR_CODE"), "Status Code is not matching");

	}

	@Then("^Clone a cloned purl or from draft purl by savePurl Api using (.+), (.+), (.+) and (.+)$")
	public void clone_clonedPurl(String AccessToken, String PurlID, String PurlerrorCode, String PurlErrorMessage) throws Exception {
		AccessToken = (String) base.getGDValue(AccessToken);
		PurlID = (String) base.getGDValue(PurlID);
		PurlerrorCode = (String) base.getGDValue(PurlerrorCode);
		PurlErrorMessage = (String) base.getGDValue(PurlErrorMessage);
		api.clonePurlWithInvalidData(AccessToken,PurlID,base.Dictionary.get("NEW_PURLNAME"));
		Assert.assertEquals(base.Dictionary.get("message"), PurlErrorMessage,"Failed message is not matching");
		Assert.assertEquals(base.Dictionary.get("StatusCode"),PurlerrorCode, "Status Code is not matching");
	}

	@Then("^Verify campaignList should contain campaignId campaignName using (.+)$")
	public void verify_CampaignList(String AccessToken) throws Exception {
		AccessToken = (String) base.getGDValue(AccessToken);
		JSONObject response = api.searchCampaignList(AccessToken);
		JSONObject innerObject = response.getJSONObject("data");
		String campaignList = innerObject.getJSONObject("CampaignList").get("Campaigns").toString();
		Assert.assertTrue(campaignList.contains(base.Dictionary.get("CAMPAIGNID")));
		Assert.assertTrue(campaignList.contains(base.Dictionary.get("CAMPAIGNNAME")));
	}

	@Then("^Verify campaignList should not contain campaignId campaignName using (.+)$")
	public void verify_PurlId_InCampaignList(String AccessToken) throws Exception {
		AccessToken = (String) base.getGDValue(AccessToken);
		JSONObject response = api.searchCampaignList(AccessToken);
		JSONObject innerObject = response.getJSONObject("data");
		String campaignList = innerObject.getJSONObject("CampaignList").get("Campaigns").toString();
		Assert.assertFalse(campaignList.contains(base.Dictionary.get("CAMPAIGNID")));
		Assert.assertFalse(campaignList.contains(base.Dictionary.get("CAMPAIGN_NAME")));
	}

	@Then("^Verify create purl api with invalid purlOwner using (.+), (.+), (.+), (.+) and (.+)$")
	public void createPurl_withInvalid_PurlOwner(String AccessToken, String PurlName, String PurlType,String PurlOwner, String errMessage )
			throws Exception {
		AccessToken = (String) base.getGDValue(AccessToken);
		PurlName = (String) base.getGDValue(base.Dictionary.get("NEW_PurlName"));
		PurlType = (String) base.getGDValue(PurlType);	
		PurlOwner = (String) base.getGDValue(PurlOwner);
		errMessage = (String) base.getGDValue(errMessage);

		api.createPurlWithInvalidData(AccessToken, PurlName, PurlType,PurlOwner,
				base.Dictionary.get("userId"));
		Assert.assertEquals(base.Dictionary.get("message"), "In-Valid purl Owner", "purlOwner matched");

	}

	@Then("^Verify create purl api with invalid slideOwner using (.+), (.+), (.+), (.+) and (.+)$")
	public void createPurl_withInvalid_SlideOwner(String AccessToken, String PurlName, String PurlType, String SlideOwner,String errMessage)
			throws Exception {
		AccessToken = (String) base.getGDValue(AccessToken);
		PurlName = (String) base.getGDValue(base.Dictionary.get("NEW_PurlName"));
        PurlType = (String) base.getGDValue(PurlType);	
        SlideOwner = (String) base.getGDValue(SlideOwner);
		errMessage = (String) base.getGDValue(errMessage);
api.createPurlWithInvalidData(AccessToken, PurlName, PurlType,
				base.Dictionary.get("userId"),SlideOwner);
		Assert.assertEquals(base.Dictionary.get("message"), errMessage, "Slide owner matched");

	}

	@When("^Create campaign with duplicate campaign name by using (.+), (.+), (.+), (.+), (.+), (.+), (.+) and (.+)$")
	public void createCampaignWithDuplicateCampaignName(String AccessToken,String CampaignName, String PurlID, String eTemplateID,String masterETemplateId,String email ,String errMessage,String errCode)
			throws Exception {
		AccessToken = (String) base.getGDValue(AccessToken);
		CampaignName = (String) base.getGDValue(CampaignName);
		PurlID = (String) base.getGDValue(PurlID);
		eTemplateID = (String) base.getGDValue(eTemplateID);
		masterETemplateId = (String) base.getGDValue(masterETemplateId);
		email = (String) base.getGDValue(email);
		errMessage = (String) base.getGDValue(errMessage);
		errCode = (String) base.getGDValue(errCode);
        api.createCampaignWithDuplicateCampaignName(AccessToken,PurlID,eTemplateID,masterETemplateId,email);
		Assert.assertEquals(base.Dictionary.get("message"), errMessage,"Campaign is created with duplicate campaignName");
		Assert.assertEquals(base.Dictionary.get("StatusCode"),errCode, "campaign created created ");

	}
	
	@When("^Call master template API and get templateId using (.+)$")
	public void masterTemplateId(String AccessToken) throws Exception {
		AccessToken = (String) base.getGDValue(AccessToken);
		JSONObject response = api.purlTemplate(AccessToken);
		String templateId = api.getPurlTemplateId(response);
		base.Dictionary.put("templateId",templateId);	
    }
	
	
	@Then("^Create purl with invalid templateId using (.+), (.+), (.+), (.+), (.+) and (.+)$")
	public void createPurlWithInvalidTemplateId(String AccessToken, String PurlName, String PurlType,String templateID,String errMessage,String errCode) throws Exception {
		AccessToken = (String) base.getGDValue(AccessToken);
		PurlName = (String) base.getGDValue(PurlName);
        PurlType = (String) base.getGDValue(PurlType);	
		templateID = (String) base.getGDValue(templateID);

		errMessage = (String) base.getGDValue(errMessage);
		errCode = (String) base.getGDValue(errCode);
	  api.createPurlWithDuplicateData(AccessToken, PurlName, PurlType,base.Dictionary.get("userId"), base.Dictionary.get("userId"),templateID);
	  Assert.assertEquals(base.Dictionary.get("message"),errMessage ,"TemplateId is matched");
      Assert.assertEquals(base.Dictionary.get("StatusCode"),errCode , "Templated is matched");
	}
	
	
	@Given("^Verify update purl with invalid input templateId using (.+) and (.+) and (.+) and (.+) and (.+) and (.+) and (.+)$")
	public void updatePurlWithInvalidTemplateId(String AccessToken,String PURLID,String PurlName,String templateID,String PurlType,String errMessage,String errCode) throws Exception {
		AccessToken = (String) base.getGDValue(AccessToken);
		PURLID = (String) base.getGDValue(PURLID);
		PurlType = (String) base.getGDValue(PurlType);
		PurlName = (String) base.getGDValue(PurlName);
	    templateID = (String) base.getGDValue(templateID);
        errMessage = (String) base.getGDValue(errMessage);
		errCode = (String) base.getGDValue(errCode);		
		api.updatePurlWithInvlidData(AccessToken,PURLID,PurlName,PurlType,base.Dictionary.get("userId"), base.Dictionary.get("userId"),templateID);
        Assert.assertEquals(base.Dictionary.get("message"),errMessage,"TemplateId is matched");
       Assert.assertEquals(base.Dictionary.get("StatusCode"), errCode, "Templated is matched");
	
	}
		
	@When("^Generate purl by savepurlApi with invalid templateId using (.+), (.+), (.+) and (.+)$")
	public void savePurlApiWithInvalidTemplateId(String AccessToken,String templateID,String errMessage,String errCode) throws Exception {
		AccessToken = (String) base.getGDValue(AccessToken);
		templateID = (String) base.getGDValue(templateID);
		errMessage = (String) base.getGDValue(errMessage);
		errCode = (String) base.getGDValue(errCode);
		api.createAndGetSavePurlWithInvalidData(AccessToken,templateID);
	    Assert.assertEquals(base.Dictionary.get("message"),errMessage,"TemplateId is matched");
	    Assert.assertEquals(base.Dictionary.get("StatusCode"), errCode, "Templated is matched");
	}
	
	
	@Then("^Search for invalid purlId using (.+), (.+), (.+) and (.+)$")
	public void search_for_InvalidPurlId(String AccessToken,String InvalidPurlId,String errMessage,String errCode) throws Exception {
		AccessToken = (String) base.getGDValue(AccessToken);
		InvalidPurlId = (String) base.getGDValue(InvalidPurlId);
		errMessage = (String) base.getGDValue(errMessage);
		errCode = (String) base.getGDValue(errCode);
        api.searchPurlWithInvalidData(AccessToken,InvalidPurlId);
		Assert.assertEquals(base.Dictionary.get("message"),errMessage, "purlId found");
		Assert.assertEquals(base.Dictionary.get("StatusCode"),errCode, "status code is not matched ");
	}
	
	
@When("^Verify update purl with invalid purlId using (.+), (.+), (.+), (.+), (.+), (.+) and (.+)$")
	public void updatePurlWithInvalidPurlId(String AccessToken,String InvalidPurlId,String PurlName,String PurlType,String templateID, String errCode,String errMessage) throws Exception {
		AccessToken = (String) base.getGDValue(AccessToken);
		InvalidPurlId = (String) base.getGDValue(InvalidPurlId);
        PurlName = (String) base.getGDValue(PurlName);
        PurlType = (String) base.getGDValue(PurlType);	
		templateID = (String) base.getGDValue(templateID);
		errCode = (String) base.getGDValue(errCode);
		errMessage = (String) base.getGDValue(errMessage);
		api.updatePurlWithInvlidData(AccessToken,InvalidPurlId,PurlName,PurlType,base.Dictionary.get("userId"), base.Dictionary.get("userId"),templateID);
	    Assert.assertEquals(base.Dictionary.get("message"),errMessage,"Failed message is not matching");
		Assert.assertEquals(base.Dictionary.get("StatusCode"),errCode, "Status Code is not matching");
	}
	
//@Then("^Verify update purl with invalid PurlOwner using (.+), (.+), (.+), (.+), (.+) and (.+)$")
//public void updatePurlWithInvalidSlideOwnerPurlOwner(String AccessToken,String PurlOwner,String PurlName,String PurlType,String errMessage,String templateID) throws Exception {
//	AccessToken = (String) base.getGDValue(AccessToken);
//	//PurlID = (String) base.getGDValue(base.Dictionary.get("purlID"));
//    PurlName = (String) base.getGDValue(base.Dictionary.get("NEW_PURLNAME"));
//    PurlType = (String) base.getGDValue(PurlType);
//    PurlOwner = (String) base.getGDValue(PurlOwner);	
//	templateID = (String) base.getGDValue(templateID);
//	//errCode = (String) base.getGDValue(errCode);
//	errMessage = (String) base.getGDValue(errMessage);
//	api.updatePurlWithInvlidPOWner_SlideOwner(AccessToken,base.Dictionary.get("purlID"),PurlName,PurlType,PurlOwner, base.Dictionary.get("userId"),templateID);
//     Assert.assertEquals(base.Dictionary.get("message"),errMessage,"Failed message is not matching");
//	//Assert.assertEquals(base.Dictionary.get("statusCode"),errCode, "Status Code is not matching");
//}


@When("^Verify update purl with purlId using (.+), (.+), (.+), (.+), (.+) and (.+)$")
public void updatePurlWithInvalidPurlId(String AccessToken,String PurlId,String PurlName,String PurlType,String purlOwner,String templateID) throws Exception {
	AccessToken = (String) base.getGDValue(AccessToken);
	PurlId = (String) base.getGDValue(PurlId);
    PurlName = (String) base.getGDValue(PurlName);
    PurlType = (String) base.getGDValue(PurlType);	
	templateID = (String) base.getGDValue(templateID);
	purlOwner = (String) base.getGDValue(purlOwner);
	api.updatePurlWithInvlidData(AccessToken,PurlId,PurlName,PurlType,purlOwner, base.Dictionary.get("userId"),templateID);
    Assert.assertEquals(base.Dictionary.get("message"),base.Dictionary.get("ERROR_MESSAGE"),"Failed message is not matching");
	Assert.assertEquals(base.Dictionary.get("StatusCode"),base.Dictionary.get("ERROR_CODE"), "Status Code is not matching");
}
	
@When("^Call master template API and get EtemplateId using (.+)$")
public void masterETemplateId(String AccessToken) throws Exception {
	AccessToken = (String) base.getGDValue(AccessToken);
	JSONObject response = api.getAllEmailTemplates(AccessToken);
	String templateId = api.getETemplateId(response);
    base.Dictionary.put("EtemplateId",templateId);	
   base.Dictionary.put("MASTER_TEMPLATE_ID", templateId);

}
	
}
