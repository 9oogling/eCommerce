package com.team9oogling.codyus.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
  UNAUTHORIZED(401, "접근 권한이 없습니다."),
  NOT_FOUND_USER(404, "해당 유저를 찾을 수 없습니다."),
  ALREADY_EXIST_USER(409, "이미 존재하는 이메일입니다."),
  NOT_FOUND_COOKIE(404, "쿠키를 찾을 수 없습니다."),
  INVALID_TOKEN(400, "유효하지 않은 토큰입니다."),
  EXPIRED_TOKEN(400, "만료된 토큰입니다."),
  BAD_REQUEST(400, "잘못된 요청입니다.");


  private final int status;
  private final String message;
}
