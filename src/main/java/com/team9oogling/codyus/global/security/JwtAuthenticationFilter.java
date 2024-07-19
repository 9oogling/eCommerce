package com.team9oogling.codyus.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team9oogling.codyus.domain.user.dto.UserLoginRequestDto;
import com.team9oogling.codyus.domain.user.entity.User;
import com.team9oogling.codyus.domain.user.repository.UserRepository;
import com.team9oogling.codyus.domain.user.security.UserDetailsImpl;
import com.team9oogling.codyus.global.dto.SecurityResponse;
import com.team9oogling.codyus.global.jwt.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private final JwtProvider jwtProvider;
  private final UserRepository userRepository;
  private final SecurityResponse securityResponse;

  public JwtAuthenticationFilter(JwtProvider jwtProvider, UserRepository userRepository,
      SecurityResponse securityResponse) {
    this.jwtProvider = jwtProvider;
    this.userRepository = userRepository;
    this.securityResponse = securityResponse;
    setFilterProcessesUrl("/users/login");
  }

  @Override
  public Authentication attemptAuthentication(
      HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

    if (!request.getMethod().equals("POST")) {
      try {
        securityResponse.sendResponse(response, HttpStatus.BAD_REQUEST, "잘못된 http 요청입니다.");
      } catch (IOException e) {
        throw new RuntimeException(e);
      }

      return null;
    }

    try {

      UserLoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(),
          UserLoginRequestDto.class);

      return getAuthenticationManager().authenticate(
          new UsernamePasswordAuthenticationToken(
              requestDto.getEmail(),
              requestDto.getPassword(),
              null)
      );

    } catch (IOException e) {
      throw new RuntimeException(e.getMessage());
    }

  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain, Authentication authResult) throws IOException {

    UserDetailsImpl userDetails = (UserDetailsImpl) authResult.getPrincipal();
    String userId = userDetails.getUsername();
    String role = userDetails.getAuthorities().iterator().next().getAuthority();

    Optional<User> user = userRepository.findByemail(userId);

    if (user.isEmpty()) {
      securityResponse.sendResponse(response, HttpStatus.BAD_REQUEST, "아이디, 비밀번호를 확인해주세요.");

      return;
    }

    String accessToken = jwtProvider.createAccessToken(userId, role);
    String refreshToken = jwtProvider.createRefreshToken(userId, role);

    sendLoginResponse(response, user.get(), accessToken, refreshToken);

  }

  @Override
  protected void unsuccessfulAuthentication(HttpServletRequest request,
      HttpServletResponse response, AuthenticationException failed) throws IOException {
    securityResponse.sendResponse(response, HttpStatus.UNAUTHORIZED, "아이디, 비밀번호를 확인해주세요.");
  }

  private void sendLoginResponse(HttpServletResponse response, User user, String accessToken,
      String refreshToken) throws IOException {

    user.updateRefreshToken(refreshToken);
    userRepository.save(user);

    ResponseCookie refreshTokenCookie = jwtProvider.createCookieRefreshToken(refreshToken);
    response.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
    response.addHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());
    securityResponse.sendResponse(response, HttpStatus.OK, "로그인에 성공했습니다.");

  }

}