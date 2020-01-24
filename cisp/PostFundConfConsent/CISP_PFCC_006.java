package com.psd2.tests.cisp.PostFundConfConsent;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.psd2.core.TestBase;
import com.psd2.logger.TestListener;
import com.psd2.logger.TestLogger;
import com.psd2.utils.PropertyUtils;

/**
 * Class Description : Checking the request (UTF-8 character encoded) status through POST method with mandatory and optional fields.
 * @author Alok Kumar
 *
 */

@Listeners( { TestListener.class })
@Test(groups={"Regression"})
public class CISP_PFCC_006 extends TestBase
{
	@Test
	public void m_CISP_PFCC_006() throws Throwable{	
		
		TestLogger.logStep("[Step 1] : Creating client credetials....");
		
		createClientCred.setBaseURL(apiConst.cc_endpoint);
		createClientCred.setScope("fundsconfirmations");
		createClientCred.submit();
		
		testVP.assertStringEquals(String.valueOf(createClientCred.getResponseStatusCode()),"200", 
				"Response Code is correct for client credetials");
		access_token = createClientCred.getAccessToken();
		TestLogger.logVariable("AccessToken : " + access_token);	
		TestLogger.logBlankLine();
		
		TestLogger.logStep("[Step 2] : Fund confirmation consent....");
		
		fundConfConsent.setBaseURL(apiConst.fc_endpoint);
		fundConfConsent.setHeadersString("Authorization:Bearer "+access_token);
		fundConfConsent.addHeaderEntry("x-fapi-customer-ip-address",PropertyUtils.getProperty("cust_ip_add"));
		fundConfConsent.addHeaderEntry("x-fapi-customer-last-logged-time",PropertyUtils.getProperty("cust_last_log_time"));
		fundConfConsent.addHeaderEntry("x-fapi-interaction-id",PropertyUtils.getProperty("inter_id"));
		fundConfConsent.submit();
		
		testVP.assertStringEquals(String.valueOf(fundConfConsent.getResponseStatusCode()),"201", 
				"Response Code is correct for fund Confiramtion Consent when all the values of request payload are valid");
		
		consentId = fundConfConsent.getConsentId();
		TestLogger.logVariable("Fund Confirmation Consent : " + consentId);	
		
		testVP.assertTrue(!(fundConfConsent.getResponseNodeStringByPath("Data.ConsentId").isEmpty()), 
				"Mandatory field i.e ConsentId is present and is not null - " +fundConfConsent.getResponseNodeStringByPath("Data.ConsentId"));
		
		testVP.assertTrue(!(fundConfConsent.getResponseNodeStringByPath("Data.CreationDateTime").isEmpty()), 
				"Mandatory field i.e CreationDateTime is present and is not null - " +fundConfConsent.getResponseNodeStringByPath("Data.CreationDateTime"));
		
		testVP.assertTrue(!(fundConfConsent.getResponseNodeStringByPath("Data.Status").isEmpty()), 
				"Mandatory field i.e Status is present and is not null - " +fundConfConsent.getResponseNodeStringByPath("Data.Status"));
		
		testVP.assertTrue(!(fundConfConsent.getResponseNodeStringByPath("Data.StatusUpdateDateTime").isEmpty()), 
				"Mandatory field i.e StatusUpdateDateTime is present and is not null - " +fundConfConsent.getResponseNodeStringByPath("Data.StatusUpdateDateTime"));
		
		testVP.assertTrue(!(fundConfConsent.getResponseNodeStringByPath("Data.ExpirationDateTime").isEmpty()), 
				"Mandatory field i.e ExpirationDateTime is present and is not null - " +fundConfConsent.getResponseNodeStringByPath("Data.ExpirationDateTime"));
		
		testVP.assertTrue(!(fundConfConsent.getResponseNodeStringByPath("Data.DebtorAccount.SchemeName").isEmpty()), 
				"Mandatory field i.e SchemeName is present and is not null - " +fundConfConsent.getResponseNodeStringByPath("Data.DebtorAccount.SchemeName"));
		
		testVP.assertTrue(!(fundConfConsent.getResponseNodeStringByPath("Data.DebtorAccount.Identification").isEmpty()), 
				"Mandatory field i.e Identification is present and is not null - " +fundConfConsent.getResponseNodeStringByPath("Data.DebtorAccount.Identification"));
		
		testVP.assertTrue(!(fundConfConsent.getResponseNodeStringByPath("Data.DebtorAccount.Name").isEmpty()), 
				"Mandatory field i.e Name is present and is not null - " +fundConfConsent.getResponseNodeStringByPath("Data.DebtorAccount.Name"));
		
		testVP.assertTrue(!(fundConfConsent.getResponseNodeStringByPath("Data.DebtorAccount.SecondaryIdentification").isEmpty()), 
				"Mandatory field i.e SecondaryIdentification is present and is not null - " +fundConfConsent.getResponseNodeStringByPath("Data.DebtorAccount.SecondaryIdentification"));
		
		testVP.assertTrue(!(fundConfConsent.getResponseNodeStringByPath("Links.Self").isEmpty()), 
				"Mandatory field i.e Self in Links field is present and is not null - " +fundConfConsent.getResponseNodeStringByPath("Links.Self"));
		
		TestLogger.logBlankLine();
		
		testVP.testResultFinalize();			

	}
}
