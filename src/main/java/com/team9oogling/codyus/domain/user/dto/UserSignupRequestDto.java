package com.team9oogling.codyus.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserSignupRequestDto {

  @NotBlank(message = "이메일을 입력해주세요.")
  @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$", message = "이메일 형식이 올바르지 않습니다.")
  private String email;

  @NotBlank(message = "비밀번호를 입력해주세요.")
  @Size(min = 8, max = 15, message = "비밀번호는 8자 이상, 15자 이하여야 합니다.")
  @Pattern(regexp = "(?=.*[a-zA-Z0-9])(?=.*[!@#$%^&*()_+=?<>{};:'/]).+", message = "비밀번호는 영문 대소문자와 숫자, 특수 문자로 이루어져야 합니다.")
  private String password;

  @NotBlank
  private String checkPassword;

  @NotBlank(message = "닉네임을 입력해주세요.")
  private String nickname;

}