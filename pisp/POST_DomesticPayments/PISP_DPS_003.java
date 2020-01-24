package com.psd2.tests.pisp.POST_DomesticPayments;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.psd2.core.TestBase;
import com.psd2.logger.TestListener;
import com.psd2.logger.TestLogger;
import com.psd2.utils.API_E2E_Utility;

/**
 * Class Description : Verification of domestic-payments URL that should be as
 * per Open Banking standards as specified format
 * 
 * @author Rama Arora
 *
 */

@Listeners({ TestListener.class })
@Test(groups = { "Regression" })
public class PISP_DPS_003 extends TestBase {

	API_E2E_Utility apiUtility = new API_E2E_Utility();

	@Test
	public void m_PISP_DPS_003() throws Throwable {

		TestLogger.logStep("[Step 1] : Generate Consent Id");
        consentDetails=apiUtility.generatePayments(false, apiConst.domesticPayments, false, false);
		TestLogger.logBlankLine();

		TestLogger.logStep("[Step 2] : POST Domestic Payment Submission");
		paymentConsent.setBaseURL(apiConst.otherpispapi_dso_endpoint);
		paymentConsent.setHeadersString("Authorization:Bearer "+consentDetails.get("api_access_token"));
		paymentConsent.setConsentId(consentDetails.get("consentId"));
		paymentConsent.submit();

		testVP.assertStringEquals(paymentConsent.getURL(),apiConst.otherpispapi_dso_endpoint,
				"URL for POST Domestic Payment Submission is as per open banking standard");

		testVP.assertStringEquals(String.valueOf(paymentConsent.getResponseStatusCode()), "400",
				"Response Code is correct for POST Domestic Payment Submission");

		testVP.assertStringEquals(paymentConsent.getResponseNodeStringByPath("Errors[0].ErrorCode"),
				"UK.OBIE.Field.Missing", "Error Code are matched");
		
		testVP.assertStringEquals(paymentConsent.getResponseNodeStringByPath("Errors[0].Message"),
				"Error validating JSON. Error: - Missing required field \"Frequency\"","Error Message are matched");

		TestLogger.logBlankLine();
		testVP.testResultFinalize();
	}
}




