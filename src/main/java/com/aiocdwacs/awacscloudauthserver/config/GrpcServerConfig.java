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
	
//	@Bean
//	public HealthBlockingStub health() {
//		return HealthGrpc.newBlockingStub(InProcessChannelBuilder.forName(InProcessServerBuilder.generateName()).directExecutor().build());
//	}
	
}
