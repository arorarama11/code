package com.psd2.wrapper;

import java.util.HashMap;

import com.psd2.core.RestAPIRequest;
import com.psd2.utils.PropertyUtils;

public class ClientCredential extends RestAPIRequest{
	
	private String _baseURL;
	private String _scope;
	private String _grant_type;
	private String _client_id;

	HashMap<String, String> _bodyMap = new HashMap<String, String>();
	
	/**
	 * This method is for constructing an empty ClientCredential with default settings
	 */
	public ClientCredential(){
		_baseURL = "";
		_scope="";
		_grant_type="";
		_client_id="";
		setDescription("Create Client Credentials");
		setHeadersString("Content-Type:application/x-www-form-urlencoded, Accept:application/json");
		setMethod("POST");
	}
	
	/**
	 * This method is for setting BaseURL for REST request
	 */	
	public void setBaseURL(String baseURL){
		_baseURL = baseURL;
	}
	
	/**
	 * This method is for setting scope for REST request
	 */	
	public void setScope(String scope){
		_scope = scope;
	}
	
	/**
	 * This method is for setting grant_type for REST request
	 */	
	public void setGrant_Type(String grant_type){
		_grant_type = grant_type;
	}
	
	/**
	 * This method is for setting client_id for REST request
	 */	
	public void setClientID(String client_id){
		_client_id = client_id;
	}
	
	/**
	 * This method is for setting body for REST request
	 */	
	private void genRequestBody(){
		if(_grant_type != null && _grant_type != ""){
			_bodyMap.put("grant_type", _grant_type);

		}else{
			_bodyMap.put("grant_type", "client_credentials");
		}
		if(_scope != null && _scope != ""){
			_bodyMap.put("scope", _scope);

		}else{
			_bodyMap.put("scope", "accounts");
		}
		if(PropertyUtils.getProperty("client_secret").equalsIgnoreCase("NA")){
			if(_client_id != null && _client_id != ""){
				_bodyMap.put("client_id", _client_id);
	
			}else{
				_bodyMap.put("client_id", PropertyUtils.getProperty("client_id"));
			}
		}else{
			setAuthentication(PropertyUtils.getProperty("client_id"), PropertyUtils.getProperty("client_secret"));
		}
	}
	
	/**
	 * This method is for submitting the REST request 
	 */
	public void submit() {
		setURL(_baseURL);
		genRequestBody();
		setUrlEncodedRequestBody(_bodyMap);
		super.submit();
	}
	
	/**
	 * This method is for getting access token from the request response
	 * 
	 * @return <code>String</code>
	 */
	public String getAccessToken() {
		if (this.isResponseInJSON())
			return getResponseNodeStringByPath("access_token");
		else
			return getResponseNodeStringByPath("{.access_token");
	}
	
	/**
	 * This method is for getting token_type from the request response
	 * 
	 * @return <code>String</code>
	 */
	public String getTokenType() {
		if (this.isResponseInJSON())
			return getResponseNodeStringByPath("token_type");
		else
			return getResponseNodeStringByPath("{" + ".token_type");
	}
	
	/**
	 * This method is for getting expires_in from the request response
	 * 
	 * @return <code>String</code>
	 */
	public String getExpiresin() {
		if (this.isResponseInJSON())
			return getResponseNodeStringByPath("expires_in");
		else
			return getResponseNodeStringByPath("{" + ".expires_in");
	}
}
