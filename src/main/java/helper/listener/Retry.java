package helper.listener;

import org.apache.log4j.Logger;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import helper.logger.LoggerHelper;

//https://www.youtube.com/watch?v=rfLFt_BYvM0&list=PL5NG-eEzvTthT8eMv6PIgDI7SHL_ajvnE&index=13
/*
 * Technically IRetryAnalyzer is an interface with a retry method declaration. 
 * This method is used to analyze a test result in order to make a decision whether the test method has to be rerun.
 * So IRetryAnalyzer is about making the decision based on a test result. 
 * The invocationCount does not evaluate test result. It is a simple counter.
 */

public class Retry implements IRetryAnalyzer{
	
	private int retryCount = 0;
	private int maxRetryCount = 3;
	
	private Logger log = LoggerHelper.getLogger(Retry.class);
	
	// whenever a test will fail the listener object result will
	public boolean retry(ITestResult result) {
		if(retryCount<maxRetryCount) {
			log.info("Retry attempt number " +(retryCount+1)+ " for test method : " +result.getName()+ " - " +getResultStatusName(result.getStatus()));
			retryCount++;
			return true;
		}
		return false;
	}
	
	// returns the status name based on the status received in  
	public String getResultStatusName(int status) {
		String resultName = null;
		if(status==1) {
			resultName = "SUCCESS";
		}
		if(status==2) {
			resultName = "FAILURE";
		}
		if(status==3) {
			resultName = "SKIP";
		}
		return resultName;
	}
	
	
}

