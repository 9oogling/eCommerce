package com.team9oogling.ecommerce.global.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(CustomException.class)
  public ResponseEntity<ExceptionResponseDto> handleInvalidPasswordException(
      HttpServletRequest request, CustomException e) {
    ExceptionResponseDto exceptionResponse = ExceptionResponseDto.builder()
        .message(e.getErrorCode().getMessage())
        .path(request.getRequestURI())
        .build();
    return new ResponseEntity<>(exceptionResponse,
        HttpStatusCode.valueOf(e.getErrorCode().getStatus()));
  }
}
