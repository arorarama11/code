package com.psd2.core;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import com.psd2.logger.TestLogger;
import com.psd2.logger.TestVerification;
/**
* Class Description - This class consists of methods that are required for automating WEB UI application 
* @author Alok Kumar
* 
*/
public class SeleniumUtility {
	
	int timeout = 10;
	WebElementLocator loc = new WebElementLocator();
	TestVerification testVerification = new TestVerification(); 
	Robot robot = null;
	//String filePath = System.getProperty("user.dir") + "/screenshot/";
	
	public  void navigateToURL(String pageURL){
		try{
			TestLogger.logInfo("Navigate to page via URL: " + pageURL + "\n");
			TestBase.driver.get(pageURL);
		}catch(Exception e){
			testVerification.testResult = false;
			e.printStackTrace();
		}
	}
	
	public  void maximizeBrowserWindow(){
		try{
			TestLogger.logInfo("Maximize current active browser window\n");
			TestBase.driver.manage().window().maximize();
		}catch(Exception e){
			testVerification.testResult = false;
			e.printStackTrace();
		}
	}
	
	public  void refreshPage(){
		try{
			TestLogger.logInfo("Refresh current web page\n");
			TestBase.driver.navigate().refresh();
		}catch(Exception e){
			testVerification.testResult = false;
			e.printStackTrace();
		}
	}
	
	public  String getCurrentWindowHandle(){
		try{
			return TestBase.driver.getWindowHandle();
		}catch(Exception e){
			testVerification.testResult = false;
			e.printStackTrace();
			return null;
		}
	}
	
	public void switchToWindow(String windowHandle){
		TestBase.driver.switchTo().window(windowHandle);
	}	
	
	
	public void closeWindow(String windowHandle){
		String currentWindowHandle = getCurrentWindowHandle();
		if(currentWindowHandle.equals(windowHandle)){
			TestBase.driver.close();
		}
		else{
			TestBase.driver.switchTo().window(windowHandle);
			TestBase.driver.close();
			TestBase.driver.switchTo().window(currentWindowHandle);
		}
	}
	
	public void switchToFrame(String object){
		try{
			WebElement framehandle = getElement(object);
			TestBase.driver.switchTo().frame(framehandle);
		} catch (Exception e) {
			testVerification.testResult = false;
			e.printStackTrace();
		}
	}
	
	public void dragdrop( WebElement drag,  WebElement drop ){
		Actions builder = new Actions(TestBase.driver);  // Configure the Action  
		Action dragAndDrop = builder.clickAndHold(drag)  
				 			  .moveToElement(drop)  
				 			  .release(drop)  
				 			  .build();  // Get the action  
		 
		 dragAndDrop.perform(); // Execute the Action 		
	}
	
	public boolean isDisplayed( String object) throws InterruptedException{
		WebElement ele = getElement(object);
		for(int i=0; i<5; i++){
			try{
				if(ele.isDisplayed())
				return true;
			}
			catch(Exception e){
				e.printStackTrace();
			}
			Thread.sleep(4000);
		}
		TestLogger.logError("Element is failed to get display on screen in specified time");
		return false;
	}
	
	public void waitForElementDisplayed(String object) throws InterruptedException{
		if(!isDisplayed(object)){
			TestLogger.logError("Required webelement is not found");
		}		
	}
	
	public String waitForCondition(WebElement ele, String executionString, String attr) throws InterruptedException{
		
		String elementAttValue = null;
		for(int i=0; i<5; i++){
			
			if(ele.isDisplayed()){
				executeJavascript(executionString);
				elementAttValue = ele.getAttribute(attr);
				return elementAttValue;
			}
			else{
				Thread.sleep(4000);
			}
		}
		TestLogger.logError("Required webelement is not found");
		return elementAttValue;
	}
	
	
	public void implicitWait(int timeout){
		TestBase.driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
	}
	
	public void explicitWaitWithClickableEle(String object, int timeout){
		try{
			WebElement ele = getElement(object);
			WebDriverWait wait = new WebDriverWait(TestBase.driver, timeout);
			wait.until(ExpectedConditions.elementToBeClickable(ele));
		} catch (Exception e) {
			testVerification.testResult = false;
			e.printStackTrace();
	    }
	}
	
	public Object executeJavascript(String scriptString){
		return ((JavascriptExecutor)TestBase.driver).executeScript(scriptString);
	}
	
	public static void takeScreenShot() throws Exception{
		try{
		String screenShotName = "ScreenShot_"
				+(DateFormatUtils.format(System.currentTimeMillis(),"yyyy-MM-dd_hh-mm-ss-SSS"))+".jpg";
				
		File directory ;
		directory = new File("./test-output/screenshot");
		if(!directory.exists())
			directory.mkdirs();		
		String screenShotPath = directory.getAbsolutePath() + "/" + screenShotName;	
		
		File scrFile = ((TakesScreenshot)TestBase.driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, new File(screenShotPath));
		
		Reporter.log("<a href=\"../screenshot/"+screenShotName+"\" "+"target=\"_blank\" style=\"color:red\">"+ screenShotName+"</a>\n");
		}catch(Exception e){
			e.printStackTrace();
		}
	}	
	
	@SuppressWarnings("unused")
	public void checkAlert() {
	    try {
	        WebDriverWait wait = new WebDriverWait(TestBase.driver, 5);
	      //  wait.until(ExpectedConditions.alertIsPresent());
	        Alert alert = TestBase.driver.switchTo().alert();
	        alert.accept();
	    } catch (Exception e) {
			testVerification.testResult = false;
			e.printStackTrace();
	    }
	}
	
	public void waitForPageLoad() throws InterruptedException {
		try{
			Thread.sleep(5000);
			ExpectedCondition<Boolean> pageLoadCondition = new
			    ExpectedCondition<Boolean>() {
			        public Boolean apply(WebDriver driver) {
			            return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
			        }
			    };
			WebDriverWait wait = new WebDriverWait(TestBase.driver, 30);
			wait.until(pageLoadCondition);
		}catch(Exception e){
			testVerification.testResult = false;
		}
	}
	
	/**
	 * To get the object/element with provided locator 
	 * @param object
	 * @return
	 */
	public WebElement getElement(String object){
		try{
			String locatorType = object.split(":")[0];
			String locator = object.split(":")[1];
			WebElement ele = loc.getObject(TestBase.driver, locatorType, locator);
			if(ele != null){
				return ele;
			}else{
			takeScreenShot();
			testVerification.testResult = false;
			return null;
		}}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * To get the object/element with provided locator for java script
	 * @param object
	 * @return
	 */
	public WebElement getElementForJS(String object){
		try{
			String locatorType = object.split(":")[0];
			String locator = object.split(":")[1];
			WebElement ele = loc.getObjectForJS(TestBase.driver, locatorType, locator);
			if(ele != null){
				return ele;
			}else{
			takeScreenShot();
			testVerification.testResult = false;
			return null;
			}
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * To get the text with provided locator 
	 * @param object
	 * @return
	 */
	public String getText(String object){
		try{
			String locatorType = object.split(":")[0];
			String locator = object.split(":")[1];
			WebElement ele = loc.getObject(TestBase.driver, locatorType, locator);
			if(ele != null){
				return ele.getText();
			}else{
			takeScreenShot();
			testVerification.testResult = false;
			return null;
		}}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * To enter text into text field
	 * @param object
	 * @param value
	 */
	public void setText(String object, String value){
		try{
			WebElement ele = getElement(object);
			ele.sendKeys(value);
			TestLogger.logSuccess("Text entered successfully with value : "+value+" for object with attribute - "+object);
		}catch(Exception e){
			testVerification.testResult = false;
			e.printStackTrace();
			TestLogger.logSuccess("Failed to enter text for object with attribute - "+object);
		}
	}
	
	/**
	 * To click on object by javascript
	 * @param object
	 */
	public void clickByJS(String object){
		try{
			WebElement ele = getElementForJS(object);			
			JavascriptExecutor js = (JavascriptExecutor) TestBase.driver;
			js.executeScript("arguments[0].click();", ele);
			TestLogger.logSuccess("Click perform successfully for object with attribute - "+object);
		}catch(Exception e){
			testVerification.testResult = false;
			e.printStackTrace();
			TestLogger.logSuccess("Failed to perform click for object with attribute - "+object);
		}
	}
	
	/**
	 * To click on object
	 * @param object
	 */
	public void click(String object){
		try{
			WebElement ele = getElement(object);
			ele.click();
			TestLogger.logSuccess("Click perform successfully for object with attribute - "+object);
		}catch(Exception e){
			e.printStackTrace();
			testVerification.testResult = false;
			TestLogger.logSuccess("Failed to perform click for object with attribute - "+object);
		}
	}
	
	/**
	 * To get the URL
	 * @return
	 */
	public String getURL(){
		try{
			return TestBase.driver.getCurrentUrl();
		}catch(Exception e){
			e.printStackTrace();
			testVerification.testResult = false;
			return null;
		}
	}
	
	
	public void pressKey(String key){
		try{
			Robot robot = new Robot ();
			switch (key) {
			case  "ENTER" :
					robot.keyPress(KeyEvent.VK_ENTER);
		           	break;
			case  "ARROW_DOWN" :
				robot.keyPress(KeyEvent.VK_DOWN);
	           	break;
			case  "PAGE_DOWN" :
				robot.keyPress(KeyEvent.VK_PAGE_DOWN);
	           	break;
			default: 
					System.out.println("INVALID KEY");
			break;
			}
		}
		catch(Exception e){
			e.printStackTrace();
			testVerification.testResult = false;
		}
	}	
	
	public void waitForAuthCode() throws InterruptedException {
		try{
			for(int i=0; i<30; i++){
				if(!getURL().contains("code"))
					Thread.sleep(1000);
				else
					break;
			}
		}catch(Exception e){
			testVerification.testResult = false;
		}
	}
}
