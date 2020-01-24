package com.psd2.logger;

import org.testng.Assert;

import com.psd2.core.SeleniumUtility;
import com.psd2.core.TestBase;

/**
 *  Class Description - This provides mechanism to perform verification check using Assert function.
 *  
 * @author alok_kumar
 *
 */
public class TestVerification {

	public boolean testResult;

	public TestVerification(){
		testResult = true;
	}
	
	public void testResultFinalize() {
		if (!testResult) {
			Assert.fail("Test failed due to some VP failure, please check error message in the detail log above.");
		}
	}

	
	/*
	 * In assert methods, if VP condition is false, the testcase will fail and
	 * stop at once. Exception is thrown to notify TestNG.
	 */
	public void assertTrue(boolean condition, String msg)
			throws Throwable {
		try {
			Assert.assertTrue(condition, msg);
			TestLogger.logSuccess("[VP Pass] : " + msg);
		} catch (Throwable e) {
			TestLogger.logError("[VP Fail] : " + msg);
			
			if(TestBase.driver != null){				
				SeleniumUtility.takeScreenShot();
			}
			throw e;
		}
	}

	public void assertFalse(boolean condition, String msg)
			throws Throwable {
		try {
			Assert.assertFalse(condition, msg);
			TestLogger.logSuccess("[VP Pass] : " + msg);
		} catch (Throwable e) {
			TestLogger.logError("[VP Fail] : " + msg);
			if(TestBase.driver != null){				
				SeleniumUtility.takeScreenShot();
			}
			throw e;
		}
	}
	
	public void assertStringEquals(String actual, String expected, String msg)
			throws Throwable {
		try {
			Assert.assertEquals(actual, expected);
			TestLogger.logSuccess("[VP Pass] : " + msg + " [ Expected: "
					+ expected + ", Actual: " + actual + "]");
		} catch (Throwable e) {
			if (actual == null)
				actual = "NULL";
			if (expected == null)
				expected = "NULL";
			TestLogger.logError("[VP Fail] : " + msg + " [Expected: "
					+ expected + ", Actual: " + actual + "]");
			if(TestBase.driver != null){				
				SeleniumUtility.takeScreenShot();
			}
			throw e;
		}
	}

	public void assertEquals(Object actual, Object expected, String msg)
			throws Throwable {
		try {
			Assert.assertEquals(actual, expected, msg);
			TestLogger.logSuccess("[VP Pass] : " + msg);
		} catch (Throwable e) {
			TestLogger.logError("[VP Fail] : " + msg);
			if(TestBase.driver != null){				
				SeleniumUtility.takeScreenShot();
			}
			throw e;
		}
	}

	/*
	 * In verify methods, if VP condition is false, the testcase will not stop.
	 * Following steps will continue, the flag testResult will be set to false
	 * to tell that some "verify" VP is failed. testResultFinalize() method will
	 * use the flag testResult to fail the case in the end
	 */
	
	public void verifyTrue(boolean condition, String msg)
		throws Throwable {
		try {
			Assert.assertTrue(condition);
			TestLogger.logSuccess("[VP Pass] : " + msg);
		} catch (Throwable e) {
			TestLogger.logError("[VP Fail] : " + msg);
			if(TestBase.driver != null){				
				SeleniumUtility.takeScreenShot();
			}
			testResult = false;
		}
	}

	// Verify if condition false
	public void verifyFalse(boolean condition, String msg)
		throws Throwable {
		try {
			Assert.assertFalse(condition);
			TestLogger.logSuccess("[VP Pass] : " + msg);
		} catch (Throwable e) {
			TestLogger.logError("[VP Fail] : " + msg);
			if(TestBase.driver != null){				
				SeleniumUtility.takeScreenShot();
			}
			testResult = false;
		}
	}
	
	// Verify if two String is equal
	public void verifyStringEquals(String actual, String expected, String msg)
		throws Throwable {
		try {
			Assert.assertEquals(actual, expected);
			TestLogger.logSuccess("[VP Pass] : " + msg + " [ Expected: "
					+ expected + ", Actual: " + actual + "]");
		} catch (Throwable e) {
			if (actual == null)
				actual = "NULL";
			if (expected == null)
				expected = "NULL";
			TestLogger.logError("[VP Fail] : " + msg + " [Expected: "
					+ expected + ", Actual: " + actual + "]");
			if(TestBase.driver != null){				
				SeleniumUtility.takeScreenShot();
			}
			testResult = false;
		}
	}

	// Verify the object equation
	public void verifyEquals(Object actual, Object expected, String msg)
		throws Throwable {
		try {
			Assert.assertEquals(actual, expected);
			TestLogger.logSuccess("[VP Pass] : " + msg);
		} catch (Throwable e) {
			TestLogger.logError("[VP Fail] : " + msg);
			if(TestBase.driver != null){				
				SeleniumUtility.takeScreenShot();
			}
			testResult = false;
		}
	}
}
