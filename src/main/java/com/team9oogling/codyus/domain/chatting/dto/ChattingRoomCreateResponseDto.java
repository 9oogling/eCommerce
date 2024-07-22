package com.team9oogling.codyus.domain.chatting.dto;


import com.team9oogling.codyus.domain.chatting.entity.ChattingRoom;
import com.team9oogling.codyus.domain.post.entity.Post;

import lombok.Getter;

@Getter
public class ChattingRoomCreateResponseDto {
	private final Long ChattingRoomId;
	private final Long postId;
	private final String title;
//	private final int price;
//	private final String dealStatus;
//	private final String sellingType;

	public ChattingRoomCreateResponseDto(final Long chattingRoomId, final Post post) {
	this.ChattingRoomId = chattingRoomId;
	this.postId = post.getId();
	this.title = post.getTitle();
//	this.price = post.getPrice();
//	this.dealStatus = post.getDealStatus();
//	this.sellingType = post.getSellingType();
	}


}
