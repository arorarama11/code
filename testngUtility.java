package com.psd2.utils;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlPackage;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

public class testngUtility {
	
	List<XmlSuite> suites=null;
	TestListenerAdapter tla=null;
	TestNG tng=null;
	XmlSuite testSuite=null;
	static HashMap<String, String> testPackageMapping = null;
	
	/**
	 * This method is used create testng.xml
	 * @param classlist
	 */
	public void createXml(HashMap<String, String> map){
	
		tla = new TestListenerAdapter();
		tng = new TestNG();
		tng.addListener(tla);
        
		testSuite = new XmlSuite();
		testSuite.setName("PSD2Suite");
		testSuite.addListener("com.psd2.utils.NewReporter");   
        testSuite.setVerbose(2);
        testSuite.setPreserveOrder("true");
        testSuite.setAllowReturnValues(true);
		
        XmlTest test = null;
        List<XmlClass> myclass =  new ArrayList<XmlClass>();
        List<XmlPackage> myPackages = null;
		List<XmlTest> myTests = new ArrayList<XmlTest>();

        ///Adding pre-requisite to test case
	    test = new XmlTest(testSuite);
	    test.setName("Pre-requisite for AISP and CISP");
	    myclass.add(new XmlClass("com.psd2.utils.API_E2E_Utility"));
	    test.setClasses(myclass);
	    myTests.add(test);		  

		for (Map.Entry<String, String> entry : map.entrySet()) {
		    test = new XmlTest(testSuite);
		    myPackages = new ArrayList<XmlPackage> ();
		    test.setName(entry.getKey());
		    myPackages.add(new XmlPackage(entry.getValue()));
		    test.setXmlPackages(myPackages);
		    test.addExcludedGroup("Invalid");
		    test.addExcludedGroup("Blocked");
		    test.addIncludedGroup("Regression");
		    test.addIncludedGroup("Sanity");
		    myTests.add(test);		  
		}
        testSuite.setTests(myTests);        
	}
	
	/**
	 * This method is used to run the testng.xml
	 * @param classlist
	 */
	public void executeXml(HashMap<String, String> nmap){	
		
		createXml(nmap); // Create testng.xml file		
        List<XmlSuite> suites = new ArrayList<XmlSuite>();
        suites.add(testSuite);
        tng.setXmlSuites(suites);        
        tng.run();		// Executing testng.xml file	
	}
	
	/**
	 * This method is used to set the test packages
	 * @return 
	 */
	public static HashMap<String, String> setTestPackageMapping(){
		
		testPackageMapping = new HashMap<String, String>();
		testPackageMapping.put("AccessToken Creation Using Auth Code", "com.psd2.tests.AccessToken");
		testPackageMapping.put("AccessToken Creation Using Refresh Token", "com.psd2.tests.aisp.AccessTokenWithRefreshToken");
		testPackageMapping.put("Generate Client Credential Token", "com.psd2.tests.ClientCredentialAccessToken");
		testPackageMapping.put("Single Account Information", "com.psd2.tests.aisp.SingleAccount");
		testPackageMapping.put("Multi Account Information", "com.psd2.tests.aisp.MultiAccountInfo");
		testPackageMapping.put("Single Account Balance", "com.psd2.tests.aisp.SingleAccountBalance");
		testPackageMapping.put("Single Account Beneficiaries", "com.psd2.tests.aisp.SingleAccountBeneficiaries");
		testPackageMapping.put("Single Account Transaction", "com.psd2.tests.aisp.SingleAccountTransaction");
		testPackageMapping.put("Single Account DirectDebits", "com.psd2.tests.aisp.SingleAccountDirectDebits");
		testPackageMapping.put("Single Account Products", "com.psd2.tests.aisp.SingleAccountProducts");
		testPackageMapping.put("Single Account StandingOrders", "com.psd2.tests.aisp.SingleAccountStandingOrders");
		testPackageMapping.put("Single Account ScheduledPayment", "com.psd2.tests.aisp.SingleAccountScheduledPayments");
		testPackageMapping.put("Single Account Statement", "com.psd2.tests.aisp.SingleAccountStatement");
		testPackageMapping.put("Get Account Setup", "com.psd2.tests.aisp.GetAccountRequest");
		testPackageMapping.put("Post Account Setup", "com.psd2.tests.aisp.PostAccountRequest");
		testPackageMapping.put("Delete Account Setup", "com.psd2.tests.aisp.DeleteAccountRequest");
		testPackageMapping.put("Get Fund Consent Confirmation", "com.psd2.tests.cisp.GetFundConfConsent");
		testPackageMapping.put("POST Fund Consent Confirmation", "com.psd2.tests.cisp.PostFundConfConsent");
		testPackageMapping.put("DELETE Fund Consent Confirmation", "com.psd2.tests.cisp.DeleteFundConfConsent");
		testPackageMapping.put("Fund Confirmation", "com.psd2.tests.cisp.FundConfirmation");
		testPackageMapping.put("POST Domestic Payment Consents", "com.psd2.tests.pisp.POST_DomesticPaymentConsents");
		testPackageMapping.put("GET Domestic Payment Consents", "com.psd2.tests.pisp.GET_DomesticPaymentConsents");
		testPackageMapping.put("POST Domestic Payments", "com.psd2.tests.pisp.POST_DomesticPayments");
		testPackageMapping.put("GET Domestic Payments", "com.psd2.tests.pisp.GET_DomesticPayments");
		testPackageMapping.put("GET Domestic Payments Consent Fund Confirmations", "com.psd2.tests.pisp.GET_DomesticPayment_FundConfirmation");
		testPackageMapping.put("POST Domestic Scheduled  Payment Consents", "com.psd2.tests.pisp.POST_DomesticScheduledPaymentConsents");
		testPackageMapping.put("GET Domestic Scheduled Payment Consents", "com.psd2.tests.pisp.GET_DomesticScheduledPaymentConsents");
		testPackageMapping.put("POST Domestic Scheduled Payments", "com.psd2.tests.pisp.POST_DomesticScheduledPayment");
		testPackageMapping.put("GET Domestic Scheduled Payments", "com.psd2.tests.pisp.GET_DomesticScheduledPayments");
		testPackageMapping.put("POST Domestic Standing Order Consents", "com.psd2.tests.pisp.POST_DomesticStandingOrderConsents");
		testPackageMapping.put("GET Domestic Standing Order Consents", "com.psd2.tests.pisp.GET_DomesticStandingOrderConsents");
		testPackageMapping.put("POST Domestic Standing Order", "com.psd2.tests.pisp.POST_DomesticStandingOrders");
		testPackageMapping.put("GET Domestic Standing Order", "com.psd2.tests.pisp.GET_DomesticStandingOrders");
		testPackageMapping.put("POST International Payments Consents", "com.psd2.tests.pisp.POST_InternationalPaymentConsent");
		testPackageMapping.put("GET International Payments Consents", "com.psd2.tests.pisp.GET_InternationalPaymentConsent");
		testPackageMapping.put("POST International Payments", "com.psd2.tests.pisp.POST_InternationalPayment");
		testPackageMapping.put("GET International Payments", "com.psd2.tests.pisp.GET_InternationalPayment");
		testPackageMapping.put("GET International Payments Consent Fund Confirmations", "com.psd2.tests.pisp.GET_InternationalPayment_FundConfirmation");
		testPackageMapping.put("POST International Scheduled Payments Consents", "com.psd2.tests.pisp.POST_InternationalScheduledPaymentConsent");
		testPackageMapping.put("GET International Scheduled Payments Consents", "com.psd2.tests.pisp.GET_InternationalScheduledPaymentConsent");
		testPackageMapping.put("POST International Scheduled Payments", "com.psd2.tests.pisp.POST_InternationalScheduledPayment");
		testPackageMapping.put("GET International Scheduled Payments", "com.psd2.tests.pisp.GET_InternationalScheduledPayment");
		testPackageMapping.put("GET International Scheduled Payments Consent Fund Confirmations", "com.psd2.tests.pisp.GET_InternationalPayment_FundConfirmation");
		testPackageMapping.put("POST File Payments Consents", "com.psd2.tests.pisp.POST_FilePaymentConsent");
		testPackageMapping.put("GET File Payments Consents", "com.psd2.tests.pisp.GET_FilePaymentConsent");
		testPackageMapping.put("POST File Payments Upload", "com.psd2.tests.pisp.POST_FileUpload");
		testPackageMapping.put("GET Download File Payments Consent", "com.psd2.tests.pisp.GET_Download_FilePaymentConsent");
		testPackageMapping.put("POST File Payments", "com.psd2.tests.pisp.POST_FilePayment");
		testPackageMapping.put("GET File Payments", "com.psd2.tests.pisp.GET_FilePayment");
		testPackageMapping.put("GET Download File Payments", "com.psd2.tests.pisp.GET_Download_FilePayment");
		
		return testPackageMapping;
	}
	
	@SuppressWarnings("unchecked")
	public static void main(String args[]) throws Exception{
		try{
			setTestPackageMapping();
			String configPath = System.getProperty("user.dir") + "/RunConfiguration.xlsx";
			if(Files.isRegularFile(Paths.get(configPath))){

				HashMap<String, String> map = ExcelDataProvider.getTestNames(testPackageMapping,configPath,"Config");

				testngUtility tg = new testngUtility();
				tg.executeXml(map);
			}else{
				System.out.println("Config file is not found at path : "+configPath);
				System.exit(0);
			}

		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
