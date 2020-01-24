package com.psd2.logger;

import java.io.File;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.testng.Reporter;

/**
 *  Class Description - This provides logging methods 
 * @author alok_kumar
 *
 */

public class TestLogger {
	
private static Logger logger;
	
	static{
		logger = Logger.getLogger("PSD2_Automation");
		DOMConfigurator.configure(new File("files/log4j.xml").getAbsolutePath());
	}
//	
	 static final Logger LOGGER = Logger.getLogger(TestLogger.class);
	
	public static void logInfo(String message){
		logger.info(message);
		String dateInfo = DateFormatUtils.format(System.currentTimeMillis(),"yyyy-MM-dd_hh:mm:ss");
		Reporter.log("<font color=Blue>[" + dateInfo + "] " + message + "</font>",1);
	}
	
	public static void logSuccess(String message){
		logger.info(message);
		String dateInfo = DateFormatUtils.format(System.currentTimeMillis(),"yyyy-MM-dd_hh:mm:ss");
		Reporter.log("<font color=Green>[" + dateInfo + "] " + message + "</font>",1);	
	}
	
	public static void logError(String message){
		logger.error(message);
		String dateInfo = DateFormatUtils.format(System.currentTimeMillis(),"yyyy-MM-dd_hh:mm:ss");
		Reporter.log("<font color=Red>[" + dateInfo + "] " + message + "</font>",1);			
		
	}
	public static void logVariable(String message){
		logger.info(message);
		String dateInfo = DateFormatUtils.format(System.currentTimeMillis(),"yyyy-MM-dd_hh:mm:ss");
		Reporter.log("<font color=Gray>[" + dateInfo + "] " + message + "</font>",2);			
		
	}
	
	public static void logStep(String message){
		logger.info(message);
		String dateInfo = DateFormatUtils.format(System.currentTimeMillis(),"yyyy-MM-dd_hh:mm:ss");
		Reporter.log("<font color=Orange>[" + dateInfo + "] " + message + "</font>",1);			
		
	}	
	
	public static void logBlankLine(){
		Reporter.log("\n");	
	}
}
