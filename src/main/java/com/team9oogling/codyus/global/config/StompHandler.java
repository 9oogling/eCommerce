package com.team9oogling.codyus.global.config;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import com.team9oogling.codyus.global.jwt.JwtProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class StompHandler implements ChannelInterceptor {

	private final JwtProvider jwtProvider;

	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

		if (StompCommand.CONNECT.equals(accessor.getCommand())) {
			log.info("connect" + " 연결");
			try {
				// Authorization 헤더에서 Bearer 토큰을 추출
				String token = accessor.getFirstNativeHeader("Authorization");
				if (token != null && token.startsWith("Bearer ")) {
					log.info("Token => {}", token);

					token = token.substring(7); // 'Bearer ' 제거
					jwtProvider.getClaimsFromToken(token);
				}
			} catch (Exception e) {
				throw new RuntimeException("연결 안됨!!");
			}
		}

		return message;
	}
}
