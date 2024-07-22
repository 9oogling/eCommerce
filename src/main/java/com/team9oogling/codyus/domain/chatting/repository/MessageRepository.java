package com.team9oogling.codyus.domain.chatting.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.team9oogling.codyus.domain.chatting.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
