package com.psd2.tests.pisp.POST_DomesticPayments;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.psd2.core.TestBase;
import com.psd2.logger.TestListener;
import com.psd2.logger.TestLogger;
import com.psd2.utils.API_E2E_Utility;

/**
 * Class Description : Verification of the values of MANDATORY InstructedAmount/Amount field having values in between 13 non-decimal and 5 decimals
 * @author Rama Arora
 *
 */

@Listeners( { TestListener.class })
@Test(groups={"Regression"})
public class PISP_DPS_051 extends TestBase{	
	
	API_E2E_Utility apiUtility = new API_E2E_Utility();
	
	@Test
	public void m_PISP_DPS_051() throws Throwable{	
	
			
		 TestLogger.logStep("[Step 1] : Create Domestic Payment Consent");
			
	        consentDetails=apiUtility.generatePayments(false, apiConst.domesticPayments, false, false);
			TestLogger.logBlankLine();
			
			TestLogger.logStep("[Step 2] : POST Domestic Payment Submission");	
			paymentConsent.setBaseURL(apiConst.dps_endpoint);
			paymentConsent.setHeadersString("Authorization:Bearer "+consentDetails.get("api_access_token"));
			paymentConsent.setConsentId(consentDetails.get("consentId"));
			paymentConsent.setAmount("300.12");
			paymentConsent.submit();
		
		    TestLogger.logStep("[Step 3] : Verification of the values of MANDATORY InstructedAmount/Amount field having values in between 13 non-decimal and 5 decimals");
				
		    testVP.assertStringEquals(String.valueOf(paymentConsent.getResponseStatusCode()),"201", 
					"Response Code is correct for Domestic Payment URI when mandatory Data/Initiation/InstructedAmount/Amount field having values in between 13 non-decimal and 5 decimals");
			
		TestLogger.logBlankLine();
		
		testVP.testResultFinalize();		
	}
}
