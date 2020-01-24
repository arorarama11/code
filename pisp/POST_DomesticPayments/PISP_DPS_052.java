package com.psd2.tests.pisp.POST_DomesticPayments;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.psd2.core.TestBase;
import com.psd2.logger.TestListener;
import com.psd2.logger.TestLogger;
import com.psd2.utils.API_E2E_Utility;

/**
 * Class Description : Verification of the status when MANDATORY InstructedAmount/Amount field block haven't sent
 * @author Rama Arora
 *
 */

@Listeners( { TestListener.class })
@Test(groups={"Regression"})
public class PISP_DPS_052 extends TestBase{	
	
	API_E2E_Utility apiUtility = new API_E2E_Utility();
	
	@Test
	public void m_PISP_DPS_052() throws Throwable{	
	
			
        TestLogger.logStep("[Step 1] : Create Domestic Payment Consent");
		
        consentDetails=apiUtility.generatePayments(false, apiConst.domesticPayments, false, false);
		TestLogger.logBlankLine();
		
		TestLogger.logStep("[Step 2] : POST Domestic Payment Submission");	
			
		    paymentConsent.setBaseURL(apiConst.dps_endpoint);
			paymentConsent.setHeadersString("Authorization:Bearer "+consentDetails.get("api_access_token"));
			paymentConsent.setConsentId(consentDetails.get("consentId"));
			String _reqBody = paymentConsent.genRequestBody().replace("\"Amount\": \"300.12\",","");
			paymentConsent.submit(_reqBody);

		
		    TestLogger.logStep("[Step 3] : Verification of the status when MANDATORY InstructedAmount/Amount field block haven't sent");
				
		    testVP.assertStringEquals(String.valueOf(paymentConsent.getResponseStatusCode()),"400", 
					"Response Code is correct for Domestic Payment URI when MANDATORY InstructedAmount/Amount field block haven't sent");
			
		    testVP.assertStringEquals(paymentConsent.getResponseNodeStringByPath("Errors[0].ErrorCode"), "UK.OBIE.Field.Missing", "Error Code are matched");
			testVP.assertStringEquals(paymentConsent.getResponseNodeStringByPath("Errors[0].Message"), "Error validating JSON. Error: - Missing required field \"Amount\"", "Error Message are matched");
			
		TestLogger.logBlankLine();
		
		testVP.testResultFinalize();		
	}
}
