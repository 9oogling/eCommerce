package com.team9oogling.codyus.domain.chatting.entity;

import com.team9oogling.codyus.domain.chatting.dto.ChattingMessageRequestDto;
import com.team9oogling.codyus.domain.user.entity.User;
import com.team9oogling.codyus.global.entity.Timestamped;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Message extends Timestamped {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private User user;

	@ManyToOne
	private ChattingMember chattingMember;

	private String message;

	public Message(User user, ChattingMember member,ChattingMessageRequestDto requestDto) {
		this.user = user;
		this.chattingMember = member;
		this.message = requestDto.getMessage();
	}

}
