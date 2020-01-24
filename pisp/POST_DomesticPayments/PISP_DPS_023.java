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
 * Class Description : Verification of the request without x-fapi-financial-id value into header
 * @author Rama Arora
 *
 */

@Listeners( { TestListener.class })
@Test(groups={"Regression"})
public class PISP_DPS_023 extends TestBase{	
	
	API_E2E_Utility apiUtility = new API_E2E_Utility();
	
	@Test
	public void m_PISP_DPS_023() throws Throwable{	
		
		TestLogger.logStep("[Step 1] : Generate Consent Id");
        consentDetails=apiUtility.generatePayments(false, apiConst.domesticPayments, false, false);
		TestLogger.logBlankLine();
		
		TestLogger.logStep("[Step 2] : POST Domestic Payment Submission");	
		
		restRequest.setURL(apiConst.dps_endpoint);
		paymentConsent.setBaseURL(apiConst.dps_endpoint);
		paymentConsent.setConsentId(consentDetails.get("consentId"));
		restRequest.setHeadersString("Authorization:Bearer "+consentDetails.get("api_access_token"));
		restRequest.addHeaderEntry("x-idempotency-key:" , PropertyUtils.getProperty("idempotency_key")+"."+Misc.generateString(3));
		restRequest.addHeaderEntry("x-jws-signature:" , PropertyUtils.getProperty("jws-sign"));
		restRequest.addHeaderEntry("x-fapi-financial-id", "");
		restRequest.addHeaderEntry("Content-Type", "application/json");
		restRequest.addHeaderEntry("Accept", "application/json");
		restRequest.setRequestBody(paymentConsent.genRequestBody());
		restRequest.setMethod("POST");
		restRequest.submit();
		
		testVP.assertStringEquals(String.valueOf(restRequest.getResponseStatusCode()),"201", 
				"Response Code is correct for Domestic Payment Consent when request without x-fapi-financial-id value into header");
	
		TestLogger.logBlankLine();		
		testVP.testResultFinalize();		
	}
}
