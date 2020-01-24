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
 * Class Description : Verification of the status when MANDATORY Data/Initiation/InstructedAmount block haven't sent
 * @author Rama Arora
 *
 */

@Listeners( { TestListener.class })
@Test(groups={"Regression"})
public class PISP_DPS_050 extends TestBase{	
	
	API_E2E_Utility apiUtility = new API_E2E_Utility();
	
	@Test
	public void m_PISP_DPS_050() throws Throwable{	
	
			
		 TestLogger.logStep("[Step 1] : Create Domestic Payment Consent");
			
	        consentDetails=apiUtility.generatePayments(false, apiConst.domesticPayments, false, false);
			TestLogger.logBlankLine();
			
			TestLogger.logStep("[Step 2] : POST Domestic Payment Submission");	
			
			restRequest.setURL(apiConst.dps_endpoint);
            restRequest.setHeadersString("Authorization:Bearer "+consentDetails.get("api_access_token")+", Content-Type:application/json, x-jws-signature:"+PropertyUtils.getProperty("jws-sign")+", x-fapi-interaction-id:"+PropertyUtils.getProperty("inter_id")+", x-fapi-financial-id:"+PropertyUtils.getProperty("fin_id")+", Accept:application/json, x-idempotency-key:"+PropertyUtils.getProperty("idempotency_key")+"."+Misc.generateString(3));
			paymentConsent.setConsentId(consentDetails.get("consentId"));
			restRequest.setRequestBody(paymentConsent.genRequestBody().replace(",\"InstructedAmount\": { "+ "\"Amount\": \"300.12\","+ "\"Currency\": \"EUR\""+ "}",""));
            restRequest.setMethod("POST");
            restRequest.submit();
		
		    TestLogger.logStep("[Step 1-7] : Verification of the status when MANDATORY Data/Initiation/InstructedAmount block haven't sent");
				
		    testVP.assertStringEquals(String.valueOf(restRequest.getResponseStatusCode()),"400", 
					"Response Code is correct for Domestic Payment URI when mandatory Data/Initiation/InstructedAmount block has not been sent");
			
		    testVP.assertStringEquals(restRequest.getResponseNodeStringByPath("Errors[0].ErrorCode"), "UK.OBIE.Field.Missing", "Error Code are matched");
			testVP.assertStringEquals(restRequest.getResponseNodeStringByPath("Errors[0].Message"), "Error validating JSON. Error: - Missing required field \"InstructedAmount\"", "Error Message are matched");
			
		TestLogger.logBlankLine();
		
		testVP.testResultFinalize();		
	}
}
