package com.psd2.tests.pisp.POST_DomesticPayments;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.psd2.core.TestBase;
import com.psd2.logger.TestListener;
import com.psd2.logger.TestLogger;
import com.psd2.utils.API_E2E_Utility;

/**
 * Class Description : Verification of NULL SchemeName field under CreditorAccount/SchemeName
 * 
 * @author Rama Arora
 *
 */

@Listeners({ TestListener.class })
@Test(groups = { "Regression" })
public class PISP_DPS_090 extends TestBase {

	API_E2E_Utility apiUtility = new API_E2E_Utility();

	@Test
	public void m_PISP_DPS_090() throws Throwable {

		TestLogger.logStep("[Step 1] : Create Domestic Payment Consent");

		consentDetails=apiUtility.generatePayments(false, apiConst.domesticPayments, false, false);
		TestLogger.logBlankLine();

        TestLogger.logStep("[Step 2] : Verification of the value of CreditorAccount/SchemeName field");
		
		paymentConsent.setBaseURL(apiConst.dps_endpoint);
		paymentConsent.setHeadersString("Authorization:Bearer "+consentDetails.get("api_access_token"));
		paymentConsent.setConsentId(consentDetails.get("consentId"));
		paymentConsent.submit();
		
		testVP.assertStringEquals(String.valueOf(paymentConsent.getResponseStatusCode()),"201", 
				"Response Code is correct for Domestic Payment URI");
		
		testVP.assertTrue(!(paymentConsent.getResponseNodeStringByPath("Data.Initiation.CreditorAccount.SchemeName")).isEmpty(), 
				"SchemeName field under CreditorAccount is present and is not null");
		
		testVP.assertTrue(paymentConsent.getResponseNodeStringByPath("Data.Initiation.CreditorAccount.SchemeName").length()<=40, 
				"SchemeName field under CreditorAccount is less than 40 characters");
		
		TestLogger.logBlankLine();
		
        TestLogger.logStep("[Step 3] : Verification of NULL value of MANDATORY DebtorAccount/Name field");
		
		paymentConsent.setBaseURL(apiConst.dps_endpoint);
		paymentConsent.setHeadersString("Authorization:Bearer "+consentDetails.get("api_access_token"));
		paymentConsent.setCrAccountSchemeName("");
		paymentConsent.submit();
		
		testVP.assertStringEquals(String.valueOf(paymentConsent.getResponseStatusCode()),"400", 
				"Response Code is correct for Domestic Payment URI when CreditorAccount/SchemeName is NULL");
		
		testVP.assertStringEquals(paymentConsent.getResponseNodeStringByPath("Errors[0].ErrorCode"), "UK.OBIE.Resource.ConsentMismatch", 
				"Error code for the response is correct i.e. '"+paymentConsent.getResponseNodeStringByPath("Errors[0].ErrorCode")+"'");
		
		testVP.assertTrue(paymentConsent.getResponseNodeStringByPath("Errors[0].Message").equals("Payload comparison failed with the consent resource"), 
				"Message for error code is '"+paymentConsent.getResponseNodeStringByPath("Errors[0].Message")+"'");
		TestLogger.logBlankLine();
		
		TestLogger.logStep("[Step 4] : Verification of the value of MANDATORY CreditorAccount/SchemeName field having length variations");
		
		paymentConsent.setBaseURL(apiConst.dps_endpoint);
		paymentConsent.setHeadersString("Authorization:Bearer "+consentDetails.get("api_access_token"));
		paymentConsent.setCrAccountSchemeName("Anything");
		paymentConsent.submit();
		
		testVP.assertStringEquals(String.valueOf(paymentConsent.getResponseStatusCode()),"400", 
				"Response Code is correct for Domestic Payment URI when CreditorAccount/SchemeName is more than 40 characters");
		
		testVP.assertStringEquals(paymentConsent.getResponseNodeStringByPath("Errors[0].ErrorCode"), "UK.OBIE.Resource.ConsentMismatch", 
				"Error code for the response is correct i.e. '"+paymentConsent.getResponseNodeStringByPath("Errors[0].ErrorCode")+"'");
		
		testVP.assertTrue(paymentConsent.getResponseNodeStringByPath("Errors[0].Message").equals("Payload comparison failed with the consent resource"), 
				"Message for error code is '"+paymentConsent.getResponseNodeStringByPath("Errors[0].Message")+"'");
		TestLogger.logBlankLine();
		
		TestLogger.logStep("[Step 5] : Verification of the response when CreditorAccount/SchemeName field haven't sent");
		
		paymentConsent.setBaseURL(apiConst.dps_endpoint);
		paymentConsent.setHeadersString("Authorization:Bearer "+consentDetails.get("api_access_token"));
		String requestBody=paymentConsent.genRequestBody().replace("\"SchemeName\": \"Anything\",","");
		paymentConsent.submit(requestBody);
		
		testVP.assertStringEquals(String.valueOf(paymentConsent.getResponseStatusCode()),"400", 
				"Response Code is correct for Domestic Payment URI when CreditorAccount SchemeName is not sent");
		
		testVP.assertStringEquals(paymentConsent.getResponseNodeStringByPath("Errors[0].ErrorCode"), "UK.OBIE.Field.Missing", 
				"Error code for the response is correct i.e. '"+paymentConsent.getResponseNodeStringByPath("Errors[0].ErrorCode")+"'");
		
		testVP.assertTrue(paymentConsent.getResponseNodeStringByPath("Errors[0].Message").equals("Error validating JSON. Error: - Missing required field \"SchemeName\""), 
				"Message for error code is '"+paymentConsent.getResponseNodeStringByPath("Errors[0].Message")+"'");
		
		TestLogger.logBlankLine();

		testVP.testResultFinalize();
	}
}
