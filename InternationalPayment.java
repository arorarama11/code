package com.psd2.wrapper;

import com.psd2.core.RestAPIRequest;
import com.psd2.signature.SignatureUtility;
import com.psd2.utils.Misc;
import com.psd2.utils.PropertyUtils;

public class InternationalPayment extends RestAPIRequest {

	private String _baseurl;
	public String _reqBody="";
	public String _consentId;

	private String _idempotancyKey=PropertyUtils.getProperty("idempotency_key")+"."+Misc.generateString(4);
	
	/**
	 * This method is for constructing an empty International Payment with default settings
	 */
	private InternationalPayment(){
		setTestData();
	}
	
	public void setTestData(){
		_idempotancyKey=PropertyUtils.getProperty("idempotency_key")+"."+Misc.generateString(4);
		setDescription("Create International Payment Consent");
		setHeadersString("Content-Type:application/json, x-fapi-interaction-id:"+PropertyUtils.getProperty("inter_id")+", x-fapi-financial-id:"+PropertyUtils.getProperty("fin_id")+", Accept:application/json, x-idempotency-key:"+_idempotancyKey);
		addHeaderEntry("x-fapi-auth-date", PropertyUtils.getProperty("cust_last_log_time"));
		setMethod("POST");
		
		_instructionIdentification = "ACME412ACME412ACME412ACME412ACME412";
		_endToEndIdentification = "FRESCO.21302.GFX.20FRESCO.21302.GF";
		_instructionPriority = "Normal";
		_localInstrument = "UK.OBIE.SEPACreditTransfer";
		_purpose = "Test";
		_chargeBearer = "Shared";
		_currencyOfTransfer = "GBP";
		_amount = "300.12";
		_currency = "EUR";
		_unitCurrency = "EUR";
		_exchangeRate = "1.2";
		_rateType = "Agreed";
		_contractIdentification = "123identification";
		_crAccountSchemeName  = "UK.OBIE.PAN";
		_crAccountIdentification = "JO94CBJO0010000000000131000302";
		_crAccountName = "CrAccountTest";
		_crAccountSrIdentification = "1234567890123456789012345678901234";
		_crName = "CreditorTest";
		_crPostalAddressType = "Statement";
		_crDepartment = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ12345678";
		_crSubDepartment = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ12345678";
		_crStreetName = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ12345678";
		_crBuildingNumber = "ABCDEFGHIJ123456";
		_crPostCode = "ABCDEFGHIJ123456";
		_crTownName = "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";
		_crCountrySubDivision = "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";
		_crCountry = "US";
		_crAddressLine ="ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ\",\"12345678";
		_crAgentSchemeName = "BICFI";
		_crAgentIdentification = "BIC";
		_crAgentName = "CrAgentTest";
		_crAgentAddressType = "Business";
		_crAgentDepartment = "ABC";
		_crAgentSubDepartment = "DEF";
		_crAgentStreetName = "Test Street";
		_crAgentBuildingNumber = "Test Bldg Number";
		_crAgentPostCode = "Test Code";
		_crAgentTownName = "Test Town";
		_crAgentCountrySubDivision = "test";
		_crAgentCountry = "US";
		_crAgentAddressLine = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ\",\"12345678";;
		_drAccountSchemeName  = "UK.OBIE.IBAN";
		_drAccountIdentification = "IE85BOFI90120412345679";
		_drAccountName = "JESSICA";
		_drAccountSrIdentification = "54988";
		_remittanceUnstructered = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ12345678ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ12345678";
		_remittanceReference = "ABCDEFGHIJKLMNOPQRSTUVWXYZ12345678";
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
		_riskCountrySubDivision = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";                                                                              
		_riskAddressLine = "[\"ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ\",\"12345678\"]";
		removeDebtorAccount = false;
		removeRemittanceInformation = false;
		removeauthorisation= false;
		removeRisk = false;
		removeInitiation = false;
		removeCreditorAccount = false;
		removeCreditor = false;
		removeCreditorAgent = false;
		removeExchangeRateInformation = false;
		removeSCASupportData = false;
		removeCreditorAgentPostalAddress = false;
	}
		private static class InnerInternationalPayment{
			private static InternationalPayment ip;
			public static InternationalPayment createInstance(){
				if(ip==null){
					ip=new InternationalPayment();
				}
				return ip;
			}
		}
		
		public static InternationalPayment getInstance(){
			return InnerInternationalPayment.createInstance();
		}
	
		/**
		 * This method is for setting BaseURL for REST request
		 */	
		public void setBaseURL(String baseURL){
			_baseurl = baseURL;
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
		 * This method is for setting InstructionPriority for REST request
		 */	
		public void setInstructionPriority(String value){
			_instructionPriority = value;
		}
		/**
		 * This method is for setting LocalInstrument for REST request
		 */	
		public void setLocalInstrument(String value){
			_localInstrument = value;
		}
		
		/**
		 * This method is for setting Purpose for REST request
		 */	
		public void setPurpose(String value){
			_purpose = value;
		}
		/**
		 * This method is for setting ChargeBearer for REST request
		 */	
		public void setChargeBearer(String value){
			_chargeBearer = value;
		}
		/**
		 * This method is for setting CurrencyOfTransfer for REST request
		 */	
		public void setCurrencyOfTransfer(String value){
			_currencyOfTransfer = value;
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
		 * This method is for setting UnitCurrency for REST request
		 */	
		public void setUnitCurrency(String value){
			_unitCurrency = value;
		}
		/**
		 * This method is for setting ExchangeRate for REST request
		 */	
		public void setExchangeRate(String value){
			_exchangeRate = value;
		}
		/**
		 * This method is for setting RateType for REST request
		 */	
		public void setRateType(String value){
			_rateType = value;
		}
		/**
		 * This method is for setting ContractIdentification for REST request
		 */	
		public void setContractIdentification(String value){
			_contractIdentification = value;
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
		 * This method is for setting CrName for REST request
		 */	
		public void setCrName(String value){
			_crName = value;
		}
		/**
		 * This method is for setting CrAddressType for REST request
		 */	
		public void setCrAddressType(String value){
			_crPostalAddressType = value;
		}
		
		/**
		 * This method is for setting CrDepartment for REST request
		 */	
		public void setCrDepartment(String value){
			_crDepartment = value;
		}
		
		/**
		 * This method is for setting CrSubDepartment for REST request
		 */	
		public void setCrSubDepartment(String value){
			_crSubDepartment = value;
		}
		
		/**
		 * This method is for setting CrStreetName for REST request
		 */	
		public void setCrStreetName(String value){
			_crStreetName = value;
		}
		
		/**
		 * This method is for setting CrBuildingNumber for REST request
		 */	
		public void setCrBuildingNumber(String value){
			_crBuildingNumber = value;
		}
		
		/**
		 * This method is for setting CrPostCode for REST request
		 */	
		public void setCrPostCode(String value){
			_crPostCode = value;
		}
		
		/**
		 * This method is for setting CrTownName for REST request
		 */	
		public void setCrTownName(String value){
			_crTownName = value;
		}
		
		/**
		 * This method is for setting CrCountrySubDivision for REST request
		 */	
		public void setCrCountrySubDivision(String value){
			_crCountrySubDivision = value;
		}
		
		/**
		 * This method is for setting CrCountry for REST request
		 */	
		public void setCountry(String value){
			_crCountry = value;
		}
		
		/**
		 * This method is for setting CrAddressLine for REST request
		 */	
		public void setCrAddressLine(String value){
			_crAddressLine = value;
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
		 * This method is for setting Creditor Agent SchemeName for REST request
		 */	
		public void setCrAgentSchemeName(String value){
			_crAgentSchemeName = value;
		}
		
		/**
		 * This method is for setting Creditor Agent Identification for REST request
		 */	
		public void setCrAgentIdentification(String value){
			_crAgentIdentification = value;
		}
		
		/**
		 * This method is for setting Creditor Agent Name for REST request
		 */	
		public void setCrAgentName(String value){
			_crAgentName = value;
		}
		
		/**
		 * This method is for setting Creditor Agent AddressType for REST request
		 */	
		public void setCrAgentAddressType(String value){
			_crAgentAddressType = value;
		}
		
		/**
		 * This method is for setting CrAgentDepartment for REST request
		 */	
		public void setCrAgentDepartment(String value){
			_crAgentDepartment = value;
		}
		
		/**
		 * This method is for setting CrAgentSubDepartment for REST request
		 */	
		public void setCrAgentSubDepartment(String value){
			_crAgentSubDepartment = value;
		}
		
		/**
		 * This method is for setting CrAgentStreetName for REST request
		 */	
		public void setCrAgentStreetName(String value){
			_crAgentStreetName = value;
		}
		
		/**
		 * This method is for setting CrAgentBuildingNumber for REST request
		 */	
		public void setCrAgentBuildingNumber(String value){
			_crAgentBuildingNumber = value;
		}
		
		/**
		 * This method is for setting CrAgentPostCode for REST request
		 */	
		public void setCragentPostCode(String value){
			_crAgentPostCode = value;
		}
		
		/**
		 * This method is for setting CrAgentTownName for REST request
		 */	
		public void setCrAgentTownName(String value){
			_crAgentTownName = value;
		}
		
		/**
		 * This method is for setting CrAgentCountrySubDivision for REST request
		 */	
		public void setCrAgentCountrySubDivision(String value){
			_crAgentCountrySubDivision = value;
		}
		
		/**
		 * This method is for setting CrAgentCountry for REST request
		 */	
		public void setCrAgentCountry(String value){
			_crAgentCountry = value;
		}
		
		/**
		 * This method is for setting CrAgentAddressLine for REST request
		 */	
		public void setCrAgentAddressLine(String value){
			_crAgentAddressLine = value;
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
		 * This method is for remove Debtor Account for REST request
		 */
		public void removeDebtorAccount(){
			removeDebtorAccount = true;
		}
		
		/**
		 * This method is for remove Creditor for REST request
		 */
		public void removeCreditor(){
			removeCreditor = true;
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
		public void setAuthorisationType(String value){
			_authorisationType = value; 
		}
		
		/**
		 * This method is for setting CompletionDateTime for REST request
		 */	
		public void setCompletionDateTime(String value){
			_completionDateTime = value; 
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
		 * This method is for removing authorisation for REST request
		 */	
		public void removeauthorisation(){
			removeauthorisation = true;
		}
		
		/**
		 * This method is for removing creditor Agent for REST request
		 */	
		public void removeCreditorAgent(){
			removeCreditorAgent = true;
		}
		
		/**
		 * This method is for removing ExchangeRateInformation for REST request
		 */	
		public void removeExchangeRateInformation(){
			removeExchangeRateInformation = true;
		}

		/**
		 * This method is for removing SCASupportData for REST request
		 */	
		public void removeSCASupportData(){
			removeSCASupportData = true;
		}
		/**
		 * This method is for removing CreditorAgentPostalAddress for REST request
		 */	
		public void removeCreditorAgentPostalAddress(){
			removeCreditorAgentPostalAddress = true;
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
									+ "\"InstructionIdentification\": \""+_instructionIdentification+"\","
									+ "\"EndToEndIdentification\": \""+_endToEndIdentification+"\","
									+ "\"InstructionPriority\": \""+_instructionPriority+"\","
									+ "\"LocalInstrument\": \""+_localInstrument+"\","	
									+ "\"Purpose\": \""+_purpose+"\","
									+ "\"ChargeBearer\": \""+_chargeBearer+"\","
									+ "\"CurrencyOfTransfer\": \""+_currencyOfTransfer+"\","
									+ "\"InstructedAmount\": { "
										+ "\"Amount\": \""+_amount+"\","
										+ "\"Currency\": \""+_currency+"\""
									+ "}";
							if(!removeExchangeRateInformation){
								_reqBody = _reqBody + ",\"ExchangeRateInformation\": {"
										+ "\"UnitCurrency\": \""+_unitCurrency+"\","
										+ "\"RateType\": \""+_rateType+"\","
										+ "\"ExchangeRate\": \""+_exchangeRate+"\","
										+ "\"ContractIdentification\": \""+_contractIdentification+"\""
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
							if(!removeCreditor){
								_reqBody = _reqBody + ",\"Creditor\": {"
									+ "\"Name\": \""+_crName+"\","
									+ "\"PostalAddress\": { "
									+ "\"AddressType\": \""+_crPostalAddressType+"\","
									+ "\"Department\": \""+_crDepartment+"\","
									+ "\"SubDepartment\": \""+_crSubDepartment+"\","
									+ "\"StreetName\": \""+_crStreetName+"\","
									+ "\"BuildingNumber\": \""+_crBuildingNumber+"\","
									+ "\"PostCode\": \""+_crPostCode+"\","
									+ "\"TownName\": \""+_crTownName+"\","
									+ "\"CountrySubDivision\": \""+_crCountrySubDivision+"\","
									+ "\"Country\": \""+_crCountry+"\","
									+ "\"AddressLine\": [\""+_crAddressLine+"\"]"
									+ "}}";
							}
							if(!removeCreditorAgent){
								_reqBody = _reqBody + ",\"CreditorAgent\": {"
									+ "\"SchemeName\": \""+_crAgentSchemeName+"\","
									+ "\"Identification\": \""+_crAgentIdentification+"\","
									+ "\"Name\": \""+_crAgentName+"\"";
							if(!removeCreditorAgentPostalAddress){
								_reqBody = _reqBody +  ",\"PostalAddress\": { "
									+ "\"AddressType\": \""+_crAgentAddressType+"\","
									+ "\"Department\": \""+_crAgentDepartment+"\","
									+ "\"SubDepartment\": \""+_crAgentSubDepartment+"\","
									+ "\"StreetName\": \""+_crAgentStreetName+"\","
									+ "\"BuildingNumber\": \""+_crAgentBuildingNumber+"\","
									+ "\"PostCode\": \""+_crAgentPostCode+"\","
									+ "\"TownName\": \""+_crAgentTownName+"\","
									+ "\"CountrySubDivision\": \""+_crAgentCountrySubDivision+"\","
									+ "\"Country\": \""+_crAgentCountry+"\","
									+ "\"AddressLine\": [\""+_crAgentAddressLine+"\"]"
									+ "}}";
							}
							if(removeCreditorAgentPostalAddress)
							{
								_reqBody = _reqBody + "}";
							}}
							if(!removeDebtorAccount){
								_reqBody = _reqBody + ",\"DebtorAccount\": {"
										+ "\"SchemeName\": \""+_drAccountSchemeName+"\","
										+ "\"Identification\": \""+_drAccountIdentification+"\","
										+ "\"Name\": \""+_drAccountName+"\","
										+ "\"SecondaryIdentification\": \""+_drAccountSrIdentification+"\""
										+ "}";
							}
							if(!removeRemittanceInformation){
								_reqBody = _reqBody + ",\"RemittanceInformation\": {"
										+ "\"Unstructured\": \""+_remittanceUnstructered+"\","
										+ "\"Reference\": \""+_remittanceReference+"\""
										+ "}},";
							}
							if(removeRemittanceInformation)
							{
								_reqBody = _reqBody + "},";
							}
						}
							
							if(_baseurl.contains("consents")){
								setDescription("Create International Payment Consents");
								if(!removeauthorisation){
									_reqBody = _reqBody + "\"Authorisation\":{"
											+ "\"AuthorisationType\": \""+_authorisationType+"\","
											+ "\"CompletionDateTime\": \""+_completionDateTime+"\""
											+ "}";
									
								}
								if(!removeSCASupportData){
									_reqBody = _reqBody + ",\"SCASupportData\":{"
											+ "\"RequestedSCAExemptionType\": \""+_requestedSCAExemptionType+"\","
											+ "\"AppliedAuthenticationApproach\": \""+_appliedAuthenticationApproach+"\","
											+ "\"ReferencePaymentOrderId\": \""+_referencePaymentOrderId+"\""
											+ "}";
								}
							}else{
								setDescription("Create International Payments");
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
		 * This method is for getting ConsentId from the request response
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
			return getResponseNodeStringByPath("Data.InternationalPaymentId");
	}
		
	}
