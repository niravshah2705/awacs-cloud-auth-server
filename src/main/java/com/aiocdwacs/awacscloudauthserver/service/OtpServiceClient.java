package com.aiocdwacs.awacscloudauthserver.service;

public interface OtpServiceClient {

	public void sendOneTimePassword(String msisdn, String emailId);
	
	public boolean validateOneTimePassword(String userId, String otpEntered);
	
}
