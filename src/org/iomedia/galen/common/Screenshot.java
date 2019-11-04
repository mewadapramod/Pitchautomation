package org.iomedia.galen.common;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.iomedia.common.BaseUtil;
import org.iomedia.framework.OSValidator;

public class Screenshot {
	BaseUtil base;

	public Screenshot(BaseUtil base) {
		this.base = base;
	}
	
	public void deleteDirectoryIfExists(String clientName) throws Exception {
        String path1=  System.getProperty("user.dir") + OSValidator.delimiter + "CompareScreenshots" + OSValidator.delimiter + clientName.trim();
        File path = new File(path1);
        if (path.exists()) {
            System.out.println("delete the directory");
            FileUtils.deleteDirectory(path);
        }
    }
	
	public File createFolder() throws Exception {
		File file = new File("CompareScreenshots");
		if(!file.exists()) {
	        if (file.mkdir()) {
	            System.out.println("Directory is created!");
	        } else {           
	            System.out.println("Failed to create directory!");
	        }
		}
		return file;
	}
	
	public File siteFoldercreation(File parent, String clientName) {
//		parent.mkdirs();
//	    if (!parent.exists() || !parent.isDirectory()) {
//	     System.out.println("Parent Directory is missing");
//	    }
	    File subFile = new File(parent, clientName);
	    subFile.mkdir();
	    if (!subFile.exists() || !subFile.isDirectory()) {
	    	System.out.println("Failed to create Subdirectory");
	    }
	    return subFile;
	  }

	public String getFilepath(boolean deleteExistingDirectory) throws Exception {
		String clientName = "";
		if(!base.Environment.get("APP_URL").trim().equalsIgnoreCase("")) {
			String appurl = base.Environment.get("APP_URL").trim();
			if(appurl.trim().endsWith("/"))
				appurl = appurl.trim().substring(0, appurl.trim().length() - 1);
			String clientId = appurl.substring(appurl.lastIndexOf("/") + 1).trim().toUpperCase();
			clientName = clientId;
		} else {
			clientName = base.Environment.get("x-client").trim();
		}
		clientName = clientName.trim().toLowerCase();
		if(deleteExistingDirectory)
			deleteDirectoryIfExists(clientName);
		File file = siteFoldercreation(createFolder(), clientName);
		return file.getAbsolutePath();	
	}
}