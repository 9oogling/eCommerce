package com.team9oogling.codyus.global.entity;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusCode {
  // 200번대
  SUCCESS_SIGNUP(HttpStatus.CREATED, "회원가입에 성공했습니다."),
  SUCCESS_REFRESH_TOKEN(HttpStatus.OK, "토큰 재발급에 성공했습니다."),
  SUCCESS_WITHDRAWAL(HttpStatus.OK, "회원탈퇴가 처리 중입니다. '탈퇴 신청일'로부터 30일이 경과되어야 완료됩니다."),
  SUCCESS_UPDATE_PASSWORD(HttpStatus.OK, "비밀번호 수정에 성공했습니다."),
  SUCCESS_UPDATE_ADDRESS(HttpStatus.OK, "주소 수정에 성공했습니다."),
  SUCCESS_UPDATE_PHONE_NUMBER(HttpStatus.OK, "휴대폰 번호 수정에 성공했습니다."),
  SUCCESS_CREATE_POST(HttpStatus.CREATED, " 게시물 생성에 성공했습니다."),
  SUCCESS_UPDATE_POST(HttpStatus.OK, "게시물 수정에 성공했습니다."),
  SUCCESS_DELETE_POST(HttpStatus.OK, "게시물 삭제에 성공했습니다."),
  SUCCESS_GET_POST(HttpStatus.OK,"게시물 선택 조회에 성공했습니다."),
  SUCCESS_GET_ALLPOST(HttpStatus.OK, "게시물 전체 조회에 성공했습니다."),
  SUCCESS_GET_MYPOST(HttpStatus.OK, "내 게시물 조회에 성공했습니다."),
  SUCCESS_SEARCH_POSTS(HttpStatus.OK,"게시물 검색에 성공했습니다"),
  SUCCESS_CREATE_CHATTINGROOMS(HttpStatus.CREATED, "채팅방 생성에 성공했습니다."),
  SUCCESS_GET_CHATTINGROOMS(HttpStatus.OK, "채팅방 조회에 성공했습니다."),
  SUCCESS_GET_CHATTINGROOMS_LIST(HttpStatus.OK, "채팅방 목록 조회에 성공했습니다."),
  SUCCESS_ADD_LIKE(HttpStatus.CREATED, "좋아요를 눌렀습니다."),
  SUCCESS_DELETE_LIKE(HttpStatus.OK, "좋아요가 취소되었습니다."),
  SUCCESS_SEND_MESSAGE(HttpStatus.OK, "메세지 전송에 성공했습니다."),
  SUCCESS_GET_MESSAGE_LIST(HttpStatus.OK,"메세지 목록 조회에 성공했습니다."),
  SUCCESS_CHATTINGROOMS_EXIT(HttpStatus.OK, "채팅방 나가기에 성공했습니다."),


  // 400번대
  UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "접근 권한이 없습니다."),
  NOT_FOUND_USER(HttpStatus.NOT_FOUND, "해당 사용자를 찾을 수 없습니다."),
  ALREADY_EXIST_USER(HttpStatus.CONFLICT, "이미 존재하는 이메일입니다."),
  NOT_FOUND_COOKIE(HttpStatus.NOT_FOUND, "쿠키를 찾을 수 없습니다."),
  INVALID_TOKEN(HttpStatus.BAD_REQUEST, "유효하지 않은 토큰입니다."),
  EXPIRED_TOKEN(HttpStatus.BAD_REQUEST, "만료된 토큰입니다."),
  BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
  CHECK_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호를 확인해주세요."),
  ALREADY_INACTIVE_USER(HttpStatus.BAD_REQUEST, "이미 탈퇴한 사용자입니다."),
  CANNOT_CHANGE_SAME_PASSWORD(HttpStatus.BAD_REQUEST, "현재 비밀번호와 동일한 비밀번호로는 변경할 수 없습니다."),
  NOT_MATCH_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
  NOT_FOUND_SEARCH(HttpStatus.NOT_FOUND, "검색 결과가 없습니다."),
  INVALID_SEARCH_QUERY(HttpStatus.NOT_FOUND, "검색어와 검색 타입이 비어있습니다"),
  NOT_FOUND_DATA(HttpStatus.NOT_FOUND, "데이터를 전부 채워주세요."),
  CANNOT_LIKE_YOURS(HttpStatus.BAD_REQUEST, "본인이 작성한 게시글에는 좋아요를 할 수 없습니다."),
  NOT_FOUND_LIKE(HttpStatus.NOT_FOUND, "좋아요를 찾을 수 없습니다."),
  ALREADY_EXIST_LIKE(HttpStatus.BAD_REQUEST, "이미 좋아요를 누른 게시글입니다."),
  NOT_FOUND_CATEGORY(HttpStatus.NOT_FOUND, "해당 카테고리를 찾을 수 없습니다."),

  // Chatting
  NOT_FOUND_POST(HttpStatus.NOT_FOUND, "게시물이 없습니다."),
  NOT_FOUND_CHATTINGROOMS(HttpStatus.NOT_FOUND, "채팅방 존재하지 않습니다."),
  ITEM_TRANSACTION_COMPLETED(HttpStatus.CONFLICT, "이미 거래가 완료되었습니다."),
  SAME_USERID_POST_USERID(HttpStatus.CONFLICT, "게시물 작성 시 사용자 ID가 동일합니다."),
  NOT_FOUND_MESSAGE(HttpStatus.NOT_FOUND,"메세지가 존재하지 않습니다"),
  NOT_FOUND_MESSAGE_OFFSET(HttpStatus.NOT_FOUND, "메시지오프셋이 존재하지 않습니다."),
  NOT_FOUND_CHATTINGROOMS_USER(HttpStatus.NOT_FOUND, "채팅방에 유저가 존재하지 않습니다"),
  NOT_FOUND_CHATTING_PARTNER(HttpStatus.NOT_FOUND, "채팅방 상대가 나갔습니다."),
  ALREADY_CHATTINGROOMS_EXISTS(HttpStatus.CONFLICT, "이미 채팅방이 존재합니다.");


  private final HttpStatus status;
  private final String message;
}
