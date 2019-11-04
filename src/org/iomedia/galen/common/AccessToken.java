package org.iomedia.galen.common;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import org.iomedia.common.BaseUtil;
import org.iomedia.framework.Driver.HashMapNew;
import org.iomedia.framework.Reporting;
import org.iomedia.framework.WebDriverFactory;
import org.json.JSONException;
import org.json.JSONObject;

public class AccessToken extends BaseUtil{
	
	public AccessToken(WebDriverFactory driverFactory, HashMapNew Dictionary, HashMapNew Environment, Reporting Reporter, org.iomedia.framework.Assert Assert, org.iomedia.framework.SoftAssert SoftAssert, ThreadLocal<HashMapNew> sTestDetails) {
		super(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
	}
	
	Utils utils = new Utils(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
	String host= Environment.get("TM_HOST").trim();
	
	public String getAccessToken(String emailaddress, String password) throws JSONException, IOException  {
		//System.out.println("Email: "+emailaddress  +"  pass:  "+password);
		String access_token = null;
		String userId = null;
		String createdBy=null;
		String createdBy1=null;

		int responseCode = 0;
		int counter = 4;
		do{
			//System.out.println("acesstoken ");
			Object[] obj = postOauthToken(emailaddress, password);
			//System.out.println("acesstoken "  + obj[4]  + "dfgghjg "+ obj[3]);
			//System.out.println("object "+obj.toString());
			access_token = (String) obj[0];
			responseCode = (int) obj[1];
			userId = (String) obj[2];
			createdBy=(String) obj[3];
			//createdBy1= (String) obj[3];

			createdBy1= (String) obj[4];
			counter--;
		} while(counter > 0 && (access_token == null));
		//System.out.println("Token ="+access_token);
		Dictionary.put("Token", access_token);
		Dictionary.put("userId", userId);
		Dictionary.put("PURL_OWNER", userId);

		Dictionary.put("createdBy",createdBy);
		Dictionary.put("createdBy1",createdBy1);
		//System.out.println("userID:: "+ Dictionary.get("userId"));
		Assert.assertNotNull(access_token, "Server response code : " + String.valueOf(responseCode));
		return access_token;
	}
	
	public Object[] postOauthToken(String emailaddress, String password) throws IOException, JSONException {
		//System.out.println("posty sadsfsEmail: "+emailaddress  +"  pass:  "+password);
		URL url = new URL(Environment.get("LAMBDA_URL").trim()+"/login");

		JSONObject item = new JSONObject();

		item.put("username", emailaddress);
		item.put("password", password);
		//System.out.println("post aoauth toekn ");
		String message= item.toString();
		//System.out.println(message);

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
				System.out.println("tewwerwe"+responseCode);
			}
			JSONObject jsonObject1=new JSONObject(output2);	
			JSONObject innerObject = jsonObject1.getJSONObject("command");
			String token= (String) innerObject.get("token");
			//String userId=innerObject.getJSONObject("role").get("user_id").toString(); 
			//String userId=innerObject.getJSONObject("userId").toString(); 
			String userId=jsonObject1.getJSONObject("command").get("userId").toString(); 


			
			String firstName=innerObject.getJSONObject("userDetails").get("firstName").toString(); 
		//	String middleName=innerObject.getJSONObject("userDetails").get("middleName").toString(); 
			String lastName=innerObject.getJSONObject("userDetails").get("lastName").toString();
			String createdBy= firstName+" "+lastName;
			//System.out.println(createdBy);
			String createdBy1= firstName+"   "+lastName;
			//System.out.println(token  +  "   rer  "+responseCode +" cfg   "+ userId +  "dfg   " +createdBy +"  dfgh "+  createdBy1);
			return new Object[]{token,responseCode, userId, createdBy,createdBy1};
			//return new Object[]{token,responseCode, userId,createdBy1};


		} else {
			
			System.out.println("Server Response Code"+responseCode);
			return new Object[]{null, responseCode, null};
		}
	}
}	
