package helper.browserconfiguration.config;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import helper.browserconfiguration.BrowserType;
import helper.resource.ResourceHelper;

//https://www.youtube.com/watch?v=wY9nrkznFWU&index=28&list=PL5NG-eEzvTthT8eMv6PIgDI7SHL_ajvnE

public class PropertyReader implements  ConfigReader{
	
	private static FileInputStream file;
	public static Properties OR;
	
	public PropertyReader() {
		// this will load the properties file
		try {
			String filePath = ResourceHelper.getResourcePath("/resources/configFile/config.properties");
			file = new FileInputStream(new File(filePath));
			OR = new Properties();
			OR.load(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int getImplicitWait() {
		return Integer.parseInt(OR.getProperty("implicitwait"));
	}

	public int getExplicitWait() {
		return Integer.parseInt(OR.getProperty("explicitwait"));
	}

	public int getPageLoadTime() {
		return Integer.parseInt(OR.getProperty("pageloadtime"));
	}

	public BrowserType getBrowserType() {
		return BrowserType.valueOf(OR.getProperty("browsertype"));
	}
	
	public String getURL() {
		return OR.getProperty("URL");
	}
	
	public String getEmail() {
		return OR.getProperty("email");
	}

	public String getPassword() {
		return OR.getProperty("password");
	}
}
