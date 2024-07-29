package com.team9oogling.codyus.domain.chatting.entity;

import com.team9oogling.codyus.domain.user.entity.User;
import com.team9oogling.codyus.global.entity.Timestamped;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class MessageOffset extends Timestamped {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long lastReadMessageId;

	private Long chattingRoomId;

	private Long userId;

	public MessageOffset(User user, ChattingRoom chattingRoom) {
		this.chattingRoomId = chattingRoom.getId();
		this.userId = user.getId();
	}

	public void updateOffset(Message message) {
		this.lastReadMessageId = message.getId();
	}
}
