package helper.alert;

import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;

import helper.logger.LoggerHelper;

public class AlertHelper {
	
	private WebDriver driver;
	private Logger log = LoggerHelper.getLogger(AlertHelper.class);

	public AlertHelper(WebDriver driver) {
		this.driver = driver;
		log.info("AlertHelper object is created");
	}
	
	public Alert getAlert() {
		log.info("Alert text : " +driver.switchTo().alert().getText());
		return driver.switchTo().alert();
	}
	
	public void acceptAlert() {
		getAlert().accept();
		log.info("Alert accepted");
	}
	
	public void rejectAlert() {
		getAlert().dismiss();
		log.info("Alert rejected");
	}
	
	public String getAlertText() {
		String text = getAlert().getText();
		log.info("Alert text: " +text);
		return text;
	}
	
	public boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			log.info("Alert is present");
			return true;
		} catch(NoAlertPresentException e) {
			log.info(e.getCause());
			return false;
		}
	}
	
	public void acceptAlertIfPresent() {
		if(isAlertPresent()) {
			acceptAlert();
		}else {
			log.info("Alert is not present");
		}
	}
	
	public void rejectAlertIfPresent() {
		if(isAlertPresent()) {
			rejectAlert();
		}else {
			log.info("Alert is not present");
		}
	}
	
	public void acceptPrompt(String text) {
		if(isAlertPresent()) {
			Alert alert = getAlert();
			alert.sendKeys(text);
			alert.accept();
			log.info("Alert text : "+text);
		}
	}
}
