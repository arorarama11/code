package com.psd2.tests.cisp.PostFundConfConsent;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.psd2.core.TestBase;
import com.psd2.logger.TestListener;
import com.psd2.logger.TestLogger;

/**
 * Class Description : Verification of "ConsentId' field in the response
 * @author Alok Kumar
 *
 */

@Listeners( { TestListener.class })
@Test(groups={"Regression"})
public class CISP_PFCC_034 extends TestBase{	
	
	@Test
	public void m_CISP_PFCC_034() throws Throwable{	

		TestLogger.logStep("[Step 1] : Creating client credetials....");
		
		createClientCred.setBaseURL(apiConst.cc_endpoint);
		createClientCred.setScope("fundsconfirmations");
		createClientCred.submit();
		
		testVP.assertStringEquals(String.valueOf(createClientCred.getResponseStatusCode()),"200", 
				"Response Code is correct for client credetials");
		access_token = createClientCred.getAccessToken();
		TestLogger.logVariable("AccessToken : " + access_token);	
		TestLogger.logBlankLine();
		
		TestLogger.logStep("[Step 2] : Verification of 'ConsentId' field in the response");
		
		fundConfConsent.setBaseURL(apiConst.fc_endpoint);
		fundConfConsent.setHeadersString("Authorization:Bearer "+access_token);
		fundConfConsent.submit();
		
		testVP.assertStringEquals(String.valueOf(fundConfConsent.getResponseStatusCode()),"201", 
				"Response Code is correct for fund Confiramtion Consent");
		testVP.assertTrue(!String.valueOf(fundConfConsent.getResponseValueByPath("Data.ConsentId")).isEmpty(), 
				"ConsentId is present in the response and is not empty");
		
		consentId = fundConfConsent.getConsentId();
		testVP.assertTrue(consentId.length()<=128, "ConsentId field is less than 128 characters");
				
		TestLogger.logVariable("Fund Confirmation Consent : " + consentId);	
		TestLogger.logBlankLine();
		
		testVP.testResultFinalize();			

	}
}