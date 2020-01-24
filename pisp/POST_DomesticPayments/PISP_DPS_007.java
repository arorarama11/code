package com.psd2.tests.pisp.POST_DomesticPayments;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.psd2.core.TestBase;
import com.psd2.logger.TestListener;
import com.psd2.logger.TestLogger;
import com.psd2.utils.API_E2E_Utility;
import com.psd2.utils.PropertyUtils;

/**
 * Class Description : Verification of request without x-idempotency-key value
 * OR key-value pair in the header
 * 
 * @author Rama Arora
 *
 */

@Listeners({ TestListener.class })
@Test(groups = { "Regression" })
public class PISP_DPS_007 extends TestBase {

	API_E2E_Utility apiUtility = new API_E2E_Utility();

	@Test
	public void m_PISP_DPS_007() throws Throwable {

		TestLogger.logStep("[Step 1] : Generate Consent Id");
        consentDetails=apiUtility.generatePayments(false, apiConst.domesticPayments, false, false);
		TestLogger.logBlankLine();

		TestLogger.logStep("[Step 2] : POST Domestic Payment Submission");

		restRequest.setURL(apiConst.dps_endpoint);
		restRequest.setHeadersString("Authorization:Bearer "+consentDetails.get("api_access_token"));
		restRequest.addHeaderEntry("x-jws-signature:",PropertyUtils.getProperty("jws-sign"));
		restRequest.addHeaderEntry("x-fapi-financial-id",PropertyUtils.getProperty("fin_id"));
		restRequest.addHeaderEntry("Content-Type", "application/json");
		restRequest.addHeaderEntry("Accept", "application/json");
		restRequest.setRequestBody(accountSetup.genRequestBody());
		restRequest.setMethod("POST");
		paymentConsent.setConsentId(consentDetails.get("consentId"));
		restRequest.setRequestBody(paymentConsent.genRequestBody());
		restRequest.submit();

		testVP.assertStringEquals(String.valueOf(restRequest.getResponseStatusCode()),"400",
				"Response Code is correct for Domestic Payment Consent when x-idempotency-key header is not present");

		testVP.assertStringEquals(restRequest.getResponseNodeStringByPath("Errors[0].ErrorCode"),
				"UK.OBIE.Header.Missing", "Error Code are matched");
		testVP.assertStringEquals(restRequest.getResponseNodeStringByPath("Errors[0].Message"),
				"Required header x-idempotency-key not specified",
				"Error message is correct");

		TestLogger.logBlankLine();
		testVP.testResultFinalize();
	}
}
