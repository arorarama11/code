package com.psd2.tests.cisp.GetFundConfConsent;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.psd2.core.TestBase;
import com.psd2.logger.TestListener;
import com.psd2.logger.TestLogger;
import com.psd2.utils.API_E2E_Utility;
import com.psd2.utils.PropertyUtils;

/**
 * Class Description : Verification of x-fapi-interaction-id value in the response header when the value sent is in incorrect format
 * @author Alok Kumar
 *
 */

@Listeners( { TestListener.class })
@Test(groups={"Regression"})
public class CISP_GFCC_021 extends TestBase
{
	API_E2E_Utility util = new API_E2E_Utility();
	
	@Test
	public void m_CISP_GFCC_021() throws Throwable{	
		
		TestLogger.logStep("[Step 1] : Fund confirmation consent....");
		consentDetails = util.generateCISPConsent(true,false,false);	
		TestLogger.logBlankLine();
		
		TestLogger.logStep("[Step 2] : Checking the response with header - x-fapi-interaction-id having value in incorrect format ");
		
		getFundConfConsent.setBaseURL(apiConst.fc_endpoint+"/"+consentDetails.get("consentId"));
		getFundConfConsent.setHeadersString("Authorization:Bearer "+consentDetails.get("cc_access_token"));
		getFundConfConsent.addHeaderEntry("x-fapi-interaction-id",PropertyUtils.getProperty("fin_id"));
		getFundConfConsent.submit();
		
		testVP.assertStringEquals(String.valueOf(getFundConfConsent.getResponseStatusCode()),"400", 
				"Response Code is correct for Get fund Confirmation Consentif the value of x-fapi-interaction-id is in incorrect format");
		
		testVP.assertStringEquals(getFundConfConsent.getResponseNodeStringByPath("Errors[0].ErrorCode"),"UK.OBIE.Header.Invalid", 
				"Error Code is correct for Get fund Confirmation Consent when x-fapi-interaction-id having value in incorrect format");
		testVP.assertStringEquals(getFundConfConsent.getResponseNodeStringByPath("Errors[0].Message"),"invalid headers found in request", 
				"Error Message is correct for Get fund Confirmation Consent when x-fapi-interaction-id having value in incorrect format");
	
		TestLogger.logBlankLine();
		testVP.testResultFinalize();			

	}
}
