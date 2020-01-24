package com.psd2.wrapper;

import java.io.File;

import com.psd2.core.RestAPIRequest;
import com.psd2.utils.Misc;
import com.psd2.utils.PropertyUtils;

public class RequestObjectCreation extends RestAPIRequest{
	
	private String _baseURL;
	private String _reqBody;
	private String _value;
	private String _scope;
	private String _inPath=PropertyUtils.envPath+"/RequestObjectAgent/RequestObject.zip";
	private String _outPath=PropertyUtils.envPath+"/RequestObjectAgent";

	/**
	 * This method is for constructing an empty RequestObjectCreation with default settings
	 */
	public RequestObjectCreation(){
		_baseURL = "";
		_value = ""; 
		_scope = "";
		setDescription("Create Request Object Creation");
		setHeadersString("Content-Type:application/json, Accept:application/json");
		setMethod("POST");
	}
	
	/**
	 * This method is for setting BaseURL for REST request
	 */	
	public void setBaseURL(String baseURL){
		_baseURL = baseURL;
	}
	
	/**
	 * This method is for setting BaseURL for REST request
	 */	
	public void setValueField(String accountReqId){
		_value = accountReqId;
	}
	
	/**
	 * This method is for setting BaseURL for REST request
	 */	
	public void setScopeField(String scope){
		_scope = scope;
	}
	
	/**
	 * This method is for setting body for REST request
	 */	
	public String genRequestBody(){
		if(_scope.equalsIgnoreCase("")){
			_scope = "accounts";
		}
						_reqBody = "{"
								+ "\"iss\": \""+PropertyUtils.getProperty("client_id")+"\","
								+ "\"aud\": \""+PropertyUtils.getProperty("aud")+"\","
								+ "\"response_type\": \"code id_token\","
								+ "\"client_id\": \""+PropertyUtils.getProperty("client_id")+"\","
								+ "\"redirect_uri\": \""+PropertyUtils.getProperty("redirect_url")+"\","
								+ "\"scope\": \"openid "+_scope+"\","
								+ "\"state\": \""+PropertyUtils.getProperty("state")+"\","
								+ "\"nonce\": \""+PropertyUtils.getProperty("nonce")+"\","
								+ "\"max_age\": "+PropertyUtils.getProperty("max_age")+","
								+ "\"claims\": "
									+ "{"
									+ 	"\"userinfo\":"
									+ 		"{"
									+ 			"\"openbanking_intent_id\": {\"value\": \""+_value+"\", \"essential\": true"
									+ 		"}"
									+ "},"
									+ "\"id_token\": "
									+ "{\"openbanking_intent_id\": "
									+ 		"{\"value\": \""+_value+"\","
									+ 		"\"essential \": true"
									+ "},"
									+ "\"acr\": {"
									+ 		"\"essential\": true,"
									+ 		"\"values\": [\"urn:openbanking:psd2:sca\", \"urn:openbanking:psd2:ca\"]"
									+ "}"
									+ "}"
									+ "}"
									+ "} ";
						return _reqBody;
		}
	/**
	 * This method is for submitting the REST request 
	 */
	public void submit() {
		try {
			setURL(_baseURL);
			setRequestBody(genRequestBody());
			if(!(new File(_outPath+"/RequestObject").exists()))
			{
				Misc.unzipFile(_inPath, _outPath);
				Misc.createFileWithCopyContent(Misc.getFileUsingExt(PropertyUtils.envPath+"/CertificateDetails/InFiles/Signing", ".key"), PropertyUtils.envPath+"/RequestObjectAgent/RequestObject/private.key");
			}
			startAgent();
			Thread.sleep(1000);
			super.submit();
			for(int i=0;i<5;i++){
				if(!getResponseStatusCode().equalsIgnoreCase("200")){
					stopAgent();
					Thread.sleep(1000);
					startAgent();
					super.submit();
					} 
				else{
					break;
				}
			}
			stopAgent();
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * This method is for getting AccountRequestId from the request response
	 * 
	 */
	public String getOutId() {
		if (this.isResponseInJSON())
			return getResponseNodeStringByPath("out");
		else
			return getResponseNodeStringByPath("\"out\"");
	}	
}


