package com.psd2.tests.cisp.PostFundConfConsent;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.psd2.core.TestBase;
import com.psd2.logger.TestListener;
import com.psd2.logger.TestLogger;
import com.psd2.wrapper.FundConfirmationConsent;

/**
 * Class Description : Verification of MANDATORY fields haven't sent
 * @author Alok Kumar
 *
 */

@Listeners( { TestListener.class })
@Test(groups={"Regression"})
public class CISP_PFCC_045 extends TestBase
{
	@Test
	public void m_CISP_PFCC_045() throws Throwable{	
		
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
		fundConfConsent.setSchemeName("RemoveField");
		fundConfConsent.submit();
		
		testVP.assertStringEquals(String.valueOf(fundConfConsent.getResponseStatusCode()),"400", 
				"Response Code is correct for fund Confiramtion Consent when SchemeName is not passed in body");
		testVP.assertStringEquals(fundConfConsent.getResponseNodeStringByPath("Errors[0].ErrorCode"),"UK.OBIE.Field.Missing", 
				"Error Code is correct for fund Confirmation Consent when the value of Identification is blank");
		testVP.assertStringEquals(fundConfConsent.getResponseNodeStringByPath("Errors[0].Message"),"Error validating JSON. Error: - Missing required field \"SchemeName\"", 
				"Error Message is correct for fund Confirmation Consent when the value of Identification is blank");
		TestLogger.logBlankLine();
		
		TestLogger.logStep("[Step 3] : Fund confirmation consent....");
		fundConfConsent = new FundConfirmationConsent(); 
		fundConfConsent.setBaseURL(apiConst.fc_endpoint);
		fundConfConsent.setHeadersString("Authorization:Bearer "+access_token);
		fundConfConsent.setIdentification("RemoveField");
		fundConfConsent.submit();
		
		testVP.assertStringEquals(String.valueOf(fundConfConsent.getResponseStatusCode()),"400", 
				"Response Code is correct for fund Confiramtion Consent when Identification is not passed in body");
		testVP.assertStringEquals(fundConfConsent.getResponseNodeStringByPath("Errors[0].ErrorCode"),"UK.OBIE.Field.Missing", 
				"Error Code is correct for fund Confirmation Consent when the value of Identification is blank");
		testVP.assertStringEquals(fundConfConsent.getResponseNodeStringByPath("Errors[0].Message"),"Error validating JSON. Error: - Missing required field \"Identification\"", 
				"Error Message is correct for fund Confirmation Consent when the value of Identification is blank");
		TestLogger.logBlankLine();
		
		TestLogger.logStep("[Step 4] : Fund confirmation consent....");
		fundConfConsent = new FundConfirmationConsent(); 
		fundConfConsent.setBaseURL(apiConst.fc_endpoint);
		fundConfConsent.setHeadersString("Authorization:Bearer "+access_token);
		fundConfConsent.setName("RemoveField");
		fundConfConsent.submit();
		
		testVP.assertStringEquals(String.valueOf(fundConfConsent.getResponseStatusCode()),"201", 
				"Response Code is correct for fund Confiramtion Consent when Name is not passed in body");
		TestLogger.logBlankLine();
		
		TestLogger.logStep("[Step 5] : Fund confirmation consent....");
		fundConfConsent = new FundConfirmationConsent(); 
		fundConfConsent.setBaseURL(apiConst.fc_endpoint);
		fundConfConsent.setHeadersString("Authorization:Bearer "+access_token);
		fundConfConsent.setSecondaryIdentification("RemoveField");
		fundConfConsent.submit();
		
		testVP.assertStringEquals(String.valueOf(fundConfConsent.getResponseStatusCode()),"201", 
				"Response Code is correct for fund Confiramtion Consent when SecondaryIdentification is not passed in body");
		TestLogger.logBlankLine();
		
		testVP.testResultFinalize();
	}				
}
