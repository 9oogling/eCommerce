package com.team9oogling.codyus.domain.user.entity;

import com.team9oogling.codyus.domain.user.dto.UpdateProfileAddressRequestDto;
import com.team9oogling.codyus.domain.user.dto.UpdateProfilePasswordRequestDto;
import com.team9oogling.codyus.domain.user.dto.UpdateProfilePhoneNumberRequestDto;
import com.team9oogling.codyus.domain.user.dto.UserSignupRequestDto;
import com.team9oogling.codyus.global.entity.Timestamped;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class User extends Timestamped {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  private String nickname;

  @Column(nullable = false)
  @Enumerated(value = EnumType.STRING)
  private UserRole role;

  @Column(nullable = false)
  @Enumerated(value = EnumType.STRING)
  private UserStatus status;

  @Column
  private String address;

  @Column
  private String phoneNumber;

  @Column
  private String refreshToken;

  @Column
  private Date inactivatedAt; // 비활성화된 시간

  public User(UserSignupRequestDto requestDto, UserRole role, UserStatus status/*, UserOauth oauth*/) {
    this.email = requestDto.getEmail();
    this.password = requestDto.getPassword();
    this.email = requestDto.getEmail();
    this.nickname = requestDto.getNickname();
    this.role = role;
    this.status = status;
    // this.oauth = oauth;
  }

  public void encryptionPassword(String encryptionPassword) {
    this.password = encryptionPassword;
  }

  public void updateRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }

  public void updateStatus(UserStatus status) {
    this.status = status;
  }

  public void updateInactivatedAt(Date inactivatedAt) {
    this.inactivatedAt = inactivatedAt;
  }

  public void updateAddress(UpdateProfileAddressRequestDto requestDto) {
    this.address = requestDto.getAddress();
  }

  public void updatePhoneNumber(UpdateProfilePhoneNumberRequestDto requestDto) {
    this.phoneNumber = requestDto.getPhoneNumber();
  }

}
