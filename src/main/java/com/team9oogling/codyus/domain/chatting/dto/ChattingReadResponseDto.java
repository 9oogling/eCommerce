package com.team9oogling.codyus.domain.chatting.dto;

import java.util.List;

import com.team9oogling.codyus.domain.chatting.entity.ChattingMember;

import lombok.Getter;

@Getter
public class ChattingReadResponseDto {

	private final Long chattingRoomId;
	private final Long messageId;
	private final String partnerEmail;

	public ChattingReadResponseDto(List<ChattingMember> members, Long chattingRoomId, Long messageId) {
		this.chattingRoomId = chattingRoomId;
		this.messageId = messageId;
		this.partnerEmail = members.get(1).getUser().getEmail();
	}
}
