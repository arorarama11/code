package com.psd2.tests.pisp.POST_DomesticPayments;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.psd2.core.TestBase;
import com.psd2.logger.TestListener;
import com.psd2.logger.TestLogger;
import com.psd2.utils.API_E2E_Utility;

/**
 * Class Description : Verification of the values into OPTIONAL Data/MultiAuthorisation block where Request has sent successfully and returned a HTTP Code 201 Created
 * @author Rama Arora
 *
 */

@Listeners( { TestListener.class })
@Test(groups={"Regression"})
public class PISP_DPS_216 extends TestBase{	
	API_E2E_Utility apiUtility = new API_E2E_Utility();
	@Test
	public void m_PISP_DPS_216() throws Throwable{	
	
			
		TestLogger.logStep("[Step 1] : Create Domestic Payment Consent");
		
        consentDetails=apiUtility.generatePayments(false, apiConst.domesticPayments, false, false);
		TestLogger.logBlankLine();
		
		TestLogger.logStep("[Step 2] : POST Domestic Payment Submission");	
		paymentConsent.setBaseURL(apiConst.dps_endpoint);
		paymentConsent.setHeadersString("Authorization:Bearer "+consentDetails.get("api_access_token"));
		paymentConsent.setConsentId(consentDetails.get("consentId"));
		paymentConsent.submit();
			
		testVP.assertStringEquals(String.valueOf(paymentConsent.getResponseStatusCode()),"201", 
				"Response Code is correct for Domestic Payment Consent URI");
		
		testVP.assertTrue(paymentConsent.getResponseValueByPath("Data.MultiAuthorisation")!=null,
				"Optional field Authorisation is present and is not null");
		
		testVP.assertTrue(paymentConsent.getResponseNodeStringByPath("Data.MultiAuthorisation.Status")!=null,
				"Mandatory field Status is present under MultiAuthorisation");
		
		testVP.assertTrue(!(paymentConsent.getResponseNodeStringByPath("Data.MultiAuthorisation.NumberRequired")).isEmpty(),
				"Optional field NumberRequired is present under Authorisation");
		
		testVP.assertTrue(!(paymentConsent.getResponseNodeStringByPath("Data.MultiAuthorisation.NumberReceived")).isEmpty(),
				"Optional field NumberReceived is present under Authorisation");
		
		testVP.assertTrue(!(paymentConsent.getResponseNodeStringByPath("Data.MultiAuthorisation.LastUpdateDateTime")).isEmpty(),
				"Optional field LastUpdatedDateTime is present under Authorisation");
		
		testVP.assertTrue(!(paymentConsent.getResponseNodeStringByPath("Data.MultiAuthorisation.ExpirationDateTime")).isEmpty(),
				"Optional field ExpirationDateTime is present under Authorisation");
		
		TestLogger.logBlankLine();
		
		testVP.testResultFinalize();		
	}
}

