package com.psd2.tests.cisp.GetFundConfConsent;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.psd2.core.TestBase;
import com.psd2.logger.TestListener;
import com.psd2.logger.TestLogger;
import com.psd2.utils.API_E2E_Utility;
import com.psd2.utils.Misc;

/**
 * Class Description : Verification of 'StatusUpdateDateTime' date field values
 * @author Alok Kumar
 *
 */

@Listeners( { TestListener.class })
@Test(groups={"Regression"})
public class CISP_GFCC_030 extends TestBase{	
	
	API_E2E_Utility util = new API_E2E_Utility();
	
	@Test
	public void m_CISP_GFCC_030() throws Throwable{	
		
		TestLogger.logStep("[Step 1] : Fund confirmation consent....");
		consentDetails = util.generateCISPConsent(true,false,false);	
		TestLogger.logBlankLine();
		
		TestLogger.logStep("[Step 2] : Verification of 'StatusUpdateDateTime' date field values");
		
		getFundConfConsent.setBaseURL(apiConst.fc_endpoint+"/"+consentDetails.get("consentId"));
		getFundConfConsent.setHeadersString("Authorization:Bearer "+consentDetails.get("cc_access_token"));
		getFundConfConsent.submit();
		
		testVP.assertStringEquals(String.valueOf(getFundConfConsent.getResponseStatusCode()),"200", 
				"Response Code is correct for fund Confiramtion Consent");
		testVP.assertTrue(Misc.verifyDateTimeFormat(getFundConfConsent.getResponseNodeStringByPath("Data.StatusUpdateDateTime").split("T")[0], "yyyy-MM-dd") && 
				Misc.verifyDateTimeFormat(getFundConfConsent.getResponseNodeStringByPath("Data.StatusUpdateDateTime").split("T")[1], "HH:mm:ss+00:00"), 
				"StatusUpdateDateTime is as per expected format");
		TestLogger.logInfo("The StatusUpdateDateTime for the account request Id: "+getFundConfConsent.getResponseNodeStringByPath("Data.StatusUpdateDateTime"));
	    		
		testVP.testResultFinalize();			
	}
}
