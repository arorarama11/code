package com.psd2.tests.ClientCredentialAccessToken;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.psd2.core.TestBase;
import com.psd2.logger.TestListener;
import com.psd2.logger.TestLogger;
import com.psd2.utils.PropertyUtils;

/**
 * Class Description : Checking the request status with CID/Secret are not in right format or invalid combination or not exists in the system 
 * @author Alok Kumar
 *
 */

@Listeners( { TestListener.class })
@Test(groups={"Regression"})
public class CCAT_006 extends TestBase
{
	@Test
	public void m_CCAT_006() throws Throwable{	
			
		TestLogger.logStep("[Step 1] : Creating client credetials....");
		
		createClientCred.setBaseURL(apiConst.cc_endpoint);
		createClientCred.setClientID(PropertyUtils.getProperty("client_id")+"123");
		createClientCred.submit();
		
		testVP.assertStringEquals(String.valueOf(createClientCred.getResponseStatusCode()),"401", 
				"Response Code is correct for client credetials uri with invalid CI/CS");	
		TestLogger.logBlankLine();
	}
}
