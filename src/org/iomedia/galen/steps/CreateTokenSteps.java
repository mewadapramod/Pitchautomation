package org.iomedia.galen.steps;


import java.io.IOException;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.iomedia.common.BaseUtil;

import org.iomedia.galen.common.Utils;

import org.iomedia.galen.common.AccessToken;

import org.iomedia.galen.pages.DashboardSection;
import org.iomedia.galen.pages.Hamburger;
import org.json.JSONException;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;


public class CreateTokenSteps {

       AccessToken token;
       Utils utils;
   	   BaseUtil base;
	
	  public CreateTokenSteps(AccessToken token,Utils utils, BaseUtil base) {
	
		this.token = token;
		this.utils = utils;
		this.base = base;
	
	}

	@Given("^User generates access Token for (.+) and (.+)$")
	public String generate_access_token(String emailaddress,String password) throws Exception {
		emailaddress = (String) base.getGDValue(emailaddress);
		password = (String) base.getGDValue(password);
		return token.getAccessToken(emailaddress, password);	
	}
}
