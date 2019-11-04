package org.iomedia.galen.tests;

import org.json.JSONException;
import org.json.simple.parser.ParseException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.io.IOException;
import org.iomedia.framework.Driver;
import org.iomedia.galen.common.PitchmanApi;


public class createcampaignandsearchByID extends Driver{
	
	
	private PitchmanApi pitchmanApi;
	
	@BeforeMethod(alwaysRun=true)
	public void init() throws JSONException, IOException, ParseException 	{
		pitchmanApi= new PitchmanApi(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);	
	}
	
	@Test
	public void testAccessToken() throws Exception  {
		pitchmanApi.getAccessToken(Dictionary.get("EMAIL_ADDRESS"), Dictionary.get("PASSWORD"));
		System.out.println(pitchmanApi.searchCampaignById(Dictionary.get("Token"), "C_1515734152255_1_1"));
	}
}
