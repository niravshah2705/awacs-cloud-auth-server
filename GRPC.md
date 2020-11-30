### Three parts, an interface, a server part and a clients part

### An Interface (maven settings)
```
<dependency>
  <groupId>com.aiocdawacs</groupId>
  <artifactId>awacs-grpc-interface</artifactId>
  <version>1.0.25.M2</version>
</dependency>

```

### Google Proto Definition
```
giris@DESKTOP-45UA338 MINGW64 /d/aiocd-workspace/java-workspace/awacs-cloud-commons/awacs-grpc-interface (master)
$ cat src/main/proto/check_token.proto
syntax = "proto3";

package com.aiocdawacs.boot.grpc.interface;

option java_multiple_files = true;
option java_package = "com.aiocdawacs.boot.grpc.lib";
option java_outer_classname = "GrpcAwacsTokenServiceProto";

import "google/protobuf/timestamp.proto";

service GrpcAwacsTokenService {

    rpc CheckToken (CheckTokenRequest)
        returns (CheckTokenReply) {

        }
}

message CheckTokenRequest {
    string token = 1;
}

message CheckTokenReply {
  string username = 1;
  bool approved = 2;
  string client_id = 3;
  Authority authorities = 4;
  Scope scope = 5;
  google.protobuf.Timestamp exp = 6;
  string whoami = 7;
}

message Scope {
  repeated string scope = 1;
}
message Authority {
   repeated string authority = 1;
}
```

### Second, a server part (Configuration) 

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
package com.aiocdwacs.awacscloudauthserver.config;

import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.grpc.netty.shaded.io.grpc.netty.NettyServerBuilder;
import net.devh.boot.grpc.server.security.authentication.BasicGrpcAuthenticationReader;
import net.devh.boot.grpc.server.security.authentication.GrpcAuthenticationReader;
import net.devh.boot.grpc.server.serverfactory.GrpcServerConfigurer;

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

import java.util.List;
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
	@PreAuthorize("hasAuthority('implicit')" )
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

		Long millis  	   = (Long)response.get(FrameworkParams.exp.name());
		Timestamp exp      = Timestamp.newBuilder().setSeconds(millis / 1000).setNanos((int) ((millis % 1000) * 1000000)).build();

		String clientId    = (String)response.get(FrameworkParams.client_id.name());
		String userName    = (String)response.get(FrameworkParams.user_name.name());
				
		Boolean isActive   = null == response.get(FrameworkParams.active.name()) ? Boolean.TRUE: Boolean.FALSE;

		CheckTokenReply reply = CheckTokenReply.newBuilder()
				.setApproved(isActive)
				.setUsername(userName)
				.setAuthorities(Authority.newBuilder().addAllAuthority((List<String>) response.get(FrameworkParams.authorities.name())).build())
				.setClientId(clientId)
				.setScope(Scope.newBuilder().addAllScope((Set<String>) response.get(FrameworkParams.scope.name())).build())
				.setExp(exp)
				.setWhoami(gRPCServerName)	// discovery ??
				.build();
		
		logger.debug("check_token login success from grpc proc");
		
		responseObserver.onNext(reply);
		responseObserver.onCompleted();
	}
}

```
### CLI to work with gRPC comm 
```
https://github.com/fullstorydev/grpcurl/releases/tag/v1.7.0
export PATH=$PATH;/path/to/grpcurl

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
prerequisite get Token from REST - (postman), then use wakandagrpc:wakandagrpc basic auth which is a SYSTEM user with implicit authority

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
Third, clients part. Tomorrow!
