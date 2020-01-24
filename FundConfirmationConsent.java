package com.psd2.wrapper;

import com.psd2.core.RestAPIRequest;
import com.psd2.utils.PropertyUtils;

public class FundConfirmationConsent extends RestAPIRequest{
	
	private String _baseURL;
	private String _reqBody;
	private String _setexpirationdate;
	private String _setschemeName;
	private String _setidentification;
	private String _setname;
	private String _setsridentification;
	private boolean _firstField;

	/**
	 * This method is for constructing an empty Fund Confirmation Consent with default settings
	 */
	public FundConfirmationConsent(){
		_baseURL = "";
		_setexpirationdate = PropertyUtils.getProperty("exp_date_time");
		_setschemeName = "UK.OBIE.IBAN";
		_setidentification = "IE85BOFI90120412345679";
		_setname = "JESSICA";
		_setsridentification = "54988";
		_firstField = true;
		setDescription("Create Fund Confirmation Consent");
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
	 * This method is used to set expiration date and time
	 */
	public void setExpirationDateTime(String expireDate){
		_setexpirationdate = expireDate;
	}
	
	/**
	 * This method is used to set schemeName
	 */
	public void setSchemeName(String schemeName){
		_setschemeName = schemeName;
	}
	
	/**
	 * This method is used to set Identification
	 */
	public void setIdentification(String identification){
		_setidentification = identification;
	}
	
	/**
	 * This method is used to set Name
	 */
	public void setName(String name){
		_setname = name;
	}
	
	/**
	 * This method is used to set Identification
	 */
	public void setSecondaryIdentification(String srIdentification){
		_setsridentification = srIdentification;
	}
	
	/**
	 * This method is for setting body for REST request
	 */	
	public String genRequestBody(){
						_reqBody = "{"
								+ "\"Data\": {"
								+ "\"DebtorAccount\": { ";
						if(!_setschemeName.equalsIgnoreCase("RemoveField")){
							if(_firstField){
								_reqBody = _reqBody + "\"SchemeName\": \""+_setschemeName+"\""; 
								_firstField = false;
							}
							else{
								_reqBody = _reqBody + ",\"SchemeName\": \""+_setschemeName+"\""; 
							}
						}
						if(!_setidentification.equalsIgnoreCase("RemoveField")){
							if(_firstField){
								_reqBody = _reqBody + "\"Identification\": \""+_setidentification+"\""; 
								_firstField = false;
							}
							else{
								_reqBody = _reqBody + ",\"Identification\": \""+_setidentification+"\""; 
							}
						}
						if(!_setname.equalsIgnoreCase("RemoveField")){
							if(_firstField){
								_reqBody = _reqBody + "\"Name\": \""+_setname+"\""; 
								_firstField = false;
							}
							else{
								_reqBody = _reqBody + ",\"Name\": \""+_setname+"\""; 
							}
						}			
						if(!_setsridentification.equalsIgnoreCase("RemoveField")){
							if(_firstField){
								_reqBody = _reqBody + "\"SecondaryIdentification\": \""+_setsridentification+"\""; 
								_firstField = false;
							}
							else{
								_reqBody = _reqBody + ",\"SecondaryIdentification\": \""+_setsridentification+"\""; 
							}
						}
						_reqBody = _reqBody + "  }";
						if(!_setexpirationdate.equalsIgnoreCase("RemoveField")){
									_reqBody = _reqBody + ",\"ExpirationDateTime\": \""+_setexpirationdate+"\"}"; 
						}
						_reqBody = _reqBody + "}";
						_firstField = true;
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
