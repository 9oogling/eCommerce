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
	private final User user;
	private final ChattingMember chattingMember;
	private final String message;
	private final LocalDateTime createdAt;

	public ChattingRoomFindTopResponseDto(ChattingMember chattingMember, Optional<Message> optionalMessage) {
		this.chattingRoomId = chattingMember.getChattingRoom().getId();
		if (optionalMessage.isPresent()) {
			Message message = optionalMessage.get();
			this.user = message.getUser();
			this.chattingMember = message.getChattingMember();
			this.message = message.getMessage();
			this.createdAt = message.getCreatedAt();
		} else {
			this.user = null;
			this.chattingMember = chattingMember;
			this.message = null;
			this.createdAt = null;
		}
	}
}
