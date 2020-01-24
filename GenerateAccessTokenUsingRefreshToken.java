package com.psd2.wrapper;

import java.util.HashMap;

import com.psd2.core.RestAPIRequest;
import com.psd2.utils.PropertyUtils;

public class GenerateAccessTokenUsingRefreshToken extends RestAPIRequest{
	
	private String _baseURL;
	private String _refreshToken;
	private String _grant_type;
	private String _client_id;

	HashMap<String, String> _bodyMap = new HashMap<String, String>();
	
	/**
	 * This method is for constructing an empty GenerateAccessTokenUsingRefreshToken with default settings
	 */
	public GenerateAccessTokenUsingRefreshToken(){
		_baseURL = "";
		_refreshToken="";
		_grant_type="";
		_client_id="";
		setDescription("Create Access Token by using refresh token");
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
	 * This method is for setting refresh token for REST request
	 */	
	public void setRefreshToken(String token){
		_refreshToken = token;
	}
	
	/**
	 * This method is for setting grant type for REST request
	 */	
	public void setGrantType(String value){
		_grant_type = value;
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
			_bodyMap.put("grant_type", "refresh_token");
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
		_bodyMap.put("refresh_token", _refreshToken);
		
		
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
		return getResponseNodeStringByPath("access_token");
	}
	
	/**
	 * This method is for getting token_type from the request response
	 * 
	 * @return <code>String</code>
	 */
	public String getTokenType() {
		return getResponseNodeStringByPath("token_type");
	}
	
	/**
	 * This method is for getting expires_in from the request response
	 * 
	 * @return <code>String</code>
	 */
	public String getExpiresin() {
		return getResponseNodeStringByPath("expires_in");
	}
}
