package com.psd2.wrapper;

import com.psd2.core.RestAPIRequest;
import com.psd2.utils.PropertyUtils;

public class AccountSetup extends RestAPIRequest{
	
	private String _baseURL;
	private String _reqBody;
	private boolean _allPermissionFlag;
	private String _permissions;
	private String _setexpirationdate;
	private String _setTxnFromDate;
	private String _setTxnToDate;
	public boolean _setexpirationdateFlag;
	public boolean _setTxnFromDateFlag;
	public boolean _setTxnToDateFlag;

	/**
	 * This method is for constructing an empty AccountSetup with default settings
	 */
	public AccountSetup(){
		_baseURL = "";
		_allPermissionFlag = false;
		_setexpirationdateFlag = true;
		_setTxnFromDateFlag = true;
		_setTxnToDateFlag = true;
		_permissions = "";
		_setexpirationdate = PropertyUtils.getProperty("exp_date_time");
		_setTxnFromDate = PropertyUtils.getProperty("trans_frm_date_time");
		_setTxnToDate = PropertyUtils.getProperty("trans_to_date_time");
		setDescription("Create Account Setup");
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
	 * This method is used to set all permissions
	 */
	public void setAllPermission(){
		_allPermissionFlag = true;
	}
	
	/**
	 * This method is used to set expiration date and time
	 */
	public void setExpirationDateTime(String expireDate){
		_setexpirationdate = expireDate;
	}
	
	/**
	 * This method is used to set transaction from date and time
	 */
	public void setTxnFromDate(String txnfromDate){
		_setTxnFromDate = txnfromDate;
	}
	
	/**
	 * This method is used to set transaction to date and time
	 */
	public void setTxnToDate(String txntoDate){
		_setTxnToDate = txntoDate;
	}
	
	
	/**
	 * This method is used to set custom permission
	 * @param permissionNames (separated by ; for multiple permissions)
	 */
	public void setCustomPermissions(String permissionNames){
		int count = permissionNames.split(";").length;
		for(String per : permissionNames.split(";")){
			count = count -1;
			_permissions = _permissions + "\"" +per+ "\" ";
			if(count>0){
				_permissions = _permissions + ",";
			}
		}
	}
	
	public void createPermissions(){
		if(_allPermissionFlag){
			
			if(_baseURL.contains("boiroi")){
				_permissions="\"ReadBalances\",   "
							+ "\"ReadAccountsDetail\",  "
							+ "\"ReadAccountsBasic\",   "
							+ "\"ReadTransactionsDetail\",   "
							+ "\"ReadTransactionsBasic\",   "
							+ "\"ReadTransactionsCredits\",   "
							+ "\"ReadTransactionsDebits\"";
			}
			else{
				_permissions="\"ReadBalances\",   "
							+ "\"ReadAccountsDetail\",  "
							+ "\"ReadAccountsBasic\",   "
							+ "\"ReadBeneficiariesBasic\",   "
							+ "\"ReadBeneficiariesDetail\",  "
							+ "\"ReadDirectDebits\","
							+ "\"ReadProducts\",  "
							+ "\"ReadStandingOrdersBasic\",   "
							+ "\"ReadStandingOrdersDetail\",   "
							+ "\"ReadTransactionsDetail\",   "
							+ "\"ReadTransactionsBasic\",   "
							+ "\"ReadTransactionsCredits\",   "
							+ "\"ReadTransactionsDebits\",	"
							+ "\"ReadPartyPSU\", "
							+ "\"ReadScheduledPaymentsBasic\", "
							+ "\"ReadScheduledPaymentsDetail\", "
							+ "\"ReadStatementsBasic\", "
							+ "\"ReadStatementsDetail\"";
			}
		}/*else{
			_permissions = _permissions;
		}*/	
	}
	
	/**
	 * This method is for setting body for REST request
	 */	
	public String genRequestBody(){
						_reqBody = "{"
								+ "\"Data\": {"
								+ "\"Permissions\": [ "
									+ _permissions + "]";
						if(_setexpirationdateFlag){
							_reqBody = _reqBody + ",\"ExpirationDateTime\": \""+_setexpirationdate+"\"";
						}
						if(_setTxnFromDateFlag){
							_reqBody = _reqBody + ",\"TransactionFromDateTime\": \""+_setTxnFromDate+"\"";
						}
						if(_setTxnToDateFlag){
							_reqBody = _reqBody + ",\"TransactionToDateTime\": \""+_setTxnToDate+"\"";
						}
						_reqBody = _reqBody +  "  }, "
								+ " \"Risk\": {}"
							+ "}";
						return _reqBody;
		}
	/**
	 * This method is for submitting the REST request 
	 */
	public void submit() {
		setURL(_baseURL);
		createPermissions();
		setRequestBody(genRequestBody());
		super.submit();
	}
	
	/**
	 * This method is for getting ConsentId from the request response
	 * 
	 */
	public String getConsentId() {
		if (this.isResponseInJSON())
			return getResponseNodeStringByPath("Data.ConsentId");
		else
			return getResponseNodeStringByPath("\"Data\"");
	}	
	
	/**
	 * 
	 */
	public String getAccountRequestId() {
		if (this.isResponseInJSON())
			return getResponseNodeStringByPath("Data.AccountRequestId");
		else
			return getResponseNodeStringByPath("\"Data\"");
	}	
	
	
	
	/**
	 * This method is for getting ExpirationDateTime from the request response
	 * 
	 */
	public String getExpirationDateTime() {
		if (this.isResponseInJSON())
			return getResponseNodeStringByPath("Data.ExpirationDateTime");
		else
			return getResponseNodeStringByPath("\"Data\"");
	}	
	
	/**
	 * This method is for getting All Permissions from the request response
	 * 
	 */
	public String getAllPermissions() {
		if (this.isResponseInJSON())
			return getResponseNodeStringByPath("Data.Permissions");
		else
			return getResponseNodeStringByPath("\"Data\"");
	}
	
	/**
	 * This method is for getting ExpirationDateTime from the request response
	 * 
	 */
	public String getTransactionFromDateTime() {
		if (this.isResponseInJSON())
			return getResponseNodeStringByPath("Data.TransactionFromDateTime");
		else
			return getResponseNodeStringByPath("\"Data\"");
	}
	
	/**
	 * This method is for getting TransactionToDateTime from the request response
	 * 
	 */
	public String getTransactionToDateTime() {
		if (this.isResponseInJSON())
			return getResponseNodeStringByPath("Data.TransactionToDateTime");
		else
			return getResponseNodeStringByPath("\"Data\"");
	}
}
