package com.team9oogling.codyus.domain.chatting.service;

import static com.team9oogling.codyus.global.entity.StatusCode.*;

import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.team9oogling.codyus.domain.chatting.dto.ChattingMessageRequestDto;
import com.team9oogling.codyus.domain.chatting.dto.ChattingMessageResponseDto;
import com.team9oogling.codyus.domain.chatting.entity.ChattingMember;
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

	private final SimpMessageSendingOperations messagingTemplate;

	@Transactional
	public ChattingMessageResponseDto sendMessage(ChattingMessageRequestDto requestDto) {
		User user = userService.findByToken(requestDto.getToken());

		// 1. 채팅룸 존재  and 게시물이 닫혔는지 확인

		ChattingRoom chattingRoom = chattingRoomRepository.findById(requestDto.getChattingRoomId()).orElseThrow(
			() -> new CustomException(NOT_FOUND_CHATTINGROOMS)
		);
		if (chattingRoom.getPost().getStatus().equals(PostStatus.COMPLETE)) {
			throw new CustomException(ITEM_TRANSACTION_COMPLETED);
			// 추후 변경
		}
		// 2. 멤버 조회
		ChattingMember member = chattingMemberRepository.findByChattingRoomAndUser(chattingRoom, user);

		// 4. 메시지 처리
		Message message = new Message(user, member, requestDto);
		messageRepository.save(message);
		// 생성되있으면 업데이트!!
		updateMessageOffset(message, user, requestDto);

		// 읽음 처리
		ChattingMessageResponseDto responseDto = new ChattingMessageResponseDto(requestDto.getToken(), message);
		/*
		messagingTemplate.convertAndSend: 컨트롤러 메소드 내에서 직접 특정 경로(/topic/{chatRoomId})로 메시지를 전송합니다.
		이를 통해 특정 채팅방에 관련된 클라이언트에게 메시지를 전송할 수 있습니다.
		*/
		messagingTemplate.convertAndSend("/topic/" + requestDto.getChattingRoomId(), responseDto);

		return responseDto;
	}

	public void updateMessageOffset(Message message, User user, ChattingMessageRequestDto requestDto) {
		MessageOffset messageOffset = messageOffsetFindById(requestDto.getChattingRoomId(), user.getId());
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
}
