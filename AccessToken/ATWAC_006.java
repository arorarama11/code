package com.psd2.tests.AccessToken;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.psd2.core.TestBase;
import com.psd2.logger.TestListener;
import com.psd2.logger.TestLogger;
import com.psd2.utils.API_E2E_Utility;
import com.psd2.utils.PropertyUtils;

/**
 * Class Description : Checking the request status with CID/Secret are not in right format or invalid combination or not exists in the system   
 * @author Alok Kumar
 *
 */

@Listeners( { TestListener.class })
@Test(groups={"Regression"})
public class ATWAC_006 extends TestBase{	
	
	API_E2E_Utility apiUtil = new API_E2E_Utility();
	
	@Test
	public void m_ATWAC_006() throws Throwable{	
		
		TestLogger.logStep("[Step 1] : Create AISP Consent	....");
		
		consentDetails = apiUtil.generateAISPConsent(null,false,false,false,false,null,false,true);
		TestLogger.logBlankLine();
		
		TestLogger.logStep("[Step 2] : Get access token...");	
		accesstoken.setBaseURL(apiConst.at_endpoint);
		accesstoken.setClientID(PropertyUtils.getProperty("client_id")+"123");
		accesstoken.setAuthCode(consentDetails.get("authCode"));
		accesstoken.submit();
		
		testVP.assertStringEquals(String.valueOf(accesstoken.getResponseStatusCode()),"401", 
				"Response Code is correct for get access token request with incorrect client-id and client-secret");	
		TestLogger.logBlankLine();
		testVP.testResultFinalize();		
	}	
}
