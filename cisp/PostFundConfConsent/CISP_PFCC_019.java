package com.psd2.tests.cisp.PostFundConfConsent;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.psd2.core.TestBase;
import com.psd2.logger.TestListener;
import com.psd2.logger.TestLogger;
import com.psd2.utils.PropertyUtils;

/**
 * Class Description : Comparison of x-fapi-interaction-id value in the response with the value sent in the request.
 * @author Alok Kumar
 *
 */

@Listeners( { TestListener.class })
@Test(groups={"Regression"})
public class CISP_PFCC_019 extends TestBase
{
	@Test
	public void m_CISP_PFCC_019() throws Throwable{	
		
		TestLogger.logStep("[Step 1] : Creating client credetials....");
		
		createClientCred.setBaseURL(apiConst.cc_endpoint);
		createClientCred.setScope("fundsconfirmations");
		createClientCred.submit();
		
		testVP.assertStringEquals(String.valueOf(createClientCred.getResponseStatusCode()),"200", 
				"Response Code is correct for client credetials");
		access_token = createClientCred.getAccessToken();
		TestLogger.logVariable("AccessToken : " + access_token);	
		TestLogger.logBlankLine();
		
		TestLogger.logStep("[Step 2] : Comparison of x-fapi-interaction-id value in the response with the value sent in the request");
		
		fundConfConsent.setBaseURL(apiConst.fc_endpoint);
		fundConfConsent.setHeadersString("Authorization:Bearer "+access_token);
		fundConfConsent.addHeaderEntry("x-fapi-interaction-id", PropertyUtils.getProperty("inter_id"));
		fundConfConsent.submit();
		
		testVP.assertStringEquals(String.valueOf(fundConfConsent.getResponseStatusCode()),"201", 
				"Response Code is correct for fund Confiramtion Consent with all valid headers");
		testVP.assertStringEquals(fundConfConsent.getResponseHeader("x-fapi-interaction-id"),PropertyUtils.getProperty("inter_id"), 
				"x-fapi-interaction-id value in the response header is correct and matching with one sent in the request.");
		
		TestLogger.logBlankLine();
		
		testVP.testResultFinalize();			

	}
}
