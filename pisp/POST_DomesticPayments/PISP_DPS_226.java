package com.psd2.tests.pisp.POST_DomesticPayments;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.psd2.core.TestBase;
import com.psd2.logger.TestListener;
import com.psd2.logger.TestLogger;
import com.psd2.utils.API_Constant;

/**
 * Class Description : Verification of the values into MANDATORY Data/Initiation/InstructedAmount/Amount field starts with 200 for Currency as EUR/GBP
 * @author Rama Arora
 *
 */

@Listeners( { TestListener.class })
@Test(groups={"Regression"})
public class PISP_DPS_226 extends TestBase {	
	
	@Test
	public void m_PISP_DPS_226() throws Throwable{	
		
        TestLogger.logStep("[Step 1-1] : Creating client credetials....");
		
		createClientCred.setBaseURL(apiConst.cc_endpoint);
		createClientCred.setScope("payments");
		createClientCred.submit();
		
		testVP.assertStringEquals(String.valueOf(createClientCred.getResponseStatusCode()),"200","Response Code is correct for client credetials");
		cc_token = createClientCred.getAccessToken();
		TestLogger.logVariable("AccessToken : " + cc_token);
		API_Constant.setPisp_CC_AccessToken(cc_token);	
		TestLogger.logBlankLine();
		
		TestLogger.logStep("[Step 1-2] : Domestic Payment Consent SetUp....");
		
		paymentConsent.setBaseURL(apiConst.dpc_endpoint);
		paymentConsent.setHeadersString("Authorization:Bearer "+cc_token);
		paymentConsent.setAmount("200.12");
		paymentConsent.submit();
		
		testVP.assertStringEquals(String.valueOf(paymentConsent.getResponseStatusCode()),"201", "Response Code is correct for Domestic Payment Consent");
		consentId = paymentConsent.getConsentId();
		TestLogger.logVariable("Consent Id : " + consentId);
		TestLogger.logBlankLine();
		
		TestLogger.logStep("[Step 1-3] : Request Object Creation....");
		
		reqObject.setBaseURL(apiConst.ro_endpoint);
		reqObject.setValueField(consentId);
		reqObject.setScopeField("payments");
		reqObject.submit();
		
		testVP.assertStringEquals(String.valueOf(reqObject.getResponseStatusCode()),"200", "Response Code is correct for request object creation");
		outId = reqObject.getOutId();
		TestLogger.logVariable("Request Object Out Id : " + outId);
		TestLogger.logBlankLine();
		
		TestLogger.logStep("[Step 1-4] : Go to URL and authenticate consent");	
		redirecturl = apiConst.pispconsent_URL.replace("#token_RequestGeneration#", outId);
		startDriverInstance();
		authCode = consentOps.authorisePISPConsent(redirecturl,paymentConsent.addDebtorAccount);		
		closeDriverInstance();
		TestLogger.logBlankLine();

		TestLogger.logStep("[Step 1-5] : Get access token");	
		accesstoken.setBaseURL(apiConst.at_endpoint);
		accesstoken.setAuthCode(authCode);
		accesstoken.submit();
		
		testVP.assertStringEquals(String.valueOf(accesstoken.getResponseStatusCode()),"200", 
				"Response Code is correct for get access token request");	
		access_token = accesstoken.getAccessToken();
		TestLogger.logVariable("Access Token : " + access_token);		
		TestLogger.logBlankLine();
		
		TestLogger.logStep("[Step 1-6] : Domestic Payment ");	
		paymentConsent.setBaseURL(apiConst.dps_endpoint);
		paymentConsent.setHeadersString("Authorization:Bearer "+access_token);
		paymentConsent.setConsentId(consentId);
		paymentConsent.submit();
		
		testVP.assertStringEquals(String.valueOf(paymentConsent.getResponseStatusCode()),"201",
				"Response Code is correct for Domestic Payment URI when InstructedAmount/Amount as 200");

		testVP.assertStringEquals(String.valueOf(paymentConsent.getResponseValueByPath("Data.Status")), "Rejected",
				"Mandatory field Status as Rejected");
		
		testVP.assertTrue(paymentConsent.getResponseValueByPath("MultiAuthorisation") == null,
				"Mandatory field MultiAuthorisation is not present under Initiation block");
		
		TestLogger.logBlankLine();		
		testVP.testResultFinalize();		
	}
}
