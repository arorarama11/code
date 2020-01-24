package com.psd2.tests.pisp.POST_DomesticPayments;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.psd2.core.TestBase;
import com.psd2.logger.TestListener;
import com.psd2.logger.TestLogger;
import com.psd2.utils.API_E2E_Utility;

/**
 * Class Description :Verification of the value of OPTIONAL CreditorPostalAddress/CountrySubDivision field
 * @author Snehal Chaudhari
 *
 */

@Listeners( { TestListener.class })
@Test(groups={"Regression"})
public class PISP_DPS_129 extends TestBase{	
	
	API_E2E_Utility apiUtility = new API_E2E_Utility();

	@Test
	public void m_PISP_DPS_129() throws Throwable{	
		
        TestLogger.logStep("[Step 1] : Create Domestic Payment Consent");
		
        consentDetails=apiUtility.generatePayments(false, apiConst.domesticPayments, false, false);
		TestLogger.logBlankLine();
		
		TestLogger.logStep("[Step 2] : POST Domestic Payment Submission");	
		paymentConsent.setBaseURL(apiConst.dps_endpoint);
		paymentConsent.setHeadersString("Authorization:Bearer "+consentDetails.get("api_access_token"));
		paymentConsent.setCrCountrySubDivision("ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789");
		paymentConsent.setConsentId(consentDetails.get("consentId"));
		paymentConsent.submit();
		
		testVP.assertStringEquals(String.valueOf(paymentConsent.getResponseStatusCode()),"201", 
		"Response Code is correct for POST Domestic Payment Submission where CountrySubDivision field having length in between 1-35 characters ");
		
		testVP.assertTrue(paymentConsent.getResponseValueByPath("Data.Initiation.CreditorPostalAddress.CountrySubDivision")!=null, 
		"Optional field CountrySubDivision is present under CreditorPostalAddress block");
		
		testVP.assertTrue((paymentConsent.getResponseValueByPath("Data.Initiation.CreditorPostalAddress.CountrySubDivision").toString().length()<=35)&&(paymentConsent.getResponseValueByPath("Data.Initiation.CreditorPostalAddress.CountrySubDivision").toString().length()>=1), 
		"Optional field CountrySubDivision value is between 1 and 35");
				
		
		
		TestLogger.logBlankLine();		
		testVP.testResultFinalize();		
}
}
