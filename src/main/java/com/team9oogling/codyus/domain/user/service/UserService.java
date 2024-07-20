package com.team9oogling.codyus.domain.user.service;

import com.team9oogling.codyus.domain.user.dto.UserSignupRequestDto;
import com.team9oogling.codyus.domain.user.entity.User;
import com.team9oogling.codyus.domain.user.entity.UserRole;
import com.team9oogling.codyus.domain.user.entity.UserStatus;
import com.team9oogling.codyus.domain.user.repository.UserRepository;
import com.team9oogling.codyus.global.dto.MessageResponseDto;
import com.team9oogling.codyus.global.exception.CustomException;
import com.team9oogling.codyus.global.exception.ErrorCode;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Transactional
  public MessageResponseDto signup(UserSignupRequestDto requestDto) {

    userRepository.findByemail(requestDto.getEmail()).ifPresent((existingUser) -> {
      throw new CustomException(ErrorCode.ALREADY_EXIST_USER);
    });

    User user = new User(requestDto, UserRole.USER, UserStatus.ACTIVE);

    String encrytionPassword = passwordEncoder.encode(requestDto.getPassword());
    user.encryptionPassword(encrytionPassword);

    userRepository.save(user);

    return new MessageResponseDto(201, "회원가입에 성공했습니다.");
  }

}
