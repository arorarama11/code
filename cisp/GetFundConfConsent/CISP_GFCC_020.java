package com.psd2.tests.cisp.GetFundConfConsent;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.psd2.core.TestBase;
import com.psd2.logger.TestListener;
import com.psd2.logger.TestLogger;
import com.psd2.utils.API_E2E_Utility;

/**
 * Class Description : Verification of x-fapi-interaction-id value in the response if not sent in the request.
 * @author Alok Kumar
 *
 */

@Listeners( { TestListener.class })
@Test(groups={"Regression"})
public class CISP_GFCC_020 extends TestBase
{
	API_E2E_Utility util = new API_E2E_Utility();
	
	@Test
	public void m_CISP_GFCC_020() throws Throwable{	
		
		TestLogger.logStep("[Step 1] : Fund confirmation consent....");
		consentDetails = util.generateCISPConsent(true,false,false);	
		TestLogger.logBlankLine();
		
		TestLogger.logStep("[Step 2] : Verification of x-fapi-interaction-id value in the response if not sent in the request");
		
		getFundConfConsent.setBaseURL(apiConst.fc_endpoint+"/"+consentDetails.get("consentId"));
		getFundConfConsent.setHeadersString("Authorization:Bearer "+consentDetails.get("cc_access_token"));
		getFundConfConsent.submit();
		
		testVP.assertStringEquals(String.valueOf(getFundConfConsent.getResponseStatusCode()),"200", 
				"Response Code is correct for fund Confiramtion Consent with all valid headers");
		
		testVP.assertFalse(String.valueOf(getFundConfConsent.getResponseHeader("x-fapi-interaction-id")).isEmpty(),
				"x-fapi-interaction-id response header is not empty");		
		TestLogger.logBlankLine();
		testVP.testResultFinalize();			

	}
}
