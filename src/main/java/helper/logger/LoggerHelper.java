package helper.logger;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import helper.resource.ResourceHelper;

public class LoggerHelper {
	
	private static boolean root = false;
	
	public static Logger getLogger(Class cls) {
		if(root) {
			return Logger.getLogger(cls);
		}
		PropertyConfigurator.configure(ResourceHelper.getResourcePath("/resources/configFile/log4j.properties"));
		root = true;
		return Logger.getLogger(cls);
	}
	
	

}
