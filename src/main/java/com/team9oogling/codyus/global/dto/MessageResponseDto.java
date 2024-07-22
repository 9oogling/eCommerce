package com.team9oogling.codyus.global.dto;

import com.team9oogling.codyus.global.entity.StatusCode;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MessageResponseDto {

  private final Integer statusCode;
  private final String message;

  @Builder
  public MessageResponseDto(Integer status, String message) {
    this.statusCode = status;
    this.message = message;
  }

  public MessageResponseDto(StatusCode statusCode) {
    this.statusCode = statusCode.getStatus();
    this.message = statusCode.getMessage();
  }
}
