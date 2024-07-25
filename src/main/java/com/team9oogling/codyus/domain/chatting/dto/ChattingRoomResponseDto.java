package com.team9oogling.codyus.domain.chatting.dto;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class ChattingRoomResponseDto {

	private final Long chattingRoomId;
	private String senderNickname;
	private String LastMessage;
	private Integer unReadMessageCount;
	private LocalDateTime lastTimestamp;

	public ChattingRoomResponseDto(ChattingRoomFindTopResponseDto responseDto, Integer count) {
		this.chattingRoomId = responseDto.getChattingRoomId();
		if (responseDto.getUser() != null) {
			this.senderNickname = responseDto.getUser().getNickname();
			this.LastMessage = responseDto.getMessage();
			this.unReadMessageCount = count;
			this.lastTimestamp = responseDto.getCreatedAt();
		}

	}
}
