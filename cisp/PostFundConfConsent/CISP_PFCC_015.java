package com.psd2.tests.cisp.PostFundConfConsent;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.psd2.core.TestBase;
import com.psd2.logger.TestListener;
import com.psd2.logger.TestLogger;

/**
 * Class Description : Checking the request status without x-fapi-financial-id header
 * @author Alok Kumar
 *
 */

@Listeners( { TestListener.class })
@Test(groups={"Regression"})
public class CISP_PFCC_015 extends TestBase
{
	@Test
	public void m_CISP_PFCC_015() throws Throwable{	
		
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
			
		restRequest.setURL(apiConst.fc_endpoint);
		restRequest.setHeadersString("Authorization:Bearer "+access_token+",Accept:application/json,Content-Type:application/json");
		restRequest.setRequestBody(fundConfConsent.genRequestBody());
		restRequest.setMethod("POST");
		restRequest.submit();
		testVP.assertStringEquals(String.valueOf(restRequest.getResponseStatusCode()),"201", 
				"Response Code is correct for fund Confiramtion Consent when the value of x-fapi-financial-id is not present");
		
		TestLogger.logBlankLine();
		
		testVP.testResultFinalize();			

	}
}
