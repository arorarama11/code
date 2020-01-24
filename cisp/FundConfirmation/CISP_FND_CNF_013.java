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
 * Class Description : Verification of Fund Confirmation request with blank value of Amount field under InstructedAmount array
 * @author Alok Kumar
 *
 */

@Listeners( { TestListener.class })
@Test(groups={"Regression"}, dependsOnGroups={"Pre_CISP_FND_CNF"})
public class CISP_FND_CNF_013 extends TestBase
{
	@BeforeClass
	public void loadTestData() throws Throwable{
		
		access_token = createNewAccessToken(apiConst.rt_endpoint, API_Constant.getCisp_RefreshToken(),
				PropertyUtils.getProperty("client_id"),PropertyUtils.getProperty("client_secret"));
		testVP.assertTrue(access_token != null,	"Access token is not null");
	}
	
	@Test
	public void m_CISP_FND_CNF_013() throws Throwable{	
		
		TestLogger.logStep("[Step 1] : Check Fund Confirmation request... ");
		
		fundConf.setBaseURL(apiConst.fcc_endpoint);
		fundConf.setHeadersString("Authorization:Bearer "+access_token);
		fundConf.setConsentIdField(API_Constant.getConsentId());
		fundConf.setAmount(" ");
		fundConf.submit();
	
		testVP.assertStringEquals(String.valueOf(fundConf.getResponseStatusCode()),"400", 
				"Response Code is correct for Fund Confiramtion Request when the amount field value under InstructedAmount array is blank");
		testVP.assertStringEquals(fundConf.getResponseNodeStringByPath("Errors[0].ErrorCode"),"UK.OBIE.Field.Invalid", 
				"Error Code is correct with blank amount for fund Confirmation request");
		testVP.assertStringEquals(fundConf.getResponseNodeStringByPath("Errors[0].Message"),"Error validating JSON. Error: - Invalid value ' '. Expected ^\\d{1,13}\\.\\d{1,5}$", 
				"Error Message is correct with blank amount for fund Confirmation request");
		TestLogger.logBlankLine();
		testVP.testResultFinalize();
	}
}
