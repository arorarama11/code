package com.psd2.tests.AccessToken;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.psd2.core.TestBase;
import com.psd2.logger.TestListener;
import com.psd2.logger.TestLogger;
import com.psd2.utils.API_E2E_Utility;

/**
 * Class Description : Generation of oAuth Access Token URI when TPP has not provided or malformed value of redirect_uri parameter.  
 * @author Alok Kumar
 *
 */

@Listeners( { TestListener.class })
@Test(groups={"Regression"})
public class ATWAC_012 extends TestBase{	
	
	API_E2E_Utility apiUtil = new API_E2E_Utility();
	
	@Test
	public void m_ATWAC_012() throws Throwable{	
		
		TestLogger.logStep("[Step 1] : Create AISP Consent	....");
		
		consentDetails = apiUtil.generateAISPConsent(null,false,false,false,false,null,false,true);
		TestLogger.logBlankLine();

		TestLogger.logStep("[Step 2] : Get access token...");	
		accesstoken.setBaseURL(apiConst.at_endpoint);
		accesstoken.setAuthCode(consentDetails.get("authCode"));
		accesstoken.setRedirectURI("https://www.getpostman.com/oauth2/call/test");
		accesstoken.submit();
		
		testVP.assertStringEquals(String.valueOf(accesstoken.getResponseStatusCode()),"400", 
				"Response Code is correct for get access token request with incorrect redirect uri");	
		
		TestLogger.logBlankLine();
		testVP.testResultFinalize();		
	}	
}
