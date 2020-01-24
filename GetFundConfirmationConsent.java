package com.psd2.wrapper;

import com.psd2.core.RestAPIRequest;
import com.psd2.utils.PropertyUtils;

public class GetFundConfirmationConsent extends RestAPIRequest{
	
	private String _baseURL;

	/**
	 * This method is for constructing an empty Get Fund Confirmation Consent with default settings
	 */
	public GetFundConfirmationConsent(){
		setDescription("Get Fund Confirmation Consent");
		setHeadersString("Content-Type:application/json, x-fapi-financial-id:"+PropertyUtils.getProperty("fin_id")+", Accept:application/json");
		setMethod("GET");
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
	
	/**
	 * This method is for getting ConsentId from the request response
	 * 
	 */
	public String getConsentId() {
		return getResponseNodeStringByPath("Data.ConsentId");
	}	
	
	/**
	 * This method is for getting CreationDateTime from the request response
	 * 
	 */
	public String getCreationDateTime() {
		return getResponseNodeStringByPath("Data.CreationDateTime");
		
	}	
	
	/**
	 * This method is for getting Status from the request response
	 * 
	 */
	public String getStatus() {
		return getResponseNodeStringByPath("Data.Status");
	}
	
	/**
	 * This method is for getting StatusUpdateDateTime from the request response
	 * 
	 */
	public String getStatusUpdateDateTime() {
		return getResponseNodeStringByPath("Data.StatusUpdateDateTime");
	}
	
	/**
	 * This method is for getting ExpirationDateTime from the request response
	 * 
	 */
	public String getExpirationDateTime() {
		return getResponseNodeStringByPath("Data.ExpirationDateTime");
	}
	
	/**
	 * This method is for getting SchemeName from the request response
	 * 
	 */
	public String getSchemeName() {
		return getResponseNodeStringByPath("Data.DebtorAccount.SchemeName");
	}
	
	/**
	 * This method is for getting Identification from the request response
	 * 
	 */
	public String getIdentification() {
		return getResponseNodeStringByPath("Data.DebtorAccount.Identification");
	}
	
	/**
	 * This method is for getting Name from the request response
	 * 
	 */
	public String getName() {
		return getResponseNodeStringByPath("Data.DebtorAccount.Name");
	}
	
	/**
	 * This method is for getting SecondaryIdentification from the request response
	 * 
	 */
	public String getSecondaryIdentification() {
		return getResponseNodeStringByPath("Data.DebtorAccount.SecondaryIdentification");
	}
}
