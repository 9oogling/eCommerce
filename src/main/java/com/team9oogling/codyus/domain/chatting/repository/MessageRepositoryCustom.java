package com.team9oogling.codyus.domain.chatting.repository;

import com.team9oogling.codyus.domain.chatting.dto.ChattingRoomFindTopResponseDto;
import com.team9oogling.codyus.domain.chatting.entity.ChattingMember;

public interface MessageRepositoryCustom {

	ChattingRoomFindTopResponseDto findTopMessage(ChattingMember chattingMember);
}
