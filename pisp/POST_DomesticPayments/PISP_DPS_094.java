package com.psd2.tests.pisp.POST_DomesticPayments;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.psd2.core.TestBase;
import com.psd2.logger.TestListener;
import com.psd2.logger.TestLogger;
import com.psd2.utils.API_E2E_Utility;

/**
 * Class Description : Verification of the Invalid value of MANDATORY CreditorAccount/Identification field having length variation
 * 
 * @author Rama Arora
 *
 */

@Listeners({ TestListener.class })
@Test(groups = { "Regression" })
public class PISP_DPS_094 extends TestBase {

	API_E2E_Utility apiUtility = new API_E2E_Utility();

	@Test
	public void m_PISP_DPS_094() throws Throwable {

		TestLogger.logStep("[Step 1] : Create Domestic Payment Consent");

		consentDetails=apiUtility.generatePayments(false, apiConst.domesticPayments, false, false);
		TestLogger.logBlankLine();

		TestLogger.logStep("[Step 2] : POST Domestic Payment Submission");

		paymentConsent.setBaseURL(apiConst.dps_endpoint);
		paymentConsent.setHeadersString("Authorization:Bearer "+consentDetails.get("api_access_token"));
		paymentConsent.setConsentId(consentDetails.get("consentId"));
		paymentConsent.setCrAccountIdentification("IE85BOFI901204123456792465234253816249274023482hdsghxdzjhvxzxkIE85BOFI901204123456792465234253816249274023482hdsghxdzjhvxzxkIE85BOFI901204123456792465234253816249274023482hdsghxdzjhvxzxkIE85BOFI901204123456792465234253816249274023482hdsghxdzjhvxzxk53816249274023482hdsghxdzjhvxzxk");
		paymentConsent.submit();

		testVP.assertStringEquals(String.valueOf(paymentConsent.getResponseStatusCode()),"400",
				"Response Code is correct for Domestic Payment URI when CreditorAccount Identification is more than 256 characters");

		testVP.assertStringEquals(paymentConsent.getResponseNodeStringByPath("Errors[0].ErrorCode"),"UK.OBIE.Field.Invalid",
				"Error code for the response is correct i.e. '"+ paymentConsent.getResponseNodeStringByPath("Errors[0].ErrorCode")+ "'");

		testVP.assertTrue(paymentConsent.getResponseNodeStringByPath("Errors[0].Message").equals("Error validating JSON. Error: - Expected max length 256"),"Message for error code is '"
						+ paymentConsent.getResponseNodeStringByPath("Errors[0].Message")+ "'");

		TestLogger.logBlankLine();

		testVP.testResultFinalize();
	}
}
