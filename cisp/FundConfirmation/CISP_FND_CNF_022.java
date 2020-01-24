package com.psd2.tests.cisp.FundConfirmation;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.psd2.core.TestBase;
import com.psd2.logger.TestListener;
import com.psd2.logger.TestLogger;
import com.psd2.utils.API_Constant;
import com.psd2.utils.PropertyUtils;

/**
 * Class Description : Verification of Fund Confirmation response for FundsConfirmationId with value should not be more than 40 char
 * @author Alok Kumar
 *
 */

@Listeners( { TestListener.class })
@Test(groups={"Regression"},dependsOnGroups={"Pre_CISP_FND_CNF"})
public class CISP_FND_CNF_022 extends TestBase{	
	
	@BeforeClass
	public void loadTestData() throws Throwable{
		
		access_token = createNewAccessToken(apiConst.rt_endpoint, API_Constant.getCisp_RefreshToken(),
				PropertyUtils.getProperty("client_id"),PropertyUtils.getProperty("client_secret"));
		testVP.assertTrue(access_token != null,	"Access token is not null");
	}
	
	@Test
	public void m_CISP_FND_CNF_022() throws Throwable{	
		
		TestLogger.logStep("[Step 1] : Verification of Fund Confirmation response for FundsConfirmationId with value should not be more than 40 char");
	
		fundConf.setBaseURL(apiConst.fcc_endpoint);
		fundConf.setHeadersString("Authorization:Bearer "+access_token);
		fundConf.setConsentIdField(API_Constant.getConsentId());
		fundConf.submit();
	
		testVP.assertStringEquals(String.valueOf(fundConf.getResponseStatusCode()),"201", 
				"Response Code is correct for Fund Confiramtion Request");
		testVP.assertTrue(String.valueOf(fundConf.getResponseValueByPath("Data.FundsConfirmationId")).length()<=40, 
				"Length of FundsConfirmationId in response is within 40 characters");
		
		testVP.testResultFinalize();
	}
}