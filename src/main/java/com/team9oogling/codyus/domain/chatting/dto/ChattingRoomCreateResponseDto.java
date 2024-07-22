package com.team9oogling.codyus.domain.chatting.dto;

import com.team9oogling.codyus.domain.post.entity.Post;

import lombok.Getter;

@Getter
public class ChattingRoomCreateResponseDto {
	private final Long id;
	private final Post post;

	public ChattingRoomCreateResponseDto(final Long id, final Post post) {
		this.id = id;
		this.post = post;
	}
}
