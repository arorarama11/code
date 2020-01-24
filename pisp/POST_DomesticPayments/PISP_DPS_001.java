package com.psd2.tests.pisp.POST_DomesticPayments;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.psd2.core.TestBase;
import com.psd2.logger.TestListener;
import com.psd2.logger.TestLogger;
import com.psd2.utils.API_E2E_Utility;

/**
 * Class Description : Verification of domestic-payments URL that should be as per Open Banking standards as specified format 
 * @author Rama Arora
 *
 */

@Listeners( { TestListener.class })
@Test(groups={"Regression","Sanity"})
public class PISP_DPS_001 extends TestBase{	
	
	API_E2E_Utility apiUtility = new API_E2E_Utility();

	@Test
	public void m_PISP_DPS_001() throws Throwable{	
		
		TestLogger.logStep("[Step 1] : Generate Consent Id");
        consentDetails=apiUtility.generatePayments(false, apiConst.domesticPayments, false, false);
		TestLogger.logBlankLine();
		
		TestLogger.logStep("[Step 2] : POST Domestic Payment Submission");	
		paymentConsent.setBaseURL(apiConst.dps_endpoint);
		paymentConsent.setHeadersString("Authorization:Bearer "+consentDetails.get("api_access_token"));
		paymentConsent.setConsentId(consentDetails.get("consentId"));
		paymentConsent.submit();
		
		testVP.assertStringEquals(String.valueOf(paymentConsent.getResponseStatusCode()),"201", 
				"Response Code is correct for POST Domestic Payment Submission");		
		
		paymentId = paymentConsent.getPaymentId();
		TestLogger.logVariable("Domestic Payment Id : " + paymentId);
		TestLogger.logBlankLine();	
		
		testVP.assertStringEquals(paymentConsent.getURL(),apiConst.dps_endpoint, 
				"URL for POST Domestic Payment Submission is as per open banking standard");
		
		TestLogger.logBlankLine();		
		testVP.testResultFinalize();		
	}
}
