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
 * Class Description : Verification of the request where MANDATORY x-jws-signature header haven't sent
 * @author Rama Arora
 *
 */

@Listeners( { TestListener.class })
@Test(groups={"Regression"})
public class PISP_DPS_027 extends TestBase{	
	
	API_E2E_Utility apiUtility = new API_E2E_Utility();
	
	@Test
	public void m_PISP_DPS_027() throws Throwable{	
		
		TestLogger.logStep("[Step 1] : Generate Consent Id");
        consentDetails=apiUtility.generatePayments(false, apiConst.domesticPayments, false, false);
		TestLogger.logBlankLine();
		
		TestLogger.logStep("[Step 2] : POST Domestic Payment Submission");	
		
		restRequest.setURL(apiConst.dps_endpoint);
		paymentConsent.setConsentId(consentDetails.get("consentId"));
		restRequest.setHeadersString("Authorization:Bearer "+consentDetails.get("api_access_token"));
		restRequest.addHeaderEntry("x-idempotency-key:" , PropertyUtils.getProperty("idempotency_key")+"."+Misc.generateString(3));
		restRequest.addHeaderEntry("x-fapi-financial-id", PropertyUtils.getProperty("fin_id"));
		restRequest.addHeaderEntry("Content-Type", "application/json");
		restRequest.addHeaderEntry("x-fapi-interaction-id:" , PropertyUtils.getProperty("inter_id"));
		restRequest.addHeaderEntry("Accept", "application/json");
		restRequest.setRequestBody(accountSetup.genRequestBody());
		restRequest.setMethod("POST");
		restRequest.submit();
		
		testVP.assertStringEquals(String.valueOf(restRequest.getResponseStatusCode()),"400", 
				"Response Code is correct for Domestic Payments");
		
		testVP.assertStringEquals(String.valueOf(restRequest.getResponseValueByPath("Errors[0].ErrorCode")),"UK.OBIE.Header.Missing", 
				"Response Error Code is correct");
		
		testVP.assertStringEquals(String.valueOf(restRequest.getResponseValueByPath("Errors[0].Message")),"Required header x-jws-signature not specified","Error message is correct");
		
	
		TestLogger.logBlankLine();		
		testVP.testResultFinalize();		
	}
}
