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
 * Class Description : Verification of Fund Confirmation request with invalid value for Currency field under InstructedAmount array
 * @author Alok Kumar
 *
 */

@Listeners( { TestListener.class })
@Test(groups={"Regression"}, dependsOnGroups={"Pre_CISP_FND_CNF"})
public class CISP_FND_CNF_019 extends TestBase
{
	@BeforeClass
	public void loadTestData() throws Throwable{
		
		access_token = createNewAccessToken(apiConst.rt_endpoint, API_Constant.getCisp_RefreshToken(),
				PropertyUtils.getProperty("client_id"),PropertyUtils.getProperty("client_secret"));
		testVP.assertTrue(access_token != null,	"Access token is not null");
	}
	
	@Test
	public void m_CISP_FND_CNF_019() throws Throwable{	
		
		TestLogger.logStep("[Step 1] : Check Fund Confirmation request... ");
		
		fundConf.setBaseURL(apiConst.fcc_endpoint);
		fundConf.setHeadersString("Authorization:Bearer "+access_token);
		fundConf.setConsentIdField(API_Constant.getConsentId());
		fundConf.setCurrency("GBPS");
		fundConf.submit();
	
		testVP.assertStringEquals(String.valueOf(fundConf.getResponseStatusCode()),"400", 
				"Response Code is correct for Fund Confiramtion Request when the value of Currency field is in invalid form");
		testVP.assertStringEquals(fundConf.getResponseNodeStringByPath("Errors[0].ErrorCode"),"UK.OBIE.Field.Invalid", 
				"Error Code is correct for Fund Confiramtion Request when the value of Currency field is in invalid form");
		testVP.assertStringEquals(fundConf.getResponseNodeStringByPath("Errors[0].Message"),"Error validating JSON. Error: - Invalid value 'GBPS'. Expected ^[A-Z]{3,3}$", 
				"Error Message is correct for Fund Confiramtion Request when the value of Currency field is in invalid form");
	
		TestLogger.logBlankLine();
		testVP.testResultFinalize();
	}
}
