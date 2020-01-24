package com.psd2.tests.cisp.GetFundConfConsent;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.psd2.core.TestBase;
import com.psd2.logger.TestListener;
import com.psd2.logger.TestLogger;
import com.psd2.utils.API_E2E_Utility;

/**
 * Class Description : Checking the request status without Authorization (Access Token) 
 * @author Alok Kumar
 *
 */

@Listeners( { TestListener.class })
@Test(groups={"Regression"})
public class CISP_GFCC_010 extends TestBase{	
	
	API_E2E_Utility util = new API_E2E_Utility();
	
	@Test
	public void m_CISP_GFCC_010() throws Throwable{	
		
		TestLogger.logStep("[Step 1] : Fund confirmation consent....");
		consentDetails = util.generateCISPConsent(true,false,false);	
		TestLogger.logBlankLine();
		
		TestLogger.logStep("[Step 2] : Get Fund confirmation consent....");
		
		getFundConfConsent.setBaseURL(apiConst.fc_endpoint+"/"+consentDetails.get("consentId"));
		getFundConfConsent.submit();
		
		testVP.assertStringEquals(String.valueOf(getFundConfConsent.getResponseStatusCode()),"400", 
				"Response Code is correct without authorization header of Get fund Confirmation Consent request");
		testVP.assertStringEquals(getFundConfConsent.getResponseNodeStringByPath("Errors[0].ErrorCode"),"UK.OBIE.Header.Missing", 
				"Error Code is correct for Get fund Confirmation Consent without authorization header");
		testVP.assertStringEquals(getFundConfConsent.getResponseNodeStringByPath("Errors[0].Message"),"Required header Authorization not specified", 
				"Error Message is correct for Get fund Confirmation Consent without authorization header");
	
		testVP.testResultFinalize();			

	}
}

