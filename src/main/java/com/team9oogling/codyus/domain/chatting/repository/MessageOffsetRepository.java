package com.team9oogling.codyus.domain.chatting.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.team9oogling.codyus.domain.chatting.entity.MessageOffset;

public interface MessageOffsetRepository extends JpaRepository<MessageOffset, Long> {
	Optional<MessageOffset> findByChattingRoomIdAndUserId(Long chattingRoomId, Long userId);
}
