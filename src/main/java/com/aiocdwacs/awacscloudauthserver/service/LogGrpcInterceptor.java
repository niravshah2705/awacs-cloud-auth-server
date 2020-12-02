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