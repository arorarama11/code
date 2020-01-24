package com.psd2.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLSocketFactory;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.config.SSLConfig;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.path.xml.XmlPath;
import com.jayway.restassured.response.Header;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import com.jayway.restassured.specification.ResponseSpecification;
import com.psd2.logger.TestLogger;
import com.psd2.utils.API_Constant;
import com.psd2.utils.API_Test_Variables;
import com.psd2.utils.Misc;
import com.psd2.utils.PropertyUtils;

 /**
 * Class Description - This class consists of methods that are required for handling REST API request/response
 * @author Alok Kumar
 * 
 */
@SuppressWarnings("deprecation")
public class RestAPIRequest extends API_Test_Variables {

	//Property of request
	String _reqDesc;									// for description of the request, e.g. "Describe key"
	String _reqURL;                        				// for URL of request (Required)               
	HashMap<String, String> _reqHeaders;   				// for Headers of request (Required). 
															// We use HashMap to make add/update/query headers easy
	HashMap<String, String> _reqMultipartHeaders;   	// for Headers of multipart request (Required). 
	String _reqMethod;					   				// for Method of request, e.g. POST, DELETE (Required)
	String _reqBody;					   				// for Request body, e.g. name=autotestKey
	String _reqUserName;				 				// for Username,
	String _reqPassword;				 				// for Password
	String _reqFileUpload;								// for uploaded multipart form data as a file
	String _reqFileUploadType;
	HashMap<String, String> _reqQueryParams;			// for setting query parameters for the request
	
	//Property of response
	String _responseStatusCode;							// for response statuscode
	String _responseStatusLine;							// for response statusline
	String _responseString;								// for response string
	HashMap<String, String> _responseHeaders;			// for response headers
	
	//Enable or disable the logging for the request
	protected Boolean _logFlag;
	String certificate_password = "changeit";
	
	/**
	 * This method is for constructing an empty <code>RestAPIRequest</code> with
	 * default settings
	 * 
	 */	
	public RestAPIRequest(){
		_reqDesc = "General REST API request";
		_reqURL = null;
		_reqHeaders = new HashMap<String, String>();
		_reqMultipartHeaders =  new HashMap<String, String>();
		_reqMethod = null;
		_reqBody = null;
		_reqUserName = null;
		_reqPassword = null;
		_reqFileUpload = null;
		_reqFileUploadType=null;
		_responseStatusCode = null;
		_responseStatusLine = null;
		_responseString = null;
		_responseHeaders = new HashMap<String, String>();
		_logFlag = true;
		_reqQueryParams = new HashMap<String, String>();
	}
	
	/**
	 * This method is for resetting the details for REST request
	 * 
	 */	
	public void reset(){
		_reqDesc = "General REST API request";
		_reqURL = null;
		_reqHeaders.clear();
		_reqMultipartHeaders.clear();
		_reqMethod = null;
		_reqBody = null;
		_reqUserName = null;
		_reqPassword = null;
		_reqFileUpload = null;
		_responseStatusCode = null;
		_responseStatusLine = null;
		_responseString = null;
		_responseHeaders.clear();
		_logFlag = true;
		_reqQueryParams.clear();
	}
	
	/**
	 * This method is for setting description for REST request
	 * 
	 * @param desc
	 *            Description <code>String</code> for REST request You can use it to show the purpose of
	 *            this request.
	 * 
	 */	
	public void setDescription(String desc){
		_reqDesc = desc.trim();
	}
	
	/**
	 * This method is for getting description from REST request
	 * 
	 * @return <code>String</code>
	 * 
	 */	
	public String getDescription(){
		return _reqDesc;
	}
	
	/**
	 * This method is for setting URL for REST request
	 * 
	 * @param url
	 *            URL <code>String</code> for REST request
	 * 
	 */	
	public void setURL(String url){
		_reqURL = url.trim();
	}
	
	/**
	 * This method is for getting URL from REST request
	 * 
	 * @return <code>String</code>
	 * 
	 */	
	public String getURL(){
		return _reqURL;
	}
	
	/**
	 * This method is for setting Method for REST request
	 * 
	 * @param method
	 *            Method <code>String</code> for REST request e.g. GET, PUT, POST, DELETE etc
	 * 
	 */	
	public void setMethod(String method){
		_reqMethod = method.trim();
	}
	
	/**
	 * This method is for getting Method from REST request
	 * 
	 * @return <code>String</code>
	 * 
	 */
	public String getMethod(){
		return _reqMethod;
	}
	
	/**
	 * This method is for setting authentication information for REST request
	 * 
	 * @param username
	 *            Username <code>String</code> for REST request 
	 * @param password
	 *            Password <code>String</code> for REST request
	 * 
	 */	
	public void setAuthentication(String username, String password){
		_reqUserName = username.trim();
		_reqPassword = password.trim();
	}
	
	/**
	 * This method is for setting username (authentication info) for REST
	 * request
	 * 
	 * @param username
	 *            Username <code>String</code> for REST request
	 * 
	 */	
	public void setUsername(String username){
		_reqUserName = username.trim();
	}
	
	/**
	 * This method is for setting password (authentication info) for REST
	 * request
	 * 
	 * @param password
	 *            Password <code>String</code> for REST request
	 * 
	 */	
	public void setPassword(String password){
		_reqPassword = password.trim();
	}
	
	/**
	 * This method is for getting username (authentication info) from REST
	 * request
	 * 
	 * @return <code>String</code>
	 * 
	 */	
	public String getUsername(){
		return _reqUserName;
	}
	
	/**
	 * This method is for getting password (authentication info) from REST
	 * request
	 * 
	 * @return <code>String</code>
	 * 
	 */
	public String getPassword(){
		return _reqPassword;
	}
	
	/**
	 * This method is for setting headers string for REST request
	 * 
	 * @param headersString
	 *            All headers be formatted in a <code>String</code> with ":" and separated
	 *            with "," e.g. Content-Type:application/x-www-form-urlencoded,
	 *            Accept:application/xml, Accept-Encoding:gzip etc
	 * 
	 */		
	public void setHeadersString(String headersString){
		if(headersString == null || headersString.trim() ==""){
			TestLogger.logError("Empty headers for the request.");
			return;
		}
		String[] headerStr = headersString.trim().split(",");
		String[] headerEntry = null; 
		for (int i = 0; i < headerStr.length; i++){
			headerEntry = headerStr[i].trim().split(":");
			if(headerEntry.length != 2){
				TestLogger.logError("Invalid headers parameter provided! Please follow name:value,name:value format.");
				continue;
			}
			_reqHeaders.put(headerEntry[0].trim(), headerEntry[1].trim());
		}
		_reqHeaders.put("x-fapi-customer-ip-address", PropertyUtils.getProperty("cust_ip_add"));
	}
	
	/**
	 * This method is for getting headers string from REST request
	 * 
	 * @return <code>String</code>
	 */		
	public String getHeadersString(){
		if(_reqHeaders == null || _reqHeaders.isEmpty())
			return "";
		String headerString = "";
		for (Entry <String, String> headerEntry : _reqHeaders.entrySet()) {
			headerString = headerString + headerEntry.getKey() + 
			":" + headerEntry.getValue() + ", "; 			
		}
		return headerString;		
	}
	
	/**
	 * This method is for setting headers map for REST request
	 * 
	 * @param headersMap
	 *            All headers be formatted in a <code>HashMap</code>
	 * 
	 */		
	public void setHeadersMap(HashMap<String, String> headersMap){
		_reqHeaders = headersMap;		
	}
	
	/**
	 * This method is for getting headers map from REST request
	 * 
	 * @return <code>HashMap</code>
	 */	
	public HashMap<String, String> getHeadersMap(){
		return _reqHeaders;		
	}
	
	/**
	 * This method is for adding a header entry from REST request
	 * 
	 * @param name
	 *            Name of the header entry e.g. Content-Type, Accept,
	 *            Accept-Encoding etc
	 * @param value
	 *            Value of the header entry e.g. application/xml,
	 *            application/json, gzip etc
	 */		
	public void addHeaderEntry(String name, String value){
		_reqHeaders.put(name.trim(), value.trim());		
	}
	
	/**
	 * This method is for getting header's value by name from REST request
	 * 
	 * @param name
	 *            The name of header entry for querying e.g. Content-Type,
	 *            Accept, Accept-Encoding etc
	 * 
	 * @return <code>String</code>
	 */	
	public String getHeaderEntry(String name){
		return _reqHeaders.get(name.trim());
	}
	
	/**
	 * This method is for adding a header entry from multipart section of REST request
	 * 
	 * @param name
	 *            Name of the header entry e.g. Content-Type
	 *
	 * @param value
	 *            Value of the header entry e.g. application/vnd.ms-excel
	 */		
	protected void addMultipartHeaderEntry(String name, String value){
		_reqMultipartHeaders.put(name.trim(), value.trim());		
	}
	
	/**
	 * This method is for getting header's value by name from multipart section of REST request
	 * 
	 * @param name
	 *            The name of header entry for querying e.g. Content-Type
	 * 
	 * @return <code>String</code>
	 */	
	protected String getMultipartHeaderEntry(String name){
		return _reqMultipartHeaders.get(name.trim());
	}
	
	/**
	 * This method is for setting request body for REST request
	 * 
	 * @param requestBody
	 *            <code>String</code> of request body e.g. default=true etc
	 * 
	 */	
	public void setRequestBody(String requestBody){
		
		// Handled Null condition to avoid error
		if (requestBody == null) {
			requestBody = "";
		}	
		_reqBody = requestBody.trim();
	}
	
	/**
	 * This method is for setting request body (including dynamic parameters)
	 * for REST request
	 * 
	 * @param requestBodyString
	 *            <code>String</code> of request body (parameters formatted in #A#) 
	 *            e.g. name=#keyName# etc
	 * @param bodyParams
	 *            Actual parameter values which we want to replace in body
	 *            string e.g. <code>Map</code>({keyName:testkey}) etc
	 * 
	 */	
	public void setRequestBody(String requestBodyString,  Map<String, String> bodyParams){		
		_reqBody = requestBodyString.trim();
		if(bodyParams == null || bodyParams.isEmpty())
			return;
		for(Entry<String,String> entry: bodyParams.entrySet())
			_reqBody = _reqBody.replaceAll( "#"+ entry.getKey() +"#" , entry.getValue());
	}
	
	/**
	 * This method for setting URL encoded request body which is used in REST
	 * API request.
	 * 
	 * @param reqBodyParamMap
	 *            <code>String</code> It denotes HashMap for parameter/value in
	 *            request body.<br>
	 *            e.g. {param1:1, pramer2:2}, the URL encoded request body will
	 *            be param1=1&param2=2&param3=3....
	 * 
	 */
	public void setUrlEncodedRequestBody(HashMap<String, String> reqBodyParamMap) {
		_reqBody = null;
		if(reqBodyParamMap == null || reqBodyParamMap.size() == 0)
			return;			
		for (Entry<String, String> reqBodyParamEntry : reqBodyParamMap.entrySet()){
			String name = reqBodyParamEntry.getKey();
			String value = reqBodyParamEntry.getValue();
			if(value != null){
				if(_reqBody == null)
					_reqBody = name + "=" + value;
				else
					_reqBody = _reqBody + "&" + name + "=" + value;
			}
		}
	}
	
	/**
	 * This method is for getting request body from REST request
	 * 
	 * @return <code>String</code>
	 */	
	public String getRequestBody(){
		return _reqBody;
	}

	/**
	 * This method will disable the log creation for this Rest request.
	 * 
	 */
	public void disableReqDetailLog(){
		_logFlag = false;
	}
	
	/**
	 * This method will enable the log creation for this Rest request.
	 * Use this only if you have disabled the log creation using disableReqDetailLog().
	 * 
	 */
	public void enableReqDetailLog(){
		_logFlag = true;
	}
	
	/**
	 * This method is used set the filePath of file to be uploaded as multi-part data for this Rest request.
	 * 
	 */
	public void setFileUploadType(String fileType){
		_reqFileUploadType = fileType;
	}
	
	/**
	 * This method is used set the filePath of file to be uploaded as multi-part data for this Rest request.
	 * 
	 */
	public void setFileUploadPath(String filePath){
		_reqFileUpload = filePath;
	}
	
	/**
	 * This method is used get the filePath of file to be uploaded as multi-part data for this Rest request.
	 * 
	 */
	public String getFileUploadPath(){
		return _reqFileUpload;
	}
	
	/**
	 * This method is for adding a query parameters for the REST request
	 * 
	 * @param name
	 *            Name of the parameter
	 *
	 * @param value
	 *            Value of the parameter
	 *//*		
	protected void addQueryParameter(String name, String value){
		_reqQueryParams.put(name.trim(), value.trim());		
	}*/
	
	/**
	 * This method is for submitting the REST request (Request/Response detail
	 * will be logged in report unless it is specifically disabled by user using
	 * disableReqDetailLog() function)
	 *
	 * 
	 */	
	public void submit() {
			//Print the REST request details in the log report
			if(_logFlag){
				TestLogger.logBlankLine();
				TestLogger.logInfo("Request Description: " + _reqDesc);
				TestLogger.logInfo("Submitting Rest API request with configurations below: ");
				TestLogger.logInfo("URL: " + _reqURL);
				TestLogger.logInfo("Method: " + _reqMethod);
				TestLogger.logInfo("Headers: " + getHeadersString());
				if(_reqQueryParams.size()>0)
					TestLogger.logInfo("Query Params: " + _reqQueryParams.toString());
				
				if(_reqBody == null)
					TestLogger.logInfo("Body Parameters: ");
				else
					TestLogger.logInfo("Body Parameters: " + _reqBody);
				if(_reqUserName != null)
					TestLogger.logInfo("Authentication - Username: " + _reqUserName);
				TestLogger.logBlankLine();
			}
			
			
			//Initialize the REST request instance via RestAssured API
			RequestSpecBuilder reqBuilder = new RequestSpecBuilder(); 
			reqBuilder.addHeaders(_reqHeaders);
			if(_reqBody!=null && !_reqBody.trim().isEmpty())
				reqBuilder.setBody(_reqBody);
			
			RequestSpecification requestSpec = reqBuilder.build();
			
			if(API_Constant.SECURITY_CONFORMANCE_ENABLED) {
				requestSpec.proxy("127.0.0.1", 8082);
			}
			
			if(_reqUserName != null && !_reqUserName.isEmpty())
				requestSpec.authentication().preemptive().basic(_reqUserName , _reqPassword);
				//requestSpec.authentication().basic(_reqUserName, _reqPassword);
			
			for (Iterator<Entry<String,String>> iterator = _reqQueryParams.entrySet().iterator(); iterator.hasNext();) {
				Entry<String, String> entry = iterator.next();
				requestSpec.queryParameter(entry.getKey(), entry.getValue());
			}
			
			if(_reqFileUpload != null && !_reqFileUpload.trim().isEmpty()){
				/*MultiPartSpecification multiSpec = new MultiPartSpecBuilder(new File(_reqFileUpload)).build();
				requestSpec.multiPart(multiSpec);*/
				requestSpec.multiPart("FileParam", new File(_reqFileUpload), _reqFileUploadType);
			}
	
			// adding certificate for server side authentication
			boolean addCertStatus = addServerCertificate();
			if(addCertStatus){
				//Submit the request
				Response response = null;
				ResponseSpecification responseSpecification = RestAssured.given().spec(requestSpec).expect();
	
				if (_reqMethod.trim().equalsIgnoreCase("GET")) {
					response = responseSpecification.get(_reqURL);
				} else if (_reqMethod.trim().equalsIgnoreCase("POST")) {
					response = responseSpecification.post(_reqURL);
				} else if (_reqMethod.trim().equalsIgnoreCase("DELETE")) {
					response = responseSpecification.delete(_reqURL);
				} else if (_reqMethod.trim().equalsIgnoreCase("PUT")) {
					response = responseSpecification.put(_reqURL);
				}
				
				//Retrieve the response content
				if (response != null) {
					_responseString = response.asString();
					_responseStatusCode = Integer.toString(response.getStatusCode());
					_responseStatusLine = response.getStatusLine();
					Iterator<Header>  itr = response.getHeaders().iterator();
					while(itr.hasNext()){
						Header header = itr.next();
						_responseHeaders.put(header.getName(), header.getValue());
					}
					
					
					if(_logFlag){
						TestLogger.logInfo("Request submitted successful, response detail as below:");
						TestLogger.logInfo("StatusCode: " + _responseStatusCode);
						TestLogger.logInfo("StatusLine: " + _responseStatusLine);
						TestLogger.logInfo("ResponseString: " + _responseString);
						if( _responseHeaders.get("x-fapi-interaction-id") != null){
							TestLogger.logInfo("Corelation ID: " + _responseHeaders.get("x-fapi-interaction-id"));
						}
						TestLogger.logBlankLine();
					}
				}	
				else if(_logFlag){
					TestLogger.logInfo("Request submitted failure, response is null");
					TestLogger.logBlankLine();
				}
			}else{
				TestLogger.logError("Certificate is not added properly");
			}
	}
	
	/**
	 * This method is for getting response status code
	 * 
	 * @return <code>String</code>
	 */	
	public String getResponseStatusCode(){
		return _responseStatusCode;
	}
	
	/**
	 * This method is for getting response status line
	 * 
	 * @return <code>String</code>
	 */	
	public String getResponseStatusLine(){
		return _responseStatusLine;
	}
	
	/**
	 * This method is for getting response string
	 * 
	 * @return <code>String</code>
	 */	
	public String getResponseString(){
		return _responseString;
	}
	
	/**
	 * This method is for getting response header value by providing the response header name
 	 * 
 	 * @param headerName It is the name of the header that needs to be retrieved
	 *            
	 * @return <code>String</code>
	 */	
	public String getResponseHeader(String headerName){
		return _responseHeaders.get(headerName);
	}
	
	/**
	 * This method is for getting all the response headers as key-value pair
	 *            
	 * @return <code>HashMap<String, String></code>
	 */	
	public HashMap<String, String> getResponseHeaders(){
		return _responseHeaders;
	}

	/**
	 * This method is for getting response(json/xml) value by specifying
	 * customized dataPath
	 * 
	 * @param dataPath
	 *            <code>String</code> of datapath e.g.
	 *            "addreses.price.currencyCode" ,
	 *            "addresses.find {addresses -> addresses.id == '10000957'}.location"
	 *            etc.
	 * @see reference 
	 *      http://rest-assured.googlecode.com/svn/tags/1.7.2/apidocs/com
	 *      /jayway/restassured/path/json/JsonPath.html
	 * @see reference 
	 *      http://rest-assured.googlecode.com/svn/tags/1.7.2/apidocs/com
	 *      /jayway/restassured/path/xml/XmlPath.html
	 * 
	 * @return <code>Object</code> The return type is <code>Object</code>,
	 *         because it may return <code>String</code>, <code>Integer</code>,
	 *         <code>List<*></code>... based on query result for specified
	 *         datapath. User need to cast it into actual types
	 * 
	 */
	public Object getResponseValueByPath(String dataPath){
		
		if(_responseString == null || _responseString.trim().equals("")){
			TestLogger.logError("Response String is null");
			return null;
		}
		if(dataPath == null || dataPath.trim().equals("")){
			TestLogger.logError("Provided dataPath String is null");
			return null;
		}
		
		try{
			if(!(_reqHeaders.get("Accept") == null)){
				if(_reqHeaders.get("Accept").trim().equalsIgnoreCase("application/json")){
					JsonPath jsonPath = new JsonPath(_responseString);
					return jsonPath.get(dataPath);
				}
				else if(_reqHeaders.get("Accept").trim().equalsIgnoreCase("application/xml")){
					XmlPath xmlPath = new XmlPath(_responseString);
					return xmlPath.get(dataPath);
				}
				else {
					TestLogger.logError("Accept type for the request is unknown or incorrect, hence it is processed as application/xml by default");
					XmlPath xmlPath = new XmlPath(_responseString);
					return xmlPath.get(dataPath);
				}
			}
			else {
				TestLogger.logError("Accept type for the request is not provided, hence it is processed as application/xml");
				/*XmlPath xmlPath = new XmlPath(_responseString);
				return xmlPath.get(dataPath);*/
				JsonPath jsonPath = new JsonPath(_responseString);
				return jsonPath.get(dataPath);
			}
						
		}
		catch(Exception e){
			TestLogger.logError("Failed to get response value for specified datapath : " + dataPath);
			TestLogger.logError("Error happens: " + e.toString());
			return null;
		}
	}
	
	/**
	 * This method is for getting response(json/xml) value by specifying
	 * customized dataPath
	 * 
	 * @param dataPath
	 *            <code>String</code> of datapath e.g.
	 *            "addreses.price.currencyCode" ,
	 *            "addresses.find {addresses -> addresses.id == '10000957'}.location"
	 *            etc.
	 * @see reference 
	 *      http://rest-assured.googlecode.com/svn/tags/1.7.2/apidocs/com
	 *      /jayway/restassured/path/json/JsonPath.html
	 * @see reference 
	 *      http://rest-assured.googlecode.com/svn/tags/1.7.2/apidocs/com
	 *      /jayway/restassured/path/xml/XmlPath.html
	 * 
	 * @return <code>List<String></code> The return type is <code>List<String></code>,
	 *        
	 * 
	 */
	public List<String> getResponseListByPath(String dataPath){
		
		if(_responseString == null || _responseString.trim().equals("")){
			TestLogger.logError("Response String is null");
			return null;
		}
		if(dataPath == null || dataPath.trim().equals("")){
			TestLogger.logError("Provided dataPath String is null");
			return null;
		}
		
		try{
			if(!(_reqHeaders.get("Accept") == null)){
				if(_reqHeaders.get("Accept").trim().equalsIgnoreCase("application/json")){
					JsonPath jsonPath = new JsonPath(_responseString);
					return jsonPath.getList(dataPath);
				}
				else if(_reqHeaders.get("Accept").trim().equalsIgnoreCase("application/xml")){
					XmlPath xmlPath = new XmlPath(_responseString);
					return xmlPath.getList(dataPath);
				}
				else {
					TestLogger.logError("Accept type for the request is unknown or incorrect, hence it is processed as application/xml by default");
					XmlPath xmlPath = new XmlPath(_responseString);
					return xmlPath.getList(dataPath);
				}
			}
			else {
				TestLogger.logError("Accept type for the request is not provided, hence it is processed as application/xml");
				XmlPath xmlPath = new XmlPath(_responseString);
				return xmlPath.getList(dataPath);
			}
						
		}
		catch(Exception e){
			TestLogger.logError("Failed to get response value for specified datapath : " + dataPath);
			TestLogger.logError("Error happens: " + e.toString());
			return null;
		}
	}
	
	/**
	 * This method is for getting response(json/xml) node string value by
	 * specifying customized dataPath
	 * 
	 * @param dataPath
	 *            <code>String</code> of datapath e.g.
	 *            "addreses.price.currencyCode" etc. User should ensure the
	 *            dataPath only return <code>String</code> result, or will get
	 *            error exception
	 * 
	 * @see reference
	 *      http://rest-assured.googlecode.com/svn/tags/1.7.2/apidocs/com/jayway/restassured/path/json/JsonPath.html
	 * @see reference
	 *      http://rest-assured.googlecode.com/svn/tags/1.7.2/apidocs/com/jayway/restassured/path/xml/XmlPath.html
	 *      
	 * @return <code>String</code>
	 * 
	 */
	public String getResponseNodeStringByPath(String dataPath){
		Object result = getResponseValueByPath(dataPath);
		String returnString = null;
		if(result==null)
			return returnString;
		if(result instanceof Boolean){
			if((Boolean)result)
				returnString = "true";
			else
				returnString = "false";
		}
		else
			returnString = result.toString();
		return returnString;
	}

	/**
	 * This method is for checking if accept type of response string is in JSON
	 * 
	 * @return <code>boolean</code>
	 */	
	public boolean isResponseInJSON(){
		if(_reqHeaders.get("Accept") == null)
			return false;
		if(_reqHeaders.get("Accept").trim().equalsIgnoreCase("application/json"))
			return true;
		else 
			return false;	
	}
	
	/**
	 * This method is for checking if content-type of request body string is in JSON
	 * 
	 * @return <code>boolean</code>
	 */	
	public boolean isRequestInJSON(){
		if(_reqHeaders.get("Content-Type") == null)
			return false;
		if(_reqHeaders.get("Content-Type").trim().equalsIgnoreCase("application/json"))
			return true;
		else 
			return false;	
	}
	
	/**
	 * This method is for checking if content-type of request body string is in XML
	 * 
	 * @return <code>boolean</code>
	 */	
	public boolean isRequestInXML(){
		if(_reqHeaders.get("Content-Type") == null)
			return false;
		if(_reqHeaders.get("Content-Type").trim().equalsIgnoreCase("application/xml"))
			return true;
		else 
			return false;	
	}
	
	/**
	 * This method is for printing detail info of request in html report on demand
	 * 
	 */	
	public void printRequestDetail(){
		TestLogger.logBlankLine();
		TestLogger.logInfo("Request Description: " + _reqDesc);
		TestLogger.logInfo("Submitting Rest API request with configurations below: ");
		TestLogger.logInfo("URL: " + _reqURL);
		TestLogger.logInfo("Method: " + _reqMethod);
		TestLogger.logInfo("Headers: " + getHeadersString());
		if(_reqQueryParams.size()>0)
			TestLogger.logInfo("Query Params: " + _reqQueryParams.toString());
		if(_reqBody == null)
			TestLogger.logInfo("Body Parameters: ");
		else
			TestLogger.logInfo("Body Parameters: " + _reqBody);
		if(_reqUserName != null)
			TestLogger.logInfo("Authentication - Username: " + _reqUserName);
		TestLogger.logBlankLine();
		TestLogger.logInfo("Request submitted, response detail as below:");
		TestLogger.logInfo("StatusCode: " + _responseStatusCode);
		TestLogger.logInfo("StatusLine: " + _responseStatusLine);
		TestLogger.logInfo("ResponseString: " + _responseString);
		TestLogger.logBlankLine();

	}
	
	/**
	 * This method is for getting response error message
	 * 
	 * @return <code>String</code>
	 */	
	public String getResponseErrorMsg(){
		if(isResponseInJSON())
			return getResponseNodeStringByPath("errorMessage");
		else
			return getResponseNodeStringByPath("ErrorResponse.errorMessage");
	}
	
	/**
	 * This method is for getting response error code
	 * 
	 * @return <code>String</code>
	 */	
	public String getResponseErrorCode(){
		if(isResponseInJSON())
			return getResponseNodeStringByPath("errorCode");
		else
			return getResponseNodeStringByPath("ErrorResponse.errorCode");
	}
	
	/**
	 * This method is for getting response exception stack
	 * 
	 * @return <code>String</code>
	 */	
	public String getResponseExpStack(){
		if(isResponseInJSON())
			return getResponseNodeStringByPath("exceptionStack");
		else
			return getResponseNodeStringByPath("ErrorResponse.exceptionStack");
	}
	/**
	 * This method is for adding server side certificate to java truststore
	 *
	 * @return boolean
	 */
	public boolean addServerCertificate(){
		try{
			createCertificate();
			String clientCertificatePath = PropertyUtils.propertyMap.get("CertificatePath");
			KeyStore clientStore = KeyStore.getInstance("PKCS12");
			clientStore.load(new FileInputStream(clientCertificatePath), certificate_password.toCharArray());
			
			KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
			kmf.init(clientStore, certificate_password.toCharArray());
			KeyManager[] kms = kmf.getKeyManagers();
	
			TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
				@Override
                 	public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                 }

                 @Override
                    public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                 }
                 @Override
                     public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                     return new java.security.cert.X509Certificate[]{};
                  }
               }
            };
			
			SSLContext sslContext = null;
			sslContext = SSLContext.getInstance("TLS");
			sslContext.init(kms, trustAllCerts, new SecureRandom());
			SSLSocketFactory lSchemeSocketFactory=null;
			lSchemeSocketFactory = new SSLSocketFactory(sslContext, new AllowAllHostnameVerifier());
			// configure Rest Assured
			RestAssured.config = RestAssured.config().sslConfig(SSLConfig.sslConfig().with().sslSocketFactory(lSchemeSocketFactory).and().x509HostnameVerifier(new AllowAllHostnameVerifier()));
			return true;
		}
		catch(Exception ex){			
			TestLogger.logError("Getting exception on adding server certificate." );
			ex.printStackTrace();	
			return false;
		}
	}
	
	public void startAgent(){
		try{
			Runtime rt = Runtime.getRuntime();
	        Process p = rt.exec("cmd /c netstat -ano | findstr 8081");
	        BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
	        String s = null;
			if ((s = stdInput.readLine()) != null) {
			  int index=s.lastIndexOf(" ");
			  String sc=s.substring(index, s.length());
			  System.out.println("Closing the already running instance of request object agent with process id :"+sc);
			  p = rt.exec("cmd /c Taskkill /PID" +sc+" /T /F");
			}
			System.out.println("Starting the agent.....");
			Thread.sleep(1000);
			p = rt.exec("cmd /K cd "+PropertyUtils.propertyMap.get("RequestObjectPath")+" && node index.js");
			stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
			if ((s = stdInput.readLine()) != null && s.equalsIgnoreCase("App listening at http://:::8081")) {
				System.out.println(s);
				System.out.println("Agent started successfully.....");
			}else{
				System.out.println("Failed to start the agent");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void stopAgent(){
		try{
			Runtime rt = Runtime.getRuntime();
	        Process p = rt.exec("cmd /c netstat -ano | findstr 8081");
	        BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
	        String s = null;
			if ((s = stdInput.readLine()) != null) {
			  int index=s.lastIndexOf(" ");
			  String sc=s.substring(index, s.length());
			  System.out.println("Closing the already running instance of request object agent with process id :"+sc);
			  p = rt.exec("cmd /c Taskkill /PID" +sc+" /T /F");
			}			
		}catch(Exception e){
			e.printStackTrace();
		}		
	}
	
	@SuppressWarnings("unused")
	public void createCertificate() throws IOException, InterruptedException{
		
		String keyFileLoc = null;;
		String pemFileLoc = null;
		
		String fileLocation = PropertyUtils.propertyMap.get("InFilesPath")+"/Network";
		String certificatePath = PropertyUtils.envPath+"/CertificateDetails/Certificate.p12";
		
		if(!new File(certificatePath).exists()){
			keyFileLoc = Misc.getFileUsingExt(fileLocation, ".key");
			pemFileLoc = Misc.getFileUsingExt(fileLocation, ".pem");
			
			if(keyFileLoc != null && pemFileLoc != null){				
				String command="openssl pkcs12 -export -out "+certificatePath+" -inkey "+keyFileLoc+" -in "+pemFileLoc+" -password pass:"+certificate_password;
				Runtime r=Runtime.getRuntime();
				Process p=r.exec(command);
				Thread.sleep(1000);
				if(new File(certificatePath).exists()){
					System.out.println("Network certificate has been created successfully");
					Thread.sleep(2000);
				}else{
					System.out.println("Network certificate failed to create");
				}
			}else{
				System.out.println("Either key or pem file are not correct or not found");
			}
		}
	}
	
}
