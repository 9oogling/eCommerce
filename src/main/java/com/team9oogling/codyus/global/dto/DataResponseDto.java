package com.team9oogling.codyus.global.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class DataResponseDto<T> {

  private final HttpStatus statusCode;
  private final String message;
  private final T data;

  @Builder
  public DataResponseDto(HttpStatus status, String message, T data) {
    this.statusCode = status;
    this.message = message;
    this.data = data;
  }
}
