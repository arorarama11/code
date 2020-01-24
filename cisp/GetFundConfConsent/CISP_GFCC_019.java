package com.psd2.tests.cisp.GetFundConfConsent;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.psd2.core.TestBase;
import com.psd2.logger.TestListener;
import com.psd2.logger.TestLogger;
import com.psd2.utils.API_E2E_Utility;
import com.psd2.utils.PropertyUtils;

/**
 * Class Description : Comparison of x-fapi-interaction-id value in the response with the value sent in the request.
 * @author Alok Kumar
 *
 */

@Listeners( { TestListener.class })
@Test(groups={"Regression"})
public class CISP_GFCC_019 extends TestBase
{
	API_E2E_Utility util = new API_E2E_Utility();
	
	@Test
	public void m_CISP_GFCC_019() throws Throwable{	
		
		TestLogger.logStep("[Step 1] : Fund confirmation consent....");
		consentDetails = util.generateCISPConsent(true,false,false);	
		TestLogger.logBlankLine();
		
		TestLogger.logStep("[Step 2] : Comparison of x-fapi-interaction-id value in the response with the value sent in the request");
		
		getFundConfConsent.setBaseURL(apiConst.fc_endpoint+"/"+consentDetails.get("consentId"));
		getFundConfConsent.setHeadersString("Authorization:Bearer "+consentDetails.get("cc_access_token"));
		getFundConfConsent.addHeaderEntry("x-fapi-interaction-id", PropertyUtils.getProperty("inter_id"));
		getFundConfConsent.submit();
		
		testVP.assertStringEquals(String.valueOf(getFundConfConsent.getResponseStatusCode()),"200", 
				"Response Code is correct for Get fund Confirmation Consentif the value of x-fapi-interaction-id is in incorrect format");
		
		testVP.assertStringEquals(getFundConfConsent.getResponseHeader("x-fapi-interaction-id"),PropertyUtils.getProperty("inter_id"), 
				"x-fapi-interaction-id value in the response header is matching with one sent in the request.");
		
		TestLogger.logBlankLine();
		testVP.testResultFinalize();			

	}
}
