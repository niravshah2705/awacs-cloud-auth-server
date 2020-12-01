package com.aiocdwacs.awacscloudauthserver.actuator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import io.grpc.health.v1.HealthCheckRequest;
import io.grpc.health.v1.HealthCheckResponse.ServingStatus;
import io.grpc.health.v1.HealthGrpc.HealthBlockingStub;
import io.grpc.services.HealthStatusManager;

@Component
@RestControllerEndpoint(id = "grpc")
public class GrpcHealthIndicatorEndpoint implements HealthIndicator {

	@Autowired
	HealthBlockingStub health;

	@Override
	public Health health() {
		HealthCheckRequest grpcHealth = HealthCheckRequest.newBuilder()
				.setService(HealthStatusManager.SERVICE_NAME_ALL_SERVICES).build();

		ServingStatus status = health.check(grpcHealth).getStatus();

		switch (status) {
		case SERVING:
			return Health.up().build();
		case NOT_SERVING:
		default:
			return Health.down().build();

		}
	}
}
