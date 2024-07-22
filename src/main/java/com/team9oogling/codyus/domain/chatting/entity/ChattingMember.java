package com.team9oogling.codyus.domain.chatting.entity;

import com.team9oogling.codyus.domain.user.entity.User;
import com.team9oogling.codyus.global.entity.Timestamped;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ChattingMember extends Timestamped {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "chatting_room_id")
	private ChattingRoom chattingRoom;

	public ChattingMember(User user, ChattingRoom chattingRoom) {
		this.user = user;
		this.chattingRoom = chattingRoom;
	}
}
