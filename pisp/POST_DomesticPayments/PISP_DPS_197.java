package com.psd2.tests.pisp.POST_DomesticPayments;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.psd2.core.TestBase;
import com.psd2.logger.TestListener;
import com.psd2.logger.TestLogger;
import com.psd2.utils.API_E2E_Utility;

/**
 * Class Description : Verification of the values into MANDATORY Data/Initiation/CreditorAccount block where Request has sent successfully and returned a HTTP Code 201 Created
 * @author Rama Arora
 *
 */

@Listeners( { TestListener.class })
@Test(groups={"Regression"})
public class PISP_DPS_197 extends TestBase{	
	API_E2E_Utility apiUtility = new API_E2E_Utility();
	@Test
	public void m_PISP_DPS_197() throws Throwable{	
	
			
		TestLogger.logStep("[Step 1] : Create Domestic Payment Consent");
		
        consentDetails=apiUtility.generatePayments(false, apiConst.domesticPayments, false, false);
		TestLogger.logBlankLine();
		
		TestLogger.logStep("[Step 2] : POST Domestic Payment Submission");	
		paymentConsent.setBaseURL(apiConst.dps_endpoint);
		paymentConsent.setHeadersString("Authorization:Bearer "+consentDetails.get("api_access_token"));
		paymentConsent.setConsentId(consentDetails.get("consentId"));
		paymentConsent.submit();
			
		testVP.assertStringEquals(String.valueOf(paymentConsent.getResponseStatusCode()),"201", 
				"Response Code is correct for Domestic Payment URI");
		
		testVP.assertTrue(paymentConsent.getResponseValueByPath("Data.Initiation.CreditorAccount.SchemeName")!=null,
				"Mandatory Block CreditorAccount is present and is not null");
		
		testVP.assertTrue(!(paymentConsent.getResponseNodeStringByPath("Data.Initiation.CreditorAccount.SchemeName")).isEmpty(), 
				"Mandatory field SchemeName is present under CreditorAccount");
		
		testVP.assertTrue(!(paymentConsent.getResponseNodeStringByPath("Data.Initiation.CreditorAccount.Identification")).isEmpty(), 
				"Mandatory field Identification is present under CreditorAccount");
		
		testVP.assertTrue(!(paymentConsent.getResponseNodeStringByPath("Data.Initiation.CreditorAccount.Name")).isEmpty(), 
				"Optional field Name is present under CreditorAccount");
		
		testVP.assertTrue(!(paymentConsent.getResponseNodeStringByPath("Data.Initiation.CreditorAccount.SecondaryIdentification")).isEmpty(), 
				"Optional field SecondaryIdentification is present under CreditorAccount");
		
		TestLogger.logBlankLine();
		
		testVP.testResultFinalize();		
	}
}
