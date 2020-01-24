package com.psd2.wrapper;

import java.util.ResourceBundle;

import com.psd2.core.SeleniumUtility;
import com.psd2.core.TestBase;
import com.psd2.logger.TestLogger;
import com.psd2.logger.TestVerification;
import com.psd2.utils.PropertyUtils;

public class ConsentOperation{

	public SeleniumUtility driverOps = new SeleniumUtility();
	public TestVerification testVP = new TestVerification();
	ResourceBundle bundle = TestBase.loadResourceBundle("com.psd2.testdata.ObjectRepository");

	@SuppressWarnings("static-access")
	public String authoriseAISPConsent(String url) throws Throwable{
		
		TestLogger.logStep("[Step 4-1] : Navigate to consent page and verify page title");	
		driverOps.navigateToURL(url);
		driverOps.explicitWaitWithClickableEle(bundle.getString("Obj_ClosePopup"),20);
		driverOps.click(bundle.getString("Obj_ClosePopup"));
		testVP.assertTrue(TestBase.driver.getTitle().equalsIgnoreCase(bundle.getString("homescreen_title").trim()),
				"Navigate to home screen successfully");
		TestLogger.logBlankLine();
		
		TestLogger.logStep("[Step 4-2] : Enter User and Password");
		driverOps.setText(bundle.getString("Obj_User"), PropertyUtils.getProperty("usr_name"));
		driverOps.setText(bundle.getString("Obj_Pass"), PropertyUtils.getProperty("pwd"));
		driverOps.click(bundle.getString("Obj_Login"));
	
		TestLogger.logStep("[Step 4-3] : Select Accounts");	
		//driverOps.explicitWaitWithClickableEle(bundle.getString("Obj_SelectAccount"),30);
		driverOps.clickByJS(bundle.getString("Obj_SelectAccount"));
		Thread.sleep(1000);
		driverOps.pressKey("PAGE_DOWN");
		Thread.sleep(1000);
		driverOps.takeScreenShot();

		TestLogger.logStep("[Step 4-4] : Confirm and autorize Consent");
		driverOps.click(bundle.getString("Obj_ConfirmConsent"));
		driverOps.waitForAuthCode();
		String authCode = driverOps.getURL().substring(driverOps.getURL().indexOf("code=")+5, driverOps.getURL().indexOf("&id_token"));
		TestLogger.logVariable("Auth Code : " + authCode);
		TestLogger.logBlankLine();
		
		return authCode;
	}
	
	@SuppressWarnings("static-access")
	public void rejectAISPConsent(String url) throws Throwable{
		
		TestLogger.logStep("[Step 4-1] : Navigate to consent page and verify page title");	
		driverOps.navigateToURL(url);
		driverOps.explicitWaitWithClickableEle(bundle.getString("Obj_ClosePopup"),20);
		driverOps.click(bundle.getString("Obj_ClosePopup"));
		testVP.assertTrue(TestBase.driver.getTitle().equalsIgnoreCase(bundle.getString("homescreen_title").trim()),
				"Navigate to home screen successfully");
		TestLogger.logBlankLine();
		
		TestLogger.logStep("[Step 4-2] : Enter User and Password");
		driverOps.setText(bundle.getString("Obj_User"), PropertyUtils.getProperty("usr_name"));
		driverOps.setText(bundle.getString("Obj_Pass"), PropertyUtils.getProperty("pwd"));
		driverOps.click(bundle.getString("Obj_Login"));
	
		TestLogger.logStep("[Step 4-3] : Select Accounts");
		driverOps.clickByJS(bundle.getString("Obj_SelectAccount"));
		driverOps.takeScreenShot();

		TestLogger.logStep("[Step 4-4] : Cancel Consent");
		driverOps.click(bundle.getString("Obj_CancelConsent"));		
		driverOps.click(bundle.getString("Obj_ConfirmCancelConsent"));		
		driverOps.waitForPageLoad();
		
		TestLogger.logBlankLine();		
	}
	
	@SuppressWarnings("static-access")
	public String authorisePISPConsent(String url, boolean addDebtorAccountFlag) throws Throwable{
		
		TestLogger.logStep("[Step 4-1] : Navigate to consent page and verify page title");	
		driverOps.navigateToURL(url);
		driverOps.explicitWaitWithClickableEle(bundle.getString("Obj_ClosePopup"),20);
		driverOps.click(bundle.getString("Obj_ClosePopup"));
		testVP.assertTrue(TestBase.driver.getTitle().equalsIgnoreCase(bundle.getString("homescreen_title").trim()),
				"Navigate to home screen successfully");
		TestLogger.logBlankLine();
		
		TestLogger.logStep("[Step 4-2] : Enter User and Password");
		driverOps.setText(bundle.getString("Obj_User"), PropertyUtils.getProperty("usr_name"));
		driverOps.setText(bundle.getString("Obj_Pass"), PropertyUtils.getProperty("pwd"));
		driverOps.click(bundle.getString("Obj_Login"));
		
		if (addDebtorAccountFlag){
			TestLogger.logStep("[Step 4-3] : Select Accounts");
			driverOps.clickByJS(bundle.getString("Obj_SelectAccountToPay"));
			driverOps.takeScreenShot();
			
			TestLogger.logStep("[Step 4-4] : Confirm and autorize Consent");
			driverOps.click(bundle.getString("Obj_ConfirmPayment"));
		}
			
		driverOps.waitForAuthCode();
		
		String authCode = driverOps.getURL().substring(driverOps.getURL().indexOf("code=")+5, driverOps.getURL().indexOf("&id_token"));
		TestLogger.logVariable("Auth Code : " + authCode);
		
		TestLogger.logBlankLine();
		
		return authCode;
	}
	
	public void rejectPISPConsent(String url) throws Throwable{
		
		TestLogger.logStep("[Step 4-1] : Navigate to consent page and verify page title");	
		driverOps.navigateToURL(url);
		driverOps.explicitWaitWithClickableEle(bundle.getString("Obj_ClosePopup"),20);
		driverOps.click(bundle.getString("Obj_ClosePopup"));
		testVP.assertTrue(TestBase.driver.getTitle().equalsIgnoreCase(bundle.getString("homescreen_title").trim()),
				"Navigate to home screen successfully");
		TestLogger.logBlankLine();
		
		TestLogger.logStep("[Step 4-2] : Enter User and Password");
		driverOps.setText(bundle.getString("Obj_User"), PropertyUtils.getProperty("usr_name"));
		driverOps.setText(bundle.getString("Obj_Pass"), PropertyUtils.getProperty("pwd"));
		driverOps.click(bundle.getString("Obj_Login"));
		driverOps.waitForPageLoad();
		
		TestLogger.logStep("[Step 4-4] : Reject Consent");
		driverOps.click(bundle.getString("Obj_CancelPISPConsent"));		
		driverOps.click(bundle.getString("Obj_ConfirmPISPCancelConsent"));
		driverOps.waitForPageLoad();
				
		TestLogger.logBlankLine();
	}
	
	@SuppressWarnings("static-access")
	public String authoriseCISPConsent(String url) throws Throwable{
		
		TestLogger.logStep("[Step 4-1] : Navigate to consent page and verify page title");	
		driverOps.navigateToURL(url);
		driverOps.explicitWaitWithClickableEle(bundle.getString("Obj_ClosePopup"),20);
		driverOps.click(bundle.getString("Obj_ClosePopup"));
		testVP.assertTrue(TestBase.driver.getTitle().equalsIgnoreCase(bundle.getString("homescreen_title").trim()),
				"Navigate to home screen successfully");
		TestLogger.logBlankLine();
		
		TestLogger.logStep("[Step 4-2] : Enter User and Password");
		driverOps.setText(bundle.getString("Obj_User"), PropertyUtils.getProperty("usr_name"));
		driverOps.setText(bundle.getString("Obj_Pass"), PropertyUtils.getProperty("pwd"));
		driverOps.click(bundle.getString("Obj_Login"));		

		TestLogger.logStep("[Step 4-3] : Confirm and autorize Consent");
		driverOps.clickByJS(bundle.getString("Obj_AcceptConsent"));
		driverOps.click(bundle.getString("Obj_ConfirmCISPConsent"));
		driverOps.takeScreenShot();	
		driverOps.waitForAuthCode();
		String authCode = driverOps.getURL().substring(driverOps.getURL().indexOf("code=")+5, driverOps.getURL().indexOf("&id_token"));
		TestLogger.logVariable("Auth Code : " + authCode);
		
		TestLogger.logBlankLine();
		
		return authCode;
	}
	
	@SuppressWarnings("static-access")
	public void rejectCISPConsent(String url) throws Throwable{
		
		TestLogger.logStep("[Step 4-1] : Navigate to consent page and verify page title");	
		driverOps.navigateToURL(url);
		driverOps.explicitWaitWithClickableEle(bundle.getString("Obj_ClosePopup"),20);
		driverOps.click(bundle.getString("Obj_ClosePopup"));
		testVP.assertTrue(TestBase.driver.getTitle().equalsIgnoreCase(bundle.getString("homescreen_title").trim()),
				"Navigate to home screen successfully");
		TestLogger.logBlankLine();
		
		TestLogger.logStep("[Step 4-2] : Enter User and Password");
		driverOps.setText(bundle.getString("Obj_User"), PropertyUtils.getProperty("usr_name"));
		driverOps.setText(bundle.getString("Obj_Pass"), PropertyUtils.getProperty("pwd"));
		driverOps.click(bundle.getString("Obj_Login"));
		driverOps.waitForPageLoad();
	
		TestLogger.logStep("[Step 4-3] : Cancel Consent");
		driverOps.explicitWaitWithClickableEle(bundle.getString("Obj_CancelCISPConsent"),20);
		driverOps.clickByJS(bundle.getString("Obj_CancelCISPConsent"));	
		driverOps.explicitWaitWithClickableEle(bundle.getString("Obj_ConfirmCancelCISPConsent"),20);
		driverOps.click(bundle.getString("Obj_ConfirmCancelCISPConsent"));
		driverOps.takeScreenShot();
		driverOps.waitForPageLoad();
		
		TestLogger.logBlankLine();		
	}
	
	@SuppressWarnings("static-access")
	public String authoriseAISPConsentWithSpecificAccounts(String url, String accounts) throws Throwable{
		
		TestLogger.logStep("[Step 4-1] : Navigate to consent page and verify page title");	
		driverOps.navigateToURL(url);
		driverOps.explicitWaitWithClickableEle(bundle.getString("Obj_ClosePopup"),20);
		driverOps.click(bundle.getString("Obj_ClosePopup"));
		testVP.assertTrue(TestBase.driver.getTitle().equalsIgnoreCase(bundle.getString("homescreen_title").trim()),
				"Navigate to home screen successfully");
		TestLogger.logBlankLine();
		
		TestLogger.logStep("[Step 4-2] : Enter User and Password");
		driverOps.setText(bundle.getString("Obj_User"), PropertyUtils.getProperty("usr_name"));
		driverOps.setText(bundle.getString("Obj_Pass"), PropertyUtils.getProperty("pwd"));
		driverOps.click(bundle.getString("Obj_Login"));
		
		TestLogger.logStep("[Step 4-3] : Select Accounts");
		driverOps.clickByJS(accounts);
		driverOps.takeScreenShot();
		TestLogger.logStep("[Step 4-4] : Confirm and autorize Consent");
		driverOps.click(bundle.getString("Obj_ConfirmConsent"));		
		driverOps.waitForAuthCode();
		
		String authCode = driverOps.getURL().substring(driverOps.getURL().indexOf("code=")+5, driverOps.getURL().indexOf("&id_token"));
		TestLogger.logVariable("Auth Code : " + authCode);
		
		TestLogger.logBlankLine();
		
		return authCode;
	}
	
	public String renewAISPConsent(String url, boolean cancelConsent) throws Throwable{
		
		TestLogger.logStep("[Step 4-1] : Navigate to consent page and verify page title");	
		driverOps.navigateToURL(url);
		driverOps.explicitWaitWithClickableEle(bundle.getString("Obj_ClosePopup"),20);
		driverOps.click(bundle.getString("Obj_ClosePopup"));
		testVP.assertTrue(TestBase.driver.getTitle().equalsIgnoreCase(bundle.getString("homescreen_title").trim()),
				"Navigate to home screen successfully");
		TestLogger.logBlankLine();
		
		TestLogger.logStep("[Step 4-2] : Enter User and Password");
		driverOps.setText(bundle.getString("Obj_User"), PropertyUtils.getProperty("usr_name"));
		driverOps.setText(bundle.getString("Obj_Pass"), PropertyUtils.getProperty("pwd"));
		if(cancelConsent){
			driverOps.click(bundle.getString("Obj_BackToThirdParty"));
			return null;
		}
		else{
			driverOps.click(bundle.getString("Obj_Login"));		
		}
		driverOps.waitForAuthCode();
		String authCode = driverOps.getURL().substring(driverOps.getURL().indexOf("code=")+5, driverOps.getURL().indexOf("&id_token"));
		TestLogger.logVariable("Auth Code : " + authCode);
		TestLogger.logBlankLine();
		
		return authCode;
	}
	
	public String invalidPayerAccont(String url) throws Throwable{
		
		TestLogger.logStep("[Step 4-1] : Navigate to consent page and verify page title");	
		driverOps.navigateToURL(url);
		driverOps.explicitWaitWithClickableEle(bundle.getString("Obj_ClosePopup"),20);
		driverOps.click(bundle.getString("Obj_ClosePopup"));
		testVP.assertTrue(TestBase.driver.getTitle().equalsIgnoreCase(bundle.getString("homescreen_title").trim()),
				"Navigate to home screen successfully");
		TestLogger.logBlankLine();
		
		TestLogger.logStep("[Step 4-2] : Enter User and Password");
		driverOps.setText(bundle.getString("Obj_User"), PropertyUtils.getProperty("usr_name"));
		driverOps.setText(bundle.getString("Obj_Pass"), PropertyUtils.getProperty("pwd"));
		driverOps.click(bundle.getString("Obj_Login"));
		return "Error: "+driverOps.getText(bundle.getString("Obj_InvalidPayerAccount_ErrorMessage"));
	}
}