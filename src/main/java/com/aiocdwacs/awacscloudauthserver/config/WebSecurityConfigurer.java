package com.aiocdwacs.awacscloudauthserver.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.aiocdwacs.awacscloudauthserver.service.UserDetailServiceImpl;

@Configuration
@EnableWebSecurity
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;
	
	@Autowired
	UserDetailServiceImpl userDetailsService;
 	

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.formLogin();
		http.headers().frameOptions().disable();
		http.csrf().disable();	// i know what I am doing. until
		http
		.authorizeRequests()
		.antMatchers("/api/user/").hasAnyRole("BOARD","API_ACCESS", "TRUSTED_CLIENT")
		.antMatchers("/oauth/check_token").hasRole("TRUSTED_CLIENT")
		.antMatchers("/").permitAll()
		.antMatchers("/swagger**").permitAll()
		.antMatchers("/actuator**").permitAll();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
		.jdbcAuthentication()
		.dataSource(dataSource)
		.usersByUsernameQuery("SELECT username, password, enabled from users where username = ?");
	}
}