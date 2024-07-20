package com.team9oogling.codyus.global.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class SecurityResponse {

  private final ObjectMapper objectMapper = new ObjectMapper();

  public void sendResponse(
      HttpServletResponse response, HttpStatus status, String message) throws IOException {

    MessageResponseDto responseDto = new MessageResponseDto(status.value(), message);
    String json = objectMapper.writeValueAsString(responseDto);

    response.setStatus(status.value());
    response.setContentType("application/json;charset=UTF-8");
    response.getWriter().write(json);

  }

}
