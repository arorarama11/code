package com.psd2.tests.cisp.DeleteFundConfConsent;

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
public class CISP_DFCC_020 extends TestBase{	
	
	API_E2E_Utility util = new API_E2E_Utility();
	
	@Test
	public void m_CISP_DFCC_020() throws Throwable{	
		
		TestLogger.logStep("[Step 1] : Fund confirmation consent....");
		consentDetails = util.generateCISPConsent(true,false,false);	
		TestLogger.logBlankLine();
		
		TestLogger.logStep("[Step 2] : Comparison of x-fapi-interaction-id value in the response with the value sent in the request");
		
		restRequest.setURL(apiConst.fc_endpoint+"/"+consentDetails.get("consentId"));
		restRequest.setHeadersString("Authorization:Bearer "+consentDetails.get("cc_access_token")+""
				+ ",x-fapi-financial-id:"+PropertyUtils.getProperty("fin_id")+",Content-Type:application/json");
		restRequest.addHeaderEntry("x-fapi-interaction-id", PropertyUtils.getProperty("inter_id"));
		restRequest.setMethod("DELETE");
		restRequest.submit();
		testVP.assertStringEquals(String.valueOf(restRequest.getResponseStatusCode()),"204", 
				"Response Code is correct for DELETE fund Confirmation request");
		
		testVP.assertStringEquals(restRequest.getResponseHeader("x-fapi-interaction-id"),PropertyUtils.getProperty("inter_id"), 
				"x-fapi-interaction-id value in the response header is matching with one sent in the request");
		testVP.testResultFinalize();			

	}
}