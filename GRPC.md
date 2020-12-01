### Configuration 

```

grpc.server.port=${GRPC_PORT:9345}
grpc.server.in-process-name=check_token
grpc.server.address=0.0.0.0
grpc.client.inProcess.address=in-process:check_token

```

### Init logs 

```
2020-11-30 19:52:47.287  INFO 36248 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8100 (http) with context path ''
2020-11-30 19:52:47.390  INFO 36248 --- [           main] n.d.b.g.s.s.AbstractGrpcServerFactory    : Registered gRPC service: com.aiocdawacs.boot.grpc.interface.GrpcAwacsTokenService, bean: cloudGrpcCheckTokenServiceImpl, class: com.aiocdwacs.awacscloudauthserver.service.CloudGrpcCheckTokenServiceImpl$$EnhancerBySpringCGLIB$$88a92332
2020-11-30 19:52:47.410  INFO 36248 --- [           main] n.d.b.g.s.s.GrpcServerLifecycle          : gRPC Server started, listening on address: 0.0.0.0, port: 9345
2020-11-30 19:52:47.412  INFO 36248 --- [           main] n.d.b.g.s.s.AbstractGrpcServerFactory    : Registered gRPC service: com.aiocdawacs.boot.grpc.interface.GrpcAwacsTokenService, bean: cloudGrpcCheckTokenServiceImpl, class: com.aiocdwacs.awacscloudauthserver.service.CloudGrpcCheckTokenServiceImpl$$EnhancerBySpringCGLIB$$88a92332
2020-11-30 19:52:47.413  INFO 36248 --- [           main] n.d.b.g.s.s.GrpcServerLifecycle          : gRPC Server started, listening on address: in-process:check_token, port: -1

```

### Authentication code with spring security

```
@Configuration
public class GrpcServerConfig {

	@Bean 
	public GrpcAuthenticationReader grpcAuthenticationReader() {
		return new BasicGrpcAuthenticationReader();
	}
	
	@Bean
	public GrpcServerConfigurer keepAliveServerConfigurer() {
		
	    return serverBuilder -> {
	        if (serverBuilder instanceof NettyServerBuilder) {
	            ((NettyServerBuilder) serverBuilder)
	                    .keepAliveTime(30, TimeUnit.SECONDS)
	                    .keepAliveTimeout(5, TimeUnit.SECONDS)
	                    .permitKeepAliveWithoutCalls(true);
	        }
	    };
	}
	
}

```

@GrpcService =>

```
package com.aiocdwacs.awacscloudauthserver.service;

import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

import com.aiocdawacs.boot.grpc.lib.Authority;
import com.aiocdawacs.boot.grpc.lib.CheckTokenReply;
import com.aiocdawacs.boot.grpc.lib.CheckTokenRequest;
import com.aiocdawacs.boot.grpc.lib.GrpcAwacsTokenServiceGrpc.GrpcAwacsTokenServiceImplBase;
import com.aiocdawacs.boot.grpc.lib.Scope;
import com.google.protobuf.Timestamp;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class CloudGrpcCheckTokenServiceImpl extends GrpcAwacsTokenServiceImplBase {

	@Value("${grpc.server.in-process-name}")
	private String gRPCServerName;
	
	enum FrameworkParams {
		client_id, authorities, user_name, aud, jti, scope, active, exp
	}

	Logger logger = LoggerFactory.getLogger(CloudGrpcCheckTokenServiceImpl.class);

	@Autowired
	ResourceServerTokenServices resourceServerTokenServices;

	@Autowired
	AccessTokenConverter accessTokenConverter;

	@SuppressWarnings("unchecked")
	@Override
	@PreAuthorize("hasRole('IMPLICIT')")
	public void checkToken(CheckTokenRequest request, StreamObserver<CheckTokenReply> responseObserver) {

		logger.info("gRPC call for checkToken invoked ");

		OAuth2AccessToken token = resourceServerTokenServices.readAccessToken(request.getToken());

		if (token == null) {
			throw new InvalidTokenException("Token was not recognised");
		}

		if (token.isExpired()) {
			throw new InvalidTokenException("Token has expired");
		}

		OAuth2Authentication authentication = resourceServerTokenServices.loadAuthentication(token.getValue());

		Map<String, Object> response = (Map<String, Object>) accessTokenConverter.convertAccessToken(token, authentication);

		String clientId    = (String)response.get(FrameworkParams.client_id.name());
		String userName    = (String)response.get(FrameworkParams.user_name.name());
		Timestamp exp      = (Timestamp)response.get(FrameworkParams.exp.name());
		Boolean isActive   = (Boolean)response.get(FrameworkParams.active.name());

		CheckTokenReply reply = CheckTokenReply.newBuilder()
				.setApproved(isActive)
				.setUsername(userName)
				.setAuthorities(Authority.newBuilder().addAllAuthority((Set<String>) response.get(FrameworkParams.authorities.name())).build())
				.setClientId(clientId)
				.setScope(Scope.newBuilder().addAllScope((Set<String>) response.get(FrameworkParams.scope.name())).build())
				.setExp(exp)
				.setWhoami(gRPCServerName)	// discovery ??
				.build();

		responseObserver.onNext(reply);
		responseObserver.onCompleted();
	}

}


### Health checks
```
package com.aiocdwacs.awacscloudauthserver.actuator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;

import io.grpc.health.v1.HealthCheckRequest;
import io.grpc.health.v1.HealthCheckResponse.ServingStatus;
import io.grpc.health.v1.HealthGrpc.HealthBlockingStub;
import io.grpc.services.HealthStatusManager;

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

```

### Health check demo - 
```
```

### and
```
```
### Actuator Health check 
```
```

```
### GRPC Describe service

```
$ grpcurl --plaintext localhost:9345 describe
grpc.health.v1.Health is a service:
service Health {
  rpc Check ( .grpc.health.v1.HealthCheckRequest ) returns ( .grpc.health.v1.HealthCheckResponse );
  rpc Watch ( .grpc.health.v1.HealthCheckRequest ) returns ( stream .grpc.health.v1.HealthCheckResponse );
}
grpc.reflection.v1alpha.ServerReflection is a service:
service ServerReflection {
  rpc ServerReflectionInfo ( stream .grpc.reflection.v1alpha.ServerReflectionRequest ) returns ( stream .grpc.reflection.v1alpha.ServerReflectionResponse );
}
com.aiocdawacs.boot.grpc.interface.GrpcAwacsTokenService is a service:
service GrpcAwacsTokenService {
  rpc CheckToken ( .com.aiocdawacs.boot.grpc.interface.CheckTokenRequest ) returns ( .com.aiocdawacs.boot.grpc.interface.CheckTokenReply );
}

```
### GRPC Lists

```
$ grpcurl --plaintext localhost:9345 list
com.aiocdawacs.boot.grpc.interface.GrpcAwacsTokenService
grpc.health.v1.Health
grpc.reflection.v1alpha.ServerReflection

```

### GRPC Login (with role other than 'implicit')

```
C:\Users\giris>grpcurl --rpc-header "Authorization: Basic YWRtaW46YWRtaW4xMjM0" --plaintext -d "{\"token\": \"tokenvalue\"}" localhost:9345 com.aiocdawacs.boot.grpc.interface.GrpcAwacsTokenService/CheckToken
ERROR:
  Code: PermissionDenied
  Message: Access denied
```  

### GRPC Successful checkToken implicit call example 
Get Token from REST - (postman), then use wakandagrpc:wakandagrpc basic auth

```
grpcurl --rpc-header "Authorization: Basic d2FrYW5kYWdycGM6d2FrYW5kYWdycGM=" --plaintext -d "{\"token\": \"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsicmVzb3VyY2Utc2VydmVyLXJlc3QtYXBpIl0sInVzZXJfbmFtZSI6ImFkbWluIiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl0sImV4cCI6MTYwNjc3NDcxNywiYXV0aG9yaXRpZXMiOlsiU1lTVEVNIiwib3JkZXJfcmVhZCIsIm9yZGVyX2NyZWF0ZSIsInByb2R1Y3RfdXBkYXRlIiwib3JkZXJfZGVsZXRlIiwicm9sZV9wcm9kdWN0X29yZGVyX3JlYWRlciIsIlVTRVIiLCJvcmRlcl91cGRhdGUiLCJwcm9kdWN0X3JlYWQiLCJwcm9kdWN0X2NyZWF0ZSIsInByb2R1Y3RfZGVsZXRlIl0sImp0aSI6IjAzZTc5ZTllLTJhOWItNGU2Ny04ZTg0LWVjZDZmNTk2YzY3ZCIsImNsaWVudF9pZCI6Im5lbyJ9.oe73_ypVF3OnslNzlgbcRj4ScnmeaIOB992DKtCeayo\"}" localhost:9345 com.aiocdawacs.boot.grpc.interface.GrpcAwacsTokenService/CheckToken

{
  "username": "admin",
  "approved": true,
  "client_id": "neo",
  "authorities": {
    "authority": [
      "SYSTEM",
      "order_read",
      "order_create",
      "product_update",
      "order_delete",
      "role_product_order_reader",
      "USER",
      "order_update",
      "product_read",
      "product_create",
      "product_delete"
    ]
  },
  "scope": {
    "scope": [
      "read",
      "write"
    ]
  },
  "exp": "1970-01-19T14:19:34.717Z",
  "whoami": "check_token"
}

```
