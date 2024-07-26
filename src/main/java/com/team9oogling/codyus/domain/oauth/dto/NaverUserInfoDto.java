package com.team9oogling.codyus.domain.oauth.dto;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NaverUserInfoDto {

  private String email;
  private String nickname;

  public NaverUserInfoDto(String email, String nickname) {
    this.email = email;
    this.nickname = nickname;
  }
}