package com.psd2.tests.cisp.GetFundConfConsent;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.psd2.core.TestBase;
import com.psd2.logger.TestListener;
import com.psd2.logger.TestLogger;
import com.psd2.utils.API_E2E_Utility;

/**
 * Class Description : Verification of "ConsentId' field in the response
 * @author Alok Kumar
 *
 */

@Listeners( { TestListener.class })
@Test(groups={"Regression"})
public class CISP_GFCC_027 extends TestBase{	
	
	API_E2E_Utility util = new API_E2E_Utility();
	
	@Test
	public void m_CISP_GFCC_027() throws Throwable{	
		
		TestLogger.logStep("[Step 1] : Fund confirmation consent....");
		consentDetails = util.generateCISPConsent(true,false,false);	
		TestLogger.logBlankLine();
		
		TestLogger.logStep("[Step 2] : Verification of ConsentId field in the response");
		
		getFundConfConsent.setBaseURL(apiConst.fc_endpoint+"/"+consentDetails.get("consentId"));
		getFundConfConsent.setHeadersString("Authorization:Bearer "+consentDetails.get("cc_access_token"));
		getFundConfConsent.submit();
		
		testVP.assertStringEquals(String.valueOf(getFundConfConsent.getResponseStatusCode()),"200", 
				"Response Code is correct for Get fund Confirmation Consent");
	    testVP.assertTrue(!String.valueOf(getFundConfConsent.getResponseValueByPath("Data.ConsentId")).isEmpty(), 
	    		"ConsentId is not empty");
	
	    testVP.assertStringEquals(String.valueOf(getFundConfConsent.getResponseValueByPath("Data.ConsentId")),consentDetails.get("consentId"), 
	    		"ConsentId is match correctly");
	    		
		testVP.testResultFinalize();			

	}
}
