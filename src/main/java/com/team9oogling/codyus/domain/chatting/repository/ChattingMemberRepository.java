package com.team9oogling.codyus.domain.chatting.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.team9oogling.codyus.domain.chatting.entity.ChattingMember;
import com.team9oogling.codyus.domain.chatting.entity.ChattingMemberStatus;
import com.team9oogling.codyus.domain.chatting.entity.ChattingRoom;
import com.team9oogling.codyus.domain.user.entity.User;

public interface ChattingMemberRepository extends JpaRepository<ChattingMember, Long> {
	boolean existsByChattingRoomAndUser(ChattingRoom room, User user);

	Optional<ChattingMember> findByChattingRoomIdAndUser(Long chattingRoomId, User user);

	List<ChattingMember> findByUserAndStatus(User user, ChattingMemberStatus status);

	List<ChattingMember> findByChattingRoom(ChattingRoom chattingRoom);
}
