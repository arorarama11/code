package com.psd2.tests.cisp.DeleteFundConfConsent;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.psd2.core.TestBase;
import com.psd2.logger.TestListener;
import com.psd2.logger.TestLogger;
import com.psd2.utils.API_E2E_Utility;
import com.psd2.utils.PropertyUtils;

/**
 * Class Description : Verification of CMA compliance for DELETE Funds Confirmation Consent URI 
 * @author Alok Kumar
 *
 */

@Listeners( { TestListener.class })
@Test(groups={"Regression","Sanity"})
public class CISP_DFCC_001 extends TestBase{	
	
	API_E2E_Utility util = new API_E2E_Utility();
	
	@Test
	public void m_CISP_DFCC_001() throws Throwable{	
		
		TestLogger.logStep("[Step 1] : Fund confirmation consent....");
		consentDetails = util.generateCISPConsent(true,false,false);	
		TestLogger.logBlankLine();
		
		TestLogger.logStep("[Step 2] : Delete Fund confirmation consent....");
		
		restRequest.setURL(apiConst.fc_endpoint+"/"+consentDetails.get("consentId"));
		restRequest.setHeadersString("Authorization:Bearer "+consentDetails.get("cc_access_token")+""
				+ ",x-fapi-financial-id:"+PropertyUtils.getProperty("fin_id")
				+",Content-Type:application/json");
		restRequest.setMethod("DELETE");
		restRequest.submit();
		testVP.assertStringEquals(String.valueOf(restRequest.getResponseStatusCode()),"204", 
				"Response Code is correct for DELETE fund Confirmation request");
		
		testVP.assertStringEquals(restRequest.getURL(),apiConst.fc_endpoint+"/"+consentDetails.get("consentId"), 
				"URI for DELETE fund Confirmation request is as per open banking standard");
		
		testVP.testResultFinalize();			

	}
}

