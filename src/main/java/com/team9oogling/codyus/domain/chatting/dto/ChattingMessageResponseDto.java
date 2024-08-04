package com.team9oogling.codyus.domain.chatting.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.team9oogling.codyus.domain.chatting.entity.ChattingMember;
import com.team9oogling.codyus.domain.chatting.entity.Message;

import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChattingMessageResponseDto {

	private String userEmail;
	private String partnerEmail;
	private final Long messageId;
	private Long chattingRoomId;
	private final String message;
	private Long userId;
	private final String email;
	private String nickName;
	private final LocalDateTime timestamp;

	public ChattingMessageResponseDto(final Message message) {
		this.messageId = message.getId();
		this.message = message.getMessage();
		this.userId = message.getUser().getId();
		this.email = message.getUser().getEmail();
		this.nickName = message.getUser().getNickname();
		this.timestamp = message.getCreatedAt();
	}

	public ChattingMessageResponseDto(final Long chattingRoomId, final Message message, List<ChattingMember> members) {
		this.userEmail = members.get(0).getUser().getEmail();
		this.partnerEmail = members.get(1).getUser().getEmail();
		this.messageId = message.getId();
		this.chattingRoomId = chattingRoomId;
		this.message = message.getMessage();
		this.email = message.getUser().getEmail();
		this.timestamp = message.getCreatedAt();
	}
}
