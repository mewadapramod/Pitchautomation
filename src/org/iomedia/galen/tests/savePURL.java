package org.iomedia.galen.tests;

import org.json.JSONObject;
import org.testng.annotations.Test;
import org.iomedia.galen.tests.HttpURLConnection;

public class savePURL {

	HttpURLConnection HCE = new HttpURLConnection();
	String savePurl="{\"query\":\"mutation{\\n   savePurl(templateId:1\\n    name:\\\"Automated Penn State Football\\\"\\n  description:\\\"runned by script\\\")\\n  {\\n    purlId\\n  }\\n}\",\"variables\":null}";
	String updateStatusToPublic="{\"query\":\"mutation{\\n  updatePurlInfo( purlId:\\\"T_1514374249896_1_1\\\"\\n  purlType:public){\\n    statusCode\\n    code\\n    message\\n  }\\n}\",\"variables\":null}";
	String updateStatusToPrivate="{\"query\":\"mutation{\\n  updatePurlInfo( purlId:\\\"T_1514374249896_1_1\\\"\\n  purlType:private){\\n    statusCode\\n    code\\n    message\\n  }\\n}\",\"variables\":null}";
	String searchPurlById="{\"query\":\"{\\n  Purl(purlId:\\\"T_1514374249896_1_1\\\"){\\n    name\\n    description\\n    purlType\\n    templateId\\n  }\\n}\",\"variables\":null,\"operationName\":null}";
	
	public String savepurlID1() throws Exception {
		/////to create purl id
		System.out.println("savepurl");
		JSONObject jsonObject1=HCE.postrequest(savePurl);
		System.out.println(jsonObject1);
		JSONObject secondObject=jsonObject1.getJSONObject("data");
		JSONObject savepurl=secondObject.getJSONObject("savePurl");
		String purlId=((String) savepurl.get("purlId"));
		System.out.println("PurlID id is-"+purlId);
		return purlId;
	}
	@Test(groups= {"prod"})
	public void savepurlID2() throws Exception {

		savepurlID1();
	}
	public Object[] SearchPurlBYID() throws Exception {
		System.out.println("Search Purl By ID");
		JSONObject jsonObject1=HCE.postrequest(searchPurlById);
		System.out.println(jsonObject1);
		JSONObject secondObject=jsonObject1.getJSONObject("data");
		JSONObject savepurl=secondObject.getJSONObject("Purl");
		String PurlNAME=((String) savepurl.get("name"));
		String PurlDescription=((String) savepurl.get("description"));
		String PurlType=((String) savepurl.get("purlType"));
		Integer templateId=((Integer) savepurl.get("templateId"));
		return new Object[] {PurlNAME,PurlDescription,PurlType,templateId};
	}
	
	@Test(groups= {"prod"})
	public void updateStatusPublic_Verify() throws Exception {
		System.out.println("Update Status");
		JSONObject jsonObject1=HCE.postrequest(updateStatusToPublic);
		System.out.println(jsonObject1);
		JSONObject secondObject=jsonObject1.getJSONObject("data");
		JSONObject purlINFO=secondObject.getJSONObject("updatePurlInfo");
		String code=((String) purlINFO.get("code"));
		String message=((String) purlINFO.get("message"));
		Integer Statuscode=((Integer) purlINFO.get("statusCode"));


		HCE.datamatcher(Statuscode, 200);
		HCE.datamatcher(code, "OK");
		HCE.datamatcher(message, "Purl info updated successfully");
		verifypurldetails("public");
	}
		//SearchBy Id to verify the status and details of the purl
	public void verifypurldetails(String PURLTYPE) throws Exception {	
		Object[] obj = SearchPurlBYID();
		String PurlNAME = (String) obj[0];
		String PurlDescription = (String) obj[1];
		String PurlType = (String) obj[2];
		Integer templateId = (int) obj[3];
		HCE.datamatcher("Automated Penn State Football", PurlNAME);
		HCE.datamatcher("runned by script", PurlDescription);
		HCE.datamatcher(PURLTYPE, PurlType);
		HCE.datamatcher(1, templateId);
		System.out.println("****************************************************");
		
	}
	
	
	@Test(groups= {"prod"})
	public void updatestatusToPrivate_verify () throws Exception {
		System.out.println("Update Status");
		JSONObject jsonObject1=HCE.postrequest(updateStatusToPrivate);
		System.out.println(jsonObject1);
		JSONObject secondObject=jsonObject1.getJSONObject("data");
		JSONObject purlINFO=secondObject.getJSONObject("updatePurlInfo");
		String code=((String) purlINFO.get("code"));
		String message=((String) purlINFO.get("message"));
		Integer Statuscode=((Integer) purlINFO.get("statusCode"));


		HCE.datamatcher(Statuscode, 200);
		HCE.datamatcher(code, "OK");
		HCE.datamatcher(message, "Purl info updated successfully");
		verifypurldetails("private");
	}

}
