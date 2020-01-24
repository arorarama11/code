package com.psd2.tests.ClientCredentialAccessToken;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.psd2.core.TestBase;
import com.psd2.logger.TestListener;
import com.psd2.logger.TestLogger;

/**
 * Class Description : Generation of Client-Credential Access Token URI when TPP has provided invalid value of grant_type parameter
 * @author Alok Kumar
 *
 */

@Listeners( { TestListener.class })
@Test(groups={"Regression"})
public class CCAT_010 extends TestBase
{
	@Test
	public void m_CCAT_010() throws Throwable{	
		
		TestLogger.logStep("[Step 1] : Creating client credetials....");
		
		createClientCred.setBaseURL(apiConst.cc_endpoint);
		createClientCred.setGrant_Type("client_credential");
		createClientCred.submit();
		
		testVP.assertStringEquals(String.valueOf(createClientCred.getResponseStatusCode()),"400", 
				"Response Code is correct for client credetials uri with invalid value of grant_type");	
		TestLogger.logBlankLine();
	}
}
