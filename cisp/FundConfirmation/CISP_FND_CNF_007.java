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
 * Class Description : Verification of Fund Confirmation request with blank ConsentId and valid value for rest of the fields in request body 
 * @author Alok Kumar
 *
 */

@Listeners( { TestListener.class })
@Test(groups={"Regression"},dependsOnGroups={"Pre_CISP_FND_CNF"})
public class CISP_FND_CNF_007 extends TestBase{	
	
	@BeforeClass
	public void loadTestData() throws Throwable{
		
		access_token = createNewAccessToken(apiConst.rt_endpoint, API_Constant.getCisp_RefreshToken(),
				PropertyUtils.getProperty("client_id"),PropertyUtils.getProperty("client_secret"));
		testVP.assertTrue(access_token != null,	"Access token is not null");
	}
	
	@Test
	public void m_CISP_FND_CNF_007() throws Throwable{	
				
		TestLogger.logStep("[Step 1] : Check Fund Confirmation request... ");
	
		fundConf.setBaseURL(apiConst.fcc_endpoint);
		fundConf.setHeadersString("Authorization:Bearer "+access_token);
		fundConf.setConsentIdField("");
		fundConf.submit();
	
		testVP.assertStringEquals(String.valueOf(fundConf.getResponseStatusCode()),"400", 
				"Response Code is correct with blank consent id");
		testVP.assertStringEquals(fundConf.getResponseNodeStringByPath("Errors[0].ErrorCode"),"UK.OBIE.Field.Invalid", 
				"Error Code is correct with blank consent id for fund Confirmation request");
		testVP.assertStringEquals(fundConf.getResponseNodeStringByPath("Errors[0].Message"),"Error validating JSON. Error: - Expected min length 1", 
				"Error Message is correct with blank consent id for fund Confirmation request");
		
		testVP.testResultFinalize();
	}
}