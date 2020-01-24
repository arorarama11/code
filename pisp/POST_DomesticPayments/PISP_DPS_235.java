package com.psd2.tests.pisp.POST_DomesticPayments;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.psd2.core.TestBase;
import com.psd2.logger.TestListener;
import com.psd2.logger.TestLogger;
import com.psd2.utils.API_Constant;

/**
 * Class Description : Verification of the values into MANDATORY ChargeBearer field where Request has sent successfully and returned a HTTP Code 201 Created if FirstPaymentAmount/Currency is USD
 * @author Rama Arora
 *
 */

@Listeners( { TestListener.class })
@Test(groups={"Regression"})
public class PISP_DPS_235 extends TestBase {	
	
	@Test
	public void m_PISP_DPS_235() throws Throwable{	
		
        TestLogger.logStep("[Step 1-1] : Creating client credetials....");
		
		createClientCred.setBaseURL(apiConst.cc_endpoint);
		createClientCred.setScope("payments");
		createClientCred.submit();
		
		testVP.assertStringEquals(String.valueOf(createClientCred.getResponseStatusCode()),"200","Response Code is correct for client credetials");
		cc_token = createClientCred.getAccessToken();
		TestLogger.logVariable("AccessToken : " + cc_token);
		API_Constant.setPisp_CC_AccessToken(cc_token);	
		TestLogger.logBlankLine();
		
		TestLogger.logStep("[Step 1-2] : Domestic Payment Consent SetUp....");
		
		paymentConsent.setBaseURL(apiConst.dpc_endpoint);
		paymentConsent.setHeadersString("Authorization:Bearer "+cc_token);
		paymentConsent.setCurrency("USD");
		paymentConsent.submit();
		
		testVP.assertStringEquals(String.valueOf(paymentConsent.getResponseStatusCode()),"201",
				"Response Code is correct for Domestic Payment URI when InstructedAmount/Amount/Currency as USD");

		testVP.assertStringEquals(String.valueOf(paymentConsent.getResponseValueByPath("Data.Status")), "Rejected",
				"Mandatory field Status as Rejected");
		
		testVP.assertStringEquals(String.valueOf(paymentConsent.getResponseValueByPath("Data.Charges[0].ChargeBearer")), "BorneByCreditor",
				"Mandatory field ChargeBearer as BorneByCreditor");
		
		TestLogger.logBlankLine();		
		testVP.testResultFinalize();		
	}
}
