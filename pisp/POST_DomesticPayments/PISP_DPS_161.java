package com.psd2.tests.pisp.POST_DomesticPayments;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.psd2.core.TestBase;
import com.psd2.logger.TestListener;
import com.psd2.logger.TestLogger;
import com.psd2.utils.API_E2E_Utility;

/**
 * Class Description : Verification of the value of OPTIONAL DeliveryAddress/CountrySubDivision field having length variation
 * @author Snehal Chaudhari
 *
 */

@Listeners( { TestListener.class })
@Test(groups={"Regression"})
public class PISP_DPS_161 extends TestBase{	
	
	API_E2E_Utility apiUtility = new API_E2E_Utility();

	@Test
	public void m_PISP_DPS_161() throws Throwable{	
		
        TestLogger.logStep("[Step 1] : Create Domestic Payment Consent");
		
        consentDetails=apiUtility.generatePayments(false, apiConst.domesticPayments, false, false);
		TestLogger.logBlankLine();
		
		TestLogger.logStep("[Step 2] : POST Domestic Payment Submission");	
		paymentConsent.setBaseURL(apiConst.dps_endpoint);
		paymentConsent.setHeadersString("Authorization:Bearer "+consentDetails.get("api_access_token"));
		paymentConsent.setCrCountrySubDivision("ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789485765");
        paymentConsent.setConsentId(consentDetails.get("consentId"));
		paymentConsent.submit();
		
		testVP.assertStringEquals(String.valueOf(paymentConsent.getResponseStatusCode()),"400", 
	    "Response Code is correct for Domestic Payment URL for CountrySubDivision fields having length in between 1-35 characters");		
						
		testVP.assertStringEquals(paymentConsent.getResponseNodeStringByPath("Errors.ErrorCode"), "[UK.OBIE.Field.Invalid]", "Error Code are matched");
		testVP.assertStringEquals(paymentConsent.getResponseNodeStringByPath("Errors.Message"), "[Error validating JSON. Error: - Expected max length 35]", "Error Message are matched");
						
						
						
		TestLogger.logBlankLine();		
		testVP.testResultFinalize();	
	}
	}

