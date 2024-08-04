package com.team9oogling.codyus.domain.chatting.dto;

import com.team9oogling.codyus.domain.chatting.entity.MessageOffset;

import lombok.Getter;

@Getter
public class MessageOffsetResponseDto {

	private final MessageOffset messageOffset;

	public MessageOffsetResponseDto(MessageOffset messageOffset) {
		this.messageOffset = messageOffset;
	}
}
