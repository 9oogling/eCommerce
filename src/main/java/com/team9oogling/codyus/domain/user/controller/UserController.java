package com.team9oogling.codyus.domain.user.controller;

import com.team9oogling.codyus.domain.user.dto.UserSignupRequestDto;
import com.team9oogling.codyus.domain.user.service.UserService;
import com.team9oogling.codyus.global.dto.MessageResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/users/signup")
  public ResponseEntity<MessageResponseDto> signup(@Valid @RequestBody UserSignupRequestDto requestDto) {

    MessageResponseDto responseDto = userService.signup(requestDto);

    return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
  }

  @PostMapping("/users/logout")
  public ResponseEntity<?> logout() {
    userService.logout();

    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
