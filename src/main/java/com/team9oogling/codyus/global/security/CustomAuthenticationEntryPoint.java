package com.team9oogling.codyus.global.security;

import com.team9oogling.codyus.global.dto.SecurityResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

  private final SecurityResponse securityResponse;

  public CustomAuthenticationEntryPoint(SecurityResponse securityResponse) {
    this.securityResponse = securityResponse;
  }

  // 인증되지 않은 요청을 처리하는 로직
  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
    securityResponse.sendResponse(response, HttpStatus.UNAUTHORIZED, "로그인 후 이용해 주세요.");
  }

}