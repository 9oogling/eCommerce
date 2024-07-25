package com.team9oogling.codyus.domain.chatting.controller;

import static com.team9oogling.codyus.global.entity.StatusCode.*;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.team9oogling.codyus.domain.chatting.dto.ChattingRoomCreateResponseDto;
import com.team9oogling.codyus.domain.chatting.dto.ChattingRoomResponseDto;
import com.team9oogling.codyus.domain.chatting.service.ChattingRoomService;
import com.team9oogling.codyus.global.dto.DataResponseDto;
import com.team9oogling.codyus.global.entity.ResponseFactory;
import com.team9oogling.codyus.global.security.UserDetailsImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ChattingRoomController {

	private final ChattingRoomService chattingRoomService;

	@PostMapping("/posts/{postId}/chattingrooms")
	public ResponseEntity<DataResponseDto<ChattingRoomCreateResponseDto>> createChattingRoom(@PathVariable Long postId,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		ChattingRoomCreateResponseDto responseDto = chattingRoomService.createChattingRoom(postId, userDetails);
		return ResponseFactory.ok(responseDto, SUCCESS_CREATE_CHATTINGROOMS);
	}

	@GetMapping("/my/chattingrooms") // -> /chattingrooms 로 바로 가야할것 같다!
	public ResponseEntity<DataResponseDto<List<ChattingRoomResponseDto>>> chattingRoomsList(
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		List<ChattingRoomResponseDto> responseDtoList = chattingRoomService.chattingRoomList(userDetails);
		return ResponseFactory.ok(responseDtoList, SUCCESS_CREATE_CHATTINGROOMS);
	}
}
