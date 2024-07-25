package com.team9oogling.codyus.domain.chatting.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class webSocketConfig implements WebSocketMessageBrokerConfigurer {

	private final StompHandler stompHandler;

	@Override
	public void registerStompEndpoints(final StompEndpointRegistry registry) {
		// STOP 엔드포인트 등록 클라이언트는 이 엔드 포인트를 통해 서버와 연결
		registry.addEndpoint("/chatting").setAllowedOrigins("http://localhost:8080").withSockJS();
	}

	@Override
	public void configureMessageBroker(final MessageBrokerRegistry config) {
		/* 메세지 브로커를 설정합니다. 클라이언트가 메시지를 구독할 수 있는 엔드포인트를 정의합니다. */
		config.enableSimpleBroker("/topic"); // 클라이언트가 구독할 브로커 엔드포인트
		config.setApplicationDestinationPrefixes("/app"); // 클라이언트가 메시지를 전송할 때 사용하는 접두사
	}

	@Override
	public void configureClientInboundChannel(ChannelRegistration registration){
		registration.interceptors(stompHandler);
	}
}
