package com.psd2.tests.cisp.DeleteFundConfConsent;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.psd2.core.TestBase;
import com.psd2.logger.TestListener;
import com.psd2.logger.TestLogger;
import com.psd2.utils.API_E2E_Utility;
import com.psd2.utils.PropertyUtils;

/**
 * Class Description: Checking the request status with no token value in the Authorization (Client-Credential Token) header
 * @author Alok Kumar
 * 
 */
@Listeners({TestListener.class})
@Test(groups={"Regression"})
public class CISP_DFCC_012 extends TestBase
{
	API_E2E_Utility util = new API_E2E_Utility();
	
	@Test
	public void m_CISP_DFCC_012() throws Throwable{	
		
		TestLogger.logStep("[Step 1] : Fund confirmation consent....");
		consentDetails = util.generateCISPConsent(true,false,false);	
		TestLogger.logBlankLine();
		
		TestLogger.logStep("[Step 2] : Verify the request status when no access token is passed in the authorization header");
		
		restRequest.setURL(apiConst.fc_endpoint+"/"+consentDetails.get("consentId"));
		restRequest.setHeadersString("Authorization:Bearer "
				+ ",x-fapi-financial-id:"+PropertyUtils.getProperty("fin_id")
				+",Content-Type:application/json");
		restRequest.setMethod("DELETE");
		restRequest.submit();
		testVP.assertStringEquals(String.valueOf(restRequest.getResponseStatusCode()),"401", 
				"Response Code is correct when no access token is passed in the authorization header");
		
		TestLogger.logBlankLine();
		testVP.testResultFinalize();			

	}
}
