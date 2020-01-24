package com.psd2.tests.pisp.POST_DomesticPayments;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.psd2.core.TestBase;
import com.psd2.logger.TestListener;
import com.psd2.logger.TestLogger;
import com.psd2.utils.API_E2E_Utility;

/**
 * Class Description : Verification of the values into MANDATORY Data/Initiation/InstructedAmount block must have Amount & Currency fields
 * @author Rama Arora
 *
 */

@Listeners( { TestListener.class })
@Test(groups={"Regression"})
public class PISP_DPS_049 extends TestBase{	
	
	API_E2E_Utility apiUtility = new API_E2E_Utility();
	
	@Test
	public void m_PISP_DPS_049() throws Throwable{	
	
			
		 TestLogger.logStep("[Step 1] : Create Domestic Payment Consent");
			
	        consentDetails=apiUtility.generatePayments(false, apiConst.domesticPayments, false, false);
			TestLogger.logBlankLine();
			
			TestLogger.logStep("[Step 2] : POST Domestic Payment Submission");
			
			paymentConsent.setBaseURL(apiConst.dps_endpoint);
			paymentConsent.setHeadersString("Authorization:Bearer "+consentDetails.get("api_access_token"));
			paymentConsent.setConsentId(consentDetails.get("consentId"));
			paymentConsent.submit();
		
		    TestLogger.logStep("[Step 3] : Verification of the values into MANDATORY Data/Initiation/InstructedAmount block must have Amount & Currency fields");
				
		    testVP.assertStringEquals(String.valueOf(paymentConsent.getResponseStatusCode()),"201", 
					"Response Code is correct for Domestic Payment Consent URI");
		    
			testVP.assertStringEquals(String.valueOf(paymentConsent.getResponseValueByPath("Data.Initiation.InstructedAmount.Amount")), "300.12", 
					"Amount field under InstructedAmount array is present and not empty");
			
			testVP.assertStringEquals(String.valueOf(paymentConsent.getResponseValueByPath("Data.Initiation.InstructedAmount.Currency")), "EUR", 
					"Currency field under InstructedAmount array is present and not empty");
			
		TestLogger.logBlankLine();
		
		testVP.testResultFinalize();		
	}
}
