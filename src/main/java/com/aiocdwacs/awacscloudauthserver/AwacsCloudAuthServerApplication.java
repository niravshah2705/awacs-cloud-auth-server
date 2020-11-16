package com.aiocdwacs.awacscloudauthserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

import com.aiocdawacs.cloud.stream.config.EnableAwacsEventPublisherModule;

@SpringBootApplication
@EnableAuthorizationServer
@EnableAwacsEventPublisherModule
public class AwacsCloudAuthServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AwacsCloudAuthServerApplication.class, args);
	}
}