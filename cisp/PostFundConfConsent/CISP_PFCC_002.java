package com.psd2.tests.cisp.PostFundConfConsent;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.psd2.core.TestBase;
import com.psd2.logger.TestListener;
import com.psd2.logger.TestLogger;

/**
 * Class Description : Valid value of Scope which will be used to create the CCG Token 
 * @author Alok Kumar
 *
 */

@Listeners( { TestListener.class })
@Test(groups={"Regression"})
public class CISP_PFCC_002 extends TestBase
{
	@Test
	public void m_CISP_PFCC_002() throws Throwable{	
		
		TestLogger.logStep("[Step 1] : Create Client Credentials....");
		
		createClientCred.setBaseURL(apiConst.cc_endpoint);
		createClientCred.setScope("fundsconfirmations");
		createClientCred.submit();
		
		testVP.assertStringEquals(String.valueOf(createClientCred.getResponseStatusCode()),"200", 
				"Response Code is correct for client credetials");
		access_token = createClientCred.getAccessToken();
		TestLogger.logVariable("AccessToken : " + access_token);	
		TestLogger.logBlankLine();
		
		TestLogger.logStep("[Step 2] : Fund confirmation consent....");
		
		fundConfConsent.setBaseURL(apiConst.fc_endpoint);
		fundConfConsent.setHeadersString("Authorization:Bearer "+access_token);
		fundConfConsent.submit();
		
		testVP.assertStringEquals(String.valueOf(fundConfConsent.getResponseStatusCode()),"201", 
				"Response Code is correct for fund Confiramtion Consent when the scope is valid");
		
		consentId = fundConfConsent.getConsentId();
		TestLogger.logVariable("Fund Confirmation Consent : " + consentId);	
		TestLogger.logBlankLine();
		
		testVP.testResultFinalize();			

	}
}