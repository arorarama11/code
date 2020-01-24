package com.psd2.wrapper;

import com.psd2.core.RestAPIRequest;
import com.psd2.signature.SignatureUtility;
import com.psd2.utils.API_Constant;
import com.psd2.utils.Misc;
import com.psd2.utils.PropertyUtils;

public class FilePayments extends RestAPIRequest {

	private String _baseurl;
	public String _reqBody="";
	public String _fileType;
	public String _fileHash;
	public String _fileReference;
	public String _numberOfTransactions;
	public String _controlSum;
	public String _consentId;
	public boolean removeSupplementaryData;
	private String _idempotancyKey=PropertyUtils.getProperty("idempotency_key")+"."+Misc.generateString(4);
	/**
	 * This method is for constructing an empty File Payment with default settings
	 */
	private FilePayments(){
		setTestData();
	} 
	
	public void setTestData(){
		_idempotancyKey=PropertyUtils.getProperty("idempotency_key")+"."+Misc.generateString(4);
		setDescription("Create File Payment Consent");
		setHeadersString("Content-Type:application/json, x-fapi-interaction-id:"+PropertyUtils.getProperty("inter_id")+", x-fapi-financial-id:"+PropertyUtils.getProperty("fin_id")+", Accept:application/json, x-idempotency-key:"+_idempotancyKey);
		addHeaderEntry("x-fapi-auth-date", PropertyUtils.getProperty("cust_last_log_time"));
		setMethod("POST");
		
		_fileType = "UK.OBIE.pain.001.001.08";
		_fileHash = Misc.generateFileHash(API_Constant.xmlFilePath);
		_fileReference = "GB2OK238";
		_localInstrument = "UK.OBIE.Euro1";
		_numberOfTransactions = "2";
		_controlSum = "101.41";
		_requestedExecutionDateTime = "2020-01-31T00:00:00+00:00";
		_drAccountSchemeName  = "UK.OBIE.IBAN";
		_drAccountIdentification = "IE85BOFI90120412345679";
		_drAccountName = "JESSICA";
		_drAccountSrIdentification = "54988";
		_remittanceUnstructered = "Internal Ops Code 5120101";
		_remittanceReference = "ABCDEFGHIJKLMNOPQRSTUVWXYZ12345678";
		_authorisationType = "Any";
		_completionDateTime = "2020-11-30T06:59:36+00:00";
		_requestedSCAExemptionType = "BillPayment";
		_appliedAuthenticationApproach = "CA";
		_referencePaymentOrderId = "12233";
		removeSupplementaryData=false;
		removeDebtorAccount = false;
		removeRemittanceInformation = false;
		removeauthorisation= false;
		removeInitiation = false;
		removeSCASupportData = false;
	}
	
		private static class InnerFilePayments{
			private static FilePayments fp;
			public static FilePayments createInstance(){
				if(fp==null){
					fp=new FilePayments();
				}
				return fp;
			}
		}
		
		public static FilePayments getInstance(){
			return InnerFilePayments.createInstance();
		} 
	
		/**
		 * This method is for setting BaseURL for REST request
		 */	
		public void setBaseURL(String baseURL){
			_baseurl = baseURL;
		}
		
		/**
		 * This method is for setting FileType for REST request
		 */	
		public void setFileType(String value){
			_fileType = value;
		}
		
		/**
		 * This method is for getting FileType
		 */	
		public String getFileType(){
			return _fileType;
		}
		
		/**
		 * This method is for setting FileHash for REST request
		 */	
		public void setFileHash(String value){
			_fileHash = value;
		}
		
		/**
		 * This method is for setting FileReference for REST request
		 */	
		public void setFileReference(String value){
			_fileReference = value;
		}
		/**
		 * This method is for setting LocalInstrument for REST request
		 */	
		public void setLocalInstrument(String value){
			_localInstrument = value;
		}
		
		/**
		 * This method is for setting NumberOfTransactions for REST request
		 */	
		public void setNumberOfTransactions(String value){
			_numberOfTransactions = value;
		}
		/**
		 * This method is for setting ControlSum for REST request
		 */	
		public void setControlSum(String value){
			_controlSum = value;
		}
		/**
		 * This method is for setting RequestedExecutionDateTime for REST request
		 */	
		public void setRequestedExecutionDateTime(String value){
			_requestedExecutionDateTime = value;
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
		 * This method is for remove Supplementary Data for REST request
		 */
		public void removeSupplementaryData(){
			removeSupplementaryData = true;
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
		 * This method is for removing RemittanceInformation for REST request
		 */	
		public void removeRemittanceInformation(){
			removeRemittanceInformation = true;
		}
		
		/**
		 * This method is for removing authorisation for REST request
		 */	
		public void removeauthorisation(){
			removeauthorisation = true;
		}

		/**
		 * This method is for removing SCASupportData for REST request
		 */	
		public void removeSCASupportData(){
			removeSCASupportData = true;
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
									+ "\"FileType\": \""+_fileType+"\","
									+ "\"FileHash\": \""+_fileHash+"\","
									+ "\"FileReference\": \""+_fileReference+"\","
									+ "\"NumberOfTransactions\": \""+_numberOfTransactions+"\","	
									+ "\"ControlSum\": \""+_controlSum+"\","
									+ "\"RequestedExecutionDateTime\": \""+_requestedExecutionDateTime+"\","
									+ "\"LocalInstrument\": \""+_localInstrument+"\"";
							if(!removeDebtorAccount){
								_reqBody = _reqBody + ",\"DebtorAccount\": {"
										+ "\"SchemeName\": \""+_drAccountSchemeName+"\","
										+ "\"Identification\": \""+_drAccountIdentification+"\","
										+ "\"Name\": \""+_drAccountName+"\","
										+ "\"SecondaryIdentification\": \""+_drAccountSrIdentification+"\""
										+ "}";
							}
							if(!removeSupplementaryData){
								_reqBody = _reqBody + ",\"SupplementaryData\": {}";
							}
							if(!removeRemittanceInformation){
								_reqBody = _reqBody + ",\"RemittanceInformation\": {"
										+ "\"Unstructured\": \""+_remittanceUnstructered+"\","
										+ "\"Reference\": \""+_remittanceReference+"\""
										+ "}}";
							}
							if(removeRemittanceInformation)
							{
								_reqBody = _reqBody + "}";
							}
						}
							if(_baseurl.contains("consents")){
								setDescription("Create File Payment Consents");
								if(!removeauthorisation){
									_reqBody = _reqBody + ",\"Authorisation\":{"
											+ "\"AuthorisationType\": \""+_authorisationType+"\","
											+ "\"CompletionDateTime\": \""+_completionDateTime+"\""
											+ "}";
									
								}
								if(!removeSCASupportData){
									_reqBody = _reqBody + ",\"SCASupportData\":{"
											+ "\"RequestedSCAExemptionType\": \""+_requestedSCAExemptionType+"\","
											+ "\"AppliedAuthenticationApproach\": \""+_appliedAuthenticationApproach+"\","
											+ "\"ReferencePaymentOrderId\": \""+_referencePaymentOrderId+"\""
											+ "}}}";
								}
								if(removeSCASupportData){
									_reqBody = _reqBody + "}}";
								}
							}else{
								setDescription("Create File Payments");
								_reqBody = _reqBody + ",\"ConsentId\": \""+_consentId+"\"}}";
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
			return getResponseNodeStringByPath("Data.FilePaymentId");
	}
}