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
 * Class Description : All the above steps would remains same where different request payload has been sent into BODY with different x-idempotency-key by same TPP within 24 hrs.
 * @author Rama Arora
 *
 */

@Listeners( { TestListener.class })
@Test(groups={"Regression"})
public class PISP_DPS_014 extends TestBase {	
	API_E2E_Utility apiUtility = new API_E2E_Utility();
	@Test
	public void m_PISP_DPC_014() throws Throwable{	
String idemPotencyKey=PropertyUtils.getProperty("idempotency_key")+"."+Misc.generateString(3);
		
		TestLogger.logStep("[Step 1] : Generate Consent Id");
        consentDetails=apiUtility.generatePayments(false, apiConst.domesticPayments, false, false);
		TestLogger.logBlankLine();
		
		TestLogger.logStep("[Step 2] : Verification of request with x-idempotency-key value as "+idemPotencyKey+" in the header");
		
		paymentConsent.setBaseURL(apiConst.dps_endpoint);
		paymentConsent.setHeadersString("Authorization:Bearer "+consentDetails.get("api_access_token")+",x-idempotency-key:"+idemPotencyKey);
		paymentConsent.setConsentId(consentDetails.get("consentId"));
		paymentConsent.submit();
		
		testVP.assertStringEquals(String.valueOf(paymentConsent.getResponseStatusCode()),"201", 
				"Response Code is correct for Domestic Payment Consent when x-idempotency-key value in header is "+idemPotencyKey+"");
		TestLogger.logBlankLine();
		
		TestLogger.logStep("[Step 3] : Verification of request with different x-idempotency-key i.e. "+idemPotencyKey+" and different payload");
		
		paymentConsent.setBaseURL(apiConst.dps_endpoint);
		paymentConsent.setHeadersString("Authorization:Bearer "+consentDetails.get("api_access_token")+",x-idempotency-key:"+idemPotencyKey+"1213");
		paymentConsent.setConsentId(consentDetails.get("consentId"));
		paymentConsent.setCrAccountName("xyz");
		paymentConsent.submit();
		
		testVP.assertStringEquals(String.valueOf(paymentConsent.getResponseStatusCode()),"400", 
	    		"Response Code is correct for Post Domestic Payment Consent when different request payload has been sent into BODY with different x-idempotency-key by same TPP within 24 hrs");
		
		testVP.assertStringEquals(paymentConsent.getResponseNodeStringByPath("Errors[0].ErrorCode"), "UK.OBIE.Resource.ConsentMismatch", 
				"Error code for the response is correct i.e. '"+paymentConsent.getResponseNodeStringByPath("Errors[0].ErrorCode")+"'");
		
		testVP.assertTrue(paymentConsent.getResponseNodeStringByPath("Errors[0].Message").equals("Payload comparison failed with the consent resource"), 
				"Message for error code is '"+paymentConsent.getResponseNodeStringByPath("Errors[0].Message")+"'");
		TestLogger.logBlankLine();
		testVP.testResultFinalize();		
	}
}
