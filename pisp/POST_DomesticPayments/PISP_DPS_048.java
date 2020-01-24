package com.psd2.tests.pisp.POST_DomesticPayments;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.psd2.core.TestBase;
import com.psd2.logger.TestListener;
import com.psd2.logger.TestLogger;
import com.psd2.utils.API_E2E_Utility;

/**
 * Class Description : Verification of the values of OPTIONAL Initiation/LocalInstrument field having OB defined values
 * @author Rama Arora
 *
 */

@Listeners( { TestListener.class })
@Test(groups={"Regression"})
public class PISP_DPS_048 extends TestBase{	
	
	API_E2E_Utility apiUtility = new API_E2E_Utility();
	
	@Test
	public void m_PISP_DPS_048() throws Throwable{	
	
			
		 TestLogger.logStep("[Step 1] : Create Domestic Payment Consent");
			
	        consentDetails=apiUtility.generatePayments(false, apiConst.domesticPayments, false, false);
			TestLogger.logBlankLine();
			
			TestLogger.logStep("[Step 2] : POST Domestic Payment Submission");
			
			paymentConsent.setBaseURL(apiConst.dps_endpoint);
			paymentConsent.setHeadersString("Authorization:Bearer "+consentDetails.get("api_access_token"));
			paymentConsent.setLocalInstrument("UK.OBIE.Link");
			paymentConsent.setConsentId(consentDetails.get("consentId"));
			paymentConsent.submit();
		
		    TestLogger.logStep("[Step 3] : Verification of the values of MANDATORY Initiation/LocalInstrument field having length variation");
			
		    testVP.assertStringEquals(String.valueOf(paymentConsent.getResponseStatusCode()),"201", 
					"Response Code is correct for Domestic Payment URI");
		    
		    testVP.assertTrue(paymentConsent.getResponseNodeStringByPath("Data.Initiation.LocalInstrument").equals("UK.OBIE.Link") 
					|| paymentConsent.getResponseNodeStringByPath("Data.Initiation.LocalInstrument").equals("UK.OBIE.FPS") 
					|| paymentConsent.getResponseNodeStringByPath("Data.Initiation.LocalInstrument").equals("UK.OBIE.BACS")
					|| paymentConsent.getResponseNodeStringByPath("Data.Initiation.LocalInstrument").equals("UK.OBIE.CHAPS") 
					|| paymentConsent.getResponseNodeStringByPath("Data.Initiation.LocalInstrument").equals("UK.OBIE.Paym") 
					|| paymentConsent.getResponseNodeStringByPath("Data.Initiation.LocalInstrument").equals("UK.OBIE.BalanceTransfer")
					|| paymentConsent.getResponseNodeStringByPath("Data.Initiation.LocalInstrument").equals("UK.OBIE.MoneyTransfer"),
					"LocalInstrument field value is correct i.e. "+paymentConsent.getResponseNodeStringByPath("Data.Initiation[0].LocalInstrument"));
			
		    TestLogger.logBlankLine();
		
		    testVP.testResultFinalize();		
	}
}
