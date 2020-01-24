package com.psd2.tests.AccessToken;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.psd2.core.TestBase;
import com.psd2.logger.TestListener;
import com.psd2.logger.TestLogger;
import com.psd2.utils.API_E2E_Utility;

/**
 * Class Description : Checking the request status through other than POST method with mandatory and optional fields.  
 * @author Alok Kumar
 *
 */

@Listeners( { TestListener.class })
@Test(groups={"Regression"})
public class ATWAC_005 extends TestBase{	
	
	API_E2E_Utility apiUtil = new API_E2E_Utility();
	
	@Test
	public void m_ATWAC_005() throws Throwable{	
		
		TestLogger.logStep("[Step 1] : Create AISP Consent	....");
		
		consentDetails = apiUtil.generateAISPConsent(null,false,false,false,false,null,false,true);
		TestLogger.logBlankLine();

		TestLogger.logStep("[Step 2] : Get access token...");	
		accesstoken.setBaseURL(apiConst.at_endpoint);
		accesstoken.setAuthCode(consentDetails.get("authCode"));;
		accesstoken.setMethod("GET");
		accesstoken.submit();
		
		testVP.assertStringEquals(String.valueOf(accesstoken.getResponseStatusCode()),"405", 
				"Response Code is correct for get access token request with incorrect http method");	
		TestLogger.logBlankLine();
		testVP.testResultFinalize();		
	}	
}
