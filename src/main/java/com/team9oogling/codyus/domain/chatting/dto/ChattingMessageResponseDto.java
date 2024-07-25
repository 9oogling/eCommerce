package com.team9oogling.codyus.domain.chatting.dto;

import java.time.LocalDateTime;

import com.team9oogling.codyus.domain.chatting.entity.Message;

import lombok.Getter;

@Getter
public class ChattingMessageResponseDto {

	private final Long messageId;
	private final String message;
	private final String nickName;
	private final String token;
	private final LocalDateTime timestamp;

	public ChattingMessageResponseDto(String token, final Message message) {
		this.messageId = message.getId();
		this.message = message.getMessage();
		this.token = token;
		this.nickName = message.getUser().getNickname();
		this.timestamp = message.getCreatedAt();
	}
}
