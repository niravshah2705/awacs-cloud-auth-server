package com.aiocdwacs.awacscloudauthserver.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import com.aiocdwacs.awacscloudauthserver.model.User;

@Component("NotInUse")
public class CustomTokenEnhancer implements TokenEnhancer {

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		Map<String, Object> additionalInfo = new HashMap<>();
		try {
			if (authentication != null) {
				User user = (User)authentication.getPrincipal();
				additionalInfo.put("id", user.getId());
				additionalInfo.put("email", user.getEmail());
				additionalInfo.put("phone", user.getPhoneNo());
				additionalInfo.put("profileLastUpdated", user.getModifiedOn());
			}else {
				additionalInfo.put("sessionid", "awacs");
			}
		}finally {
			((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
		}

		return accessToken;
	}
}
