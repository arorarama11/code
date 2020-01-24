package com.psd2.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

import org.apache.commons.io.FileUtils;

/**
 * Class Description - This class provides mechanism to read/write functionality of supplied propery file
 * 
 * @author alok_kumar
 * @version 1.0
 * 
 */
public class PropertyUtils
{	
	private static String propertyFile = null;
	public static String envPath = null;
	public static HashMap<String, String> propertyMap = new HashMap<String, String>();
	
	public PropertyUtils(){
		try{
			propertyFile = System.getProperty("user.dir") + "/src/com/psd2/testdata/Global.properties";
			envPath = System.getProperty("user.dir") + "/environment/"+getProperty("env");
			if(Files.isDirectory(Paths.get(envPath))){
				propertyMap.put("CertificatePath", envPath+"/CertificateDetails/Certificate.p12");
				propertyMap.put("InFilesPath", envPath+"/CertificateDetails/InFiles");
				propertyMap.put("RequestObjectPath", envPath+"/RequestObjectAgent/RequestObject");
				propertyMap.put("DBCertificatePath", envPath+"/DatabaseCertificate/DB_Server_Key_File.pem");
				propertyMap.put("DBKeystorePath", envPath+"/DatabaseCertificate/keystore.jks");
				propertyMap.put("DBTruststorePath", envPath+"/DatabaseCertificate/truststore.jks");
				propertyFile = envPath+"/Configurations.properties";
			}else{
				System.out.println("Environment is not found with name : "+getProperty("env"));
				System.exit(0);
			}
		}catch(Exception e){
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	/**
	* This method retrieves the property value from the given property file
	* 
	* @param  key  It is the name to identify the property
	* @param  propertyFile The file path of the property file
	*  
	* @return      <code>String</code>
	*                          
	*/
	public static String getProperty(String key) {
		String val = null;
		try {
			Properties props = new Properties();
			InputStream is = new FileInputStream(propertyFile);
			props.load(is);
			val = props.getProperty(key);			
			is.close();
		} catch (IOException ie) {
			System.out.println("Error occured while getting the property : " + key + " from " + propertyFile + " : " + ie.getMessage());
		}
		return val;
	}
	
	/**
	* This method sets the property in the property file
	* 
	* @param  key  It is the name of the property
	* @param value It is value of the property to set
	* @param  propertyFile The file path of the property file
	*                         
	*/
	public  void setProperty(String key, String value) {
		try {
			Properties props = new Properties();
			FileInputStream fis = new FileInputStream(new File(propertyFile));
			props.load(fis);
			props.setProperty(key, value);
			FileOutputStream fos = new FileOutputStream (new File(propertyFile));
	        
			props.store(fos,null);
			fis.close();
			fos.close();
		} catch (IOException ie) {
			System.out.println("Error occured while setting the property : " + key + " = " + value + " in " + propertyFile + " : " + ie.getMessage());
		}
	}

	/**
	* This method deletes a specific property from the property file
	* 
	* @param  key  It is the name of the property to be removed
	* @param  propertyFile The file path of the property file
	*                          
	*/
	@SuppressWarnings("unused")
	public  void deleteProperty(String key) {
		try {
			Properties props = new Properties();
			FileInputStream fis = new FileInputStream(new File(propertyFile));
			if (fis == null) {
				System.out.println("Error occured while loading the file"+ propertyFile);
			}
			else{
				props.load(fis);
				props.remove(key);
				FileOutputStream fos = new FileOutputStream (new File(propertyFile));
		        
				props.store(fos,null);
				fis.close();
				fos.close();
			}
		} catch (IOException ie) {
			System.out.println("Error occured while deleting the property : " + key + " from " + propertyFile + " : " + ie.getMessage());
		}
	}
	
	
	/**
	 * Method used to copy the file or folder
	 * @param source : path of the file to be copy
	 * @param target : path where need to copy the file
	 * @return
	 * @throws IOException
	 */
	public String copyFileToOtherLoc(String source, String target) throws IOException{
	    
	    File sourceFile = new File(source);
	    String name = sourceFile.getName();
	     
	   File targetFile = new File(target+name);
	   System.out.println("Copying file : " + sourceFile.getName());				     
	   //copy file from one location to other
	   if(sourceFile.isFile()){
		   FileUtils.copyFile(sourceFile, targetFile);
	   }
	   else{
	   FileUtils.copyDirectory(sourceFile, targetFile);	
	   }
	   System.out.println("copying of file is completed at location " +targetFile.getAbsolutePath());
	   return (targetFile.getAbsolutePath());
	}
	
	public static void main (String args[]){
		System.out.println(System.getProperty("user.dir"));
	}
	
	@SuppressWarnings("rawtypes")
	public HashMap<String , String>  getKeyValuePair(String file) throws IOException{
		HashMap<String, String> map = new HashMap<String, String> () ;
		Properties prop = new Properties();
		InputStream is = new FileInputStream(file);
		prop.load(is);
		Enumeration KeyValues = prop.keys();
		while (KeyValues.hasMoreElements()) {
			String key = (String) KeyValues.nextElement();
			String value = prop.getProperty(key);
			map.put(key, value);
		}
		return map;
	}
	
}
		