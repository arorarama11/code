package com.psd2.tests.cisp.PostFundConfConsent;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.psd2.core.TestBase;
import com.psd2.logger.TestListener;
import com.psd2.logger.TestLogger;

/**
 * Class Description : Checking the request status through other than POST method with mandatory and optional fields.
 * @author Alok Kumar
 *
 */

@Listeners( { TestListener.class })
@Test(groups={"Regression"})
public class CISP_PFCC_007 extends TestBase
{
	@Test
	public void m_CISP_PFCC_007() throws Throwable{	
		
		TestLogger.logStep("[Step 1] : Creating client credetials....");
		
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
		fundConfConsent.setMethod("GET");
		fundConfConsent.submit();
		
		testVP.assertStringEquals(String.valueOf(fundConfConsent.getResponseStatusCode()),"405", 
				"Response Code is correct for fund Confiramtion Consent when the method is other than POST");
		
		TestLogger.logBlankLine();
		
		testVP.testResultFinalize();			

	}
}
