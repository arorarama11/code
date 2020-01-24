package com.psd2.utils;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.psd2.logger.TestLogger;

public class Misc {
	
	/**
	* This method returns unique generated string
	* 
	* @param  length  Length of the string
	*
	* @return		<code>Integer</code> 
	*                         
	*/
	public static String generateString(int length)
	{
		Random rng =new Random();
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		char[] text = new char[length];
		for (int i = 0; i < length; i++)
		{
			text[i] = characters.charAt(rng.nextInt(characters.length()));
		}
		return new String(text);
	} 
	
	public static String randomString(){
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HHmmss");
		Date date = new Date();
		return(dateFormat.format(date)+"-"+generateString(3));
	}
	
	public static String currentDate(String format){
		
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		Date date = new Date();
		return(dateFormat.format(date));
	}
	
	public static String getCurrentTimezone(String timezone, String format){		
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		dateFormat.setTimeZone(TimeZone.getTimeZone(timezone));
		return(dateFormat.format(date));
	}
	
	public static String updatedCurrentTime(int hourToUpdate, int minToUpdate){
		 DecimalFormat mFormat= new DecimalFormat("00");
		 Calendar now = Calendar.getInstance();
		 now.add(Calendar.HOUR_OF_DAY,hourToUpdate);
		 now.add(Calendar.MINUTE,minToUpdate);
		 String updateTime =mFormat.format(now.get(Calendar.HOUR_OF_DAY)) +":"+ mFormat.format(now.get(Calendar.MINUTE)) +":"+ mFormat.format(now.get(Calendar.SECOND));
		 return(updateTime);
	}
	
	public static String todaysDate(){		
		SimpleDateFormat dateFormat = new SimpleDateFormat("d");
		Date date = new Date();
		return(dateFormat.format(date));
	}
	
	public static String prevDay(){		
		int MILLIS_IN_DAY = 1000 * 60 * 60 * 24;
		SimpleDateFormat dateFormat = new SimpleDateFormat("d");
		Date date = new Date();
		return(dateFormat.format(date.getTime() - MILLIS_IN_DAY));
	}
	
	public static String nextDay(){		
		int MILLIS_IN_DAY = 1000 * 60 * 60 * 24;
		SimpleDateFormat dateFormat = new SimpleDateFormat("d");
		Date date = new Date();
		return(dateFormat.format(date.getTime() + MILLIS_IN_DAY));
	}
	
	public static String prevDate(String format){
		int MILLIS_IN_DAY = 1000 * 60 * 60 * 24;
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		Date date = new Date();
		return(dateFormat.format(date.getTime() - MILLIS_IN_DAY));
	}	
	
	public static String nextDate(String format){
		int MILLIS_IN_DAY = 1000 * 60 * 60 * 24;
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		Date date = new Date();
		return(dateFormat.format(date.getTime() + MILLIS_IN_DAY));
	}	
	
	public static String searchtext(String text, int start, int end){		
		return(text.substring(start, end));
	}
	
	public static boolean verifyDateTimeFormat(String value, String format){
		Date date = null;
		try {
		    SimpleDateFormat sdf = new SimpleDateFormat(format);
		    date = sdf.parse(value);
		    if (value.equals(sdf.format(date))) {
		    	return true;
		    }
		} catch (ParseException ex) {
		    ex.printStackTrace();
		}
		return false;
	}
	
	public static void main(String args[]) throws IOException{
		
		System.out.println(Misc.nextDate("yyyy-MM-dd")+"T"+Misc.nextDate("HH:mm:ss.SSS+00:00"));
	}
	
	public static String getFileUsingExt(String fileLocation, String  searchThisExtn){
		GenericExtFilter files = new GenericExtFilter(searchThisExtn);
		File folder = new File(fileLocation);
		String temp = null;
		if(folder.isDirectory()==false){
			System.out.println("Folder does not exists: " + fileLocation);
			return null;
		}
		String[] list = folder.list(files);
		if (list.length == 0) {
			System.out.println("There are no files with " + searchThisExtn + " Extension at path : "+fileLocation);
			return null;
		}
		for (String file : list) {
			temp = new StringBuffer(fileLocation).append(File.separator).append(file).toString();
			//System.out.println("file : " + temp);
		}	
		return temp;

	}
	
	public static void createFileWithCopyContent(String source, String dest) throws IOException{
		
		File fin = new File(source);
		FileInputStream fis = new FileInputStream(fin);
		BufferedReader in = new BufferedReader(new InputStreamReader(fis));
 
		FileWriter fstream = new FileWriter(dest, false);
		BufferedWriter out = new BufferedWriter(fstream);
 
		String aLine = null;
		while ((aLine = in.readLine()) != null) {
			//Process each line and add output to Dest.txt file
			out.write(aLine);
			out.newLine();
		}
		in.close();
		out.close();
	}
	
	public static String generateFileHash(String filePath){
		MessageDigest digester=null;;
		try{
			byte[] data = Files.readAllBytes(new File(filePath).toPath());
			digester = MessageDigest.getInstance("SHA-256");
			digester.update(data);
			
		}catch(IOException | NoSuchAlgorithmException ex){ex.printStackTrace();}
		return Base64.getEncoder().encodeToString(digester.digest());
	}
	
	public static double calculatePercentage(double obtained, double total) {
        return (obtained * total)/100 ;
    }
	
	public static String previousDate(){
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss+00:00");
		format.setTimeZone(TimeZone.getTimeZone("GMT"));
		Calendar cal=Calendar.getInstance();
		System.out.println(format.format(cal.getTime()));
		cal.add(Calendar.DATE, -1);
		cal.add(Calendar.MINUTE, -1);
		String date=format.format(cal.getTime());
		return date.split(" ")[0]+"T"+date.split(" ")[1];
	}
	
	public static void unzipFile(String inPath, String outPath) throws Exception {
		
		FileInputStream fis= new FileInputStream(inPath);
		ZipInputStream zip=new ZipInputStream(fis);
		ZipEntry entry=zip.getNextEntry();
		while (entry != null) {
            String filePath = outPath + File.separator + entry.getName();
            if (!entry.isDirectory()) {
            	BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
                byte[] bytesIn = new byte[4096];
                int read = 0;
                while ((read = zip.read(bytesIn)) != -1) {
                    bos.write(bytesIn, 0, read);
                }
                bos.close();
            }else {
                File dir = new File(filePath);
                dir.mkdir();
            }
            zip.closeEntry();
            entry = zip.getNextEntry();
        }
		if(new File(outPath+"/RequestObject").exists()){
			TestLogger.logSuccess("Successfully unzipped Request Object at path '"+outPath+"'");
		}else{
			TestLogger.logError("Failed to unzip Request Object");
		}
		zip.close();
    }
}