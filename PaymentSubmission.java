package com.psd2.wrapper;

import com.psd2.core.RestAPIRequest;
import com.psd2.utils.Misc;
import com.psd2.utils.PropertyUtils;

public class PaymentSubmission extends RestAPIRequest{
	
	private String _baseURL;
	private String _reqBody="";
	private String _instructionIdentification;
	private String _endToEndIdentification;
	private String _amount;
	private String _currency;
	private String _crAgentSchemeName;
	private String _crAgentIdentification;
	private String _crAccountSchemeName;
	private String _crAccountIdentification;
	private String _crAccountName;
	private String _crAccountSrIdentification;
	private String _remittanceUnstructered;
	private String _remittanceReference;
	private String _riskPaymentContextCode;
	private String _riskMerchantCategoryCode;
	private String _riskMerchantCustomerIdentification;
	private String _riskAddressStreetName;
	private String _riskAddressBuildingNumber;
	private String _riskAddressPostCode;
	private String _riskAddressTown;
	private String _riskCountry;
	private String _riskCountrySubDivision;
	private String _riskAddressLine;
	private String _paymentId;

	private boolean removeRemittanceInformation = false;
	private boolean removeRisk = false;
	
	
	/**
	 * This method is for constructing an empty PaymentSubmission with default settings
	 */
	public PaymentSubmission(){
		_baseURL = "";
		setDescription("Create Payment Setup");
		setHeadersString("Content-Type:application/json, x-fapi-financial-id:"+PropertyUtils.getProperty("fin_id")+", Accept:application/json, x-idempotency-key:Idemp_ad_"+Misc.generateString(3));
		setMethod("POST");
		
		_instructionIdentification = "ABDDCF";
		_endToEndIdentification = "DEMO USER";
		_amount = "1234567891234.12345";
		_currency = "EUR";
		_crAgentSchemeName = "BICFI";
		_crAgentIdentification = "12345";
		_crAccountSchemeName  = "IBAN";
		_crAccountIdentification = "FR1420041010050500013M02606";
		_crAccountName = "Test user";
		_crAccountSrIdentification = "0002";
		_remittanceUnstructered = "Internal ops code 5120101";
		_remittanceReference = "FRESCO-101";
		_riskPaymentContextCode = "EcommerceGoods";
		_riskMerchantCategoryCode = "5967";
		_riskMerchantCustomerIdentification = "053598653254";
		_riskAddressStreetName = "AcaciaAvenue";
		_riskAddressBuildingNumber = "27";
		_riskAddressPostCode = "GU31 2ZZ";
		_riskAddressTown = "Sparsholt";
		_riskCountry = "GB";
		_riskCountrySubDivision = "[\"ABCDABCDABCDABCDAB\",\"DEFG\"]";
		_riskAddressLine = "[\"Flat 7\",\"Acacia Lodge\"]";
	}
	
	/**
	 * This method is for setting BaseURL for REST request
	 */	
	public void setBaseURL(String baseURL){
		_baseURL = baseURL;
	}
	
	/**
	 * This method is for setting Payment Id for REST request
	 */	
	public void setPaymentId(String value){
		_paymentId = value;
	}
	
	/**
	 * This method is for setting InstructionIdentification for REST request
	 */	
	public void setInstructionIdentification(String value){
		_instructionIdentification = value;
	}
	
	/**
	 * This method is for setting EndToEndIdentification for REST request
	 */	
	public void setEndToEndIdentification(String value){
		_endToEndIdentification = value;
	}
	
	/**
	 * This method is for setting Amount for REST request
	 */	
	public void setAmount(String value){
		_amount = value;
	}
	
	/**
	 * This method is for setting Currency for REST request
	 */	
	public void setCurrency(String value){
		_currency = value;
	}
	
	/**
	 * This method is for setting CrAgentSchemeName for REST request
	 */	
	public void setCrAgentSchemeName(String value){
		_crAgentSchemeName = value;
	}
	
	/**
	 * This method is for setting CrAgentSchemeName for REST request
	 */	
	public void setCrAgentIdentification(String value){
		_crAgentIdentification = value;
	}
	
	/**
	 * This method is for setting CrAccountSchemeName for REST request
	 */	
	public void setCrAccountSchemeName(String value){
		_crAccountSchemeName = value;
	}
	
	/**
	 * This method is for setting CrAccountIdentification for REST request
	 */	
	public void setCrAccountIdentification(String value){
		_crAccountIdentification = value;
	}
	
	/**
	 * This method is for setting CrAccountName for REST request
	 */	
	public void setCrAccountName(String value){
		_crAccountName = value;
	}
	
	/**
	 * This method is for setting CrAccountSrIdentification for REST request
	 */	
	public void setCrAccountSrIdentification(String value){
		_crAccountSrIdentification = value;
	}
	
	/**
	 * This method is for setting RemittanceUnstructered for REST request
	 */	
	public void setRemittanceUnstructered(String value){
		_remittanceUnstructered = value;
	}
	
	/**
	 * This method is for setting RemittanceReference for REST request
	 */	
	public void setRemittanceReference(String value){
		_remittanceReference = value;
	}
	
	/**
	 * This method is for setting RiskPaymentContextCode for REST request
	 */	
	public void setRiskPaymentContextCode(String value){
		_riskPaymentContextCode = value;
	}
	
	/**
	 * This method is for setting RiskMerchantCategoryCode for REST request
	 */	
	public void setRiskMerchantCategoryCode(String value){
		_riskMerchantCategoryCode = value;
	}
	
	/**
	 * This method is for setting RiskMerchantCustomerIdentification for REST request
	 */	
	public void setRiskMerchantCustomerIdentification(String value){
		_riskMerchantCustomerIdentification = value;
	}
	
	/**
	 * This method is for setting RiskAddressStreetName for REST request
	 */	
	public void setRiskAddressStreetName(String value){
		_riskAddressStreetName = value;
	}
	
	/**
	 * This method is for setting RiskAddressBuildingNumber for REST request
	 */	
	public void setRiskAddressBuildingNumber(String value){
		_riskAddressBuildingNumber = value;
	}
	
	/**
	 * This method is for setting RiskAddressPostCode for REST request
	 */	
	public void setRiskAddressPostCode(String value){
		_riskAddressPostCode = value;
	}
	
	/**
	 * This method is for setting RiskAddressTown for REST request
	 */	
	public void setRiskAddressTown(String value){
		_riskAddressTown = value;
	}
	
	/**
	 * This method is for setting RiskCountry for REST request
	 */	
	public void setRiskCountry(String value){
		_riskCountry = value;
	}
	
	/**
	 * This method is for removing RemittanceInformation for REST request
	 */	
	public void removeRemittanceInformation(){
		removeRemittanceInformation = true;
	}
	/**
	 * This method is for removing Risk for REST request
	 */	
	public void removeRisk(){
		removeRisk = true;
	}
	/**
	 * This method is for setting Country Sub Division for REST request
	 */	
	public void setRiskCountrySubDivision(String value){
		_riskCountrySubDivision = value;
	}
	/**
	 * This method is for setting Address Line for REST request
	 */	
	public void setRiskAddressLine(String value){
		_riskAddressLine = value;
	} 
	/**
	 * This method is for setting body for REST request
	 */	
	public String genRequestBody(){
						_reqBody = "{  \"Data\": {"
								+ "\"PaymentId\": \""+_paymentId+"\", "
								+ "\"Initiation\": { "
								+ "\"InstructionIdentification\": \""+_instructionIdentification+"\",  "
								+ "\"EndToEndIdentification\": \""+_endToEndIdentification+"\", "
								+ "\"InstructedAmount\": { "
									+ "\"Amount\": \""+_amount+"\","
									+ "\"Currency\": \""+_currency+"\""
								+ "},"
								+ "\"CreditorAgent\": {"
									+ "\"SchemeName\": \""+_crAgentSchemeName+"\","
									+ "\"Identification\": \""+_crAgentIdentification+"\""
								+ "},"
								+ "\"CreditorAccount\": {"
								+ "\"SchemeName\": \""+_crAccountSchemeName+"\","
								+ "\"Identification\": \""+_crAccountIdentification+"\","
								+ "\"Name\": \""+_crAccountName+"\","
								+ "\"SecondaryIdentification\": \""+_crAccountSrIdentification+"\""
								+ "}";
						if(!removeRemittanceInformation){
							_reqBody = _reqBody + ",\"RemittanceInformation\": {"
									+ "\"Unstructured\": \""+_remittanceUnstructered+"\","
									+ "\"Reference\": \""+_remittanceReference+"\""
									+ "}}}";
						}
						if(!removeRisk){
							_reqBody = _reqBody + ",\"Risk\": {"
									+ "\"PaymentContextCode\": \""+_riskPaymentContextCode+"\","
									+ "\"MerchantCategoryCode\": \""+_riskMerchantCategoryCode+"\","
									+ "\"MerchantCustomerIdentification\": \""+_riskMerchantCustomerIdentification+"\","
									+ "\"DeliveryAddress\": { \"AddressLine\":"+ _riskAddressLine+","
								+ "\"StreetName\": \""+_riskAddressStreetName+"\","
								+ "\"BuildingNumber\": \""+_riskAddressBuildingNumber+"\","
								+ "\"PostCode\": \""+_riskAddressPostCode+"\","
								+ "\"TownName\": \""+_riskAddressTown+"\","
								+ "\"CountrySubDivision\": "+ _riskCountrySubDivision+","
								+ "\"Country\": \""+_riskCountry+"\"}}}";
						}
								
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
	 * This method is for getting PaymentId from the request response
	 * 
	 */
	public String getPaymentId() {
			return getResponseNodeStringByPath("Data.PaymentId");
	}
	
	/**
	 * This method is for getting PaymentSubmissionId from the request response
	 * 
	 */
	public String getPaymentSubmissionId() {
			return getResponseNodeStringByPath("Data.PaymentSubmissionId");
	}
	
	/**
	 * This method is for getting Status from the request response
	 * 
	 */
	public String getStatus() {
			return getResponseNodeStringByPath("Data.Status");
	}
	
	/**
	 * This method is for getting CreationDateTime from the request response
	 * 
	 */
	public String getCreationDateTime() {
			return getResponseNodeStringByPath("Data.CreationDateTime");
	}
}

