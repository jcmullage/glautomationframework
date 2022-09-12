package helper.browserconfiguration.config;

import helper.browserconfiguration.BrowserType;

public interface ConfigReader {
	
	public int getImplicitWait();
	public int getExplicitWait();
	public int getPageLoadTime();
	public BrowserType getBrowserType();
	public String getURL();
	public String getEmail();
	public String getPassword();

}
