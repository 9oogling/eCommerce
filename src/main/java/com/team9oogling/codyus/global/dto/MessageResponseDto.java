package com.team9oogling.codyus.global.dto;

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
}
