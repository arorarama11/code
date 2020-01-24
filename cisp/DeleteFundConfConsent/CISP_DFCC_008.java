package com.psd2.tests.cisp.DeleteFundConfConsent;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.psd2.core.TestBase;
import com.psd2.logger.TestListener;
import com.psd2.logger.TestLogger;
import com.psd2.utils.API_E2E_Utility;
import com.psd2.utils.PropertyUtils;

/**
 * Class Description : Verification of request when ConsentId length is greater than 128 
 * @author Alok Kumar
 *
 */

@Listeners( { TestListener.class })
@Test(groups={"Regression"})
public class CISP_DFCC_008 extends TestBase{	
	
	API_E2E_Utility util = new API_E2E_Utility();
	
	@Test
	public void m_CISP_DFCC_008() throws Throwable{	
		
		TestLogger.logStep("[Step 1] : Fund confirmation consent....");
		consentDetails = util.generateCISPConsent(true,false,false);	
		TestLogger.logBlankLine();
		
		TestLogger.logStep("[Step 2] : Delete Fund confirmation consent....");
		
		restRequest.setURL(apiConst.fc_endpoint+"/"+consentDetails.get("consentId")+consentDetails.get("consentId")+consentDetails.get("consentId"));
		restRequest.setHeadersString("Authorization:Bearer "+consentDetails.get("cc_access_token")+""
				+ ",x-fapi-financial-id:"+PropertyUtils.getProperty("fin_id")
				+",Content-Type:application/json,Accept:application/json");
		restRequest.setMethod("DELETE");
		restRequest.submit();
		testVP.assertStringEquals(String.valueOf(restRequest.getResponseStatusCode()),"400", 
				"Response Code is correct with consent id length more than 128 char for Delete fund Confirmation Consent request");
		testVP.assertStringEquals(restRequest.getResponseNodeStringByPath("Errors[0].ErrorCode"),"UK.OBIE.Resource.NotFound", 
				"Error Code is correct with invalid consent id format for Delete fund Confirmation Consent request");
		testVP.assertStringEquals(restRequest.getResponseNodeStringByPath("Errors[0].Message"),"Validation error found with the provided input", 
				"Error Message is correct with invalid consent id format for Delete fund Confirmation Consent request");
	
		testVP.testResultFinalize();			

	}
}

