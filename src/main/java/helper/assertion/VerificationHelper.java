package helper.assertion;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import helper.logger.LoggerHelper;
import testbase.TestBase;

//https://www.youtube.com/watch?v=aBAfPLbTC6Q&list=PL5NG-eEzvTthT8eMv6PIgDI7SHL_ajvnE&index=15
public class VerificationHelper {
	
	private WebDriver driver;
	private Logger log = LoggerHelper.getLogger(VerificationHelper.class);
	
	public VerificationHelper(WebDriver driver) {
		this.driver = driver;
	}

	public boolean isDisplayed(WebElement element) {
		try {
			element.isDisplayed();
			log.info("Element is displayed " +element.getText());
			TestBase.logExtentReport("Element is displayed " +element.getText());
			return true;
		} catch(Exception e) {
			log.error("Element is not displayed", e.getCause());
			TestBase.logExtentReport("Element is not displayed"+ e.getMessage());
			return false;
		}
	}
	
	public boolean isNotDisplayed(WebElement element) {
		try {
			element.isDisplayed();
			log.info("Element is not present " +element.getText());
			TestBase.logExtentReport("Element is not present " + element.getText());
			return false;
		} catch(Exception e) {
			log.error("Element is not present");
			TestBase.logExtentReport("Element is not present");
			return true;
		}
	}
	
	public String getText(WebElement element) {
		if(null==element) {
			log.info("WebElement is null");
			return null;
		} else {
			boolean status = isDisplayed(element);
			if(status) {
				log.info("Element text is : "+element.getText());
				return element.getText();
			}else {
				//log will be handled in the above isDisplayed method
				return null;
			}
		}
	}
	
	public String getTitle() {
		log.info("Page title is "+driver.getTitle());
		return driver.getTitle();
	}
	
}
