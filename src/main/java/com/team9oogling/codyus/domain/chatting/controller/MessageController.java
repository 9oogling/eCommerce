package com.team9oogling.codyus.domain.chatting.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import com.team9oogling.codyus.domain.chatting.dto.ChattingMessageRequestDto;
import com.team9oogling.codyus.domain.chatting.dto.ChattingMessageResponseDto;
import com.team9oogling.codyus.domain.chatting.dto.ChattingReadRequestDto;
import com.team9oogling.codyus.domain.chatting.dto.ChattingReadResponseDto;
import com.team9oogling.codyus.domain.chatting.service.MessageService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MessageController {

	private final MessageService messageService;

	private final SimpMessageSendingOperations messagingTemplate;

	@MessageMapping("/message")
	public void sendMessage(final ChattingMessageRequestDto requestDto) {
		ChattingMessageResponseDto responseDto = messageService.sendMessage(requestDto);
		/*
		messagingTemplate.convertAndSend: 컨트롤러 메소드 내에서 직접 특정 경로(/topic/{chatRoomId})로 메시지를 전송합니다.
		이를 통해 특정 채팅방에 관련된 클라이언트에게 메시지를 전송할 수 있습니다.
		*/
		messagingTemplate.convertAndSend("/topic/" + requestDto.getChattingRoomId(), responseDto);
		messagingTemplate.convertAndSend("/topic/user/" + responseDto.getPartnerEmail(), responseDto);
		messagingTemplate.convertAndSend("/topic/user/" + responseDto.getUserEmail(), responseDto);
	}

	@MessageMapping("/chattingrooms/read")
	public void readMessage(final ChattingReadRequestDto requestDto) {
		ChattingReadResponseDto responseDto = messageService.readMessage(requestDto);
		messagingTemplate.convertAndSend("/topic/" + responseDto.getChattingRoomId() + "/read/" + responseDto.getPartnerEmail(), responseDto);
	}
}
