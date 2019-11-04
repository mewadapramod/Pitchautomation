package org.iomedia.galen.tests;

import org.iomedia.galen.tests.HttpURLConnection;
import org.json.JSONObject;
import org.testng.annotations.Test;

public class CampaignByList {

	HttpURLConnection HCE = new HttpURLConnection();
	String Campaignlist="{\"query\":\"{\\n  CampaignList{\\n    Campaigns{\\n      campaignId\\n      name\\n      createdBy\\n    }\\n  }\\n}\",\"variables\":null,\"operationName\":null}";
	
	@Test (groups= {"prod"})
	public void CampaignLIST() throws Exception  {
		System.out.println("Campaign list");
		JSONObject jsonObject1=HCE.postrequest(Campaignlist);
		System.out.println(jsonObject1);
		System.out.println("*******************************************************");
		
	//It should be verified by UI.		
			
	}

	}
	

