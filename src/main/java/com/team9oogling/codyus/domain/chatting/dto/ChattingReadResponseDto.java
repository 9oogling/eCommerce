package com.team9oogling.codyus.domain.chatting.dto;

import lombok.Getter;

@Getter
public class ChattingReadResponseDto {
	private final Long chattingRoomId;
	private final Long messageId;
	private final String token;

	public ChattingReadResponseDto(Long chattingRoomId, Long messageId, String token) {
		this.chattingRoomId = chattingRoomId;
		this.messageId = messageId;
		this.token = token;
	}
}
