package com.aiocdwacs.awacscloudauthserver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration 
public class MailServerConfig {

	@Value("${email.host:authserver}")
	private String host;

	@Value("${email.from:noreply@aiocdawacs.com}")
	private String from;

	@Value("${email.subject:Default Email}")
	private String subject;

	@Bean
	public JavaMailSender javaMailService() {
		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
		javaMailSender.setHost(host);
		return javaMailSender;
	}

	@Bean
	public SimpleMailMessage simpleMailMessage() {
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setFrom(from);
		simpleMailMessage.setSubject(subject);
		return simpleMailMessage;
	}
}