package com.psd2.wrapper;

import com.psd2.core.RestAPIRequest;
import com.psd2.utils.PropertyUtils;

public class RevokeConsent extends RestAPIRequest{
	
	private String _baseURL;
	
	/**
	 * This method is for constructing an empty GetAccounts with default  settings
	 */
	public RevokeConsent(){
		_baseURL = "";	
		setDescription("");
		setHeadersString("x-fapi-financial-id:"+PropertyUtils.getProperty("fin_id")+", Accept:application/json ");
		setMethod("DELETE");
	}
	
	/**
	 * This method is for setting BaseURL for REST request
	 */	
	public void setBaseURL(String baseURL){
		_baseURL = baseURL;
	}
	
	/**
	 * This method is for submitting the REST request 
	 */
	public void submit() {
		setURL(_baseURL);
		super.submit();
	}
	
}

