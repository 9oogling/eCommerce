package com.team9oogling.codyus.domain.chatting.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.team9oogling.codyus.domain.chatting.entity.ChattingMemberStatus;
import com.team9oogling.codyus.domain.chatting.entity.ChattingRoom;
import com.team9oogling.codyus.domain.user.entity.User;

public interface ChattingRoomRepository extends JpaRepository<ChattingRoom, Long> {
	List<ChattingRoom> findByPostId(Long postId);

	boolean existsByIdAndMembersUserAndMembersStatus(Long chattingRoomId, User user, ChattingMemberStatus status);
}
