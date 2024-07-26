package com.team9oogling.codyus.domain.oauth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class KakaoUserInfoDto {

  private String email;
  private String nickname;

  public KakaoUserInfoDto(String email, String nickname) {
    this.email = email;
    this.nickname = nickname;
  }
}