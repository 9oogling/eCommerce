package com.team9oogling.ecommerce.global.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class ExceptionResponseDto {

  String message;
  String path;
}
