package helper.listener;

import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import helper.logger.LoggerHelper;
import utils.ExtentManager;


public class ExtentListner implements ITestListener{
	
	private Logger log = LoggerHelper.getLogger(ExtentListner.class);
	public static ExtentReports extent;
	public static ExtentTest test;

	// Methods will execute before the Suite start 
	public void onStart(ITestContext context) {
		//extent = ExtentManager.getInstance();
		//test = extent.createTest(context.getName());
		// Reporter.log will add logs in the emailable-report.html which is a part of testng
		Reporter.log(context.getName() + " - test has been started");
		log.info(context.getName() + " - test has been started");
	}
	
	// Method will execute once the Suite is finished
	public void onFinish(ITestContext context) {
		//extent.flush();
		Reporter.log(context.getName() + " - test has finished");
		log.info(context.getName() + " - test has finished");
	}
	
	// Method will execute before the main test start (@Test)
	public void onTestStart(ITestResult result) {
		//test.log(Status.INFO, result.getName() + " - started");
		Reporter.log(result.getMethod().getMethodName() + " - test has started");
		log.info(result.getMethod().getMethodName() + " - test has started");
	}

	// Method will execute only when the test is pass
	public void onTestSuccess(ITestResult result) {
		//test.log(Status.INFO, result.getName() + " - passed");
		Reporter.log(result.getMethod().getMethodName() + " - test has passed");
		log.info(result.getMethod().getMethodName() + " - test has passed");
	}

	// Method will execute only on the event of fail test
	public void onTestFailure(ITestResult result) {
		//test.log(Status.FAIL, result.getThrowable() + " - failed");
		Reporter.log(result.getMethod().getMethodName() + " - test has failed - " + result.getThrowable());
		log.error(result.getMethod().getMethodName() + " - test has failed - " + result.getThrowable());
	}

	// Method will execute only if any of the main test(@Test) get skipped
	public void onTestSkipped(ITestResult result) {
		//test.log(Status.SKIP, result.getThrowable() + " - skipped");
		Reporter.log(result.getMethod().getMethodName() + " - test has skipped - " + result.getThrowable());
		log.warn(result.getMethod().getMethodName() + " - test has failed - " + result.getThrowable());
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// not implemented
	}

	
	
	
	
}
