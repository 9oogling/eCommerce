package com.team9oogling.codyus.domain.chatting.controller;

import static com.team9oogling.codyus.global.entity.StatusCode.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

	@GetMapping("/chattingrooms") // -> 나중에 url 변경 예정
	public ResponseEntity<DataResponseDto<Page<ChattingRoomResponseDto>>> chattingRoomsList(
		@RequestParam(defaultValue = "1") int page,
		@RequestParam(defaultValue = "30") int size,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		Page<ChattingRoomResponseDto> responseDtoList = chattingRoomService.chattingRoomList(userDetails, page, size);
		return ResponseFactory.ok(responseDtoList, SUCCESS_CREATE_CHATTINGROOMS);
	}
}
