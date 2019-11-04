package org.iomedia.galen.framework;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.iomedia.framework.DBActivities;
import org.iomedia.framework.Driver;
import org.iomedia.framework.Infra;
import org.iomedia.framework.OSValidator;
import org.iomedia.framework.WebDriverFactory;
import org.iomedia.framework.Driver.HashMapNew;
import org.json.JSONException;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import org.testng.TestNG;
import org.testng.annotations.Test;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlInclude;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Execute extends Driver {
  
  String[] getAppCredentials(String env, String version, String driverType, String datasheet) {
    HashMapNew temp = GetXMLNodeValue(Environment.get("appCredentialsPath").trim(), "//" + env.trim().toUpperCase() + "/" + version.trim().toUpperCase() + "/" + driverType.trim().toUpperCase() , 0);
    String emailaddress = null, password = null;
    Util util= new Util(Environment);
    if(util.isProd() && datasheet.trim().equalsIgnoreCase("PROD_SANITY")) {
      emailaddress = Environment.get("EMAIL_ADDRESS").trim().equalsIgnoreCase("") ? null : Environment.get("EMAIL_ADDRESS").trim();
      password = Environment.get("PASSWORD").trim().equalsIgnoreCase("") ? null : Environment.get("PASSWORD").trim();
    }
    if(temp != null && emailaddress == null && password == null) {
      String appCredentialsEmailAddressKey = System.getProperty("appCredentialsEmailAddressKey") != null && !System.getProperty("appCredentialsEmailAddressKey").trim().equalsIgnoreCase("") ? System.getProperty("appCredentialsEmailAddressKey").trim() : Environment.get("appCredentialsEmailAddressKey").trim();
      String appCredentialsPasswordKey = System.getProperty("appCredentialsPasswordKey") != null && !System.getProperty("appCredentialsPasswordKey").trim().equalsIgnoreCase("") ? System.getProperty("appCredentialsPasswordKey").trim() : Environment.get("appCredentialsPasswordKey").trim();
      if(appCredentialsEmailAddressKey.contains("@")) {
        emailaddress = appCredentialsEmailAddressKey;
        password = appCredentialsPasswordKey;
      } else {
        emailaddress = temp.get(appCredentialsEmailAddressKey).trim();
        password = temp.get(appCredentialsPasswordKey).trim();
      }
    }
    if(util.isProd() && datasheet.trim().equalsIgnoreCase("PROD_SANITY") && emailaddress == null && password == null) {
      emailaddress = "accountmanagersupport@ticketmaster.com";
      password = "x9876";
    }
    if(emailaddress == null || password == null)
      return null;
    else
      return new String[]{emailaddress, password};
  }
  
  String[][] getDeviceConfiguration() {
    String deviceConf = System.getProperty("deviceConf") != null && !System.getProperty("deviceConf").trim().equalsIgnoreCase("") ? System.getProperty("deviceConf").trim() : "";
    boolean runLocally = System.getProperty("runLocally") != null && !System.getProperty("runLocally").trim().equalsIgnoreCase("") ? Boolean.valueOf(System.getProperty("runLocally").trim().toLowerCase()) : Boolean.valueOf(Environment.get("runLocally").trim().toLowerCase());
    HashMapNew temp;
    
    if(deviceConf.trim().equalsIgnoreCase(""))
      temp = GetXMLNodeValue(OSValidator.delimiter + "src" + OSValidator.delimiter + "Configuration.xml", "//selenium", 0);
    else
      temp = GetXMLNodeValueFromString(deviceConf, "//selenium", 0);
    
    List<List<String>> conf = new ArrayList<List<String>>();
    Set<String> keys = temp.keySet();
    Iterator<String> iter = keys.iterator();
    while(iter.hasNext()){
      String key = iter.next();
      if(!key.trim().equalsIgnoreCase("common") && !key.trim().equalsIgnoreCase("#text")) {
        HashMapNew data;
        if(deviceConf.trim().equalsIgnoreCase(""))
          data = GetXMLNodeValue(OSValidator.delimiter + "src" + OSValidator.delimiter + "Configuration.xml", "//" + key, 0);
        else
          data = GetXMLNodeValueFromString(deviceConf, "//" + key, 0);
        
        List<String> device = new ArrayList<String>();
        if(key.trim().toLowerCase().contains("android") || key.trim().toLowerCase().contains("ios")){
          String platformVersion = data.get("platformVersion").trim();
          String deviceName = data.get("deviceName").trim();
          String deviceOrientation = data.get("deviceOrientation").trim();
          String deviceType = data.get("deviceType").trim();
          if(runLocally){
            device.add(toCamelCase(key.substring(0, key.length() - 1)));
            device.add(key.trim().toUpperCase());
          } else {
            device.add(toCamelCase(key.substring(0, key.length() - 1)) + "-" + deviceName + "-" + platformVersion + "-" + toCamelCase(deviceType) + "-" + toCamelCase(deviceOrientation));
            device.add(key.trim().toUpperCase());
          }
        } else {
          String browserVersion = data.get("browserVersion").trim();
          String platformName = data.get("platformName").trim();
          if(runLocally){
            device.add(toCamelCase(key.substring(0, key.length() - 1)));
            device.add(key.trim().toUpperCase());
          } else {
            device.add(toCamelCase(key.substring(0, key.length() - 1)) + "-" + platformName + "-" + browserVersion);
            device.add(key.trim().toUpperCase());
          }
        }
        
        conf.add(device);
      }
    }
    
    String[][] array = new String[conf.size()][];

    int i = 0;
    for (List<String> nestedList : conf) {
        array[i++] = nestedList.toArray(new String[nestedList.size()]);
    }
    
    return array;
  }
  
  public static void main(String[] args) throws JSONException, Exception{
    Execute exec = new Execute();
    
    String relatedEnv = null;
    String envConfig = System.getProperty("envConfig") != null && !System.getProperty("envConfig").trim().equalsIgnoreCase("") ? System.getProperty("envConfig").trim() : "";
    if(!envConfig.trim().equalsIgnoreCase("")) {
      relatedEnv = new Util(exec.Environment).getRelatedEnv();
      if(relatedEnv != null && !relatedEnv.trim().equalsIgnoreCase("")) {
        exec.Environment.put("env", relatedEnv.trim().toUpperCase());
      }
    }
    
    String User = System.getProperty("BUILD_USER_ID") != null && !System.getProperty("BUILD_USER_ID").trim().equalsIgnoreCase("") ? System.getProperty("BUILD_USER_ID").trim() : System.getProperty("user.name");
    String RootPath = System.getProperty("user.dir");
    String Datasheet = System.getProperty("calendar") != null && !System.getProperty("calendar").trim().equalsIgnoreCase("") ? System.getProperty("calendar").trim() : exec.Environment.get("calendar");
      String ExecutionFolderPath = RootPath + OSValidator.delimiter + "Execution";
      
      String CurrentExecutionFolder = ExecutionFolderPath + OSValidator.delimiter + Datasheet + OSValidator.delimiter + exec.Environment.get("testSet") + OSValidator.delimiter + User;
      String CurrentExecutionDatasheet = CurrentExecutionFolder + OSValidator.delimiter + Datasheet + ".xls";
      String DatasheetsPath = RootPath + exec.Environment.get("dataSheets").trim();
      
      exec.Environment.put("CURRENTEXECUTIONFOLDER", CurrentExecutionFolder);
      exec.Environment.put("CURRENTEXECUTIONDATASHEET", CurrentExecutionDatasheet);
      
      new File(CurrentExecutionFolder).mkdirs();
      
      Boolean replaceCalendarFile = System.getProperty("replaceCalendarFile") != null && !System.getProperty("replaceCalendarFile").trim().equalsIgnoreCase("") ? Boolean.valueOf(System.getProperty("replaceCalendarFile").trim()) : Boolean.valueOf(exec.Environment.get("replaceCalendarFile").trim());
      boolean runLocally = System.getProperty("runLocally") != null && !System.getProperty("runLocally").trim().equalsIgnoreCase("") ? Boolean.valueOf(System.getProperty("runLocally").trim().toLowerCase()) : Boolean.valueOf(exec.Environment.get("runLocally").trim().toLowerCase());
      String groups = System.getProperty("groups") != null && !System.getProperty("groups").trim().equalsIgnoreCase("") ? System.getProperty("groups").trim() : exec.Environment.get("groups").trim();
      String createAccount = System.getProperty("createAccount") != null && !System.getProperty("createAccount").trim().equalsIgnoreCase("") ? System.getProperty("createAccount").trim() : exec.Environment.get("createAccount").trim();
      
      if (!new File(CurrentExecutionDatasheet).exists() || (replaceCalendarFile)) {
          exec.gblFunctions.fCopyXLS(DatasheetsPath + Datasheet + ".xls", CurrentExecutionDatasheet);
      }
      
      String number_of_accounts = System.getProperty("number_of_accounts") != null && !System.getProperty("number_of_accounts").trim().equalsIgnoreCase("") ? System.getProperty("number_of_accounts").trim() : exec.Environment.get("number_of_accounts").trim();
      if(!number_of_accounts.trim().equalsIgnoreCase("")) {
        CreateAccounts create = new CreateAccounts(exec.Environment);
        create.create(null, createAccount.trim().equalsIgnoreCase("createonly"), Datasheet, true);
        return;
      }
      
      String parallel = System.getProperty("parallel") != null && !System.getProperty("parallel").trim().equalsIgnoreCase("") ? System.getProperty("parallel").trim() : "";
      
      XmlSuite suite = new XmlSuite();
      suite.setName("Suite");
      Util util= new Util(exec.Environment);
      if(!parallel.trim().equalsIgnoreCase("")) {
        suite.setParallel(parallel);
      } else {
        if(runLocally || (util.isProd() && Datasheet.trim().equalsIgnoreCase("PROD_SANITY")))
          suite.setParallel("classes");
        else
          suite.setParallel("tests");
      }
      
      //Create configuration array
      String[][] configuration = exec.getDeviceConfiguration();
      HashMap<String, String[]> appCredentials = new HashMap<String, String[]>();
      
      List<String> testNames = new ArrayList<String>();
      int counter = 0;
      for(int i = 0; i < configuration.length; i++) {
        String testName = configuration[i][0];
        String driverType = configuration[i][1];
        WebDriverFactory driverFactory = new WebDriverFactory();
        driverFactory.setDriverType(new ThreadLocal<String>(){@Override public String initialValue() {
        return driverType;
      };});
        clearX(exec, driverFactory);
        exec.RecordSetMap = retreiveDataFromExcel(exec, driverFactory, CurrentExecutionDatasheet);
        if(exec.RecordSetMap == null)
          continue;
        
        if((!runLocally && !(util.isProd() && Datasheet.trim().equalsIgnoreCase("PROD_SANITY"))) || parallel.trim().equalsIgnoreCase("tests"))
          counter++;
        
        XmlTest test = new XmlTest(suite);
        test.setName(testName);
        testNames.add(testName);
        
        if(!createAccount.trim().equalsIgnoreCase("")) {
          String[] credentials = exec.getAppCredentials(exec.Environment.get("env"), exec.Environment.get("version"), driverType, Datasheet);
          appCredentials.put(driverType.trim().toUpperCase(), credentials);
        }
        
        if((!runLocally && !(util.isProd() && Datasheet.trim().equalsIgnoreCase("PROD_SANITY"))) || parallel.trim().equalsIgnoreCase("tests"))
          test.setGroupByInstances(true);
        Map<String, String> parameters = new HashMap<String, String>(); 
        parameters.put("browser", driverType);
        test.setParameters(parameters);
        
        HashMapNew methodsList = new HashMapNew();
        
        int iRSCount = 0;
        do {
              RecordSet res1 = (RecordSet)exec.RecordSetMap.get(Integer.valueOf(iRSCount + 1));
              String sActionName = res1.get_sActionName();
              String className = getClassName(exec, sActionName, groups);
              if(className != null && !className.trim().equalsIgnoreCase("")){
                methodsList.put(className, methodsList.get(className) + sActionName + ",");
              }
          iRSCount++;
        } while(iRSCount < exec.RecordSetMap.size());
        
        Set<String> keys = methodsList.keySet();
        Iterator<String> iterator = keys.iterator();
        
        List<XmlClass> allclasses = new ArrayList<XmlClass>(); 
        
        while(iterator.hasNext()){
          String key = iterator.next();
          XmlClass testClass = new XmlClass();
          testClass.setName(key);
          int methodscount = getMethodsCountInClass(exec, key);
          String[] mtdList = methodsList.get(key).split(",");
          if(methodscount == mtdList.length){
            //Do Nothing
          }
          else {
            List<XmlInclude> methodsToRun = constructIncludes(mtdList);
            testClass.setIncludedMethods(methodsToRun);
          }
          allclasses.add(testClass);
        }
        if((runLocally || (util.isProd() && Datasheet.trim().equalsIgnoreCase("PROD_SANITY"))) || parallel.trim().equalsIgnoreCase("classes"))
          counter = counter < allclasses.size() ? allclasses.size() : counter;
        test.setXmlClasses(Arrays.asList(allclasses.toArray(new XmlClass[allclasses.size()])));
      }
      
      if(appCredentials.size() > 0) {
        CreateAccounts create = new CreateAccounts(exec.Environment);
        appCredentials = create.create(appCredentials, createAccount.trim().equalsIgnoreCase("createonly"), Datasheet, true);
        boolean saveToAppCredentials = System.getProperty("saveToAppCredentials") != null && !System.getProperty("saveToAppCredentials").trim().equalsIgnoreCase("") ? Boolean.valueOf(System.getProperty("saveToAppCredentials").trim()) : Boolean.valueOf(exec.Environment.get("saveToAppCredentials").trim());
        if(saveToAppCredentials)
          saveAppCredentialsXmlFile(exec.Environment.get("appCredentialsPath").trim(), appCredentials, exec.Environment.get("env"), exec.Environment.get("version"));
      }
      
      //Set unlimited sessions for AMGR
      boolean setSession = System.getProperty("setSession") != null && !System.getProperty("setSession").trim().equalsIgnoreCase("") ? Boolean.valueOf(System.getProperty("setSession").trim()) : Boolean.valueOf(exec.Environment.get("setSession").trim());
      if(setSession) {
        String sessions = System.getProperty("sessions") != null && !System.getProperty("sessions").trim().equalsIgnoreCase("") ? System.getProperty("sessions").trim() : "0";
        WebDriverFactory driverFactory = new WebDriverFactory();
        driverFactory.setDriverType(new ThreadLocal<String>(){@Override public String initialValue() {
        return "CHROME1";
      };});
        Sessions ses= new Sessions(driverFactory, exec.Environment);
        ses.setSessionLimit(sessions);
      }
      
//      if(System.getProperty("BUILD_USER_ID") == null && runLocally && testNames.toString().toUpperCase().contains("IOS")) {
//        suite.setParallel("tests");
//        counter = testNames.size();
//      }
      
      if(runLocally || (util.isProd() && Datasheet.trim().equalsIgnoreCase("PROD_SANITY"))) {
        if(testNames.size() == 1 && (testNames.get(0).trim().toLowerCase().contains("ios") || testNames.get(0).trim().toLowerCase().contains("android"))) {
          if(!exec.Environment.get("sauceLabsConcurrentSession").trim().equalsIgnoreCase("")) {
            int sauceLabConcurrentSession = Integer.valueOf(exec.Environment.get("sauceLabsConcurrentSession").trim());
            if(counter < sauceLabConcurrentSession) {
              suite.setThreadCount(counter);
            } else {
              suite.setThreadCount(sauceLabConcurrentSession);
            }
          } else {
            suite.setThreadCount(counter);
          }
        } else {
          suite.setThreadCount(counter);
        }
      } else {
        if(!exec.Environment.get("sauceLabsConcurrentSession").trim().equalsIgnoreCase("")) {
          int sauceLabConcurrentSession = Integer.valueOf(exec.Environment.get("sauceLabsConcurrentSession").trim());
          if(counter < sauceLabConcurrentSession){
            suite.setThreadCount(counter);
          } else {
            suite.setThreadCount(sauceLabConcurrentSession);
          }
        } else {
          suite.setThreadCount(counter);
        }
      }
      
//      suite.addListener("org.iomedia.framework.MyInterceptor");
        System.out.println(suite.toXml());
      
      List<XmlSuite> suites = new ArrayList<XmlSuite>();
      suites.add(suite);
      
      boolean writeToFileOnly = System.getProperty("writeToFile") != null && !System.getProperty("writeToFile").trim().equalsIgnoreCase("") ? Boolean.valueOf(System.getProperty("writeToFile").trim()) : false;

      FileOutputStream fout = new FileOutputStream(RootPath + OSValidator.delimiter + "testng" + OSValidator.delimiter + "testng-customsuite.xml", false);
      new PrintStream(fout).println(suite.toXml());
      fout.close();
      
      if(!writeToFileOnly){
        TestNG tng = new TestNG();
        tng.setXmlSuites(suites);
        tng.run();
      }
  }
  
  public static List<XmlInclude> constructIncludes (String... methodNames) {
        List<XmlInclude> includes = new ArrayList<XmlInclude> ();
        for (String eachMethod : methodNames) {
            includes.add (new XmlInclude (eachMethod));
        }
        return includes;
    }
  
  public static void clearX(Execute exec, WebDriverFactory driverFactory){
    Infra objInfra = new Infra(driverFactory, null, exec.Environment, null);
    String clearX = System.getProperty("clearX") != null && !System.getProperty("clearX").trim().equalsIgnoreCase("") ? System.getProperty("clearX").trim() : exec.Environment.get("clearX").trim();
      if (!clearX.equals("")) {
        objInfra.fClearSkip(clearX);
      }
  }
  
  public static HashMap<Integer, RecordSet> retreiveDataFromExcel(Execute exec, WebDriverFactory driverFactory, String CurrentExecutionDatasheet){
    DBActivities objDB = new DBActivities(driverFactory, null, exec.Environment);
      ArrayList<String> columnNames = objDB.fGetColumnName(CurrentExecutionDatasheet, "MAIN");
    
      int skipColumnNo = columnNames.indexOf("SKIP_" + driverFactory.getDriverType().get().toUpperCase().replace(" ", ""));
      int headerColumnNo = columnNames.indexOf("HEADER");
      int testNameColumnNo = columnNames.indexOf("TEST_NAME");
      int actionNameColumnNo = columnNames.indexOf("ACTION");
      
      if(skipColumnNo == -1)
        return null;
    
      List<List<String>> calendarFileData = null;
      calendarFileData = objDB.fRetrieveDataExcel(CurrentExecutionDatasheet, "MAIN", new int[]{skipColumnNo, headerColumnNo}, new String[]{"", ""});
      
      if(calendarFileData == null) {
        return null;
      }
   
      if(calendarFileData.size() == 0) {
        return null;
      }
    
      exec.RecordSetMap = new HashMap<Integer, RecordSet>();
      int iRSCount = 0;
      do {
        exec.RecordSetMap.put(Integer.valueOf(iRSCount + 1), exec.new RecordSet(calendarFileData.get(iRSCount).get(actionNameColumnNo), Integer.valueOf(calendarFileData.get(iRSCount).get(0)) - 1, calendarFileData.get(iRSCount).get(testNameColumnNo), Integer.valueOf(calendarFileData.get(iRSCount).get(0)) + 1));
        iRSCount++;
      } while (iRSCount < calendarFileData.size());
      
      return exec.RecordSetMap;
  }
  
  public static String getClassName(Execute exec, String methodName, String groups) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        String className = "";
        
        Reflections reflections = new Reflections(new ConfigurationBuilder() .setUrls(ClasspathHelper.forPackage(exec.Environment.get("agilePackage").trim())).setScanners(new MethodAnnotationsScanner()));
        Set<Method> resources = reflections.getMethodsAnnotatedWith(org.testng.annotations.Test.class);
        Iterator<Method> iter = resources.iterator();
        
        while(iter.hasNext()){
          Method method = iter.next();
          if(method.getName().equalsIgnoreCase(methodName)){
            className = method.getDeclaringClass().getName();
            if(!groups.trim().equalsIgnoreCase("")) {
              Test _test = (Test) method.getDeclaredAnnotations()[0];
              String[] _groups = _test.groups();
              String[] actualGroups = groups.trim().split(",");
              for(int i = 0; i < _groups.length; i++) {
                for(int j = 0; j < actualGroups.length; j++) {
                  if(_groups[i].trim().equalsIgnoreCase(actualGroups[j])) {
                    return className;
                  }
                }
              }
            } else
              return className;
          }
        }
        return null;
  }
  
  public static int getMethodsCountInClass(Execute exec, String className) {
    Reflections reflections = new Reflections(new ConfigurationBuilder() .setUrls(ClasspathHelper.forPackage(exec.Environment.get("agilePackage").trim())).setScanners(new MethodAnnotationsScanner()).filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix(className))));
        Set<Method> resources = reflections.getMethodsAnnotatedWith(org.testng.annotations.Test.class);
    return resources.size();
  }
  
  static String toCamelCase(String s){
     String[] parts = s.split(" ");
     String camelCaseString = "";
     for (String part : parts){
        camelCaseString = camelCaseString + toProperCase(part) + " ";
     }
     return camelCaseString.trim();
  }
      
  static String toProperCase(String s) {
      return s.substring(0, 1).toUpperCase() +
                 s.substring(1).toLowerCase();
  }
  
  private static void saveAppCredentialsXmlFile(String path, HashMap<String, String[]> appCredentials, String env, String version){
      String RootPath = System.getProperty("user.dir");
      try {
        String xmlPath = RootPath + path;
        File fXmlFile = new File(xmlPath);
        
        if(!fXmlFile.exists())
          return;
        
        DocumentBuilderFactory dbFac = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = dbFac.newDocumentBuilder();
        Document xmldoc = docBuilder.parse(fXmlFile);
        
        Node ENV = xmldoc.getElementsByTagName("ENV").item(0);
        
        XPathFactory xPathfac = XPathFactory.newInstance();
        XPath xpath = xPathfac.newXPath();

        //"//" + env.trim().toUpperCase() + "/" + version.trim().toUpperCase() + "/" + driverType.trim().toUpperCase()
        XPathExpression expr = xpath.compile("//" + env.trim().toUpperCase());
        Object obj = expr.evaluate(xmldoc, XPathConstants.NODESET);
        if(obj != null) {
          Node node = ((NodeList)obj).item(0);
          if(node == null) {
            Element envNode = xmldoc.createElement(env.trim().toUpperCase());
            envNode.appendChild(xmldoc.createElement(version.trim().toUpperCase()));
            ENV.appendChild(envNode);
          }
        }
        
        List<String> keys = new ArrayList<>(appCredentials.keySet());
        for(int i = 0; i < keys.size(); i++) {
          String driverType = keys.get(i);
          String[] creds = appCredentials.get(driverType);
          String emailAddress = creds[0];
          String pass = creds[1];
          expr = xpath.compile("//" + env.trim().toUpperCase() + "/" + version.trim().toUpperCase() + "/" + driverType.trim().toUpperCase());
          obj = expr.evaluate(xmldoc, XPathConstants.NODESET);
          if(obj != null) {
            Node node = ((NodeList)obj).item(0);
            if(node != null) {
              NodeList nl = node.getChildNodes();
              for (int child = 0; child < nl.getLength(); child++) {
                String nodeName = nl.item(child).getNodeName();
                if(nodeName.trim().toUpperCase().contains("EMAIL_ADDRESS") && !nodeName.trim().toUpperCase().contains("SWITCH_ACC_EMAIL_ADDRESS") && !nodeName.trim().toUpperCase().contains("ASSOCIATED_ACC_EMAIL_ADDRESS")) {
                  nl.item(child).setTextContent(emailAddress);
                } else if(nodeName.trim().toUpperCase().contains("PASSWORD") && !nodeName.trim().toUpperCase().contains("SWITCH_ACC_PASSWORD") && !nodeName.trim().toUpperCase().contains("ASSOCIATED_ACC_PASSWORD")) {
                  nl.item(child).setTextContent(pass);
                }
              }
            } else {
              expr = xpath.compile("//" + env.trim().toUpperCase() + "/" + version.trim().toUpperCase()); 
              obj = expr.evaluate(xmldoc, XPathConstants.NODESET);
              node = ((NodeList)obj).item(0);
              
              Element driverTypeNode = xmldoc.createElement(driverType.trim().toUpperCase());
              String[] keyNodes = new String[]{"EMAIL_ADDRESS", "PASSWORD", "PLAN_EMAIL_ADDRESS", "PLAN_PASSWORD", "PAID_EMAIL_ADDRESS", "PAID_PASSWORD", "PARTIALLY_PAID_EMAIL_ADDRESS", "PARTIALLY_PAID_PASSWORD", "EXISTING_CARD_EMAIL_ADDRESS", "EXISTING_CARD_PASSWORD", "SWITCH_ACC_EMAIL_ADDRESS", "SWITCH_ACC_PASSWORD", "ASSOCIATED_ACC_EMAIL_ADDRESS", "ASSOCIATED_ACC_PASSWORD"};
              for(int j = 0; j < keyNodes.length; j++) {
                if(keyNodes[j].trim().toUpperCase().contains("EMAIL_ADDRESS")) {
                  Element emailAddressNode = xmldoc.createElement(keyNodes[j]);
                  emailAddressNode.appendChild(xmldoc.createTextNode(emailAddress));
                  driverTypeNode.appendChild(emailAddressNode);
                } else if(keyNodes[j].trim().toUpperCase().contains("PASSWORD")) {
                  Element passwordNode = xmldoc.createElement(keyNodes[j]);
                  passwordNode.appendChild(xmldoc.createTextNode(pass));
                  driverTypeNode.appendChild(passwordNode);
                }
              }
              node.appendChild(driverTypeNode);
            }
          }
        }
        
        //write the content into xml file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        
        DOMSource source = new DOMSource(xmldoc);
        System.out.println("App credentials path : " + xmlPath);
        StreamResult result = new StreamResult(new FileOutputStream(xmlPath));
        transformer.transform(source, result);
        System.out.println("Done");
      } catch (Exception excep) {
        excep.printStackTrace();
      }
  }
}