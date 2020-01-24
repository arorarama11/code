package com.psd2.tests.ClientCredentialAccessToken;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.psd2.core.TestBase;
import com.psd2.logger.TestListener;
import com.psd2.logger.TestLogger;

/**
 * Class Description : Checking the request status through other than GET method with mandatory and optional fields
 * @author Alok Kumar
 *
 */

@Listeners( { TestListener.class })
@Test(groups={"Regression"})
public class CCAT_005 extends TestBase
{
	@Test
	public void m_CCAT_005() throws Throwable{	
		
		TestLogger.logStep("[Step 1] : Creating client credetials....");
		
		createClientCred.setBaseURL(apiConst.cc_endpoint);
		createClientCred.setMethod("GET");
		createClientCred.submit();
		
		testVP.assertStringEquals(String.valueOf(createClientCred.getResponseStatusCode()),"405", 
				"Response Code is correct for client credetials uri with GET Method");	
		TestLogger.logBlankLine();
	}
}
