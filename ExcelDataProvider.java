package com.psd2.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelDataProvider {
	
	private static XSSFSheet ExcelWSheet;	 
	private static XSSFWorkbook ExcelWBook;	 

	
	@SuppressWarnings("rawtypes")
	public static HashMap getTestNames(HashMap<String, String> mapping, String filePath, String sheetName) throws Exception {  
		 
		HashMap<String, String> newMap = new HashMap<String, String>();
		HashMap<String, String> testPackageMapping = mapping;
		
		   try {		 
			   FileInputStream ExcelFile = new FileInputStream(filePath);		 
			   
			   ExcelWBook = new XSSFWorkbook(ExcelFile);		
			   ExcelWSheet = ExcelWBook.getSheet(sheetName);		 
			 
			  	XSSFRow nextData;
				for(int i=1; i<ExcelWSheet.getLastRowNum()+1; i++){				
					nextData = ExcelWSheet.getRow(i);
					if(nextData.getCell(0).toString().equalsIgnoreCase("y")){
						newMap.put(nextData.getCell(1).toString(), testPackageMapping.get(nextData.getCell(1).toString()));
					}
				}			         
		   }
		   catch (FileNotFoundException e){		 
			   System.out.println("Could not read the Excel sheet");
			   e.printStackTrace();		 
		   }		 
		   catch (IOException e){		 
			   System.out.println("Could not read the Excel sheet");		 
			   e.printStackTrace();		 
		   }	
		return(newMap);		 
	}
	
	@SuppressWarnings("rawtypes")
	public static HashMap getClassDetails(String filePath, String sheetName, String className) throws Exception {  
		 
		HashMap<String, String> newMap = new HashMap<String, String>();
		
		   try {		 
			   FileInputStream ExcelFile = new FileInputStream(filePath);		 
			   
			   ExcelWBook = new XSSFWorkbook(ExcelFile);		
			   ExcelWSheet = ExcelWBook.getSheet(sheetName);
			 
			  	XSSFRow nextData;
				for(int i=1; i<ExcelWSheet.getLastRowNum()+1; i++){				
					nextData = ExcelWSheet.getRow(i);
					if(nextData.getCell(0).toString().trim().equalsIgnoreCase(className.trim())){
						newMap.put(nextData.getCell(0).toString(), nextData.getCell(1).toString()+"##"+nextData.getCell(2).toString());
						return(newMap);		 
					}
				}			         
		   }
		   catch (FileNotFoundException e){		 
			   System.out.println("Could not read the Excel sheet");
			   e.printStackTrace();		 
		   }		 
		   catch (NullPointerException e){		 
			   System.out.println("Could not read the Excel sheet");		 
			   e.printStackTrace();		 
		   }	
		return(null);		 
	}
}
