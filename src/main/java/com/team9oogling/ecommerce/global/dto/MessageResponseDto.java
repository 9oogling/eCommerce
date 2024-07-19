package com.team9oogling.ecommerce.global.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MessageResponseDto<T> {

  private final Integer statusCode;
  private final String message;

  @Builder
  public MessageResponseDto(Integer status, String message, T data) {
    this.statusCode = status;
    this.message = message;
  }
}
