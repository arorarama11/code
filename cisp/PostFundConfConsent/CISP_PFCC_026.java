package com.psd2.tests.cisp.PostFundConfConsent;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.psd2.core.TestBase;
import com.psd2.logger.TestListener;
import com.psd2.logger.TestLogger;

/**
 * Class Description : Verification of fields included in the DebtorAccount  array
 * @author Alok Kumar
 *
 */

@Listeners( { TestListener.class })
@Test(groups={"Regression"})
public class CISP_PFCC_026 extends TestBase
{
	@Test
	public void m_CISP_PFCC_026() throws Throwable{	
		
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
		
		
		testVP.assertTrue(!(fundConfConsent.getResponseNodeStringByPath("Data.DebtorAccount.SchemeName").isEmpty()), 
				"Mandatory field i.e SchemeName is present and is not null - " +fundConfConsent.getResponseNodeStringByPath("Data.DebtorAccount.SchemeName"));
		
		testVP.assertTrue(!(fundConfConsent.getResponseNodeStringByPath("Data.DebtorAccount.Identification").isEmpty()), 
				"Mandatory field i.e Identification is present and is not null - " +fundConfConsent.getResponseNodeStringByPath("Data.DebtorAccount.Identification"));
		
		testVP.assertTrue(!(fundConfConsent.getResponseNodeStringByPath("Data.DebtorAccount.Name").isEmpty()), 
				"Mandatory field i.e Name is present and is not null - " +fundConfConsent.getResponseNodeStringByPath("Data.DebtorAccount.Name"));
		
		testVP.assertTrue(!(fundConfConsent.getResponseNodeStringByPath("Data.DebtorAccount.SecondaryIdentification").isEmpty()), 
				"Mandatory field i.e SecondaryIdentification is present and is not null - " +fundConfConsent.getResponseNodeStringByPath("Data.DebtorAccount.SecondaryIdentification"));
		
		TestLogger.logBlankLine();
		
		testVP.testResultFinalize();			

	}
}
