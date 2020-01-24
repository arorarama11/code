package com.psd2.tests.cisp.FundConfirmation;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.psd2.core.TestBase;
import com.psd2.logger.TestListener;
import com.psd2.logger.TestLogger;
import com.psd2.utils.API_E2E_Utility;

/**
 * Class Description : Verification of Fund Confirmation response for expired consent 
 * @author Alok Kumar
 *
 */

@Listeners( { TestListener.class })
@Test(groups={"Regression"})
public class CISP_FND_CNF_036 extends TestBase{	
	
	API_E2E_Utility util = new API_E2E_Utility();

	@Test
	public void m_CISP_FND_CNF_036() throws Throwable{	
		
		TestLogger.logStep("[Step 1] : Fund confirmation consent....");
		consentDetails = util.generateCISPConsent(false,false,true);	
		TestLogger.logBlankLine();
		
		TestLogger.logStep("[Step 2] : Check Fund Confirmation request... ");
	
		fundConf.setBaseURL(apiConst.fcc_endpoint);
		fundConf.setHeadersString("Authorization:Bearer "+consentDetails.get("cisp_access_token"));
		fundConf.setConsentIdField(consentDetails.get("consentId"));
		fundConf.submit();
	
		testVP.assertStringEquals(String.valueOf(fundConf.getResponseStatusCode()),"403", 
				"Response Code is correct for expired consent in Fund Confirmation Request");
		
		testVP.testResultFinalize();
	}
}