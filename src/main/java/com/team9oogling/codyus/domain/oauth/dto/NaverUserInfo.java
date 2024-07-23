package com.team9oogling.codyus.domain.oauth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NaverUserInfo {
  private String email;
  private String nickname;
}