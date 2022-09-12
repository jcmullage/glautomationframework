package helper.wait;

import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import helper.logger.LoggerHelper;

// https://www.youtube.com/watch?v=sPFwDZ2HSno&list=PL5NG-eEzvTthT8eMv6PIgDI7SHL_ajvnE&index=7
public class WaitHelper {
	
	private WebDriver driver;
	private Logger log = LoggerHelper.getLogger(WaitHelper.class);
	
	public WaitHelper(WebDriver driver) {
		this.driver = driver;
		log.info("WaitHelper object created");
	}
	
	
	//Implicit wait method
	public void setImplicitWait(long timeout, TimeUnit unit) {
		driver.manage().timeouts().implicitlyWait(timeout, unit);
		log.info("setting implicit wait to "+timeout);
	}
	
	// This method will return a WebDriverWait object
	private WebDriverWait getWait(int timeOutInSeconds, int pollingEveryInMilliseconds) {
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		wait.pollingEvery(Duration.ofMillis(pollingEveryInMilliseconds));
		wait.ignoring(NoSuchElementException.class);
		wait.ignoring(ElementNotVisibleException.class);
		wait.ignoring(StaleElementReferenceException.class);
		wait.ignoring(NoSuchFrameException.class);
		return wait;
	}
	
	// method waits until the element is visible
	public void WaitForElementToBeVisible(WebElement element, int timeOutInSeconds, int pollingEveryInMilliseconds) {
		log.info("waiting for : " +element+ " to be visible for " +timeOutInSeconds+ " seconds");
		WebDriverWait wait = getWait(timeOutInSeconds, pollingEveryInMilliseconds);
		wait.until(ExpectedConditions.visibilityOf(element));
		log.info("element is visible");
	}
	
	// method waits until the element is clickable
	public void WaitForElementToBeClickable(WebElement element, int timeOutInSeconds) {
		log.info("waiting for : " +element+ " to be clickable for " +timeOutInSeconds+ " seconds");
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		wait.until(ExpectedConditions.elementToBeClickable(element));
		log.info("element is clickable");
	}
	
	// method waits until a element disappears
	public boolean WaitUntilElementIsNotPresent(WebElement element, long timeOutInSeconds) {
		log.info("waiting for : " +element+ " to dissapear for " +timeOutInSeconds+ " seconds");
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		boolean status = wait.until(ExpectedConditions.invisibilityOf(element));
		log.info("element has dissapeared now");
		return status;
	}
	
	// waiting until frame becomes available and switches to it
	public void WaitUntilFrameIsAvaialableAndSwitchToIt(WebElement element, long timeOutInSeconds) {
		log.info("waiting for frame : " +element+ " to be available for " +timeOutInSeconds+ " seconds");
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(element));
		log.info("Frame is avaialble and switched");
	}
	
	// This method will return a new fluent wait instance
	// https://www.youtube.com/watch?v=f2wsRN1V8N8&index=8&list=PL5NG-eEzvTthT8eMv6PIgDI7SHL_ajvnE
	private Wait<WebDriver> getFluentWait(int timeOutInSeconds, int pollingEveryInMilliseconds) {
		Wait<WebDriver> fwait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeOutInSeconds))
				.pollingEvery(Duration.ofMillis(pollingEveryInMilliseconds)).ignoring(NoSuchElementException.class);
		return fwait;
	}
	
	// 
	// https://www.youtube.com/watch?v=f2wsRN1V8N8&index=8&list=PL5NG-eEzvTthT8eMv6PIgDI7SHL_ajvnE
	public WebElement waitForElement(WebElement element, int timeOutInSeconds, int pollingEveryInMilliseconds) {
		Wait<WebDriver> fwait = getFluentWait(timeOutInSeconds, pollingEveryInMilliseconds);
		fwait.until(ExpectedConditions.visibilityOf(element));
		return element;
	}
	
	public void pageLoadTime(long timeout, TimeUnit unit) {
		log.info("waiting for the page to load for : " + timeout + " seconds");
		driver.manage().timeouts().pageLoadTimeout(timeout, unit);
		log.info("page has been loaded");
	}
	
	// This method will make sure element is clickable
	public void waitForElement(WebElement element, int timeOutInSeconds) {
		log.info("Waiting for element - " + element.toString() + " for - " + timeOutInSeconds + " seconds");
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		wait.until(ExpectedConditions.visibilityOf(element));
		log.info("Element - " + element.toString() + " is visible now");
	}

}
