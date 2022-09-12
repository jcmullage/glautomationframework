package helper.javascript;

import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import helper.logger.LoggerHelper;

//https://www.youtube.com/watch?v=9zI-VlzlEgk&list=PL5NG-eEzvTthT8eMv6PIgDI7SHL_ajvnE&index=11
public class JavascriptHelper {
	
	private WebDriver driver;
	private Logger log = LoggerHelper.getLogger(JavascriptHelper.class);
	
	public JavascriptHelper(WebDriver driver) {
		this.driver = driver;
		log.info("javascript helper has been initialized");
	}
	
	public Object executeScript(String script) {
		JavascriptExecutor exe = (JavascriptExecutor)driver;
		return exe.executeScript(script);
	}
	
	// multiple arguments
	public Object executeScript(String script, Object...args) {
		// cast the driver with the java-script-executor
		JavascriptExecutor exe = (JavascriptExecutor)driver;
		return exe.executeScript(script, args);
	}
	
	public void scrollToElement(WebElement element) {
		log.info("scrolling to webelement");
		// call the executeScript method and pass the script and multiple arguments 
		executeScript("window.scrollTo(arguments[0], arguments[1])", element.getLocation().x, element.getLocation().y);
	}

	public void scrollToElementAndClick(WebElement element) {
		scrollToElement(element);
		element.click();
		log.info("element has been clicked : " +element.toString());
	}
	
	// method will scroll to web-elements location
	public void scrollIntoView(WebElement element ){
		log.info("scroll till web element");
		executeScript("arguments[0].scrollIntoView()", element);
	}
	
	// method will scroll to web-elements location
	public void scrollIntoViewAndClick(WebElement element){
		log.info("scroll till web element");
		scrollIntoView(element);
		element.click();
		log.info("Element has been clicked : "+element.toString());
	}

	public void scrollDownVeritcally() {
		log.info("scrolling down vertically");
		executeScript("window.scrollTo(0, document.body.scrollHeight)"); 	
	}
	
	public void scrollUpVeritcally() {
		log.info("scrolling up vertically");
		executeScript("window.scrollTo(0, -document.body.scrollHeight)"); 	
	}
	
	public void scrollDownByPixel(int pixel) {
		executeScript("window.scrollBy(0," +pixel+ ")");
		log.info("scrolled down by: " +pixel+ " pixels");
	}
	
	public void scrollUpByPixel(int pixel) {
		executeScript("window.scrollBy(0, -" +pixel+ ")");
		log.info("scrolled up by: " +pixel+ " pixels");
	}
	
	public void zoomInBy100Percentage() {
		executeScript("document.body.style.zoom='100%'");
		log.info("zommed in by 100%");
	}
	
	public void zoomInBy60Percentage() {
		executeScript("document.body.style.zoom='60%'");
		log.info("zommed in by 60%");
	}
	
	public void clickElement(WebElement element) {
		executeScript("arguments[0].click();", element);
	}
	
}
