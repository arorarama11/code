package com.psd2.utils;

public class API_Constant {
	
	public static final boolean SECURITY_CONFORMANCE_ENABLED = false;
	public static final boolean SECURITY_CONFORMANCE_SKIP_SCAN = true;
	
	public String baseurl = PropertyUtils.getProperty("base_url")+PropertyUtils.getProperty("brand_id")+"/api/open-banking/"+PropertyUtils.getProperty("cma_ver");
	public String cc_endpoint = PropertyUtils.getProperty("base_url")+"oauth/as/token.oauth2";
	public String as_endpoint = baseurl+"/aisp/account-access-consents";
	public String ps_endpoint = baseurl+"/payments";
	public String dpc_endpoint = baseurl+"/pisp/domestic-payment-consents";
	public String dps_endpoint = baseurl+"/pisp/domestic-payments";
	public String dspConsent_endpoint = baseurl+"/pisp/domestic-scheduled-payment-consents";
	public String dsp_endpoint = baseurl+"/pisp/domestic-scheduled-payments";
	public String dsoConsent_endpoint = baseurl+"/pisp/domestic-standing-order-consents";
	public String dsoSubmission_endpoint = baseurl+"/pisp/domestic-standing-orders";
	public String iPaymentConsent_endpoint = baseurl+"/pisp/international-payment-consents";
	public String iPaymentSubmission_endpoint = baseurl+"/pisp/international-payments";
	public String iScheduledPaymentConsent_endpoint = baseurl+"/pisp/international-scheduled-payment-consents";
	public String iScheduledPaymentSubmission_endpoint = baseurl+"/pisp/international-scheduled-payments";
	public String fPaymentConsent_endpoint = baseurl+"/pisp/file-payment-consents";
	public String fPaymentSubmission_endpoint = baseurl+"/pisp/file-payments";
	
	public String ro_endpoint = PropertyUtils.getProperty("req_obj_url")+PropertyUtils.getProperty("sign_key");
	public String at_endpoint = PropertyUtils.getProperty("base_url")+"oauth/as/token.oauth2";
	public String account_endpoint = baseurl+"/aisp/accounts/";
	public String rt_endpoint = PropertyUtils.getProperty("base_url")+"oauth/as/token.oauth2";
	public String psub_endpoint = PropertyUtils.getProperty("base_url")+PropertyUtils.getProperty("brand_id")+"/api/open-banking/"+PropertyUtils.getProperty("back_cma_ver")+"/payment-submissions";

	public String fc_endpoint = baseurl+"/cbpii/funds-confirmation-consents";
	public String fcc_endpoint = baseurl+"/cbpii/funds-confirmations";

	public String aispconsent_URL = PropertyUtils.getProperty("auth_servr_url")+"oauth/as/"+PropertyUtils.getProperty("account_type")+"/authorization.oauth2?client_id="+PropertyUtils.getProperty("client_id")+"&response_type=code id_token&scope=openid accounts&state="+PropertyUtils.getProperty("state")+"&nonce="+PropertyUtils.getProperty("nonce")+"&request=#token_RequestGeneration#&redirect_uri="+PropertyUtils.getProperty("redirect_url");
	public String pispconsent_URL = PropertyUtils.getProperty("auth_servr_url")+"oauth/as/"+PropertyUtils.getProperty("account_type")+"/authorization.oauth2?client_id="+PropertyUtils.getProperty("client_id")+"&response_type=code id_token&scope=openid payments&state="+PropertyUtils.getProperty("state")+"&nonce="+PropertyUtils.getProperty("nonce")+"&request=#token_RequestGeneration#&redirect_uri="+PropertyUtils.getProperty("redirect_url");
	public String cispconsent_URL = PropertyUtils.getProperty("auth_servr_url")+"oauth/as/"+PropertyUtils.getProperty("account_type")+"/authorization.oauth2?client_id="+PropertyUtils.getProperty("client_id")+"&response_type=code id_token&scope=openid fundsconfirmations&state="+PropertyUtils.getProperty("state")+"&nonce="+PropertyUtils.getProperty("nonce")+"&request=#token_RequestGeneration#&redirect_uri="+PropertyUtils.getProperty("redirect_url");

	///Backward Compatilibility
	public String backcomp_as_endpoint = PropertyUtils.getProperty("base_url")+PropertyUtils.getProperty("brand_id")+"/api/open-banking/"+PropertyUtils.getProperty("back_cma_ver")+"/aisp/account-access-consents";
	public String backcomp_account_endpoint = PropertyUtils.getProperty("base_url")+PropertyUtils.getProperty("brand_id")+"/api/open-banking/"+PropertyUtils.getProperty("back_cma_ver")+"/aisp/accounts/";
	public String backcomp_dps_endpoint = PropertyUtils.getProperty("base_url")+PropertyUtils.getProperty("brand_id")+"/api/open-banking/"+PropertyUtils.getProperty("back_cma_ver")+"/pisp/domestic-payments";
	public String backcomp_dsps_endpoint = PropertyUtils.getProperty("base_url")+PropertyUtils.getProperty("brand_id")+"/api/open-banking/"+PropertyUtils.getProperty("back_cma_ver")+"/pisp/domestic-scheduled-payments";
	public String backcomp_ispc_endpoint = PropertyUtils.getProperty("base_url")+PropertyUtils.getProperty("brand_id")+"/api/open-banking/"+PropertyUtils.getProperty("back_cma_ver")+"/pisp/international-scheduled-payment-consents";
	public String backcomp_ipc_endpoint = PropertyUtils.getProperty("base_url")+PropertyUtils.getProperty("brand_id")+"/api/open-banking/"+PropertyUtils.getProperty("back_cma_ver")+"/pisp/international-payment-consents";
	public String backcomp_isp_endpoint = PropertyUtils.getProperty("base_url")+PropertyUtils.getProperty("brand_id")+"/api/open-banking/"+PropertyUtils.getProperty("back_cma_ver")+"/pisp/international-scheduled-payment";
	public String backcomp_dso_endpoint = PropertyUtils.getProperty("base_url")+PropertyUtils.getProperty("brand_id")+"/api/open-banking/"+PropertyUtils.getProperty("back_cma_ver")+"/pisp/domestic-standing-order";
	public String backcomp_dspc_endpoint = PropertyUtils.getProperty("base_url")+PropertyUtils.getProperty("brand_id")+"/api/open-banking/"+PropertyUtils.getProperty("back_cma_ver")+"/pisp/domestic-scheduled-payment-consents"; 
	
	/////Negative Variables
	public String invalid_account_endpoint = baseurl+"/cards-accounts/";
	public String invalid_cma_account_endpoint = PropertyUtils.getProperty("base_url")+PropertyUtils.getProperty("brand_id")+"/api/open-banking/2.0/accounts/";
	public String invalid_accountSetup_endpoint = baseurl+"/aisp/card-account-access-consents";
	public String invalid_cma_accountSetup_endpoint = PropertyUtils.getProperty("base_url")+PropertyUtils.getProperty("brand_id")+"/api/open-banking/2.0/account-requests/";
	public String invalid_fc_endpoint = baseurl+"cbpii/cards-funds-confirmation-consents/";
	public String invalid_cma_fc_endpoint = baseurl+"cbpii/cards-funds-confirmation-consents/";
	public String invalid_fcc_endpoint = baseurl+"cbpii/cards-funds-confirmations/";
	public String invalid_cma_fcc_endpoint = baseurl+"cbpii/cards-funds-confirmations/";
	public String invalid_ps_endpoint = PropertyUtils.getProperty("base_url")+PropertyUtils.getProperty("brand_id")+"/api/open-banking/"+PropertyUtils.getProperty("back_cma_ver")+"/cards-payments";
	public String invalid_paymentconsent_endpoint = baseurl+"/pisp/cards-payment-consents";
	public String invalid_payment_endpoint = baseurl+"/pisp/cards-payments";
	public String otherpispapi_dso_endpoint = baseurl+"/pisp/domestic-standing-orders";
	public String otherpispapi_ip_endpoint = baseurl+"/pisp/international-payments";
	public String invalid_cma_ps_endpoint = PropertyUtils.getProperty("base_url")+PropertyUtils.getProperty("brand_id")+"/api/open-banking/v1.0/payments";
	public String invalid_cma_dpc_endpoint = PropertyUtils.getProperty("base_url")+PropertyUtils.getProperty("brand_id")+"/api/open-banking/v2.0/payments";
	public String invalid_psub_endpoint = PropertyUtils.getProperty("base_url")+PropertyUtils.getProperty("brand_id")+"/api/open-banking/"+PropertyUtils.getProperty("back_cma_ver")+"/cards-payment-submissions";
	public String invalid_cma_psub_endpoint = PropertyUtils.getProperty("base_url")+PropertyUtils.getProperty("brand_id")+"/api/open-banking/v1.0/pisp/domestic-payments";
	public String invalid_dspc_endpoint = baseurl+"pisp/payment-consents";
	public String invalid_cma_dspc_endpoint = PropertyUtils.getProperty("base_url")+PropertyUtils.getProperty("brand_id")+"/api/open-banking/v1.0/payments";
	public String invalid_dsoc_endpoint = baseurl+"/pisp/domestic-standing-order-consent";
	public String invalid_cma_dsoc_endpoint = PropertyUtils.getProperty("base_url")+PropertyUtils.getProperty("brand_id")+"/api/open-banking/v1.0/payments";
	public String invalid_cma_fpc_endpoint = PropertyUtils.getProperty("base_url")+PropertyUtils.getProperty("brand_id")+"/api/open-banking/v2.0/file-payment-consents";
	public String invalid_cma_fp_endpoint = PropertyUtils.getProperty("base_url")+PropertyUtils.getProperty("brand_id")+"/api/open-banking/v2.0/file-payments";
	public String older_isp_endpoint = PropertyUtils.getProperty("base_url")+PropertyUtils.getProperty("brand_id")+"/api/open-banking/v3.0/pisp/international-scheduled-payments";
		
	public String othertpp_cc_access_token = "nibsgCxpEwjMQGYOx8XPogtygUim";
	public String expired_cc_accesstoken = "v9Do13FgiqWKH59gkyebfcSwfiaa";
	public String othertpp_AccountId= "89c73c0b-c463-4aba-8449-e4a636a1625e";
	public String otherTPP_accountConsentID= "b17d1d05-0760-4142-b94e-59f790ba7417";
	public String otherTPP_accountaccess_token= "PUlILAckSZWg5FDTiHA7cPDV8Eg7";
	public String expired_accesstoken = "ZwIeCpTT7q4ZBcKvKwF2Vh6F4aFh";
	public String othertpp_RefreshToken = "AOA0sKeXlu4JlctWR4ONZTbec9kNmv08JMucSf1ipb";
	public String expired_auth_code = "0z1G01umUIDvA3CK2uq_W27GyiPlvkBluAwjFx-U";
	public String othertpp_auth_code = "5g9FE9ewYlEZfoJYqxM2xOm_t00zJo_Im40jFx-U";
	public String expiredCispAccessToke = "EZJA8ZXTpoS4FAr2Zh4XPGH2XXa8";
	public String expiredPispCCAccessToken = "5HLpleKh89Z2bBuoNibyll4vOufn";
	public String expiredPispAccessToken = "an1Q4MFz8t3NRYha8emzE4Xr2MaI";
	public String otherTPP_AccessToken= "0cKpH4r2RXjfK3bUFStuplwjO5Hs";
	public String otherTPP_PaymentId= "b5342482-b093-4601-bba7-63c236188bb8";
	public String otherTPP_paymentConsentId= "b241bcb7-1d66-4afa-94f5-8101d921421f";
		
	//end points for generate payments
	public String domesticPayments="Domestic-Payments";
	public String domesticScheduledPayments="Domestic-Scheduled-Payments";
	public String domesticStandingOrders="Domestic-Standing-Orders";
	public String internationalPayments="International-Payments";
	public String internationalScheduledPayments="International-Scheduled-Payments";
	public String filePayments="File-Payments";
	
	//file path for file payments
	public static String xmlFilePath=System.getProperty("user.dir") + "/files/File_Payments/transaction.xml";
	public static String jsonFilePath=System.getProperty("user.dir") + "/files/File_Payments/JsonPayment.json";
	
	//Key store path
	public static String signKeystore=System.getProperty("user.dir") + "/files/Keystores/signinKeystore";
	public static String netKeystore=System.getProperty("user.dir") + "/files/Keystores/networkKeystore";

	public static String aisp_AccessToken;
	public static String aisp_RefreshToken;
	public static String aisp_AccountId;
	public static String aisp_EmptyAccountId;
	public static String aisp_secondAccountId;
	public static String pisp_AccessToken;
	public static String cisp_AccessToken;
	public static String cisp_RefreshToken;
	public static String cc_AccessToken;
	public static String cc_AccountReqId;
	public static String paymentId;
	public static String consentId;
	public static String cisp_CC_AccessToken;
	public static String pisp_CC_AccessToken;
	public static String consentURL;

	public static String getPisp_CC_AccessToken() {
		return pisp_CC_AccessToken;
	}
	public static void setPisp_CC_AccessToken(String pisp_CC_AccessToken) {
		API_Constant.pisp_CC_AccessToken = pisp_CC_AccessToken;
	}
	public static String getCisp_RefreshToken() {
		return cisp_RefreshToken;
	}
	public static void setCisp_RefreshToken(String cisp_RefreshToken) {
		API_Constant.cisp_RefreshToken = cisp_RefreshToken;
	}
	
	public static String getCisp_CC_AccessToken() {
		return cisp_CC_AccessToken;
	}
	public static void setCisp_CC_AccessToken(String cisp_CC_AccessToken) {
		API_Constant.cisp_CC_AccessToken = cisp_CC_AccessToken;
	}
	public static String getConsentId() {
		return consentId;
	}
	public static void setConsentId(String consentId) {
		API_Constant.consentId = consentId;
	}
	public static String getCisp_AccessToken() {
		return cisp_AccessToken;
	}
	public static void setCisp_AccessToken(String cisp_AccessToken) {
		API_Constant.cisp_AccessToken = cisp_AccessToken;
	}	

	public static String getPaymentId() {
		return paymentId;
	}
	public static void setPaymentId(String paymentId) {
		API_Constant.paymentId = paymentId;
	}
	
	public static String getPisp_AccessToken() {
		return pisp_AccessToken;
	}
	public static void setPisp_AccessToken(String pisp_AccessToken) {
		API_Constant.pisp_AccessToken = pisp_AccessToken;
	}

	public static String getAisp_AccessToken() {
		return aisp_AccessToken;
	}
	public static void setAisp_AccessToken(String aisp_AccessToken) {
		API_Constant.aisp_AccessToken = aisp_AccessToken;
	}
	public static String getAisp_RefreshToken() {
		return aisp_RefreshToken;
	}
	public static void setAisp_RefreshToken(String aisp_RefreshToken) {
		API_Constant.aisp_RefreshToken = aisp_RefreshToken;
	}
	public static String getAisp_AccountId() {
		return aisp_AccountId;
	}
	public static void setAisp_AccountId(String aisp_AccountId) {
		API_Constant.aisp_AccountId = aisp_AccountId;
	}
	public static String getAisp_EmptyAccountId() {
		return aisp_EmptyAccountId;
	}
	public static void setAisp_EmptyAccountId(String aisp_EmptyAccountId) {
		API_Constant.aisp_EmptyAccountId = aisp_EmptyAccountId;
	}
	public static String getAisp_secondAccountId() {
		return aisp_secondAccountId;
	}
	public static void setAisp_secondAccountId(String aisp_secondAccountId) {
		API_Constant.aisp_secondAccountId = aisp_secondAccountId;
	}
	public static String getCc_AccessToken() {
		return cc_AccessToken;
	}
	public static void setCc_AccessToken(String cc_AccessToken) {
		API_Constant.cc_AccessToken = cc_AccessToken;
	}

	public static String getCc_AccountReqId() {
		return cc_AccountReqId;
	}
	public static void setCc_AccountReqId(String cc_AccountReqId) {
		API_Constant.cc_AccountReqId = cc_AccountReqId;
	}
	
	public static String getConsentURL() {
		return consentURL;
	}
	public static void setConsentURL(String consentURL) {
		API_Constant.consentURL = consentURL;
	}
	
	//paymentid for get APIs
		public static String dp_PaymentId;
		public static String dsp_PaymentId;
		public static String dso_PaymentId;
		public static String ip_PaymentId;
		public static String isp_PaymentId;
		public static String fp_PaymentId;
		
		//getter and setters of paymentid
		public static String getDp_PaymentId() {
			return dp_PaymentId;
		}
		public static void setDp_PaymentId(String dpPaymentId) {
			API_Constant.dp_PaymentId = dpPaymentId;
		}
		
		public static String getDsp_PaymentId() {
			return dsp_PaymentId;
		}
		public static void setDsp_PaymentId(String dspPaymentId) {
			API_Constant.dsp_PaymentId = dspPaymentId;
		}
		
		public static String getDso_PaymentId() {
			return dso_PaymentId;
		}
		public static void setDso_PaymentId(String dsoPaymentId) {
			API_Constant.dso_PaymentId = dsoPaymentId;
		}
		
		public static String getIp_PaymentId() {
			return ip_PaymentId;
		}
		public static void setIp_PaymentId(String ipPaymentId) {
			API_Constant.ip_PaymentId = ipPaymentId;
		}
		
		public static String getIsp_PaymentId() {
			return isp_PaymentId;
		}
		public static void setIsp_PaymentId(String ispPaymentId) {
			API_Constant.isp_PaymentId = ispPaymentId;
		}
		
		public static String getFp_PaymentId() {
			return fp_PaymentId;
		}
		public static void setFp_PaymentId(String fpPaymentId) {
			API_Constant.fp_PaymentId = fpPaymentId;
		}
}