package com.team9oogling.codyus.domain.chatting.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.team9oogling.codyus.domain.chatting.entity.ChattingMember;
import com.team9oogling.codyus.domain.chatting.entity.ChattingRoom;
import com.team9oogling.codyus.domain.user.entity.User;

public interface ChattingMemberRepository extends JpaRepository<ChattingMember, Long> {
	boolean existsByChattingRoomAndUser(ChattingRoom room, User user);

	ChattingMember findByChattingRoomAndUser(ChattingRoom room, User user);

	List<ChattingMember> findByUser(User user);
}
