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
		String resourceId  = (String)response.get(FrameworkParams.aud.name());
		String jti		   = (String)response.get(FrameworkParams.jti.name());	// UUID.randomUUID().toString();
		String clientId    = (String)response.get(FrameworkParams.client_id.name());
		String userName    = (String)response.get(FrameworkParams.user_name.name());
				
		Boolean isActive   = null == response.get(FrameworkParams.active.name()) ? Boolean.TRUE: Boolean.FALSE;

		CheckTokenReply reply = CheckTokenReply.newBuilder()
				.setApproved(isActive)
				.setAud(resourceId)
				.setJti(jti)
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
