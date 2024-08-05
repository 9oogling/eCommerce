package com.team9oogling.codyus.domain.chatting.service;

import static com.team9oogling.codyus.global.entity.StatusCode.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.team9oogling.codyus.domain.chatting.dto.ChattingMessageResponseDto;
import com.team9oogling.codyus.domain.chatting.dto.ChattingRoomCreateResponseDto;
import com.team9oogling.codyus.domain.chatting.dto.ChattingRoomFindTopResponseDto;
import com.team9oogling.codyus.domain.chatting.dto.ChattingRoomGetPostResponseDto;
import com.team9oogling.codyus.domain.chatting.dto.ChattingRoomResponseDto;
import com.team9oogling.codyus.domain.chatting.dto.MessageOffsetResponseDto;
import com.team9oogling.codyus.domain.chatting.entity.ChattingMember;
import com.team9oogling.codyus.domain.chatting.entity.ChattingMemberStatus;
import com.team9oogling.codyus.domain.chatting.entity.ChattingRoom;
import com.team9oogling.codyus.domain.chatting.entity.Message;
import com.team9oogling.codyus.domain.chatting.entity.MessageOffset;
import com.team9oogling.codyus.domain.chatting.repository.ChattingMemberRepository;
import com.team9oogling.codyus.domain.chatting.repository.ChattingRoomRepository;
import com.team9oogling.codyus.domain.chatting.repository.MessageOffsetRepository;
import com.team9oogling.codyus.domain.chatting.repository.MessageRepositoryCustomImpl;
import com.team9oogling.codyus.domain.post.entity.Post;
import com.team9oogling.codyus.domain.post.entity.PostImage;
import com.team9oogling.codyus.domain.post.service.PostService;
import com.team9oogling.codyus.domain.user.entity.User;
import com.team9oogling.codyus.global.exception.CustomException;
import com.team9oogling.codyus.global.security.UserDetailsImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChattingService {

	private final ChattingRoomRepository chattingRoomRepository;
	private final ChattingMemberRepository chattingMemberRepository;
	private final MessageOffsetRepository messageOffsetRepository;

	private final MessageRepositoryCustomImpl messageRepositoryCustom;

	private final MessageService messageService;
	private final PostService postService;

	@Transactional
	public ChattingRoomCreateResponseDto createChattingRoom(Long postId, UserDetailsImpl userDetails) {
		User user = userDetails.getUser();
		Post post = postService.findById(postId);

		if (Objects.equals(post.getUser().getId(), user.getId())) {
			throw new CustomException(SAME_USERID_POST_USERID);
		}
		chattingRoomPostAndUser(postId, user);

		ChattingRoom chattingRoom = new ChattingRoom(post);
		chattingRoomRepository.save(chattingRoom);
		ChattingMember chattingMember = new ChattingMember(user, chattingRoom);
		ChattingMember chattingPostMember = new ChattingMember(post.getUser(), chattingRoom);
		chattingRoom.addMember(chattingMember);
		chattingRoom.addMember(chattingPostMember);
		chattingMemberRepository.save(chattingMember);
		chattingMemberRepository.save(chattingPostMember);

		MessageOffset messageOffset = new MessageOffset(user, chattingRoom);
		MessageOffset messageOffsetPostUser = new MessageOffset(post.getUser(), chattingRoom);
		messageOffsetRepository.save(messageOffset);
		messageOffsetRepository.save(messageOffsetPostUser);

		return new ChattingRoomCreateResponseDto(chattingRoom.getId(), post);
	}

	@Transactional(readOnly = true)
	public ChattingRoomResponseDto getChattingRoom(Long chattingRoomId, UserDetailsImpl userDetails) {
		User user = userDetails.getUser();
		ChattingMember chattingMember = findByChattingRoomIdAndUser(chattingRoomId, user);
		return getChattingRoomResponseDto(chattingMember, user);

	}

	@Transactional(readOnly = true)
	public List<ChattingRoomResponseDto> chattingRoomList(UserDetailsImpl userDetails, int page, int size) {
		User user = userDetails.getUser();
		var memberList = chattingMemberRepository.findByUserAndStatus(user, ChattingMemberStatus.ACTIVE);
		List<ChattingRoomResponseDto> responseList = memberList.stream()
			.map(chattingMember -> getChattingRoomResponseDto(chattingMember, user))
			.sorted(Comparator.comparing(ChattingRoomResponseDto::getLastTimestamp,
					Comparator.nullsLast(Comparator.reverseOrder()))
				.thenComparing(ChattingRoomResponseDto::getChattingRoomId))
			.toList();

		Pageable pageable = PageRequest.of(page - 1, size);
		int totalSize = responseList.size();
		int start = (int)pageable.getOffset();
		int end = Math.min(start + pageable.getPageSize(), totalSize);
		if(page > (totalSize + size +1 ) / size) {
			return new ArrayList<>();
		}
		return responseList.subList(start, end);
	}

	@Transactional(readOnly = true)
	public List<ChattingMessageResponseDto> chattingRoomMessageList(Long chattingRoomId,
		Long messageId, int page, int size, UserDetailsImpl userDetails) {
		User user = userDetails.getUser();
		existsChattingRoomUser(chattingRoomId, user);

		Pageable pageable = PageRequest.of(page - 1, size);
		List<Message> messageList = messageRepositoryCustom.getMessageList(user,messageId,
			chattingRoomId, pageable);
		return messageList.stream()
			.map(ChattingMessageResponseDto::new)
			.toList();
	}

	@Transactional(readOnly = true)
	public ChattingRoomGetPostResponseDto getChattingRoomPost(Long chattingroomsId, UserDetailsImpl userDetails) {
		ChattingRoom chattingRoom = messageService.findByChattingRoomId(chattingroomsId);
		Post post = chattingRoom.getPost();
		PostImage postImage = postService.findFirstByPost(post);
		return new ChattingRoomGetPostResponseDto(post, postImage);
	}

	@Transactional
	public void chattingRoomExit(Long chattingRoomId, UserDetailsImpl userDetails) {
		User user = userDetails.getUser();
		existsChattingRoomUser(chattingRoomId, user);
		var chattingMember = findByChattingRoomIdAndUser(chattingRoomId, user);
		chattingMember.updateChattingMemberStatusExit();
	}

	public MessageOffsetResponseDto getChattingRoomReadMessage(Long chattingroomsId, UserDetailsImpl userDetails) {
		MessageOffset messageOffset = messageOffsetRepository.findByChattingRoomIdAndUserIdNot(chattingroomsId, userDetails.getUser().getId()).orElse(null);
		return new MessageOffsetResponseDto(messageOffset);
	}

	private void chattingRoomPostAndUser(Long postId, User user) {
		boolean isChattingRoom = chattingRoomRepository.findByPostId(postId).stream()
			.anyMatch(room -> chattingMemberRepository.existsByChattingRoomAndUser(room, user)
			);
		if (isChattingRoom) {
			throw new CustomException(ALREADY_CHATTINGROOMS_EXISTS);
		}
	}

	public void existsChattingRoomUser(Long chattingRoomId, User user) {
		if (chattingRoomRepository.existsByIdAndMembersUserAndMembersStatus(chattingRoomId, user,
			ChattingMemberStatus.EXIT)) {
			throw new CustomException(NOT_FOUND_CHATTINGROOMS_USER);
		}
	}

	public ChattingMember findByChattingRoomIdAndUser(Long chattingRoomId, User user) {
		return chattingMemberRepository.findByChattingRoomIdAndUser(chattingRoomId, user).orElseThrow(
			() -> new CustomException(NOT_FOUND_CHATTINGROOMS_USER)
		);
	}

	private ChattingRoomResponseDto getChattingRoomResponseDto(ChattingMember chattingMember, User user){
		ChattingRoomFindTopResponseDto response = messageRepositoryCustom.findTopMessage(chattingMember);
		var messageOffset = messageService.messageOffsetFindById(response.getChattingRoomId(), user.getId());
		Integer unReadCount = messageService.unReadCount(response.getChattingMember(), messageOffset.getLastReadMessageId());
		var member = chattingMemberRepository.findByChattingRoom(response.getChattingMember().getChattingRoom());
		return new ChattingRoomResponseDto(response, unReadCount, member, user);
	}
}


