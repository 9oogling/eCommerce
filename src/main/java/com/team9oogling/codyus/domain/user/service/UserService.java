package com.team9oogling.codyus.domain.user.service;

import com.team9oogling.codyus.domain.user.dto.UpdateProfileAddressRequestDto;
import com.team9oogling.codyus.domain.user.dto.UpdateProfilePasswordRequestDto;
import com.team9oogling.codyus.domain.user.dto.UpdateProfilePhoneNumberRequestDto;
import com.team9oogling.codyus.domain.user.dto.UserSignupRequestDto;
import com.team9oogling.codyus.domain.user.dto.UserWithDrawalRequestDto;
import com.team9oogling.codyus.domain.user.entity.User;
import com.team9oogling.codyus.domain.user.entity.UserRole;
import com.team9oogling.codyus.domain.user.entity.UserStatus;
import com.team9oogling.codyus.domain.user.repository.UserRepository;
import com.team9oogling.codyus.global.security.UserDetailsImpl;
import com.team9oogling.codyus.global.entity.BlacklistedToken;
import com.team9oogling.codyus.global.entity.StatusCode;
import com.team9oogling.codyus.global.exception.CustomException;
import com.team9oogling.codyus.global.jwt.JwtProvider;
import com.team9oogling.codyus.global.repository.BlacklistedTokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
  public void signup(UserSignupRequestDto requestDto) {

    userRepository.findByEmail(requestDto.getEmail()).ifPresent((existingUser) -> {
      throw new CustomException(StatusCode.ALREADY_EXIST_USER);
    });

    User user = new User(requestDto, UserRole.USER, UserStatus.ACTIVE);

    if (!requestDto.getPassword().equals(requestDto.getCheckPassword())) {
      throw new CustomException(StatusCode.CHECK_PASSWORD);
    }

    String encrytionPassword = passwordEncoder.encode(requestDto.getPassword());
    user.encryptionPassword(encrytionPassword);

    userRepository.save(user);
  }

  @Transactional
  public void logout(UserDetailsImpl userDetails) {
    String currentToken = jwtProvider.getAccessTokenFromHeader(request);

    // 현재 토큰 블랙리스트에 추가
    addToBlacklist(currentToken);

    User user = userRepository.findByEmail(userDetails.getUsername())
        .orElseThrow(() -> new CustomException(StatusCode.NOT_FOUND_USER));

    userRepository.save(user);

    // 현재 인증 정보 무효화
    SecurityContextHolder.clearContext();
  }

  @Transactional
  public HttpHeaders refreshToken(HttpServletRequest request) {
    String token = getRefreshToken(request);

    // 리프레시 토큰이 존재하지 않거나 빈 문자열인 경우 예외를 발생시킵니다.
    if (!StringUtils.hasText(token)) {
      throw new CustomException(StatusCode.BAD_REQUEST);
    }

    return validateToken(token);
  }

  @Transactional
  public void withdrawal(UserWithDrawalRequestDto requestDto, UserDetailsImpl userDetails) {

    User user = userRepository.findByEmail(userDetails.getUsername())
        .orElseThrow(() -> new CustomException(StatusCode.NOT_FOUND_USER));

    checkPassword(requestDto.getPassword(), user.getPassword());

    if (user.getStatus() == UserStatus.INACTIVE) {
      throw new CustomException(StatusCode.ALREADY_INACTIVE_USER);
    }

    user.updateStatus(UserStatus.INACTIVE);
    user.updateInactivatedAt(new Date()); // 비활성화된 시간 설정
    userRepository.save(user);

    String currentToken = jwtProvider.getAccessTokenFromHeader(request);
    // 현재 토큰 블랙리스트에 추가
    addToBlacklist(currentToken);

    // 현재 인증 정보 무효화
    SecurityContextHolder.clearContext();
  }

  @Transactional
  public void updatePassword(UpdateProfilePasswordRequestDto requestDto,
      UserDetailsImpl userDetails) {

    User user = userRepository.findByEmail(userDetails.getUsername())
        .orElseThrow(() -> new CustomException(StatusCode.NOT_FOUND_USER));

    checkPassword(requestDto.getPassword(), user.getPassword());

    if (requestDto.getNewPassword().equals(requestDto.getPassword())) {
      throw new CustomException(StatusCode.CANNOT_CHANGE_SAME_PASSWORD);
    }

    if (!requestDto.getNewPassword().equals(requestDto.getCheckPassword())) {
      throw new CustomException(StatusCode.NOT_MATCH_PASSWORD);
    }

    String encryptionPassword = passwordEncoder.encode(requestDto.getNewPassword());
    user.encryptionPassword(encryptionPassword);

    userRepository.save(user);
  }

  @Transactional
  public void updateAddress(UpdateProfileAddressRequestDto requestDto,
      UserDetailsImpl userDetails) {

    User user = userRepository.findByEmail(userDetails.getUsername())
        .orElseThrow(() -> new CustomException(StatusCode.NOT_FOUND_USER));

    user.updateAddress(requestDto);

    userRepository.save(user);
  }

  @Transactional
  public void updatePhoneNumber(UpdateProfilePhoneNumberRequestDto requestDto,
      UserDetailsImpl userDetails) {

    User user = userRepository.findByEmail(userDetails.getUsername())
        .orElseThrow(() -> new CustomException(StatusCode.NOT_FOUND_USER));

    user.updatePhoneNumber(requestDto);

    userRepository.save(user);
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

  private String getRefreshToken(HttpServletRequest request) {
    Cookie[] cookies = request.getCookies();

    if (cookies == null) {
      throw new CustomException(StatusCode.NOT_FOUND_COOKIE);
    }

    for (Cookie cookie : cookies) {
      if ("refreshToken".equals(cookie.getName())) {
        return cookie.getValue();
      }
    }

    return null;
  }

  private HttpHeaders validateToken(String token) {

    try {

      Claims info = jwtProvider.getClaimsFromToken(token);
      User user = userRepository.findByEmail(info.getSubject())
          .orElseThrow(() -> new CustomException(StatusCode.NOT_FOUND_USER));

      if (!user.getRefreshToken().equals(token)) {
        throw new CustomException(StatusCode.INVALID_TOKEN);
      }

      return setHeaders(info, user);

    } catch (ExpiredJwtException e) {
      throw new CustomException(StatusCode.EXPIRED_TOKEN);
    } catch (JwtException e) {
      throw new CustomException(StatusCode.INVALID_TOKEN);
    }
  }

  private HttpHeaders setHeaders(Claims info, User user) {

    String accessToken = jwtProvider.createAccessToken(user.getEmail(), user.getRole());
    String refreshToken = jwtProvider.generateToken(user.getEmail(), user.getRole(),
        info.getExpiration());
    ResponseCookie responseCookie = jwtProvider.createCookieRefreshToken(refreshToken,
        info.getExpiration());

    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", "Bearer " + accessToken);
    headers.add(HttpHeaders.SET_COOKIE, responseCookie.toString());

    user.updateRefreshToken(refreshToken);

    return headers;

  }

  private void checkPassword(String inputPassword, String dbPassword) {
    if (!passwordEncoder.matches(inputPassword, dbPassword)) {
      throw new CustomException(StatusCode.CHECK_PASSWORD);
    }
  }

  public User findByUser(User user) {
    return userRepository.findByUser(user)
        .orElseThrow(() -> new CustomException(StatusCode.NOT_FOUND_USER));
  }

}
