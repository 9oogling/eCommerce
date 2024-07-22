package com.team9oogling.codyus.domain.chatting.service;

import static com.team9oogling.codyus.global.entity.StatusCode.*;

import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.team9oogling.codyus.domain.chatting.dto.ChattingRoomCreateResponseDto;
import com.team9oogling.codyus.domain.chatting.entity.ChattingMember;
import com.team9oogling.codyus.domain.chatting.entity.ChattingRoom;
import com.team9oogling.codyus.domain.chatting.repository.ChattingMemberRepository;
import com.team9oogling.codyus.domain.chatting.repository.ChattingRoomRepository;
import com.team9oogling.codyus.domain.post.entity.Post;
import com.team9oogling.codyus.domain.post.repository.PostRepository;
import com.team9oogling.codyus.domain.user.entity.User;
import com.team9oogling.codyus.global.exception.CustomException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChattingRoomService {

	private final ChattingRoomRepository chattingRoomRepository;
	private final ChattingMemberRepository chattingMemberRepository;

	private final PostRepository postRepository;
	@Transactional
	public ChattingRoomCreateResponseDto createChattingRoom(Long postId, User user) {
		Post post = postRepository.findById(postId).orElse(null); // -> PostService.PostfindById 로 바꿀예정
		if(post == null) {
			throw new CustomException(NOT_FOUND_POST);
		}

		if(Objects.equals(post.getUser().getId(), user.getId())) {
			throw new CustomException(SAME_USERID_POST_USERID);
		}
		ChattingRoomPostAndUser(postId, user);

		ChattingRoom chattingRoom = new ChattingRoom(post);
		ChattingMember chattingMember = new ChattingMember(user, chattingRoom);

		chattingRoomRepository.save(chattingRoom);
		chattingRoom.addMember(chattingMember);
		chattingMemberRepository.save(chattingMember);

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
}
