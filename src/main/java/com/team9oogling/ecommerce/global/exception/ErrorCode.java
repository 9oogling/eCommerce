package com.team9oogling.ecommerce.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
  UNAUTHORIZED(401, "접근 권한이 없습니다."),
  USER_NOT_FOUND(404, "해당 유저를 찾을 수 없습니다.");

  private final int status;
  private final String message;
}
