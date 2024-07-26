package com.team9oogling.codyus.domain.chatting.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.team9oogling.codyus.domain.chatting.dto.ChattingRoomFindTopResponseDto;
import com.team9oogling.codyus.domain.chatting.entity.ChattingMember;
import com.team9oogling.codyus.domain.chatting.entity.Message;
import com.team9oogling.codyus.domain.user.entity.User;

public interface MessageRepositoryCustom {

	ChattingRoomFindTopResponseDto findTopMessage(ChattingMember chattingMember);

	List<Message> getMessageList(User user, Long messageId, Long chattingRoomId, Pageable pageable);

}
