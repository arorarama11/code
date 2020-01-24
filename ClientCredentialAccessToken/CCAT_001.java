package com.psd2.tests.ClientCredentialAccessToken;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.psd2.core.TestBase;
import com.psd2.logger.TestListener;
import com.psd2.logger.TestLogger;

/**
 * Class Description : Verification of Client-Credential Access Token URI 
 * @author Alok Kumar
 *
 */

@Listeners( { TestListener.class })
@Test(groups={"Regression","Sanity"})
public class CCAT_001 extends TestBase
{
	@Test
	public void m_CCAT_001() throws Throwable{	
		
		TestLogger.logStep("[Step 1] : Creating client credetials....");
		
		createClientCred.setBaseURL(apiConst.cc_endpoint);
		createClientCred.submit();
		
		testVP.assertStringEquals(String.valueOf(createClientCred.getResponseStatusCode()),"200", 
				"Response Code is correct for client credetials uri");
		testVP.assertStringEquals(createClientCred.getURL(),apiConst.cc_endpoint, 
				"URI for client credential token is as per open banking standard");
		TestLogger.logBlankLine();
	}
}
