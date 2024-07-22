package com.team9oogling.codyus.domain.chatting.controller;

import static com.team9oogling.codyus.global.entity.StatusCode.*;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.team9oogling.codyus.domain.chatting.dto.ChattingRoomCreateResponseDto;
import com.team9oogling.codyus.domain.chatting.service.ChattingRoomService;
import com.team9oogling.codyus.domain.user.security.UserDetailsImpl;
import com.team9oogling.codyus.global.dto.DataResponseDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ChattingRoomController {

	private final ChattingRoomService chattingRoomService;

	@PostMapping("/post/{postId}/chattingrooms")
	public ResponseEntity<DataResponseDto<ChattingRoomCreateResponseDto>> createChattingRoom(@PathVariable Long postId,
		@AuthenticationPrincipal
		UserDetailsImpl userDetails) {
		ChattingRoomCreateResponseDto responseDto = chattingRoomService.createChattingRoom(postId,
			userDetails.getUser());
		return ResponseEntity.ok(
			new DataResponseDto<>(
				SUCCESS_CREATE_CHATTINGROOMS.getStatus(),
				SUCCESS_CREATE_CHATTINGROOMS.getMessage(),
				responseDto
			));
	}
}
