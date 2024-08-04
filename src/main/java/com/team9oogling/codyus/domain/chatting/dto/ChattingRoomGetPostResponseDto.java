package com.team9oogling.codyus.domain.chatting.dto;

import com.team9oogling.codyus.domain.post.entity.Post;
import com.team9oogling.codyus.domain.post.entity.PostImage;
import com.team9oogling.codyus.domain.post.entity.PostStatus;

import lombok.Getter;

@Getter
public class ChattingRoomGetPostResponseDto {

	private final Long postId;
	private final String postTitle;
	private final PostStatus postStatus;
	private final int price;
	private final String postFirstUrl;

	public ChattingRoomGetPostResponseDto(Post post, PostImage postImage) {
		this.postId = post.getId();
		this.postTitle = post.getTitle();
		this.postStatus = post.getStatus();
		this.price = post.getPrice();
		this.postFirstUrl = postImage != null ? postImage.getPostUrl() : null;
	}
}
