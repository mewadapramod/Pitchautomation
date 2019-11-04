package org.iomedia.galen.framework;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;
import java.util.regex.Pattern;

import org.iomedia.framework.Driver.HashMapNew;
import org.iomedia.framework.OSValidator;
import org.iomedia.framework.WebDriverFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.opencsv.CSVWriter;

public class CreateAccounts {
  HashMapNew Environment;
  static CSVWriter csvOutput = null;
  
  public CreateAccounts(HashMapNew Environment) {
    WebDriverFactory driverFactory = new WebDriverFactory(); 
    this.Environment = Environment == null || Environment.size() == 0 ? (driverFactory.getEnvironment() == null ? null : driverFactory.getEnvironment().get()) : Environment;
  }
  
  public void updateConfigJSON(String ats, String db_server, String dsn, int number_of_accounts, List<String> events, List<String> parking_events, List<String> handling_events, int payment_plan_id, String uid) throws Exception {
    String site_name = Environment.get("x-client").trim();
    
    JSONObject obj = update(parseJsonFile("config.json"), "ats", ats);
    obj = update(obj, "db_server", db_server);
    obj = update(obj, "dsn", dsn);
    if(!dsn.trim().equalsIgnoreCase("genesis"))
      obj = update(obj, "uid", uid);
    obj = update(obj, "number_of_accounts", number_of_accounts);
    obj = update(obj, "events", events);
    obj = update(obj, "parking_events", parking_events);
    obj = update(obj, "handling_events", handling_events);
    obj = update(obj, "site_name", site_name);
    
    int number_of_seats_per_block = System.getProperty("number_of_seats_per_block") != null && !System.getProperty("number_of_seats_per_block").trim().equalsIgnoreCase("") ? Integer.valueOf(System.getProperty("number_of_seats_per_block").trim()) : Integer.valueOf(Environment.get("number_of_seats_per_block").trim());
    int seats_paid = System.getProperty("seats.paid") != null && !System.getProperty("seats.paid").trim().equalsIgnoreCase("") ? Integer.valueOf(System.getProperty("seats.paid").trim()) : Integer.valueOf(Environment.get("seats.paid").trim());
    int seats_unpaid = System.getProperty("seats.unpaid") != null && !System.getProperty("seats.unpaid").trim().equalsIgnoreCase("") ? Integer.valueOf(System.getProperty("seats.unpaid").trim()) : Integer.valueOf(Environment.get("seats.unpaid").trim());
    int parking_paid = System.getProperty("parking.paid") != null && !System.getProperty("parking.paid").trim().equalsIgnoreCase("") ? Integer.valueOf(System.getProperty("parking.paid").trim()) : Integer.valueOf(Environment.get("parking.paid").trim());
    int parking_unpaid = System.getProperty("parking.unpaid") != null && !System.getProperty("parking.unpaid").trim().equalsIgnoreCase("") ? Integer.valueOf(System.getProperty("parking.unpaid").trim()) : Integer.valueOf(Environment.get("parking.unpaid").trim());
    int handling_paid = System.getProperty("handling.paid") != null && !System.getProperty("handling.paid").trim().equalsIgnoreCase("") ? Integer.valueOf(System.getProperty("handling.paid").trim()) : Integer.valueOf(Environment.get("handling.paid").trim());
    int handling_unpaid = System.getProperty("handling.unpaid") != null && !System.getProperty("handling.unpaid").trim().equalsIgnoreCase("") ? Integer.valueOf(System.getProperty("handling.unpaid").trim()) : Integer.valueOf(Environment.get("handling.unpaid").trim());
    String spayment_plan_id = System.getProperty("payment_plan_id") != null && !System.getProperty("payment_plan_id").trim().equalsIgnoreCase("") ? System.getProperty("payment_plan_id").trim() : String.valueOf(payment_plan_id);
    
    obj = update(obj, "number_of_seats_per_block", number_of_seats_per_block);
    obj = update(obj, "seats", new JSONObject("{\"paid\": " + seats_paid + ",\"unpaid\": " + seats_unpaid + "}"));
    obj = update(obj, "parking", new JSONObject("{\"paid\": " + parking_paid + ",\"unpaid\": " + parking_unpaid + "}"));
    obj = update(obj, "handling", new JSONObject("{\"paid\": " + handling_paid + ",\"unpaid\": " + handling_unpaid + "}"));
    if(!spayment_plan_id.trim().equalsIgnoreCase("")) {
      obj = update(obj, "payment_plan_id", spayment_plan_id);
    }
    
    try (FileWriter file = new FileWriter(System.getProperty("user.dir") + OSValidator.delimiter + "APIRequest" + OSValidator.delimiter + "config.json")) {
             file.write(obj.toString(2));
             System.out.println("Successfully updated json object to file...!!");
         }
  }
  
  public HashMap<String, String[]> create(HashMap<String, String[]> appCredentials, boolean createOnly, String datasheet, boolean buyEvents) throws JSONException, Exception {
    boolean useATSGenericUrl = System.getProperty("useATSGenericUrl") != null && !System.getProperty("useATSGenericUrl").trim().equalsIgnoreCase("") ? Boolean.valueOf(System.getProperty("useATSGenericUrl").trim()) : Boolean.valueOf(Environment.get("useATSGenericUrl").trim());
    if(!useATSGenericUrl) {
      if(Environment.get("ats").trim().equalsIgnoreCase(""))
        return null;
    }
    
    String credFolder = System.getProperty("user.dir") + OSValidator.delimiter +  "Credentials" + OSValidator.delimiter;
    if(!new File(credFolder).exists()) {
      new File(credFolder).mkdirs();
    }
    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
    String resultPath = credFolder + Environment.get("DSN").trim() + "_AcctGen" + timeStamp + ".csv";
    System.out.println("Output in file: " + resultPath);

    csvOutput = new CSVWriter(new FileWriter(resultPath, true), ',', CSVWriter.NO_QUOTE_CHARACTER);
    
    String no_of_accounts = System.getProperty("number_of_accounts") != null && !System.getProperty("number_of_accounts").trim().equalsIgnoreCase("") ? System.getProperty("number_of_accounts").trim() : Environment.get("number_of_accounts").trim();
    int number_of_accounts;
    if(appCredentials != null) {
      number_of_accounts = appCredentials.size();
    } else {
      number_of_accounts = Integer.valueOf(no_of_accounts);
    }
    
    String[] events;
    int payment_plan_id = -1;
    if(Environment.get("DSN").trim().equalsIgnoreCase("genesis")) {
      events = new String[]{"ARCH02", "ARCH03", "ARCH04", "ARCH05", "ARCH06", "ARCH07", "ARCH08", "ARCH01"};
      payment_plan_id = 3;
    } else if(Environment.get("DSN").trim().equalsIgnoreCase("unitas")) {
      events = new String[]{"EIOMED02", "EIOMED03", "EIOMED04", "EIOMED05", "EIOMED06", "EIOMED07", "EIOMED08"};
      payment_plan_id = 3;
    } else {
      events = new String[]{"15GAME05", "15GAME06", "15GAME01", "15GAME02", "15GAME03", "15GAME04"};
      payment_plan_id = 26;
    }
    
    String eventsList = System.getProperty("eventsList") != null && !System.getProperty("eventsList").trim().equalsIgnoreCase("") ? System.getProperty("eventsList").trim() : "";
    if(!eventsList.trim().equalsIgnoreCase("")) {
      events = eventsList.trim().split(",");
    }
    
    String[] parking_events;
    if(Environment.get("DSN").trim().equalsIgnoreCase("genesis"))
      parking_events = new String[]{"SDKP01", "SDKP02"};
    else if(Environment.get("DSN").trim().equalsIgnoreCase("unitas"))
      parking_events = new String[]{"EOPARK1", "PARK01", "PARK03", "PARK27"};
    else
      parking_events = new String[]{"15IOPARK"};
    
    String parkingEventsList = System.getProperty("parkingEventsList") != null && !System.getProperty("parkingEventsList").trim().equalsIgnoreCase("") ? System.getProperty("parkingEventsList").trim() : "";
    if(!parkingEventsList.trim().equalsIgnoreCase("")) {
      parking_events = parkingEventsList.trim().split(",");
    }
    
    String[] handling_events;
    if(Environment.get("DSN").trim().equalsIgnoreCase("genesis"))
      handling_events = new String[]{"07HANDLG", "08HANDLG"};
    else if(Environment.get("DSN").trim().equalsIgnoreCase("unitas"))
      handling_events = new String[]{"07HANDLG", "08HANDLG"};
    else
      handling_events = new String[]{"15HAND"};
    
    String handlingEventsList = System.getProperty("handlingEventsList") != null && !System.getProperty("handlingEventsList").trim().equalsIgnoreCase("") ? System.getProperty("handlingEventsList").trim() : "";
    if(!handlingEventsList.trim().equalsIgnoreCase("")) {
      handling_events = handlingEventsList.trim().split(",");
    }
    
    //Update config.json
    updateConfigJSON(Environment.get("ats").trim(), Environment.get("db_server").trim(), Environment.get("DSN").trim(), number_of_accounts, Arrays.asList(events), Arrays.asList(parking_events), Arrays.asList(handling_events), payment_plan_id, Environment.get("TM_UID").trim());
    
    JSONObject config = loadObject("config.json", null);
    System.out.println("Loaded config file");
    
    HashMap<String, String[]> newCreds = new HashMap<String, String[]>();
    Util util = new Util(Environment);

    for (int i = 0; i < config.getInt("number_of_accounts"); i++) {
      String driverType = "";
      if(appCredentials != null) {
        List<String> keys = new ArrayList<>(appCredentials.keySet());
        driverType = keys.get(i);
      } else
        driverType = "New User";
      
      String[] values;
      if(createOnly) {
        System.out.println("Creating account for :: " + driverType);
        values = accountCreation(config);
      }
      else {
        List<String[]> appCreds = new ArrayList<String[]>(appCredentials.values());
        String[] creds = appCreds.get(i);
        if(creds != null) {
          String emailaddress = creds[0].trim();
          String password = creds[1].trim();
          try {
            String accesstoken = getAccessToken(emailaddress, password);
            String accountId = getAccountId(accesstoken);
            values = new String[]{accountId, emailaddress, password};
            System.out.println("Updating account for :: " + driverType);
          } catch(Exception ex) {
            if(util.isProd() && datasheet.trim().equalsIgnoreCase("PROD_SANITY") && !emailaddress.trim().equalsIgnoreCase("accountmanagersupport@ticketmaster.com") && !password.trim().equalsIgnoreCase("x9876")) {
              emailaddress = "accountmanagersupport@ticketmaster.com";
              password = "x9876";
              try {
                String accesstoken = getAccessToken(emailaddress, password);
                String accountId = getAccountId(accesstoken);
                values = new String[]{accountId, emailaddress, password};
                System.out.println("Updating account for :: " + driverType);
              } catch(Exception ex1) {
                System.out.println("Creating account for :: " + driverType);
                values = accountCreation(config);
              }
            } else {
              System.out.println("Creating account for :: " + driverType);
              values = accountCreation(config);
            }
          }
        } else {
          System.out.println("Creating account for :: " + driverType);
          values = accountCreation(config);
        }
      }
      
      String accountId = values[0];
      String emailAddress = values[1];
      String pin = values[2];
      
      if(util.isProd() && datasheet.trim().equalsIgnoreCase("PROD_SANITY")) {
        writeToCSV(resultPath, emailAddress, pin, accountId);
        newCreds.put(driverType, new String[]{emailAddress, pin});
        continue;
      }
      
      if(!buyEvents) {
        writeToCSV(resultPath, emailAddress, pin, accountId);
        newCreds.put(driverType, new String[]{emailAddress, pin});
        continue;
      }
      
      System.out.println("Buying events..Please wait");
      
      Boolean bundling = System.getProperty("bundling") != null && !System.getProperty("bundling").trim().equalsIgnoreCase("") ? Boolean.valueOf(System.getProperty("bundling").trim()) : Boolean.valueOf(Environment.get("bundling").trim());
      // events
      if(config.has("events")) {
        final int events_max_index = config.getJSONArray("events").length();
        
        String[] paid_order_nums = new String[config.getJSONObject("seats").getInt("paid")];
        String[] unpaid_order_nums = new String[config.getJSONObject("seats").getInt("unpaid")];
        int number_of_seats_per_block = config.getInt("number_of_seats_per_block");
        final int parking_max_index = config.getJSONArray("parking_events").length();
        final int handling_max_index = config.getJSONArray("handling_events").length();
        
        int current_index = 0, parking_current_index = 0, handling_current_index = 0;
        for (int j = 0; j < unpaid_order_nums.length; j++) {
          Object[] seatHoldResp = seatHold(config, current_index, events_max_index, config.getJSONArray("events"), 0);
          current_index = Integer.valueOf(String.valueOf(seatHoldResp[0]));
          int orderNum = Integer.valueOf(String.valueOf(seatHoldResp[2]));
          if(orderNum == 0)
            break;
          if(bundling) {
            Object[] _values = addToBundling(config, parking_current_index, parking_max_index, handling_current_index, handling_max_index, String.valueOf(orderNum));
            parking_current_index = Integer.valueOf(String.valueOf(_values[0]));
            handling_current_index = Integer.valueOf(String.valueOf(_values[1]));
          }
          JSONObject response = (JSONObject) seatHoldResp[3];
          Object[] checkout_values = checkout(config, Integer.valueOf(accountId), orderNum);
          String[] order_line_items = String.valueOf(checkout_values[0]).trim().split(",");
          int plan_id = Integer.valueOf(String.valueOf(checkout_values[1]));
          paymentRequest(config, Integer.valueOf(accountId), orderNum, response, "0.00", true, "I", order_line_items, plan_id);
        }
        
        for (int k = 0; k < paid_order_nums.length; k++) {
          Object[] seatHoldResp = seatHold(config, current_index, events_max_index, config.getJSONArray("events"), 0);
          current_index = Integer.valueOf(String.valueOf(seatHoldResp[0]));
          int orderNum = Integer.valueOf(String.valueOf(seatHoldResp[2]));
          if(orderNum == 0)
            break;
          float seatAmount = Float.valueOf(395 * number_of_seats_per_block);
          if(bundling) {
            Object[] _values = addToBundling(config, parking_current_index, parking_max_index, handling_current_index, handling_max_index, String.valueOf(orderNum));
            parking_current_index = Integer.valueOf(String.valueOf(_values[0]));
            handling_current_index = Integer.valueOf(String.valueOf(_values[1]));
            float parkingAmount = Float.valueOf((float) (35 * number_of_seats_per_block));
            float handlingAmount = Float.valueOf((float) (35 * number_of_seats_per_block));
            seatAmount += parkingAmount + handlingAmount;
          }
          JSONObject response = (JSONObject) seatHoldResp[3];
          Object[] checkout_values = checkout(config, Integer.valueOf(accountId), orderNum);
          String[] order_line_items = String.valueOf(checkout_values[0]).trim().split(",");
          int plan_id = Integer.valueOf(String.valueOf(checkout_values[1]));
          String resp = paymentRequest(config, Integer.valueOf(accountId), orderNum, response, String.valueOf(seatAmount), false, "O", order_line_items, plan_id);
          if(resp != null && !resp.trim().equalsIgnoreCase("")) {
            JSONObject jsonResp = new JSONObject(resp);
            String error = jsonResp.getJSONObject("command1").has("error_detail") ? jsonResp.getJSONObject("command1").getString("error_detail") : "";
            if(error.trim().startsWith("Calculated amount to be approved")) {
              error = error.trim();
              error = error.replace("Calculated amount to be approved: ", "");
              String amount = error.split(" ")[0];
              paymentRequest(config, Integer.valueOf(accountId), orderNum, response, amount, false, "O", order_line_items, plan_id);
            }
          }
        }
      }
      if(config.has("parking_events")) {
        final int parking_max_index = config.getJSONArray("parking_events").length();
        
        String[] paid_order_nums = new String[config.getJSONObject("parking").getInt("paid")];
        String[] unpaid_order_nums = new String[config.getJSONObject("parking").getInt("unpaid")];
        int number_of_seats_per_block = config.getInt("number_of_seats_per_block");
        
        int current_index = 0;
        for (int m = 0; m < unpaid_order_nums.length; m++) {
          Object[] seatHoldResp = seatHold(config, current_index, parking_max_index, config.getJSONArray("parking_events"), 0);
          current_index = Integer.valueOf(String.valueOf(seatHoldResp[0]));
          int orderNum = Integer.valueOf(String.valueOf(seatHoldResp[2]));
          if(orderNum == 0)
            break;
          JSONObject response = (JSONObject) seatHoldResp[3];
          Object[] checkout_values = checkout(config, Integer.valueOf(accountId), orderNum);
          String[] order_line_items = String.valueOf(checkout_values[0]).trim().split(",");
          int plan_id = Integer.valueOf(String.valueOf(checkout_values[1]));
          paymentRequest(config, Integer.valueOf(accountId), orderNum, response, "0.00", true, "I", order_line_items, plan_id); 
        }
        for (int n = 0; n < paid_order_nums.length; n++) {
          Object[] seatHoldResp = seatHold(config, current_index, parking_max_index, config.getJSONArray("parking_events"), 0);
          current_index = Integer.valueOf(String.valueOf(seatHoldResp[0]));
          int orderNum = Integer.valueOf(String.valueOf(seatHoldResp[2]));
          if(orderNum == 0)
            break;
          JSONObject response = (JSONObject) seatHoldResp[3];
          Object[] checkout_values = checkout(config, Integer.valueOf(accountId), orderNum);
          String[] order_line_items = String.valueOf(checkout_values[0]).trim().split(",");
          int plan_id = Integer.valueOf(String.valueOf(checkout_values[1]));
          String resp = paymentRequest(config, Integer.valueOf(accountId), orderNum, response, String.valueOf(Float.valueOf((float) (35 * number_of_seats_per_block))), false, "O", order_line_items, plan_id);
          if(resp != null && !resp.trim().equalsIgnoreCase("")) {
            JSONObject jsonResp = new JSONObject(resp);
            String error = jsonResp.getJSONObject("command1").has("error_detail") ? jsonResp.getJSONObject("command1").getString("error_detail") : "";
            if(error.trim().startsWith("Calculated amount to be approved")) {
              error = error.trim();
              error = error.replace("Calculated amount to be approved: ", "");
              String amount = error.split(" ")[0];
              paymentRequest(config, Integer.valueOf(accountId), orderNum, response, amount, false, "O", order_line_items, plan_id);
            }
          }
        }
      }
      if(config.has("handling_events")) {
        final int handling_max_index = config.getJSONArray("handling_events").length();
        
        String[] paid_order_nums = new String[config.getJSONObject("handling").getInt("paid")];
        String[] unpaid_order_nums = new String[config.getJSONObject("handling").getInt("unpaid")];
        int number_of_seats_per_block = config.getInt("number_of_seats_per_block");
        
        int current_index = 0;
        for (int m = 0; m < unpaid_order_nums.length; m++) {
          Object[] seatHoldResp = seatHold(config, current_index, handling_max_index, config.getJSONArray("handling_events"), 0);
          current_index = Integer.valueOf(String.valueOf(seatHoldResp[0]));
          int orderNum = Integer.valueOf(String.valueOf(seatHoldResp[2]));
          if(orderNum == 0)
            break;
          JSONObject response = (JSONObject) seatHoldResp[3];
          Object[] checkout_values = checkout(config, Integer.valueOf(accountId), orderNum);
          String[] order_line_items = String.valueOf(checkout_values[0]).trim().split(",");
          int plan_id = Integer.valueOf(String.valueOf(checkout_values[1]));
          paymentRequest(config, Integer.valueOf(accountId), orderNum, response, "0.00", true, "I", order_line_items, plan_id); 
        }
        for (int n = 0; n < paid_order_nums.length; n++) {
          Object[] seatHoldResp = seatHold(config, current_index, handling_max_index, config.getJSONArray("handling_events"), 0);
          current_index = Integer.valueOf(String.valueOf(seatHoldResp[0]));
          int orderNum = Integer.valueOf(String.valueOf(seatHoldResp[2]));
          if(orderNum == 0)
            break;
          JSONObject response = (JSONObject) seatHoldResp[3];
          Object[] checkout_values = checkout(config, Integer.valueOf(accountId), orderNum);
          String[] order_line_items = String.valueOf(checkout_values[0]).trim().split(",");
          int plan_id = Integer.valueOf(String.valueOf(checkout_values[1]));
          String resp = paymentRequest(config, Integer.valueOf(accountId), orderNum, response, String.valueOf(Float.valueOf((float) (35 * number_of_seats_per_block))), false, "O", order_line_items, plan_id);
          if(resp != null && !resp.trim().equalsIgnoreCase("")) {
            JSONObject jsonResp = new JSONObject(resp);
            String error = jsonResp.getJSONObject("command1").has("error_detail") ? jsonResp.getJSONObject("command1").getString("error_detail") : "";
            if(error.trim().startsWith("Calculated amount to be approved")) {
              error = error.trim();
              error = error.replace("Calculated amount to be approved: ", "");
              String amount = error.split(" ")[0];
              paymentRequest(config, Integer.valueOf(accountId), orderNum, response, amount, false, "O", order_line_items, plan_id);
            }
          }
        }
      }
      writeToCSV(resultPath, emailAddress, pin, accountId);
      newCreds.put(driverType, new String[]{emailAddress, pin});
    }
    
    return newCreds;
  }
  
  public String[] accountCreation(JSONObject config) throws JSONException, Exception {
    ATSConnector ats = new ATSConnector(config.getString("ats"));
    Map<String, String> customer_add_params = new HashMap<String, String>();
    String emailAddress = randomEmail();
    customer_add_params.put("$EMAIL", emailAddress);
    customer_add_params.put("$NAME_FIRST", config.getJSONObject("acct_info").getString("name_first"));
    customer_add_params.put("$NAME_LAST", config.getJSONObject("acct_info").getString("name_last"));
    customer_add_params.put("$DSN", config.getString("dsn"));
    customer_add_params.put("$SITE_NAME", config.getString("site_name"));
    customer_add_params.put("$PIN", config.getJSONObject("acct_info").getString("pin"));
    customer_add_params.put("$ARCHTICS_VERSION", config.getString("archtics_version"));
    customer_add_params.put("$UID", config.getString("uid"));
    JSONObject customer_add = loadObject("customer_add.json", customer_add_params);
    String resp;
    boolean useATSGenericUrl = System.getProperty("useATSGenericUrl") != null && !System.getProperty("useATSGenericUrl").trim().equalsIgnoreCase("") ? Boolean.valueOf(System.getProperty("useATSGenericUrl").trim()) : Boolean.valueOf(Environment.get("useATSGenericUrl").trim());
    if(useATSGenericUrl)
      resp = ats.waitForTMInvoiceResponse(config.getString("dsn"), customer_add);
    else
      resp = ats.postRequest(customer_add);
    JSONObject response = new JSONObject(resp);
    
    System.out.println("Account created ::");
    System.out.println(response.toString(1));
    System.out.println("****************************************************************************************************************************");
    
    String cookies = loginThruDrupalApi(emailAddress, config.getJSONObject("acct_info").getString("pin"));
    String versionNumber = getTerms(cookies);
    String token = getCsrfToken(cookies);
    acceptTerms(cookies, token, versionNumber);
    
    return new String[]{response.getJSONObject("command1").get("acct_id").toString(), emailAddress, config.getJSONObject("acct_info").getString("pin")};
  }
  
  public String loginThruDrupalApi(String emailaddress, String password) throws Exception {
    Util util = new Util(Environment);
    String sessionCookie = util.post(Environment.get("APP_URL") + "/user/login?_format=json", "{\"name\":\"" + emailaddress + "\",\"pass\":\"" + password + "\",\"remember_me\":0}", new String[]{"accept", "accept-encoding", "accept-language", "content-type", "user-agent"}, new String[]{"application/json, text/plain, */*", "gzip, deflate, br", "en-GB,en-US;q=0.8,en;q=0.6", "application/json", "Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1"});
    System.out.println(sessionCookie);
    return sessionCookie;
  }
     
  public String getTerms(String cookies) throws Exception {
    Util util = new Util(Environment);
    InputStream is = util.get(Environment.get("APP_URL") + "/api/text/terms?_format=json", new String[]{"accept", "accept-encoding", "accept-language", "user-agent", "cookie"}, new String[]{"application/json, text/plain, */*", "gzip, deflate, br", "en-GB,en-US;q=0.8,en;q=0.6", "Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1", cookies});
    JSONObject jsonObject = util.convertToJSON(new BufferedReader(new InputStreamReader(is, "UTF-8")));
    return jsonObject.has("version") ? jsonObject.getString("version") : "";
  }
     
  public void acceptTerms(String cookies, String token, String versionNumber) throws Exception {
    if(!versionNumber.trim().equalsIgnoreCase("")) {
      Util util = new Util(Environment);
      util.post(Environment.get("APP_URL") + "/api/accept/terms?_format=json", "{\"response\" : true, \"version\" : \"" +versionNumber + "\"}", new String[]{"accept", "accept-encoding", "accept-language", "user-agent", "cookie", "content-type", "x-csrf-token"}, new String[]{"application/json, text/plain, */*", "gzip, deflate, br", "en-GB,en-US;q=0.8,en;q=0.6", "Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1", cookies, "application/json", token});
    }
  }
  
  public String getCsrfToken(String cookies) throws Exception {
    Util util = new Util(Environment);
    InputStream is = util.get(Environment.get("APP_URL") + "/rest/session/token", new String[]{"accept", "accept-encoding", "accept-language", "user-agent", "cookie"}, new String[]{"application/json, text/plain, */*", "gzip, deflate, br", "en-GB,en-US;q=0.8,en;q=0.6", "Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1", cookies});
    Scanner scan = new Scanner(is);
    String token = new String();
    while (scan.hasNext())
      token += scan.nextLine();
    scan.close();
    System.out.println(token);
    return token;
  }
  
  private String randomEmail() {
    String generatedString = UUID.randomUUID().toString();
    generatedString = generatedString.replaceAll("-", "");
    generatedString = generatedString.substring(0, Math.min(generatedString.length(), 10));
    String email = "iomedia" + generatedString + "@example.com";
    System.out.println(email);
    return email;
  }
  
  public JSONObject loadObject(String fileName, Map<String, String> params) throws IOException {
    InputStream is = new FileInputStream(System.getProperty("user.dir") + OSValidator.delimiter + "APIRequest" + OSValidator.delimiter + fileName);

    BufferedReader buf = new BufferedReader(new InputStreamReader(is));
    String line = buf.readLine();
    StringBuilder sb = new StringBuilder();
    while (line != null) {
      sb.append(line).append("\n");
      line = buf.readLine();
    }
    buf.close();
    String fileAsString = sb.toString();
    if (params != null) {
      for (String key : params.keySet()) {
        if (key.charAt(0) == '$') {
          fileAsString = fileAsString.replaceAll(Pattern.quote(key), params.get(key));
        }
      }
    }
    return new JSONObject(fileAsString.toString());
  }
  
  private Object[] seatHold(JSONObject config, int current_index, int events_max_index, JSONArray events, int index) throws JSONException, Exception {
    ATSConnector ats_hold = new ATSConnector(config.getString("ats"));
    Map<String, String> seats_hold_params = new HashMap<String, String>();
    seats_hold_params.put("$DSN", config.getString("dsn"));
    seats_hold_params.put("$SITE_NAME", config.getString("site_name"));
    seats_hold_params.put("$EVENT_NAME", events.getString(current_index));
    if (current_index + 1 > events_max_index - 1) {
      current_index = 0;
    } else {
      current_index++;
    }
    seats_hold_params.put("$NUM_SEATS", String.valueOf(config.getInt("number_of_seats_per_block")));
    seats_hold_params.put("$ARCHTICS_VERSION", config.getString("archtics_version"));
    seats_hold_params.put("$UID", config.getString("uid"));

    JSONObject seats_hold = loadObject("seats_hold.json", seats_hold_params);
    System.out.println("REQ: " + seats_hold.toString());
    String resp;
    boolean useATSGenericUrl = System.getProperty("useATSGenericUrl") != null && !System.getProperty("useATSGenericUrl").trim().equalsIgnoreCase("") ? Boolean.valueOf(System.getProperty("useATSGenericUrl").trim()) : Boolean.valueOf(Environment.get("useATSGenericUrl").trim());
    if(useATSGenericUrl)
      resp = ats_hold.waitForTMInvoiceResponse(config.getString("dsn"), seats_hold);
    else
      resp = ats_hold.postRequest(seats_hold);
    JSONObject response = new JSONObject(resp);
    System.out.println("Seats Hold ::");
    System.out.println(response.toString(1));
    if(response.getJSONObject("header").getInt("result") != 0)
      System.exit(0);
    if(response.getJSONObject("command1").getInt("order_num_out") == 0) {
      if(index == events_max_index - 1)
        return new Object[]{current_index, events_max_index, response.getJSONObject("command1").getInt("order_num_out"), response};
      return seatHold(config, current_index, events_max_index, events, ++index);
    } else {
      return new Object[]{current_index, events_max_index, response.getJSONObject("command1").getInt("order_num_out"), response};
    }
  }
  
  private Object[] addToSeatHold(JSONObject config, int current_index, int events_max_index, JSONArray events, String orderNumber, int index) throws JSONException, Exception {
    ATSConnector ats_hold = new ATSConnector(config.getString("ats"));
    Map<String, String> seats_hold_params = new HashMap<String, String>();
    seats_hold_params.put("$DSN", config.getString("dsn"));
    seats_hold_params.put("$SITE_NAME", config.getString("site_name"));
    seats_hold_params.put("$EVENT_NAME", events.getString(current_index));
    if (current_index + 1 > events_max_index - 1) {
      current_index = 0;
    } else {
      current_index++;
    }
    seats_hold_params.put("$NUM_SEATS", String.valueOf(config.getInt("number_of_seats_per_block")));
    seats_hold_params.put("$ARCHTICS_VERSION", config.getString("archtics_version"));
    seats_hold_params.put("$UID", config.getString("uid"));
    seats_hold_params.put("$ORDER_NUM", orderNumber);

    JSONObject seats_hold = loadObject("add_to_seats_hold.json", seats_hold_params);
    System.out.println("REQ: " + seats_hold.toString());
    String resp;
    boolean useATSGenericUrl = System.getProperty("useATSGenericUrl") != null && !System.getProperty("useATSGenericUrl").trim().equalsIgnoreCase("") ? Boolean.valueOf(System.getProperty("useATSGenericUrl").trim()) : Boolean.valueOf(Environment.get("useATSGenericUrl").trim());
    if(useATSGenericUrl)
      resp = ats_hold.waitForTMInvoiceResponse(config.getString("dsn"), seats_hold);
    else
      resp = ats_hold.postRequest(seats_hold);
    JSONObject response = new JSONObject(resp);
    System.out.println("Seats Hold ::");
    System.out.println(response.toString(1));
    if(response.getJSONObject("header").getInt("result") != 0)
      System.exit(0);
    if(response.getJSONObject("command1").getInt("order_num_out") == 0) {
      if(index == events_max_index - 1)
        return new Object[]{current_index, events_max_index, response.getJSONObject("command1").getInt("order_num_out"), response};
      return addToSeatHold(config, current_index, events_max_index, events, orderNumber, ++index);
    } else {
      return new Object[]{current_index, events_max_index, response.getJSONObject("command1").getInt("order_num_out"), response};
    }
  }
  
  private String paymentRequest(JSONObject config, int account_id, int orderNum, JSONObject response, String amount, boolean payWithPaymentPlan, String auth_mode, String[] order_line_items, int payment_plan_id) throws Exception {
    ATSConnector ats_pay = new ATSConnector(config.getString("ats"));
    Map<String, String> payment_request_params = new HashMap<String, String>();
    payment_request_params.put("$ACCT_ID", String.valueOf(account_id));
    payment_request_params.put("$DSN", config.getString("dsn"));
    payment_request_params.put("$SITE_NAME", config.getString("site_name"));
    payment_request_params.put("$ORDER_NUM", String.valueOf(orderNum));
    // No external service supports $0 payments :(
    payment_request_params.put("$AMOUNT", amount);
    payment_request_params.put("$ARCHTICS_VERSION", config.getString("archtics_version"));
    payment_request_params.put("$UID", config.getString("uid"));
    payment_request_params.put("$AUTH_MODE", auth_mode);
    
    JSONArray cart_items = new JSONArray();
    for(int i = 0; i < order_line_items.length; i++) {
      JSONObject obj = new JSONObject();
      obj.put("dm_code", "TF");
      obj.put("order_line_item", order_line_items[i]);
      cart_items.put(obj);
    }
    
    String json = "";
    if(payWithPaymentPlan) {
      json = "payment_request_plan_id.json";
      payment_request_params.put("$PAYMENT_PLAN_ID", String.valueOf(payment_plan_id));
    } else
      json = "payment_request.json";
    
    JSONObject payment_request = loadObject(json, payment_request_params);
    payment_request = update(payment_request, "cart_items", cart_items);
    System.out.println("REQ: " + payment_request.toString());
    System.out.println("Payment request created ::");
    String resp;
    boolean useATSGenericUrl = System.getProperty("useATSGenericUrl") != null && !System.getProperty("useATSGenericUrl").trim().equalsIgnoreCase("") ? Boolean.valueOf(System.getProperty("useATSGenericUrl").trim()) : Boolean.valueOf(Environment.get("useATSGenericUrl").trim());
    if(useATSGenericUrl)
      resp = ats_pay.getTMInvoiceAPIResponse(config.getString("dsn"), payment_request);
    else
      resp = ats_pay.postRequest(payment_request);
    if(resp != null && !resp.trim().equalsIgnoreCase("")) {
      JSONObject response_pay = new JSONObject(resp);
      System.out.println(response_pay.toString(1));
      if(response.getJSONObject("header").getInt("result") != 0)
        System.exit(0);
      String error = response_pay.getJSONObject("command1").has("error_detail") ? response_pay.getJSONObject("command1").getString("error_detail") : "";
      if(error.trim().startsWith("Calculated amount to be approved")) {
        //Do Nothing
      } else
        ccUpdate(config, account_id);
    }
    return resp;
  }
  
  private String ccUpdate(JSONObject config, int account_id) throws Exception {
    ATSConnector ats_pay = new ATSConnector(config.getString("ats"));
    Map<String, String> ccupdate_request_params = new HashMap<String, String>();
    ccupdate_request_params.put("$ACCT_ID", String.valueOf(account_id));
    ccupdate_request_params.put("$DSN", config.getString("dsn"));
    ccupdate_request_params.put("$SITE_NAME", config.getString("site_name"));
    ccupdate_request_params.put("$ARCHTICS_VERSION", config.getString("archtics_version"));
    ccupdate_request_params.put("$UID", config.getString("uid"));
    String json = "cc_Update.json";
    JSONObject cc_update_request = loadObject(json, ccupdate_request_params);
    System.out.println("REQ: " + cc_update_request.toString());
    System.out.println("CC Update request created ::");
    String resp;
    boolean useATSGenericUrl = System.getProperty("useATSGenericUrl") != null && !System.getProperty("useATSGenericUrl").trim().equalsIgnoreCase("") ? Boolean.valueOf(System.getProperty("useATSGenericUrl").trim()) : Boolean.valueOf(Environment.get("useATSGenericUrl").trim());
    if(useATSGenericUrl)
      resp = ats_pay.getTMInvoiceAPIResponse(config.getString("dsn"), cc_update_request);
    else
      resp = ats_pay.postRequest(cc_update_request);
    if(resp != null && !resp.trim().equalsIgnoreCase("")) {
      JSONObject response_pay = new JSONObject(resp);
      System.out.println(response_pay.toString(1));
    }
    System.out.println("============================================================================================================================");
    return resp;
  }
  
  private Object[] checkout(JSONObject config, int account_id, int orderNum) throws Exception {
    ATSConnector ats_pay = new ATSConnector(config.getString("ats"));
    Map<String, String> payment_request_params = new HashMap<String, String>();
    payment_request_params.put("$ACCT_ID", String.valueOf(account_id));
    payment_request_params.put("$DSN", config.getString("dsn"));
    payment_request_params.put("$SITE_NAME", config.getString("site_name"));
    payment_request_params.put("$ORDER_NUM", String.valueOf(orderNum));
    payment_request_params.put("$ARCHTICS_VERSION", config.getString("archtics_version"));
    payment_request_params.put("$UID", config.getString("uid"));
    
    String json = "checkout.json";
    
    JSONObject checkout = loadObject(json, payment_request_params);
    System.out.println("REQ: " + checkout.toString());
    System.out.println("Checkout created ::");
    String resp;
    boolean useATSGenericUrl = System.getProperty("useATSGenericUrl") != null && !System.getProperty("useATSGenericUrl").trim().equalsIgnoreCase("") ? Boolean.valueOf(System.getProperty("useATSGenericUrl").trim()) : Boolean.valueOf(Environment.get("useATSGenericUrl").trim());
    if(useATSGenericUrl)
      resp = ats_pay.getTMInvoiceAPIResponse(config.getString("dsn"), checkout);
    else
      resp = ats_pay.postRequest(checkout);
    JSONObject response_pay = new JSONObject(resp);
    System.out.println(response_pay.toString(1));
    if(response_pay.getJSONObject("header").getInt("result") != 0)
      System.exit(0);
    
    JSONArray cartItems = response_pay.getJSONObject("command1").getJSONArray("cart_items");
    String orderLineItems = "";
    int i = 0;
    for(; i < cartItems.length() - 1; i++) {
      JSONObject cartItem = cartItems.getJSONObject(i);
      int order_line_item = cartItem.getInt("order_line_item");
      orderLineItems += String.valueOf(order_line_item) + ",";
    }
    JSONObject cartItem = cartItems.getJSONObject(i);
    int order_line_item = cartItem.getInt("order_line_item");
    orderLineItems += String.valueOf(order_line_item);
    
    int payment_plan_id = response_pay.getJSONObject("command1").has("pmt_plans") ? response_pay.getJSONObject("command1").getJSONArray("pmt_plans").getJSONObject(0).getInt("available_payment_plan_id") : Integer.valueOf(config.getString("payment_plan_id"));
    return new Object[]{orderLineItems, payment_plan_id};
  }
  
  private void writeToCSV(String resultPath, String emailaddress, String password, String accountId) throws IOException {
    try{
      csvOutput = new CSVWriter(new FileWriter(resultPath, true), ',', CSVWriter.NO_QUOTE_CHARACTER);
      csvOutput.writeNext(new String[] {emailaddress, password, accountId});
    }
    finally{
      if(csvOutput != null)
        csvOutput.close();
    }
  }
  
  public JSONObject update(org.json.JSONObject obj, String keyMain, Object newValue) throws Exception {
      Iterator<String> iterator = obj.keys();
      String key = null;
      while (iterator.hasNext()) {
          key = (String) iterator.next();
            if ((key.equals(keyMain))) {
                obj.put(key, newValue);
                return obj;
            }
          if (obj.optJSONObject(key) != null) {
            update(obj.getJSONObject(key), keyMain, newValue);
          }

          // if it's jsonarray
          if (obj.optJSONArray(key) != null) {
              JSONArray jArray = obj.getJSONArray(key);
              int flag = 0;
              for (int i = 0; i < jArray.length(); i++) {
                if(jArray.get(i) instanceof JSONObject){
                  flag = 1;
                  update(jArray.getJSONObject(i), keyMain, newValue);
                }
              }
              if(flag == 0){
                if(String.valueOf(newValue).trim().contains("&&")){
                  if ((key.equals(keyMain))) {
                      // put new value
                    List<String> newValues = new ArrayList<String>(Arrays.asList(String.valueOf(newValue).trim().split("&&")));
                    obj.put(key, newValues);
                      return obj;
                  }
                }
              }
          }
      }
      return obj;
  }
  
  public JSONObject parseJsonFile(String filename) throws Exception{
    String jsonText = readAll(new FileReader(System.getProperty("user.dir") + OSValidator.delimiter + "APIRequest" + OSValidator.delimiter + filename));
    JSONObject obj = new JSONObject(jsonText);
        return obj;
  }
  
  public String readAll(Reader rd) throws IOException {
      StringBuilder sb = new StringBuilder();
      int cp;
      while ((cp = rd.read()) != -1) {
        sb.append((char) cp);
      }
      return sb.toString();
  }
  
  public String getAccessToken(String emailaddress, String password) throws IOException, JSONException{
    if(Environment.get("TM_OAUTH_URL").trim().endsWith("/")) {
      Environment.put("TM_OAUTH_URL", Environment.get("TM_OAUTH_URL").trim().substring(0, Environment.get("TM_OAUTH_URL").trim().length() - 1));
    }
    URL url = new URL(Environment.get("TM_OAUTH_URL").trim());
      Map<String,Object> params = new LinkedHashMap<>();
      params.put("client_id", Environment.get("CLIENT_ID").trim());
      params.put("client_secret", Environment.get("CLIENT_SECRET").trim());
      params.put("grant_type", "password");
      params.put("username", emailaddress);
      params.put("password", password);
      
      StringBuilder postData = new StringBuilder();
      for (Map.Entry<String,Object> param : params.entrySet()) {
          if (postData.length() != 0) postData.append('&');
          postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
          postData.append('=');
          postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
      }
      byte[] postDataBytes = postData.toString().getBytes("UTF-8");

      HttpURLConnection conn = (HttpURLConnection)url.openConnection();
      conn.setRequestMethod("POST");
      conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
      conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
      conn.setDoOutput(true);
      conn.getOutputStream().write(postDataBytes);
      
      BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
      String output = "";
        Boolean keepGoing = true;
        while (keepGoing) {
            String currentLine = br.readLine();
            if (currentLine == null) {
                keepGoing = false;
            } else {
                output += currentLine;
            }
        }

       // System.out.println("Response-->" + output);
      
      JSONObject jsonObject=new JSONObject(output);
      String access_token=(String) jsonObject.get("access_token");
      return access_token;
  }
  
  public String getAccountId(String access_token) throws IOException, JSONException{
      String Access_tokens=access_token;
      if(Environment.get("TM_OAUTH_URL").trim().endsWith("/")) {
      Environment.put("TM_OAUTH_URL", Environment.get("TM_OAUTH_URL").trim().substring(0, Environment.get("TM_OAUTH_URL").trim().length() - 1));
    }
    URL url = new URL(Environment.get("TM_OAUTH_URL").trim() + "/" + Access_tokens);

      HttpURLConnection conn = (HttpURLConnection)url.openConnection();
      conn.setRequestMethod("GET");
      conn.setRequestProperty("Content-Type", "application/json");
      
      if (conn.getResponseCode() != 200) {
        throw new RuntimeException("Failed : HTTP error code : "+ conn.getResponseCode());
    }

      Scanner scan = new Scanner(url.openStream());
    String response = new String();
    while (scan.hasNext())
    response += scan.nextLine();
    scan.close();
      
    JSONObject jsonObject=new JSONObject(response);
    String umember_token=(String) jsonObject.get("umember_token");
    String[] account_id=umember_token.split("\\.");
    return account_id[1];
  }
  
  private Object[] addToBundling(JSONObject config, int parking_current_index, int parking_max_index, int handling_current_index, int handling_max_index, String orderNumber) throws JSONException, Exception {
    if(parking_current_index < parking_max_index) {
      Object[] seatHoldResp = addToSeatHold(config, parking_current_index, parking_max_index, config.getJSONArray("parking_events"), orderNumber, 0);
      parking_current_index = Integer.valueOf(String.valueOf(seatHoldResp[0]));
    }
    if(handling_current_index < handling_max_index) {
      Object[] seatHoldResp = addToSeatHold(config, handling_current_index, handling_max_index, config.getJSONArray("handling_events"), orderNumber, 0);
      handling_current_index = Integer.valueOf(String.valueOf(seatHoldResp[0]));
    }
    return new Object[]{parking_current_index, handling_current_index, orderNumber};
  }
}