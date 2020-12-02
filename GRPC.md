### Three parts, an interface, a server part and a clients part

### An Interface (maven settings)
```
<dependency>
  <groupId>com.aiocdawacs</groupId>
  <artifactId>awacs-grpc-interface</artifactId>
  <version>1.0.25.M5</version>
</dependency>

```

### Google Proto Definition
```
cat check_token.proto

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
    string source = 2;
}

message CheckTokenReply {
  repeated string aud = 1;
  string user_name = 2;
  repeated string scope = 3;
  bool active = 4;
  google.protobuf.Timestamp exp = 5;
  repeated string authorities = 6;
  string jti = 7;
  string client_id = 8;
  string whoami = 9;
}
```

### Second, a server part (Configuration) 


```

grpc.server.port=${GRPC_PORT:9345}
grpc.server.in-process-name=check_token
grpc.server.address=0.0.0.0
grpc.client.inProcess.address=in-process:check_token
security.oauth2.authorization.check-token-access=denyAll()

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

import io.grpc.health.v1.HealthGrpc;
import io.grpc.health.v1.HealthGrpc.HealthBlockingStub;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
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
	@Bean
	public HealthBlockingStub health() {
		return HealthGrpc.newBlockingStub(InProcessChannelBuilder.forName(InProcessServerBuilder.generateName()).directExecutor().build());
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneOffset;

import com.aiocdawacs.boot.grpc.lib.CheckTokenReply;
import com.aiocdawacs.boot.grpc.lib.CheckTokenRequest;
import com.aiocdawacs.boot.grpc.lib.GrpcAwacsTokenServiceGrpc.GrpcAwacsTokenServiceImplBase;
import com.google.protobuf.Timestamp;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService(interceptors = { LogGrpcInterceptor.class })
public class CloudGrpcCheckTokenServiceImpl extends GrpcAwacsTokenServiceImplBase {

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
	@PreAuthorize("hasAuthority('implicit')")
	public void checkToken(CheckTokenRequest request, StreamObserver<CheckTokenReply> responseObserver) {

		OAuth2AccessToken token = resourceServerTokenServices.readAccessToken(request.getToken());

		if (token == null) {
			throw new InvalidTokenException("Token was not recognised");
		}

		if (token.isExpired()) {
			throw new InvalidTokenException("Token has expired");
		}

		OAuth2Authentication authentication = resourceServerTokenServices.loadAuthentication(token.getValue());

		Map<String, Object> response = (Map<String, Object>) accessTokenConverter.convertAccessToken(token,
				authentication);

		// DefaultOAuth2AccessToken result = new DefaultOAuth2AccessToken(token);

		Long millis = LocalDateTime.now().plusHours(3).toInstant(ZoneOffset.UTC).toEpochMilli(); // there is an issue
		// with response
		// timestamp
		Timestamp exp = Timestamp.newBuilder().setSeconds(millis / 1000).setNanos((int) ((millis % 1000) * 1000000))
				.build();
		Set<String> resourceIds = (Set) response.get(FrameworkParams.aud.name());
		String jti = (String) response.get(FrameworkParams.jti.name()); // UUID.randomUUID().toString();
		String clientId = (String) response.get(FrameworkParams.client_id.name());
		String userName = (String) response.get(FrameworkParams.user_name.name());

		final String resourceName = resourceIds.iterator().hasNext() ? (String) resourceIds.iterator().next()
				: "Missing aud";

		Boolean isActive = null == response.get(FrameworkParams.active.name()) ? Boolean.TRUE : Boolean.FALSE;

		CheckTokenReply reply = CheckTokenReply.newBuilder().setActive(isActive)
				.addAud(resourceName).setJti(jti)
				.setUserName(userName)
				.addAllAuthorities((List<String>) response.get(FrameworkParams.authorities.name()))
				.setClientId(clientId)
				.addAllScope((Set<String>) response.get(FrameworkParams.scope.name()))
				.setExp(exp).setWhoami("wakandagrpc") // discovery ??
				.build();

		logger.debug("check_token login success from grpc proc by ("+request.getSource()+")");

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
$ grpcurl --rpc-header "Authorization: Basic d2FrYW5kYWdycGM6d2FrYW5kYWdycGM=" --plaintext -d "{\"token\": \"tokenvalue\"}" localhost:9345 com.aiocdawacs.boot.grpc.interface.GrpcAwacsTokenService/CheckToken
ERROR:
  Code: PermissionDenied
  Message: Access denied
  
```

### GRPC Successful checkToken implicit call example 

- Note that prerequisite get Token from REST - (postman), then use wakandagrpc:wakandagrpc basic auth which is a SYSTEM user with implicit authority

```
grpcurl --rpc-header "Authorization: Basic d2FrYW5kYWdycGM6d2FrYW5kYWdycGM=" --plaintext -d "{\"token\": \"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsicmVzb3VyY2Utc2VydmVyLXJlc3QtYXBpIl0sInVzZXJfbmFtZSI6ImFkbWluIiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl0sImV4cCI6MTYwNjk0MTAwMSwiYXV0aG9yaXRpZXMiOlsiU1lTVEVNIiwib3JkZXJfcmVhZCIsIm9yZGVyX2NyZWF0ZSIsInByb2R1Y3RfdXBkYXRlIiwib3JkZXJfZGVsZXRlIiwicm9sZV9wcm9kdWN0X29yZGVyX3JlYWRlciIsIlVTRVIiLCJvcmRlcl91cGRhdGUiLCJwcm9kdWN0X3JlYWQiLCJwcm9kdWN0X2NyZWF0ZSIsInByb2R1Y3RfZGVsZXRlIl0sImp0aSI6ImNlZGYwMmJkLTZhMDktNDM4My1iMjdiLTQwZjJiNTE3NjI4YyIsImNsaWVudF9pZCI6Im5lbyJ9.lm3FNEfHcQscYnacpUO5Kga-gTB02jaZ3vOVWxIBEZw\"}" localhost:9345 com.aiocdawacs.boot.grpc.interface.GrpcAwacsTokenService/CheckToken
{
  "aud": [
    "resource-server-rest-api"
  ],
  "user_name": "admin",
  "scope": [
    "read",
    "write"
  ],
  "active": true,
  "exp": "2020-12-03T02:00:15.620Z",
  "authorities": [
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
  ],
  "jti": "cedf02bd-6a09-4383-b27b-40f2b517628c",
  "client_id": "neo",
  "whoami": "wakandagrpc"
}
```

### Health Indicator


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
### Server Logging - 

```
package com.aiocdwacs.awacscloudauthserver.service;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;

public class LogGrpcInterceptor implements ServerInterceptor {

	private static final Logger log = LoggerFactory.getLogger(LogGrpcInterceptor.class);

	@Override
	public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers,
			ServerCallHandler<ReqT, RespT> next) {
		log.info(ToStringBuilder.reflectionToString(call, ToStringStyle.NO_CLASS_NAME_STYLE));
		log.info(ToStringBuilder.reflectionToString(headers, ToStringStyle.NO_CLASS_NAME_STYLE));
		log.info(ToStringBuilder.reflectionToString(next, ToStringStyle.NO_CLASS_NAME_STYLE));
		return next.startCall(call, headers);
	}
}
```

### Health check (nutshell) -

```
$ grpcurl --plaintext localhost:9345 grpc.health.v1.Health/Check
{
  "status": "SERVING"
}
```

### Actuator Health check



Third, clients part. Tomorrow!

1. Maven add -  

```
		<dependency>
			<groupId>com.aiocdawacs</groupId>
			<artifactId>awacs-grpc-interface</artifactId>
			<version>${awacs-commons-version}</version>
			<exclusions>
				<exclusion>
					<groupId>io.grpc</groupId>
					<artifactId>grpc-netty-shaded</artifactId>
				</exclusion>
			</exclusions>
		</dependency>
		<dependency>
			<groupId>net.devh</groupId>
			<artifactId>grpc-client-spring-boot-starter</artifactId>
			<version>2.10.1.RELEASE</version>
			<exclusions>
				<exclusion>
					<groupId>io.grpc</groupId>
					<artifactId>grpc-netty-shaded</artifactId>
				</exclusion>
			</exclusions>
		</dependency>			

```
### Grpc Client config

```
package com.aiocdawacs.smart.pharmacy.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.aiocdawacs.smart.pharmacy.interceptor.LogGrpcInterceptor;

import io.grpc.ClientInterceptor;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import net.devh.boot.grpc.client.config.GrpcChannelProperties;
import net.devh.boot.grpc.client.interceptor.GrpcGlobalClientInterceptor;

@Configuration
@Import(GrpcChannelProperties.class)
public class GrpcClientConfig {

	@GrpcGlobalClientInterceptor
	ClientInterceptor logClientInterceptor() {
		return new LogGrpcInterceptor();
	}

	//	@Bean
	//	public ManagedChannel managedChannel(@Value("${spring.cloud.consul.host:127.0.0.1}") String host,
	//			@Value("${spring.cloud.consul.port:9345}") int port,
	//			@Value("${connection.idle-timeout}") int timeout,
	//			@Value("${connection.max-inbound-message-size}") int maxInBoundMessageSize,
	//			@Value("${connection.max-inbound-metadata-size}") int maxInBoundMetadataSize,
	//			@Value("${connection.load-balancing-policy}") String loadBalancingPolicy,
	//			@Qualifier("userResolver") NameResolverProvider nameResolverProvider) {
	//		return ManagedChannelBuilder
	//				.forTarget("consul://" + host + ":" + port)                     // build channel to server with server's address
	//				.keepAliveWithoutCalls(false)                                   // Close channel when client has already received response
	//				.idleTimeout(timeout, TimeUnit.MILLISECONDS)                    // 10000 milliseconds / 1000 = 10 seconds --> request time-out
	//				.maxInboundMetadataSize(maxInBoundMetadataSize * 1024 * 1024)   // 2KB * 1024 = 2MB --> max message header size
	//				.maxInboundMessageSize(maxInBoundMessageSize * 1024 * 1024)     // 10KB * 1024 = 10MB --> max message size to transfer together
	//				.defaultLoadBalancingPolicy(loadBalancingPolicy)                // set load balancing policy for channel
	//				.nameResolverFactory(nameResolverProvider)                      // using Consul service discovery for DNS querying
	//				/* .intercept(clientInterceptor) */                                   // add internal credential authentication
	//				.usePlaintext()                                                 // use plain-text to communicate internally
	//				.build();                                                       // Build channel to communicate over gRPC
	//	}

	@Bean
	public ManagedChannel managedChannel(
			@Value("${spring.cloud.consul.host:/127.0.0.1}") String host,
			@Value("${spring.cloud.consul.port:9345}") int port) {
		return ManagedChannelBuilder.forTarget("dns://" + host + ":" + port).keepAliveWithoutCalls(false).usePlaintext().build();                                                       
	}
	}

```
### Remote GRPC Token Service (Spring Custom Implementation)


```
package com.aiocdawacs.smart.pharmacy.service;


import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.stereotype.Service;

import com.aiocdawacs.boot.grpc.lib.CheckTokenReply;
import com.aiocdawacs.boot.grpc.lib.CheckTokenRequest;
import com.aiocdawacs.boot.grpc.lib.GrpcAwacsTokenServiceGrpc;

import io.grpc.Context;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Metadata;
import io.grpc.stub.MetadataUtils;
import io.micrometer.core.instrument.util.StringUtils;

class Constants {

	public static final Metadata.Key<String> AUTHORIZATION_METADATA_KEY = Metadata.Key.of("Authorization", Metadata.ASCII_STRING_MARSHALLER);

	public static final Context.Key<String> CLIENT_ID_CONTEXT_KEY 		= Context.key("clientId");

	private Constants() {
		throw new AssertionError();
	}
}

@Service
public class RemoteGrpcTokenService implements ResourceServerTokenServices {

	Logger logger = LoggerFactory.getLogger(RemoteGrpcTokenService.class);

	enum FrameworkParams {
		client_id, authorities, user_name, aud, jti, scope, active, exp
	}

	//	@GrpcClient("check_token")
	//	private Channel channel;
	//	
	//	@GrpcClient(value = "check_token", interceptors = LogGrpcInterceptor.class)
	//	private GrpcAwacsTokenServiceBlockingStub checkTokenStub;

	private AccessTokenConverter tokenConverter = new DefaultAccessTokenConverter();

	public RemoteGrpcTokenService() {
		super();
	}

	public void setAccessTokenConverter(AccessTokenConverter accessTokenConverter) {
		this.tokenConverter = accessTokenConverter;
	}

	@Override
	public OAuth2Authentication loadAuthentication(String accessToken) throws AuthenticationException, InvalidTokenException {

		if(StringUtils.isEmpty(accessToken)) {
			throw new InvalidTokenException(accessToken);
		}

		ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9345).usePlaintext().build();

		Metadata basicWakandaAuthHeaderMetadata =new Metadata();
		basicWakandaAuthHeaderMetadata.put(Constants.AUTHORIZATION_METADATA_KEY, getAuthorizationHeader("wakandagrpc", "wakandagrpc"));

		CheckTokenRequest request = CheckTokenRequest.newBuilder().setToken(accessToken).build();

		// BasicToken basicWakandaCredentials = new BasicToken(); CallCredentials has its own hell and heaven so leaving it a backlog. ;)

		CheckTokenReply reply     = MetadataUtils.attachHeaders(GrpcAwacsTokenServiceGrpc.newBlockingStub(channel), basicWakandaAuthHeaderMetadata).checkToken(request);

		if(! reply.getActive()) {
			logger.info(ToStringBuilder.reflectionToString(reply, ToStringStyle.NO_CLASS_NAME_STYLE));
			throw new InvalidTokenException(accessToken);
		}

		ToStringBuilder.reflectionToString((Object)reply, ToStringStyle.JSON_STYLE);

		return tokenConverter.extractAuthentication(toMap(reply));
	}

	private Map<String, ?> toMap(CheckTokenReply reply){
		Map<String, Object> foo = new HashMap<String, Object>();
		foo.put(FrameworkParams.authorities.name(), reply.getAuthoritiesList());
		foo.put(FrameworkParams.client_id.name(), reply.getClientId());
		foo.put(FrameworkParams.aud.name(), "resource-server-rest-api");
		foo.put(FrameworkParams.exp.name(), reply.getExp());
		foo.put(FrameworkParams.jti.name(), UUID.randomUUID().toString());	//FIXME what's that ?
		foo.put(FrameworkParams.scope.name(), reply.getScopeList());
		foo.put(FrameworkParams.user_name.name(), reply.getUserName());
		foo.put("whoami", "wakandagrpc");	// this.clientId

		return foo;
	}

	@Override
	public OAuth2AccessToken readAccessToken(String accessToken) {
		throw new UnsupportedOperationException("Not supported: read access token");
	}

	private String getAuthorizationHeader(String clientId, String clientSecret) {

		if(clientId == null || clientSecret == null) {
			logger.warn("Null Client ID or Client Secret detected. Endpoint that requires authentication will reject request with 401 error.");
		}

		String creds = String.format("%s:%s", clientId, clientSecret);
		try {
			return "Basic " + new String(Base64.getEncoder().encode(creds.getBytes("UTF-8")));
		}
		catch (UnsupportedEncodingException e) {
			throw new IllegalStateException("Could not convert String");
		}
	}
}

```

### Switch from REST Based Check Token service to GRPC in existing Oauth2 resource server configurer

```
	
	[...]
	@Autowired
	RemoteGrpcTokenService remoteGrpcTokenService;
	
		@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.tokenServices(remoteGrpcTokenService);
		resources.resourceId("resource-server-rest-api");
	}

	@Bean
	public TokenStore tokenStore() {
		if (tokenStore == null) {
			tokenStore = new JwtTokenStore(jwtAccessTokenConverter());
		}
			return tokenStore;
	}

	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		return converter;
	}

```


### Done, resource server log

```
2020-12-02 23:09:18.227  INFO 18044 --- [ault-executor-6] c.a.a.service.LogGrpcInterceptor         : [responseCounter=io.micrometer.core.instrument.cumulative.CumulativeCounter@5e84cdac,timerFunction=net.devh.boot.grpc.common.metric.AbstractMetricCollectingInterceptor$$Lambda$1114/0x00000008015d5b88@54ca686f,timerSample=io.micrometer.core.instrument.Timer$Sample@2109310c,delegate=io.grpc.internal.ServerCallImpl@26d1f84b]
2020-12-02 23:09:18.228  INFO 18044 --- [ault-executor-6] c.a.a.service.LogGrpcInterceptor         : [namesAndValues={{99,111,110,116,101,110,116,45,116,121,112,101},{97,112,112,108,105,99,97,116,105,111,110,47,103,114,112,99},{117,115,101,114,45,97,103,101,110,116},{103,114,112,99,45,106,97,118,97,45,110,101,116,116,121,47,49,46,51,51,46,48},{97,117,116,104,111,114,105,122,97,116,105,111,110},{66,97,115,105,99,32,100,50,70,114,89,87,53,107,89,87,100,121,99,71,77,54,100,50,70,114,89,87,53,107,89,87,100,121,99,71,77,61},{103,114,112,99,45,97,99,99,101,112,116,45,101,110,99,111,100,105,110,103},{103,122,105,112},<null>,<null>,<null>,<null>,<null>,<null>,<null>,<null>},size=4]
2020-12-02 23:09:18.228  INFO 18044 --- [ault-executor-6] c.a.a.service.LogGrpcInterceptor         : [method=com.aiocdawacs.boot.grpc.lib.GrpcAwacsTokenServiceGrpc$MethodHandlers@15c9b492,serverStreaming=false]

```
### Note - When there is an exception, trace like -
```
2020-12-02 23:08:29.230 ERROR 9548 --- [nio-8181-exec-4] o.a.c.c.C.[.[.[/].[dispatcherServlet]    : Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception

io.grpc.StatusRuntimeException: UNKNOWN
	at io.grpc.stub.ClientCalls.toStatusRuntimeException(ClientCalls.java:262)
	at io.grpc.stub.ClientCalls.getUnchecked(ClientCalls.java:243)
	at io.grpc.stub.ClientCalls.blockingUnaryCall(ClientCalls.java:156)
	at com.aiocdawacs.boot.grpc.lib.GrpcAwacsTokenServiceGrpc$GrpcAwacsTokenServiceBlockingStub.checkToken(GrpcAwacsTokenServiceGrpc.java:169)
	
```	

