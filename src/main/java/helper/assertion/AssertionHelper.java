package helper.assertion;

import org.apache.log4j.Logger;
import org.testng.Assert;

import helper.logger.LoggerHelper;

public class AssertionHelper {
	
	// we have to make the object static because not static variables cannot be accessed within static methods
	private static Logger log = LoggerHelper.getLogger(AssertionHelper.class);
	
	public static void verifyText(String s1, String s2) {
		log.info("Verifying text: " +s1+ " with " +s2);
		Assert.assertEquals(s1, s2);
	}
	
	public static void makeTrue() {
		log.info("Making script PASS");
		Assert.assertTrue(true);
	}
	
	public static void makeTrue(String message) {
		log.info("Making script PASS : " +message);
		Assert.assertTrue(true, message);
	}
	
	public static void makeFalse() {
		log.info("Making script FAIL");
		Assert.assertTrue(false);
	}
	
	public static void makeFalse(String message) {
		log.info("Making script FAIL : " +message);
		Assert.assertTrue(false, message);
	}
	
	public static void verifyTrue(boolean status) {
		Assert.assertTrue(status);
	}
	
	public static void verifyFalse(boolean status) {
		Assert.assertTrue(status);
	}
	
	public static void verifyNull(String s1) {
		log.info("Verify if object is null");
		Assert.assertNull(s1);
	}
	
	public static void verifyNotNull(String s1) {
		log.info("Verify if object is null");
		Assert.assertNotNull(s1);
	}
	
	public static void pass() {
		Assert.assertTrue(true);
	}
	
	public static void fail() {
		Assert.assertTrue(false);
	}
	
	public static void updateTestStatus(boolean status) {
		if(status) {
			pass();
		} else {
			fail();
		}
	}

}