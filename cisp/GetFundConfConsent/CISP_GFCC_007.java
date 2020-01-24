package com.psd2.tests.cisp.GetFundConfConsent;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.psd2.core.TestBase;
import com.psd2.logger.TestListener;
import com.psd2.logger.TestLogger;
import com.psd2.utils.API_E2E_Utility;
import com.psd2.utils.PropertyUtils;

/**
 * Class Description : Checking the request status through other than GET method with mandatory and optional fields. 
 * @author Alok Kumar
 *
 */

@Listeners( { TestListener.class })
@Test(groups={"Regression"})
public class CISP_GFCC_007 extends TestBase{	
	
	API_E2E_Utility util = new API_E2E_Utility();
	
	@Test
	public void m_CISP_GFCC_007() throws Throwable{	
		
		TestLogger.logStep("[Step 1] : Fund confirmation consent....");
		consentDetails = util.generateCISPConsent(true,false,false);	
		TestLogger.logBlankLine();
		
		TestLogger.logStep("[Step 2] : Get Fund confirmation consent....");
		
		getFundConfConsent.setBaseURL(apiConst.fc_endpoint+"/"+consentDetails.get("consentId"));
		getFundConfConsent.setHeadersString("Authorization:Bearer "+consentDetails.get("cc_access_token"));
		getFundConfConsent.addHeaderEntry("x-fapi-customer-ip-address",PropertyUtils.getProperty("cust_ip_add"));
		getFundConfConsent.addHeaderEntry("x-fapi-customer-last-logged-time",PropertyUtils.getProperty("cust_last_log_time"));
		getFundConfConsent.addHeaderEntry("x-fapi-interaction-id",PropertyUtils.getProperty("inter_id"));
		getFundConfConsent.setMethod("POST");
		getFundConfConsent.submit();	
		
		testVP.assertStringEquals(String.valueOf(getFundConfConsent.getResponseStatusCode()),"405", 
				"Response Code is correct with invalid request method of Get fund Confirmation Consent request");
		testVP.testResultFinalize();			

	}
}

