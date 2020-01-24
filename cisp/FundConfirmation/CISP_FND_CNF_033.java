package com.psd2.tests.cisp.FundConfirmation;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.psd2.core.TestBase;
import com.psd2.logger.TestListener;
import com.psd2.logger.TestLogger;
import com.psd2.utils.API_Constant;

/**
 * Class Description : Verification of Fund Confirmation response for other tpp's access token 
 * @author Alok Kumar
 *
 */

@Listeners( { TestListener.class })
@Test(groups={"Regression"},dependsOnGroups={"Pre_CISP_FND_CNF"})
public class CISP_FND_CNF_033 extends TestBase{	
	
	
	@Test
	public void m_CISP_FND_CNF_033() throws Throwable{	
		
		TestLogger.logStep("[Step 1] : Check Fund Confirmation request... ");
	
		fundConf.setBaseURL(apiConst.fcc_endpoint);
		fundConf.setHeadersString("Authorization:Bearer "+apiConst.othertpp_cc_access_token);
		fundConf.setConsentIdField(API_Constant.getConsentId());
		fundConf.submit();
	
		testVP.assertStringEquals(String.valueOf(fundConf.getResponseStatusCode()),"401", 
				"Response Code is correct with other tpp access token");
			
		testVP.testResultFinalize();
	}
}