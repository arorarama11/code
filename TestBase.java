package com.psd2.core;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.Proxy.ProxyType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;

import com.psd2.logger.TestLogger;
import com.psd2.logger.TestVerification;
import com.psd2.utils.API_Constant;
import com.psd2.utils.MongoDBconfig;
import com.psd2.utils.PropertyUtils;
import com.psd2.wrapper.AccessToken;
import com.psd2.wrapper.AccountSetup;
import com.psd2.wrapper.ClientCredential;
import com.psd2.wrapper.ConsentOperation;
import com.psd2.wrapper.DomesticPayments;
import com.psd2.wrapper.DomesticScheduledPayment;
import com.psd2.wrapper.DomesticStandingOrder;
import com.psd2.wrapper.FilePayments;
import com.psd2.wrapper.FundConfirmation;
import com.psd2.wrapper.FundConfirmationConsent;
import com.psd2.wrapper.GenerateAccessTokenUsingRefreshToken;
import com.psd2.wrapper.GetAccounts;
import com.psd2.wrapper.GetFundConfirmationConsent;
import com.psd2.wrapper.InternationalPayment;
import com.psd2.wrapper.InternationalScheduledPayment;
import com.psd2.wrapper.PaymentSetup;
import com.psd2.wrapper.PaymentSubmission;
import com.psd2.wrapper.RequestObjectCreation;
import com.psd2.wrapper.RevokeConsent;
import com.psd2.wrapper.UploadFile;


/**
 * Class Description - It is the WebUI framework base class. Every WebUI testcase must extend this class.
 * 
 * @author Alok Kumar
 * 
 */
public class TestBase {
	  
	public static WebDriver driver = null;
	public ResourceBundle bundle = null;
	public PropertyUtils props = new PropertyUtils();

	public RestAPIRequest restRequest = new RestAPIRequest();
	public TestVerification testVP = new TestVerification();
	
	public ClientCredential createClientCred = new ClientCredential();
	public AccountSetup accountSetup = new AccountSetup();
	public AccessToken accesstoken = new AccessToken();
	public RequestObjectCreation reqObject = new RequestObjectCreation();
	public GetAccounts getAccount = new GetAccounts();
	public RevokeConsent revokeConsent=new RevokeConsent();
	public SeleniumUtility driverOps = new SeleniumUtility();
	public PaymentSetup paymentSetUp = new PaymentSetup();
	public DomesticPayments paymentConsent= DomesticPayments.getInstance();
	public PaymentSubmission paymentSub = new PaymentSubmission();
	public ConsentOperation	consentOps = new ConsentOperation();
	public FundConfirmationConsent fundConfConsent = new FundConfirmationConsent();
	public GetFundConfirmationConsent getFundConfConsent = new GetFundConfirmationConsent();
	public FundConfirmation fundConf = new FundConfirmation();
	public GenerateAccessTokenUsingRefreshToken createAccessToken = new GenerateAccessTokenUsingRefreshToken();
	public DomesticScheduledPayment dspConsent = DomesticScheduledPayment.getInstance();
	public DomesticStandingOrder dStandingOrder = DomesticStandingOrder.getInstance();
	public InternationalPayment internationalPayment = InternationalPayment.getInstance();
	public InternationalScheduledPayment iScheduledPayment = InternationalScheduledPayment.getInstance();
	public FilePayments filePayment = FilePayments.getInstance();
	public UploadFile uploadFile = UploadFile.getInstance();
	public ResourceBundle objectRepo = loadResourceBundle("com.psd2.testdata.ObjectRepository");

	public HashMap<String, String> consentDetails = new HashMap<String, String>();
	public API_Constant  apiConst = new API_Constant();
	public 	MongoDBconfig mongo = null;
	
	public String access_token = null;
	public String accountReqId = null;
	public String outId = null;
	public String baseurl = null;
	public String redirecturl = null;
	public String authCode = null;
	public String refresh_token = null;
	public String accountId = null;
	public String cc_token = null;
	public String paymentId = null;
	public String accountNumber = null;
	public String consentId = null;

	
	@BeforeClass(alwaysRun = true)
	public void reset(ITestContext ctontext){
		if(ctontext.getCurrentXmlTest().getName().contains("Domestic Payment")){
			paymentConsent.setTestData();
			TestLogger.logInfo("Domestic Payment Test Data Reset");
		}else if(ctontext.getCurrentXmlTest().getName().contains("Domestic Standing Order")){
			dStandingOrder.setTestData();
			TestLogger.logInfo("Domestic Standing Order Test Data Reset");
		}else if(ctontext.getCurrentXmlTest().getName().contains("Domestic Scheduled Payment")){
			dspConsent.setTestData();
			TestLogger.logInfo("Domestic Scheduled Payment Test Data Reset");
		}else if(ctontext.getCurrentXmlTest().getName().contains("International Payment")){
			internationalPayment.setTestData();
			TestLogger.logInfo("International Payment Test Data Reset");
		}else if(ctontext.getCurrentXmlTest().getName().contains("International Scheduled Payment")){
			iScheduledPayment.setTestData();
			TestLogger.logInfo("International Scheduled Payment Test Data Reset");
		}else if(ctontext.getCurrentXmlTest().getName().contains("File Payment")){
			filePayment.setTestData();
			TestLogger.logInfo("File Payment Test Data Reset");
		}else if(ctontext.getCurrentXmlTest().getName().contains("File Upload")){
			filePayment.setTestData();
			uploadFile.setTestData();
			TestLogger.logInfo("Upload File Test Data Reset");
		}else{
			TestLogger.logError("Invalid test name");
		}
	}
	
	//@BeforeClass(alwaysRun = true)
	public void startDriverInstance() throws IOException {
		if(driver != null){
			driver.close();
			driver.quit();	
		}
		driver = createDriverInstance();
	}

	
	//@AfterClass(alwaysRun = true)
	public void closeDriverInstance() {
		try{
			if(driver != null){
				driver.close();
				driver.quit();
				driver = null;
			}
		}catch(Exception e){
			e.printStackTrace();
			driver = null;
		}
	}
	
	public static ResourceBundle loadResourceBundle(String filePath){
		ResourceBundle bundle = null;
		if(filePath !=null){
			bundle = ResourceBundle.getBundle(filePath, Locale.getDefault());			
		}
		return bundle;
	}
	
	public String createNewAccessToken(String url, String refresh_token, String clientId, String clientSecret ) throws Throwable{
		GenerateAccessTokenUsingRefreshToken createAccessToken = new GenerateAccessTokenUsingRefreshToken();

		createAccessToken.setBaseURL(url);
		createAccessToken.setRefreshToken(refresh_token);
		createAccessToken.submit();
		if(String.valueOf(createAccessToken.getResponseStatusCode()).equalsIgnoreCase("200")){
			String access_token = createAccessToken.getAccessToken();
			return access_token;
		}
		else{
			return null;
		}
	}
	
	/*@SuppressWarnings("static-access")
	@org.testng.annotations.DataProvider(name = "InputDataFromCSV")
    public Object[][] getData() throws Exception {       
		
	 	return(csv.getDataFromCSV(filepath, sheetname));
    }
	
	@SuppressWarnings("static-access")
	@org.testng.annotations.DataProvider(name = "InputDataFromExcel")
    public Object[][] getDataFromExcel() throws Exception {       
		
	 	return(excel.getDataFromExcel(filepath, sheetname));
    }*/
	
	/*@AfterSuite(enabled = false)
	public void mailTestReport() throws InterruptedException{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
		Date date = new Date();
		String src = System.getProperty("user.dir") + "/test-output";
		String zipfolder = System.getProperty("user.dir") + "/files/Test Report/"+dateFormat.format(date);
		File createFolder = new File(zipfolder);
		createFolder.mkdirs();
		String dest = zipfolder+"/Report.zip";
		//ZipHandler zip = new ZipHandler();
		boolean zipReport = zip.zip(src, dest);		
		if(zipReport){
			EmailUtility email = new EmailUtility();
			String from = System.getProperty("user.name") + "@capgemini.com";
			String[] to = {"alok2089@gmail.com"};
			String password = "";	
			boolean mailStatus = email.sendMail(from, to, password, dest);
			if(mailStatus){
				TestLogger.logSuccess("Test Execution report is send successfully");
			}
			else {
				TestLogger.logError("Failed to send the execution report");
			}			
		}
		else {
			TestLogger.logError("Failed to zip the report");
		}		
	}*/
	
	@SuppressWarnings("static-access")
	public WebDriver createDriverInstance() throws IOException{
		
		if (props.getProperty("brwsr_drv").equals("firefox")) {
			
			FirefoxProfile profile = new FirefoxProfile();
			profile.setPreference("browser.download.folderList", 2); 
			profile.setPreference("browser.helperApps.alwaysAsk.force", false); 
			profile.setPreference("browser.download.manager.showWhenStarting",false);
			profile.setPreference("browser.download.dir",System.getProperty("user.dir") +"\\files\\Template");
			//profile.setPreference( "pdfjs.disabled", true );
			profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/pdf, application/octet-stream");	
						
			if(props.getProperty("isproxy").equals("y")){
				String ip = props.getProperty("proxy");
				String port = props.getProperty("browserport");
				
				Proxy proxy = new Proxy(); 
				proxy.setProxyType(ProxyType.MANUAL); 
				proxy.setHttpProxy(ip+":"+port); 
				proxy.setSslProxy(ip+":"+port);
			
				DesiredCapabilities dc = DesiredCapabilities.firefox();
				dc.setCapability(CapabilityType.PROXY, proxy);	
				dc.setCapability(FirefoxDriver.PROFILE, profile);
				driver = new FirefoxDriver(dc); 
				driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
			}
			else{
				driver = new FirefoxDriver((Capabilities) profile); 
				driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
			}

		} else if ((props.getProperty("brwsr_drv").equals("ie"))){
			//DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
			//capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			//capabilities.setCapability("ignoreZoomSetting", true);
			System.setProperty("webdriver.ie.driver",
					System.getProperty("user.dir") + "\\files\\IEDriverServer.exe");
			driver = new InternetExplorerDriver(); 
			driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		}
		else if ((props.getProperty("brwsr_drv").equals("chrome"))){
			//Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe /T");
			Runtime.getRuntime().exec("taskkill /F /IM chrome.exe /T");
			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir") + "\\files\\chromedriver.exe");
			
			if(API_Constant.SECURITY_CONFORMANCE_ENABLED) {
				//Set proxy for ZAP
				Proxy proxy = new Proxy();
				proxy.setProxyType(ProxyType.MANUAL); 
				proxy.setHttpProxy("127.0.0.1:8082"); 
				proxy.setSslProxy("127.0.0.1:8082");			
				DesiredCapabilities capabilities = DesiredCapabilities.chrome();
				capabilities.setCapability(CapabilityType.PROXY, proxy);	
				
			
				driver = new ChromeDriver(capabilities);
			}else {
				driver = new ChromeDriver();
			}
			//driver = new ChromeDriver(); 
			//driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		}
		driver.manage().window().maximize();
		return driver;
	}
}

