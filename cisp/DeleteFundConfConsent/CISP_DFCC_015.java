package com.psd2.tests.cisp.DeleteFundConfConsent;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.psd2.core.TestBase;
import com.psd2.logger.TestListener;
import com.psd2.logger.TestLogger;
import com.psd2.utils.API_Constant;
import com.psd2.utils.API_E2E_Utility;
import com.psd2.utils.PropertyUtils;

/**
 * Class Description: Checking the request status with invalid value of scope. Client-Credential Token having value of scope other than 'fundsconfirmations'
 * @author Alok Kumar
 * 
 */
@Listeners({TestListener.class})
@Test(groups={"Regression"})
public class CISP_DFCC_015 extends TestBase
{
	API_E2E_Utility util = new API_E2E_Utility();
	
	@Test
	public void m_CISP_DFCC_015() throws Throwable{	
		
		TestLogger.logStep("[Step 1] : Fund confirmation consent....");
		consentDetails = util.generateCISPConsent(true,false,false);	
		TestLogger.logBlankLine();
		
		TestLogger.logStep("[Step 2] : Verify the response code with scope other than fundsconfirmations");
		
		restRequest.setURL(apiConst.fc_endpoint+"/"+consentDetails.get("consentId"));
		restRequest.setHeadersString("Authorization:Bearer "+API_Constant.getCc_AccessToken()+""
				+ ",x-fapi-financial-id:"+PropertyUtils.getProperty("fin_id")
				+",Content-Type:application/json");
		restRequest.setMethod("DELETE");
		restRequest.submit();
		testVP.assertStringEquals(String.valueOf(restRequest.getResponseStatusCode()),"403", 
				"Response Code is correct for scope other than fundsconfirmations");
		
		TestLogger.logBlankLine();
		testVP.testResultFinalize();			

	}
}
