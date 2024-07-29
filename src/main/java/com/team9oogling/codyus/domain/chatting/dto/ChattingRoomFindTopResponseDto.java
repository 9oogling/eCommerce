package com.team9oogling.codyus.domain.chatting.dto;

import java.time.LocalDateTime;
import java.util.Optional;

import com.team9oogling.codyus.domain.chatting.entity.ChattingMember;
import com.team9oogling.codyus.domain.chatting.entity.Message;
import com.team9oogling.codyus.domain.user.entity.User;

import lombok.Getter;

@Getter
public class ChattingRoomFindTopResponseDto {
	private final Long chattingRoomId;
	private User user;
	private ChattingMember chattingMember;
	private String message;
	private LocalDateTime createdAt;

	public ChattingRoomFindTopResponseDto(Long chattingRoomId, Optional<Message> optionalMessage) {
		this.chattingRoomId = chattingRoomId;
		if (optionalMessage.isPresent()) {
			Message message = optionalMessage.get();
			this.user = message.getUser();
			this.chattingMember = message.getChattingMember();
			this.message = message.getMessage();
			this.createdAt = message.getCreatedAt();
		}
	}
}
