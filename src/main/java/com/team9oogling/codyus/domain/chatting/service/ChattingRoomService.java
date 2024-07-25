package com.team9oogling.codyus.domain.chatting.service;

import static com.team9oogling.codyus.global.entity.StatusCode.*;

import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.team9oogling.codyus.domain.chatting.dto.ChattingRoomCreateResponseDto;
import com.team9oogling.codyus.domain.chatting.entity.ChattingMember;
import com.team9oogling.codyus.domain.chatting.entity.ChattingRoom;
import com.team9oogling.codyus.domain.chatting.entity.MessageOffset;
import com.team9oogling.codyus.domain.chatting.repository.ChattingMemberRepository;
import com.team9oogling.codyus.domain.chatting.repository.ChattingRoomRepository;
import com.team9oogling.codyus.domain.chatting.repository.MessageOffsetRepository;
import com.team9oogling.codyus.domain.post.entity.Post;
import com.team9oogling.codyus.domain.post.service.PostService;
import com.team9oogling.codyus.domain.user.entity.User;
import com.team9oogling.codyus.global.exception.CustomException;
import com.team9oogling.codyus.global.security.UserDetailsImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChattingRoomService {

	private final ChattingRoomRepository chattingRoomRepository;
	private final ChattingMemberRepository chattingMemberRepository;
	private final MessageOffsetRepository messageOffsetRepository;

	private final PostService postService;

	@Transactional
	public ChattingRoomCreateResponseDto createChattingRoom(Long postId, UserDetailsImpl userDetails) {
		User user = userDetails.getUser();
		Post post = postService.findById(postId);

		if(Objects.equals(post.getUser().getId(), user.getId())) {
			throw new CustomException(SAME_USERID_POST_USERID);
		}
		ChattingRoomPostAndUser(postId, user);

		ChattingRoom chattingRoom = new ChattingRoom(post);
		chattingRoomRepository.save(chattingRoom);
		ChattingMember chattingMember = new ChattingMember(user, chattingRoom);
		chattingMemberRepository.save(chattingMember);
		chattingRoom.addMember(chattingMember);
		MessageOffset messageOffset = new MessageOffset(user, chattingRoom);
		MessageOffset messageOffsetPostUser = new MessageOffset(post.getUser(), chattingRoom);
		messageOffsetRepository.save(messageOffset);
		messageOffsetRepository.save(messageOffsetPostUser);

		return new ChattingRoomCreateResponseDto(chattingRoom.getId(), post);
	}

	private void ChattingRoomPostAndUser(Long postId, User user) {
		boolean isChattingRoom = chattingRoomRepository.findByPostId(postId)
			.stream()
			.anyMatch(room -> chattingMemberRepository.existsByChattingRoomAndUser(room, user)
			);
		if (isChattingRoom) {
			throw new CustomException(ALREADY_CHATTINGROOMS_EXISTS);
		}
	}

	public ChattingRoom ChattingRoomFindById(Long ChattingRoomId) {
		return chattingRoomRepository.findById(ChattingRoomId).orElseThrow(
			() -> new CustomException(NOT_FOUND_CHATTINGROOMS)
		);
	}
}
