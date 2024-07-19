package com.team9oogling.ecommerce.global.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j(topic = "CustomException:: ")
public class CustomException extends RuntimeException {

  private final ErrorCode errorCode;

  public CustomException(ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
    log.info("ExceptionMethod: {}", getExceptionMethod());
    log.info("ErrorCode: {}", errorCode.getMessage());
  }

  public String getExceptionMethod() {
    String className = Thread.currentThread().getStackTrace()[3].getClassName();
    String methodName = Thread.currentThread().getStackTrace()[3].getMethodName();
    return className + "." + methodName;
  }

}