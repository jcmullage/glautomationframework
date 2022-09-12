package helper.window;

import java.util.Set;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import helper.logger.LoggerHelper;

public class WindowHelper {
	
	private WebDriver driver;
	Logger log = LoggerHelper.getLogger(WindowHelper.class);
	
	// constructor
	public WindowHelper(WebDriver driver) {
		this.driver = driver;
	}
	
	public void switchToParentWindow() {
		log.info("swithching to parent window");
		driver.switchTo().defaultContent();
	}
	
	// this method will switch to child window based on index
	public void switchToWindow(int index) {
		log.info("swithching to parent window");
		Set<String> windows = driver.getWindowHandles();
		int i = 1;
		for(String window : windows) {
			if(i == index) {
				log.info("switch to ");
				driver.switchTo().window(window);
			}else {
				i++;
			}
		}
	}
	
	// This method will close all windows and switch to the main window 
	public void closeAllTabsAndSwitchToMainWindow() {
		Set<String> windows = driver.getWindowHandles();
		String mainWindow = driver.getWindowHandle();
		
		for(String window : windows) {
			if(!window.equalsIgnoreCase(mainWindow)) {
				driver.close();
			}
		}
		
		driver.switchTo().window(mainWindow);
	}
	
	// This method will navigate the browser back
	public void navigateBack() {
		log.info("naviagting foward");
		driver.navigate().back();
	}
	
	// This method will navigate the browser forward
	public void naviagteFoward() {
		log.info("navigating backward");
		driver.navigate().forward();
	}

}
