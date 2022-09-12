package helper.frame;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import helper.logger.LoggerHelper;
import helper.wait.WaitHelper;

//https://www.youtube.com/watch?v=txtTwehC9jM&list=PL5NG-eEzvTthT8eMv6PIgDI7SHL_ajvnE&index=9
public class FrameHelper {
	
	private WebDriver driver;
	private Logger log = LoggerHelper.getLogger(FrameHelper.class);
	
	//constructor
	public FrameHelper(WebDriver driver) {
		this.driver = driver;
	}
	
	// This method will switch to frame based on index
	public void switchToFrame(int index) {
		driver.switchTo().frame(index);
		log.info("switched to frame :" +index);
	}
	
	// This method will switch to frame based on name
	public void switchToFrame(String framename) {
		driver.switchTo().frame(framename);
		log.info("switched to frame :" +framename);
	}
	
	// This method will switch to frame based on web-element
	public void switchToFrame(WebElement element) {
		driver.switchTo().frame(element);
		log.info("switched to frame :" +element);
	}
	
	

}
