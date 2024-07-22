package com.team9oogling.codyus.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateProfilePasswordRequestDto {

  @NotBlank(message = "비밀번호를 입력해주세요.")
  private String password;

  @NotBlank(message = "비밀번호를 입력해주세요.")
  @Size(min = 8, max = 15, message = "비밀번호는 8자 이상, 15자 이하여야 합니다.")
  @Pattern(regexp = "(?=.*[a-zA-Z0-9])(?=.*[!@#$%^&*()_+=?<>{};:'/]).+", message = "비밀번호는 영문 대소문자와 숫자, 특수 문자로 이루어져야 합니다.")
  private String newPassword;

  @NotBlank(message = "비밀번호를 입력해주세요.")
  private String checkPassword;

}
