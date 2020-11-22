package com.aiocdwacs.awacscloudauthserver.config;

import javax.sql.DataSource;

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
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

@Configuration
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {

	@Autowired
	DataSource dataSource;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsService userDetailsService;


	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.jdbc(dataSource)
		.withClient("neo")//client username: a
		.secret(passwordEncoder().encode("neo"))//password: a
		.authorities("ROLE_SCRUM","ROLE_BOARD","ROLE_API_ACCESS", "ROLE_TRUSTED_CLIENT")
		.scopes("all")
		.authorizedGrantTypes("client_credentials")
		
		
		.and()
		
		.withClient("bluesky")
		.secret(passwordEncoder().encode("bluesky"))
		.authorities("ROLE_API_ACCESS")
		.scopes("all", "read", "write")
		.authorizedGrantTypes("refresh_token", "password", "client_credentials")
		.accessTokenValiditySeconds(3600)
		.refreshTokenValiditySeconds(240000);
	}


	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.checkTokenAccess("hasAuthority('ROLE_API_ACCESS')");
	}

	@Bean
	public JdbcTokenStore tokenStore() {
		return new JdbcTokenStore(dataSource);
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

	@Bean
    protected AuthorizationCodeServices authorizationCodeServices() {
		return new JdbcAuthorizationCodeServices(dataSource);
    }
}	