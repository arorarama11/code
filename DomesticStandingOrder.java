package com.psd2.wrapper;

import com.psd2.core.RestAPIRequest;
import com.psd2.signature.SignatureUtility;
import com.psd2.utils.Misc;
import com.psd2.utils.PropertyUtils;

public class DomesticStandingOrder extends RestAPIRequest {

	public String _baseurl;
	public String _reqBody="";
	public String _frequency;
	public String _reference;
	public String _numberOfPayments;
	public String _firstPaymentDateTime;
	public String _recurringPaymentDateTime;
	public String _finalPaymentDateTime;
	public String _firstPaymentAmount;
	public String _firstPaymentCurrency;
	public String _recurringPaymentAmount;
	public String _recurringPaymentCurrency;
	public String _finalPaymentAmount;
	public String _finalPaymentCurrency;
	public String _consentId;
	public boolean addFinalPaymentDateTime;
	private boolean removeFirstPaymentAmount;
	private boolean removeRecurringPaymentAmount;
	private boolean removeFinalPaymentAmount;
	private boolean addNumberOfPayments;
	private String _idempotancyKey=PropertyUtils.getProperty("idempotency_key")+"."+Misc.generateString(4);
	/**
	 * This method is for constructing an empty PaymentSetup with default settings
	 */  
	private DomesticStandingOrder(){
		setTestData();
		}
	
	public void setTestData(){
		_idempotancyKey=PropertyUtils.getProperty("idempotency_key")+"."+Misc.generateString(4);
		setDescription("Create Domestic Standing Orders");
		setHeadersString("Content-Type:application/json, x-fapi-interaction-id:"+PropertyUtils.getProperty("inter_id")+", x-fapi-financial-id:"+PropertyUtils.getProperty("fin_id")+", Accept:application/json, x-idempotency-key:"+_idempotancyKey);
		addHeaderEntry("x-fapi-auth-date", PropertyUtils.getProperty("cust_last_log_time"));
		setMethod("POST");
		_permission = "Create";
		_frequency = "EvryDay";
		_reference = "test reference";
		_numberOfPayments = "2";
		_firstPaymentDateTime = "2020-02-25T06:06:06+00:00";
		_recurringPaymentDateTime = "2020-02-26T06:06:06+00:00";
		_finalPaymentDateTime = "2020-02-27T06:06:06+00:00";
		_firstPaymentAmount = "300.12";
		_firstPaymentCurrency = "EUR";
		_recurringPaymentAmount = "311.12";
		_recurringPaymentCurrency = "GBP";
		_finalPaymentAmount = "322.12";
		_finalPaymentCurrency = "EUR";
		_crAccountSchemeName  = "UK.OBIE.PAN";
		_crAccountIdentification = "JO94CBJO0010000000000131000302";
		_crAccountName = "Test";
		_crAccountSrIdentification = "1234567890123456789012345678901234";
		_drAccountSchemeName  = "UK.OBIE.IBAN";
		_drAccountIdentification = "IE85BOFI90120412345679";
		_drAccountName = "JESSICA";
		_drAccountSrIdentification = "54988";
		_authorisationType = "Any";
		_completionDateTime = "2019-11-30T06:59:36+00:00";
		_requestedSCAExemptionType = "Parking";
		_appliedAuthenticationApproach = "CA";
		_referencePaymentOrderId = "12233";
		_riskPaymentContextCode = "PartyToParty";
		_riskMerchantCategoryCode = "0123";
		_riskMerchantCustomerIdentification = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ12345678";
		_riskAddressStreetName = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ12345678";
		_riskAddressBuildingNumber = "ABCDEF1234567890";
		_riskAddressPostCode = "ABCDEF 123456789";
		_riskAddressTown = "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";
		_riskCountry = "GB";
		_riskCountrySubDivision = "test";                                                                              
		_riskAddressLine = "[\"ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ\",\"12345678\"]";
		addDebtorAccount = false;
		addFinalPaymentDateTime = true;
		removeFirstPaymentAmount = false;
		removeRecurringPaymentAmount = false;
		removeFinalPaymentAmount = false;
		removeauthorisation = false;
		removeRisk = false;
		removeCreditorAccount = false;
		removeInitiation = false;
		removeSCASupportData = false;
		addNumberOfPayments = false;
	}
		
	private static class InnerDomesticStandingOrder{
		private static DomesticStandingOrder dso;
		public static DomesticStandingOrder createInstance(){
			if(dso==null){
				dso=new DomesticStandingOrder();
			}
			return dso;
		}
	}
	
	public static DomesticStandingOrder getInstance(){
		return InnerDomesticStandingOrder.createInstance();
	}	
		
		/**
		 * This method is for setting BaseURL for REST request
		 */	
		public void setBaseURL(String baseURL){
			_baseurl = baseURL;
		}
		
		/**
		 * This method is for setting Permission for REST request
		 */	
		public void setPermission(String permission){
			_permission = permission;
		}
		
		/**
		 * This method is for setting Frequency for REST request
		 */	
		public void setFrequency(String frequency){
			_frequency = frequency;
		}
		
		/**
		 * This method is for setting Reference for REST request
		 */	
		public void setReference(String reference){
			_reference = reference;
		}
		
		/**
		 * This method is for setting NumberOfPayments for REST request
		 */	
		public void setNumberOfPayments(String numOfPayments){
			_numberOfPayments = numOfPayments;
		}
		
		/**
		 * This method is for setting FirstPaymentDateTime for REST request
		 */	
		public void setFirstPaymentDateTime(String firstPaymentDateTime){
			_firstPaymentDateTime = firstPaymentDateTime;
		}
		
		/**
		 * This method is for setting RecurringPaymentDateTime for REST request
		 */	
		public void setRecurringPaymentDateTime(String recurringPaymentDateTime){
			_recurringPaymentDateTime = recurringPaymentDateTime;
		}
		
		/**
		 * This method is for setting FinalPaymentDateTime for REST request
		 */	
		public void setFinalPaymentDateTime(String finalPaymentDateTime){
			_finalPaymentDateTime = finalPaymentDateTime;
		}
		
		/**
		 * This method is for setting FirstPaymentAmount for REST request
		 */	
		public void setFirstPaymentAmount(String firstPaymentAmount){
			_firstPaymentAmount = firstPaymentAmount;
		}
		
		/**
		 * This method is for setting FirstPaymentCurrency for REST request
		 */	
		public void setFirstPaymentCurrency(String firstPaymentCurrency){
			_firstPaymentCurrency = firstPaymentCurrency;
		}
		
		/**
		 * This method is for setting RecurringPaymentAmount for REST request
		 */	
		public void setRecurringPaymentAmount(String recurringPaymentAmount){
			_recurringPaymentAmount = recurringPaymentAmount;
		}
		
		/**
		 * This method is for setting RecurringPaymentCurrency for REST request
		 */	
		public void setRecurringPaymentCurrency(String recurringPaymentCurrency){
			_recurringPaymentCurrency = recurringPaymentCurrency;
		}
		
		/**
		 * This method is for setting FinalPaymentAmount for REST request
		 */	
		public void setFinalPaymentAmount(String finalPaymentAmount){
			_finalPaymentAmount = finalPaymentAmount;
		}
		
		/**
		 * This method is for setting FinalPaymentCurrency for REST request
		 */	
		public void setFinalPaymentCurrency(String finalPaymentCurrency){
			_finalPaymentCurrency = finalPaymentCurrency;
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
		 * This method is for setting DrAccountSchemeName for REST request
		 */	
		public void setDrAccountSchemeName(String value){
			_drAccountSchemeName = value;
		}
		
		/**
		 * This method is for setting DrAccountIdentification for REST request
		 */	
		public void setDrAccountIdentification(String value){
			_drAccountIdentification = value;
		}
		
		/**
		 * This method is for setting DrAccountName for REST request
		 */	
		public void setDrAccountName(String value){
			_drAccountName = value;
		}
		
		/**
		 * This method is for setting DrAccountSrIdentification for REST request
		 */	
		public void setDrAccountSrIdentification(String value){
			_drAccountSrIdentification = value;
		}
		
		/**
		 * This method is for setting RiskAddressPaymentContextCode for REST request
		 */	
		public void setRiskPaymentContextCode(String value){
			_riskPaymentContextCode = value;
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
		 * This method is for setting RiskAddressMerchantCategoryCode for REST request
		 */	
		public void setRiskAddressMerchantCategoryCode(String value){
			_riskMerchantCategoryCode = value;
		}
		
		/**
		 * This method is for setting RiskAddressMerchantCustomerIdentification for REST request
		 */	
		public void setRiskAddressMerchantCustomerIdentification(String value){
			_riskMerchantCustomerIdentification = value;
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
		 * This method is for remove Initiation Block for REST request
		 */
		public void removeInitiation(){
			removeInitiation = true;
		}
		
		/**
		 * This method is for adding NumberOfPayments field for REST request
		 */
		public void addNumberOfPayments(){
			addNumberOfPayments = true;
		}
		
		/**
		 * This method is for adding NumberOfPayments field for REST request
		 */
		public void removeFinalPaymentDateTime(){
			addFinalPaymentDateTime = false;
		}
		
		
		/**
		 * This method is for remove Debtor Account for REST request
		 */
		public void removeDebtorAccount(){
			addDebtorAccount = true;
		}
		
		/**
		 * This method is for setting Creditor Account for REST request
		 */
		public void removeCreditorAccount(){
			 removeCreditorAccount = true;
		}
		 
		/**
		 * This method is for setting Authorisation for REST request
		 */	
		public void setAuthorisation(String value){
			_authorisationType = value; 
		}
		
		/**
		 * This method is for setting CompletionDateTime for REST request
		 */	
		public void setCompletionDateTime(String value){
			_completionDateTime = value; 
		}
		
		/**
		 * This method is for removing FirstPaymentAmount for REST request
		 */	
		public void removeFirstPaymentAmount(){
			removeFirstPaymentAmount = true;
		}
		
		/**
		 * This method is for removing RecurringPaymentAmount for REST request
		 */	
		public void removeRecurringPaymentAmount(){
			removeRecurringPaymentAmount = true;
		}
		
		/**
		 * This method is for removing FinalPaymentAmount for REST request
		 */	
		public void removeFinalPaymentAmount(){
			removeFinalPaymentAmount = true;
		}
		
		/**
		 * This method is for removing Risk for REST request
		 */	
		public void removeRisk(){
			removeRisk = true;
		}
		/**
		 * This method is for removing authorisation for REST request
		 */	
		public void removeauthorisation(){
			removeauthorisation = true;
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
		 * This method is for setting RequestedSCAExemptionType for REST request
		 */	
		public void setRequestedSCAExemptionType(String value){
			_requestedSCAExemptionType = value;
		} 
		/**
		 * This method is for setting AppliedAuthenticationApproach Line for REST request
		 */	
		public void setAppliedAuthenticationApproach(String value){
			_appliedAuthenticationApproach = value;
		} 
		/**
		 * This method is for setting ReferencePaymentOrderId for REST request
		 */	
		public void setReferencePaymentOrderId(String value){
			_referencePaymentOrderId = value;
		} 
		/**
		 * This method is for setting Consent Id for REST request
		 */	
		public void setConsentId(String value){
			_consentId = value;
		}
		
		/**
		 * This method is for setting body for REST request
		 */	
		public String genRequestBody(){
							_reqBody = "{  \"Data\": {";
					if(!removeInitiation){
								_reqBody = _reqBody + "\"Initiation\": { "
									+ "\"Frequency\": \""+_frequency+"\","
									+ "\"Reference\": \""+_reference+"\", "
									+ "\"FirstPaymentDateTime\": \""+_firstPaymentDateTime+"\","
									+ "\"RecurringPaymentDateTime\": \""+_recurringPaymentDateTime+"\",";
									if(addNumberOfPayments){
										_reqBody = _reqBody + "\"NumberOfPayments\": \""+_numberOfPayments+"\"";
									}
									if (!addNumberOfPayments){
										if(addFinalPaymentDateTime)
											_reqBody = _reqBody + "\"FinalPaymentDateTime\": \""+_finalPaymentDateTime+"\"";
									}
									if(!addNumberOfPayments && !addFinalPaymentDateTime){
										_reqBody = _reqBody.replace("\"RecurringPaymentDateTime\": \""+_recurringPaymentDateTime+"\",", "\"RecurringPaymentDateTime\": \""+_recurringPaymentDateTime+"\"");
									}
									if(!removeFirstPaymentAmount){
										_reqBody = _reqBody + ",\"FirstPaymentAmount\": { "
												+ "\"Amount\": \""+_firstPaymentAmount+"\","
												+ "\"Currency\": \""+_firstPaymentCurrency+"\""
											+ "}";
									}
									if(!removeRecurringPaymentAmount){
										_reqBody = _reqBody + ",\"RecurringPaymentAmount\": { "
												+ "\"Amount\": \""+_recurringPaymentAmount+"\","
												+ "\"Currency\": \""+_recurringPaymentCurrency+"\""
												+ "}";
									}
									if(!removeFinalPaymentAmount){
										_reqBody = _reqBody + ",\"FinalPaymentAmount\": { "
												+ "\"Amount\": \""+_finalPaymentAmount+"\","
												+ "\"Currency\": \""+_finalPaymentCurrency+"\""
												+ "}";
									}
							if(!removeCreditorAccount){
								_reqBody = _reqBody + ",\"CreditorAccount\": {"
									+ "\"SchemeName\": \""+_crAccountSchemeName+"\","
									+ "\"Identification\": \""+_crAccountIdentification+"\","
									+ "\"Name\": \""+_crAccountName+"\","
									+ "\"SecondaryIdentification\": \""+_crAccountSrIdentification+"\""
									+ "}";
							}
							if(!addDebtorAccount){
								_reqBody = _reqBody + ",\"DebtorAccount\": {"
										+ "\"SchemeName\": \""+_drAccountSchemeName+"\","
										+ "\"Identification\": \""+_drAccountIdentification+"\","
										+ "\"Name\": \""+_drAccountName+"\","
										+ "\"SecondaryIdentification\": \""+_drAccountSrIdentification+"\""
										+ "}},";
							}
							if(addDebtorAccount)
							{
								_reqBody = _reqBody + "},";
							}
					}
							if(_baseurl.contains("consents")){
								setDescription("Create Domestic Standing Orders Consents");
								_reqBody = _reqBody + "\"Permission\": \""+_permission+"\"";
								if(!removeauthorisation){
									_reqBody = _reqBody + ",\"Authorisation\":{"
											+ "\"AuthorisationType\": \""+_authorisationType+"\","
											+ "\"CompletionDateTime\": \""+_completionDateTime+"\""
											+ "}";
								}if(!removeSCASupportData){
									_reqBody = _reqBody + ",\"SCASupportData\":{"
											+ "\"RequestedSCAExemptionType\": \""+_requestedSCAExemptionType+"\","
											+ "\"AppliedAuthenticationApproach\": \""+_appliedAuthenticationApproach+"\","
											+ "\"ReferencePaymentOrderId\": \""+_referencePaymentOrderId+"\""
											+ "}";
								}
							}else{
								setDescription("Create Domestic Standing Orders");
								_reqBody = _reqBody + "\"ConsentId\": \""+_consentId+"\"  ";
							}
							if(!removeRisk){
								_reqBody = _reqBody + "},\"Risk\": {"
									+ "\"PaymentContextCode\": \""+_riskPaymentContextCode+"\","
									+ "\"MerchantCategoryCode\": \""+_riskMerchantCategoryCode+"\","
									+ "\"MerchantCustomerIdentification\": \""+_riskMerchantCustomerIdentification+"\","
									+ "\"DeliveryAddress\": {"
									+ "\"AddressLine\":"+ _riskAddressLine+","					
									+ "\"StreetName\": \""+_riskAddressStreetName+"\","
									+ "\"BuildingNumber\": \""+_riskAddressBuildingNumber+"\","
									+ "\"PostCode\": \""+_riskAddressPostCode+"\","
									+ "\"TownName\": \""+_riskAddressTown+"\","
									+ "\"CountrySubDivision\":[ \""+_riskCountrySubDivision+"\"],"
									+ "\"Country\": \""+_riskCountry+"\"}}}";
							}
							if(removeRisk)
							{
								_reqBody = _reqBody + "}}";
							}
							return _reqBody;
			}
		/**
		 * This method is for submitting the REST request 
		 */
		public void submit() {
			try{
				_reqBody=genRequestBody();
				setURL(_baseurl);
				if(getMethod().equals("POST")){
					addHeaderEntry("x-jws-signature",SignatureUtility.generateSignature(_reqBody));
				}
				setRequestBody(_reqBody);
				super.submit();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		/**
		 * This method is for submitting the REST request 
		 * @throws Exception 
		 */
		public void submit(String requestBody) throws Exception {
			setURL(_baseurl);
			if(getMethod().equals("POST")){
				addHeaderEntry("x-jws-signature",SignatureUtility.generateSignature(requestBody));
			}
			setRequestBody(requestBody);
			super.submit();
		}
		
		/**
		 * This method is for getting PaymentId from the request response
		 * 
		 */
		public String getConsentId() {
				return getResponseNodeStringByPath("Data.ConsentId");
		}	
		
		/**
		 * This method is for getting Payment Id from the request response
		 * 
		 */
		public String getPaymentId() {
			return getResponseNodeStringByPath("Data.DomesticStandingOrderId");
	}
	
	}
