package org.iomedia.galen.common;
import java.io.BufferedReader;

import java.io.InputStream;
import java.io.InputStreamReader;

import org.iomedia.framework.Reporting;
import org.iomedia.framework.WebDriverFactory;

import org.iomedia.framework.Driver.HashMapNew;
import org.json.JSONArray;
import org.json.JSONObject;


public class PitchmanApi extends AccessToken{

	String host, emailaddress, password, accessToken;
	String path = System.getProperty("user.dir");
	private String campaignById = path + "/APIRequest/CampaignById.json";

	private String createPurl = path + "/APIRequest/CreatePurl.json";
	private String getPurl = path + "/APIRequest/Purl.json";
	private String getPurlP = path + "/APIRequest/PurlP.json";
	private String getPurls = path + "/APIRequest/Purls.json";
	private String createEmailT = path + "/APIRequest/createEmailtemplate.json";
	private String deletePurl = path + "/APIRequest/deletePurl.json";
	private String emailDetails = path + "/APIRequest/Email.json";
	private String updatePurl = path + "/APIRequest/updatePurl.json";
	private String searchArchticsById = path + "/APIRequest/SearchByarcticsId.json";
	private String savePurl=path + "/APIRequest/savePurl.json";
	private String seachCampaignListByPurlID=path + "/APIRequest/campaignListByPurlID.json";
	private String searchCampaignList=path + "/APIRequest/CampaignList.json";
	private String updatePurlInfo = path+"/APIRequest/updatePurlInfo.json";
	private String purlTemplate = path+"/APIRequest/PurlTemplates.json";
	private String changePassword = path+"/APIRequest/changePassword.json";
	private String createCampaign = path+"/APIRequest/createCampaign.json";
	private String getVFSManifest = path+"/APIRequest/getVFSManifest.json";

	private String cloneSavePurl = path+"/APIRequest/CloneSavePurl.json";
	private String clonePurl = path+"/APIRequest/clonePurl.json";

	private Utils util;


	public PitchmanApi(WebDriverFactory driverFactory, HashMapNew Dictionary, HashMapNew Environment,Reporting Reporter, org.iomedia.framework.Assert Assert, org.iomedia.framework.SoftAssert SoftAssert,ThreadLocal<HashMapNew> sTestDetails)   {
		super(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
		  host= Environment.get("TM_HOST").trim();
		  emailaddress = Dictionary.get("EMAIL_ADDRESS").trim();
		  password = Dictionary.get("PASSWORD").trim();
		  accessToken = "";
		 util = new Utils(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
	}


	public JSONObject post(String accesstoken, String payload) throws Exception {

		  String url = Environment.get("LAMBDA_URL").trim()+"/graphql";
		  InputStream is = utils.post(url, payload, new String[] {"Content-Type", "Authorization", "Accept"}, new String[] {"application/json", accesstoken, Environment.get("pmVersion").trim()});		  
		  JSONObject jsonObject = utils.convertToJSON(new BufferedReader(new InputStreamReader(is, "UTF-8")));
		  return jsonObject;
	  }

	public JSONObject searchCampaignById(String accesstoken,String campaignId) throws Exception{
		JSONObject campaignJson = util.parseJsonFile(campaignById);
		String newQuery = campaignJson.get("query").toString();
		newQuery=updateQuery(newQuery, "campaignId", campaignId);
		String updatedRequest="{\"query\":\""+newQuery+"\",\"variables\":null,\"operationName\":null}";
		return post(accesstoken,updatedRequest);
	  }
	
	public String createPurlandGetPurlId(String accesstoken, String purlName,String purlType,String purlOwner,String slideOwner,String templateId) throws Exception  {
		 JSONObject updatePurlInfoJson = util.parseJsonFile(createPurl);
		 String newQuery = updatePurlInfoJson.get("query").toString();
		 newQuery=updateQuery(newQuery, "name", purlName);
		 newQuery=updateQuery(newQuery, "purlType", purlType);
         newQuery=updateQuery(newQuery, "purlOwner",purlOwner);
         newQuery=updateQuery(newQuery, "templateId",templateId);       
         newQuery=updateQuery(newQuery, "slideOwner",slideOwner);
       	 String updatedRequest="{\"query\":\""+newQuery+"\",\"variables\":null}";
         JSONObject response= post(accesstoken,updatedRequest);  
         System.out.println(response);
         JSONObject Obj = response.getJSONObject("data").getJSONObject("createPurl");
         String purlId = Obj.get("purlId").toString();
         Dictionary.put("PURLID", purlId);
         return purlId;  
     }
         

	public JSONObject searchPurlID(String accesstoken,String purlId) throws Exception{
	JSONObject campaignJson = util.parseJsonFile(getPurl);
		String newQuery = campaignJson.get("query").toString();
		newQuery=updateQuery(newQuery, "purlId", purlId);
		String updatedRequest="{\"query\":\""+newQuery+"\",\"variables\":null,\"operationName\":null}";
		return post(accesstoken,updatedRequest);	
		///JSONObject response= post(accesstoken,updatedRequest );
		//JSONObject Obj = response.getJSONObject("data").getJSONObject("purlType");
		//return Obj.get("purlId").toString();
		//String purlType = Obj.get("purlId").toString();
		
		//JSONObject purlName = response.getJSONObject("data").getJSONObject("purlType").getJSONObject("name");
		//return Obj.get("purlId").toString();
		//String name = Obj.get("purlId").toString();
		
		//Dictionary.put("purlID", purlId);
		//return purlId;
		
	  }
	
	public JSONObject purlList(String accesstoken) throws Exception{
		JSONObject purlsJson = util.parseJsonFile(getPurls);
		String newQuery = purlsJson.get("query").toString();
		//newQuery=updateQuery(newQuery, "purlId", purlId);
		String updatedRequest="{\"query\":\""+newQuery+"\",\"variables\":null,\"operationName\":null}";
		return post(accesstoken,updatedRequest );		
	  }
	
	 public String createEmailtemplate(String accesstoken,String templateName,String templateId) throws Exception{
		    JSONObject emailTemplateJson = util.parseJsonFile(createEmailT);
		    String newQuery = emailTemplateJson.get("query").toString();
		    newQuery=updateQuery(newQuery, "name", templateName);
		    newQuery=updateQuery(newQuery, "templateId",templateId);
		    String updatedRequest="{\"query\":\""+newQuery+"\",\"variables\":null}";
		    JSONObject response = post(accesstoken,updatedRequest);
		    JSONObject Obj = response.getJSONObject("data").getJSONObject("createEmailTemplate");
		    String eTemplateId = Obj.get("eTemplateId").toString();
		    Dictionary.put("ETEMPLATE_ID", eTemplateId);
		    return eTemplateId;
     }
	 
	public JSONObject deletePurl(String accesstoken,String purlId) throws Exception{
		JSONObject deleteP = util.parseJsonFile(deletePurl);
		String newQuery = deleteP.get("query").toString();
		newQuery=updateQuery(newQuery, "purlId", purlId);
		String updatedRequest="{\"query\":\""+newQuery+"\",\"variables\":null}";
		return post(accesstoken,updatedRequest);		
	  }
	
	public JSONObject getAllEmailTemplates(String accesstoken) throws Exception	{
		JSONObject email = util.parseJsonFile(emailDetails);
		String newQuery = email.getString("query").toString();
		String updatedRequest="{\"query\":\""+newQuery+"\",\"variables\":null,\"operationName\":null}";
		return post(accesstoken,updatedRequest );
	}
	
	
	public String updatePurlName(String accesstoken,String purlId,String newPurlName,String purlType) throws Exception		{
	JSONObject updatepurl = util.parseJsonFile(updatePurl);
    String newQuery = updatepurl.getString("query").toString();
    newQuery=updateQuery(newQuery, "name", newPurlName);
    newQuery=updateQuery(newQuery, "purlId", purlId);
    newQuery=updateQuery(newQuery, "purlType", purlType);
    newQuery=updateQuery(newQuery, "purlOwner",Dictionary.get("userId"));
    newQuery=updateQuery(newQuery, "slideOwner",Dictionary.get("userId"));
	newQuery=updateQuery(newQuery, "templateId",Dictionary.get("templateId"));
    String updatedRequest="{\"query\":\""+newQuery+"\",\"variables\":null}";
    JSONObject response= post(accesstoken,updatedRequest);
    JSONObject Obj = response.getJSONObject("data").getJSONObject("updatePurl");
    String purlID = Obj.get("purlId").toString();
    //Dictionary.put("purlId", purlID);
    //return Obj.get("purlId").toString();
    return purlID;
	}

	public String updateQuery(String query, String fieldName, String value){
		String[] splitQuery=query.split("update_"+fieldName);
		return splitQuery[0]+value+splitQuery[1];
	  }
	

	public String uniqueNameGenerator(String prefix) {
           return (prefix + System.currentTimeMillis());  
	}

	public String updateQuery(String query, String fieldName, int value){
		String[] splitQuery=query.split("update_"+fieldName);
		query= splitQuery[0]+value+"\\"+splitQuery[1];
		splitQuery=query.split(fieldName+":");
		return splitQuery[0]+fieldName+":\\"+splitQuery[1];
	}

	
	public JSONObject searchArchticsByID(String accesstoken, String architicsId) throws Exception{

		JSONObject searchArchticsByID = util.parseJsonFile(searchArchticsById);
		String newQuery = searchArchticsByID.get("query").toString();
		newQuery=updateQuery(newQuery, "architicsId", architicsId);
		String updatedRequest="{\"query\":\""+newQuery+"\",\"variables\":null,\"operationName\":null}";
		return post(accesstoken,updatedRequest );
	}

	public JSONObject searchCampaignListByPurlID(String accesstoken, String purlId ) throws Exception{
		JSONObject campaignJson = util.parseJsonFile(seachCampaignListByPurlID);
		String newQuery = campaignJson.get("query").toString();
		newQuery=updateQuery(newQuery, "purlId", purlId );
		String updatedRequest="{\"query\":\""+newQuery+"\",\"variables\":null,\"operationName\":null}";
		return post(accesstoken,updatedRequest );
	}

	public JSONObject searchCampaignList(String accesstoken) throws Exception{
		JSONObject searchCampaignListJson = util.parseJsonFile(searchCampaignList);
		String newQuery = searchCampaignListJson.get("query").toString();
		String updatedRequest="{\"query\":\""+newQuery+"\",\"variables\":null,\"operationName\":null}";
		return post(accesstoken,updatedRequest);
	}

	public JSONObject updatePurlInfo(String accesstoken,String purlId,String purlType) throws Exception{
		JSONObject updatePurlInfoJson = util.parseJsonFile(updatePurlInfo);
		String newQuery = updatePurlInfoJson.get("query").toString();
		newQuery=updateQuery(newQuery, "purlId", purlId);
		newQuery=updateQuery(newQuery, "purlType", purlType);
		String updatedRequest="{\"query\":\""+newQuery+"\",\"variables\":null,\"operationName\":null}";
		return post(accesstoken,updatedRequest);
	}
	
	
	public JSONObject purlTemplate(String accesstoken) throws Exception{
		JSONObject updatePurlInfoJson = util.parseJsonFile(purlTemplate);
		String newQuery = updatePurlInfoJson.get("query").toString();
		String updatedRequest="{\"query\":\""+newQuery+"\",\"variables\":null,\"operationName\":null}";
		return post(accesstoken,updatedRequest);
	}
	

	public JSONObject changePassword(String accesstoken, String previousPasssword, String praposedPassword) throws Exception{
		JSONObject updatePurlInfoJson = util.parseJsonFile(changePassword);
		String newQuery = updatePurlInfoJson.get("query").toString();
		newQuery=updateQuery(newQuery, "accessToken", accesstoken);
		newQuery=updateQuery(newQuery, "previousPassword", previousPasssword);
		newQuery=updateQuery(newQuery, "previousPassword", praposedPassword);
		String updatedRequest="{\"query\":\""+newQuery+"\",\"variables\":null,\"operationName\":null}";
		return post(accesstoken,updatedRequest );
	}


	public JSONObject getVFSManifest(String accesstoken) throws Exception{
		JSONObject updatePurlInfoJson = util.parseJsonFile(getVFSManifest);
		String newQuery = updatePurlInfoJson.get("query").toString();
		String updatedRequest="{\"query\":\""+newQuery+"\",\"variables\":null,\"operationName\":null}";
		return post(accesstoken,updatedRequest );
	}

	public String createCampaignGetCampaignId(String accesstoken,String purlId,String CampaignName,String etemplateID,String masterETemplateId,String email) throws Exception{
		JSONObject updatePurlInfoJson = util.parseJsonFile(createCampaign);
		String newQuery = updatePurlInfoJson.get("query").toString();
		String campaignName= uniqueNameGenerator(CampaignName);
		Dictionary.put("CAMPAIGN_NAME", campaignName);
     //Dictionary.put("NEW_CAMPAIGN_NAME", campaignName);
		newQuery=updateQuery(newQuery, "name", campaignName);
		newQuery=updateQuery(newQuery, "purlId", purlId);
		newQuery=updateQuery(newQuery, "eTemplateId", etemplateID);
		newQuery=updateQuery(newQuery, "masterETemplateId", masterETemplateId);
		newQuery=updateQuery(newQuery, "email", email);

		String updatedRequest="{\"query\":\""+newQuery+"\",\"variables\":null,\"operationName\":null}";
		JSONObject response= post(accesstoken,updatedRequest);		
		JSONObject Obj = response.getJSONObject("data").getJSONObject("createCampaign");
		String campaignid=Obj.get("campaignId").toString();
		Dictionary.put("CAMPAIGNID", campaignid);
		return campaignid;
	}

	public String createAndGetSavePurl(String accesstoken,String PurlName) throws Exception{
		JSONObject savePurlJson = util.parseJsonFile(savePurl);
		String newQuery = savePurlJson.get("query").toString();
		String purlName=uniqueNameGenerator(PurlName);
		
		Dictionary.put("NEW_PURLNAME", purlName);
		newQuery=updateQuery(newQuery, "name", purlName);
		newQuery=updateQuery(newQuery, "description", Dictionary.get("DESCRIPTION"));
		newQuery=updateQuery(newQuery, "templateId",Dictionary.get("templateId"));

		String updatedRequest="{\"query\":\""+newQuery+"\",\"variables\":null,\"operationName\":null}";
		JSONObject response = post(accesstoken,updatedRequest );
		JSONObject Obj = response.getJSONObject("data").getJSONObject("savePurl");
		String purlID= Obj.get("purlId").toString();
		Dictionary.put("PURLID", purlID);
		return purlID;
	}
	
	public void clonePurlWithInvalidData(String accesstoken,String purlId,String purlName) throws Exception	{	
		JSONObject clonePurlJson = util.parseJsonFile(clonePurl);
		String newQuery = clonePurlJson.get("query").toString();
		newQuery=updateQuery(newQuery, "purlId", purlId);
		newQuery=updateQuery(newQuery, "name", purlName);
		String updatedRequest="{\"query\":\""+newQuery+"\",\"variables\":null,\"operationName\":null}";
		JSONObject response = post(accesstoken,updatedRequest);
		/*JSONObject Obj = response.getJSONObject("data").getJSONObject("savePurl");
		String purlID= Obj.get("purlId").toString();
		Dictionary.put("purlID", purlID);
		return purlID;*/
	    JSONArray a=response.getJSONArray("errors");
	    JSONObject ob = a.getJSONObject(0);
	    String message=ob.getString("message");
	    String message1=(new JSONObject(message)).get("message").toString();
	    Dictionary.put("message",message1);
	    String statusCode=(new JSONObject(message)).get("statusCode").toString();
	    Dictionary.put("StatusCode",statusCode);
	     	
	}

	public String cloneSavePurl(String accesstoken, String purlId) throws Exception	{
		JSONObject savePurlJson = util.parseJsonFile(cloneSavePurl);
		String newQuery = savePurlJson.get("query").toString();
		String purlName= uniqueNameGenerator(Dictionary.get("CLONE_PURL"));
		Dictionary.put("NEW_PURLNAME", purlName);
		newQuery=updateQuery(newQuery, "name", purlName);
		newQuery=updateQuery(newQuery, "purlId", purlId);
         String updatedRequest="{\"query\":\""+newQuery+"\",\"variables\":null,\"operationName\":null}";
		JSONObject response = post(accesstoken,updatedRequest);
		JSONObject Obj = response.getJSONObject("data").getJSONObject("savePurl");
		String purlID= Obj.get("purlId").toString();
		Dictionary.put("CLONE_PURLID", purlID);
		Dictionary.put("PURLID", purlID);
		return purlID;
	}
	
	public void campaign_failed(String accesstoken,String purlId,String etemplateID, String Email, String master_template_id,String campaign_Name) throws Exception{
		JSONObject updatePurlInfoJson = util.parseJsonFile(createCampaign);
		String newQuery = updatePurlInfoJson.get("query").toString();
		String campaignName=uniqueNameGenerator(campaign_Name);
		Dictionary.put("CAMPAIGN_NAME", campaignName);
		newQuery=updateQuery(newQuery, "name", campaignName);
		newQuery=updateQuery(newQuery, "purlId", purlId);
		newQuery=updateQuery(newQuery, "eTemplateId", etemplateID);
		newQuery=updateQuery(newQuery, "masterETemplateId", master_template_id);
		newQuery=updateQuery(newQuery, "email", Email);
		
		String updatedRequest="{\"query\":\""+newQuery+"\",\"variables\":null,\"operationName\":null}";
		System.out.println(updatedRequest);

		JSONObject response= post(accesstoken,updatedRequest);
		System.out.println(response);
		JSONArray a=response.getJSONArray("errors");
		JSONObject ob = a.getJSONObject(0);
		String message=ob.getString("message");
		String message1=(new JSONObject(message)).get("message").toString();
		String statusCode=(new JSONObject(message)).get("statusCode").toString();
		Dictionary.put("failedMessage", message1);
		Dictionary.put("failedStatusCode", statusCode);
	}
	
	
	public void updatePurlWithInvlidData(String accesstoken,String purlId,String newPurlName,String purlType,String purlOwner,String slideOwner,String templateId) throws Exception		{
		JSONObject updatepurl = util.parseJsonFile(updatePurl);
		String newQuery = updatepurl.getString("query").toString();
		newQuery=updateQuery(newQuery, "purlId", purlId);
		newQuery=updateQuery(newQuery, "name", newPurlName);
        newQuery=updateQuery(newQuery, "purlType", purlType);
        newQuery=updateQuery(newQuery, "purlOwner",purlOwner);
		newQuery=updateQuery(newQuery, "slideOwner",slideOwner);
		newQuery=updateQuery(newQuery, "templateId",templateId);
		String updatedRequest="{\"query\":\""+newQuery+"\",\"variables\":null}";
		
		JSONObject response= post(accesstoken,updatedRequest); 
        JSONArray a=response.getJSONArray("errors");
	    JSONObject ob = a.getJSONObject(0);
	        String message=ob.getString("message");
	        String message1=(new JSONObject(message)).get("message").toString();
	        Dictionary.put("message",message1);
	        String statusCode=(new JSONObject(message)).get("statusCode").toString();
	        Dictionary.put("StatusCode",statusCode);
		}
	
	public String updatePurlWithInvlidPOWner_SlideOwner(String accesstoken,String purlId,String newPurlName,String purlType,String purlOwner,String slideOwner,String templateId ) throws Exception		{
		JSONObject updatepurl = util.parseJsonFile(updatePurl);
		String newQuery = updatepurl.getString("query").toString();
		newQuery=updateQuery(newQuery, "name", newPurlName);
		newQuery=updateQuery(newQuery, "purlId", purlId);
		newQuery=updateQuery(newQuery, "purlType", purlType);
        newQuery=updateQuery(newQuery, "purlOwner",purlOwner);
		newQuery=updateQuery(newQuery, "slideOwner",slideOwner);
		newQuery=updateQuery(newQuery, "templateId",templateId);
		String updatedRequest="{\"query\":\""+newQuery+"\",\"variables\":null}";
		JSONObject response= post(accesstoken,updatedRequest);           
        JSONArray a=response.getJSONArray("errors");
	    JSONObject ob = a.getJSONObject(0);
	        String message=ob.getString("message").toString();
	        //String message1=(new JSONObject(message)).get("message").toString();
	        Dictionary.put("message",message);
	        ///String statusCode=(new JSONObject(message)).get("statusCode").toString();
	        //Dictionary.put("statusCode",statusCode);
             return message;
	}
	
	public String deleteDataPurl(String accesstoken,String purlId) throws Exception		{
        JSONObject deleteP = util.parseJsonFile(deletePurl);
	    String newQuery = deleteP.get("query").toString();
		newQuery=updateQuery(newQuery, "purlId", purlId);
		String updatedRequest="{\"query\":\""+newQuery+"\",\"variables\":null}";
		JSONObject response= post(accesstoken,updatedRequest); 
        JSONObject Obj = response.getJSONObject("data").getJSONObject("deletePurl");
		String message= Obj.get("message").toString();
        Dictionary.put("message",message);
        //JSONObject Obj1 = response.getJSONObject("data").getJSONObject("deletePurl");
		String statusCode= Obj.get("statusCode").toString();
        Dictionary.put("StatusCode",statusCode);
        return message;	
	}
	
	public void searchPurlWithInvalidData(String accesstoken,String purlId) throws Exception{
	JSONObject campaignJson = util.parseJsonFile(getPurlP);
		String newQuery = campaignJson.get("query").toString();
		newQuery=updateQuery(newQuery, "purlId",purlId);
		String updatedRequest="{\"query\":\""+newQuery+"\",\"variables\":null,\"operationName\":null}";
		JSONObject response= post(accesstoken,updatedRequest);           
        JSONArray a=response.getJSONArray("errors");
	    JSONObject ob = a.getJSONObject(0);
	        String message=ob.getString("message");
	        String message1=(new JSONObject(message)).get("message").toString();
	        Dictionary.put("message",message1);
	        String statusCode=(new JSONObject(message)).get("statusCode").toString();
	        Dictionary.put("StatusCode",statusCode);
	}	
	
	
	
	public String waitForSentState(String accesstoken,String campaignId, String expectedStatus) throws Exception {
        String status = null;
        int counter = 100;
        do{
            JSONObject  response= searchCampaignById(accesstoken,campaignId);
             status = response.getJSONObject("data").getJSONObject("Campaign").get("status").toString();
            sync(100L);
            counter--;
        } while(counter > 0 && (status == null || !status.trim().equalsIgnoreCase(expectedStatus.trim())));
       Assert.assertEquals(status, expectedStatus);
        return status;
    }
	
	
	public String createPurlWithInvalidData(String accesstoken, String purlName,String purlType,String purlOwner,String slideOwner) throws Exception  {
		JSONObject updatePurlInfoJson = util.parseJsonFile(createPurl);
		String newQuery = updatePurlInfoJson.get("query").toString();
		newQuery=updateQuery(newQuery, "name", purlName);
		newQuery=updateQuery(newQuery, "purlType", purlType);
        newQuery=updateQuery(newQuery, "purlOwner",purlOwner);
		newQuery=updateQuery(newQuery, "slideOwner",slideOwner);
		newQuery=updateQuery(newQuery, "templateId",Dictionary.get("templateId"));
          String updatedRequest="{\"query\":\""+newQuery+"\",\"variables\":null}";
         JSONObject response= post(accesstoken,updatedRequest );
        JSONArray a=response.getJSONArray("errors");
         JSONObject ob = a.getJSONObject(0);
        String message=ob.getString("message");
        Dictionary.put("message",message);
         return message;
	}

	
	public void createPurlWithDuplicateData(String accesstoken, String purlName,String purlType,String purlOwner,String slideOwner,String templateId) throws Exception  {
		JSONObject updatePurlInfoJson = util.parseJsonFile(createPurl);
        String newQuery = updatePurlInfoJson.get("query").toString();
        newQuery=updateQuery(newQuery, "name", purlName);
        newQuery=updateQuery(newQuery, "purlType", purlType);
        newQuery=updateQuery(newQuery, "purlOwner",purlOwner);
        newQuery=updateQuery(newQuery, "slideOwner",slideOwner);
		newQuery=updateQuery(newQuery, "templateId",templateId);
      //newQuery=updateQuery(newQuery, "purlOwner",Dictionary.get("userId"));
       //newQuery=updateQuery(newQuery, "slideOwner",Dictionary.get("userId"));
       String updatedRequest="{\"query\":\""+newQuery+"\",\"variables\":null}";
       JSONObject response= post(accesstoken,updatedRequest);
       JSONArray a=response.getJSONArray("errors");
       JSONObject ob = a.getJSONObject(0);
       String message=ob.getString("message");
       String message1=(new JSONObject(message)).get("message").toString();
       Dictionary.put("message",message1);
       String statusCode=(new JSONObject(message)).get("statusCode").toString();
       Dictionary.put("StatusCode",statusCode);      
     }
	
	
	public String createCampaignWithDuplicateCampaignName(String accesstoken,String purlId,String etempateID,String masterETemplateId, String email) throws Exception{
		JSONObject updatePurlInfoJson = util.parseJsonFile(createCampaign);
		String newQuery = updatePurlInfoJson.get("query").toString();
		newQuery=updateQuery(newQuery, "name",Dictionary.get("CAMPAIGN_NAME"));
		newQuery=updateQuery(newQuery, "purlId", purlId);
		newQuery=updateQuery(newQuery, "eTemplateId", etempateID);
		newQuery=updateQuery(newQuery, "masterETemplateId", masterETemplateId);
		newQuery=updateQuery(newQuery, "email", email);
		String updatedRequest="{\"query\":\""+newQuery+"\",\"variables\":null,\"operationName\":null}";
		JSONObject response= post(accesstoken,updatedRequest );
		JSONArray a=response.getJSONArray("errors");
	       JSONObject ob = a.getJSONObject(0);
	       String message=ob.getString("message");
	       String message1=(new JSONObject(message)).get("message").toString();
	        Dictionary.put("message",message1);
	      String statusCode=(new JSONObject(message)).get("statusCode").toString();
	      Dictionary.put("StatusCode",statusCode);
	        return message;
	        
	}
	
	
	public void purlTemplateId(String accesstoken) throws Exception{
		JSONObject updatePurlInfoJson = util.parseJsonFile(purlTemplate);
		String newQuery = updatePurlInfoJson.get("query").toString();
		String updatedRequest="{\"query\":\""+newQuery+"\",\"variables\":null,\"operationName\":null}";
		JSONObject response= post(accesstoken,updatedRequest );
		JSONArray Obj = response.getJSONObject("data").getJSONArray("PurlTemplates");
	       JSONObject ob = Obj.getJSONObject(0);
	       int obj=ob.getInt("templateId");
	       System.out.println(obj);
}
	
	public String createAndGetSavePurlWithInvalidData(String accesstoken,String templateId) throws Exception{
		JSONObject savePurlJson = util.parseJsonFile(savePurl);
		String newQuery = savePurlJson.get("query").toString();
		String purlName=	uniqueNameGenerator("Purl-PennState Football-");
		Dictionary.put("NEW_PURLNAME", purlName);
		newQuery=updateQuery(newQuery, "name", purlName);
		newQuery=updateQuery(newQuery, "description", "Pen State Football stadium");
		newQuery=updateQuery(newQuery, "templateId",templateId);
		String updatedRequest="{\"query\":\""+newQuery+"\",\"variables\":null,\"operationName\":null}";
		JSONObject response = post(accesstoken,updatedRequest);
	    JSONArray a=response.getJSONArray("errors");
	    JSONObject ob = a.getJSONObject(0);
	    String message=ob.getString("message");
	    String message1=(new JSONObject(message)).get("message").toString();
	    Dictionary.put("message",message1);
	   String statusCode=(new JSONObject(message)).get("statusCode").toString();
	      Dictionary.put("StatusCode",statusCode);
	        return message;
	}
	
	
	public String getPurlTemplateId(JSONObject reponse) throws Exception{
		JSONArray Obj = reponse.getJSONObject("data").getJSONArray("PurlTemplates");
	    JSONObject ob = Obj.getJSONObject(0);
	    return ob.get("templateId").toString();
	}
	public String getETemplateId(JSONObject reponse) throws Exception{
		JSONArray Obj = reponse.getJSONObject("data").getJSONArray("Email");
	    JSONObject ob = Obj.getJSONObject(0);
	    return ob.get("templateId").toString();
	}
}


	

	/*	public String verifyResponseCode(String responsecode) {
	String message1 = null;
if(responsecode.contains("error")) {
		JSONObject jsonObj = new JSONObject(responsecode);
       JSONArray a=jsonObj.getJSONArray("errors");
	    JSONObject ob = a.getJSONObject(0);
	       String message=ob.getString("message");
	      message1=(new JSONObject(message)).get("message").toString();
	        Dictionary.put("message",message1);
	        String statusCode=(new JSONObject(message)).get("statusCode").toString();
	        Dictionary.put("statusCode",statusCode);   
	}
 else if(responsecode.contains("data")){
		JSONObject jsonObj = new JSONObject(responsecode);
       JSONObject Obj = jsonObj.getJSONObject("data").getJSONObject("deletePurl");
       message1 = Obj.getString("message").toString();
		//System.out.println(message1);
        Dictionary.put("message",message1);
        //JSONObject Obj1 = response.getJSONObject("data").getJSONObject("deletePurl");
		String statusCode= Obj.get("statusCode").toString();
        Dictionary.put("statusCode",statusCode);
		}
			System.out.println("ytytytytyuty"+message1);   

	
	return message1;

	
}*/


	


	



	
	
	


	
	
	

	

	

	

	

	

	

	

	

	
	


