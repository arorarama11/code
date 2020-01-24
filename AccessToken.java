package com.psd2.wrapper;

import java.util.HashMap;

import com.psd2.core.RestAPIRequest;
import com.psd2.utils.PropertyUtils;

public class AccessToken extends RestAPIRequest{
	
	private String _baseURL;
	private String _authCode;
	private String _grant_type;
	private String _redirect_uri;
	private String _client_id;


	HashMap<String, String> _bodyMap = new HashMap<String, String>();
	
	/**
	 * This method is for constructing an empty AccessToken with default  settings
	 */
	public AccessToken(){
		_baseURL = "";	
		_authCode = "";
		_grant_type="";
		_redirect_uri="";
		_client_id="";
		setDescription("Create Access Token");
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
	 * This method is for setting Auth Code for REST request
	 */	
	public void setAuthCode(String authCode){
		_authCode = authCode;
	}
	
	/**
	 * This method is for setting grant_type for REST request
	 */	
	public void setGrantType(String value){
		_grant_type = value;
	}
	
	/**
	 * This method is for setting Redirect URI for REST request
	 */	
	public void setRedirectURI(String value){
		_redirect_uri = value;
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
	public void genRequestBody(){
		if(_grant_type != null && _grant_type != ""){
			_bodyMap.put("grant_type", _grant_type);

		}else{
			_bodyMap.put("grant_type", "authorization_code");
		}
		if(_redirect_uri != null && _redirect_uri != ""){
			_bodyMap.put("redirect_uri", _redirect_uri);

		}else{
			_bodyMap.put("redirect_uri", PropertyUtils.getProperty("redirect_url"));
		}
		_bodyMap.put("code", _authCode);
		
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
	 * This method is for getting Access Token Id from the request response
	 * 
	 */
	public String getAccessToken() {
		if (this.isResponseInJSON())
			return getResponseNodeStringByPath("access_token");
		else
			return getResponseNodeStringByPath("\"Data\"");
	}
	
	/**
	 * This method is for getting Refresh Token Id from the request response
	 * 
	 */
	public String getRefreshToken() {
		if (this.isResponseInJSON())
			return getResponseNodeStringByPath("refresh_token");
		else
			return getResponseNodeStringByPath("\"Data\"");
	}	
}

