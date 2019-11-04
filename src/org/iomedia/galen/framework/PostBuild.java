package org.iomedia.galen.framework;

import org.iomedia.framework.Driver;
import org.iomedia.framework.WebDriverFactory;

public class PostBuild extends Driver {
	public static void main(String[] args) throws Exception {
		PostBuild postBuild = new PostBuild();
		String relatedEnv = null;
		String envConfig = System.getProperty("envConfig") != null && !System.getProperty("envConfig").trim().equalsIgnoreCase("") ? System.getProperty("envConfig").trim() : "";
		if(!envConfig.trim().equalsIgnoreCase("")) {
			relatedEnv = new Util(postBuild.Environment).getRelatedEnv();
			if(relatedEnv != null && !relatedEnv.trim().equalsIgnoreCase("")) {
				postBuild.Environment.put("env", relatedEnv.trim().toUpperCase());
			}
		}
		boolean setSession = System.getProperty("setSession") != null && !System.getProperty("setSession").trim().equalsIgnoreCase("") ? Boolean.valueOf(System.getProperty("setSession").trim()) : Boolean.valueOf(postBuild.Environment.get("setSession").trim());
		if(setSession) {
			String sessions = postBuild.Environment.get("sessions");
			if(!sessions.trim().equalsIgnoreCase("")) {
				WebDriverFactory driverFactory = new WebDriverFactory();
				driverFactory.setDriverType(new ThreadLocal<String>(){@Override public String initialValue() {
					return "CHROME1";
				};});
				Sessions ses= new Sessions(driverFactory, postBuild.Environment);
			    ses.setSessionLimit(sessions);
			}
		}
	}
}
