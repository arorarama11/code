package com.psd2.tests.AccessToken;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.psd2.core.TestBase;
import com.psd2.logger.TestListener;
import com.psd2.logger.TestLogger;
import com.psd2.utils.API_E2E_Utility;

/**
 * Class Description : Verification of oAuth Access Token URI  
 * @author Alok Kumar 
 *
 */

@Listeners( { TestListener.class })
@Test(groups={"Regression","Sanity"})
public class ATWAC_001 extends TestBase{	
	
	API_E2E_Utility apiUtil = new API_E2E_Utility();
	
	@Test
	public void m_ATWAC_001() throws Throwable{	
		
		TestLogger.logStep("[Step 1] : Create AISP Consent	....");
		
		consentDetails = apiUtil.generateAISPConsent(null,false,false,false,false,null,false,true);
		TestLogger.logBlankLine();

		TestLogger.logStep("[Step 2] : Get access token...");	
		accesstoken.setBaseURL(apiConst.at_endpoint);
		accesstoken.setAuthCode(consentDetails.get("authCode"));
		accesstoken.submit();
		
		testVP.assertStringEquals(String.valueOf(accesstoken.getResponseStatusCode()),"200", 
				"Response Code is correct for get access token request");	
		testVP.assertStringEquals(accesstoken.getURL(),apiConst.at_endpoint, 
				"URI for Access Token generation using auth code is as per open banking standard");
		
		TestLogger.logBlankLine();
		testVP.testResultFinalize();		
	}	
}
