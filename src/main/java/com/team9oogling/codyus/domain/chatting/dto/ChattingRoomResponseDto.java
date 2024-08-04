package com.team9oogling.codyus.domain.chatting.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import com.team9oogling.codyus.domain.chatting.entity.ChattingMember;
import com.team9oogling.codyus.domain.user.entity.User;

import lombok.Getter;

@Getter
public class ChattingRoomResponseDto {

	private final Long chattingRoomId;
	private String senderNickname;
	private String partnerNickname;
	private String LastMessage;
	private Integer unReadMessageCount;
	private LocalDateTime lastTimestamp;

	public ChattingRoomResponseDto(ChattingRoomFindTopResponseDto responseDto, Integer count,
		List<ChattingMember> chattingMembers, User user) {
		this.chattingRoomId = responseDto.getChattingRoomId();
		if (responseDto.getUser() != null) {
			this.senderNickname = responseDto.getUser().getNickname();
			this.LastMessage = responseDto.getMessage();
			this.unReadMessageCount = count;
			this.lastTimestamp = responseDto.getCreatedAt();
		}
		for (var member : chattingMembers) {
			if (!Objects.equals(member.getUser().getId(), user.getId())) {
				this.partnerNickname = member.getUser().getNickname();
				break;
			}
		}
	}
}
