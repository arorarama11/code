package com.psd2.tests.pisp.POST_DomesticPayments;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.psd2.core.TestBase;
import com.psd2.logger.TestListener;
import com.psd2.logger.TestLogger;
import com.psd2.utils.API_E2E_Utility;
import com.psd2.utils.Misc;
import com.psd2.utils.PropertyUtils;

/**
 * Class Description : All the above steps would remains same where same  request payload has been sent into BODY with same x-idempotency-key by same TPP within 24 hrs.
 * 
 * @author Rama Arora
 *
 */

@Listeners({ TestListener.class })
@Test(groups = { "Regression" })
public class PISP_DPS_011 extends TestBase {

	API_E2E_Utility apiUtility = new API_E2E_Utility();

	@Test
	public void m_PISP_DPS_011() throws Throwable {

		String idemPotencyKey=PropertyUtils.getProperty("idempotency_key")+"."+Misc.generateString(3);
		
		TestLogger.logStep("[Step 1] : Generate Consent Id");
        consentDetails=apiUtility.generatePayments(false, apiConst.domesticPayments, false, false);
		TestLogger.logBlankLine();

		TestLogger.logStep("[Step 2] : POST Domestic Payment Submission");

		paymentConsent.setBaseURL(apiConst.dps_endpoint);
		paymentConsent.setHeadersString("Authorization:Bearer "+consentDetails.get("api_access_token")+",x-idempotency-key:"+idemPotencyKey);
		paymentConsent.setConsentId(consentDetails.get("consentId"));
		paymentConsent.submit();

		testVP.assertStringEquals(String.valueOf(paymentConsent.getResponseStatusCode()),"201", 
				"Response Code is correct for Domestic Payment when x-idempotency-key value in header is "+idemPotencyKey+"");
		
		TestLogger.logBlankLine();
		
		TestLogger.logStep("[Step 3] : Verification of request with same x-idempotency-key i.e. "+idemPotencyKey+" and different payload");

		paymentConsent.setBaseURL(apiConst.dps_endpoint);
		paymentConsent.setHeadersString("Authorization:Bearer "+consentDetails.get("api_access_token")+",x-idempotency-key:"+idemPotencyKey);
		paymentConsent.submit();
		
		testVP.assertStringEquals(String.valueOf(paymentConsent.getResponseStatusCode()),"201", 
				"Response Code is correct for Domestic Payment Consent when x-idempotency-key value is same i.e. "+idemPotencyKey+" and payload is same");
		
		TestLogger.logBlankLine();
		testVP.testResultFinalize();
	}
}
