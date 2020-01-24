package com.psd2.wrapper;

import com.psd2.core.RestAPIRequest;
import com.psd2.signature.SignatureUtility;
import com.psd2.utils.API_Constant;
import com.psd2.utils.PropertyUtils;

public class UploadFile extends RestAPIRequest {

	private String _baseurl;
	public String _fileType;
	private String _idempotencyKey;
	
	/**
	 * This method is for constructing an empty File Payment with default settings
	 */
	private UploadFile(){
		setTestData();
	}
	public void setTestData(){
		setDescription("Upload File");
		setHeadersString("x-fapi-interaction-id:"+PropertyUtils.getProperty("inter_id")+", x-fapi-financial-id:"+PropertyUtils.getProperty("fin_id")+", Accept:application/json");
		addHeaderEntry("x-fapi-auth-date", PropertyUtils.getProperty("cust_last_log_time"));
		setMethod("POST");
	}
		private static class InnerUploadFile{
			private static UploadFile uf;
			public static UploadFile createInstance(){
				if(uf==null){
					uf=new UploadFile();
				}
				return uf;
			}
		}
		
		public static UploadFile getInstance(){
			return InnerUploadFile.createInstance();
		}
	
		/**
		 * This method is to set Idempotency Key for REST request
		 */	
		public void setIdempotencyKey(String idempotencyKey){
			_idempotencyKey=idempotencyKey;
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
		public void setFileType(String filePath){
			_fileType= filePath;
		}
		
		/**
		 * This method is for upload file type the REST request 
		 */
		public void submit() {
			try{
				setURL(_baseurl);
				if(_fileType.equalsIgnoreCase("UK.OBIE.pain.001.001.08")){
					setFileUploadPath(API_Constant.xmlFilePath);
					setFileUploadType("application/xml");
					addHeaderEntry("x-jws-signature",SignatureUtility.generateFileSignature(API_Constant.xmlFilePath));
				}
				if(_fileType.equalsIgnoreCase("UK.OBIE.PaymentInitiation.3.0")){
					setFileUploadPath(API_Constant.jsonFilePath);
					setFileUploadType("application/json");
					addHeaderEntry("x-jws-signature",SignatureUtility.generateFileSignature(API_Constant.jsonFilePath));
				}
				addHeaderEntry("x-idempotency-key", _idempotencyKey);
				addHeaderEntry("Accept", "application/json");
				super.submit();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
}