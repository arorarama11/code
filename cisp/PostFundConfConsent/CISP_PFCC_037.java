package com.psd2.tests.cisp.PostFundConfConsent;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.psd2.core.TestBase;
import com.psd2.logger.TestListener;
import com.psd2.logger.TestLogger;
import com.psd2.utils.Misc;

/**
 * Class Description : Verification of 'StatusUpdateDateTime' date field values
 * @author Alok Kumar
 *
 */

@Listeners( { TestListener.class })
@Test(groups={"Regression"})
public class CISP_PFCC_037 extends TestBase{	
	
	@Test
	public void m_CISP_PFCC_037() throws Throwable{	

		TestLogger.logStep("[Step 1] : Creating client credetials....");
		
		createClientCred.setBaseURL(apiConst.cc_endpoint);
		createClientCred.setScope("fundsconfirmations");
		createClientCred.submit();
		
		testVP.assertStringEquals(String.valueOf(createClientCred.getResponseStatusCode()),"200", 
				"Response Code is correct for client credetials");
		access_token = createClientCred.getAccessToken();
		TestLogger.logVariable("AccessToken : " + access_token);	
		TestLogger.logBlankLine();
		
		TestLogger.logStep("[Step 2] : Verification of 'StatusUpdateDateTime' date field values");
		
		fundConfConsent.setBaseURL(apiConst.fc_endpoint);		
		fundConfConsent.setHeadersString("Authorization:Bearer "+access_token);		
		fundConfConsent.submit();
		
		testVP.assertStringEquals(String.valueOf(fundConfConsent.getResponseStatusCode()),"201", 
				"Response Code is correct for fund Confiramtion Consent");
		testVP.assertTrue(Misc.verifyDateTimeFormat(fundConfConsent.getResponseNodeStringByPath("Data.StatusUpdateDateTime").split("T")[0], "yyyy-MM-dd") && 
				Misc.verifyDateTimeFormat(fundConfConsent.getResponseNodeStringByPath("Data.StatusUpdateDateTime").split("T")[1], "HH:mm:ss+00:00"), 
				"StatusUpdateDateTime is as per expected format");
		TestLogger.logInfo("The StatusUpdateDateTime for the account request Id: "+fundConfConsent.getResponseNodeStringByPath("Data.StatusUpdateDateTime"));
		
		TestLogger.logBlankLine();
		
		testVP.testResultFinalize();			

	}
}