package testbase;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.security.Key;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.windows.PressesKeyCode;
import org.apache.commons.digester.plugins.PluginAssertionFailure;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.record.PageBreakRecord;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.winium.DesktopOptions;
import org.openqa.selenium.winium.WiniumDriver;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import helper.reporter.ReportBuilder;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.relevantcodes.extentreports.LogStatus;

import helper.browserconfiguration.BrowserType;
import helper.browserconfiguration.ChromeBrowser;
import helper.browserconfiguration.FirefoxBrowser;
import helper.browserconfiguration.config.ObjectReader;
import helper.browserconfiguration.config.PropertyReader;
import helper.logger.LoggerHelper;
import helper.resource.ResourceHelper;
import helper.wait.WaitHelper;
import io.appium.java_client.windows.WindowsDriver;
import utils.ExtentManager;


public class TestBase
{
	public static final ReportBuilder reportBuilder = new ReportBuilder();
	public static ExtentReports extent;
	public static ExtentTest test;
	public WebDriver driver;

	public static String timeStamp = new SimpleDateFormat("HHmmss").format(Calendar.getInstance().getTime());
	public static final String loginName = ("jcmullage")+timeStamp;

	private Logger log = LoggerHelper.getLogger(TestBase.class);
	public static File reportDirectory;
	private static WindowsDriver Session = null;
	private static WebElement element = null;
	WiniumDriver driverSp = null;


	@BeforeSuite
	public void beforeSuite(ITestContext context)
	{
		extent = ExtentManager.getInstance();
		if(context.getCurrentXmlTest().getName().equals("Default test")) {
			reportBuilder.startReport(getClass().getSimpleName(), "Chrome");
		}else {
		reportBuilder.startReport(context.getCurrentXmlTest().getName(), "Chrome");
		}
	}

	@BeforeTest
	public void beforeTest()
	{
//		reportBuilder.startReport(getClass().getSimpleName(), "Chrome");
		//Runtime.getRuntime().exec("C:\\Program Files (x86)\\Windows Application Driver\\WinAppDriver.exe");
		//ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "WinAppDriver.exe");
		//File dir = new File("C:\\Program Files (x86)\\Windows Application Driver\\");
		//pb.directory(dir);
		//Process p = pb.start();
		ObjectReader.reader = new PropertyReader();
		reportDirectory = new File(ResourceHelper.getResourcePath("/resources/screenshots"));
	}

	@AfterTest
	public void afterTest()
	{
		
		if(driver!=null)
			driver.quit();
		reportBuilder.endReport();
	}

	@BeforeClass
	public void beforeClass()
	{
		test =  extent.createTest(getClass().getSimpleName());
	}

	@BeforeMethod
	public void beforeMethod(Method method)
	{
		test.log(Status.INFO, method.getName() + " test-started-testbase");
		reportBuilder.startTestcase(method.getName());
	}

	@AfterMethod
	public void afterMethod(ITestResult result) throws IOException
	{
		if(result.getStatus() == ITestResult.FAILURE) {
			test.log(Status.FAIL, result.getThrowable());
			String imagePath = captureScreen(result.getName(), driver);
			test.addScreenCaptureFromPath(imagePath);
		}
		else if(result.getStatus() == ITestResult.SUCCESS)
		{
			//String imagePath = captureScreen(result.getName(), driver);
		}
		else if(result.getStatus() == ITestResult.SKIP)
		{
			test.log(Status.SKIP, result.getThrowable());
		}
		extent.flush();
		reportBuilder.endTestcase();
//		Session.quit();
//		driverSp.quit();
	}


	public WebDriver getBrowserObject(BrowserType browsertype) throws Exception
	{
		try {
			switch(browsertype)
			{
				case Chrome:
					ChromeBrowser chrome = ChromeBrowser.class.newInstance();
					ChromeOptions chromeoptions = chrome.getChromeOptions();
					return chrome.getChromeDriver(chromeoptions);
				case Firefox:
					FirefoxBrowser firefox = FirefoxBrowser.class.newInstance();
					FirefoxOptions firefoxoptions = firefox.getFirefoxOptions();
					return firefox.getFirefoxDriver(firefoxoptions);
				default:
					throw new Exception("Driver not found" + browsertype.name());
			}
		}
		catch(Exception e)
		{
			log.info(e.getMessage());
			throw e;
		}
	}

	public void setUpDriver(BrowserType browsertype) throws Exception
	{
		driver = getBrowserObject(browsertype);
		log.info("Initialize WebDriver " + driver.hashCode());
		WaitHelper wait = new WaitHelper(driver);
		wait.setImplicitWait(ObjectReader.reader.getImplicitWait(), TimeUnit.SECONDS);
		wait.pageLoadTime(ObjectReader.reader.getPageLoadTime(), TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	// we pass a WebDriver instance as the argument because, if we are calling from some other class you will have to pass the driver object because the driver wont be available at that time
	public String captureScreen(String fileName, WebDriver driver)
	{
//		//if(driver == null)
//	{
//		log.info("driver is null");
//		return null;
//	}
		if(fileName == "")
		{
			fileName = "blank";
		}
		File destFile = null;
		Calendar calender = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
		File screFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try
		{
			destFile = new File(reportDirectory + "/" + fileName + "_" + formatter.format(calender.getTime()) + ".png");
			Files.copy(screFile.toPath(), destFile.toPath());
			Reporter.log("<a href='" + destFile.getAbsolutePath() + "'><img src='" + destFile.getAbsolutePath() + "'height='100' width='100'/></a>");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return destFile.toString();
	}

	// method will capture screenshots and add them in the emailable report
	public void captureScreenShot(WebDriver driver)
	{
//		log.info("capturing screenshot");
//		// captureScreen method will take a screenshot and add the link to the emailable report
//		String screen  = captureScreen("", driver);
//		// this will add the screenshot to the extent report
//		try
//		{
//			test.addScreenCaptureFromPath(screen);
//		}
//		catch (IOException e)
//		{
//			e.printStackTrace();
//		}
	}

	public static void logExtentReport(String message) {
		test.log(Status.INFO, message);
	}

	public  void Pass(String message) {
		test.log(Status.PASS, message);
		reportBuilder.getLogger().log(LogStatus.PASS, message);

	}
	public  void Fail(String message) {

		test.log(Status.FAIL, message);
		captureScreenShot(Session);
		reportBuilder.getLogger().log(LogStatus.FAIL, message);
		
	}

	public void OpenWebURL(String url)
	{
		driver.get(url);
		driver.manage().window().maximize();
		log.info("Navigating to : " + url);
		logExtentReport("Navigating to : " + url);
	}


	//region Desktop Actions
	public void OpenDesktopApp(String appString) throws MalformedURLException
	{
		// Set Config data
		try {
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability("app", appString);
			Session = new WindowsDriver(new URL("http://127.0.0.1:4723"), capabilities);
			Session.manage().timeouts().implicitlyWait(12, TimeUnit.SECONDS);
			Set handles = Session.getWindowHandles();
			System.out.println(handles);
			for (String handle1 : Session.getWindowHandles())
			{
				System.out.println(handle1);
				Session.switchTo().window(handle1);
			}
			Pass("OpenDesktopApp");
		}catch (Exception e){
			String exception=e.toString();
			Fail(exception);
			Assert.fail();
		}

	}

	public void Click_ByImage(String path){


		String filepath = path;

		Screen s = new Screen();
		Pattern pdf = new Pattern(filepath + "input.PNG");
		try {
			s.click(pdf);
		} catch (FindFailed findFailed) {
			findFailed.printStackTrace();
		}
	}
	public void POS_Operation() throws InterruptedException {
		DesktopOptions desktop = new DesktopOptions();
		desktop.setApplicationPath("C:\\ACCESSV3\\apctm_btq.exe");
		try {
			driverSp = new WiniumDriver(new URL ("http://localhost:9999"), desktop);
			Pass("POS_Operation Init");
		} catch (MalformedURLException e) {
			e.printStackTrace();
			Fail("POS_Operation Init");
			Assert.fail();
		}
		Thread.sleep(10000);
		driverSp.findElementById("1008").click();
		Robot_TAB();
		Thread.sleep(4000);
		driverSp.findElementById("1007").click();
		Thread.sleep(4000);
		driverSp.findElementById("1007").sendKeys("1");
		Thread.sleep(4000);
		//driverSp.findElementByName("Sign On").click();
		Robot_Enter();
		System.out.println("RR");
		Thread.sleep(8000);
		Robot_KeyPressOK_POS();
		Thread.sleep(6000);
		Robot_Cancel();
		Thread.sleep(5000);
		System.out.println("****");
		Robot_Queue();
		Thread.sleep(6000);
		Robot_Search();
		System.out.println("OOO");
	}
	public void SwitchDesktopWindow()
	{
		Set handles = Session.getWindowHandles();
		for (String handle1 : Session.getWindowHandles())
		{
			System.out.println(handle1);
			Session.switchTo().window(handle1);
		}
	}


	public void ClickDesktop(String locator)
	{
		try
		{
			Session.findElementByName(locator).click();
			Pass("Click");
		}
		catch (Exception ex)
		{
			try
			{
				Session.findElementByAccessibilityId(locator).click();
				Pass("Click");
			}
			catch (Exception e)
			{

				System.out.println(e.getMessage().toString());
				Fail("Click");
				Assert.fail();
				try {
					TakeScreenShotOnFailure();
				} catch (Exception exc) {
					exc.printStackTrace();
				}
			}
		}
	}

	public void PressKeysDesktop(String locator, Keys key)
	{
		try
		{
			Thread.sleep(2000);
			Session.findElementByName(locator).sendKeys(key);
			//	Pass("KeyboardEvent");
		}
		catch (Exception e)
		{
			//	Fail("KeyboardEvent: " + e);
			//	Assert.fail();
		}
	}
	public void WaitElementPresentDesktop(String locator)
	{
		try
		{
			int i;
			boolean existEle = Session.findElementByName(locator).isDisplayed();
			for (i=0;i<=100;i++){
				if (existEle){
					break;
				}
				else
					Thread.sleep(1000);
				Pass("Passed");
			}
		}
		catch (Exception e)
		{
			Fail("Event: " + e);
		}
	}
	public void FillDesktop(String locator, String text)
	{
		try
		{
			Session.findElementById(locator).sendKeys(text);
			Pass("Fill");
		}
		catch (Exception ex)
		{
			try
			{
				Session.findElementByAccessibilityId(locator).sendKeys(text);
				Pass("Fill");
			}
			catch (Exception e)
			{
				try
				{
					Session.findElementByName(locator).sendKeys(text);
					Pass("Fill");
				}
				catch (Exception e1)
				{
					System.out.println(e1.getMessage());
					Fail("Fill");
					Assert.fail();
					extent.flush();
				}
			}
		}
	}

	public void Robot_Enter(){

		try {
			java.awt.Robot robot = new java.awt.Robot();
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void Robot_KeyPressOK_POS(){
		try {
			java.awt.Robot robot = new java.awt.Robot();
			robot.keyPress(KeyEvent.VK_ALT);
			robot.keyPress(KeyEvent.VK_K);
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void Robot_Cancel(){
		try {
			java.awt.Robot robot = new java.awt.Robot();
			robot.keyPress(KeyEvent.VK_ALT);
			robot.keyPress(KeyEvent.VK_N);
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public void Robot_TAB(){

		try {
			java.awt.Robot robot = new java.awt.Robot();
			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_TAB);
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void Robot_BackSpace(){

		try {
			java.awt.Robot robot = new java.awt.Robot();
			robot.keyPress(KeyEvent.VK_BACK_SPACE);

		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void Robot_DoubleClick(){
		try {
			java.awt.Robot robot = new java.awt.Robot();
			robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
			robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
// second click
			robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
			robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	public void Robot_OptionPopup(){
		// Yes/No popup
		try {
			java.awt.Robot robot = new java.awt.Robot();
			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_TAB);
			robot.delay(3000);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void Robot_Queue(){
		try {
			java.awt.Robot robot = new java.awt.Robot();
			robot.keyPress(KeyEvent.VK_ALT);
			robot.keyPress(KeyEvent.VK_U);

		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void Robot_Search(){

		try {
			java.awt.Robot robot = new java.awt.Robot();
			robot.keyPress(KeyEvent.VK_ALT);
			robot.keyPress(KeyEvent.VK_S);

		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void ClickTab(String locator){

		try
		{
			Session.findElementByName(locator).sendKeys(Keys.TAB);

			Pass("TAB");
		}
		catch (Exception ex)
		{
			try
			{
				Session.findElementByName(locator).sendKeys(Keys.TAB);
				Pass("TAB");
			}
			catch (Exception e)
			{

				System.out.println(e.getMessage().toString());
				Pass("TAB");
				try {
					TakeScreenShotOnFailure();
				} catch (Exception exc) {
					exc.printStackTrace();
				}
			}
		}

	}

	public void ClearDesktop(String locator)
	{
		try
		{
			Session.findElementByName(locator).clear();
			Pass("Clear");
		}
		catch (Exception ex)
		{
			try
			{
				Session.findElementByAccessibilityId(locator).clear();
				Pass("Clear");
			}
			catch (Exception e)
			{
				System.out.println(e.getMessage().toString());
				Fail("Clear");
			}
		}
	}

	public void IsDisplayedDesktop(String locator)
	{
		boolean displayed = false;
		try
		{
			displayed = Session.findElementByName(locator).isDisplayed();
		}
		catch (Exception ex)
		{
			try
			{
				displayed = Session.findElementByAccessibilityId(locator).isDisplayed();
			}
			catch (Exception e)
			{
				System.out.println(e.getMessage());
				test.fail("Item not displayed");
			}
		}
		System.out.println("IsDisplayed result: " + displayed);
	}

	public void SeeTextDesktop(String locator, String text)
	{
		try
		{
			String actualText = Session.findElementByName(locator).getText();
			Assert.assertEquals(actualText, text);
			Pass("SeeText");
		}
		catch (Exception ex)
		{
			try
			{
				String actualText = Session.findElementByAccessibilityId(locator).getText();
				Assert.assertEquals(actualText, text);
				Pass("SeeText");
			}
			catch (Exception e)
			{
				System.out.println(e.getMessage());
				Fail("SeeText");
				Assert.fail();
			}
		}
	}

	public void SwitchToWindow()
	{
		Set handles = Session.getWindowHandles();
		System.out.println(handles);
		for (String handle1 : Session.getWindowHandles())
		{
			System.out.println(handle1);
			Session.switchTo().window(handle1);
		}
	}

	public String GetTextDesktop(String locator)
	{
		try
		{
			String textvalue=Session.findElementByName(locator).getText();
			return textvalue;
		}
		catch(Exception e)
		{
			try
			{
				String textvalue=Session.findElementByAccessibilityId(locator).getText();
				return textvalue;
			}
			catch(Exception ex)
			{
				System.out.println(ex.getMessage());
				Fail("GetText");
				return null;
			}
		}
	}

	public void QuitDesktopApp()
	{
		Session.quit();
	}
	//endregion

	//region Web Actions
	public void OpenBrowser(String url)
	{
		try
		{
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\drivers\\chromedriver.exe");
			driver = new ChromeDriver();
			driver.navigate().to(url);
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			Pass("OpenBrowser");
		}
		catch (Exception e)
		{
			Fail("OpenBrowser: " + e.getMessage());
			Assert.fail();
		}
	}

	public void Fill(String locator, String text)
	{
		try
		{
			if(locator.contains("//"))
			{
				driver.findElement(By.xpath(locator)).sendKeys(text);
				Pass("Fill");
			}
			else
			{
				driver.findElement(By.cssSelector(locator)).sendKeys(text);
				Pass("Fill");
			}
		}
		catch (Exception e)
		{
			Fail("Fill: "+e);
		}
	}

	public void Clear(String locator)
	{
		try
		{
			if(locator.contains("//"))
			{
				driver.findElement(By.xpath(locator)).clear();
				Pass("Clear");
			}
			else
			{
				driver.findElement(By.cssSelector(locator)).clear();
				Pass("Clear");
			}
		}
		catch (Exception e)
		{
			Fail("Clear: "+e);
		}
	}

	public void SeeEnabled(String locator)
	{
		try
		{
			if(locator.contains("//"))
			{
				driver.findElement(By.xpath(locator)).isEnabled();
				Pass("SeeEnabled");
			}
			else
			{
				driver.findElement(By.cssSelector(locator)).isEnabled();
				Pass("SeeEnabled");
			}
		}
		catch (Exception e)
		{
			Fail("SeeEnabled: "+e);
		}
	}
	
	public void Navigate(String navigate)
	{
		try
		{
			if (navigate.equalsIgnoreCase("back")) {
				driver.navigate().back();
			} else if (navigate.trim().equalsIgnoreCase("forward")) {
				driver.navigate().forward();
			}
				Pass("Navigate");
			
			
		}
		catch (Exception e)
		{
			Fail("Navigate: "+e);
		}
	}

	public void SeeDisplayed(String locator)
	{
		try
		{
			if(locator.contains("//"))
			{
				driver.findElement(By.xpath(locator)).isDisplayed();
				Pass("SeeDisplayed");
			}
			else
			{
				driver.findElement(By.cssSelector(locator)).isDisplayed();
				Pass("SeeDisplayed");
			}
		}
		catch (Exception e)
		{
			Fail("SeeDisplayed: "+e);
		}
	}
	
	public void Quit() {
		try {
			driver.quit();
			Pass("Quite");
		} catch (Exception e) {
			Fail("Quit");
		}
	}

	public void SelectOptionByVisibleText(String locator, String visibleText)
	{
		try
		{
			if(locator.contains("//"))
			{
				Select option =  new Select(driver.findElement(By.xpath(locator)));
				option.selectByVisibleText(visibleText);
				Pass("SelectOption");
			}
			else
			{
				Select option =  new Select(driver.findElement(By.cssSelector(locator)));
				option.selectByVisibleText(visibleText);
				Pass("SelectOption");
			}
		}
		catch (Exception e)
		{
			Fail("SelectOption: "+e);
		}
	}

	public void SelectOptionByIndex(String locator, int index)
	{
		try
		{
			if(locator.contains("//"))
			{
				Select option =  new Select(driver.findElement(By.xpath(locator)));
				option.selectByIndex(index);
				Pass("SelectOption");
			}
			else
			{
				Select option =  new Select(driver.findElement(By.cssSelector(locator)));
				option.selectByIndex(index);
				Pass("SelectOption");
			}
		}
		catch (Exception e)
		{
			Fail("SelectOption: "+e);
		}
	}

	public void Click(String locator)
	{
		try
		{
			if(locator.contains("//"))
			{
				driver.findElement(By.xpath(locator)).click();
				Pass("Click");
			}
			else
			{
				driver.findElement(By.cssSelector(locator)).click();
				Pass("Click");
			}
		}
		catch (Exception e)
		{
			Fail("Click "+e);
			Assert.fail();
		}
	}

	public void SwitchToIFrame(String name)
	{
		driver.switchTo().frame(name);
	}

	public String GetText(String locator)
	{
		try
		{
			if(locator.contains("//"))
			{
				String sValue = driver.findElement(By.xpath(locator)).getText();
				Pass("Get Text: " + sValue);
				return sValue;
			}
			else
			{
				String sValue = driver.findElement(By.cssSelector(locator)).getText();
				Pass("Get Text: " + sValue);
				return sValue;
			}
		}
		catch(Exception e)
		{
			Fail("Get Text");
			return null;
		}
	}
	
	public void CheckElementPresent(String locator) {
		try
		{
			if(locator.contains("//"))
			{
				driver.findElement(By.xpath(locator));
				Pass("Element Present");
				
			}
			else
			{
				driver.findElement(By.cssSelector(locator));
				Pass("Element Present");
				
			}
		}
		catch(Exception e)
		{
			Fail("Element Present");
			
		}
		
	}
	
	public void Wait(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//endregion

	public void TakeScreenShotOnFailure() throws Exception
	{
		File scrFile = ((TakesScreenshot)Session).getScreenshotAs(OutputType.FILE);
		// Now you can do whatever you need to do with it, for example copy somewhere
		FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir")+"\\screenshots\\fail.png"));
		test.addScreenCaptureFromPath(System.getProperty("user.dir")+"\\screenshots\\fail.png");
	}

	public void BrowserQuit(){

		driver.quit();
	}
}