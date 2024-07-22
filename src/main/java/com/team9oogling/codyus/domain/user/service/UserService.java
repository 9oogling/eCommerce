package com.team9oogling.codyus.domain.user.service;

import com.team9oogling.codyus.domain.user.dto.UserSignupRequestDto;
import com.team9oogling.codyus.domain.user.entity.User;
import com.team9oogling.codyus.domain.user.entity.UserRole;
import com.team9oogling.codyus.domain.user.entity.UserStatus;
import com.team9oogling.codyus.domain.user.repository.UserRepository;
import com.team9oogling.codyus.domain.user.security.UserDetailsImpl;
import com.team9oogling.codyus.global.dto.MessageResponseDto;
import com.team9oogling.codyus.global.entity.BlacklistedToken;
import com.team9oogling.codyus.global.exception.CustomException;
import com.team9oogling.codyus.global.exception.ErrorCode;
import com.team9oogling.codyus.global.jwt.JwtProvider;
import com.team9oogling.codyus.global.repository.BlacklistedTokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import java.time.Duration;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtProvider jwtProvider;
  private final BlacklistedTokenRepository blacklistedTokenRepository;
  private final HttpServletRequest request;

  @Value("${ACCESS_TOKEN_EXPIRATION}")
  private long accessTokenExpiration;

  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
      JwtProvider jwtProvider, BlacklistedTokenRepository blacklistedTokenRepository,
      HttpServletRequest request) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtProvider = jwtProvider;
    this.blacklistedTokenRepository = blacklistedTokenRepository;
    this.request = request;
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

  @Transactional
  public void logout() {
    UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();

    User user = userRepository.findByemail(userDetails.getUsername())
        .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

    String currentToken = jwtProvider.getAccessTokenFromHeader(request);

    // 현재 토큰 블랙리스트에 추가
    addToBlacklist(currentToken);

    userRepository.save(user);

    // 현재 인증 정보 무효화
    SecurityContextHolder.clearContext();
  }

  private void addToBlacklist(String token) {
    LocalDateTime expiryDate = LocalDateTime.now().plus(Duration.ofMillis(accessTokenExpiration));
    BlacklistedToken blacklistedToken = new BlacklistedToken();
    blacklistedToken.setToken(token);
    blacklistedToken.setExpiryDate(expiryDate);
    blacklistedTokenRepository.save(blacklistedToken);
  }

  public boolean isTokenBlacklisted(String token) {
    return blacklistedTokenRepository.existsByToken(token);
  }
}
