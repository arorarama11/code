package com.psd2.tests.pisp.POST_DomesticPayments;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.psd2.core.TestBase;
import com.psd2.logger.TestListener;
import com.psd2.logger.TestLogger;
import com.psd2.utils.API_E2E_Utility;

/**
 * Class Description : Verification of the value of OPTIONAL DebtorAccount/Name field
 * @author Rama Arora
 *
 */

@Listeners( { TestListener.class })
@Test(groups={"Regression"})
public class PISP_DPS_084 extends TestBase {	
	
	API_E2E_Utility apiUtility = new API_E2E_Utility();
	
	@Test
	public void m_PISP_DPS_84() throws Throwable{	
		
		TestLogger.logStep("[Step 1] : Create Domestic Payment Consent");

		consentDetails=apiUtility.generatePayments(false, apiConst.domesticPayments, false, false);
		TestLogger.logBlankLine();

        TestLogger.logStep("[Step 2] : Verification of the value of OPTIONAL DebtorAccount/Name field");
		
		paymentConsent.setBaseURL(apiConst.dps_endpoint);
		paymentConsent.setHeadersString("Authorization:Bearer "+consentDetails.get("api_access_token"));
		paymentConsent.setConsentId(consentDetails.get("consentId"));
		paymentConsent.submit();
		
		testVP.assertStringEquals(String.valueOf(paymentConsent.getResponseStatusCode()),"201", 
				"Response Code is correct for Domestic Payment URI");
		
		testVP.assertTrue(!(paymentConsent.getResponseNodeStringByPath("Data.Initiation.DebtorAccount.Name")).isEmpty(), 
				"Name field under DebtorAccount is present and is not null");
		
		testVP.assertTrue(paymentConsent.getResponseNodeStringByPath("Data.Initiation.DebtorAccount.Name").length()<=70, 
				"Name field under DebtorAccount is less than 70 characters");
		String key = paymentConsent.getHeaderEntry("x-idempotency-key");
		TestLogger.logBlankLine();
		
        TestLogger.logStep("[Step 3] : Verification of NULL value of MANDATORY DebtorAccount/Name field");
		
		paymentConsent.setBaseURL(apiConst.dps_endpoint);
		paymentConsent.setHeadersString("Authorization:Bearer "+consentDetails.get("api_access_token")+key);
		paymentConsent.setDrAccountName("");
		paymentConsent.submit();
		
		testVP.assertStringEquals(String.valueOf(paymentConsent.getResponseStatusCode()),"400", 
				"Response Code is correct for Domestic Payment Consent URI when DebtorAccount Name is NULL");
		
		testVP.assertStringEquals(paymentConsent.getResponseNodeStringByPath("Errors[0].ErrorCode"), "UK.OBIE.Field.Invalid", 
				"Error code for the response is correct i.e. '"+paymentConsent.getResponseNodeStringByPath("Errors[0].ErrorCode")+"'");
		
		testVP.assertTrue(paymentConsent.getResponseNodeStringByPath("Errors[0].Message").equals("Error validating JSON. Error: - Expected min length 1"), 
				"Message for error code is '"+paymentConsent.getResponseNodeStringByPath("Errors[0].Message")+"'");
		TestLogger.logBlankLine();
		
		TestLogger.logStep("[Step 4] : Verification of the value of MANDATORY DebtorAccount/Name field having length variations");
		
		paymentConsent.setBaseURL(apiConst.dps_endpoint);
		paymentConsent.setHeadersString("Authorization:Bearer "+consentDetails.get("api_access_token")+key);
		paymentConsent.setDrAccountName("ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567834782469723054");
		paymentConsent.submit();
		
		testVP.assertStringEquals(String.valueOf(paymentConsent.getResponseStatusCode()),"400", 
				"Response Code is correct for Domestic Payment Consent URI when DebtorAccount Name is more than 70 characters");
		
		testVP.assertStringEquals(paymentConsent.getResponseNodeStringByPath("Errors[0].ErrorCode"), "UK.OBIE.Field.Invalid", 
				"Error code for the response is correct i.e. '"+paymentConsent.getResponseNodeStringByPath("Errors[0].ErrorCode")+"'");
		
		testVP.assertTrue(paymentConsent.getResponseNodeStringByPath("Errors[0].Message").equals("Error validating JSON. Error: - Expected max length 70"), 
				"Message for error code is '"+paymentConsent.getResponseNodeStringByPath("Errors[0].Message")+"'");
		TestLogger.logBlankLine();
		
		testVP.testResultFinalize();	
		
	}
}
