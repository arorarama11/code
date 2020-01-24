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
 * Class Description : Verification of Fund Confirmation response for without passing x-fapi-financial-id header
 * @author Alok Kumar
 *
 */

@Listeners( { TestListener.class })
@Test(groups={"Regression"},dependsOnGroups={"Pre_CISP_FND_CNF"})
public class CISP_FND_CNF_039 extends TestBase{	
	
	@BeforeClass
	public void loadTestData() throws Throwable{
		
		access_token = createNewAccessToken(apiConst.rt_endpoint, API_Constant.getCisp_RefreshToken(),
				PropertyUtils.getProperty("client_id"),PropertyUtils.getProperty("client_secret"));
		testVP.assertTrue(access_token != null,	"Access token is not null");
	}
	
	@Test
	public void m_CISP_FND_CNF_039() throws Throwable{
		
		TestLogger.logStep("[Step 1] : Verification of Fund Confirmation response for without passing x-fapi-financial-id header");
		restRequest.setURL(apiConst.fcc_endpoint);
		restRequest.setHeadersString("Authorization:Bearer "+access_token+",Content-Type:application/json,Accept:application/json");
		fundConf.setConsentIdField(API_Constant.getConsentId());
		restRequest.setMethod("POST");
		restRequest.setRequestBody(fundConf.genRequestBody());
		restRequest.submit();
	
		testVP.assertStringEquals(String.valueOf(restRequest.getResponseStatusCode()),"201", 
				"Response Code is correct when x-fapi-financial-id header is not passed");
		testVP.testResultFinalize();
	}
}