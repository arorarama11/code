package com.psd2.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.util.ResourceUtils;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;
import com.psd2.logger.TestLogger;

public class MongoDBconfig {

	private static String username = "appUserReadWrite";

	private static String password = "readwrite@app";
	
	private static String keyStorePassword="P@ssw0rd";
	
	private DBCollection collection = null; 
		
	private static boolean tunnelstatus = false;
	
	public MongoDBconfig(String database, String collectionName){
		if(!tunnelstatus){
			tunnelstatus = tunnelDBServer();
		}
			 connectMongo(database,collectionName);
	}
	
	@SuppressWarnings("deprecation")
	private static MongoDbFactory  mongoDbFactory(String database) throws Exception {
		
		System.clearProperty("javax.net.ssl.keyStore");
		System.setProperty("javax.net.ssl.trustStore", PropertyUtils.propertyMap.get("DBTruststorePath"));
		System.setProperty("javax.net.ssl.trustStorePassword", "server");
		SSLContext sslContext = SSLContextBuilder.create()
				.loadKeyMaterial(ResourceUtils.getFile(PropertyUtils.propertyMap.get("DBKeystorePath")), keyStorePassword.toCharArray(),
						keyStorePassword.toCharArray()).build();
		
		MongoClientOptions.Builder builder = MongoClientOptions.builder();
		builder.sslEnabled(true).socketFactory(sslContext.getSocketFactory()).build();
		ServerAddress addr1 = new ServerAddress(PropertyUtils.getProperty("db_url"), Integer.valueOf(PropertyUtils.getProperty("db_port")));
		MongoClientOptions sslOptions = builder.build();
		MongoClient mongoClient;
		MongoCredential credential = MongoCredential.createPlainCredential(username, "$external",
				password.toCharArray());
		mongoClient = new MongoClient(addr1, Arrays.asList(credential), sslOptions);
		return new SimpleMongoDbFactory(mongoClient, database);
	}
	
	private void connectMongo(String database, String collectionName){
		try{
			MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory(database));
			DB db=mongoTemplate.getDb();
			collection = db.getCollection(collectionName);
			mongoTemplate.setReadPreference(ReadPreference.secondaryPreferred());
			mongoTemplate.setWriteConcern(WriteConcern.ACKNOWLEDGED);
		}catch(Exception e){
			e.printStackTrace();
		}
			
	}
	
	public void updateArrayObject(String searchQuery, String arrayPath, int arrayIndex, String key, String valueToUpdate){
		try{
			String[] queryToSearch  = searchQuery.split(",");
			BasicDBObject query = new BasicDBObject();
			for(String keyValue : queryToSearch){
			    query.put(keyValue.split(":")[0], keyValue.split(":")[1]);
			}
			DBCursor cursor = collection.find(query);
			if(cursor.count() > 0){
				TestLogger.logInfo("Record is found for query : " +query.toString()+ " with count - "+cursor.count());
			}
			BasicDBObject data = new BasicDBObject();
		    data.put(arrayPath +"."+ arrayIndex+ "."+ key, valueToUpdate);
		    BasicDBObject command = new BasicDBObject();
		    command.put("$set", data);
		    collection.update(query, command);
		    @SuppressWarnings("unused")
			DBObject oneDetails ;
			while (cursor.hasNext()){
				cursor.next();
				oneDetails=cursor.curr();
				TestLogger.logInfo("Updated value for key - "+key+":"+valueToUpdate);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void removeArrayObject(String searchQuery, String arrayPath, int arrayIndex, String key){
		try{
			String[] queryToSearch  = searchQuery.split(",");
			BasicDBObject query = new BasicDBObject();

			for(String keyValue : queryToSearch){
			    query.put(keyValue.split(":")[0], keyValue.split(":")[1]);
			}
			DBCursor cursor = collection.find(query);
			if(cursor.count() > 0){
				TestLogger.logInfo("Record is found for query : " +query.toString()+ " with count - "+cursor.count());
			}
			BasicDBObject data = new BasicDBObject();
		    data.put(arrayPath +"."+ arrayIndex+ "."+ key,"");
		    BasicDBObject command = new BasicDBObject();
		    command.put("$unset", data);
		    collection.update(query, command);
		    @SuppressWarnings("unused")
			DBObject oneDetails ;
			while (cursor.hasNext()){
				cursor.next();
				oneDetails=cursor.curr();
				TestLogger.logInfo(key+" field has been removed successfully");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void updateDocumentObject(String searchQuery, String keyValue){
		try{
			String[] queryToSearch  = searchQuery.split(",");
			BasicDBObject query = new BasicDBObject();

			for(String search : queryToSearch){
			    query.put(search.split(":")[0], search.split(":")[1]);
			}
			DBCursor cursor = collection.find(query);
			if(cursor.count() > 0){
				TestLogger.logInfo("Record is found for query : " +query.toString()+ " with count - "+cursor.count());
				BasicDBObject updateFields = new BasicDBObject();
				String replacedKey;
				for(String key : keyValue.split(",")){
					if(key.split(":")[1].contains("#")){
						replacedKey = key.split(":")[1].replace("#", ":");
					}
					else if(key.split(":")[1].equals(" ")){
						replacedKey = key.split(":")[1].replace(" ", "");
					}
					else{
						replacedKey = key.split(":")[1];
					}
					updateFields.append(key.split(":")[0], replacedKey);
				}
				BasicDBObject setQuery = new BasicDBObject();
				setQuery.append("$set", updateFields);
				collection.update(query, setQuery);
				@SuppressWarnings("unused")
				DBObject oneDetails ;
				while (cursor.hasNext()){
					cursor.next();
					oneDetails=cursor.curr();
					for(String key : keyValue.split(",")){
						TestLogger.logInfo("Updated value for key - "+key.split(":")[0]+":"+key.split(":")[1]);
					}
				}
			}else{
				TestLogger.logSuccess("Record is not found for query : " +query.toString());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public void removeDocumentObject(String searchQuery, String key){
		try{
			String[] queryToSearch  = searchQuery.split(",");
			BasicDBObject query = new BasicDBObject();

			for(String search : queryToSearch){
			    query.put(search.split(":")[0], search.split(":")[1]);
			}
			DBCursor cursor = collection.find(query);
			if(cursor.count() > 0){
				TestLogger.logInfo("Record is found for query : " +query.toString()+ " with count - "+cursor.count());
				BasicDBObject updateFields = new BasicDBObject();
				updateFields.append(key, "");
				BasicDBObject setQuery = new BasicDBObject();
				setQuery.put("$unset", updateFields);
				collection.update(query, setQuery);
				@SuppressWarnings("unused")
				DBObject oneDetails ;
				while (cursor.hasNext()){
					cursor.next();
					oneDetails=cursor.curr();
					TestLogger.logInfo(key+" field has been removed successfully");
				}
			}else{
				TestLogger.logSuccess("Record is not found for query : " +query.toString());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void addArrayObject(String searchQuery, String key, String arrayListValue){
		try{
			String[] queryToSearch  = searchQuery.split(",");
			BasicDBObject query = new BasicDBObject();

			for(String search : queryToSearch){
			    query.put(search.split(":")[0], search.split(":")[1]);
			}
			DBCursor cursor = collection.find(query);
			if(cursor.count() > 0){
				TestLogger.logInfo("Record is found for query : " +query.toString()+ " with count - "+cursor.count());
				BasicDBObject updateFields = new BasicDBObject();
				String value = "";
				int i=1;
				for(String str : arrayListValue.split("##")){
					value = value + str;
					if(i<arrayListValue.split("##").length){
						value = value+",";
					}
					i++;
				}
				updateFields.append(key, Arrays.asList(value.split(",")));

				BasicDBObject setQuery = new BasicDBObject();
				setQuery.append("$set", updateFields);
				collection.update(query, setQuery);
				TestLogger.logInfo("Updated value for key - "+key+":"+value);
			}else{
				TestLogger.logSuccess("Record is not found for query : " +query.toString());
			}
		}catch(Exception e){
			e.printStackTrace();
		}		
	}
	
	public String getFirstArrayObject(String searchQuery, String arrayObject, String arrayKey){
		Map<String, String> map = null;
		ArrayList<Map<String, String>> al = new ArrayList<Map<String, String>>();
		boolean foundArrayKey = false;
		try{
			String[] queryToSearch  = searchQuery.split(",");
			BasicDBObject query = new BasicDBObject();

			for(String search : queryToSearch){
			    query.put(search.split(":")[0], search.split(":")[1]);
			}
			DBCursor cursor = collection.find(query);

			if(cursor.count() > 0){
				TestLogger.logInfo("Record is found for query : " +query.toString()+ " with count - "+cursor.count());
				DBObject oneDetails ;
				cursor.next();
				oneDetails=cursor.curr();
				String var1 = oneDetails.get(arrayObject).toString();
				var1 = var1.substring(var1.indexOf("[")+1, var1.indexOf("]"));
				String[] varArray1 = var1.split("},");
				for(String var2 : varArray1){
					var2 = var2.substring(var2.indexOf("{")+1,var2.indexOf("}"));
					String[] varArray2 = var2.split(",");
					map = new HashMap <String, String> ();
					for(String var3 : varArray2){
						map.put(var3.split(":")[0], var3.split(":")[1]);
					}
					al.add(map);
				}
				for(int i=0; i<al.size();i++){
					for (Map.Entry<String,String> entry : al.get(i).entrySet()) {
			        	String var4 = entry.getKey().substring(2,entry.getKey().length()-2);
			        	if(var4.equalsIgnoreCase(arrayKey)){
							TestLogger.logInfo("Found value for key - "+arrayKey+ " is "+ entry.getValue().substring(2,entry.getValue().length()-2));
							foundArrayKey = true;
							return (entry.getValue().substring(2,entry.getValue().length()-2));
						}			        	
			        }								
				}
				if(!foundArrayKey){
					TestLogger.logError("Expected key value is not found for key name : "+arrayKey);
					return null;
				}
			}else{
				TestLogger.logError("Record is not found for query : " +query.toString());
			}
			return null;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public int getArrayObjectLength(String searchQuery, String arrayObject, String arrayString){
		try{
			String[] queryToSearch  = searchQuery.split(",");
			BasicDBObject query = new BasicDBObject();

			for(String search : queryToSearch){
			    query.put(search.split(":")[0], search.split(":")[1]);
			}
			DBCursor cursor = collection.find(query);

			if(cursor.count() > 0){
				TestLogger.logInfo("Record is found for query : " +query.toString()+ " with count - "+cursor.count());
				DBObject oneDetails ;
				cursor.next();
				oneDetails=cursor.curr();
				String var1 = oneDetails.get(arrayObject).toString();
				String[] varArray1 = var1.split("\\} , \\{ \""+arrayString);
				TestLogger.logSuccess("Array length for object : "+arrayObject+ " = "+varArray1.length);
				return(varArray1.length);
			}else{
				TestLogger.logError("Record is not found for query : " +query.toString());
			}
			return -1;
		}catch(Exception e){
			e.printStackTrace();
			return -1;
		}
	}
	
	@SuppressWarnings({ "static-access", "unused" })
	private boolean tunnelDBServer(){
		try{
			String STRICT_HOST_KEY_CHECKING_KEY = "StrictHostKeyChecking";
			String STRICT_HOST_KEY_CHECKING_VALUE = "no";
			String CHANNEL_TYPE = "shell";
		
			// Properties for tunnel and server
			String sshHost1 = PropertyUtils.getProperty("bastion_ip").toString();
			String sshUser1 = PropertyUtils.getProperty("db_user");
			String sshHost2 = PropertyUtils.getProperty("db_url");
			String sshuser2 = PropertyUtils.getProperty("db_user");
			int localport = Integer.valueOf(PropertyUtils.getProperty("db_port"));
	 
			// NOTE: Shared key file between sshHost1 and sshHost2, common for providers like AWS.
			String sshKeyFile = PropertyUtils.propertyMap.get("DBCertificatePath");
	    
			Session session = null;
			Session[] sessions = new Session[2];
	    
			// Create JSch object and set pem key
			JSch jsch = new JSch();
			jsch.addIdentity(sshKeyFile);
			jsch.setConfig(STRICT_HOST_KEY_CHECKING_KEY, STRICT_HOST_KEY_CHECKING_VALUE);	
	    
			// Open first session
			TestLogger.logInfo("Attempting connection to " + sshUser1 + "@" + sshHost1);
			sessions[0] = session = jsch.getSession(sshUser1, sshHost1, 22);
			session.connect();
			TestLogger.logInfo("Connected to " + sshUser1 + "@" + sshHost1);
	    
			// Set port forwarding hop 1
			TestLogger.logInfo("Attempting to start port forwarding");
			int assignedPort = session.setPortForwardingL(0, sshHost2, 22);
			TestLogger.logInfo("Completed port forwarding");
	    
			// Open second session
			TestLogger.logInfo("Attempting connection to " + sshuser2 + "@" + sshHost2);
	    
			sessions[1] = session = jsch.getSession(sshuser2, "127.0.0.1", assignedPort);
			session.setHostKeyAlias(sshHost2);
			session.connect();
			TestLogger.logInfo("Connected to " + sshuser2 + "@" + sshHost2);
	    
			// Set port forwarding hop 2
			TestLogger.logInfo("Attempting to start port forwarding");
			int assignedPort2 = session.setPortForwardingL(localport, "127.0.0.1", 27017);
			TestLogger.logInfo("Completed port forwarding  localhost: " + localport + " -> 127.0.0.1:" + 27017 );
	    
			Channel channel = session.openChannel(CHANNEL_TYPE);
			channel.connect();
			if(channel.isConnected()){
				return true;
			}else{
				TestLogger.logError("Failed to tunnel the DB server");
				return false;
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	@SuppressWarnings("unused")
	public static void main (String args []) throws Exception {
		PropertyUtils props = new PropertyUtils();
	
		/*MongoDBconfig mongo = new MongoDBconfig("aspspPSD2", "MockAccountTransactionCMA2");
		//mongo.updateArrayObject("accountNumber:23682879", "account",0,"name", "Bansal");
		mongo.updateArrayObject("accountNumber:33125548","statementReference",0,"0","PRE_AGREED");

		
		MongoDBconfig mongo = new MongoDBconfig("aspspPSD2", "MockAccountInformationCMA2");
		mongo.updateDocumentObject("accountNumber:23682799","nickname:Testing");*/
		//mongo.updateArrayObject("accountNumber:23682799","creditLine",0,"type","EMERGENCY");

		/*MongoDBconfig mongo = new MongoDBconfig("aspspPSD2", "aispConsent");
		String str = mongo.getFirstArrayObject("accountRequestId:02f1fb08-a756-4433-9f92-d543bbdfde34",
				"accountDetails", "accountId");
		System.out.println(str);*/

		/*MongoDBconfig mongo = new MongoDBconfig("transactionmockdata", "accnt");
		int str = mongo.getArrayObjectLength("accountNumber:23682880","transactions","transaction");
		TestLogger(str);*/
		
		//MongoDBconfig mongo = new MongoDBconfig("aspspPSD2","MockAccountInformationCMA2");
		//mongo.removeDocumentObject("psuId:55555555,accountNumber:21682781","nickname");
		//mongo.updateDocumentObject("psuId:32118465,accountNumber:23682782","description: ");
		//mongo.updateArrayObject("psuId:55555555,accountNumber:21682781", "account", 0, "name", "mohit");
		//mongo.removeArrayObject("psuId:55555555,accountNumber:21682781", "account", 0, "name");
	}
}
