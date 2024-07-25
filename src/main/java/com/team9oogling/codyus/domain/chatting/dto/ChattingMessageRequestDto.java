package com.team9oogling.codyus.domain.chatting.dto;

import lombok.Getter;

@Getter
public class ChattingMessageRequestDto {
	private String token;
	private Long chattingRoomId;
	private String message;

	public String getToken(){
		if(null != token && token.startsWith("Bearer ")) {
			return token.substring(7);
		}
		return token;
	}
}
