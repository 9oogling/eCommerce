package com.team9oogling.codyus.domain.chatting.controller;

import static com.team9oogling.codyus.global.entity.StatusCode.*;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.team9oogling.codyus.domain.chatting.dto.ChattingMessageResponseDto;
import com.team9oogling.codyus.domain.chatting.dto.ChattingRoomCreateResponseDto;
import com.team9oogling.codyus.domain.chatting.dto.ChattingRoomGetPostResponseDto;
import com.team9oogling.codyus.domain.chatting.dto.ChattingRoomResponseDto;
import com.team9oogling.codyus.domain.chatting.dto.MessageOffsetResponseDto;
import com.team9oogling.codyus.domain.chatting.service.ChattingService;
import com.team9oogling.codyus.global.dto.DataResponseDto;
import com.team9oogling.codyus.global.dto.MessageResponseDto;
import com.team9oogling.codyus.global.entity.ResponseFactory;
import com.team9oogling.codyus.global.security.UserDetailsImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ChattingRoomController {

	private final ChattingService chattingRoomService;

	@PostMapping("/posts/{postId}/chattingrooms")
	public ResponseEntity<DataResponseDto<ChattingRoomCreateResponseDto>> createChattingRoom(@PathVariable Long postId,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		ChattingRoomCreateResponseDto responseDto = chattingRoomService.createChattingRoom(postId, userDetails);
		return ResponseFactory.ok(responseDto, SUCCESS_CREATE_CHATTINGROOMS);
	}

	@GetMapping("/chattingrooms/{chattingroomsId}")
	public ResponseEntity<DataResponseDto<ChattingRoomResponseDto>> getChattingRoom(@PathVariable Long chattingroomsId,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		ChattingRoomResponseDto responseDto = chattingRoomService.getChattingRoom(chattingroomsId, userDetails);
		return ResponseFactory.ok(responseDto, SUCCESS_GET_CHATTINGROOMS);
	}

	@GetMapping("/chattingrooms") // -> 나중에 url 변경 예정
	public ResponseEntity<DataResponseDto<List<ChattingRoomResponseDto>>> chattingRoomsList(
		@RequestParam(defaultValue = "1") int page,
		@RequestParam(defaultValue = "30") int size,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		List<ChattingRoomResponseDto> responseDtoList = chattingRoomService.chattingRoomList(userDetails, page, size);
		return ResponseFactory.ok(responseDtoList, SUCCESS_GET_CHATTINGROOMS_LIST);
	}

	@GetMapping("/chattingrooms/{chattingroomsId}/messages") // -> 나중에 url 변경 예정
	public ResponseEntity<DataResponseDto<List<ChattingMessageResponseDto>>> chattingRoomMessageList(
		@PathVariable Long chattingroomsId,
		@RequestParam(required = false) Long messageId,
		@RequestParam(defaultValue = "1") int page,
		@RequestParam(defaultValue = "100") int size,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		List<ChattingMessageResponseDto> responseDtoList = chattingRoomService.chattingRoomMessageList(chattingroomsId,
			messageId, page, size, userDetails);
		return ResponseFactory.ok(responseDtoList, SUCCESS_GET_MESSAGE_LIST);
	}

	@GetMapping("/chattingrooms/{chattingroomsId}/post")
	public ResponseEntity<DataResponseDto<ChattingRoomGetPostResponseDto>> getChattingRoomPost(
		@PathVariable Long chattingroomsId,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		ChattingRoomGetPostResponseDto responseDto = chattingRoomService.getChattingRoomPost(chattingroomsId,
			userDetails);
		return ResponseFactory.ok(responseDto, SUCCESS_FIND_CHATTING_ROOM_POST);
	}

	@GetMapping("/chattingrooms/{chattingroomsId}/messagesoffset")
	public ResponseEntity<DataResponseDto<MessageOffsetResponseDto>> getChattingRoomReadMessage(
		@PathVariable Long chattingroomsId,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		var responseDto = chattingRoomService.getChattingRoomReadMessage(chattingroomsId, userDetails);
		return ResponseFactory.ok(responseDto, SUCCESS_FIND_CHATTING_ROOM_MESSAGE_OFFSET);
	}

	@PutMapping("/chattingrooms/{chattingroomsId}/exit")
	public ResponseEntity<MessageResponseDto> chattingRoomExit(@PathVariable Long chattingroomsId,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		chattingRoomService.chattingRoomExit(chattingroomsId, userDetails);
		return ResponseFactory.ok(SUCCESS_CHATTINGROOMS_EXIT);
	}
}
