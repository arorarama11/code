package com.psd2.tests.pisp.POST_DomesticPayments;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.psd2.core.TestBase;
import com.psd2.logger.TestListener;
import com.psd2.logger.TestLogger;
import com.psd2.utils.API_E2E_Utility;

/**
 * Class Description : Verification of the request having invalid value into OPTIONAL Accept header
 * @author Rama Arora
 *
 */

@Listeners( { TestListener.class })
@Test(groups={"Regression"})
public class PISP_DPS_033 extends TestBase{	
	
	API_E2E_Utility apiUtility = new API_E2E_Utility();
	
	@Test
	public void m_PISP_DPS_033() throws Throwable{	
		
		TestLogger.logStep("[Step 1] : Generate Consent Id");
        consentDetails=apiUtility.generatePayments(false, apiConst.domesticPayments, false, false);
		TestLogger.logBlankLine();
		
		TestLogger.logStep("[Step 2] : POST Domestic Payment Submission");
		
		paymentConsent.setBaseURL(apiConst.dps_endpoint);
		paymentConsent.setHeadersString("Authorization:Bearer "+consentDetails.get("api_access_token"));
		paymentConsent.setConsentId(consentDetails.get("consentId"));
		paymentConsent.addHeaderEntry("Accept","application/xml");
		paymentConsent.submit();
		
		testVP.assertStringEquals(String.valueOf(paymentConsent.getResponseStatusCode()),"406", 
				"Response Code is correct without Accept header");
		
		TestLogger.logBlankLine();		
		testVP.testResultFinalize();		
	}
}
