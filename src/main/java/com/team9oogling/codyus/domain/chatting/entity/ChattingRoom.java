package com.team9oogling.codyus.domain.chatting.entity;

import java.util.ArrayList;
import java.util.List;

import com.team9oogling.codyus.domain.post.entity.Post;
import com.team9oogling.codyus.global.entity.Timestamped;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ChattingRoom extends Timestamped {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "post_id")
	private Post post;

	@OneToMany(mappedBy = "chattingRoom", cascade = CascadeType.ALL, orphanRemoval = true)
	private final List<ChattingMember> members = new ArrayList<>();

	public ChattingRoom(Post post) {
		this.post = post;
	}

	public void addMember(ChattingMember member) {
		members.add(member);
	}
}
