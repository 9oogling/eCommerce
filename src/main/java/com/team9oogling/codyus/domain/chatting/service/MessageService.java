package com.team9oogling.codyus.domain.chatting.service;

import static com.team9oogling.codyus.global.entity.StatusCode.*;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.team9oogling.codyus.domain.chatting.dto.ChattingMessageRequestDto;
import com.team9oogling.codyus.domain.chatting.dto.ChattingMessageResponseDto;
import com.team9oogling.codyus.domain.chatting.dto.ChattingReadRequestDto;
import com.team9oogling.codyus.domain.chatting.dto.ChattingReadResponseDto;
import com.team9oogling.codyus.domain.chatting.entity.ChattingMember;
import com.team9oogling.codyus.domain.chatting.entity.ChattingMemberStatus;
import com.team9oogling.codyus.domain.chatting.entity.ChattingRoom;
import com.team9oogling.codyus.domain.chatting.entity.Message;
import com.team9oogling.codyus.domain.chatting.entity.MessageOffset;
import com.team9oogling.codyus.domain.chatting.repository.ChattingMemberRepository;
import com.team9oogling.codyus.domain.chatting.repository.ChattingRoomRepository;
import com.team9oogling.codyus.domain.chatting.repository.MessageOffsetRepository;
import com.team9oogling.codyus.domain.chatting.repository.MessageRepository;
import com.team9oogling.codyus.domain.post.entity.PostStatus;
import com.team9oogling.codyus.domain.user.entity.User;
import com.team9oogling.codyus.domain.user.service.UserService;
import com.team9oogling.codyus.global.exception.CustomException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageService {

	private final MessageRepository messageRepository;
	private final ChattingMemberRepository chattingMemberRepository;
	private final MessageOffsetRepository messageOffsetRepository;
	private final ChattingRoomRepository chattingRoomRepository;

	private final UserService userService;


	@Transactional
	public ChattingMessageResponseDto sendMessage(ChattingMessageRequestDto requestDto) {
		User user = userService.findByToken(requestDto.getToken());
		// 1. 채팅룸 존재  and 게시물이 닫혔는지 확인
		ChattingRoom chattingRoom =  findByChattingRoomId(requestDto.getChattingRoomId());
		if (chattingRoom.getPost().getStatus().equals(PostStatus.COMPLETE)) {
			throw new CustomException(ITEM_TRANSACTION_COMPLETED);
		}
		// 2. 멤버 조회
		List<ChattingMember> members = isMemberExitedFromChattingRoom(chattingRoom, user);
		// 4. 메시지 처리
		Message message = new Message(user, members.get(0), requestDto);
		messageRepository.save(message);
		// 읽음 처리
		updateMessageOffset(message, user, requestDto.getChattingRoomId());
		return new ChattingMessageResponseDto(requestDto.getChattingRoomId(), requestDto.getToken(), message);
	}

	@Transactional
	public ChattingReadResponseDto readMessage(ChattingReadRequestDto requestDto) {
		User user = userService.findByToken(requestDto.getToken());
		Message message = messageRepository.findById(requestDto.getMessageId()).orElseThrow(() -> new CustomException(NOT_FOUND_MESSAGE));
		updateMessageOffset(message, user, requestDto.getChattingRoomId());
		return new ChattingReadResponseDto(requestDto.getChattingRoomId(), message.getId(), requestDto.getToken());
	}

	public void updateMessageOffset(Message message, User user, Long chattingRoomId) {
		MessageOffset messageOffset = messageOffsetFindById(chattingRoomId, user.getId());
		messageOffset.updateOffset(message);
	}

	public MessageOffset messageOffsetFindById(Long ChattingRoomId, Long userId) {
		return messageOffsetRepository.findByChattingRoomIdAndUserId(ChattingRoomId, userId).orElseThrow(
			() -> new CustomException(NOT_FOUND_MESSAGE_OFFSET)
		);
	}

	public int unReadCount(ChattingMember chattingMember, Long lastMessageId) {
		return messageRepository.countByChattingMemberAndIdGreaterThan(chattingMember, lastMessageId == null ? 0 : lastMessageId);
	}

	public List<ChattingMember> isMemberExitedFromChattingRoom(ChattingRoom chattingRoom, User user) {
		List<ChattingMember> chattingMemberList = chattingMemberRepository.findByChattingRoom(chattingRoom);

		ChattingMember member = null;
		ChattingMember memberPartner = null;

		for (ChattingMember chattingMember : chattingMemberList) {
			if (chattingMember.getStatus() == ChattingMemberStatus.EXIT) {
				throw new CustomException(NOT_FOUND_CHATTING_PARTNER);
			}
			if (chattingMember.getUser().getId().equals(user.getId())) {
				member = chattingMember;
			} else {
				memberPartner = chattingMember;
			}
		}
		return List.of(Objects.requireNonNull(member), Objects.requireNonNull(memberPartner));
	}

	public ChattingRoom findByChattingRoomId(Long chattingRoomId) {
		return chattingRoomRepository.findById(chattingRoomId).orElseThrow(
			() -> new CustomException(NOT_FOUND_CHATTINGROOMS)
		);
	}
}
