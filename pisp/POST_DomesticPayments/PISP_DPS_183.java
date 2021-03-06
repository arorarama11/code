package com.psd2.tests.pisp.POST_DomesticPayments;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.psd2.core.TestBase;
import com.psd2.logger.TestListener;
import com.psd2.logger.TestLogger;
import com.psd2.utils.API_E2E_Utility;

/**
 * Class Description : Verification of the values into MANDATORY Amount field where Request has sent successfully and returned a HTTP Code 201 Created
 * @author Rama Arora
 *
 */

@Listeners( { TestListener.class })
@Test(groups={"Regression"})
public class PISP_DPS_183 extends TestBase{	
	API_E2E_Utility apiUtility = new API_E2E_Utility();
	@Test
	public void m_PISP_DPS_183() throws Throwable{	
	
			
		TestLogger.logStep("[Step 1] : Create Domestic Payment Consent");
		
        consentDetails=apiUtility.generatePayments(false, apiConst.domesticPayments, false, false);
		TestLogger.logBlankLine();
		
		TestLogger.logStep("[Step 2] : POST Domestic Payment Submission");	
		paymentConsent.setBaseURL(apiConst.dps_endpoint);
		paymentConsent.setHeadersString("Authorization:Bearer "+consentDetails.get("api_access_token"));
		paymentConsent.setConsentId(consentDetails.get("consentId"));
		paymentConsent.submit();
			
		testVP.assertStringEquals(String.valueOf(paymentConsent.getResponseStatusCode()),"201", 
				"Response Code is correct for Domestic Payment Consent URI");
		
		testVP.assertTrue(paymentConsent.getResponseNodeStringByPath("Data.Charges[0].Amount.Amount").split("\\.")[0].length()<=13 
				&& paymentConsent.getResponseNodeStringByPath("Data.Charges[0].Amount.Amount").split("\\.")[1].length()<=5, 
				"Amount under Charges block consists of Amount field which is equal to 13 digits and 5 precisions");
			    
		TestLogger.logBlankLine();
		
		testVP.testResultFinalize();		
	}
}

