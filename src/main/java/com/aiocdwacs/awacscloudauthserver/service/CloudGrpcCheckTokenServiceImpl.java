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

	private Authority buildAuthority(String[] authorityArray) {

		Authority.Builder authorities = Authority.newBuilder();
		for (String authority : authorityArray) {
			authorities.addAuthority(authority);
		}
		return authorities.build();
	}

}
