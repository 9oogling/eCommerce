package com.team9oogling.codyus.domain.user.controller;

import com.team9oogling.codyus.domain.user.dto.UpdateProfilePasswordRequestDto;
import com.team9oogling.codyus.domain.user.dto.UserSignupRequestDto;
import com.team9oogling.codyus.domain.user.dto.UserWithDrawalRequestDto;
import com.team9oogling.codyus.domain.user.service.UserService;
import com.team9oogling.codyus.global.dto.MessageResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
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

  @PostMapping("/users/token/refresh")
  public ResponseEntity<MessageResponseDto> refreshToken(HttpServletRequest request) {
    HttpHeaders headers = userService.refreshToken(request);
    MessageResponseDto responseDto = new MessageResponseDto(200, "토큰 재발급에 성공했습니다.");

    return ResponseEntity.status(HttpStatus.OK).headers(headers).body(responseDto);
  }

  @PutMapping("/users/withdrawal")
  public ResponseEntity<MessageResponseDto> withdrawal(@Valid @RequestBody UserWithDrawalRequestDto requestDto) {

    MessageResponseDto responseDto = userService.withdrawal(requestDto);

    return ResponseEntity.status(HttpStatus.OK).body(responseDto);
  }

  @PutMapping("/profile/password/my")
  public ResponseEntity<MessageResponseDto> updatePassword(@Valid @RequestBody
  UpdateProfilePasswordRequestDto requestDto) {

    MessageResponseDto responseDto = userService.updatePassword(requestDto);

    return ResponseEntity.status(HttpStatus.OK).body(responseDto);
  }
}
