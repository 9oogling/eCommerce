package com.team9oogling.codyus.global.entity;

import com.team9oogling.codyus.global.dto.DataResponseDto;
import com.team9oogling.codyus.global.dto.MessageResponseDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseFactory {

  public static <T> ResponseEntity<DataResponseDto<T>> ok(T data, StatusCode statusCode) {
    DataResponseDto<T> responseDto = new DataResponseDto<>(statusCode.getStatus(), statusCode.getMessage(), data);
    return ResponseEntity.ok(responseDto);
  }

  public static ResponseEntity<MessageResponseDto> ok(StatusCode statusCode) {
    MessageResponseDto responseDto = new MessageResponseDto(statusCode.getStatus(), statusCode.getMessage());
    return ResponseEntity.ok(responseDto);
  }

  // 리프레시 토큰 리턴
  public static ResponseEntity<MessageResponseDto> ok(StatusCode statusCode, HttpHeaders headers) {
    MessageResponseDto responseDto = new MessageResponseDto(statusCode.getStatus(),
        statusCode.getMessage());
    return ResponseEntity.status(HttpStatus.OK).headers(headers).body(responseDto);
  }

  public static <T> ResponseEntity<DataResponseDto<T>> created(T data, StatusCode statusCode) {
    DataResponseDto<T> responseDto = new DataResponseDto<>(statusCode.getStatus(), statusCode.getMessage(), data);
    return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
  }

  public static ResponseEntity<MessageResponseDto> created(StatusCode statusCode) {
    MessageResponseDto responseDto = new MessageResponseDto(statusCode.getStatus(), statusCode.getMessage());
    return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
  }

  public static ResponseEntity<?> noContent() {
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  public static ResponseEntity<MessageResponseDto> badRequest(StatusCode statusCode) {
    MessageResponseDto responseDto = new MessageResponseDto(statusCode.getStatus(), statusCode.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
  }

  public static ResponseEntity<MessageResponseDto> notFound(StatusCode statusCode) {
    MessageResponseDto responseDto = new MessageResponseDto(statusCode.getStatus(), statusCode.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDto);
  }

  public static ResponseEntity<MessageResponseDto> internalServerError(StatusCode statusCode) {
    MessageResponseDto responseDto = new MessageResponseDto(statusCode.getStatus(), statusCode.getMessage());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
  }

  public static ResponseEntity<MessageResponseDto> conflictError(StatusCode statusCode) {
    MessageResponseDto responseDto = new MessageResponseDto(statusCode.getStatus(), statusCode.getMessage());
    return ResponseEntity.status(HttpStatus.CONFLICT).body(responseDto);
  }

  public static ResponseEntity<MessageResponseDto> authorizedError(StatusCode statusCode) {
    MessageResponseDto responseDto = new MessageResponseDto(statusCode.getStatus(), statusCode.getMessage());
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseDto);
  }

}
