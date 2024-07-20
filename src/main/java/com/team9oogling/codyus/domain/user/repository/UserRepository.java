package com.team9oogling.codyus.domain.user.repository;

import com.team9oogling.codyus.domain.user.entity.User;
import com.team9oogling.codyus.domain.user.entity.UserStatus;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByemail(String email);

  // 비활성화 된 사용자 조회
  List<User> findByStatusAndInactivatedAtBefore(UserStatus status, Date date);
}
