package com.aiocdwacs.awacscloudauthserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private AuthenticationManager authenticationManager;


	@Autowired
	private UserDetailsService userDetailsService;


	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients
		.inMemory()
		.withClient("a")//client username: a
		.secret(passwordEncoder().encode("a"))//password: a
		.authorities("ROLE_A","ROLE_B","ROLE_TRUSTED_CLIENT")
		.scopes("all")
		.authorizedGrantTypes("client_credentials")
		.and()
		.withClient("b")
		.secret(passwordEncoder().encode("b"))
		.authorities("ROLE_C")
		.scopes("all")
		.authorizedGrantTypes("refresh_token", "password", "client_credentials")
		.accessTokenValiditySeconds(1200)
		.refreshTokenValiditySeconds(240000);
	}


	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		//	    security.checkTokenAccess("permitAll()"); //if used permitAll() this will make oauth/check_token endpoint to be unsecure
		security.checkTokenAccess("hasAuthority('ROLE_C')"); //secure the oauth/check_token endpoint
	}

	@Bean
	public TokenStore tokenStore() {
		return new InMemoryTokenStore();
	}

	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder(4);
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints
		.authenticationManager(authenticationManager)
		.userDetailsService(userDetailsService);
	}

}	