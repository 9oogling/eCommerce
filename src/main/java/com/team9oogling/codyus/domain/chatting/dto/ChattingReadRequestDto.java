package com.team9oogling.codyus.domain.chatting.dto;

import lombok.Getter;

@Getter
public class ChattingReadRequestDto {
	private Long chattingRoomId;
	private String token;
	private Long messageId;

	public String getToken() {
		if (null != token && token.startsWith("Bearer ")) {
			return token.substring(7);
		}
		return token;
	}
}
