package com.psd2.tests.pisp.POST_DomesticPayments;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.psd2.core.TestBase;
import com.psd2.logger.TestListener;
import com.psd2.logger.TestLogger;
import com.psd2.utils.API_E2E_Utility;

/**
 * Class Description : Checking the request (UTF-8 character encoded) status
 * through POST method with mandatory and optional fields
 * 
 * @author Rama Arora
 *
 */

@Listeners({ TestListener.class })
@Test(groups = { "Regression" })
public class PISP_DPS_004 extends TestBase {

	API_E2E_Utility apiUtility = new API_E2E_Utility();

	@Test
	public void m_PISP_DPS_004() throws Throwable {

		TestLogger.logStep("[Step 1] : Generate Consent Id");
        consentDetails=apiUtility.generatePayments(false, apiConst.domesticPayments, false, false);
		TestLogger.logBlankLine();

		TestLogger.logStep("[Step 2] : POST Domestic Payment Submission");
		paymentConsent.setBaseURL(apiConst.dps_endpoint);
		paymentConsent.setHeadersString("Authorization:Bearer "+consentDetails.get("api_access_token"));
		paymentConsent.setConsentId(consentDetails.get("consentId"));
		paymentConsent.submit();

		testVP.assertStringEquals(String.valueOf(paymentConsent.getResponseStatusCode()), "201",
				"Response Code is correct for POST Domestic Payment Submission");
		
		paymentId = paymentConsent.getPaymentId();
		TestLogger.logVariable("Domestic Payment Id : " + paymentId);
		TestLogger.logBlankLine();

		testVP.assertTrue(paymentConsent.getResponseValueByPath("Data") != null,
				"Mandatory field Data is present in response and is not empty");

		testVP.assertTrue(paymentConsent.getResponseValueByPath("Links") != null,
				"Mandatory field Links is present in response and is not empty");

		testVP.assertTrue(!(paymentConsent.getResponseNodeStringByPath("Links.Self")).isEmpty(),
				"Mandatory field Self is present in response and is not empty");

		testVP.assertStringEquals(paymentConsent.getResponseHeader("Content-Type"),
				"application/json;charset=UTF-8",
				"Response is UTF-8 character encoded");

		TestLogger.logBlankLine();
		testVP.testResultFinalize();
	}
}
