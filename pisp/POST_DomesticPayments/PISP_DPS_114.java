package com.psd2.tests.pisp.POST_DomesticPayments;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.psd2.core.TestBase;
import com.psd2.logger.TestListener;
import com.psd2.logger.TestLogger;
import com.psd2.utils.API_E2E_Utility;

/**
 * Class Description : Verification of the values into OPTIONAL Data/Initiation/CreditorPostalAddress block
 * @author Soumya Banerjee
 *
 */

@Listeners( { TestListener.class })
@Test(groups={"Regression"})
public class PISP_DPS_114 extends TestBase{	
	
	API_E2E_Utility apiUtility = new API_E2E_Utility();
	
	@Test
	public void m_PISP_DPS_114() throws Throwable{	
	
			
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
		
		testVP.assertTrue(paymentConsent.getResponseValueByPath("Data")!=null, 
				"Mandatory block Data is present and is not null");
	    
	    testVP.assertTrue(!(paymentConsent.getResponseNodeStringByPath("Data.ConsentId")).isEmpty(), 
				"Mandatory field ConsentId is present under Data");
	    
	    testVP.assertTrue(paymentConsent.getResponseValueByPath("Data.Initiation.CreditorPostalAddress")!=null, 
			"Optional field CreditorPostalAddress is present under Initiation block");
	    
	    testVP.assertTrue(paymentConsent.getResponseValueByPath("Data.Initiation.CreditorPostalAddress.AddressType")!=null, 
				"Optional field AddressType is present under CreditorPostalAddress block");
	    
	    testVP.assertTrue(paymentConsent.getResponseValueByPath("Data.Initiation.CreditorPostalAddress.Department")!=null, 
				"Optional field Department is present under CreditorPostalAddress block");
	    
	    testVP.assertTrue(paymentConsent.getResponseValueByPath("Data.Initiation.CreditorPostalAddress.SubDepartment")!=null, 
				"Optional field SubDepartment is present under CreditorPostalAddress block");
	
	    testVP.assertTrue(paymentConsent.getResponseValueByPath("Data.Initiation.CreditorPostalAddress.StreetName")!=null, 
				"Optional field StreetName is present under CreditorPostalAddress block");
	    
	    testVP.assertTrue(paymentConsent.getResponseValueByPath("Data.Initiation.CreditorPostalAddress.BuildingNumber")!=null, 
				"Optional field BuildingNumber is present under CreditorPostalAddress block");
	    
	    testVP.assertTrue(paymentConsent.getResponseValueByPath("Data.Initiation.CreditorPostalAddress.PostCode")!=null, 
				"Optional field PostCode is present under CreditorPostalAddress block");
	    
	    testVP.assertTrue(paymentConsent.getResponseValueByPath("Data.Initiation.CreditorPostalAddress.TownName")!=null, 
				"Optional field TownName is present under CreditorPostalAddress block");
	
	    testVP.assertTrue(paymentConsent.getResponseValueByPath("Data.Initiation.CreditorPostalAddress.CountrySubDivision")!=null, 
				"Optional field CountrySubDivision is present under CreditorPostalAddress block");
	    
	    testVP.assertTrue(paymentConsent.getResponseValueByPath("Data.Initiation.CreditorPostalAddress.Country")!=null, 
				"Optional field Country is present under CreditorPostalAddress block");
	    
	    testVP.assertTrue(paymentConsent.getResponseValueByPath("Data.Initiation.CreditorPostalAddress.AddressLine")!=null, 
				"Optional field AddressLine is present under CreditorPostalAddress block");
		
		TestLogger.logBlankLine();	
		
		testVP.testResultFinalize();		
	}
}
