package com.psd2.utils;

import java.util.HashMap;

import org.testng.annotations.BeforeTest;

import com.psd2.core.TestBase;
import com.psd2.logger.TestLogger;

public class API_E2E_Utility{
	
	TestBase tbase = new TestBase();
	API_Constant apiConst = new API_Constant();
	
	/**
	 * This method is used to generate and authenticate the end to end AISP consent with all permissions.
	 * @throws Throwable
	 */
	@BeforeTest(enabled=true)
	public void generateAISPConsent() throws Throwable{
		TestLogger.logStep("[Step 1-1] : Creating client credetials....");
		
		tbase.createClientCred.setBaseURL(apiConst.cc_endpoint);
		tbase.createClientCred.submit();
		
		tbase.testVP.assertStringEquals(String.valueOf(tbase.createClientCred.getResponseStatusCode()),"200", 
				"Response Code is correct for client credetials");
		tbase.access_token = tbase.createClientCred.getAccessToken();
		TestLogger.logVariable("AccessToken : " + tbase.access_token);
		API_Constant.setCc_AccessToken(tbase.access_token);
		TestLogger.logBlankLine();
		
		TestLogger.logStep("[Step 1-2] : Account SetUp....");
		
		tbase.accountSetup.setBaseURL(apiConst.as_endpoint);
		tbase.accountSetup.setHeadersString("Authorization:Bearer "+tbase.access_token);
		tbase.accountSetup.setAllPermission();
		tbase.accountSetup.submit();
		
		tbase.testVP.assertStringEquals(String.valueOf(tbase.accountSetup.getResponseStatusCode()),"201", 
				"Response Code is correct for Account SetUp");
		tbase.consentId = tbase.accountSetup.getConsentId();
		TestLogger.logVariable("Account Request Id : " + tbase.consentId);	
		API_Constant.setCc_AccountReqId(tbase.consentId);
		TestLogger.logBlankLine();
		
		TestLogger.logStep("[Step 1-3] : Request Object Creation....");
		
		tbase.reqObject.setBaseURL(apiConst.ro_endpoint);
		tbase.reqObject.setValueField(tbase.consentId);
		tbase.reqObject.submit();
		
		tbase.testVP.assertStringEquals(String.valueOf(tbase.reqObject.getResponseStatusCode()),"200", 
				"Response Code is correct for request object creation");
		tbase.outId = tbase.reqObject.getOutId();
		TestLogger.logVariable("Request Object Out Id : " + tbase.outId);
		TestLogger.logBlankLine();
		
		TestLogger.logStep("[Step 1-4] : Go to URL and authenticate consent");	
		tbase.redirecturl = apiConst.aispconsent_URL.replace("#token_RequestGeneration#", tbase.outId);
		API_Constant.setConsentURL(tbase.redirecturl);
		tbase.startDriverInstance();
		tbase.authCode = tbase.consentOps.authoriseAISPConsent(tbase.redirecturl);		
		tbase.closeDriverInstance();
		TestLogger.logBlankLine();

		TestLogger.logStep("[Step 1-5] : Get access and refresh token");	
		tbase.accesstoken.setBaseURL(apiConst.at_endpoint);
		tbase.accesstoken.setAuthCode(tbase.authCode);
		tbase.accesstoken.submit();
		
		tbase.testVP.assertStringEquals(String.valueOf(tbase.accesstoken.getResponseStatusCode()),"200", 
				"Response Code is correct for get access token request");	
		tbase.access_token = tbase.accesstoken.getAccessToken();
		tbase.refresh_token = tbase.accesstoken.getRefreshToken();
		TestLogger.logVariable("Access Token : " + tbase.access_token);
		TestLogger.logVariable("Refresh Token : " + tbase.refresh_token);
		TestLogger.logBlankLine();
		
		API_Constant.setAisp_AccessToken(tbase.access_token);
		API_Constant.setAisp_RefreshToken(tbase.refresh_token);
		
		TestLogger.logStep("[Step 1-6] : Get Accound Id");	
		tbase.getAccount.setBaseURL(apiConst.account_endpoint);
		tbase.getAccount.setHeadersString("Authorization:Bearer "+tbase.access_token);
		tbase.getAccount.submit();
		
		tbase.testVP.assertStringEquals(String.valueOf(tbase.getAccount.getResponseStatusCode()),"200", 
				"Response Code is correct for get accounts request");	
		
		tbase.accountId = tbase.getAccount.getAccountId();
		TestLogger.logVariable("Account Id : " + tbase.accountId);
		TestLogger.logBlankLine();	
		
		API_Constant.setAisp_AccountId(tbase.accountId);
		API_Constant.setAisp_EmptyAccountId(tbase.getAccount.getResponseNodeStringByPath("Data.Account.AccountId[2]"));
		API_Constant.setAisp_secondAccountId(tbase.getAccount.getResponseNodeStringByPath("Data.Account.AccountId[1]"));
	}

	/**
	 * This method is used to generate and authenticate the end to end CISP consent.
	 * @throws Throwable
	 */
	@BeforeTest(enabled=true)
	public void generateCISPConsent()throws Throwable{
		
		TestLogger.logStep("[Step 1-1] : Creating client credetials....");
		
		tbase.createClientCred.setBaseURL(apiConst.cc_endpoint);
		tbase.createClientCred.setScope("fundsconfirmations");
		tbase.createClientCred.submit();
		
		tbase.testVP.verifyStringEquals(String.valueOf(tbase.createClientCred.getResponseStatusCode()),"200", 
				"Response Code is correct for client credetials");
		tbase.access_token = tbase.createClientCred.getAccessToken();
		TestLogger.logVariable("AccessToken : " + tbase.access_token);
		API_Constant.setCisp_CC_AccessToken(tbase.access_token);
		TestLogger.logBlankLine();
		
		TestLogger.logStep("[Step 1-2] : Fund confirmation consent....");
		
		tbase.fundConfConsent.setBaseURL(apiConst.fc_endpoint);
		tbase.fundConfConsent.setHeadersString("Authorization:Bearer "+tbase.access_token);
		tbase.fundConfConsent.submit();
		
		tbase.testVP.verifyStringEquals(String.valueOf(tbase.fundConfConsent.getResponseStatusCode()),"201", 
				"Response Code is correct for Fund confirmation consent");
		tbase.consentId = tbase.fundConfConsent.getConsentId();
		TestLogger.logVariable("Consent Id : " + tbase.consentId);	
		TestLogger.logBlankLine();
		API_Constant.setConsentId(tbase.consentId);
		
		TestLogger.logStep("[Step 1-3] : Request Object Creation....");
		
		tbase.reqObject.setBaseURL(apiConst.ro_endpoint);
		tbase.reqObject.setValueField(tbase.consentId);
		tbase.reqObject.setScopeField("fundsconfirmations");
		tbase.reqObject.submit();
		
		tbase.testVP.verifyStringEquals(String.valueOf(tbase.reqObject.getResponseStatusCode()),"200", 
				"Response Code is correct for request object creation");
		tbase.outId = tbase.reqObject.getOutId();
		TestLogger.logVariable("Request Object Out Id : " + tbase.outId);
		TestLogger.logBlankLine();
	
		TestLogger.logStep("[Step 1-4] : Go to URL and authenticate consent");	
		tbase.redirecturl = apiConst.cispconsent_URL.replace("#token_RequestGeneration#", tbase.outId);
		tbase.startDriverInstance();
		tbase.authCode = tbase.consentOps.authoriseCISPConsent(tbase.redirecturl);		
		
		tbase.closeDriverInstance();
		TestLogger.logBlankLine();

		TestLogger.logStep("[Step 1-5] : Get access and refresh token");	
		tbase.accesstoken.setBaseURL(apiConst.at_endpoint);
		tbase.accesstoken.setAuthCode(tbase.authCode);
		tbase.accesstoken.submit();
		
		tbase.testVP.verifyStringEquals(String.valueOf(tbase.accesstoken.getResponseStatusCode()),"200", 
				"Response Code is correct for get access token request");	
		tbase.access_token = tbase.accesstoken.getAccessToken();
		tbase.refresh_token = tbase.accesstoken.getRefreshToken();
		TestLogger.logVariable("Access Token : " + tbase.access_token);
		TestLogger.logVariable("Refresh Token : " + tbase.refresh_token);
		
		API_Constant.setCisp_AccessToken(tbase.access_token);
		API_Constant.setCisp_RefreshToken(tbase.refresh_token);

		TestLogger.logBlankLine();	
	}
	
	/**
	 * This method is used to create and authenticate AISP consent based on different conditions
	 * @param custPermissions : Specific permission with which consent should create
	 * @param expireConsentFlag : set true of false if want to expire the consent or not respectively
	 * @param comp_back_to_back : set true of false if want to perform account setup and get account request with v2.0 both
	 * @param comp_back_to_next : set true of false if want to perform account setup and get account request with v2.0 and v3.0 respectively
	 * @param comp_next_to_back : set true of false if want to perform account setup and get account request with v3.0 and v2.0 respectively
	 * @param specificAccount : Specific account with which consent should create 
	 * @param rejectConsentFlag : set true of false if want to reject the consent or not
	 * @param returnAuthCode : set true of false if want to return the auth code only
	 * @return : Return a map with consent details
	 * @throws Throwable
	 */
	public HashMap<String, String> generateAISPConsent(String custPermissions, boolean expireConsentFlag, 
			boolean comp_back_to_back, boolean comp_back_to_next,boolean comp_next_to_back,
			String specificAccount, boolean rejectConsentFlag, boolean returnAuthCode) throws Throwable{
		
		HashMap<String, String> map = new HashMap<String, String>();
		
		TestLogger.logStep("[Step 1-1] : Creating client credetials....");
		
		tbase.createClientCred.setBaseURL(apiConst.cc_endpoint);
		tbase.createClientCred.submit();
		
		tbase.testVP.assertStringEquals(String.valueOf(tbase.createClientCred.getResponseStatusCode()),"200", 
				"Response Code is correct for client credetials");
		tbase.access_token = tbase.createClientCred.getAccessToken();
		map.put("cc_access_token", tbase.access_token);
		TestLogger.logVariable("AccessToken : " + tbase.access_token);
		TestLogger.logBlankLine();
		
		TestLogger.logStep("[Step 1-2] : Account SetUp....");
		if(comp_back_to_back || comp_back_to_next){
			tbase.accountSetup.setBaseURL(apiConst.backcomp_as_endpoint);
		}
		else {
			tbase.accountSetup.setBaseURL(apiConst.as_endpoint);
		}
		tbase.accountSetup.setHeadersString("Authorization:Bearer "+tbase.access_token);
		if(custPermissions != null){
			tbase.accountSetup.setCustomPermissions(custPermissions);;
		}else{
			tbase.accountSetup.setAllPermission();
		}
		if(expireConsentFlag){
			String date = Misc.currentDate("yyyy-MM-dd");
			String time = Misc.updatedCurrentTime(-5, -27);
			tbase.accountSetup.setExpirationDateTime(date +"T"+ time +"+00:00");
		}
		tbase.accountSetup.submit();
		
		tbase.testVP.assertStringEquals(String.valueOf(tbase.accountSetup.getResponseStatusCode()),"201", 
				"Response Code is correct for Account SetUp");
			
		tbase.consentId = tbase.accountSetup.getConsentId();
		map.put("consentId", tbase.consentId);
		TestLogger.logVariable("consentId : " + tbase.consentId);	
		TestLogger.logBlankLine();
		
		TestLogger.logStep("[Step 1-3] : Request Object Creation....");
		
		tbase.reqObject.setBaseURL(apiConst.ro_endpoint);
		tbase.reqObject.setValueField(tbase.consentId);
		tbase.reqObject.submit();
		
		tbase.testVP.assertStringEquals(String.valueOf(tbase.reqObject.getResponseStatusCode()),"200", 
				"Response Code is correct for request object creation");
		tbase.outId = tbase.reqObject.getOutId();
		TestLogger.logVariable("Request Object Out Id : " + tbase.outId);
		TestLogger.logBlankLine();
		
		TestLogger.logStep("[Step 1-4] : Go to URL and authenticate consent");	
		tbase.redirecturl = apiConst.aispconsent_URL.replace("#token_RequestGeneration#", tbase.outId);
		map.put("ConsentURL", tbase.redirecturl);
		tbase.startDriverInstance();
		if(comp_back_to_back || comp_back_to_next || comp_next_to_back){
			tbase.authCode = tbase.consentOps.authoriseAISPConsent(tbase.redirecturl);	
		}
		else if(rejectConsentFlag){
			tbase.consentOps.rejectAISPConsent(tbase.redirecturl);
			tbase.closeDriverInstance();
			return map;
		}
		else if(returnAuthCode){
			tbase.authCode = tbase.consentOps.authoriseAISPConsent(tbase.redirecturl);
			map.put("authCode", tbase.authCode);
			tbase.closeDriverInstance();
			return map;
		}else if(specificAccount!=null)
		{
			tbase.authCode = tbase.consentOps.authoriseAISPConsentWithSpecificAccounts(tbase.redirecturl,specificAccount);
		}
		else{
			tbase.authCode = tbase.consentOps.authoriseAISPConsent(tbase.redirecturl);		
		}
		tbase.closeDriverInstance();
		TestLogger.logBlankLine();

		TestLogger.logStep("[Step 1-5] : Get access and refresh token");	
		tbase.accesstoken.setBaseURL(apiConst.at_endpoint);
		tbase.accesstoken.setAuthCode(tbase.authCode);
		tbase.accesstoken.submit();
		
		tbase.testVP.assertStringEquals(String.valueOf(tbase.accesstoken.getResponseStatusCode()),"200", 
				"Response Code is correct for get access token request");	
		tbase.access_token = tbase.accesstoken.getAccessToken();
		tbase.refresh_token = tbase.accesstoken.getRefreshToken();
		map.put("api_access_token", tbase.access_token);
		map.put("api_refresh_token", tbase.refresh_token);

		TestLogger.logVariable("Access Token : " + tbase.access_token);
		TestLogger.logVariable("Refresh Token : " + tbase.refresh_token);
		TestLogger.logBlankLine();
		
		TestLogger.logStep("[Step 1-6] : Get Accound Id");
		if(comp_back_to_back || comp_next_to_back){
			tbase.getAccount.setBaseURL(apiConst.backcomp_account_endpoint);
		}
		else {
			tbase.getAccount.setBaseURL(apiConst.account_endpoint);
		}
		tbase.getAccount.setHeadersString("Authorization:Bearer "+tbase.access_token);
		tbase.getAccount.submit();
		
		tbase.testVP.assertStringEquals(String.valueOf(tbase.getAccount.getResponseStatusCode()),"200", 
				"Response Code is correct for get accounts request");	
		
		tbase.accountId = tbase.getAccount.getAccountId();
		map.put("accountId", tbase.accountId);
		TestLogger.logVariable("Account Id : " + tbase.accountId);
		TestLogger.logBlankLine();
		if(expireConsentFlag){
			TestLogger.logInfo("Waiting for consent to expire...");
			Thread.sleep(200000);
		}
		return map;
	}
	
	/**
	 * This method is used to perform Account Set up only
	 * @param backComp : set true of false if want to perform account set up with v1.1
	 * @param custPermissions : Specific permission with which consent should create
	 * @return Return a map with consent details
	 * @throws Throwable
	 */
	public HashMap<String, String> performAccountSetUp(boolean backComp, String custPermissions) throws Throwable{
		
		HashMap<String, String> map = new HashMap<String, String>();
		
		TestLogger.logStep("[Step 1-1] : Creating client credetials....");
		
		tbase.createClientCred.setBaseURL(apiConst.cc_endpoint);
		tbase.createClientCred.submit();
		
		tbase.testVP.assertStringEquals(String.valueOf(tbase.createClientCred.getResponseStatusCode()),"200", 
				"Response Code is correct for client credetials");
		tbase.access_token = tbase.createClientCred.getAccessToken();
		map.put("cc_access_token", tbase.access_token);
		TestLogger.logVariable("AccessToken : " + tbase.access_token);	
		TestLogger.logBlankLine();
		
		TestLogger.logStep("[Step 1-2] : Account SetUp....");
		if(backComp){
			tbase.accountSetup.setBaseURL(apiConst.backcomp_as_endpoint);
		}
		else {
			tbase.accountSetup.setBaseURL(apiConst.as_endpoint);
		}
		tbase.accountSetup.setHeadersString("Authorization:Bearer "+tbase.access_token);
		if(custPermissions != null){
			tbase.accountSetup.setCustomPermissions(custPermissions);;
		}else{
			tbase.accountSetup.setAllPermission();
		}		tbase.accountSetup.submit();
		
		tbase.testVP.assertStringEquals(String.valueOf(tbase.accountSetup.getResponseStatusCode()),"201", 
				"Response Code is correct for Account SetUp");
		tbase.consentId = tbase.accountSetup.getConsentId();
		 
		map.put("consentId", tbase.consentId);
		TestLogger.logVariable("consentId : " + tbase.consentId);	
		TestLogger.logBlankLine();
		return map;
	}
	
	/**
	 * This method is used to create and authenticate the CISP consent based on certain conditions
	 * @param returnFundConfConsent : set true of false if want to return the fund confirmation consent only
	 * @param rejectConsentFlag : set true of false if want to reject the CISP consent 
	 * @param expireConsentFlag : set true of false if want to expire the CISP consent 
	 * @return Return a map with consent details
	 * @throws Throwable
	 */
	public HashMap<String, String> generateCISPConsent(boolean returnFundConfConsent, boolean rejectConsentFlag,
			boolean expireConsentFlag) throws Throwable{
		
		HashMap<String, String> map = new HashMap<String, String>();
		
		TestLogger.logStep("[Step 1-1] : Creating client credetials....");
		
		tbase.createClientCred.setBaseURL(apiConst.cc_endpoint);
		tbase.createClientCred.setScope("fundsconfirmations");
		tbase.createClientCred.submit();
		
		tbase.testVP.assertStringEquals(String.valueOf(tbase.createClientCred.getResponseStatusCode()),"200", 
				"Response Code is correct for client credetials");
		tbase.access_token = tbase.createClientCred.getAccessToken();
		map.put("cc_access_token", tbase.access_token);
		TestLogger.logVariable("AccessToken : " + tbase.access_token);
		TestLogger.logBlankLine();
		
		TestLogger.logStep("[Step 1-2] : Fund confirmation consent....");
		
		tbase.fundConfConsent.setBaseURL(apiConst.fc_endpoint);
		tbase.fundConfConsent.setHeadersString("Authorization:Bearer "+tbase.access_token);
		
		if(expireConsentFlag){
			String date = Misc.currentDate("yyyy-MM-dd");
			String time = Misc.updatedCurrentTime(-5, -28);
			tbase.fundConfConsent.setExpirationDateTime(date +"T"+ time +"+00:00");
		}
		tbase.fundConfConsent.submit();
		
		tbase.testVP.assertStringEquals(String.valueOf(tbase.fundConfConsent.getResponseStatusCode()),"201", 
				"Response Code is correct for Fund confirmation consent");
		tbase.consentId = tbase.fundConfConsent.getConsentId();
		map.put("consentId", tbase.consentId);
		map.put("identification", tbase.fundConfConsent.getIdentification());
		map.put("schemeName", tbase.fundConfConsent.getSchemeName());
		map.put("name", tbase.fundConfConsent.getName());
		map.put("secondaryIdentification", tbase.fundConfConsent.getSecondaryIdentification());

		TestLogger.logVariable("Consent Id : " + tbase.consentId);	
		TestLogger.logBlankLine();
		if(returnFundConfConsent){
			return map ;
		}
		
		TestLogger.logStep("[Step 1-3] : Request Object Creation....");
		
		tbase.reqObject.setBaseURL(apiConst.ro_endpoint);
		tbase.reqObject.setValueField(tbase.consentId);
		tbase.reqObject.setScopeField("fundsconfirmations");
		tbase.reqObject.submit();
		
		tbase.testVP.assertStringEquals(String.valueOf(tbase.reqObject.getResponseStatusCode()),"200", 
				"Response Code is correct for request object creation");
		tbase.outId = tbase.reqObject.getOutId();
		TestLogger.logVariable("Request Object Out Id : " + tbase.outId);
		TestLogger.logBlankLine();
	
		TestLogger.logStep("[Step 1-4] : Go to URL and authenticate consent");	
		tbase.redirecturl = apiConst.cispconsent_URL.replace("#token_RequestGeneration#", tbase.outId);
		tbase.startDriverInstance();
		if(rejectConsentFlag){
			tbase.consentOps.rejectCISPConsent(tbase.redirecturl);
			tbase.closeDriverInstance();
			return map;
		}
		else{
			tbase.authCode = tbase.consentOps.authoriseCISPConsent(tbase.redirecturl);		
		}
		tbase.closeDriverInstance();
		TestLogger.logBlankLine();

		TestLogger.logStep("[Step 1-5] : Get access and refresh token");	
		tbase.accesstoken.setBaseURL(apiConst.at_endpoint);
		tbase.accesstoken.setAuthCode(tbase.authCode);
		tbase.accesstoken.submit();
		
		tbase.testVP.assertStringEquals(String.valueOf(tbase.accesstoken.getResponseStatusCode()),"200", 
				"Response Code is correct for get access token request");	
		tbase.access_token = tbase.accesstoken.getAccessToken();
		tbase.refresh_token = tbase.accesstoken.getRefreshToken();
		TestLogger.logVariable("Access Token : " + tbase.access_token);
		TestLogger.logVariable("Refresh Token : " + tbase.refresh_token);
		map.put("cisp_access_token", tbase.access_token);
		TestLogger.logBlankLine();
		
		if(expireConsentFlag){
			TestLogger.logInfo("Waiting for consent to expire...");
			Thread.sleep(150000);
		}
		return map;
	}
		
	/**
	 * This method is used to create and authenticate the PISP consent based on certain conditions
	 * @param rejectConsentFlag : set true of false if want to reject the PISP consent
	 * @param endPoint : hit particular api based on the endpoint 
	 * @param returnConsent : set true of false if want to return the consent only
	 * @param returnPaymentId : set true of false if want to return PaymentId
	 * @return Return a map with consent details
	 * @throws Throwable
	 */
	
	public HashMap<String, String> generatePayments(boolean rejectConsentFlag, 
			String endPoint, boolean returnConsent, boolean returnPaymentId) throws Throwable{
		boolean debtorAccount=false;
		HashMap<String, String> map = new HashMap<String, String>();
		TestLogger.logStep("[Step 1-1] : Creating client credetials....");
		
		tbase.createClientCred.setBaseURL(apiConst.cc_endpoint);
		tbase.createClientCred.setScope("payments");
		tbase.createClientCred.submit();
		
		tbase.testVP.assertStringEquals(String.valueOf(tbase.createClientCred.getResponseStatusCode()),"200", 
				"Response Code is correct for client credetials");
		tbase.cc_token = tbase.createClientCred.getAccessToken();
		map.put("cc_access_token", tbase.cc_token);
		TestLogger.logVariable("AccessToken : " + tbase.cc_token);
		TestLogger.logBlankLine();
		
		if(endPoint.equals("Domestic-Payments")){
			TestLogger.logStep("[Step 1-2] : POST Domestic Payment Consent");
			tbase.paymentConsent.setBaseURL(apiConst.dpc_endpoint);
			tbase.paymentConsent.setHeadersString("Authorization:Bearer "+tbase.cc_token);		
			tbase.paymentConsent.submit();
			
			tbase.testVP.assertStringEquals(String.valueOf(tbase.paymentConsent.getResponseStatusCode()),"201", 
					"Response Code is correct for Post Domestic Payment Consent");
			tbase.consentId = tbase.paymentConsent.getConsentId();
			debtorAccount=tbase.paymentConsent.addDebtorAccount;
		}else if(endPoint.equals("Domestic-Scheduled-Payments")){
			TestLogger.logStep("[Step 1-2] : POST Domestic Scheduled Payment Consent");
			tbase.dspConsent.setBaseURL(apiConst.dspConsent_endpoint);
			tbase.dspConsent.setHeadersString("Authorization:Bearer "+tbase.cc_token);		
			tbase.dspConsent.submit();
			
			tbase.testVP.assertStringEquals(String.valueOf(tbase.dspConsent.getResponseStatusCode()),"201", 
					"Response Code is correct for Post Domestic Scheduled Payment Consent");
			tbase.consentId = tbase.dspConsent.getConsentId();
			debtorAccount=tbase.dspConsent.addDebtorAccount;
		}else if(endPoint.equals("Domestic-Standing-Orders")){
			TestLogger.logStep("[Step 1-2] : POST Domestic Standing Orders Consent");
			tbase.dStandingOrder.setBaseURL(apiConst.dsoConsent_endpoint);
			tbase.dStandingOrder.setHeadersString("Authorization:Bearer "+tbase.cc_token);		
			tbase.dStandingOrder.submit();
			
			tbase.testVP.assertStringEquals(String.valueOf(tbase.dStandingOrder.getResponseStatusCode()),"201", 
					"Response Code is correct for Post Domestic Standing Orders Consent");
			tbase.consentId = tbase.dStandingOrder.getConsentId();
			debtorAccount=tbase.dStandingOrder.addDebtorAccount;
		}else if(endPoint.equals("International-Payments")){
			TestLogger.logStep("[Step 1-2] : POST International Payment Consent");
			tbase.internationalPayment.setBaseURL(apiConst.iPaymentConsent_endpoint);
			tbase.internationalPayment.setHeadersString("Authorization:Bearer "+tbase.cc_token);		
			tbase.internationalPayment.submit();
			
			tbase.testVP.assertStringEquals(String.valueOf(tbase.internationalPayment.getResponseStatusCode()),"201", 
					"Response Code is correct for Post International Payment Consent");
			tbase.consentId = tbase.internationalPayment.getConsentId();
			debtorAccount=tbase.internationalPayment.removeDebtorAccount;
		}else if(endPoint.equals("International-Scheduled-Payments")){
			TestLogger.logStep("[Step 1-2] : POST International Scheduled Payment Consent");
			tbase.iScheduledPayment.setBaseURL(apiConst.iScheduledPaymentConsent_endpoint);
			tbase.iScheduledPayment.setHeadersString("Authorization:Bearer "+tbase.cc_token);
			tbase.iScheduledPayment.submit();
			
			tbase.testVP.assertEquals(String.valueOf(tbase.iScheduledPayment.getResponseStatusCode()), "201", 
					"Response Code is correct for POST International Scheduled Payment Consent");
			tbase.consentId = tbase.iScheduledPayment.getConsentId();
			debtorAccount=tbase.iScheduledPayment.removeDebtorAccount;
		}else if(endPoint.equals("File-Payments")){
			TestLogger.logStep("[Step 1-2] : POST File Payment Consent");
			tbase.filePayment.setBaseURL(apiConst.fPaymentConsent_endpoint);
			tbase.filePayment.setHeadersString("Authorization:Bearer "+tbase.cc_token);
			tbase.filePayment.submit();
			
			tbase.testVP.assertEquals(String.valueOf(tbase.filePayment.getResponseStatusCode()), "201", 
					"Response Code is correct for POST File Payment Consent");
			tbase.consentId = tbase.filePayment.getConsentId();
			map.put("idempotency_key", tbase.filePayment.getHeaderEntry("x-idempotency-key"));
			debtorAccount=tbase.filePayment.removeDebtorAccount;
			if(!returnConsent){
				TestLogger.logStep("[Step 1-2-1] : Upload file");	
				tbase.uploadFile.setBaseURL(apiConst.fPaymentConsent_endpoint+"/"+tbase.consentId+"/file");
				tbase.uploadFile.setHeadersString("Authorization:Bearer "+tbase.cc_token);
				tbase.uploadFile.setFileType(tbase.filePayment.getFileType());
				tbase.uploadFile.setIdempotencyKey(tbase.filePayment.getHeaderEntry("x-idempotency-key"));
				tbase.uploadFile.submit();
				
				tbase.testVP.assertStringEquals(String.valueOf(tbase.uploadFile.getResponseStatusCode()),"200", 
						"Response Code is correct for POST File Payment Consent file upload");	

		        TestLogger.logBlankLine();
			}
		}else {
			TestLogger.logError("Incorrect end point");
			return null;
		}
		map.put("consentId", tbase.consentId);
		TestLogger.logVariable("Consent Id : " + tbase.consentId);	
		if(returnConsent){
			return map;
		}
		TestLogger.logBlankLine();
		
		TestLogger.logStep("[Step 1-3] : Request Object Creation....");
		tbase.reqObject.setBaseURL(apiConst.ro_endpoint);
		tbase.reqObject.setValueField(tbase.consentId);
		tbase.reqObject.setScopeField("payments");
		tbase.reqObject.submit();
		
		tbase.testVP.assertStringEquals(String.valueOf(tbase.reqObject.getResponseStatusCode()),"200", "Response Code is correct for request object creation");
		tbase.outId = tbase.reqObject.getOutId();
		TestLogger.logVariable("Request Object Out Id : " + tbase.outId);
		TestLogger.logBlankLine();
		
		TestLogger.logStep("[Step 1-4] : Go to URL and authenticate consent");	
		tbase.redirecturl = apiConst.pispconsent_URL.replace("#token_RequestGeneration#", tbase.outId);
		tbase.startDriverInstance();
		if(rejectConsentFlag){
			tbase.consentOps.renewAISPConsent(tbase.redirecturl,true);	
			tbase.closeDriverInstance();
			Thread.sleep(2000);
			return map;
		}
		tbase.authCode = tbase.consentOps.authorisePISPConsent(tbase.redirecturl,debtorAccount);
		map.put("authCode", tbase.authCode);
		tbase.closeDriverInstance();
		TestLogger.logBlankLine();

		TestLogger.logStep("[Step 1-5] : Get access token");	
		tbase.accesstoken.setBaseURL(apiConst.at_endpoint);
		tbase.accesstoken.setAuthCode(tbase.authCode);
		tbase.accesstoken.submit();
		
		tbase.testVP.assertStringEquals(String.valueOf(tbase.accesstoken.getResponseStatusCode()),"200", 
				"Response Code is correct for get access token request");	
		tbase.access_token = tbase.accesstoken.getAccessToken();
		map.put("api_access_token", tbase.access_token);
		TestLogger.logVariable("Access Token : " + tbase.access_token);		
		TestLogger.logBlankLine();
	
		if(returnPaymentId){
			if(endPoint.equals("Domestic-Payments")){
				TestLogger.logStep("[Step 1-6] : POST Domestic Payment");
				tbase.paymentConsent.setBaseURL(apiConst.dps_endpoint);
				tbase.paymentConsent.setHeadersString("Authorization:Bearer "+tbase.access_token);
				tbase.paymentConsent.setConsentId(tbase.consentId);
				tbase.paymentConsent.submit();
				
				tbase.testVP.assertStringEquals(String.valueOf(tbase.paymentConsent.getResponseStatusCode()),"201", 
						"Response Code is correct for Post Domestic Payments");
				tbase.paymentId = tbase.paymentConsent.getPaymentId();
			}else if(endPoint.equals("Domestic-Scheduled-Payments")){
				TestLogger.logStep("[Step 1-6] : POST Domestic Scheduled Payment");
				tbase.dspConsent.setBaseURL(apiConst.dsp_endpoint);
				tbase.dspConsent.setHeadersString("Authorization:Bearer "+tbase.access_token);
				tbase.dspConsent.setConsentId(tbase.consentId);
				tbase.dspConsent.submit();
				
				tbase.testVP.assertStringEquals(String.valueOf(tbase.dspConsent.getResponseStatusCode()),"201", 
						"Response Code is correct for Post Domestic Scheduled Payment");
				tbase.paymentId = tbase.dspConsent.getPaymentId();
			}else if(endPoint.equals("Domestic-Standing-Orders")){
				TestLogger.logStep("[Step 1-6] : POST Domestic Standing Orders");
				tbase.dStandingOrder.setBaseURL(apiConst.dsoSubmission_endpoint);
				tbase.dStandingOrder.setHeadersString("Authorization:Bearer "+tbase.access_token);
				tbase.dStandingOrder.setConsentId(tbase.consentId);
				tbase.dStandingOrder.submit();
				
				tbase.testVP.assertStringEquals(String.valueOf(tbase.dStandingOrder.getResponseStatusCode()),"201", 
						"Response Code is correct for Post Domestic Standing Orders");
				tbase.paymentId = tbase.dStandingOrder.getPaymentId();
			}
			else if(endPoint.equals("International-Payments")){
				TestLogger.logStep("[Step 1-6] : POST International Payments");
				tbase.internationalPayment.setBaseURL(apiConst.iPaymentSubmission_endpoint);
				tbase.internationalPayment.setHeadersString("Authorization:Bearer "+tbase.access_token);
				tbase.internationalPayment.setConsentId(tbase.consentId);
				tbase.internationalPayment.submit();
				
				tbase.testVP.assertStringEquals(String.valueOf(tbase.internationalPayment.getResponseStatusCode()),"201", 
						"Response Code is correct for Post International Payments");
				tbase.paymentId = tbase.internationalPayment.getPaymentId();
			}else if(endPoint.equals("International-Scheduled-Payments")){
				TestLogger.logStep("[Step 1-6] : POST International Scheduled Payments");
				tbase.iScheduledPayment.setBaseURL(apiConst.iScheduledPaymentSubmission_endpoint);
				tbase.iScheduledPayment.setHeadersString("Authorization:Bearer "+tbase.access_token);
				tbase.iScheduledPayment.setConsentId(tbase.consentId);
				tbase.iScheduledPayment.submit();
				
				tbase.testVP.assertStringEquals(String.valueOf(tbase.iScheduledPayment.getResponseStatusCode()),"201", 
						"Response Code is correct for Post International Scheduled Payments");
				tbase.paymentId = tbase.iScheduledPayment.getPaymentId();
			}else if(endPoint.equals("File-Payments")){
				TestLogger.logStep("[Step 1-6] : POST File Payment");
				tbase.filePayment.setBaseURL(apiConst.fPaymentSubmission_endpoint);
				tbase.filePayment.setHeadersString("Authorization:Bearer "+tbase.access_token+", x-idempotency-key:"+PropertyUtils.getProperty("idempotency_key")+"."+Misc.generateString(3));
				tbase.filePayment.setConsentId(tbase.consentId);
				tbase.filePayment.submit();
				
				tbase.testVP.assertEquals(String.valueOf(tbase.filePayment.getResponseStatusCode()), "201", 
						"Response Code is correct for POST File Payment");
				tbase.paymentId = tbase.filePayment.getPaymentId();
			}
			map.put("paymentId", tbase.paymentId);
			TestLogger.logVariable("Payment Id : " + tbase.paymentId);	
		}
		return map;
	}
}