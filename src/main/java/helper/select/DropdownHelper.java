package helper.select;

import java.util.List;
import java.util.LinkedList;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import helper.logger.LoggerHelper;

public class DropdownHelper {

	private WebDriver driver;
	private Logger log = LoggerHelper.getLogger(DropdownHelper.class);

	public DropdownHelper(WebDriver driver) {
		this.driver = driver;
		log.info("DropdownHelper object created");
	}
	
	public void selectUsingValue(WebElement element, String value) {
		Select select = new Select(element);
		select.selectByValue(value);
		log.info("Select using value : " +value);
	}
	
	public void selectUsingIndex(WebElement element, int index) {
		Select select =  new Select(element);
		select.selectByIndex(index);
		log.info("Select using index : " +index);
	}
	
	public void selectUsingVisibleText(WebElement element, String visibleText) {
		Select select = new Select(element);
		select.selectByVisibleText(visibleText);
		log.info("Select using visible text : " +visibleText);
	}
	
	public void deSelectUsingValue(WebElement element, String value) {
		Select select = new Select(element);
		select.deselectByValue(value);
		log.info("de-select using value : " +value);
	}
	
	public void deSelectUsingIndex(WebElement element, int index) {
		Select select =  new Select(element);
		select.deselectByIndex(index);
		log.info("de-select using index : " +index);
	}
	
	public void deSelectUsingVisibleText(WebElement element, String visibleText) {
		Select select = new Select(element);
		select.deselectByVisibleText(visibleText);
		log.info("de-select using visible text : " +visibleText);
	}
	
	public List<String> getAllDropDownData(WebElement element){
		Select select =  new Select(element);
		List<WebElement> elementList = select.getOptions();
		List<String> valueList = new LinkedList<String>();
		for(WebElement ele: elementList) {
			log.info(ele.getText());
			valueList.add(ele.getText());
		}
		return valueList;
	}
	
}
