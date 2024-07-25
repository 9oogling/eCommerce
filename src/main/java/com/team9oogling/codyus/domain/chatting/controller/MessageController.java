package com.team9oogling.codyus.domain.chatting.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import com.team9oogling.codyus.domain.chatting.dto.ChattingMessageRequestDto;
import com.team9oogling.codyus.domain.chatting.service.MessageService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MessageController {

	private final MessageService messageService;

	@MessageMapping("/message")
	public void sendMessage(final ChattingMessageRequestDto requestDto) {
		messageService.sendMessage(requestDto);
	}
}
