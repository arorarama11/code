package com.psd2.tests.cisp.GetFundConfConsent;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.psd2.core.TestBase;
import com.psd2.logger.TestListener;
import com.psd2.logger.TestLogger;
import com.psd2.utils.API_Constant;
import com.psd2.utils.PropertyUtils;

/**
 * Class Description : Verification of 'Status' field value - "Authorized"
 * @author Alok Kumar
 *
 */

@Listeners( { TestListener.class })
@Test(groups={"Regression"})
public class CISP_GFCC_032 extends TestBase{	
	
	@BeforeClass
	public void loadTestData() throws Throwable{
		
		access_token = createNewAccessToken(apiConst.rt_endpoint, API_Constant.getCisp_RefreshToken(),
				PropertyUtils.getProperty("client_id"),PropertyUtils.getProperty("client_secret"));
		testVP.assertTrue(access_token != null,	"Access token is not null");
	}	
	@Test
	public void m_CISP_GFCC_032() throws Throwable{	
		
		TestLogger.logStep("[Step 1] : Verification of Status field value - Authorized ");
	
		getFundConfConsent.setBaseURL(apiConst.fc_endpoint+"/"+API_Constant.getConsentId());
		getFundConfConsent.setHeadersString("Authorization:Bearer "+API_Constant.getCisp_CC_AccessToken());
		getFundConfConsent.submit();
	
		testVP.assertStringEquals(String.valueOf(getFundConfConsent.getResponseStatusCode()),"200", 
				"Response Code is correct for fund Confiramtion Consent");
		testVP.assertStringEquals(String.valueOf(getFundConfConsent.getResponseValueByPath("Data.Status")), "Authorised", 
				"Status field value - Authorized verified");
	
		testVP.testResultFinalize();
	}
}