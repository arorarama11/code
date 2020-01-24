package com.psd2.tests.pisp.POST_DomesticPayments;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.psd2.core.TestBase;
import com.psd2.logger.TestListener;
import com.psd2.logger.TestLogger;
import com.psd2.utils.API_E2E_Utility;

/**
 * Class Description : Verification of the values into OPTIONAL RemittanceInformation/Unstructured field
 * @author Snehal Chaudhari
 *
 */

@Listeners( { TestListener.class })
@Test(groups={"Regression"})
public class PISP_DPS_136 extends TestBase{	
	
	API_E2E_Utility apiUtility = new API_E2E_Utility();

	@Test
	public void m_PISP_DPS_136() throws Throwable{	
		
        TestLogger.logStep("[Step 1] : Create Domestic Payment Consent");
		
        consentDetails=apiUtility.generatePayments(false, apiConst.domesticPayments, false, false);
		TestLogger.logBlankLine();
		
		TestLogger.logStep("[Step 2] : POST Domestic Payment Submission");	
		paymentConsent.setBaseURL(apiConst.dps_endpoint);
		paymentConsent.setHeadersString("Authorization:Bearer "+consentDetails.get("api_access_token"));
		paymentConsent.setRemittanceUnstructered("ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ12345678ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ12345678");
        paymentConsent.setConsentId(consentDetails.get("consentId"));
		paymentConsent.submit();
		
		testVP.assertStringEquals(String.valueOf(paymentConsent.getResponseStatusCode()),"201", 
		"Response Code is correct for Domestic Payment URL for Unstructured fields having length in between 1-140 characters");		
	
		testVP.assertTrue(paymentConsent.getResponseValueByPath("Data.Initiation.RemittanceInformation.Unstructured")!=null, 
		"Optional field Unstructured is present under CreditorAccount block");
		
		testVP.assertTrue((paymentConsent.getResponseValueByPath("Data.Initiation.RemittanceInformation.Unstructured").toString().length()<=140)&&(paymentConsent.getResponseValueByPath("Data.Initiation.RemittanceInformation.Unstructured").toString().length()>=1), 
		"Optional field Unstructured value is between 1 and 140"); 
		
		TestLogger.logBlankLine();		
		testVP.testResultFinalize();	
		
	}
}
