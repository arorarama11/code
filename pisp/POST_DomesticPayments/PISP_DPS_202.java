package com.psd2.tests.pisp.POST_DomesticPayments;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.psd2.core.TestBase;
import com.psd2.logger.TestListener;
import com.psd2.logger.TestLogger;
import com.psd2.utils.API_E2E_Utility;

/**
 * Class Description : Verification of the values into OPTIONAL Data/Initiation/CreditorPostalAddress block where Request has sent successfully and returned a HTTP Code 201 Created
 * @author Rama Arora
 *
 */

@Listeners( { TestListener.class })
@Test(groups={"Regression"})
public class PISP_DPS_202 extends TestBase{	
	API_E2E_Utility apiUtility = new API_E2E_Utility();
	@Test
	public void m_PISP_DPS_202() throws Throwable{	
	
			
		TestLogger.logStep("[Step 1] : Create Domestic Payment Consent");
		
        consentDetails=apiUtility.generatePayments(false, apiConst.domesticPayments, false, false);
		TestLogger.logBlankLine();
		
		TestLogger.logStep("[Step 2] : POST Domestic Payment Submission");	
		paymentConsent.setBaseURL(apiConst.dps_endpoint);
		paymentConsent.setHeadersString("Authorization:Bearer "+consentDetails.get("api_access_token"));
		paymentConsent.setConsentId(consentDetails.get("consentId"));
		paymentConsent.submit();
			
		testVP.assertStringEquals(String.valueOf(paymentConsent.getResponseStatusCode()),"201", 
				"Response Code is correct for Domestic Payment URI");
		
		testVP.assertTrue(paymentConsent.getResponseValueByPath("Data.Initiation.CreditorPostalAddress")!=null,
				"Optional Block CreditorPostalAddress is present under CreditorPostalAddress");
		
		testVP.assertTrue(!(paymentConsent.getResponseNodeStringByPath("Data.Initiation.CreditorPostalAddress.AddressType")).isEmpty(), 
				"Optional field AddressType is present under CreditorPostalAddress");
		
		testVP.assertTrue(!(paymentConsent.getResponseNodeStringByPath("Data.Initiation.CreditorPostalAddress.Department")).isEmpty(), 
				"Optional field Department is present under CreditorPostalAddress");
		
		testVP.assertTrue(!(paymentConsent.getResponseNodeStringByPath("Data.Initiation.CreditorPostalAddress.SubDepartment")).isEmpty(), 
				"Optional field SubDepartment is present under CreditorPostalAddress");
		
		testVP.assertTrue(!(paymentConsent.getResponseNodeStringByPath("Data.Initiation.CreditorPostalAddress.StreetName")).isEmpty(),
				"Optional field StreetName is present under CreditorPostalAddress");
		
		testVP.assertTrue(!(paymentConsent.getResponseNodeStringByPath("Data.Initiation.CreditorPostalAddress.BuildingNumber")).isEmpty(),
				"Optional field BuildingNumber is present under CreditorPostalAddress");
		
		testVP.assertTrue(!(paymentConsent.getResponseNodeStringByPath("Data.Initiation.CreditorPostalAddress.PostCode")).isEmpty(), 
				"Optional field PostCode is present under CreditorPostalAddress");
		
		testVP.assertTrue(!(paymentConsent.getResponseNodeStringByPath("Data.Initiation.CreditorPostalAddress.TownName")).isEmpty(), 
				"Optional field TownName is present under CreditorPostalAddress");
		
		testVP.assertTrue(!(paymentConsent.getResponseNodeStringByPath("Data.Initiation.CreditorPostalAddress.Country")).isEmpty(),
				"Optional field Country is present under CreditorPostalAddress");
		
		testVP.assertTrue(!(paymentConsent.getResponseNodeStringByPath("Data.Initiation.CreditorPostalAddress.CountrySubDivision")).isEmpty(),
				"Optional field CountrySubDivision is present under CreditorPostalAddress");
		
		testVP.assertTrue(!(paymentConsent.getResponseNodeStringByPath("Data.Initiation.CreditorPostalAddress.AddressLine[0]")).isEmpty(), 
				"Optional field AddressLine is present under CreditorPostalAddress");
		
		TestLogger.logBlankLine();
		
		testVP.testResultFinalize();		
	}
}

