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
 * Class Description : Verification of Fund Confirmation request with all required fields in request body and all mandatory  and optional headers 
 * @author Alok Kumar
 *
 */

@Listeners( { TestListener.class })
@Test(groups={"Regression"},dependsOnGroups={"Pre_CISP_FND_CNF"})
public class CISP_FND_CNF_004 extends TestBase{	
	
	@BeforeClass
	public void loadTestData() throws Throwable{
		
		access_token = createNewAccessToken(apiConst.rt_endpoint, API_Constant.getCisp_RefreshToken(),
				PropertyUtils.getProperty("client_id"),PropertyUtils.getProperty("client_secret"));
		testVP.assertTrue(access_token != null,	"Access token is not null");
	}
	@Test
	public void m_CISP_FND_CNF_004() throws Throwable{	
		
		TestLogger.logStep("[Step 1] : Check Fund Confirmation request... ");
	
		fundConf.setBaseURL(apiConst.fcc_endpoint);
		fundConf.setHeadersString("Authorization:Bearer "+access_token);
		fundConf.setConsentIdField(API_Constant.getConsentId());
		fundConf.addHeaderEntry("Accept", "application/json");
		fundConf.addHeaderEntry("x-fapi-customer-ip-address",PropertyUtils.getProperty("cust_ip_add"));
		fundConf.addHeaderEntry("x-fapi-customer-last-logged-time",PropertyUtils.getProperty("cust_last_log_time"));
		fundConf.addHeaderEntry("x-fapi-interaction-id",PropertyUtils.getProperty("inter_id"));
		fundConf.submit();
	
		testVP.assertStringEquals(String.valueOf(fundConf.getResponseStatusCode()),"201", 
				"Response Code is correct with valid request body along with the optional and mandatory headers");
		
		testVP.testResultFinalize();
	}
}