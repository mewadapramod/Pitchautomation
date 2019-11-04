package org.iomedia.galen.framework;

import java.io.File;
import java.io.FileOutputStream;

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

import org.iomedia.framework.Driver;
import org.iomedia.framework.WebDriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Sessions extends Driver {
	HashMapNew Environment;
	WebDriverFactory driverFactory;
	
	public Sessions(WebDriverFactory driverFactory, HashMapNew Environment) {
		this.Environment = Environment;
		this.driverFactory = driverFactory;
	}
	
	public void setSessionLimit(String session) throws Exception {
		String url = Environment.get("APP_URL").trim();
		String userName = System.getProperty("adminUserName") != null && !System.getProperty("adminUserName").trim().equalsIgnoreCase("") ? System.getProperty("adminUserName").trim() : Environment.get("adminUserName").trim();
		String password = System.getProperty("adminPassword") != null && !System.getProperty("adminPassword").trim().equalsIgnoreCase("") ? System.getProperty("adminPassword").trim() : Environment.get("adminPassword").trim();
		
		String tm_oauth_url = Environment.get("TM_OAUTH_URL").trim();
    	if(userName.equalsIgnoreCase("") || password.equalsIgnoreCase("")) {
    		if(tm_oauth_url.contains("app.ticketmaster.com")){
    			String clientId = url.substring(url.lastIndexOf("/") + 1);
    			if(clientId.trim().endsWith("/")) {
    				clientId = clientId.substring(0, clientId.trim().length() - 1);
    			}
    			userName = "admin";
    			password = clientId + "1234";
    		} else {
    			return;
    		}
    	}
		
    	super.driverFactory = this.driverFactory;
    	super.driverType = driverFactory.getDriverType().get();
		WebDriver driver = createDriver(new Object[]{});
		try {
			driver.get(url + "/user/login");
			driver.findElement(By.id("edit-name")).sendKeys(userName);
			driver.findElement(By.id("edit-pass")).sendKeys(password);
			driver.findElement(By.id("edit-submit")).click();
			
			if(!BaseUtil.checkIfElementPresent(By.id("toolbar-bar"), 10))	{
				return;
			}
			
			driver.navigate().to(url + "/admin/config/people/session-limit");
			WebElement sessionInput = driver.findElement(By.id("edit-session-limit-max"));
			String oldValue = sessionInput.getAttribute("value");
			
			if(oldValue.trim().equalsIgnoreCase(session)) {
				return;
			}
			
			sessionInput.clear();
			sessionInput.sendKeys(session);
			JavascriptExecutor ex = (JavascriptExecutor)driver;
			ex.executeScript("arguments[0].click();", driver.findElement(By.id("edit-submit")));
			
			setXMLNodeValue(Environment.get("appCredentialsPath").trim(), Environment.get("env").trim(), Environment.get("version").trim(), "sessions", oldValue);
			driver.navigate().to(url + "/user/logout");
			BaseUtil.getElementWhenPresent(By.xpath(".//input[@name='email'] | .//div[@class='mobile-signin']//span[text()='Sign In']"));
		} catch(Exception ex) {
			throw ex;
		}
		finally {
			driver.quit();
		}
	}
	
	public void setXMLNodeValue(String path, String env, String version, String tag, String value){
	    String RootPath = System.getProperty("user.dir");
	    try
	    {
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
	      
	      expr = xpath.compile("//" + env.trim().toUpperCase() + "/" + version.trim().toUpperCase() + "/COMMON");
	      obj = expr.evaluate(xmldoc, XPathConstants.NODESET);
	      if(obj != null) {
	    	  Node node = ((NodeList)obj).item(0);
	    	  if(node != null) {
	    		  NodeList nl = node.getChildNodes();
	    		  for (int child = 0; child < nl.getLength(); child++) {
	    			  String nodeName = nl.item(child).getNodeName();
	    			  if(nodeName.trim().equalsIgnoreCase(tag.trim())) {
	    				  nl.item(child).setTextContent(value);
	    				  break;
	    			  }
	    		  }
	    	  } else {
	    		  expr = xpath.compile("//" + env.trim().toUpperCase() + "/" + version.trim().toUpperCase()); 
	    		  obj = expr.evaluate(xmldoc, XPathConstants.NODESET);
	    		  node = ((NodeList)obj).item(0);
	    		  
	    		  Element commonNode = xmldoc.createElement("COMMON");
	    		  Element _node = xmldoc.createElement(tag.trim());
				  _node.appendChild(xmldoc.createTextNode(value));
				  commonNode.appendChild(_node);
		    	  node.appendChild(commonNode);
	    	  }
	      }
	      
	      //write the content into xml file
	      TransformerFactory transformerFactory = TransformerFactory.newInstance();
	      Transformer transformer = transformerFactory.newTransformer();
	      transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
	      transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	      
	      DOMSource source = new DOMSource(xmldoc);
	      System.out.println("Path : " + xmlPath);
	      StreamResult result = new StreamResult(new FileOutputStream(xmlPath));
	      transformer.transform(source, result);
	      System.out.println("Done");
	    }
	    catch (Exception excep){
	    	excep.printStackTrace();
	    }
	}
}
