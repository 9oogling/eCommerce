package com.team9oogling.codyus.global.dto;

import com.team9oogling.codyus.global.entity.StatusCode;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class MessageResponseDto {

  private final HttpStatus statusCode;
  private final String message;

  @Builder
  public MessageResponseDto(HttpStatus status, String message) {
    this.statusCode = status;
    this.message = message;
  }

  public MessageResponseDto(StatusCode statusCode) {
    this.statusCode = statusCode.getStatus();
    this.message = statusCode.getMessage();
  }
}
