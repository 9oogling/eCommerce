package com.team9oogling.codyus.domain.chatting.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.team9oogling.codyus.domain.chatting.entity.ChattingMember;
import com.team9oogling.codyus.domain.chatting.entity.ChattingRoom;
import com.team9oogling.codyus.domain.user.entity.User;

public interface ChattingMemberRepository extends JpaRepository<ChattingMember, Long> {
	boolean existsByChattingRoomAndUser(ChattingRoom room, User user);
	ChattingMember findByChattingRoom(ChattingRoom room);
}
