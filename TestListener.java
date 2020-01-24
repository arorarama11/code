package com.psd2.logger;

import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import com.psd2.logger.TestLogger;


/**
 * Class Description - This provides mechanism to perform certain actions when Test Case is Passed or Failed
 * 
 * @author alok_kumar
 * @version 1.0
 * @todo
 * 
 */
public class TestListener extends TestListenerAdapter {

	@Override
	public void onTestSuccess(ITestResult tr) {
		TestLogger.logSuccess("Test: " + tr.getMethod().getMethodName() + " PASSED");
	}

	@Override
	public void onTestFailure(ITestResult tr) {
		TestLogger.logError("Test: " + tr.getMethod().getMethodName()+ " FAILED");
	}
}
