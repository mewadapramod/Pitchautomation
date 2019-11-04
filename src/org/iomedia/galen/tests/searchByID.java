package org.iomedia.galen.tests;

import org.json.JSONArray;
import org.json.JSONObject;

import org.testng.annotations.Test;
public class searchByID {
	HttpURLConnection HCE = new HttpURLConnection();
	String SearchById = "{\"query\":\"{\\n  SearchArchiticsCustomers(architicsId:\\\"1\\\"){\\n    \\n    Customers{\\n      customerId\\n      architicsId\\n      fName\\n      lName\\n      email\\n      cusNameId\\n      contact{\\n        addressLine1\\n        addressLine2\\n        city\\n        state\\n        country\\n        type\\n        zip\\n        phones{\\n          phoneCode\\n          phoneNumber\\n          phoneType\\n        }\\n      }\\n      recStatus\\n      hasTickets\\n      accType\\n      accTypeDesc\\n      \\n  }\\n  }\\n}\",\"variables\":null,\"operationName\":null}";
	
	@Test(groups= {"prod"})
	public void searchArchticsByID() throws Exception {
		System.out.println("SearchBYID");
		JSONObject jsonObject1=HCE.postrequest(SearchById);	
			System.out.println(jsonObject1);
			JSONObject firstObject = jsonObject1.getJSONObject("data");
			JSONObject secondObject=firstObject.getJSONObject("SearchArchiticsCustomers");
			JSONArray thirdObject=  secondObject.getJSONArray("Customers");
			JSONObject ob = thirdObject.getJSONObject(0);
			System.out.println(ob.getString("fName"));
			System.out.println(ob.getString("lName"));
			System.out.println(ob.getString("email"));
			System.out.println(ob.getString("cusNameId"));
			System.out.println(ob.getString("recStatus"));
			System.out.println(ob.getString("accType"));
			System.out.println(ob.getString("accTypeDesc"));
			System.out.println(ob.getString("recStatus"));

			JSONArray ob1 = ob.getJSONArray("contact");
			JSONObject ob3 = ob1.getJSONObject(0);

			System.out.println(ob3.getString("addressLine1"));
			System.out.println(ob3.getString("addressLine2"));
			System.out.println(ob3.getString("city"));
			System.out.println(ob3.getString("state"));
			System.out.println(ob3.getString("country"));
			System.out.println(ob3.getString("type"));
			System.out.println(ob3.getString("zip"));	 
			System.out.println("*******************************************************");
			
			//verify by UI
			
		}
	}

