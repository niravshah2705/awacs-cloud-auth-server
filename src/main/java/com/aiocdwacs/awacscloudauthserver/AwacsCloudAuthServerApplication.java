package com.aiocdwacs.awacscloudauthserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

import com.aiocdawacs.cloud.stream.config.EnableAwacsEventPublisherModule;
import com.aiocdawacs.cloud.stream.model.CloudEvent;
import com.aiocdawacs.cloud.stream.model.CloudEventType;
import com.aiocdawacs.cloud.stream.service.AwacsCloudEventProviderEnum;
import com.aiocdawacs.cloud.stream.service.CloudEventBuilder;
import com.aiocdawacs.cloud.stream.service.CloudEventNameConstants;
import com.aiocdawacs.cloud.stream.service.CloudEventPublisherService;

@SpringBootApplication
@EnableAuthorizationServer
@EnableScheduling
@EnableAwacsEventPublisherModule
public class AwacsCloudAuthServerApplication {

	public static final String SERVICE_NAME = "AUTH-SERVICE";
	
	@Autowired
	private CloudEventPublisherService cloudEventPublisherService;
	
	public static void main(String[] args) {
		SpringApplication.run(AwacsCloudAuthServerApplication.class, args);
	}
	
	@EventListener(ApplicationReadyEvent.class)
	public void sendEvent() {
		CloudEvent appReadyEvent = new CloudEventBuilder()
				.withName(CloudEventNameConstants.APPLICATION_START_EVENT)
				.withSource(SERVICE_NAME)
				.withType(CloudEventType.APPLICATION_START_EVENT)
				.build();
		cloudEventPublisherService.publishMessage(appReadyEvent, AwacsCloudEventProviderEnum.GoogleCloudPlatformPubSub);
	}
	

	@Scheduled(fixedDelay = 30000, initialDelay = 5000)
	public void sendAliveEvent() {
		CloudEvent appAliveEvent = new CloudEventBuilder()
				.withName(CloudEventNameConstants.APPLICATION_ALIVE_EVENT)
				.withSource(SERVICE_NAME)
				.withType(CloudEventType.APPLICATION_ALIVE_EVENT)
				.build();
		cloudEventPublisherService.publishMessage(appAliveEvent, AwacsCloudEventProviderEnum.GoogleCloudPlatformPubSub);
	}
}