package com.team9oogling.codyus.domain.chatting.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.team9oogling.codyus.domain.chatting.entity.Message;

import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChattingMessageResponseDto {

	private final Long messageId;
	private Long chattingRoomId;
	private final String message;
	private final Long userId;
	private final String nickName;
	private String token;
	private final LocalDateTime timestamp;

	public ChattingMessageResponseDto(final Message message) {
		this.messageId = message.getId();
		this.message = message.getMessage();
		this.userId = message.getUser().getId();
		this.nickName = message.getUser().getNickname();
		this.timestamp = message.getCreatedAt();
	}

	public ChattingMessageResponseDto(final Long chattingRoomId, String token, final Message message) {
		this.messageId = message.getId();
		this.chattingRoomId = chattingRoomId;
		this.message = message.getMessage();
		this.userId = message.getUser().getId();
		this.token = token;
		this.nickName = message.getUser().getNickname();
		this.timestamp = message.getCreatedAt();
	}
}
