package com.team9oogling.codyus.global.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.UriComponentsBuilder;

@Configuration
@ConfigurationProperties(prefix = "naver")
public class NaverProperties {
  private String requestTokenUri;
  private String clientId;
  private String clientSecret;

  public String getRequestURL(String code) {
    return UriComponentsBuilder.fromHttpUrl(requestTokenUri)
        .queryParam("grant_type", "authorization_code")
        .queryParam("client_id", clientId)
        .queryParam("client_secret", clientSecret)
        .queryParam("code", code)
        .toUriString();
  }
}