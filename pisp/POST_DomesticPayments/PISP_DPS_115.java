package com.psd2.tests.pisp.POST_DomesticPayments;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.psd2.core.TestBase;
import com.psd2.logger.TestListener;
import com.psd2.logger.TestLogger;
import com.psd2.utils.API_E2E_Utility;

/**
 * Class Description : Verification of the value of OPTIONAL CreditorPostalAddress/AddressType field
 * @author Soumya Banerjee
 *
 */

@Listeners( { TestListener.class })
@Test(groups={"Regression"})
public class PISP_DPS_115 extends TestBase{	
	API_E2E_Utility apiUtility = new API_E2E_Utility();
	@Test
	public void m_PISP_DPS_115() throws Throwable{	
	
		TestLogger.logStep("[Step 1] : Create Domestic Payment Consent");
		
        consentDetails=apiUtility.generatePayments(false, apiConst.domesticPayments, false, false);
		TestLogger.logBlankLine();
			
		TestLogger.logStep("[Step 2] : POST Domestic Payment Submission");	
		paymentConsent.setBaseURL(apiConst.dps_endpoint);
		paymentConsent.setHeadersString("Authorization:Bearer "+consentDetails.get("api_access_token"));
		paymentConsent.setConsentId(consentDetails.get("consentId"));
		paymentConsent.submit();
		
				
		testVP.assertStringEquals(String.valueOf(paymentConsent.getResponseStatusCode()),"201", 
				"Response Code is correct for POST Domestic Payment Submission");	
		
		
		 testVP.assertTrue(paymentConsent.getResponseValueByPath("Data.Initiation.CreditorPostalAddress.AddressType")!=null, 
					"Optional field AddressType is present under CreditorPostalAddress block");
		 
		 testVP.assertTrue(paymentConsent.getResponseNodeStringByPath("Data.Initiation.CreditorPostalAddress.AddressType").equals("Business") 
					|| paymentConsent.getResponseNodeStringByPath("Data.Initiation.CreditorPostalAddress.AddressType").equals("Correspondence") 
					|| paymentConsent.getResponseNodeStringByPath("Data.Initiation.CreditorPostalAddress.AddressType").equals("DeliveryTo")
					|| paymentConsent.getResponseNodeStringByPath("Data.Initiation.CreditorPostalAddress.AddressType").equals("MailTo") 
					|| paymentConsent.getResponseNodeStringByPath("Data.Initiation.CreditorPostalAddress.AddressType").equals("POBox") 
					|| paymentConsent.getResponseNodeStringByPath("Data.Initiation.CreditorPostalAddress.AddressType").equals("Postal")
					|| paymentConsent.getResponseNodeStringByPath("Data.Initiation.CreditorPostalAddress.AddressType").equals("Residential")
					|| paymentConsent.getResponseNodeStringByPath("Data.Initiation.CreditorPostalAddress.AddressType").equals("Statement"),
					"AddressType field value is correct i.e. "+paymentConsent.getResponseNodeStringByPath("Data.Initiation.CreditorPostalAddress.AddressType"));

		TestLogger.logBlankLine();	
		
		testVP.testResultFinalize();		
	}
}

