package com.psd2.tests.cisp.PostFundConfConsent;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.psd2.core.TestBase;
import com.psd2.logger.TestListener;
import com.psd2.logger.TestLogger;
import com.psd2.utils.Misc;

/**
 * Class Description : Verification of 'ExpirationDateTime' date field values
 * @author Alok Kumar
 *
 */

@Listeners( { TestListener.class })
@Test(groups={"Regression"})
public class CISP_PFCC_031 extends TestBase
{
	@Test
	public void m_CISP_PFCC_031() throws Throwable{	
		
		TestLogger.logStep("[Step 1] : Creating client credetials....");
		
		createClientCred.setBaseURL(apiConst.cc_endpoint);
		createClientCred.setScope("fundsconfirmations");
		createClientCred.submit();
		
		testVP.assertStringEquals(String.valueOf(createClientCred.getResponseStatusCode()),"200", 
				"Response Code is correct for client credetials");
		access_token = createClientCred.getAccessToken();
		TestLogger.logVariable("AccessToken : " + access_token);	
		TestLogger.logBlankLine();
		
		TestLogger.logStep("[Step 2] : Fund confirmation consent....");
		
		fundConfConsent.setBaseURL(apiConst.fc_endpoint);
		fundConfConsent.setHeadersString("Authorization:Bearer "+access_token);
		fundConfConsent.submit();
		
		testVP.assertStringEquals(String.valueOf(fundConfConsent.getResponseStatusCode()),"201", 
				"Response Code is correct for fund Confiramtion Consent when all the values of request payload are valid");
		
		
		TestLogger.logStep("[Step 3] : Verification of 'ExpirationDateTime' format");		
		
		String expirationDate = fundConfConsent.getExpirationDateTime();
		TestLogger.logVariable("ExpirationDateTime : "+expirationDate);
		
		testVP.assertTrue(Misc.verifyDateTimeFormat(expirationDate.split("T")[0], "yyyy-MM-dd") && 
				Misc.verifyDateTimeFormat(expirationDate.split("T")[1], "HH:mm:ss+00:00"),"ExpirationDateTime is as per expected format");
		
		TestLogger.logBlankLine();
		
		testVP.testResultFinalize();
	}
}
