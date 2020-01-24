package com.psd2.core;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.psd2.logger.TestLogger;

/**
 *  Class Description - This class handles search elements using different locators.
 * @author Alok Kumar
 *
 */

public class WebElementLocator {
	
	//TestLogger log = new TestLogger();
	public WebElement getObject(WebDriver driver,String locatorType, String locator){
		try{
			WebDriverWait wait = new WebDriverWait(driver, 60);
		
			if(locatorType.equalsIgnoreCase("name")){
				wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.name(locator))));
				if(!(driver.findElement(By.name(locator)).isDisplayed() && driver.findElement(By.name(locator)).isEnabled())){
					TestLogger.logError("Element is not found with attribute : name="+locator+" until timeout reaches");
					return null;
				}
				return (driver.findElement(By.name(locator)));	
			}
			else if(locatorType.equalsIgnoreCase("id")){
				wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id(locator))));
				if(!(driver.findElement(By.id(locator)).isDisplayed() && driver.findElement(By.id(locator)).isEnabled())){
					TestLogger.logError("Element is not found with attribute : id="+locator+" until timeout reaches");
					return null;
				}
				return (driver.findElement(By.id(locator)));		
			}
			else if(locatorType.equalsIgnoreCase("linkText")){
				wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.linkText(locator))));
				if(!(driver.findElement(By.linkText(locator)).isDisplayed() && driver.findElement(By.linkText(locator)).isEnabled())){
					TestLogger.logError("Element is not found with attribute : linkText="+locator+" until timeout reaches");
					return null;
				}
				return (driver.findElement(By.linkText(locator)));		
			}
			else if(locatorType.equalsIgnoreCase("partialLinkText")){
				wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.partialLinkText(locator))));
				if(!(driver.findElement(By.partialLinkText(locator)).isDisplayed() && driver.findElement(By.partialLinkText(locator)).isEnabled())){
					TestLogger.logError("Element is not found with attribute : partialLinkText="+locator+" until timeout reaches");
					return null;
				}
				return (driver.findElement(By.partialLinkText(locator)));	
			}
			else if(locatorType.equalsIgnoreCase("className")){
				wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.className(locator))));
				if(!(driver.findElement(By.className(locator)).isDisplayed() && driver.findElement(By.className(locator)).isEnabled())){
					TestLogger.logError("Element is not found with attribute : className="+locator+" until timeout reaches");
					return null;
				}
				return (driver.findElement(By.className(locator)));	
			}
			else if(locatorType.equalsIgnoreCase("tagName")){
				wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.tagName(locator))));
				if(!(driver.findElement(By.tagName(locator)).isDisplayed() && driver.findElement(By.tagName(locator)).isEnabled())){
					TestLogger.logError("Element is not found with attribute : tagName="+locator+" until timeout reaches");
					return null;
				}
				return (driver.findElement(By.tagName(locator)));			
			}
			else if(locatorType.equalsIgnoreCase("xpath")){
				wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(locator))));
				if(!(driver.findElement(By.xpath(locator)).isDisplayed() && driver.findElement(By.xpath(locator)).isEnabled())){
					TestLogger.logError("Element is not found with attribute : xpath="+locator+" until timeout reaches");
					return null;
				}
				return (driver.findElement(By.xpath(locator)));		
			}
			else if(locatorType.equalsIgnoreCase("css")){
				wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(locator))));
				if(!(driver.findElement(By.cssSelector(locator)).isDisplayed() && driver.findElement(By.cssSelector(locator)).isEnabled())){
					TestLogger.logError("Element is not found with attribute : cssSelector="+locator+" until timeout reaches");
					return null;
				}
				return (driver.findElement(By.cssSelector(locator)));	
			}
			else{
				TestLogger.logError("Locator of type :"+locatorType+" is not valid");
				return null;
			}
		}catch(NoSuchElementException exception){
			TestLogger.logError("No such element found with attrribute : "+locatorType+"="+locator);
			exception.printStackTrace();
			driver.close();
			TestBase.driver = null;
			return null;
		}
		catch(Exception e){
			e.printStackTrace();
			driver.close();
			TestBase.driver = null;
			return null;
		}
	}
	
public WebElement getObjectForJS(WebDriver driver,String locatorType, String locator){		

	try{
		WebDriverWait wait = new WebDriverWait(driver, 60);
	
		if(locatorType.equalsIgnoreCase("name")){
			wait.until(ExpectedConditions.presenceOfElementLocated((By.name(locator))));
			return (driver.findElement(By.name(locator)));	
		}
		else if(locatorType.equalsIgnoreCase("id")){
			wait.until(ExpectedConditions.presenceOfElementLocated((By.id(locator))));
			return (driver.findElement(By.id(locator)));		
		}
		else if(locatorType.equalsIgnoreCase("linkText")){
			wait.until(ExpectedConditions.presenceOfElementLocated((By.linkText(locator))));
			return (driver.findElement(By.linkText(locator)));		
		}
		else if(locatorType.equalsIgnoreCase("partialLinkText")){
			wait.until(ExpectedConditions.presenceOfElementLocated((By.partialLinkText(locator))));
			return (driver.findElement(By.partialLinkText(locator)));	
		}
		else if(locatorType.equalsIgnoreCase("className")){
			wait.until(ExpectedConditions.presenceOfElementLocated((By.className(locator))));
			return (driver.findElement(By.className(locator)));	
		}
		else if(locatorType.equalsIgnoreCase("tagName")){
			wait.until(ExpectedConditions.presenceOfElementLocated((By.tagName(locator))));
			return (driver.findElement(By.tagName(locator)));			
		}
		else if(locatorType.equalsIgnoreCase("xpath")){
			wait.until(ExpectedConditions.presenceOfElementLocated((By.xpath(locator))));
			return (driver.findElement(By.xpath(locator)));		
		}
		else if(locatorType.equalsIgnoreCase("css")){
			wait.until(ExpectedConditions.presenceOfElementLocated((By.cssSelector(locator))));
			return (driver.findElement(By.cssSelector(locator)));	
		}
		else{
			TestLogger.logError("Locator of type :"+locatorType+" is not valid");
			return null;
		}
	}catch(NoSuchElementException exception){
			TestLogger.logError("No such element found with attrribute : "+locatorType+"="+locator);
			exception.printStackTrace();
			return null;
		}
	}
}
