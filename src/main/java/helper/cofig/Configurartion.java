package helper.cofig;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class Configurartion {
	public static Properties prop = null;
	
	public static Properties readProperties( ) {

		try (InputStream input = new FileInputStream(System.getProperty("user.dir") + "\\config.settings")) {

			prop = new Properties();
			// load a properties file
			prop.load(input);
			

		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return prop;
		
	}
}
