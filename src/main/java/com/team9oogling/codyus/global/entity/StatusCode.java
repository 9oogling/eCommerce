package com.team9oogling.codyus.global.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusCode {
  // 200번대
  SUCCESS_SIGNUP(201, "회원가입에 성공했습니다."),
  SUCCESS_REFRESH_TOKEN(200, "토큰 재발급에 성공했습니다."),
  SUCCESS_WITHDRAWAL(200, "회원탈퇴가 처리 중입니다. '탈퇴 신청일'로부터 30일이 경과되어야 완료됩니다."),
  SUCCESS_UPDATE_PASSWORD(200, "비밀번호 수정에 성공했습니다."),
  SUCCESS_UPDATE_ADDRESS(200, "주소 수정에 성공했습니다."),
  SUCCESS_UPDATE_PHONE_NUMBER(200, "휴대폰 번호 수정에 성공했습니다."),
  SUCCESS_CREATE_CHATTINGROOMS(201, "채팅방 생성에 성공했습니다."),
  SUCCESS_ADD_LIKE(201, "좋아요를 눌렀습니다."),
  SUCCESS_DELETE_LIKE(200, "좋아요가 취소되었습니다."),

  // 400번대
  UNAUTHORIZED(401, "접근 권한이 없습니다."),
  NOT_FOUND_USER(404, "해당 사용자를 찾을 수 없습니다."),
  ALREADY_EXIST_USER(409, "이미 존재하는 이메일입니다."),
  NOT_FOUND_COOKIE(404, "쿠키를 찾을 수 없습니다."),
  INVALID_TOKEN(400, "유효하지 않은 토큰입니다."),
  EXPIRED_TOKEN(400, "만료된 토큰입니다."),
  BAD_REQUEST(400, "잘못된 요청입니다."),
  CHECK_PASSWORD(400, "비밀번호를 확인해주세요."),
  ALREADY_INACTIVE_USER(400, "이미 탈퇴한 사용자입니다."),
  CANNOT_CHANGE_SAME_PASSWORD(400, "현재 비밀번호와 동일한 비밀번호로는 변경할 수 없습니다."),
  NOT_MATCH_PASSWORD(400, "비밀번호가 일치하지 않습니다."),
  CANNOT_LIKE_YOURS(400, "본인이 작성한 게시글에는 좋아요를 할 수 없습니다."),
  NOT_FOUND_LIKE(404, "좋아요를 찾을 수 없습니다."),
  ALREADY_EXIST_LIKE(400, "이미 좋아요를 누른 게시글입니다."),

  // Chatting
  NOT_FOUND_POST(404, "게시물이 없습니다."),
  SAME_USERID_POST_USERID(409, "게시물 작성 시 사용자 ID가 동일합니다."),
  ALREADY_CHATTINGROOMS_EXISTS(409, "이미 채팅방이 존재합니다.");
  private final int status;
  private final String message;
}
