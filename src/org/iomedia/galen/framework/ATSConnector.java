package org.iomedia.galen.framework;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.*;
import java.security.KeyStore;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

import org.apache.http.ssl.SSLContexts;
import org.json.JSONException;
import org.json.JSONObject;

public class ATSConnector {
	HttpURLConnection ATSConnection;

	public ATSConnector(String url) throws IOException {
		if(!url.trim().equalsIgnoreCase("")) {
			URL urlObj = new URL(url);
			// URL urlObj = new URL(null, url, new
			// sun.net.www.protocol.https.Handler());
	
			this.ATSConnection = (HttpURLConnection) urlObj.openConnection();
			this.ATSConnection.setRequestMethod("POST");
		}
	}
	
	public String postRequest(JSONObject obj) throws IOException, ATSException {
		String JSONStr = obj.toString();

		this.ATSConnection.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(ATSConnection.getOutputStream());
		wr.writeBytes(JSONStr);
		wr.flush();
		wr.close();

		int responseCode = this.ATSConnection.getResponseCode();
		if (responseCode != 200) {
			throw new ATSException("The response from ATS was not proper");
		}

		BufferedReader in = new BufferedReader(new InputStreamReader(this.ATSConnection.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		// System.out.println("Response");
		// System.out.println(response.toString());

		return response.toString();
	}

	public boolean checkResponse(JSONObject response) throws ATSException {
		try {
			if (response.getJSONObject("command1").getInt("result") == 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			throw new ATSException("Improper response from ATS \n" + response);
		}
	}

	@SuppressWarnings("serial")
	static class ATSException extends Exception {
		public ATSException(String message) {
			super(message);
		}
	}
	
	public String readAll(Reader rd) throws IOException {
	    StringBuilder sb = new StringBuilder();
	    int cp;
	    while ((cp = rd.read()) != -1) {
	      sb.append((char) cp);
	    }
	    return sb.toString();
	}
	
	public String getTMInvoiceAPIResponse(String dsn, JSONObject payload) throws JSONException, Exception {
		String keyPassphrase = "";

		KeyStore keyStore = KeyStore.getInstance("PKCS12");
		keyStore.load(new FileInputStream("live.iomedia_tm_v2.pfx"), keyPassphrase.toCharArray());

		SSLContext sslContext = SSLContexts.custom()
		        .loadKeyMaterial(keyStore, null)
		        .build();
        
        final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();            
        String url = "https://ws.ticketmaster.com/archtics/ats/ticketing_services.aspx?dsn=" + dsn;
        URLConnection urlCon = new URL(url).openConnection();
        urlCon.setRequestProperty("Method", "POST");
		urlCon.setRequestProperty("Accept", "application/json");
		
        urlCon.setDoOutput(true);
        ( (HttpsURLConnection) urlCon ).setSSLSocketFactory(sslSocketFactory);
		
		// Send request
        DataOutputStream wr = new DataOutputStream(( (HttpsURLConnection) urlCon ).getOutputStream());
    	wr.writeBytes(payload.toString());
        
        wr.flush();
        wr.close();
        
    	int responseCode = ((HttpsURLConnection)urlCon).getResponseCode();
    	
    	InputStream is = null;
    	
        if(responseCode == 200 || responseCode == 204) {
        	is = urlCon.getInputStream();  
            String response = readAll(new BufferedReader(new InputStreamReader(is)));
            return response;
        }
        else {
        	return null;
        }
	}
	
	public String waitForTMInvoiceResponse(String dsn, JSONObject payload) throws JSONException, Exception {
		String response = null;
		int counter = 5;
		do{
			response = getTMInvoiceAPIResponse(dsn, payload);
			Thread.sleep(100L);
			counter--;
		} while(counter > 0 && (response == null || response.trim().equalsIgnoreCase("")));
		return response;
	}
}
