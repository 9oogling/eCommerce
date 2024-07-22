package com.team9oogling.codyus.global.exception;

import com.team9oogling.codyus.global.entity.StatusCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j(topic = "CustomException:: ")
public class CustomException extends RuntimeException {

  private final StatusCode statusCode;

  public CustomException(StatusCode statusCode) {
    super(statusCode.getMessage());
    this.statusCode = statusCode;
    log.info("ExceptionMethod: {}", getExceptionMethod());
    log.info("ErrorCode: {}", statusCode.getMessage());
  }

  public String getExceptionMethod() {
    String className = Thread.currentThread().getStackTrace()[3].getClassName();
    String methodName = Thread.currentThread().getStackTrace()[3].getMethodName();
    return className + "." + methodName;
  }

}