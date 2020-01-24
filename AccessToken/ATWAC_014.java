package com.psd2.tests.AccessToken;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.psd2.core.TestBase;
import com.psd2.logger.TestListener;
import com.psd2.logger.TestLogger;

/**
 * Class Description : Generation of oAuth Access Token URI when TPP has provided malformed value of code parameter.  
 * @author Alok Kumar
 *
 */

@Listeners( { TestListener.class })
@Test(groups={"Regression"})
public class ATWAC_014 extends TestBase{	
	
	@Test
	public void m_ATWAC_014() throws Throwable{	
		
		TestLogger.logStep("[Step 1] : Get access token...");	
		accesstoken.setBaseURL(apiConst.at_endpoint);
		accesstoken.setAuthCode("g6UapoEKwNiP78");
		accesstoken.submit();
		
		testVP.assertStringEquals(String.valueOf(accesstoken.getResponseStatusCode()),"400", 
				"Response Code is correct for get access token request with invalid auth code");	
		
		TestLogger.logBlankLine();
		testVP.testResultFinalize();		
	}	
}
