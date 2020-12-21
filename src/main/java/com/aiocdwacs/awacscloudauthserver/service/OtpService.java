package com.aiocdwacs.awacscloudauthserver.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OtpService implements OtpServiceClient {

	@Autowired
	RestTemplate restTemplate;

	@Value("${awacs.otpservice.endpoint:http://otpservice/api/}")
	String otpServiceEndpointUri;

	@Override
	public void sendOneTimePassword(String msisdn, String emailId) {
		Map<String, String> uriVariables = new HashMap<String, String>();
		uriVariables.put("numbers", msisdn);
		uriVariables.put("email", emailId);
		HttpEntity<String> request = new HttpEntity<String>("foo");
		restTemplate.exchange(otpServiceEndpointUri+"/send-message", HttpMethod.POST, request, String.class, uriVariables);
	}

	@Override
	public boolean validateOneTimePassword(String userId, String otpEntered) {
		return false;
	}
	
}
