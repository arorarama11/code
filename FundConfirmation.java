package com.psd2.wrapper;

import com.psd2.core.RestAPIRequest;
import com.psd2.utils.PropertyUtils;

public class FundConfirmation extends RestAPIRequest{
	
	private String _baseURL;
	private String _reqBody;
	private String _setReference;
	private String _setConsentId;
	private String _setAmount;
	private String _setCurrency;

	/**
	 * This method is for constructing an empty Fund Confirmation ith default settings
	 */
	public FundConfirmation(){
		_baseURL = "";
		_setReference = "Fund Conf Ref 123";
		_setAmount = "14.00";
		_setCurrency = "GBP";
		setDescription("Create Fund Confirmation");
		setHeadersString("Content-Type:application/json, x-fapi-financial-id:"+PropertyUtils.getProperty("fin_id")+", Accept:application/json");
		setMethod("POST");
	}
	
	/**
	 * This method is for setting BaseURL for REST request
	 */	
	public void setBaseURL(String baseURL){
		_baseURL = baseURL;
	}
	
	/**
	 * This method is used to set Reference
	 */
	public void setReferenceField(String refField){
		_setReference = refField;
	}
	
	/**
	 * This method is used to set Consent ID
	 */
	public void setConsentIdField(String consentId){
		_setConsentId = consentId;
	}
	
	/**
	 * This method is used to set Amount
	 */
	public void setAmount(String amount){
		_setAmount = amount;
	}
	
	/**
	 * This method is used to set Currency
	 */
	public void setCurrency(String currency){
		_setCurrency = currency;
	}
	
	
	/**
	 * This method is for setting body for REST request
	 */	
	public String genRequestBody(){
						_reqBody = "{"
								+ "\"Data\": {"
								+ "\"InstructedAmount\": { "
									+ "\"Amount\": \""+_setAmount+"\", "
									+ "\"Currency\": \""+_setCurrency+"\" "
									+ " }, "
								+ " \"Reference\": \""+_setReference+"\","
								+ " \"ConsentId\": \""+_setConsentId+"\""
							+ "}}";
						return _reqBody;
		}
	/**
	 * This method is for submitting the REST request 
	 */
	public void submit() {
		setURL(_baseURL);
		setRequestBody(genRequestBody());
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
	 * This method is for getting FundsAvailable from the request response
	 * 
	 */
	public String getFundsAvailable() {
		return getResponseNodeStringByPath("Data.FundsAvailable");
	}
	
	/**
	 * This method is for getting FundsConfirmationId from the request response
	 * 
	 */
	public String getFundsConfirmationId() {
		return getResponseNodeStringByPath("Data.FundsConfirmationId");
	}
	
	/**
	 * This method is for getting Amount from the request response
	 * 
	 */
	public String getAmount() {
		return getResponseNodeStringByPath("Data.InstructedAmount.Amount");
	}
	
	/**
	 * This method is for getting Reference from the request response
	 * 
	 */
	public String getReference() {
		return getResponseNodeStringByPath("Data.Reference");
	}
	
	/**
	 * This method is for getting Currency from the request response
	 * 
	 */
	public String getCurrency() {
		return getResponseNodeStringByPath("Data.InstructedAmount.Currency");
	}
}
