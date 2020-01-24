package com.psd2.tests.cisp.DeleteFundConfConsent;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.psd2.core.TestBase;
import com.psd2.logger.TestListener;
import com.psd2.logger.TestLogger;
import com.psd2.utils.API_E2E_Utility;
import com.psd2.utils.PropertyUtils;

/**
 * Class Description : Valid ConsentId but not associated with TPP originating the request 
 * @author Alok Kumar
 *
 */

@Listeners( { TestListener.class })
@Test(groups={"Regression"})
public class CISP_DFCC_010 extends TestBase{	
	
	API_E2E_Utility util = new API_E2E_Utility();
	
	@Test
	public void m_CISP_DFCC_010() throws Throwable{	
		
		TestLogger.logStep("[Step 1] : Fund confirmation consent....");
		consentDetails = util.generateCISPConsent(true,false,false);	
		TestLogger.logBlankLine();
		
		TestLogger.logStep("[Step 2] : Delete Fund confirmation consent....");
		
		restRequest.setURL(apiConst.fc_endpoint+"/"+PropertyUtils.getProperty("othertpp_ConsentId"));
		restRequest.setHeadersString("Authorization:Bearer "+consentDetails.get("cc_access_token")+""
				+ ",x-fapi-financial-id:"+PropertyUtils.getProperty("fin_id")
				+",Content-Type:application/json");
		restRequest.setMethod("DELETE");
		restRequest.submit();
		testVP.assertStringEquals(String.valueOf(restRequest.getResponseStatusCode()),"403", 
				"Response Code is correct with consent id exist in other tpp for Delete fund Confirmation Consent request");
		
		testVP.testResultFinalize();			

	}
}

