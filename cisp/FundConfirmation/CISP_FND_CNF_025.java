package com.psd2.tests.cisp.FundConfirmation;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.psd2.core.TestBase;
import com.psd2.logger.TestListener;
import com.psd2.logger.TestLogger;
import com.psd2.utils.API_Constant;
import com.psd2.utils.Misc;
import com.psd2.utils.PropertyUtils;

/**
 * Class Description : Verification of Fund Confirmation response for CreationDateTime value
 * @author Alok Kumar
 *
 */

@Listeners( { TestListener.class })
@Test(groups={"Regression"},dependsOnGroups={"Pre_CISP_FND_CNF"})
public class CISP_FND_CNF_025 extends TestBase{	
	
	@BeforeClass
	public void loadTestData() throws Throwable{
		
		access_token = createNewAccessToken(apiConst.rt_endpoint, API_Constant.getCisp_RefreshToken(),
				PropertyUtils.getProperty("client_id"),PropertyUtils.getProperty("client_secret"));
		testVP.assertTrue(access_token != null,	"Access token is not null");
	}
	
	@Test
	public void m_CISP_FND_CNF_025() throws Throwable{	
				
		TestLogger.logStep("[Step 1] : Verification of Fund Confirmation response for CreationDateTime value");
	
		fundConf.setBaseURL(apiConst.fcc_endpoint);
		fundConf.setHeadersString("Authorization:Bearer "+access_token);
		fundConf.setConsentIdField(API_Constant.getConsentId());
		fundConf.submit();
	
		testVP.assertStringEquals(String.valueOf(fundConf.getResponseStatusCode()),"201", 
				"Response Code is correct for Fund Confiramtion Request");
		String creationDate = String.valueOf(fundConf.getResponseValueByPath("Data.CreationDateTime"));
		TestLogger.logVariable("CreationDateTime : "+creationDate);
		
		testVP.assertTrue(Misc.verifyDateTimeFormat(creationDate.split("T")[0], "yyyy-MM-dd") && 
				Misc.verifyDateTimeFormat(creationDate.split("T")[1], "HH:mm:ss+00:00"),"CreationDateTime is as per expected format");
				
		testVP.testResultFinalize();
	}
}