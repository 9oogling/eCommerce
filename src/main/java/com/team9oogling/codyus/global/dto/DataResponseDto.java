package com.team9oogling.codyus.global.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class DataResponseDto<T> {

  private final Integer statusCode;
  private final String message;
  private final T data;

  @Builder
  public DataResponseDto(Integer status, String message, T data) {
    this.statusCode = status;
    this.message = message;
    this.data = data;
  }
}
