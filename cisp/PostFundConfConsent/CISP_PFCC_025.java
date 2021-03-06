package com.psd2.tests.cisp.PostFundConfConsent;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.psd2.core.TestBase;
import com.psd2.logger.TestListener;
import com.psd2.logger.TestLogger;

/**
 * Class Description : Verification of fields included in the Data array
 * @author Alok Kumar
 *
 */

@Listeners( { TestListener.class })
@Test(groups={"Regression"})
public class CISP_PFCC_025 extends TestBase
{
	@Test
	public void m_CISP_PFCC_025() throws Throwable{	
		
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
		fundConfConsent.submit();
		
		testVP.assertStringEquals(String.valueOf(fundConfConsent.getResponseStatusCode()),"201", 
				"Response Code is correct for fund Confiramtion Consent when all the values of request payload are valid");
		
		consentId = fundConfConsent.getConsentId();
		TestLogger.logVariable("Fund Confirmation Consent : " + consentId);	
		
		testVP.assertTrue(!(fundConfConsent.getResponseNodeStringByPath("Data.ExpirationDateTime").isEmpty()), 
				"Optional field i.e ExpirationDateTime is present and is not null - " +fundConfConsent.getResponseNodeStringByPath("Data.ExpirationDateTime"));
		
		testVP.assertTrue(!(fundConfConsent.getResponseNodeStringByPath("Data.DebtorAccount").isEmpty()), 
				"Mandatory field i.e DebtorAccount is present and is not null - " +fundConfConsent.getResponseNodeStringByPath("Data.DebtorAccount"));
		
		TestLogger.logBlankLine();
		
		testVP.testResultFinalize();			

	}
}
