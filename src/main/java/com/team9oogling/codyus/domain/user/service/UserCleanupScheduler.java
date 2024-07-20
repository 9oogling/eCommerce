package com.team9oogling.codyus.domain.user.service;

import com.team9oogling.codyus.domain.user.entity.User;
import com.team9oogling.codyus.domain.user.entity.UserStatus;
import com.team9oogling.codyus.domain.user.repository.UserRepository;
import java.util.Date;
import java.util.List;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class UserCleanupScheduler {

  private final UserRepository userRepository;

  public UserCleanupScheduler(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Scheduled(cron = "0 0 0 * * ?") // 매일 자정에 실행
  public void deleteInactiveUsers() {
    Date thirtyDaysAgo = new Date(System.currentTimeMillis() - 2592000000L); // 30일 전
    List<User> inactiveUsers = userRepository.findByStatusAndInactivatedAtBefore(UserStatus.INACTIVE, thirtyDaysAgo);

    for (User user : inactiveUsers) {
      userRepository.delete(user);
    }
  }
}
