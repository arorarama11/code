package com.psd2.tests.cisp.PostFundConfConsent;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.psd2.core.TestBase;
import com.psd2.logger.TestListener;
import com.psd2.logger.TestLogger;

/**
 * Class Description : Verification of CMA compliance for POST Funds Confirmation Consent URI 
 * @author Alok Kumar
 *
 */

@Listeners( { TestListener.class })
@Test(groups={"Regression","Sanity"})
public class CISP_PFCC_001 extends TestBase{	
	
	@Test
	public void m_CISP_PFCC_001() throws Throwable{	
		
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
				"Response Code is correct for fund Confiramtion Consent");
		testVP.assertStringEquals(fundConfConsent.getURL(),apiConst.fc_endpoint, 
				"URI for POST fund Confirmation Consent request is as per open banking standard");
		TestLogger.logBlankLine();
		
		testVP.testResultFinalize();			

	}
}

