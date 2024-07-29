package com.team9oogling.codyus.domain.chatting.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team9oogling.codyus.domain.chatting.dto.ChattingRoomFindTopResponseDto;
import com.team9oogling.codyus.domain.chatting.entity.ChattingMember;
import com.team9oogling.codyus.domain.chatting.entity.Message;
import com.team9oogling.codyus.domain.chatting.entity.QChattingMember;
import com.team9oogling.codyus.domain.chatting.entity.QMessage;
import com.team9oogling.codyus.domain.user.entity.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MessageRepositoryCustomImpl implements MessageRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public ChattingRoomFindTopResponseDto findTopMessage(ChattingMember chattingMember) {
		QMessage qMessage = QMessage.message1;
		QChattingMember qChattingMember = QChattingMember.chattingMember;

		Long chattingRoomId = chattingMember.getChattingRoom().getId();
		Long currentUserId = chattingMember.getUser().getId();

		Optional<Message> message = Optional.ofNullable(queryFactory
			.selectFrom(qMessage)
			.where(
				qMessage.chattingMember.chattingRoom.id.eq(chattingRoomId)
					.and(
						qChattingMember.user.id.eq(currentUserId)
							.or(qChattingMember.user.id.ne(currentUserId))
					)
			)
			.orderBy(qMessage.createdAt.desc())
			.fetchFirst()
		);
		return new ChattingRoomFindTopResponseDto(chattingRoomId, message);
	}

	@Override
	public List<Message> getMessageList(User user, Long messageId, Long chattingRoomId, Pageable pageable) {
		QMessage qMessage = QMessage.message1;
		QChattingMember qChattingMember = QChattingMember.chattingMember;

		var query = queryFactory
			.selectFrom(qMessage)
			.where(
				qMessage.chattingMember.chattingRoom.id.eq(chattingRoomId)
					.and(
						qChattingMember.user.id.eq(user.getId())
							.or(qChattingMember.user.id.ne(user.getId()))
					)
			);
		if (messageId != null) {
			query = query.where(qMessage.id.lt(messageId));
		}
		return query
			.orderBy(qMessage.createdAt.desc())
			.limit(pageable.getPageSize())
			.fetch();
	}
}
