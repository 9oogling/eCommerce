package com.team9oogling.codyus.global.jwt;

import com.team9oogling.codyus.domain.user.entity.UserRole;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j(topic = "Global Exception")
@Component
public class JwtProvider {
  @Value("${jwt.secret.key}")
  private String secretKey;

  @Value("${jwt.access.token.expiration}")
  private long ACCESS_TOKEN_EXPIRATION;

  @Value("${jwt.refresh.token.expiration}")
  private long REFRESH_TOKEN_EXPIRATION;

  private static final String BEARER_PREFIX = "Bearer ";
  private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
  private Key key;

  @PostConstruct
  public void init() {

    byte[] bytes = Base64.getDecoder().decode(secretKey);
    key = Keys.hmacShaKeyFor(bytes);

  }

  public String generateToken(String email, UserRole role, Date expirationDate) {
    return Jwts.builder()
        .setSubject(email)
        .claim("auth", role.name())
        .setExpiration(expirationDate)
        .setIssuedAt(new Date())
        .signWith(key, signatureAlgorithm)
        .compact();
  }

  private Date generateExpirationDate(long ms) {

    Date date = new Date();

    return new Date(date.getTime() + ms);
  }

  public String createAccessToken(String email, UserRole role) {

    Date expirationDate = generateExpirationDate(ACCESS_TOKEN_EXPIRATION);

    return generateToken(email, role, expirationDate);
  }

  public String createRefreshToken(String email, UserRole role) {

    Date expirationDate = generateExpirationDate(REFRESH_TOKEN_EXPIRATION);

    return generateToken(email, role, expirationDate);
  }

  public String getAccessTokenFromHeader(HttpServletRequest request) {

    String authorizationHeader = request.getHeader("Authorization");

    if (!StringUtils.hasText(authorizationHeader) || !authorizationHeader.startsWith(BEARER_PREFIX)) {
      return null;
    }

    return authorizationHeader.substring(7);
  }

  public ResponseCookie createCookieRefreshToken(String refreshToken) {
    return ResponseCookie.from("refreshToken", refreshToken)
        .httpOnly(true)
        .maxAge(REFRESH_TOKEN_EXPIRATION / 1000)
        .path("/")
        .sameSite("Strict")
        .build();
  }

  public ResponseCookie createCookieRefreshToken(String refreshToken, Date expirationDate) {

    long maxAge = (expirationDate.getTime() - new Date().getTime()) / 1000;

    return ResponseCookie.from("refreshToken", refreshToken)
        .httpOnly(true)
        .maxAge(maxAge)
        .path("/")
        .sameSite("Strict")
        .build();
  }

  public Claims getClaimsFromToken(String token) {

    try {
      return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    } catch (ExpiredJwtException e) {
      log.error("만료된 토큰 입니다.");
      throw e;
    } catch (JwtException e) {
      log.error("유효하지 않은 토큰 입니다.");
      throw e;
    }

  }

}
