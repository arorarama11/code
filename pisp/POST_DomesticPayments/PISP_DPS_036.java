package com.psd2.tests.pisp.POST_DomesticPayments;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.psd2.core.TestBase;
import com.psd2.logger.TestListener;
import com.psd2.logger.TestLogger;
import com.psd2.utils.API_E2E_Utility;

/**
 * Class Description : Verification of the values into MANDATORY Data/Initiation
 * block
 * 
 * @author Rama Arora
 *
 */

@Listeners({ TestListener.class })
@Test(groups = { "Regression" })
public class PISP_DPS_036 extends TestBase {

	API_E2E_Utility apiUtility = new API_E2E_Utility();

	@Test
	public void m_PISP_DPS_036() throws Throwable {

		TestLogger.logStep("[Step 1] : Generate Consent Id");
        consentDetails=apiUtility.generatePayments(false, apiConst.domesticPayments, false, false);
		TestLogger.logBlankLine();

		TestLogger.logStep("[Step 2] : POST Domestic Payment Submission");

		paymentConsent.setBaseURL(apiConst.dps_endpoint);
		paymentConsent.setHeadersString("Authorization:Bearer "+consentDetails.get("api_access_token"));
		paymentConsent.setConsentId(consentDetails.get("consentId"));
		paymentConsent.submit();

		TestLogger.logStep("[Step 3] : Verification of the values into MANDATORY Initiation block where Request has sent successfully and returned a HTTP Code 201 Created");

		testVP.assertStringEquals(String.valueOf(paymentConsent.getResponseStatusCode()), "201",
				"Response Code is correct for Domestic Payment URI");

		testVP.assertTrue(paymentConsent.getResponseValueByPath("Data") != null,
				"Mandatory block Data is present and is not null");

		testVP.assertTrue(!(paymentConsent.getResponseNodeStringByPath("Data.ConsentId")).isEmpty(),
				"Mandatory field ConsentId is present under Data");

		testVP.assertTrue(!(paymentConsent.getResponseNodeStringByPath("Data.CreationDateTime")).isEmpty(),
				"Mandatory field CreationDateTime is present under Data");

		testVP.assertTrue(!(paymentConsent.getResponseNodeStringByPath("Data.Status")).isEmpty(),
				"Mandatory field Status is present under Data");

		testVP.assertTrue(!(paymentConsent.getResponseNodeStringByPath("Data.StatusUpdateDateTime")).isEmpty(),
				"Mandatory field StatusUpdateDateTime is present under Data");

		testVP.assertTrue(paymentConsent.getResponseValueByPath("Data.Initiation") != null,
				"Mandatory block Initiation is present and is not null");

		testVP.assertTrue(!(paymentConsent.getResponseNodeStringByPath("Data.Initiation.InstructionIdentification")).isEmpty(),
				"Mandatory field InstructionIdentification is present under Initiation");

		testVP.assertTrue(!(paymentConsent.getResponseNodeStringByPath("Data.Initiation.LocalInstrument")).isEmpty(),
				"Mandatory field LocalInstrument is present under Initiation block");

		testVP.assertTrue(!(paymentConsent.getResponseNodeStringByPath("Data.Initiation.EndToEndIdentification")).isEmpty(),
				"Mandatory field EndToEndIdentification is present under Initiation block");

		testVP.assertTrue(paymentConsent.getResponseValueByPath("Data.Initiation.InstructedAmount") != null,
				"Mandatory field InstructedAmount is present under Initiation block");

		testVP.assertTrue(paymentConsent.getResponseValueByPath("Data.Initiation.CreditorAccount") != null,
				"Mandatory field CreditorAccount is present under Initiation block");

		testVP.assertTrue(paymentConsent.getResponseValueByPath("Data.Initiation.DebtorAccount") != null,
				"Optional field DebtorAccount is present under Initiation block");

		testVP.assertTrue(paymentConsent.getResponseValueByPath("Data.Initiation.CreditorPostalAddress") != null,
				"Optional field CreditorPostalAddress is present under Initiation block");

		testVP.assertTrue(paymentConsent.getResponseValueByPath("Data.Initiation.RemittanceInformation") != null,
				"Optional field RemittanceInformation is present under Initiation block");

		testVP.assertTrue(paymentConsent.getResponseValueByPath("Risk") == null,
				"Mandatory field Risk is not present under Initiation block");

		TestLogger.logBlankLine();

		testVP.testResultFinalize();
	}
}
