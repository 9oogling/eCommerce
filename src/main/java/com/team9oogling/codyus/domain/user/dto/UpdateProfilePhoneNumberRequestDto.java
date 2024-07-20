package com.team9oogling.codyus.domain.user.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UpdateProfilePhoneNumberRequestDto {

  @Pattern(regexp = "^$|^[0-9]{11}$", message = "올바른 휴대폰 번호가 아닙니다.")
  private String phoneNumber;

}
