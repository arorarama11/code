package com.psd2.tests.cisp.PostFundConfConsent;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.psd2.core.TestBase;
import com.psd2.logger.TestListener;
import com.psd2.logger.TestLogger;

/**
 * Class Description : Verification of the value of MANDATORY DebtorAccount/Identification field when SchemeName = UK.OBIE.BBAN
 * @author Alok Kumar
 *
 */

@Listeners( { TestListener.class })
@Test(groups={"Regression"})
public class CISP_PFCC_053 extends TestBase
{
	@Test
	public void m_CISP_PFCC_053() throws Throwable{	
		
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
		
		fundConfConsent.setBaseURL(apiConst.fc_endpoint);
		fundConfConsent.setHeadersString("Authorization:Bearer "+access_token);
		fundConfConsent.setSchemeName("UK.OBIE.BBAN");
		fundConfConsent.setIdentification("ABCD12345678901234");
		fundConfConsent.submit();
		
		testVP.assertStringEquals(String.valueOf(fundConfConsent.getResponseStatusCode()),"201", 
				"Response Code is correct for fund Confiramtion Consent when SchemeName = UK.OBIE.BBAN with valid identification");
		
		TestLogger.logBlankLine();
		
		testVP.testResultFinalize();
	}				
}
