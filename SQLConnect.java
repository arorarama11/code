package com.psd2.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.psd2.logger.TestLogger;

/**
 * Description : Used to connect with SQL database and execute query.
 * @author alok_kumar
 *
 */

public class SQLConnect {
	
	 Connection connection = null;
	 private boolean connectionFlag = false; 
	 boolean dbConnectStatus = false;
	 PropertyUtils prop = new PropertyUtils();
	 
		@SuppressWarnings("static-access")
		public boolean dbConnection(){
			
		    String DRIVER_CLASS = "com.microsoft.sqlserver.jdbc.SQLServerDriver";		     
		    
		   	try{		   		
		    	Class.forName(DRIVER_CLASS);	
		    	String host = prop.getProperty("IP");
		    	String port = prop.getProperty("port"); 
		    	String db = prop.getProperty("database"); 
		    	String user = prop.getProperty("user");
		        String pass = prop.getProperty("password");
		        
		     //   String connectionUrl1 = "jdbc:sqlserver://10.222.188.98:51316;user=sa;password=P@ssw0rd;databaseName=NewDatabase";
		        String connectionUrl = "jdbc:sqlserver://" +host+ ":" +port+ ";user=" +user+ ";password=" +pass+ ";databaseName=" +db;
		        TestLogger.logInfo("Connecting to database...");
		    	connection = DriverManager.getConnection(connectionUrl);
		    	if(connection == null){
		    		TestLogger.logError("Database connection failed");
		    		return connectionFlag;
		    	}
		    	TestLogger.logSuccess("Connection Established Successfull and the DATABASE NAME IS:"
	                    + connection.getMetaData().getDatabaseProductName());
		    	connectionFlag = true;
		    }
	    	catch (Exception e) {
	    		e.printStackTrace();	    			
	    	}
		   	return connectionFlag;
		}
		
		public List<Map<String, String>> query(String sql) {
			List<Map<String, String>> result = null;
		//	boolean dbConnectStatus = false;
			
			if(!connectionFlag){
				dbConnectStatus = dbConnection();
			}
			try {
				if(sql == null) {
					TestLogger.logError("Provided SQL statement is NULL");
					return null;				
				}
				TestLogger.logInfo("Execute specified SQL: " + "\"" + sql + "\"");
				if (!dbConnectStatus) {
					TestLogger.logError("No valid DB connection for executing SQL");
					return null;
				}
				result = new ArrayList<Map<String, String>>();
				Statement sqlStatement = connection.createStatement();
				ResultSet rs = sqlStatement.executeQuery(sql);
				while (rs.next()) {
					Map<String, String> recordMap = new HashMap<String, String>();
					ResultSetMetaData rsmd = rs.getMetaData();
					for (int i = 1; i <= rsmd.getColumnCount(); i++) {
						recordMap.put(rsmd.getColumnName(i), rs.getString(rsmd
								.getColumnName(i)));
					}
					result.add(recordMap);
				}
				sqlStatement.close();
			} catch (Exception e) {
				result = null;
				TestLogger.logError("Failed to execute specified SQL: " +
						"\"" + sql + "\"");
				TestLogger.logError(e.toString());
				e.printStackTrace();
			}
			return result;
		}
		
		public boolean updateQuery(String sql) {
			boolean result = false;
		//	boolean dbConnectStatus = false;
			
			if(!connectionFlag){
				dbConnectStatus = dbConnection();
			}
			try {
				if(sql == null) {
					TestLogger.logError("Provided SQL statement is NULL");
					return false;				
				}
				TestLogger.logInfo("Execute specified SQL: " + "\"" + sql + "\"");
				if (!dbConnectStatus) {
					TestLogger.logError("No valid DB connection for executing SQL");
					return false;
				}
				Statement sqlStatement = connection.createStatement();
				int row = sqlStatement.executeUpdate(sql);
				sqlStatement.close();
				if(row>0){
					result = true;
				}
			} catch (Exception e) {
				TestLogger.logError("Failed to execute specified SQL: " +
						"\"" + sql + "\"");
				TestLogger.logError(e.toString());
				e.printStackTrace();
			}
			return result;
		}
		
		public boolean close(){
			try {
				
				if (connectionFlag){
					TestLogger.logInfo("Close active connection to DB");
					connection.close();	
					connection = null;
					connectionFlag = false;
				}
			} catch (Exception e) {
				TestLogger.logError("Failed to close the active connection to DB");
				TestLogger.logError(e.toString());
				e.printStackTrace();
			    return false;
			}
			return true;
		}
}
