package org.iomedia.galen.tests;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.EmptyStackException;

import javax.net.ssl.HttpsURLConnection;

import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.iomedia.common.BaseUtil;
import org.testng.annotations.Test;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;
import org.iomedia.framework.Driver;

public class HttpURLConnection extends Driver {
	org.iomedia.framework.Assert Assert;
	BaseUtil base;
	//AccessToken accessToken;
	String Campaignlist="{\"query\":\"{\\n  CampaignList{\\n    Campaigns{\\n      campaignId\\n      name\\n      createdBy\\n    }\\n  }\\n}\",\"variables\":null,\"operationName\":null}";
	String query = "{\"query\":\"{\\n  SearchArchiticsCustomers(architicsId:\\\"1\\\"){\\n    \\n    Customers{\\n      customerId\\n      architicsId\\n      fName\\n      lName\\n      email\\n      cusNameId\\n      contact{\\n        addressLine1\\n        addressLine2\\n        city\\n        state\\n        country\\n        type\\n        zip\\n        phones{\\n          phoneCode\\n          phoneNumber\\n          phoneType\\n        }\\n      }\\n      recStatus\\n      hasTickets\\n      accType\\n      accTypeDesc\\n      \\n  }\\n  }\\n}\",\"variables\":null,\"operationName\":null}";
	String createcampaign="{\"query\":\"mutation{\\n  createCampaign(\\n    name:\\\"Automated Campaign\\\"\\n    purlId:\\\"T_1514263389784_1_1\\\"\\n    description:\\\"Api Testing by automation\\\"\\n    eTemplateId:\\\"0ca2d570-e636-11e7-b12d-11a112e2635f\\\"\\n    customerId:6\\n    customerName:\\\"Akanksha\\\"\\n  ){\\n    campaignId\\n  }\\n}\",\"variables\":null}";
	String Campaignidstart="{\"query\":\"{\\n  Campaign(campaignId:\\\"";
	String campaignnumber;
	String Campaignidend="\\\"){\\n    campaignId\\n    name\\n    description\\n    purl{\\n      purlId\\n      name\\n    }\\n    eTemplate{\\n      eTemplateId\\n      name\\n    }\\n    createdOn\\n    createdBy\\n    customerId\\n    customerName\\n    startDate\\n    status\\n    comment\\n    \\n  }\\n}\",\"variables\":null,\"operationName\":null}";
	private String campaignID1;
	public String sendGet() throws Exception {
		//load("/");
		
		System.out.println("token");
		String  accessToken=  getAccessToken(Environment.get("appCredentialsEmailAddressKey"),Environment.get("appCredentialsPasswordKey"));
		return accessToken;
	}

//	@Test(groups= {"prod"})
//	private void searchArchticsByID() throws Exception {
//		System.out.println("Post");
//		String at=sendGet();
//		URL url = new URL(Environment.get("TM_OAUTH_URL").trim()+"/graphql");
//		//URL obj = new URL(url);
//		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
//
//		conn.setDoOutput(true);
//		conn.setRequestMethod("POST");
//		conn.setRequestProperty("Content-Type", "application/json");
//		conn.setRequestProperty("Authorization", at);
//		conn.setRequestProperty("Accept", "application/json, text/javascript, */*; q=0.01");
//
//		
//		DataOutputStream output = null;
//		output = new DataOutputStream(conn.getOutputStream());
//		output.writeBytes(query);
//		output.flush();
//		output.close();
//
//
//		int responseCode = conn.getResponseCode();
//		System.out.println(responseCode);
//		//        
//
//		if(responseCode == 201 || responseCode == 200 || responseCode == 204){
//			BufferedReader br1 = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//			String output2 = "";
//			Boolean keepGoing1 = true;
//			while (keepGoing1) {
//				String currentLine = br1.readLine();
//				if (currentLine == null) {
//					keepGoing1 = false;
//				} else {
//					output2 += currentLine;
//				}
//			}
//			JSONObject jsonObject1=new JSONObject(output2);	
//			System.out.println(jsonObject1);
//			JSONObject firstObject = jsonObject1.getJSONObject("data");
//			JSONObject secondObject=firstObject.getJSONObject("SearchArchiticsCustomers");
//			JSONArray thirdObject=  secondObject.getJSONArray("Customers");
//			JSONObject ob = thirdObject.getJSONObject(0);
//			System.out.println(ob.getString("fName"));
//			System.out.println(ob.getString("lName"));
//			System.out.println(ob.getString("email"));
//			System.out.println(ob.getString("cusNameId"));
//			//System.out.println(ob.getString("customerId"));
//			System.out.println(ob.getString("recStatus"));
//			//  System.out.println(ob.getString("hasTickets")); 
//			System.out.println(ob.getString("accType"));
//			System.out.println(ob.getString("accTypeDesc"));
//			System.out.println(ob.getString("recStatus"));
//
//			JSONArray ob1 = ob.getJSONArray("contact");
//			JSONObject ob3 = ob1.getJSONObject(0);
//
//			System.out.println(ob3.getString("addressLine1"));
//			System.out.println(ob3.getString("addressLine2"));
//			System.out.println(ob3.getString("city"));
//			System.out.println(ob3.getString("state"));
//			System.out.println(ob3.getString("country"));
//			System.out.println(ob3.getString("type"));
//			System.out.println(ob3.getString("zip"));	 
//			System.out.println("*******************************************************");
//		}
//	}

	public String getAccessToken(String emailaddress, String password) throws JSONException, IOException, ParseException {
		String access_token = null;
		int responseCode = 0;
		int counter = 3;
		do{
			Object[] obj = postOauthToken(emailaddress, password);
			access_token = (String) obj[0];
			responseCode = (int) obj[1];
			counter--;
		} while(counter > 0 && (access_token == null));
		System.out.println("Server response code : " + String.valueOf(responseCode));
		System.out.println("Token ="+access_token);
		//	Assert.assertNotNull(access_token, "Server response code : " + String.valueOf(responseCode));
		return access_token;
	}

	public Object[] postOauthToken(String emailaddress, String password) throws IOException, JSONException, ParseException{

		URL url = new URL(Environment.get("TM_OAUTH_URL").trim()+"/login");

		JSONObject item = new JSONObject();

		item.put("username", emailaddress);
		item.put("password", password);

		String message= item.toString();
		System.out.println(message);

		HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setDoOutput(true);
		DataOutputStream output = null;
		output = new DataOutputStream(conn.getOutputStream());
		String content = message;
		output.writeBytes(content);
		output.flush();
		output.close();

		int responseCode = conn.getResponseCode();
		if(responseCode == 201 || responseCode == 200 || responseCode == 204){
			BufferedReader br1 = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String output2 = "";
			Boolean keepGoing1 = true;
			while (keepGoing1) {
				String currentLine = br1.readLine();
				if (currentLine == null) {
					keepGoing1 = false;
				} else {
					output2 += currentLine;
				}
			}
			JSONObject jsonObject1=new JSONObject(output2);	
			JSONObject innerObject = jsonObject1.getJSONObject("command");
			String token=(String) innerObject.get("token");
			return new Object[]{token, responseCode};
		} else {
			return new Object[]{null, responseCode};
		}
	}

//	@Test (groups= {"prod"})
//	private void CampaignLIST() throws Exception  {
//		System.out.println("Campaign list");
//		String at=sendGet();
//		URL url = new URL(Environment.get("TM_OAUTH_URL").trim()+"/graphql");
//		//URL obj = new URL(url);
//		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
//
//		conn.setDoOutput(true);
//		conn.setRequestMethod("POST");
//		conn.setRequestProperty("Content-Type", "application/json");
//		conn.setRequestProperty("Authorization", at);
//		conn.setRequestProperty("Accept", "application/json, text/javascript, */*; q=0.01");
//
//		
//		DataOutputStream output = null;
//		output = new DataOutputStream(conn.getOutputStream());
//		output.writeBytes(Campaignlist);
//		output.flush();
//		output.close();
//
//
//		int responseCode = conn.getResponseCode();
//		System.out.println(responseCode);
//		//        
//
//		if(responseCode == 201 || responseCode == 200 || responseCode == 204){
//			BufferedReader br1 = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//			String output2 = "";
//			Boolean keepGoing1 = true;
//			while (keepGoing1) {
//				String currentLine = br1.readLine();
//				if (currentLine == null) {
//					keepGoing1 = false;
//				} else {
//					output2 += currentLine;
//				}
//			}
//			JSONObject jsonObject1=new JSONObject(output2);	
//			System.out.println(jsonObject1);
//			System.out.println("*******************************************************");
//			
//			
//	}
//
//	}
//	
////	@Test (priority=1)
//	public void CreateCampaign() throws Exception {
//		CreateCampaignID();	
//		
//
//	}
//	
//	
//	
//	public String querytocampaignID() throws Exception {
//		StringBuilder sb = new StringBuilder(); 
////		String x=CreateCampaignID();	
//		sb.append(Campaignidstart).append(campaignnumber).append(Campaignidend);
//		System.out.println(sb.toString());
//		String campaignID=sb.toString();
//	return campaignID;
//
//	}
	
	
	public JSONObject postrequest(String X) throws Exception {
		String at=sendGet();
		URL url = new URL(Environment.get("TM_OAUTH_URL").trim()+"/graphql");
		//URL obj = new URL(url);
		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setRequestProperty("Authorization", at);
		conn.setRequestProperty("Accept", "application/json, text/javascript, */*; q=0.01");

		
		DataOutputStream output = null;
		output = new DataOutputStream(conn.getOutputStream());
		output.writeBytes(X);
		output.flush();
		output.close();


		int responseCode = conn.getResponseCode();
		System.out.println(responseCode);
		//        

		if(responseCode == 201 || responseCode == 200 || responseCode == 204){
			BufferedReader br1 = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String output2 = "";
			Boolean keepGoing1 = true;
			while (keepGoing1) {
				String currentLine = br1.readLine();
				if (currentLine == null) {
					keepGoing1 = false;
				} else {
					output2 += currentLine;
				}
			}
			JSONObject jsonObject1=new JSONObject(output2);
			return jsonObject1;
		} else {
		return null;
		}
	}


//	private String CreateCampaignID() throws Exception  {
//		System.out.println("Create Campaign");
//		JSONObject jsonObject1=postrequest( createcampaign);		
//			System.out.println(jsonObject1);
//			JSONObject firstObject = jsonObject1.getJSONObject("data");
//			JSONObject secondObject=firstObject.getJSONObject("createCampaign");
//			String campaignID1=((String) secondObject.get("campaignId"));
//			System.out.println("campaign id is-"+campaignID1);
//			this.campaignnumber = campaignID1;
//			return campaignID1;
//			
//			}
//	
//
//	
////	@Test 
////	(priority=2)
//	private void SearchCampaignBYID() throws Exception  {
//		System.out.println("CampaignBYID");
//		JSONObject jsonObject1=postrequest(querytocampaignID());
//			System.out.println(jsonObject1);
//
//			JSONObject firstObject = jsonObject1.getJSONObject("data");
//			JSONObject Campaign=firstObject.getJSONObject("Campaign");
//			String A=(Campaign.getString("name"));
//			JSONObject purl=Campaign.getJSONObject("purl");
//			JSONObject etemp=Campaign.getJSONObject("eTemplate");
//
//			//Verifying data		
//			datamatcher(campaignnumber, Campaign.getString("campaignId"));
//			datamatcher("Automated Campaign", Campaign.getString("name"));
//			datamatcher("Api Testing by automation", Campaign.getString("description"));
//			datamatcher("Akansha Dhall",Campaign.getString("createdBy")) ;
//			datamatcher("Akanksha", Campaign.getString("customerName"));
//			datamatcher("In Queue", Campaign.getString("status"));
//			datamatcher("T_1514263389784_1_1", purl.getString("purlId"));
//			datamatcher("Automated Purl", purl.getString("name"));
//			datamatcher("Test", etemp.getString("name"));
//			datamatcher("0ca2d570-e636-11e7-b12d-11a112e2635f",etemp.getString("eTemplateId"));
//		
//			
//	}
	
	public void datamatcher(String A, String B) {
		if(A.equalsIgnoreCase(B)) {
			
		}
		else {
			System.out.println("Text is not matching");
			 throw new EmptyStackException();
			
		}
	}
	public void datamatcher(Integer A, Integer B) {
		if(A.equals(B)) {
			
		}
		else {
			System.out.println("Text is not matching");
			 throw new EmptyStackException();
			
		}
	}

	}
	
	
	
	


