package com.team9oogling.codyus.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
  UNAUTHORIZED(401, "접근 권한이 없습니다."),
  NOT_FOUND_USER(404, "해당 유저를 찾을 수 없습니다."),
  ALREADY_EXIST_USER(409, "이미 존재하는 이메일입니다.");


  private final int status;
  private final String message;
}
